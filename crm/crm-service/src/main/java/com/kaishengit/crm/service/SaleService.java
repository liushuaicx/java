package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
public interface SaleService {

    /**
     * 根据客户查询所有 的销售机会
     * @return
     * @param id
     */
    List<SaleChance> findAllSaleWithCustomerName(Integer id);

    /**
     * 添加机会
     * @param user
     * @param saleChance
     */
    void newChange(User user, SaleChance saleChance);

    /**
     * 查询机会详情
     * @param id
     * @return
     */
    SaleChance findSaleById(Integer id);

    /**
     * 查询进程列表
     * @return
     */
    List<String> findProgressList();

    /**
     * 更新当前进度
     * @param id
     * @param progress
     */
    void updateProgress(Integer id, String progress);

    /**
     * 通过销售机会id查询跟进记录
     * @param id
     * @return
     */
    List<SaleChanceRecord> findSaleChanceRecordList(Integer id);

    /**
     * 新增跟进记录
     * @param content
     * @param saleId
     */
    void newRecord(String content, Integer saleId);

    /**
     * 根据id删除机会
     * @param id
     */
    void delSaleChance(Integer id);

    /**
     * 查询进度列表
     * @param id
     * @return
     */
    List<Map<String,Object>> findSaleChanceCountByProgress(Integer id);
}
