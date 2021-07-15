package com.yinchaxian.bookshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinchaxian.bookshop.entity.Store;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.BookService;
import com.yinchaxian.bookshop.service.StoreService;
import com.yinchaxian.bookshop.service.UserService;
import com.yinchaxian.bookshop.shiro.PermissionRealm;
import com.yinchaxian.bookshop.shiro.ShiroConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 店铺相关的访问控制器
 */
@RestController
public class StoreController {
    private static final int storePageAmount = 20;

    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionRealm permissionRealm;

    //
    // Store部分：
    //

    /**
     * 新建一个店铺
     * @param store 店铺信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/store")
    @RequiresPermissions(value = {"store:*", "store:insert"}, logical = Logical.OR)
    public Result insertStore(@RequestBody Store store, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        store.setManagerId(id);
        boolean suc = storeService.insertStore(store);
        if (suc) {
            userService.insertUserRole(id, 6);
            ShiroConfig.reloadAuthorizing(permissionRealm, session.getAttribute("username").toString());
            return Result.success(store);
        }
        return Result.error(ErrorMessage.insertError);
    }

    /**
     * 删除一个店铺
     * @param storeId 店铺ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/store/{storeId}")
    @RequiresPermissions(value = {"store:*", "store:delete"}, logical = Logical.OR)
    public Result deleteStore(@PathVariable("storeId") int storeId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int managerId = storeService.selectStoreManagerId(storeId);
        if (id != managerId) {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isPermitted("store:*")) {
                return Result.error(ErrorMessage.authError);
            }
        }

        boolean suc = storeService.deleteStore(storeId);
        if (suc) {
            // bookService.deleteStoreBook(storeId);
            return Result.success();
        }
        return Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新店铺信息
     * @param store 店铺的新信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/store")
    @RequiresPermissions(value = {"store:*", "store:update"}, logical = Logical.OR)
    public Result updateStore(@RequestBody Store store, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        if (id != store.getManagerId()) {
            return Result.error(ErrorMessage.authError);
        }

        boolean suc = storeService.updateStore(store);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询店铺信息
     * @param storeId 店铺ID
     * @return 查询结果
     */
    @GetMapping("/store/{storeId}")
    @RequiresAuthentication
    public Result selectStore(@PathVariable("storeId") int storeId) {
        Store store = storeService.selectStore(storeId);
        return Result.success(store);
    }

    /**
     * 查询我的店铺
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping(value = {"/store/manager", "/store/manager/{page}"})
    @RequiresPermissions(value = {"store:*", "store:select"}, logical = Logical.OR)
    public Result selectStoreByManager(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        PageHelper.startPage(page, storePageAmount);
        PageInfo<Store> list = new PageInfo<>(storeService.selectStoreByManager(id));
        return Result.success(list);
    }

    /**
     * 通过名字搜索店铺（支持模糊查找）
     * @param name 名字
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping({"/store/search/{name}/{page}", "/store/search/{name}"})
    @RequiresAuthentication
    public Result searchStoreByName(@PathVariable("name") String name, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, storePageAmount);
        PageInfo<Store> list = new PageInfo<>(storeService.searchStoreByName(name));
        return Result.success(list);
    }

    @GetMapping("/store/id")
    @RequiresAuthentication
    public Result selectStoreId(HttpSession session) {
        int id = (int) session.getAttribute("userId");
        Integer storeId = storeService.selectStoreId(id);
        return storeId == null ? Result.error(ErrorMessage.selectError) : Result.success(storeId);
    }
}
