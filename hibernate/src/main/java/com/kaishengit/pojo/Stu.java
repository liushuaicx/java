package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 刘帅
 */
@Entity
public class Stu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToMany
    /*name: 关联关系表,joinColumns: 在关联表中对应的id , inverseJoinColumns :对应的连接列*/
    @JoinTable(name = "stu_teacher",joinColumns = {@JoinColumn(name = "stu_id")},
                                    inverseJoinColumns ={@JoinColumn(name = "teacher_id")})
    private Set<Teacher> teacherSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }
}
