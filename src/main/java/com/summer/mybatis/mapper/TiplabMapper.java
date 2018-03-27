package com.summer.mybatis.mapper;

import com.summer.mybatis.entity.Tiplab;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface TiplabMapper {
    @Delete({
        "delete from tiplab",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tiplab (content, enable, ",
        "ctime)",
        "values (#{content,jdbcType=VARCHAR}, #{enable,jdbcType=INTEGER}, ",
        "#{ctime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Tiplab record);

    @Select({
        "select",
        "id, content, enable, ctime",
        "from tiplab",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP)
    })
    Tiplab selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, content, enable, ctime",
        "from tiplab"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Tiplab> selectAll();

    @Update({
        "update tiplab",
        "set content = #{content,jdbcType=VARCHAR},",
          "enable = #{enable,jdbcType=INTEGER},",
          "ctime = #{ctime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Tiplab record);
}