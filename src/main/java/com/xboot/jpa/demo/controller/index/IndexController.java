package com.xboot.jpa.demo.controller.index;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.xboot.jpa.demo.common.resp.Result;
import com.xboot.jpa.demo.controller.req.StudentReq;
import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.service.StudentService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    // 注解式鉴权：当前会话必须登录才能通过
    @SaCheckLogin
    @PostMapping("/hello")
    public Result hello(@RequestBody(required = false) StudentReq studentReq) {
        log.info("[CTRL] send say hi request");
        List<Student> students = studentService.getStudents(studentReq);
        return Result.ok().data("students", students);
    }

    // 注解式鉴权：当前会话必须具有指定角色标识才能通过
    @SaCheckRole("super-admin")
    @GetMapping("/create")
    public Result create() {
        log.info("[CTRL] create request");
        String name = "student";
        Student student = new Student();
        student.setAddress("XIAN's park");
        student.setAge(18);
        student.setGrade(13); // 大一
        student.setName("XBOOT 本节我们详细讲解了列注解上的性insertable和updatable以及columnDefinition。");
        student.setState("init");
        Student newStud = studentService.createStu(student);
        Student stu = new Student();
        stu.setName(name);
        stu.setGrade(10);
        stu.setAge(19);
        stu.setAddress("xian");
        stu.setState("normal");
        stu.setId(10L);
        studentService.createStu(stu);
        return Result.ok().data(name, newStud);
    }

    @GetMapping("/getConditional")
    public Result getResultForConditional() {
        Student resultCondition = studentService.getResultCondition();
        return Result.ok().data("student", resultCondition);
    }

    // 注解式鉴权：当前会话必须具有指定权限才能通过
    @SaCheckPermission("user-add")
    @GetMapping("/update/{id}")
    public Result update(@PathVariable("id") Long id) {
        studentService.updateById(id);
        return Result.ok();
    }

    @PutMapping("/update/book")
    public Result update(Long id, String bookType, Long snNumber) {
        return Result.ok();
    }
}
