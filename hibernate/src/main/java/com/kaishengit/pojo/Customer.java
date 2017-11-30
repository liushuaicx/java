package com.kaishengit.pojo;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 刘帅
 */
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GenericGenerator(name = "noAuto",strategy = "uuid")
    @GeneratedValue(generator = "noAuto")
    private String id;
    private String name;
    @Version
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
