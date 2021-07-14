package com.yinchaxian.bookshop;

import com.yinchaxian.bookshop.service.*;
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

    }

}
