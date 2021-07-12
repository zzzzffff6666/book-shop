package com.yinchaxian.bookshop.controller;

import com.yinchaxian.bookshop.entity.Book;
import com.yinchaxian.bookshop.entity.BookDesc;
import com.yinchaxian.bookshop.entity.BookRate;
import com.yinchaxian.bookshop.entity.Category;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.BookService;
import com.yinchaxian.bookshop.service.CategoryService;
import com.yinchaxian.bookshop.service.OrderService;
import com.yinchaxian.bookshop.service.StoreService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 类别和书籍相关的访问控制器
 */
@RestController
public class BookController {
    private static final int catePageAmount = 30;
    private static final int bookPageAmount = 50;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private OrderService orderService;

    //
    // Category部分
    //

    /**
     * 新建一个图书类别
     * @param category 类别信息
     * @return 是否成功
     */
    @PostMapping("/category")
    @RequiresPermissions(value = {"category:insert", "category:*"}, logical = Logical.OR)
    public Result insertCategory(@RequestBody Category category) {
        boolean suc = categoryService.insertCategory(category);
        return suc ? Result.success() : Result.error(ErrorMessage.insertError);
    }

    /**
     * 删除一个图书类别
     * @param cateId 类别ID
     * @return 是否成功
     */
    @DeleteMapping("/category/{cateId}")
    @RequiresPermissions(value = {"category:delete", "category:*"}, logical = Logical.OR)
    public Result deleteCategory(@PathVariable("cateId") int cateId) {
        boolean suc = categoryService.deleteCategory(cateId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新一个图书类别
     * @param cateId 类别ID
     * @param category 类别信息
     * @return 是否成功
     */
    @PutMapping("/category/{cateId}")
    @RequiresPermissions(value = {"category:update", "category:*"}, logical = Logical.OR)
    public Result updateCategory(@PathVariable("cateId") int cateId, @RequestBody Category category) {
        category.setCateId(cateId);
        boolean suc = categoryService.updateCategory(category);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询一个图书类别
     * @param cateId 类别ID
     * @return 查询结果
     */
    @GetMapping("/category/{cateId}")
    @RequiresAuthentication
    public Result selectCategory(@PathVariable("cateId") int cateId) {
        Category category = categoryService.selectCategory(cateId);
        return Result.success(category);
    }

    /**
     * 查询所有图书类别
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/category/list", "/category/list/{page}"})
    @RequiresAuthentication
    public Result selectAllCategory(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Category> list = categoryService.selectAllCategory((page - 1) * catePageAmount, catePageAmount);
        return Result.success(list);
    }

    /**
     * 查找相关图书类别（模糊查找）
     * @param name 类别名称
     * @param page 页数，默认为 1
     * @return 查找结果
     */
    @GetMapping(value = {"/category/search/{name}/list", "/category/search/{name}/list/{page}"})
    @RequiresAuthentication
    public Result searchCategoryByName(@PathVariable("name") String name,
                                       @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Category> list = categoryService.searchCategoryByName(name, (page - 1) * catePageAmount, catePageAmount);
        return Result.success(list);
    }

    //
    // Book部分
    //

    /**
     * 在自己的店铺里添加一本书
     * @param params 书的信息（还包括BookDesc里面的 desc 属性）
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/book")
    @RequiresPermissions(value = {"book:insert", "book:*"}, logical = Logical.OR)
    public Result insertBook(@RequestBody Map<String, String> params, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = Integer.parseInt(params.get("storeId"));
        int userId = storeService.selectStoreManagerId(storeId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        Book book = new Book();
        book.setCateId(Integer.parseInt(params.get("cateId")));
        book.setCname(params.get("cname"));
        book.setStoreId(Integer.parseInt(params.get("storeId")));
        book.setName(params.get("name"));
        book.setImageUrl(params.get("imageUrl"));
        book.setOutline(params.get("outline"));
        book.setAuthor(params.get("author"));
        book.setPress(params.get("press"));
        book.setVersion(params.get("version"));
        book.setPublishDate(params.get("publishDate"));
        book.setIsbn(params.get("isbn"));
        book.setPages(Integer.parseInt(params.get("pages")));
        book.setCatalog(params.get("catalog"));
        book.setPackStyle(params.get("packStyle"));
        book.setStoreMount(Integer.parseInt(params.get("storeMount")));
        book.setPrice(Float.parseFloat(params.get("price")));
        book.setMarketPrice(Float.parseFloat(params.get("marketPrice")));
        book.setMemberPrice(Float.parseFloat(params.get("memberPrice")));
        book.setDiscount(Float.parseFloat(params.get("discount")));
        book.setDealMount(0);
        book.setLookMount(0);
        boolean suc = bookService.insertBook(book);
        if (suc) {
            BookDesc bookDesc = new BookDesc();
            bookDesc.setBookId(book.getBookId());
            bookDesc.setDesc(params.get("desc"));
            Timestamp current = new Timestamp(System.currentTimeMillis());
            bookDesc.setCreated(current);
            bookDesc.setUpdated(current);
            return Result.success(bookService.insertBookDesc(bookDesc));
        }
        return Result.error(ErrorMessage.insertError);
    }

    /**
     * 删除一本书
     * @param bookId 图书ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/book/{bookId}")
    @RequiresPermissions(value = {"book:delete", "book:*"}, logical = Logical.OR)
    public Result deleteBook(@PathVariable("bookId") long bookId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = bookService.selectBookStoreId(bookId);
        int userId = storeService.selectStoreManagerId(storeId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }

        boolean suc = bookService.deleteBook(bookId);
        if (suc) {
            return Result.success(bookService.deleteBookDesc(bookId));
        }
        return Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新图书的信息
     * @param bookId 图书ID
     * @param book 图书信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/book/info/{bookId}")
    @RequiresPermissions(value = {"book:update", "book:*"}, logical = Logical.OR)
    public Result updateBookInfo(@PathVariable("bookId") long bookId, @RequestBody Book book, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = bookService.selectBookStoreId(bookId);
        int userId = storeService.selectStoreManagerId(storeId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        book.setStoreId(storeId);
        boolean suc = bookService.updateBookInfo(book);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新图书的价格
     * @param bookId 图书ID
     * @param book 图书价格信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/book/price/{bookId}")
    @RequiresPermissions(value = {"book:update", "book:*"}, logical = Logical.OR)
    public Result updateBookPrice(@PathVariable("bookId") long bookId, @RequestBody Book book, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = bookService.selectBookStoreId(bookId);
        int userId = storeService.selectStoreManagerId(storeId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        book.setStoreId(storeId);
        boolean suc = bookService.updateBookPrice(book);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询图书
     * @param bookId 图书ID
     * @return 查询结果
     */
    @GetMapping("/book/{bookId}")
    @RequiresAuthentication
    public Result selectBook(@PathVariable("bookId") long bookId) {
        Book book = bookService.selectBook(bookId);
        return Result.success(book);
    }

    /**
     * 查询所有图书
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/book/list", "/book/list/{page}"})
    @RequiresAuthentication
    public Result selectAllBook(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Book> list = bookService.selectAllBook((page - 1) * bookPageAmount, bookPageAmount);
        return Result.success(list);
    }

    /**
     * 按交易量排序查询所有图书
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/book/deal_mount/list", "/book/deal_mount/list/{page}"})
    @RequiresAuthentication
    public Result selectAllBookByDealMount(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Book> list = bookService.selectAllBookByDealMount((page - 1) * bookPageAmount, bookPageAmount);
        return Result.success(list);
    }

    /**
     * 按类别查询图书
     * @param cateId 类别ID
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/book/category/{cateId}/list", "/book/category/{cateId}/list/{page}"})
    @RequiresAuthentication
    public Result selectBookByCate(@PathVariable("cateId") int cateId,
                                   @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Book> list = bookService.selectBookByCate(cateId, (page - 1) * bookPageAmount, bookPageAmount);
        return Result.success(list);
    }

    /**
     * 查询店铺的图书
     * @param storeId 店铺ID
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/book/store/{storeId}/list", "/book/store/{storeId}/list/{page}"})
    @RequiresAuthentication
    public Result selectBookByStore(@PathVariable("storeId") int storeId,
                                    @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Book> list = bookService.selectBookByStore(storeId, (page - 1) * bookPageAmount, bookPageAmount);
        return Result.success(list);
    }

    /**
     * 按名字搜索图书（模糊查询）
     * @param name 图书名称
     * @param page 页数，默认为 1
     * @return 搜索结果
     */
    @GetMapping(value = {"/book/search/{name}/list", "/book/search/{name}/list/{page}"})
    @RequiresAuthentication
    public Result searchBookByName(@PathVariable("name") String name,
                                   @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<Book> list = bookService.searchBookByName(name, (page - 1) * bookPageAmount, bookPageAmount);
        return Result.success(list);
    }

    //
    // BookDetail部分
    //

    /**
     * 更新图书的详细信息
     * @param bookDesc 图书详细信息
     * @param bookId 图书ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/book/desc/{bookId}")
    @RequiresPermissions(value = {"book_desc:update", "book_desc:*"}, logical = Logical.OR)
    public Result updateBookDesc(@RequestBody BookDesc bookDesc,
                                 @PathVariable("bookId") long bookId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int storeId = bookService.selectBookStoreId(bookId);
        int userId = storeService.selectStoreManagerId(storeId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        bookDesc.setBookId(bookId);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        bookDesc.setCreated(current);
        bookDesc.setUpdated(current);
        boolean suc = bookService.updateBookDesc(bookDesc);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询图书详细信息
     * @param bookId 图书ID
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping("/book/desc/{bookId}")
    @RequiresAuthentication
    public Result selectBookDesc(@PathVariable("bookId") long bookId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        bookService.updateBookLook(bookId, 1);
        BookDesc bookDesc = bookService.selectBookDesc(bookId);
        return Result.success(bookDesc);
    }

    //
    // BookRate部分
    //

    /**
     * 给图书评分
     * 注意：没有购买则不能评分
     * @param bookRate 图书评分信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/book/rate")
    @RequiresPermissions(value = {"book_rate:insert", "book_rate:*"}, logical = Logical.OR)
    public Result insertBookRate(@RequestBody BookRate bookRate, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int times = orderService.selectOrderTimesByUserAndBook(id, bookRate.getBookId());
        if (times <= 0) {
            return Result.error(ErrorMessage.rateError);
        }
        bookRate.setUserId(id);
        boolean suc = bookService.insertBookRate(bookRate);
        return suc ? Result.success() : Result.error(ErrorMessage.insertError);
    }

    /**
     * 删除图书评分信息
     * @param bookId 图书ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/book/rate/{bookId}")
    @RequiresPermissions(value = {"book_rate:delete", "book_rate:*"}, logical = Logical.OR)
    public Result deleteBookRate(@PathVariable("bookId") long bookId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        boolean suc = bookService.deleteBookRate(id, bookId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新图书评分信息
     * @param bookRate 图书评分信息
     * @param bookId 图书ID
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/book/rate/{bookId}")
    @RequiresPermissions(value = {"book_rate:update", "book_rate:*"}, logical = Logical.OR)
    public Result updateBookRate(@RequestBody BookRate bookRate, @PathVariable("bookId") long bookId,
                                 HttpSession session) {
        int id = (int) session.getAttribute("userId");
        bookRate.setUserId(id);
        boolean suc = bookService.updateBookRate(bookRate);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询图书评分
     * @param bookId 图书ID
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping("/book/rate/{bookId}")
    @RequiresAuthentication
    public Result selectBookRate(@PathVariable("bookId") long bookId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        BookRate bookRate = bookService.selectBookRate(id, bookId);
        return Result.success(bookRate);
    }
}
