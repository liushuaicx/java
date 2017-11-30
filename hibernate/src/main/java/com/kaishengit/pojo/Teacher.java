package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 刘帅
 */
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    /*mappedBy : Stu表的属性*/
    @ManyToMany(mappedBy = "teacherSet")
    private Set<Stu> stuSet;

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

    public Set<Stu> getStuSet() {
        return stuSet;
    }

    public void setStuSet(Set<Stu> stuSet) {
        this.stuSet = stuSet;
    }
}
