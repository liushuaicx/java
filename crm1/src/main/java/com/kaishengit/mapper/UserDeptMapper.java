package com.kaishengit.mapper;

import com.kaishengit.entity.UserDeptExample;
import com.kaishengit.entity.UserDeptKey;
import java.util.List;
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