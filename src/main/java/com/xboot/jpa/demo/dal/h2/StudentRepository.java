package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface StudentRepository extends Repository<Student, Long> {

	Page<Student> findAll(Pageable pageable);

	Student findByNameAndStateAllIgnoringCase(String name, String state);

}