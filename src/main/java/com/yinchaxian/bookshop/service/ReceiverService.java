package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Receiver;
import com.yinchaxian.bookshop.mapper.ReceiverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiverService {
    @Autowired
    private ReceiverMapper receiverMapper;

    public boolean insertReceiver(Receiver receiver) {
        return receiverMapper.insert(receiver) == 1;
    }

    public boolean deleteReceiver(int receiverId) {
        return receiverMapper.delete(receiverId) == 1;
    }

    public boolean updateReceiver(Receiver receiver) {
        return receiverMapper.update(receiver) == 1;
    }

    public Receiver selectReceiver(int receiverId) {
        return receiverMapper.select(receiverId);
    }

    public List<Receiver> selectReceiverByUser(int userId) {
        return receiverMapper.selectByUser(userId);
    }

    public int selectReceiverUserId(int receiverId) {
        return receiverMapper.selectUserId(receiverId);
    }
}
