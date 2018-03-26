package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Videocomment {
    private Integer id;

    private Date ctime;

    private Date utime;

    private Integer callid;

    private String txt;

    private String type;

    private Integer userid;
}