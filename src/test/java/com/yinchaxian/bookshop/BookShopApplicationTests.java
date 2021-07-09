package com.yinchaxian.bookshop;

import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookShopApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userMapper.select(2);
        System.out.println(user.toString());
        System.out.println(user.getUserId());
        System.out.println(user.getUsername());
        System.out.println(user.getNickname());
        System.out.println(user.getPassword());
    }

}
