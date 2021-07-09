package com.yinchaxian.bookshop.utils;

import java.sql.Timestamp;

public class TimeUtil {
    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
