package com.kaishengit.entity;

public class Student {

    private int stuId;
    private String StuName;
    private String StuAddress;

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", StuName='" + StuName + '\'' +
                ", StuAddress='" + StuAddress + '\'' +
                '}';
    }

    public Student(String stuName, String stuAddress) {
        StuName = stuName;
        StuAddress = stuAddress;
    }

    public Student() {}

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getStuAddress() {
        return StuAddress;
    }

    public void setStuAddress(String stuAddress) {
        StuAddress = stuAddress;
    }
}
