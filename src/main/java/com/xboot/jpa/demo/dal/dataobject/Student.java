package com.xboot.jpa.demo.dal.dataobject;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

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
    // 非空
    @Column(length = 600, nullable = false)
    private String name;
    private Integer age;
    // 非空
    @Column(nullable = false)
    // @Column(name = "state", columnDefinition = "tinyint default 0")
    private String state;
    private Integer grade;
    private String address;
    @Column(name = "director")
    @ColumnDefault("'staff 100'")
    @Generated(value = GenerationTime.INSERT)
    private String director; // 班主任
    @Column(name = "CREATE_TIME", columnDefinition = "timestamp default current_timestamp")
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime updateTime;
}
