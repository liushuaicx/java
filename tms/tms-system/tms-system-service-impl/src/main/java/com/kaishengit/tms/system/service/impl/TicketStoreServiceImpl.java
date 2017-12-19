package com.kaishengit.tms.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.entity.TicketStoreExample;
import com.kaishengit.tms.mapper.StoreAccountMapper;
import com.kaishengit.tms.mapper.TicketStoreMapper;
import com.kaishengit.tms.system.service.TicketStoreService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liushuai
 */
@Service
public class TicketStoreServiceImpl implements TicketStoreService {

    private static Logger logger = LoggerFactory.getLogger(TicketStoreServiceImpl.class);

    @Autowired
    private TicketStoreMapper ticketStoreMapper;
    @Autowired
    private StoreAccountMapper storeAccountMapper;

    @Override
    public PageInfo<TicketStore> findAllByPageNo(Integer pageNo) {

        PageHelper.startPage(pageNo,10);
        TicketStoreExample ticketStoreExample = new TicketStoreExample();
        List<TicketStore> ticketStoreList = ticketStoreMapper.selectByExample(ticketStoreExample);
        return new PageInfo<>(ticketStoreList);
    }

    @Override
    public StoreAccount newTicketStore(TicketStore ticketStore) {
        //添加售票点
        ticketStore.setCreateTime(new Date());
        ticketStoreMapper.insert(ticketStore);
        //添加售票点账号
        StoreAccount storeAccount = new StoreAccount();
        storeAccount.setCreateTime(new Date());
        storeAccount.setTicketStoreId(ticketStore.getId());
        String password = new Md5Hash(123).toString();
        storeAccount.setStorePassword(password);
        storeAccount.setStoreAccount(ticketStore.getStoreTel());
        storeAccount.setStoreState("正常");
        storeAccountMapper.insert(storeAccount);
        logger.info("添加售票点{},账号为{}",ticketStore.getStoreName(),storeAccount.getStoreAccount());
        return storeAccount;
    }

    /**
     * 查询TicketStore信息
     * @param ticketStoreId
     * @return
     */
    @Override
    public TicketStore findByTicketStoreId(Integer ticketStoreId) {

        return ticketStoreMapper.selectByPrimaryKey(ticketStoreId);
    }

    /**
     * 修改信息
     * @param ticketStore
     */
    @Override
    public void editTicketStore(TicketStore ticketStore) {

        ticketStore.setUpdateTime(new Date());
        ticketStoreMapper.updateByPrimaryKey(ticketStore);
        logger.info("修改{}售票点信息",ticketStore.getStoreName());
    }
}
