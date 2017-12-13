package com.kaishengit.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Ticket implements Serializable {
    private Integer id;

    private Integer ticketNum;

    private Date ticketInTime;

    private Date ticketOutTime;

    private String ticketState;

    private Date createTime;

    private Date updateTime;

    private Integer storeAccountId;

    private Date ticketValidatyStart;

    private Date ticketValidatyEnd;

    private Long customerId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Date getTicketInTime() {
        return ticketInTime;
    }

    public void setTicketInTime(Date ticketInTime) {
        this.ticketInTime = ticketInTime;
    }

    public Date getTicketOutTime() {
        return ticketOutTime;
    }

    public void setTicketOutTime(Date ticketOutTime) {
        this.ticketOutTime = ticketOutTime;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStoreAccountId() {
        return storeAccountId;
    }

    public void setStoreAccountId(Integer storeAccountId) {
        this.storeAccountId = storeAccountId;
    }

    public Date getTicketValidatyStart() {
        return ticketValidatyStart;
    }

    public void setTicketValidatyStart(Date ticketValidatyStart) {
        this.ticketValidatyStart = ticketValidatyStart;
    }

    public Date getTicketValidatyEnd() {
        return ticketValidatyEnd;
    }

    public void setTicketValidatyEnd(Date ticketValidatyEnd) {
        this.ticketValidatyEnd = ticketValidatyEnd;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}