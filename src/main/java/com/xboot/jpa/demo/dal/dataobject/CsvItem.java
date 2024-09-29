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
@Entity
@Table(name = "csv_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvItem {
    @Id
    private String field1;
    private String field2;
}
