package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Comment;
import com.yinchaxian.bookshop.entity.Reply;
import com.yinchaxian.bookshop.mapper.CommentMapper;
import com.yinchaxian.bookshop.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    public boolean insertComment(Comment comment) {
        return commentMapper.insert(comment) == 1;
    }

    public boolean deleteComment(int commentId) {
        return commentMapper.delete(commentId) == 1;
    }

    public boolean updateComment(Comment comment) {
        return commentMapper.update(comment) == 1;
    }

    public Comment selectComment(int commentId) {
        return commentMapper.select(commentId);
    }

    public List<Comment> selectCommentByUser(int userId, int offset, int amount) {
        return commentMapper.selectByUser(userId, offset, amount);
    }

    public List<Comment> selectCommentByBook(long bookId, int offset, int amount) {
        return commentMapper.selectByBook(bookId, offset, amount);
    }

    public boolean insertReply(Reply reply) {
        return replyMapper.insert(reply) == 1;
    }

    public boolean deleteReply(int replyId) {
        return replyMapper.delete(replyId) == 1;
    }

    public boolean updateReply(Reply reply) {
        return replyMapper.update(reply) == 1;
    }

    public Reply selectReply(int replyId) {
        return replyMapper.select(replyId);
    }

    public List<Reply> selectReplyByComment(int commentId) {
        return replyMapper.selectByComment(commentId);
    }

    public List<Reply> selectReplyByUser(int userId) {
        return replyMapper.selectByUser(userId);
    }
}
