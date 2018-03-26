package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Videodetail {
    private Integer id;

    private Date ctime;

    private Date utime;

    private Integer callid;

    private String url;

    private Integer uploaded;

    private Integer userid;

    private Integer time;
}