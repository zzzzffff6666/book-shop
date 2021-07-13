package com.yinchaxian.bookshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinchaxian.bookshop.entity.Comment;
import com.yinchaxian.bookshop.entity.Reply;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.CommentService;
import com.yinchaxian.bookshop.service.OrderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 评论和回复相关的访问控制器
 */
@RestController
public class CommentController {
    private static final int commentPageAmount = 10;
    private static final int replyPageAmount = 10;

    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderService orderService;

    //
    // Comment部分
    //

    /**
     * 新建一条评论
     * 注意：没有购买书籍的话不能评论
     * @param comment 评论信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/comment")
    @RequiresAuthentication
    public Result insertComment(@RequestBody Comment comment, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int times = orderService.selectOrderTimesByUserAndBook(id, comment.getBookId());
        if (times <= 0) {
            return Result.error(ErrorMessage.commentError);
        }
        comment.setUserId(id);
        comment.setUsername(session.getAttribute("username").toString());
        comment.setDate(new Timestamp(System.currentTimeMillis()));
        boolean suc = commentService.insertComment(comment);
        return suc ? Result.success() : Result.error(ErrorMessage.insertError);
    }

    /**
     * 用户删除自己的一条评论
     * @param commentId 评论ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/comment/{commentId}")
    @RequiresPermissions(value = {"comment:delete", "comment:*"}, logical = Logical.OR)
    public Result deleteComment(@PathVariable("commentId") int commentId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = commentService.selectCommentUserId(commentId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        boolean suc = commentService.deleteComment(commentId, id);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 管理员删除一条评论
     * @param commentId 评论ID
     * @return 是否成功
     */
    @DeleteMapping("/comment/admin/{commentId}")
    @RequiresPermissions("comment:*")
    public Result deleteCommentByAdmin(@PathVariable("commentId") int commentId) {
        boolean suc = commentService.deleteCommentByAdmin(commentId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新一条评论
     * @param comment 评论信息
     * @param commentId 评论ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/comment/{commentId}")
    @RequiresPermissions(value = {"comment:update", "comment:*"}, logical = Logical.OR)
    public Result updateComment(@RequestBody Comment comment, @PathVariable("commentId") int commentId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = commentService.selectCommentUserId(commentId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        comment.setCommentId(commentId);
        comment.setUserId(id);
        comment.setDate(new Timestamp(System.currentTimeMillis()));
        boolean suc = commentService.updateComment(comment);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询一条评论
     * @param commentId 评论ID
     * @return 查询结果
     */
    @GetMapping("/comment/{commentId}")
    @RequiresAuthentication
    public Result selectComment(@PathVariable("commentId") int commentId) {
        Comment comment = commentService.selectComment(commentId);
        return Result.success(comment);
    }

    /**
     * 用户查询自己的评论
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping(value = {"/comment/user/list", "/comment/user/list/{page}"})
    @RequiresAuthentication
    public Result selectCommentByUser(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        PageHelper.startPage(page, commentPageAmount);
        PageInfo<Comment> list = new PageInfo<>(commentService.selectCommentByUser(id));
        return Result.success(list);
    }

    /**
     * 查询书籍的评论
     * @param bookId 书籍ID
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/comment/book/{bookId}/list", "/comment/book/{bookId}/list/{page}"})
    @RequiresAuthentication
    public Result selectCommentByBook(@PathVariable("bookId") long bookId,
                                      @PathVariable(value = "page", required = false) Integer page) {
        PageHelper.startPage(page, commentPageAmount);
        PageInfo<Comment> list = new PageInfo<>(commentService.selectCommentByBook(bookId));
        return Result.success(list);
    }

    //
    // Reply部分
    //

    /**
     * 新建一条回复
     * @param reply 回复信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/reply")
    @RequiresAuthentication
    public Result insertReply(@RequestBody Reply reply, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        String username = session.getAttribute("username").toString();
        reply.setUserId(id);
        reply.setUsername(username);
        reply.setDate(new Timestamp(System.currentTimeMillis()));
        boolean suc = commentService.insertReply(reply);
        return suc ? Result.success() : Result.error(ErrorMessage.insertError);
    }

    /**
     * 用户删除自己的一条回复
     * @param replyId 回复ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/reply/{replyId}")
    @RequiresPermissions(value = {"reply:delete", "reply:*"}, logical = Logical.OR)
    public Result deleteReply(@PathVariable("replyId") int replyId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = commentService.selectReplyUserId(replyId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        boolean suc = commentService.deleteReply(replyId, id);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 管理员删除一条回复
     * @param replyId 回复ID
     * @return 是否成功
     */
    @DeleteMapping("/reply/admin/{replyId}")
    @RequiresPermissions("reply:*")
    public Result deleteReplyByAdmin(@PathVariable("replyId") int replyId) {
        boolean suc = commentService.deleteReplyByAdmin(replyId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 用户更新一条回复
     * @param reply 回复信息
     * @param replyId 回复ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/reply/{replyId}")
    @RequiresPermissions(value = {"reply:update", "reply:*"}, logical = Logical.OR)
    public Result updateReply(@RequestBody Reply reply, @PathVariable("replyId") int replyId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = commentService.selectReplyUserId(replyId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        reply.setReplyId(replyId);
        reply.setUserId(id);
        reply.setDate(new Timestamp(System.currentTimeMillis()));
        boolean suc = commentService.updateReply(reply);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询一条回复
     * @param replyId 回复ID
     * @return 查询结果
     */
    @GetMapping("/reply/{replyId}")
    @RequiresAuthentication
    public Result selectReply(@PathVariable("replyId") int replyId) {
        Reply reply = commentService.selectReply(replyId);
        return Result.success(reply);
    }

    /**
     * 查询用户自己的回复
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping(value = {"/reply/user/list", "/reply/user/list/{page}"})
    @RequiresAuthentication
    public Result selectReplyByUser(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        PageHelper.startPage(page, replyPageAmount);
        PageInfo<Reply> list = new PageInfo<>(commentService.selectReplyByUser(id));
        return Result.success(list);
    }

    /**
     * 查询评论的回复
     * @param commentId 评论ID
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/reply/comment/{commentId}/list", "/reply/comment/{commentId}/list/{page}"})
    @RequiresAuthentication
    public Result selectReplyByComment(@PathVariable("commentId") int commentId,
                                       @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, replyPageAmount);
        PageInfo<Reply> list = new PageInfo<>(commentService.selectReplyByComment(commentId));
        return Result.success(list);
    }
}
