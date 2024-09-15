package com.xboot.jpa.demo.service.impl;

import com.xboot.jpa.demo.controller.req.StudentReq;
import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.dal.h2.StudentRepository;
import com.xboot.jpa.demo.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents(StudentReq studentReq) {
        Page<Student> studentPage;
        if (studentReq == null || studentReq.getName() == null && studentReq.getState() == null) {
            studentPage = studentRepository.findAll(PageRequest.of(0, 10));
        } else {
            Student student = new Student();
            BeanUtils.copyProperties(studentReq, student);
            studentPage = studentRepository.findAll(Example.of(student), PageRequest.of(0, 10));
        }

        return studentPage.getContent();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Student createStu(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getResultCondition(String name, String state) {
        return studentRepository.findByNameAndStateAllIgnoringCase(name, state);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateById(Long id) {
        studentRepository.updateStudent(LocalDateTime.now(), id);
    }
}
