package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace
public interface StudentMapper {

    @Insert("insert into t_student (stu_name,stu_address) values (#{stuName},#{stuAddress})")
    int save(Student student);
    @Select("select * from t_student")
    List<Student> findAll();
    @Delete("delete from t_student where stu_id = #{stuId}")
    void delete(int id);
    @Update("update t_student set stu_name= #{stuName},stu_address = #{stuAddress} where stu_id = #{stuId}")
    void update(Student student);
    @Select("select * from t_student where stu_id = #{stuId}")
    Student findById(int id);
    @Select("select * from t_student limit #{offset},#{size}")
    List<Student> page(@Param("offset") int offset,@Param("size") int size);
}
