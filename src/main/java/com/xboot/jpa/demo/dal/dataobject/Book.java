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
@Table(name = "pt_book")
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = -1981022477637389083L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String author;
    private Double price;
    private String content;
}
