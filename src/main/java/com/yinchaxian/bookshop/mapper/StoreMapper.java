package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreMapper {
    @Insert("insert into store " +
            "values(null, #{managerId}, #{storeName}, #{storePhone}, #{storePosition}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "storeId", keyColumn = "store_id")
    int insert(Store store);

    @Delete("delete from store " +
            "where store_id = #{storeId}")
    int delete(int storeId);

    @Update("update store " +
            "set manager_id = #{managerId}, " +
            "store_name = #{storeName}, " +
            "store_phone = #{storePhone}, " +
            "store_position = #{storePosition}, " +
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
            "where store_name like '%${pattern}%'")
    @ResultType(Store.class)
    List<Store> search(String pattern);
}
