package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryMapper {
    @Insert("insert into category " +
            "values(null, #{parentId}, #{name}, #{sortOrder}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "cateId", keyColumn = "cate_id")
    int insert(Category category);

    @Delete("delete from category " +
            "where cate_id = #{cateId}")
    int delete(int cateId);

    @Update("update category " +
            "set parent_id = #{parentId}, " +
            "name = #{name}, " +
            "sort_order = #{sortOrder}, " +
            "updated = #{updated} " +
            "where cate_id = #{cateId}")
    int update(Category category);

    @Select("select * " +
            "from category " +
            "where cate_id = #{cateId} " +
            "limit #{offset}, #{amount}")
    @ResultType(Category.class)
    Category select(int cateId, int offset, int amount);

    @Select("select * " +
            "from category " +
            "limit #{offset}, #{amount}")
    @ResultType(Category.class)
    List<Category> selectAll(int offset, int amount);

    @Select("select * " +
            "from category " +
            "where name like '%${pattern}%' " +
            "limit #{offset}, #{amount}")
    @ResultType(Category.class)
    List<Category> search(String pattern, int offset, int amount);
}
