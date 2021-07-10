package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.ClickLog;
import com.yinchaxian.bookshop.mapper.ClickLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private ClickLogMapper clickLogMapper;

    public boolean insertLog(ClickLog clickLog) {
        return clickLogMapper.insert(clickLog) == 1;
    }

    public boolean deleteLog(Timestamp start, Timestamp end) {
        return clickLogMapper.delete(start, end) > 0;
    }

    public List<ClickLog> selectLog(Timestamp start, Timestamp end) {
        return clickLogMapper.select(start, end);
    }
}
