package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {

    int save(Student student);
    List<Student> findAll();
    void delete(int id);
    void update(Student student);
    Student findById(int id);
    List<Student> page(@Param("offset") int offset,@Param("size") int size);
}
