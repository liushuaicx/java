package com.kaishengit.mapper;

import com.kaishengit.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from t_user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userName" , column = "user_name"),
            @Result(property = "userPassword",column = "user_password"),
            @Result(property = "sortId" ,column = "sort_id"),
            @Result(property = "sort",column = "sort_id" ,one = @One(
                    select = "com.kaishengit.mapper.SortMapper.findById"
            ))
    })
    List<User> findAll();
}
