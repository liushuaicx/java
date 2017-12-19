package com.kaishengit.tms.system.service.impl;

import com.kaishengit.tms.entity.*;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.CustomerMapper;
import com.kaishengit.tms.mapper.StoreLoginLogMapper;
import com.kaishengit.tms.mapper.TicketMapper;
import com.kaishengit.tms.mapper.TicketOrderMapper;
import com.kaishengit.tms.system.service.StoreService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author liushuai
 */
@Service
public class StoreServiceImpl implements StoreService {

    private static final String BANPIAO = "半票";
    private static final String JIHUO = "激活";
    private static final String DONGJIE = "冻结";
    private static final String ZUOFEI = "作废";

    private static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private StoreLoginLogMapper storeLoginLogMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private TicketOrderMapper ticketOrderMapper;

    @Override
    public void saveLoginLog(String ip, StoreAccount principal) {

        StoreLoginLog storeLoginLog = new StoreLoginLog();
        storeLoginLog.setLoginIp(ip);
        storeLoginLog.setLoginTime(new Date());
        storeLoginLog.setStoreAccountId(principal.getId());
        storeLoginLogMapper.insert(storeLoginLog);
    }

    /**
     * 年票办理功能
     * @param customer  客户信息
     * @param ticketNum 卡号
     * @param id        对应的售StoreAccountId
     * @param ticketOrderType
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addCustomer(Customer customer, Integer ticketNum, Integer id, String ticketOrderType) {

        Ticket ticket = getTicket(ticketNum);
        String state = ticket.getTicketState();
        if (state == ZUOFEI) {
            throw new ServiceException("本卡已作废,请重新填写");
        }
        Date date = new Date();
        //添加客户信息
        customer.setCreateTime(date);
        customer.setTicketId(ticket.getId());
        customerMapper.insert(customer);
        //修改票状态
        ticket.setTicketState(JIHUO);
        ticket.setUpdateTime(date);
        ticket.setStoreAccountId(id);
        ticket.setTicketValidatyStart(date);

        //日期增加一年
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,1);
        ticket.setTicketValidatyEnd(calendar.getTime());
        ticket.setCustomerId(customer.getId());
        ticketMapper.updateByPrimaryKey(ticket);

        //添加订单信息
        TicketOrder ticketOrder = new TicketOrder();
        String ticketOrderNum = String.valueOf(System.currentTimeMillis() + id);
        ticketOrder.setTicketOrderNum(ticketOrderNum);
        ticketOrder.setCreateTime(date);
        ticketOrder.setTicketId(ticket.getId().longValue());
        ticketOrder.setCustomerId(customer.getId().longValue());
        ticketOrder.setStoreAccountId(id);
        ticketOrder.setTicketOrderType(ticketOrderType);
        if (ticketOrderType == BANPIAO) {
            ticketOrder.setTicketOrderPrice(new BigDecimal(50));
        }else {
            ticketOrder.setTicketOrderPrice(new BigDecimal(100));
        }
        ticketOrderMapper.insert(ticketOrder);

        logger.info("年票办理成功,客户身份证号为{},卡号为{}",customer.getCustomerIdCard(),ticket.getTicketNum());
    }


    /**
     * 续费年票
     * @param ticketNum 卡号
     * @param money     金额
     * @return 到期时间
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Date payment(Integer ticketNum, Integer money) {

        Ticket ticket = getTicket(ticketNum);
        if (ticket.getTicketState() != JIHUO || ticket.getTicketState() != DONGJIE ) {
            throw new ServiceException("本卡号不存在,或已注销,请重新填写");
        }else if (ticket.getTicketState() == JIHUO) {

            Date date = ticket.getTicketValidatyEnd();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Integer num = money / 100;
            Date endTime;
            //根据ticket对象的customerId获得订单信息
            String ticketOrderType = getTicketOrder(ticket).getTicketOrderType();
            if (ticketOrderType == BANPIAO) {
                calendar.add(Calendar.YEAR,num * 2);
                endTime = calendar.getTime();
                ticket.setTicketValidatyEnd(endTime);
            }else {
                calendar.add(Calendar.YEAR,num);
                endTime = calendar.getTime();
                ticket.setTicketValidatyEnd(endTime);
            }
        }else if (ticket.getTicketState() == DONGJIE) {
            //续费 并修改为激活状态
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Integer num = money / 100;
            Date endTime;
            //根据ticket对象的customerId获得订单信息
            String ticketOrderType = getTicketOrder(ticket).getTicketOrderType();
            if (ticketOrderType == BANPIAO) {
                calendar.add(Calendar.YEAR,num*2);
                endTime = calendar.getTime();
                ticket.setTicketValidatyEnd(endTime);
                ticket.setTicketState("激活");
            }else {
                calendar.add(Calendar.YEAR,num);
                endTime = calendar.getTime();
                ticket.setTicketValidatyEnd(endTime);
                ticket.setTicketState("激活");
            }
        }
        ticketMapper.updateByPrimaryKey(ticket);

        //添加订单信息
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setTicketOrderPrice(new BigDecimal(money));
        ticketOrder.setTicketOrderType("续费");
        ticketOrder.setStoreAccountId(ticket.getStoreAccountId());
        ticketOrder.setCustomerId(ticket.getCustomerId());
        ticketOrder.setCreateTime(new Date());
        String ticketOrderNum = String.valueOf(System.currentTimeMillis() + ticket.getStoreAccountId());
        ticketOrder.setTicketOrderNum(ticketOrderNum);
        ticketOrder.setTicketId(ticket.getId().longValue());

        ticketOrderMapper.insert(ticketOrder);

        logger.info("年票续费,卡号为{},缴费金额为{},并添加订单信息",ticketNum,money);
        return ticket.getTicketValidatyEnd();

    }

    /**
     * 年票挂失
     * @param ticketNum
     * @param idCardNum
     */
    @Override
    public void reportLoss(Integer ticketNum, String idCardNum) throws ServiceException {

        Customer customer = getCustomer(idCardNum);

        Ticket ticket = getTicket(ticketNum);
        if (!ticket.getCustomerId().equals(customer.getId())) {
            throw new ServiceException("挂失卡和身份证不匹配");
        }

        ticket.setTicketState("挂失");
        ticket.setUpdateTime(new Date());
        ticketMapper.updateByPrimaryKey(ticket);

        logger.info("年票号{}挂失",ticketNum);
    }


