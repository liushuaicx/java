package com.kaishengit.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Scenic implements Serializable {
    private Integer id;

    /**
     * 景区名
     */
    private String scenicName;

    /**
     * 星级
     */
    private String scenicLevel;

    /**
     * 地址
     */
    private String scenicAddress;

    /**
     * 经度
     */
    private String scenicGeoLongitude;

    /**
     * 纬度
     */
    private String scenicGeoLatitude;

    /**
     * 联系人
     */
    private String scenicManager;

    private String scenicTel;

    /**
     * 景区介绍
     */
    private String scenicIntro;

    private Date createTime;

    private Date updateTime;

    /**
     * 景区营业执照照片
     */
    private String scenicAttachment;

    private Integer scenicAccountId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getScenicLevel() {
        return scenicLevel;
    }

    public void setScenicLevel(String scenicLevel) {
        this.scenicLevel = scenicLevel;
    }

    public String getScenicAddress() {
        return scenicAddress;
    }

    public void setScenicAddress(String scenicAddress) {
        this.scenicAddress = scenicAddress;
    }

    public String getScenicGeoLongitude() {
        return scenicGeoLongitude;
    }

    public void setScenicGeoLongitude(String scenicGeoLongitude) {
        this.scenicGeoLongitude = scenicGeoLongitude;
    }

    public String getScenicGeoLatitude() {
        return scenicGeoLatitude;
    }

    public void setScenicGeoLatitude(String scenicGeoLatitude) {
        this.scenicGeoLatitude = scenicGeoLatitude;
    }

    public String getScenicManager() {
        return scenicManager;
    }

    public void setScenicManager(String scenicManager) {
        this.scenicManager = scenicManager;
    }

    public String getScenicTel() {
        return scenicTel;
    }

    public void setScenicTel(String scenicTel) {
        this.scenicTel = scenicTel;
    }

    public String getScenicIntro() {
        return scenicIntro;
    }

    public void setScenicIntro(String scenicIntro) {
        this.scenicIntro = scenicIntro;
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

    public String getScenicAttachment() {
        return scenicAttachment;
    }

    public void setScenicAttachment(String scenicAttachment) {
        this.scenicAttachment = scenicAttachment;
    }

    public Integer getScenicAccountId() {
        return scenicAccountId;
    }

    public void setScenicAccountId(Integer scenicAccountId) {
        this.scenicAccountId = scenicAccountId;
    }
}