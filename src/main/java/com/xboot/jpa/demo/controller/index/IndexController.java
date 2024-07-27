package com.xboot.jpa.demo.controller.index;

import com.xboot.jpa.demo.common.rs.Result;
import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@RestController
@RequestMapping("/index")
@AllArgsConstructor
@Slf4j
public class IndexController {
    private final StudentService studentService;


    @RequestMapping("/hello")
    public Result hello() {
        log.info("send request");
        List<Student> students = studentService.getStudents();
        return Result.ok().data("students", students);
    }
}
