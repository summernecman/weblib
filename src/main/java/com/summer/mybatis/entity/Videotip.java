package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Videotip {
    private Integer id;

    private Date ctime;

    private Date utime;

    private String type;

    private String txt;

    private Integer enable;
}