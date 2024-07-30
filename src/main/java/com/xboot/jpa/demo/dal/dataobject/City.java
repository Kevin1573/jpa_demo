package com.xboot.jpa.demo.dal.dataobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
@Getter
@Setter
@ToString
@Entity
public class City  implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    protected City() {
    }

    public City(String name, String state) {
        this.name = name;
        this.state = state;
    }
}
