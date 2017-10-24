package com.kaishengit.entity;

public class Sort {

    private int id;
    private String sortName;

    @Override
    public String toString() {
        return "Sort{" +
                "id=" + id +
                ", sortName='" + sortName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
