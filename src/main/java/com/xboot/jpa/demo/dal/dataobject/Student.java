package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
@Entity
@Table(name = "pt_student")
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private Integer age;
    @Column(nullable = false)
    private String state;
    private Integer grade;
    private String address;
}
