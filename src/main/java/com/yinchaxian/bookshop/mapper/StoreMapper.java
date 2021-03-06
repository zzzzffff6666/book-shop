package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreMapper {
    @Insert("insert into store " +
            "values(null, #{managerId}, #{name}, #{phone}, " +
            "#{position}, #{introduction}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "storeId", keyColumn = "store_id")
    int insert(Store store);

    @Delete("delete from store " +
            "where store_id = #{storeId}")
    int delete(int storeId);

    @Update("update store " +
            "set name = #{name}, " +
            "phone = #{phone}, " +
            "position = #{position}, " +
            "introduction = #{introduction}, " +
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
    Store selectByManager(int userId);

    @Select("select * " +
            "from store " +
            "where name like '%${name}%'")
    @ResultType(Store.class)
    List<Store> searchByName(String name);

    @Select("select manager_id " +
            "from store " +
            "where store_id = #{storeId}")
    @ResultType(Integer.class)
    Integer selectManagerId(int storeId);

    @Select("select store_id " +
            "from store " +
            "where manager_id = #{managerId}")
    @ResultType(Integer.class)
    Integer selectStoreId(int managerId);
}
