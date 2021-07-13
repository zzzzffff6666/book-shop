package com.yinchaxian.bookshop.controller;

import com.yinchaxian.bookshop.entity.ClickLog;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.LogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 日志相关的访问控制器
 */
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    //
    // ClickLog部分
    //

    /**
     * 删除一段日志
     * @param start 开始时间
     * @param end 结束时间
     * @return 是否成功
     */
    @DeleteMapping(value = {"/click_log/{start}", "/click_log/{start}/{end}"})
    @RequiresPermissions(value = {"click_log:delete", "click_log:*"}, logical = Logical.OR)
    public Result deleteLog(@PathVariable("start") String start,
                            @PathVariable(value = "end", required = false) String end) {
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime;
        if (end == null) endTime = new Timestamp(System.currentTimeMillis());
        else endTime = Timestamp.valueOf(end);
        boolean suc = logService.deleteLog(startTime, endTime);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 查询一段日志
     * @param start 开始时间
     * @param end 结束时间
     * @return 查询结果
     */
    @GetMapping(value = {"/click_log/{start}", "/click_log/{start}/{end}"})
    @RequiresPermissions(value = {"click_log:select", "click_log:*"}, logical = Logical.OR)
    public Result selectLog(@PathVariable("start") String start,
                            @PathVariable(value = "end", required = false) String end) {
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime;
        if (end == null) endTime = new Timestamp(System.currentTimeMillis());
        else endTime = Timestamp.valueOf(end);
        List<ClickLog> list = logService.selectLog(startTime, endTime);
        return Result.success(list);
    }
}
