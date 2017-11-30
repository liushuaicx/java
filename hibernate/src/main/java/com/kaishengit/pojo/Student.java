package com.kaishengit.pojo;

import javax.persistence.*;
import javax.persistence.Column;

/**
 * @author 刘帅
 */
@Entity
@Table(name = "t_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stu_id")
    private Integer stuId;
    @Column(name = "stu_name")
    private String stuName;
    @Column(name = "stu_address")
    private String stuAddress;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuAddress() {
        return stuAddress;
    }

    public void setStuAddress(String stuAddress) {
        this.stuAddress = stuAddress;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", stuAddress='" + stuAddress + '\'' +
                '}';
    }
}
