package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
    private Long id;
    private String message;
    // 定义stackTrace字段类型为text
    @Lob
    private String stackTrace;
}