    /**
     * 年票解挂
     *
     * @param ticketNum
     * @param idCardNum
     * @throws ServiceException
     */
    @Override
    public void restore(Integer ticketNum, String idCardNum) throws ServiceException {

        Customer customer = getCustomer(idCardNum);
        Ticket ticket = getTicket(ticketNum);
        if (!customer.getId().equals(ticket.getCustomerId())) {
            throw new ServiceException("身份证和卡不匹配");
        }
        if (ticket.getTicketState() == "挂失") {
            ticket.setTicketState("激活");
            ticket.setUpdateTime(new Date());
            ticketMapper.updateByPrimaryKey(ticket);
        }else {
            throw new ServiceException("该卡不可解除挂失");
        }
        logger.info("年票号{}解除挂失",ticketNum);
    }

    /**
     * 年票补办
     * @param idCardNum
     * @param storeAccountId
     * @param newTicketNum
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void replace(String idCardNum, Integer storeAccountId, Integer newTicketNum) throws ServiceException {

        Customer customer = getCustomer(idCardNum);
        Ticket ticket = ticketMapper.selectByPrimaryKey(customer.getTicketId());
        if (ticket == null) {
            throw new ServiceException("该身份证下不存在年票");
        }
        if (ticket.getTicketState() != "挂失") {
            throw new ServiceException("请先挂失后再补办");
        }
        //补办一张新卡
        Ticket ticket1 = getTicket(newTicketNum);
        ticket1.setTicketState("激活");
        ticket1.setCreateTime(new Date());
        ticket1.setTicketValidatyStart(new Date());
        ticket1.setTicketValidatyEnd(ticket.getTicketValidatyEnd());
        ticket1.setCustomerId(ticket.getId().longValue());
        ticket1.setStoreAccountId(storeAccountId);
        ticketMapper.insert(ticket1);

        //旧卡作废
        ticket.setTicketState("作废");
        ticket.setUpdateTime(new Date());
        ticketMapper.updateByPrimaryKey(ticket);
        //添加订单
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setTicketId(ticket1.getId().longValue());
        String ticketOrderNum = String.valueOf(System.currentTimeMillis() + storeAccountId);
        ticketOrder.setTicketOrderNum(ticketOrderNum);
        ticketOrder.setCreateTime(new Date());
        ticketOrder.setCustomerId(customer.getId());
        ticketOrder.setStoreAccountId(storeAccountId);
        ticketOrder.setTicketOrderType("补卡");
        ticketOrder.setTicketOrderPrice(new BigDecimal(0));
        ticketOrderMapper.insert(ticketOrder);

        logger.info("补办新卡,卡号为{},作废卡号为{},并创建补卡订单",newTicketNum,ticket.getTicketNum());
    }


    /**
     * 获得Ticket对象
     * @param ticketNum
     * @return
     */
    private Ticket getTicket(Integer ticketNum) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);
        return ticketMapper.selectByExample(ticketExample).get(0);
    }

    /**
     * 根据身份证号获得账户
     * @param idCardNum
     * @return
     */
    private Customer getCustomer(String idCardNum) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andCustomerIdCardEqualTo(idCardNum);
        return customerMapper.selectByExample(customerExample).get(0);
    }

    /**
     * 获得ticketOrder
     * @param ticket
     * @return
     */
    private TicketOrder getTicketOrder(Ticket ticket) {
        TicketOrderExample ticketOrderExample = new TicketOrderExample();
        ticketOrderExample.createCriteria().andCustomerIdEqualTo(ticket.getCustomerId());
        return ticketOrderMapper.selectByExample(ticketOrderExample).get(0);
    }

    /**
     * 检查石否过期
     */
    @Override
    public void checkEndTime() {

        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketStateBetween("激活","挂失");
        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);

        for (Ticket ticket : ticketList) {
            Date date = ticket.getTicketValidatyEnd();
            DateTime dateTime = new DateTime(date.getTime());
            if (dateTime.isBeforeNow()) {
                ticket.setTicketState("冻结");
            }
        }

    }
}
