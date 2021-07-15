package com.yinchaxian.bookshop;

import com.yinchaxian.bookshop.entity.BookRate;
import com.yinchaxian.bookshop.entity.Store;
import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.mapper.BookMapper;
import com.yinchaxian.bookshop.mapper.BookRateMapper;
import com.yinchaxian.bookshop.recommend.MyRecommender;
import com.yinchaxian.bookshop.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private BookRateMapper bookRateMapper;
    @Autowired
    private BookMapper bookMapper;


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

    @Test
    void contextLoad2() {
        Store store = new Store();
        store.setStoreId(20);
        store.setManagerId(14);
        store.setName("zzz");
        store.setPhone("1231122312");
        store.setIntroduction("cccc");
        boolean suc = storeService.updateStore(store);
        System.out.println(suc);
    }

    @Test
    void contextLoad3() {
        try {
            MyRecommender.getRecommendForUser(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoad4() {
        List<Long> books = bookMapper.selectBookId();
        for (int i = 0; i < 10; ++i) {
            List<BookRate> list = new ArrayList<>();
            for (long b : books) {
                if (Math.random() > 0.1) continue;
                int score = (int) ((Math.random() * 10 + Math.random() * 10 + 3) / 2);
                BookRate rate = new BookRate();
                rate.setUserId(i);
                rate.setBookId(b);
                rate.setScore(score);
                list.add(rate);
            }
            bookRateMapper.insertAll(list);
        }
    }

}
