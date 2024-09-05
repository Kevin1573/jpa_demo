package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
@ToString
@Data
@Entity
@Table(name = "api_table")
public class ApiTable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1740081353141212472L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tableId;
    private String tableName;
    private String tableComment;
}
