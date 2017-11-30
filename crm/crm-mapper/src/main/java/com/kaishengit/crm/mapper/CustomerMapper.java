package com.kaishengit.crm.mapper;

import java.util.List;
import java.util.Map;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.example.CustomerExample;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {

    long countByExample(CustomerExample example);

    int deleteByExample(CustomerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerExample example);

    int updateByExample(@Param("record") Customer record, @Param("example") CustomerExample example);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Map<String,Object>> findCustomerCountByLever(Integer userId);

    List<Map<String,Object>> findCustomerCountByCreateTime(Integer id);
}