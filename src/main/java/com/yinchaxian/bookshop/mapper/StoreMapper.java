package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreMapper {
    @Insert("insert into store " +
            "values(null, #{managerId}, #{name}, #{phone}, " +
            "#{position}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "storeId", keyColumn = "store_id")
    int insert(Store store);

    @Delete("delete from store " +
            "where store_id = #{storeId}")
    int delete(int storeId);

    @Update("update store " +
            "set manager_id = #{managerId}, " +
            "name = #{name}, " +
            "phone = #{phone}, " +
            "position = #{position}, " +
            "updated = #{updated} " +
            "where store_id = #{storeId}")
    int update(Store store);

    @Select("select * " +
            "from store " +
            "where store_id = #{storeId}")
    @ResultType(Store.class)
    Store select(int storeId);

    @Select("select * " +
            "from store " +
            "where manager_id = #{userId}")
    @ResultType(Store.class)
    List<Store> selectByManager(int userId);

    @Select("select * " +
            "from store " +
            "where name like '%${name}%' " +
            "limit #{offset}, #{amount}")
    @ResultType(Store.class)
    List<Store> searchByName(String name, int offset, int amount);

    @Select("select manager_id " +
            "from store " +
            "where store_id = #{storeId}")
    int selectManagerId(int storeId);
}
