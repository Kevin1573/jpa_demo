package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.Student;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	Page<Student> findAll(Pageable pageable);

	@Query(value = "select * from pt_student where name = ?1 and upper(state) = upper(?2) limit 1", nativeQuery = true)
	Student findByNameAndStateAllIgnoringCase(String name, String state);

	@Transactional
	@Modifying
	@Query(value = "update Student s set s.updateTime = ?1 where s.id = ?2")
	void updateStudent(LocalDateTime updatedTime, Long id);
}