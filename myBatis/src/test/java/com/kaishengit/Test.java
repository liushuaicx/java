package com.kaishengit;

import com.kaishengit.entity.Student;

import java.util.Arrays;
import java.util.List;

public class Test {

    @org.junit.Test
    public void ArraysTest() {

        List<Student> studentList = Arrays.asList(new Student("tom","123"));

        for (Student student : studentList ) {

            System.out.println(student);
        }
    }



}
