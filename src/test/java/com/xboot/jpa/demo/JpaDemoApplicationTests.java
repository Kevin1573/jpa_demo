package com.xboot.jpa.demo;

import com.xboot.jpa.demo.dal.dataobject.Book;
import com.xboot.jpa.demo.dal.dataobject.City;
import com.xboot.jpa.demo.dal.dataobject.Student;
import com.xboot.jpa.demo.dal.h2.BookRepository;
import com.xboot.jpa.demo.dal.h2.CityRepository;
import com.xboot.jpa.demo.dal.h2.StudentRepository;
import com.xboot.jpa.demo.service.CityService;
import com.xboot.jpa.demo.service.StudentService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootTest(classes = JpaDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase  // 启动一个内存数据库，不使用真实数据库
public class JpaDemoApplicationTests {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CityService cityService;

    //    @BeforeEach
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


    @Test
    public void testBookRepo() {
        bookRepository.findById(1L).ifPresentOrElse(System.out::println, () -> {
            System.out.println("book not found");
        });

        long count = bookRepository.count();
        System.out.println("count = " + count);
    }

    private static String strBig = "liquibase 数据库版本留痕解决方案，在实际生产过程中如何高效管理数据库的DDL与DML语句，对这些语句留痕处理。如果能将sql的执行与SpringBoot项目启动结合在一起，每次启动项目自动执行新增的sql语句，这样就可以使得项目组成员各个都保持相同的开发库，避免人为操作导致数据库不符合预期。\n" +
            "————————————————\n" +
            "\n" +
            "                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。\n" +
            "                        \n" +
            "原文链接：https://blog.csdn.net/zy_zhangruichen/article/details/128730606";

    @Test
    public void testRollbackBookRepo() {

        List<Book> books = Lists.newArrayList();
        for (int i = 100; i < 254; i++) {
            Book book = new Book();
            book.setName("book-" + i);
            book.setAuthor("author-" + i);
            book.setPrice(10.0);

            String content1 = Utils.getByteStrSub(new String[]{strBig}, 0, i);
            //book.setContent(i % 10 == 0 ? content1 : strBig);
            book.setContent(content1);
            bookRepository.save(book);
            books.add(book);
        }
        // bookRepository.saveAll(books);

        List<Book> contentMaxLength = bookRepository.findContentMaxLength();
        System.out.println("book max length = " + contentMaxLength.get(contentMaxLength.size() - 1));
    }

    // 写一个测试用例，计算jdk17版本中中英文的字节数，并验证结果是否正确。
    @Test
    public void testJdk17StrLength() {
        String str = "你好，世界！";
        System.out.println("str.getBytes().length = " + str.getBytes().length);
        System.out.println("str.getBytes(StandardCharsets.UTF_8).length = " + str.getBytes(UTF_8).length);

        String str1 = "Hello,World!";
        System.out.println("str1.length = " + str1.getBytes().length);
        System.out.println("str1.getBytes(StandardCharsets.UTF_8).length = " + str1.getBytes(UTF_8).length);

        String str2 = "你好,World!";
        System.out.println("str2.length = " + str2.getBytes().length);
        System.out.println("str2.getBytes(StandardCharsets.UTF_8).length = " + str2.getBytes(UTF_8).length);
    }

    @Test
    public void testSubStringWithMaxLength() {
        for (int i = 400; i < 600; i++) {
            String text2 = Utils.getByteStrSub(new String[]{strBig}, 0, i);
            try {
                System.out.println("byteStrSub2 = " + text2.getBytes().length);
                Book book = new Book();
                book.setAuthor("金庸");
                book.setContent(text2);
                book.setName("liquibase");
                bookRepository.save(book);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.err.println("byteLength = " + i);
                System.out.println(text2);
            }

        }

    }
}
