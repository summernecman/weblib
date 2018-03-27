package com.summer.mybatis.mapper;

import com.summer.mybatis.entity.Record;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface RecordMapper {
    @Delete({
        "delete from record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into record (locpath, netpath, ",
        "ctime, utime, ",
        "atype, btype, address, ",
        "content)",
        "values (#{locpath,jdbcType=VARCHAR}, #{netpath,jdbcType=VARCHAR}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP}, ",
        "#{atype,jdbcType=VARCHAR}, #{btype,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Record record);

    @Select({
        "select",
        "id, locpath, netpath, ctime, utime, atype, btype, address, content",
        "from record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="locpath", property="locpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="netpath", property="netpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="atype", property="atype", jdbcType=JdbcType.VARCHAR),
        @Result(column="btype", property="btype", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    Record selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, locpath, netpath, ctime, utime, atype, btype, address, content",
        "from record"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="locpath", property="locpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="netpath", property="netpath", jdbcType=JdbcType.VARCHAR),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="atype", property="atype", jdbcType=JdbcType.VARCHAR),
        @Result(column="btype", property="btype", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Record> selectAll();

    @Update({
        "update record",
        "set locpath = #{locpath,jdbcType=VARCHAR},",
          "netpath = #{netpath,jdbcType=VARCHAR},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP},",
          "atype = #{atype,jdbcType=VARCHAR},",
          "btype = #{btype,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Record record);
}