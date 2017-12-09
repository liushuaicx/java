package com.kaishengit.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class Product implements Serializable {
    private Integer id;

    private String productName;

    private String poductTitle;

    private Integer productInventory;

    private String productImage;

    private BigDecimal productPrice;

    private BigDecimal productMarketPrice;

    private Date startTime;

    private Date endTime;

    private String productDesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPoductTitle() {
        return poductTitle;
    }

    public void setPoductTitle(String poductTitle) {
        this.poductTitle = poductTitle;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductMarketPrice() {
        return productMarketPrice;
    }

    public void setProductMarketPrice(BigDecimal productMarketPrice) {
        this.productMarketPrice = productMarketPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public boolean isStart() {
        DateTime dateTime = new DateTime(getStartTime().getTime());
        return dateTime.isBeforeNow();
    }

    public boolean isEnd() {
        DateTime dateTime = new DateTime(getEndTime().getTime());
        return dateTime.isBeforeNow();
    }

    public Long getStartTimeTs() {
        return getStartTime().getTime();
    }
}