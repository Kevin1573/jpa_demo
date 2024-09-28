package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
@Entity
@Table(name = "batch_log_error")
@NoArgsConstructor
@AllArgsConstructor
public class BatchLogError {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    // 定义stackTrace字段类型为text
    @Lob
    private String stackTrace;
}
