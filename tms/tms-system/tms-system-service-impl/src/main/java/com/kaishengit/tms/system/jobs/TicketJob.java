package com.kaishengit.tms.system.jobs;

import com.kaishengit.tms.system.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 添加定时任务
 * @author liushuai
 */
@Component
public class TicketJob {

    @Autowired
    private StoreService storeService;

    public void checkEndTime() {
        
        storeService.checkEndTime();
    }

}
