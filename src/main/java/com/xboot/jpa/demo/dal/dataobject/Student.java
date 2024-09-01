package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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
    @SequenceGenerator(name = "pt_student_seq", sequenceName = "pt_student_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 非空
    @Column(length = 600, nullable = false)
    private String name;
    private Integer age;

    @Column(name = "state", nullable = false)
    private String state;
    private Integer grade;
    private String address;
    @Column(name = "director")
    @ColumnDefault("'staff 100'")
    @Generated
    private String director; // 班主任
    @Column(name = "CREATE_TIME", columnDefinition = "timestamp default current_timestamp")
    @Generated
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME", columnDefinition = "timestamp default current_timestamp")
    @Generated(event = EventType.UPDATE)
    private LocalDateTime updateTime;
}
