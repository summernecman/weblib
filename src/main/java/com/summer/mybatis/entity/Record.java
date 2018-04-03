package com.summer.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Record {

    public static final String ATYPE_VIDEO = "video";

    public static final String ATYPE_IMAGE = "image";

    public static final String ATYPE_TEXT = "video";

    private Integer id;

    private String locpath;

    private String netpath;

    private Long ctime;

    private Long utime;

    private String atype;

    private String btype;

    private String address;

    private Long duration;

    private String name;

    private String content;

    public boolean isUploaded = false;

}