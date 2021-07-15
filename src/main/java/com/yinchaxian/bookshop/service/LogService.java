package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.ClickLog;
import com.yinchaxian.bookshop.entity.ConversionIndex;
import com.yinchaxian.bookshop.mapper.ClickLogMapper;
import com.yinchaxian.bookshop.mapper.ConversionIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private ClickLogMapper clickLogMapper;
    @Autowired
    private ConversionIndexMapper conversionIndexMapper;

    public List<ClickLog> selectLogByTime(Timestamp start, Timestamp end) {
        return clickLogMapper.selectByTime(start, end);
    }

    public List<ClickLog> selectLogByUserId(String userId) {
        return clickLogMapper.selectByUserId(userId);
    }

    public List<ConversionIndex> selectAllConversionIndex() {
        return conversionIndexMapper.selectAll();
    }
}
