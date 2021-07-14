package com.yinchaxian.bookshop;

import com.yinchaxian.bookshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookShopApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        try {
            int c = orderService.selectOrderUserId("2021-07-14 09:05:27.38#1#300021");
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
