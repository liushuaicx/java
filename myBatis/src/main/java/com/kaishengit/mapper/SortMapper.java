package com.kaishengit.mapper;

import com.kaishengit.entity.Sort;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SortMapper {

    @Select("select * from t_sort where id = #{id}")
    Sort findById();
}
