package com.kaishengit.crm.mapper;


import java.util.List;

import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.example.UserExample;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findByNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    List<User> findByDeptId(@Param("userName") String userName,@Param("deptId") Integer deptId,
                            @Param("start") Integer start,@Param("length") Integer length);

    Long countByDepyId(@Param("deptId") Integer deptId);
}