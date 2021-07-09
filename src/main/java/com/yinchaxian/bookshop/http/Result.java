package com.yinchaxian.bookshop.http;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    private static final String STATUS_TAG = "status";
    private static final String MESSAGE_TAG = "msg";
    private static final String RESULT_TAG = "result";

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static Result success() {
        return success(null);
    }
    public static Result success(Object obj) {
        Result result = new Result();
        result.put(STATUS_TAG, SUCCESS);
        result.put(RESULT_TAG, obj);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.put(STATUS_TAG, ERROR);
        result.put(MESSAGE_TAG, msg);
        return result;
    }
}
