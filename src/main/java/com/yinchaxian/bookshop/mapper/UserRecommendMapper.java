package com.yinchaxian.bookshop.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zhang
 * @date: 2021/7/15 15:27
 * @description:
 */
public interface UserRecommendMapper {
    @Select("select book_id " +
            "from user_recommend " +
            "where user_id = #{userId} " +
            "order by rand()" +
            "limit 20")
    List<Integer> selectRecommend20(int userId);
}
