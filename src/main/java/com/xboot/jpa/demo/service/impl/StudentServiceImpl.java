package com.xboot.jpa.demo.service.impl;

import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.dal.h2.StudentRepository;
import com.xboot.jpa.demo.service.StudentService;
import lombok.AllArgsConstructor;
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
    public List<Student> getStudents() {
        Page<Student> studentPage = studentRepository.findAll(PageRequest.of(0, 10));
        return studentPage.getContent();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Student createStu(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getResultCondition() {
        return studentRepository.findByNameAndStateAllIgnoringCase("student", "normal");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateById(Long id) {
        studentRepository.updateStudent(LocalDateTime.now(), id);
    }
}
