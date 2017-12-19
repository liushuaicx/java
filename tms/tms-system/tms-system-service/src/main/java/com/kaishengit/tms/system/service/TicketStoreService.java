package com.kaishengit.tms.system.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;

/**
 * @author liushuai
 */
public interface TicketStoreService {

    /**
     * 查询ticketStore并分页
     * @param pageNo
     * @return
     */
    PageInfo<TicketStore> findAllByPageNo(Integer pageNo);

    /**
     * 新增售票点,并创建售票点账号
     * @param ticketStore
     * @return
     */
    StoreAccount newTicketStore(TicketStore ticketStore);

    /**
     * 根据查询TicketStore信息
     * @param ticketStoreId
     * @return
     */
    TicketStore findByTicketStoreId(Integer ticketStoreId);

    /**
     * 修改信息
     * @param ticketStore
     */
    void editTicketStore(TicketStore ticketStore);
}
