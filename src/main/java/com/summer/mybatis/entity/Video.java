package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Video {
    private Integer id;

    private String file;

    private Date created;

    private Integer fromid;

    private Integer toid;

    private String fromphone;

    private String tophone;

    private Long timenum;

    private Integer uploaded;

    private Integer callstate;

    private Integer type;

}