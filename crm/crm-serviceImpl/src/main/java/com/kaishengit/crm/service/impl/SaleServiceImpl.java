package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.example.SaleChanceExample;
import com.kaishengit.crm.example.SaleChanceRecordExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SaleChanceRecordMapper saleChanceRecordMapper;
    @Autowired
    private CustomerMapper customerMapper;

    //springEL
    @Value("#{'${sale.progress}'.split(',')}")
    private List<String> progressList;

    /**
     * 根据客户查询所有 的销售机会
     * @param
     * @param id
     * @return
     */
    @Override
    public List<SaleChance> findAllSaleWithCustomerName(Integer id) {


        List<SaleChance> saleChanceList = saleChanceMapper.selectAllWithCustomerName(id);

        //查询销售机会对应的客户
        return saleChanceList;
    }

    /**
     * 添加机会
     *
     * @param user
     * @param saleChance
     */
    @Override
    public void newChange(User user, SaleChance saleChance) {
        saleChance.setUserId(user.getId());
        saleChance.setCreateTime(new Date());
        saleChance.setLastTime(new Date());
        saleChanceMapper.insertSelective(saleChance);
    }

    /**
     * 查询机会详情
     * @param id
     * @return
     */
    @Override
    public SaleChance findSaleById(Integer id) {
        return saleChanceMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询进程列表
     * @return
     */
    @Override
    public List<String> findProgressList() {
        return progressList;
    }

    /**
     * 更新当前进度
     * @param id
     * @param progress
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProgress(Integer id, String progress) {

        //增加一条跟进记录
        SaleChanceRecord saleChanceRecord = new SaleChanceRecord();
        saleChanceRecord.setContent("当前进度修改为["+progress+"]");
        saleChanceRecord.setCreateTime(new Date());
        saleChanceRecord.setSaleId(id);

        saleChanceRecordMapper.insert(saleChanceRecord);

        //修改当前进度
        SaleChance saleChance = new SaleChance();
        saleChance.setProgress(progress);
        saleChance.setLastTime(new Date());
        saleChance.setId(id);
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //修改客户资料的最后跟进时间
        //1.根据saleChanceId查询saleChance
        SaleChance sale = saleChanceMapper.selectByPrimaryKey(id);

        Customer customer = customerMapper.selectByPrimaryKey(sale.getCustId());
        customer.setLastContractTime(new Date());
        customerMapper.updateByPrimaryKey(customer);

    }

    /**
     * 通过销售机会id查询跟进记录
     *
     * @param id
     * @return
     */
    @Override
    public List<SaleChanceRecord> findSaleChanceRecordList(Integer id) {

        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(id);

        List<SaleChanceRecord> saleChanceRecordList = saleChanceRecordMapper.selectByExample(saleChanceRecordExample);
        return saleChanceRecordList;
    }

    /**
     * 新增跟进记录
     * @param content
     * @param saleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newRecord(String content, Integer saleId) {

        //新增跟进记录
        SaleChanceRecord saleChanceRecord = new SaleChanceRecord();
        saleChanceRecord.setSaleId(saleId);
        saleChanceRecord.setContent(content);
        saleChanceRecord.setCreateTime(new Date());

        saleChanceRecordMapper.insert(saleChanceRecord);
        //修改最后跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleId);
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKey(saleChance);
        //修改客户资料的跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());

        customer.setLastContractTime(new Date());
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 根据SaleId删除销售机会
     * @param id
     */
    @Override
    public void delSaleChance(Integer id) {

        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        //删除销售机会的跟进记录
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(id);
        saleChanceRecordMapper.deleteByExample(saleChanceRecordExample);

        //删除销售机会
        saleChanceMapper.deleteByPrimaryKey(id);



        //查询客户所有的销售机会
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(saleChance.getCustId());
        saleChanceExample.setOrderByClause("last_time desc");
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);

        //查询客户
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());

        //判断客户销售机会是否大于1
        if (!saleChanceList.isEmpty()) {
            //修改客户资料的跟进时间为上一次销售机会的时间
            customer.setLastContractTime(saleChanceList.get(0).getLastTime());

        }else {
            //设置最后跟进时间为null
            customer.setLastContractTime(null);
        }
        customerMapper.updateByPrimaryKey(customer);

    }

    /**
     * 查询进度列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> findSaleChanceCountByProgress(Integer id) {

        List<Map<String, Object>> result = saleChanceMapper.findSaleChanceCountByProgress(id);

        return  result;
    }

}
