package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.ConversionIndex;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zhang
 * @date: 2021/7/15 22:27
 * @description:
 */
public interface ConversionIndexMapper {
    @Select("select * " +
            "from conversion_index")
    @ResultType(ConversionIndex.class)
    List<ConversionIndex> selectAll();
}
