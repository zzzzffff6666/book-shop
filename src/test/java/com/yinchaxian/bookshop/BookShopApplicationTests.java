package com.yinchaxian.bookshop;

import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookShopApplicationTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CommentService commentService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserId(3);
        user.setUsername("bjtu");
        user.setNickname("bjtu");
        String password = "bjtu";
        String salt = RandomStringUtils.random(30, true, true);
        String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
        user.setPassword(encodedPassword);
        user.setSalt(salt);
        user.setActive(1);
        user.setCountry("中国");
        boolean suc = userService.insertUser(user);
        System.out.println("success: " + suc);
        System.out.println("userId: " + user.getUserId());
    }

}
