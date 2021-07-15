package com.yinchaxian.bookshop.http;

public interface ErrorMessage {
    String loginError = "用户名户密码错误";
    String registerError = "注册失败";
    String accountError = "账户已被禁用";
    String nameError = "用户名已存在";
    String passwordError = "密码错误";
    String insertError = "添加失败";
    String deleteError = "删除失败";
    String updateError = "更新失败";
    String selectError = "无结果";
    String authError = "无权限";
    String parameterError = "参数错误";
    String commentError = "未购买，不能评论";
    String rateError = "未购买，不能评分";
    String unknownError = "未知错误";
    String storeError = "库存不足";
    String shopError = "没有店铺";
}
