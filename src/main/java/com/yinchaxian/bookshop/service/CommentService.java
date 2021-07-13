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

    public boolean deleteComment(int commentId, int userId) {
        return commentMapper.delete(commentId, userId) == 1;
    }

    public boolean deleteCommentByAdmin(int commentId) {
        return commentMapper.deleteByAdmin(commentId) == 1;
    }

    public boolean updateComment(Comment comment) {
        return commentMapper.update(comment) == 1;
    }

    public Comment selectComment(int commentId) {
        return commentMapper.select(commentId);
    }

    public List<Comment> selectCommentByUser(int userId) {
        return commentMapper.selectByUser(userId);
    }

    public List<Comment> selectCommentByBook(long bookId) {
        return commentMapper.selectByBook(bookId);
    }

    public int selectCommentUserId(int commentId) {
        return commentMapper.selectUserId(commentId);
    }

    public boolean insertReply(Reply reply) {
        return replyMapper.insert(reply) == 1;
    }

    public boolean deleteReply(int replyId, int userId) {
        return replyMapper.delete(replyId, userId) == 1;
    }

    public boolean deleteReplyByAdmin(int replyId) {
        return replyMapper.deleteByAdmin(replyId) == 1;
    }

    public boolean updateReply(Reply reply) {
        return replyMapper.update(reply) == 1;
    }

    public Reply selectReply(int replyId) {
        return replyMapper.select(replyId);
    }

    public List<Reply> selectReplyByUser(int userId) {
        return replyMapper.selectByUser(userId);
    }

    public List<Reply> selectReplyByComment(int commentId) {
        return replyMapper.selectByComment(commentId);
    }

    public int selectReplyUserId(int replyId) {
        return replyMapper.selectUserId(replyId);
    }
}
