package com.kaishengit.crm.entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author FOREIGN_KEY_CHECKS
 */
public class Task implements Serializable {
    private Integer id;

    private String title;

    private String finishTime;

    /**
     * 提醒
     */
    private String remindTime;

    private Byte done;

    private Integer userId;

    private Integer custId;

    private Integer saleId;

    private Date createTime;

    private Customer customer;

    private SaleChance saleChance;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SaleChance getSaleChance() {
        return saleChance;
    }

    public void setSaleChance(SaleChance saleChance) {
        this.saleChance = saleChance;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public Byte getDone() {
        return done;
    }

    public void setDone(Byte done) {
        this.done = done;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 判断是否逾期,true为完成时间在现在之前,逾期,false 为正常
     * @return
     */
    public boolean isOverTime() {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime finishTime = formatter.parseDateTime(getFinishTime());
        return finishTime.isBeforeNow();
    }
}