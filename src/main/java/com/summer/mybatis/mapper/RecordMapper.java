package com.summer.mybatis.mapper;

import com.summer.mybatis.entity.Record;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface RecordMapper {
    @Delete({
        "delete from record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into record (locpath, netpath, ",
        "ctime, utime, atype, ",
        "btype, address, ",
        "duration, name, content)",
        "values (#{locpath,jdbcType=VARCHAR}, #{netpath,jdbcType=VARCHAR}, ",
        "#{ctime,jdbcType=BIGINT}, #{utime,jdbcType=BIGINT}, #{atype,jdbcType=VARCHAR}, ",
        "#{btype,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{duration,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Record record);

    @Select({
        "select",
        "id, locpath, netpath, ctime, utime, atype, btype, address, duration, name, content",
        "from record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="locpath", property="locpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="netpath", property="netpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.BIGINT),
        @Result(column="utime", property="utime", jdbcType=JdbcType.BIGINT),
        @Result(column="atype", property="atype", jdbcType=JdbcType.VARCHAR),
        @Result(column="btype", property="btype", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    Record selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, locpath, netpath, ctime, utime, atype, btype, address, duration, name, content",
        "from record"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="locpath", property="locpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="netpath", property="netpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.BIGINT),
        @Result(column="utime", property="utime", jdbcType=JdbcType.BIGINT),
        @Result(column="atype", property="atype", jdbcType=JdbcType.VARCHAR),
        @Result(column="btype", property="btype", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="duration", property="duration", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Record> selectAll();

    @Update({
        "update record",
        "set locpath = #{locpath,jdbcType=VARCHAR},",
          "netpath = #{netpath,jdbcType=VARCHAR},",
          "ctime = #{ctime,jdbcType=BIGINT},",
          "utime = #{utime,jdbcType=BIGINT},",
          "atype = #{atype,jdbcType=VARCHAR},",
          "btype = #{btype,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "duration = #{duration,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Record record);

    @Select({"select count(id) from record where locpath = #{locpath,jdbcType=VARCHAR}"})
    int  selectRecordNumWhereLocalPath(String locpath);
    @Select({"select count(id) from record"})
    int getRecordCount();


    @Select({"select * from record where locpath = #{locpath,jdbcType=VARCHAR}"})
    List<Record>  selectRecordWhereLocalPath(String locpath);

    @Update({"update record set netpath = #{netpath,jdbcType=VARCHAR} where locpath = #{locpath,jdbcType=VARCHAR}"})
    int updateNetPath(@Param("netpath") String netpath, @Param("locpath") String locpath);

}