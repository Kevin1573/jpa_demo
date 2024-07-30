package com.xboot.jpa.demo.service;

import com.xboot.jpa.demo.dal.dataobject.Student;

import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
public interface StudentService {
    List<Student> getStudents();

    Student createStu(Student student);

    Student getResultCondition();

    void updateById(Long id);
}
