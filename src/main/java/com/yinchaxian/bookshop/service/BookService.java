package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Book;
import com.yinchaxian.bookshop.entity.BookDesc;
import com.yinchaxian.bookshop.entity.BookRate;
import com.yinchaxian.bookshop.mapper.BookDescMapper;
import com.yinchaxian.bookshop.mapper.BookMapper;
import com.yinchaxian.bookshop.mapper.BookRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookDescMapper bookDescMapper;
    @Autowired
    private BookRateMapper bookRateMapper;

    public boolean insertBook(Book book) {
        return bookMapper.insert(book) == 1;
    }

    public boolean deleteBook(long bookId) {
        return bookMapper.delete(bookId) == 1;
    }

    public boolean deleteStoreBook(int storeId) {
        return bookMapper.deleteStore(storeId) > 0;
    }

    public boolean updateBookInfo(Book book) {
        return bookMapper.updateInfo(book) == 1;
    }

    public boolean updateBookPrice(Book book) {
        return bookMapper.updatePrice(book) == 1;
    }

    public boolean updateBookPriceOnce(int storeId, float discount) {
        return bookMapper.updatePriceOnce(storeId, discount) > 0;
    }

    public boolean updateBookDeal(long bookId, int addition) {
        return bookMapper.updateDeal(bookId, addition) == 1;
    }

    public boolean updateBookLook(long bookId, int addition) {
        return bookMapper.updateLook(bookId, addition) == 1;
    }

    public Book selectBook(long bookId) {
        return bookMapper.select(bookId);
    }

    public List<Book> selectBookByCate(int cateId, int offset, int amount) {
        return bookMapper.selectByCate(cateId, offset, amount);
    }

    public List<Book> selectBookByStore(int storeId, int offset, int amount) {
        return bookMapper.selectByStore(storeId, offset, amount);
    }

    public List<Book> searchBookByName(String name, int offset, int amount) {
        return bookMapper.searchByName(name, offset, amount);
    }

    public boolean insertBookDesc(BookDesc bookDesc) {
        return bookDescMapper.insert(bookDesc) == 1;
    }

    public boolean deleteBookDesc(long bookId) {
        return bookDescMapper.delete(bookId) == 1;
    }

    public boolean updateBookDesc(BookDesc bookDesc) {
        return bookDescMapper.update(bookDesc) == 1;
    }

    public BookDesc selectBookDesc(long bookId) {
        return bookDescMapper.select(bookId);
    }

    public boolean insertBookRate(BookRate bookRate) {
        return bookRateMapper.insert(bookRate) == 1;
    }

    public boolean deleteBookRate(int userId, long bookId) {
        return bookRateMapper.delete(userId, bookId) == 1;
    }

    public boolean updateBookRate(BookRate bookRate) {
        return bookRateMapper.update(bookRate) == 1;
    }

    public BookRate selectBookRate(int userId, long bookId) {
        return bookRateMapper.select(userId, bookId);
    }

    public int selectBookRateScore(int userId, long bookId) {
        return bookRateMapper.selectScore(userId, bookId);
    }
}
