package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 刘帅
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
//    开启级联删除
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<Address> addressSet;

    public Set<Address> getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set<Address> addressSet) {
        this.addressSet = addressSet;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressSet=" + addressSet +
                '}';
    }
}
