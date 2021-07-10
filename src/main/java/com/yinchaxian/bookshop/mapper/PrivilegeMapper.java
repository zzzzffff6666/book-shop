package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Privilege;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PrivilegeMapper {
    @Insert("insert into privilege " +
            "values(null, #{name}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "privilegeId", keyColumn = "privilege_id")
    int insert(Privilege privilege);

    @Delete("delete from privilege " +
            "where privilege_id = #{privilegeId}")
    int delete(int privilegeId);

    @Update("update privilege " +
            "set name = #{name}, " +
            "permission = #{permission} " +
            "where privilege_id = #{privilegeId}")
    int update(Privilege privilege);

    @Select("select * " +
            "from privilege " +
            "where privilege_id = #{privilegeId}")
    @ResultType(Privilege.class)
    Privilege select(int privilegeId);

    @Select("<script> " +
            "select * " +
            "from privilege " +
            "where privilege_id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    @ResultType(Privilege.class)
    List<Privilege> selectList(@Param("list") List<Integer> list);

    @Select("<script> " +
            "select permission " +
            "from privilege " +
            "where privilege_id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    List<String> selectUrlList(@Param("list") List<Integer> list);
}
