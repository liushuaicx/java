package com.kaishengit.tms.system.service;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.exception.ServiceException;

import java.util.Date;

/**
 * @author liushuai
 */
public interface StoreService {

    /**
     * 保存登陆日志
     * @param ip
     * @param principal
     */
    void saveLoginLog(String ip, StoreAccount principal);

    /**
     * 年票办理功能
     * @param customer 客户信息
     * @param ticketNum 卡号
     * @param id 对应的售票点id
     * @param ticketOrderType 年票类型
     * @throws ServiceException
     */
    void addCustomer(Customer customer, Integer ticketNum, Integer id, String ticketOrderType) throws ServiceException;

    /**
     * 检查石否过期
     */
    void checkEndTime();

    /**
     * 续费年票
     * @param ticketNum 卡号
     * @param money 金额
     * @return 到期时间
     */
    Date payment(Integer ticketNum, Integer money);

    /**
     * 年票挂失
     * @param idCardNum
     * @throws ServiceException
     */
    void reportLoss(String idCardNum) throws ServiceException;

    /**
     * 年票解挂
     * @param ticketNum
     * @param idCardNum
     * @throws ServiceException
     */
    void restore(Integer ticketNum, String idCardNum)throws ServiceException;

    /**
     * 年票补办
     * @param idCardNum
     * @param storeAccountId
     * @param newTicketNum
     * @throws ServiceException
     */
    void replace(String idCardNum, Integer newTicketNum,Integer storeAccountId) throws ServiceException;

    /**
     * 查询年票状态
     * @param ticketNum
     * @return
     */
    Ticket findByTicketNum(Integer ticketNum);
}
