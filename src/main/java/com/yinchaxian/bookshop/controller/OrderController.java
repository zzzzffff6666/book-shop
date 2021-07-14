package com.yinchaxian.bookshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinchaxian.bookshop.entity.Book;
import com.yinchaxian.bookshop.entity.Order;
import com.yinchaxian.bookshop.entity.OrderDetail;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.BookService;
import com.yinchaxian.bookshop.service.OrderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 订单相关的访问控制器
 */
@RestController
public class OrderController {
    private static final int orderPageAmount = 20;

    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;

    //
    // Order部分 和 OrderDetail部分
    //

    /**
     * 新建订单并新建对应的订单详情
     * @param order 订单信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/order")
    @RequiresPermissions(value = {"order:insert", "order:*"}, logical = Logical.OR)
    public Result insertOrder(@RequestBody Order order, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        String s = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(current);
        String orderId = s + "_" + order.getStoreId() + "_" + id;

        Book book = bookService.selectBook(order.getBookId());
        if (book.getStoreMount() < order.getOrderMount()) {
            return Result.error(ErrorMessage.storeError);
        }

        order.setOrderId(orderId);
        order.setUserId(id);
        order.setStoreId(book.getStoreId());
        order.setBookName(book.getName());
        order.setPrice(book.getPrice() * order.getOrderMount());
        order.setCreated(current);
        order.setUpdated(current);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setBookId(book.getBookId());
        orderDetail.setBookName(book.getName());
        orderDetail.setImageUrl(book.getImageUrl());
        orderDetail.setMount(order.getOrderMount());
        orderDetail.setUnitPrice(book.getPrice());
        orderDetail.setTotalPrice(order.getPrice());

        boolean suc = orderService.insertOrder(order) && orderService.insertOrderDetail(orderDetail);
        if (suc) {
            bookService.updateBookStoreMount(book.getBookId(), -order.getOrderMount());
            return Result.success();
        }
        return Result.error(ErrorMessage.insertError);
    }

    /**
     * 用户删除订单
     * 注意：只有未支付和已完成的订单才可以删除
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/order/{orderId}")
    @RequiresPermissions(value = {"order:delete", "order:*"}, logical = Logical.OR)
    public Result deleteOrder(@PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        Order order = orderService.selectOrder(orderId);
        boolean suc = orderService.deleteOrder(orderId);
        if (suc) {
            bookService.updateBookStoreMount(order.getBookId(), order.getOrderMount());
            return Result.success();
        }
        return Result.error(ErrorMessage.deleteError);
    }

    /**
     * 管理员删除订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @DeleteMapping("/order/admin/{orderId}")
    @RequiresPermissions("order:*")
    public Result deleteOrderByAdmin(@PathVariable("orderId") String orderId) {
        boolean suc = orderService.deleteOrderByAdmin(orderId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新订单信息和订单详细信息
     * @param order 订单信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/info/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderInfo(@RequestBody Order order,
                                  @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setUpdated(current);

        boolean suc = orderService.updateOrderInfo(order);
        if (suc) {
            return Result.success(orderService.updateOrderDetailInfo(order.getOrderId(), order.getOrderMount()));
        }
        return Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新已付款状态
     * @param params 需要更改的信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/pay")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderPay(@RequestBody Map<String, String> params, HttpSession session) {
        int id = (int) session.getAttribute("userId");

        Timestamp current = new Timestamp(System.currentTimeMillis());
        boolean suc = true;
        for (String orderId : params.keySet()) {
            int userId = orderService.selectOrderUserId(orderId);
            if (id != userId) {
                return Result.error(ErrorMessage.authError);
            }

            // 检查是否已支付

            int paymentType = Integer.parseInt(params.get(orderId));
            suc = suc & orderService.updateOrderPay(orderId, id, paymentType, current);
        }

        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新已发货状态
     * @param params 需要更改的信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/delivery/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderDelivery(@RequestBody Map<String, String> params,
                                      @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = orderService.selectOrderStoreId(orderId);
        if (id != storeId) {
            return Result.error(ErrorMessage.authError);
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        boolean suc = orderService.updateOrderDelivery(orderId, storeId, current);
        if (suc) {
            String shippingName = params.get("shippingName");
            String shippingCode = params.get("shippingCode");
            if (shippingName == null || shippingCode == null) {
                return Result.error(ErrorMessage.parameterError);
            }
            return Result.success(orderService.updateOrderDetailShipping(orderId, shippingName, shippingCode));
        }
        return Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新签收状态
     * @param params 需要更改的信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/post/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderDetailPost(@RequestBody Map<String, String> params,
                                        @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = orderService.selectOrderStoreId(orderId);
        if (id != storeId) {
            return Result.error(ErrorMessage.authError);
        }

        String postStatus = params.get("postStatus");
        String receiveStatus = params.get("receiveStatus");
        boolean suc = orderService.updateOrderDetailPost(orderId, postStatus, receiveStatus);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新完成交易状态
     * @param params 需要更改的信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/finish/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderFinish(@RequestBody Map<String, String> params,
                                    @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        boolean suc = orderService.updateOrderFinish(orderId, current);
        if (suc) {
            Order order = orderService.selectOrder(orderId);
            bookService.updateBookDeal(order.getBookId(), order.getOrderMount());
            return Result.success();
        }
        return Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新订单异常状态
     * @param params 需要更改的信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/error/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderError(@RequestBody Map<String, String> params,
                                   @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        int storeId = orderService.selectOrderStoreId(orderId);
        if (id != userId && id != storeId) {
            return Result.error(ErrorMessage.authError);
        }

        Timestamp current = new Timestamp(System.currentTimeMillis());
        boolean suc = orderService.updateOrderError(orderId, current);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新反馈和评分
     * @param params 需要更改的信息
     * @param orderId 订单ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/order/feedback/{orderId}")
    @RequiresPermissions(value = {"order:update", "order:*"}, logical = Logical.OR)
    public Result updateOrderDetailFeedback(@RequestBody Map<String, String> params,
                                            @PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }

        String feedback = params.get("feedback");
        int score = Integer.parseInt(params.get("score"));
        boolean suc = orderService.updateOrderDetailFeedback(orderId, feedback, score);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询订单信息
     * 注意：只能查询自己的订单或者自己店铺的订单
     * @param orderId 订单ID
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping("/order/{orderId}")
    @RequiresPermissions(value = {"order:select", "order:*"}, logical = Logical.OR)
    public Result selectOrder(@PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        int storeId = orderService.selectOrderStoreId(orderId);
        if (id != userId || id != storeId) {
            return Result.error(ErrorMessage.authError);
        }

        Order order = orderService.selectOrder(orderId);
        return Result.success(order);
    }

    /**
     * 查询用户自己的订单
     * @param status 订单状态，没有该项则默认查询所有订单
     *               0：未付款（购物车）
     *               1：已付款
     *               2：已发货
     *               3：成功
     *               4：异常状态
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping({"/order/user/list", "/order/user/list/{page}"})
    @RequiresPermissions(value = {"order:select", "order:*"}, logical = Logical.OR)
    public Result selectAllOrderByUser(@RequestBody(required = false) Integer status,
                                       @PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        if (status == null) {
            PageHelper.startPage(page, orderPageAmount);
            PageInfo<Order> list = new PageInfo<>(orderService.selectOrderByUser(id));
            return Result.success(list);
        } else {
            PageHelper.startPage(page, orderPageAmount);
            PageInfo<Order> list = new PageInfo<>(orderService.selectOrderByUserAndStatus(id, status));
            return Result.success(list);
        }
    }

    /**
     * 查询店铺内的订单
     * @param status 订单状态，没有该项则默认查询所有订单
     *               0：未付款（购物车）
     *               1：已付款
     *               2：已发货
     *               3：成功
     *               4：异常状态
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping({"/order/stroe/list", "/order/stroe/list/{page}"})
    @RequiresPermissions(value = {"order:select", "order:*"}, logical = Logical.OR)
    public Result selectAllOrderByStore(@RequestBody(required = false) Integer status,
                                        @PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        if (status == null) {
            PageHelper.startPage(page, orderPageAmount);
            PageInfo<Order> list = new PageInfo<>(orderService.selectOrderByStore(id));
            return Result.success(list);
        } else {
            PageHelper.startPage(page, orderPageAmount);
            PageInfo<Order> list = new PageInfo<>(orderService.selectOrderByStoreAndStatus(id, status));
            return Result.success(list);
        }
    }

    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping("/order/detail/{orderId}")
    @RequiresPermissions(value = {"order:select", "order:*"}, logical = Logical.OR)
    public Result selectOrderDetail(@PathVariable("orderId") String orderId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = orderService.selectOrderUserId(orderId);
        int storeId = orderService.selectOrderStoreId(orderId);
        if (id != userId || id != storeId) {
            return Result.error(ErrorMessage.authError);
        }
        OrderDetail orderDetail = orderService.selectOrderDetail(orderId);
        return Result.success(orderDetail);
    }
}
