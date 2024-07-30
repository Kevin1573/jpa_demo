package com.xboot.jpa.demo;

import com.xboot.jpa.demo.dal.dataobject.City;
import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.dal.h2.CityRepository;
import com.xboot.jpa.demo.dal.h2.StudentRepository;
import com.xboot.jpa.demo.service.CityService;
import com.xboot.jpa.demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = JpaDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase 启动一个内存数据库，不使用真实数据库
@ActiveProfiles("test")
public class JpaDemoApplicationTests {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityService cityService;

    @BeforeEach
    public void beforeAll() {
        Student stu = new Student();
        stu.setName("student");
        stu.setGrade(10);
        stu.setAge(19);
        stu.setAddress("xian");
        stu.setState("normal");
        stu.setId(10L);
        studentService.createStu(stu);
    }

    @Test
    void contextLoads() {
        studentService.getStudents().forEach(System.out::println);
    }


    @Test
    void updateStudent() {
        Student stu = new Student();
        stu.setName("student");
        stu.setGrade(10);
        stu.setAge(19);
        stu.setAddress("xian");
        stu.setState("normal");
        stu.setId(10L);
        Student stu1 = studentService.createStu(stu);
        stu1.setUpdateTime(LocalDateTime.now());
        stu1.setName("student-change");
        studentRepository.save(stu1);

        Student student = studentRepository.findById(stu1.getId()).get();
        System.out.println(student);
    }

    @Test
    public void testCityRepo() {
        City city = new City("xian", "ok");
        City saved = cityRepository.save(city);
        Assert.isTrue(saved != null, "insert data failed");

        List<City> allIgnoreCases = cityRepository.findByNameAndStateAllIgnoreCase("xian", "ok");
        allIgnoreCases.forEach(c -> {
            System.out.println(c);
            c.setState("no");
            cityService.updateCity(c);
        });

        Page<City> cityPage = cityRepository.findAll(PageRequest.of(0, 10));
        cityPage.getContent().forEach(System.out::println);
    }
}
