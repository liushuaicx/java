package com.kaishengit.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ScenicAccount implements Serializable {
    private Integer id;

    /**
     * 景区账号
     */
    private String scenicAccount;

    /**
     * 密码
     */
    private String scenicPassword;

    private Date createTime;

    private Date updateTime;

    /**
     * 景区状态
     */
    private String scenicState;

    private Integer scenicId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScenicAccount() {
        return scenicAccount;
    }

    public void setScenicAccount(String scenicAccount) {
        this.scenicAccount = scenicAccount;
    }

    public String getScenicPassword() {
        return scenicPassword;
    }

    public void setScenicPassword(String scenicPassword) {
        this.scenicPassword = scenicPassword;
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

    public String getScenicState() {
        return scenicState;
    }

    public void setScenicState(String scenicState) {
        this.scenicState = scenicState;
    }

    public Integer getScenicId() {
        return scenicId;
    }

    public void setScenicId(Integer scenicId) {
        this.scenicId = scenicId;
    }
}