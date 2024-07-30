package com.xboot.jpa.demo.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
