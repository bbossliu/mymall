package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author liux
 * @since 2020-03-07 18:00:19
 */
public class PmsBaseSaleAttr implements Serializable {
    private static final long serialVersionUID = -65728757937490021L;
    @Id
    @Column
    private Long id;
    @Column
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}