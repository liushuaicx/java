package com.kaishengit.entity;

public class User {
    private Integer id;

    private String userName;

    private String userPassword;

    private Integer sortId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", sortId=" + sortId +
                '}';
    }

    public User(String userName, String userPassword, Integer sortId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.sortId = sortId;
    }

    public User() {}
}