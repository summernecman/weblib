package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usertype {
    private Integer id;

    private Integer type;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}