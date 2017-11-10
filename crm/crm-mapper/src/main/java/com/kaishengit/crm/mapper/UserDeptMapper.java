package com.kaishengit.crm.mapper;


import java.util.List;

import com.kaishengit.crm.entity.UserDeptKey;
import com.kaishengit.crm.example.UserDeptExample;
import org.apache.ibatis.annotations.Param;

public interface UserDeptMapper {
    long countByExample(UserDeptExample example);

    int deleteByExample(UserDeptExample example);

    int deleteByPrimaryKey(UserDeptKey key);

    int insert(UserDeptKey record);

    int insertSelective(UserDeptKey record);

    List<UserDeptKey> selectByExample(UserDeptExample example);

    int updateByExampleSelective(@Param("record") UserDeptKey record, @Param("example") UserDeptExample example);

    int updateByExample(@Param("record") UserDeptKey record, @Param("example") UserDeptExample example);
}