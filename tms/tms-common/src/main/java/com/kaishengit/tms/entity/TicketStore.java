package com.kaishengit.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class TicketStore implements Serializable {
    private Integer id;

    private String storeName;

    /**
     * 售票点负责人
     */
    private String storeManager;

    private String storeTel;

    private String storeAddress;

    /**
     * 售票点经纬度-经度
     */
    private String storeGeoLongitude;

    /**
     * 售票点经纬度-维度
     */
    private String storeGeoLatitude;

    /**
     * 营业执照照片
     */
    private String storeAttachment;

    /**
     * 负责人身份证照片
     */
    private String storeManageAttachment;

    private Date createTime;

    private Date updateTime;

    private Integer storeAccountId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(String storeManager) {
        this.storeManager = storeManager;
    }

    public String getStoreTel() {
        return storeTel;
    }

    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreGeoLongitude() {
        return storeGeoLongitude;
    }

    public void setStoreGeoLongitude(String storeGeoLongitude) {
        this.storeGeoLongitude = storeGeoLongitude;
    }

    public String getStoreGeoLatitude() {
        return storeGeoLatitude;
    }

    public void setStoreGeoLatitude(String storeGeoLatitude) {
        this.storeGeoLatitude = storeGeoLatitude;
    }

    public String getStoreAttachment() {
        return storeAttachment;
    }

    public void setStoreAttachment(String storeAttachment) {
        this.storeAttachment = storeAttachment;
    }

    public String getStoreManageAttachment() {
        return storeManageAttachment;
    }

    public void setStoreManageAttachment(String storeManageAttachment) {
        this.storeManageAttachment = storeManageAttachment;
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
}