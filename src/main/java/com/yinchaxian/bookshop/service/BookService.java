package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Book;
import com.yinchaxian.bookshop.entity.BookRate;
import com.yinchaxian.bookshop.mapper.BookMapper;
import com.yinchaxian.bookshop.mapper.BookRateMapper;
import com.yinchaxian.bookshop.recommend.MyRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
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

    public void updateBookStoreMount(long bookId, int addition) {
        bookMapper.updateStoreMount(bookId, addition);
    }

    public boolean updateBookPrice(Book book) {
        return bookMapper.updatePrice(book) == 1;
    }

    public boolean updateBookPriceOnce(int storeId, float discount) {
        return bookMapper.updatePriceOnce(storeId, discount) > 0;
    }

    public void updateBookDeal(long bookId, int addition) {
        bookMapper.updateDeal(bookId, addition);
    }

    public void updateBookLook(long bookId, int addition) {
        bookMapper.updateLook(bookId, addition);
    }

    public void updateBookScore(long bookId, int score, int type) {
        if (type == 0) {
            bookMapper.updateScoreAdd(bookId, score);
        } else if (type == 2) {
            bookMapper.updateScoreDelete(bookId, score);
        } else {
            bookMapper.updateScoreUpdate(bookId, score);
        }
    }

    public Book selectBook(long bookId) {
        return bookMapper.select(bookId);
    }

    public List<Book> selectAllBook() {
        return bookMapper.selectAll();
    }

    public List<Book> selectAllBookByDealMount() {
        return bookMapper.selectAllByDealMount();
    }

    public List<Book> selectBookByCate(int cateId) {
        return bookMapper.selectByCate(cateId);
    }

    public List<Book> selectBookByStore(int storeId) {
        return bookMapper.selectByStore(storeId);
    }

    public List<Book> searchBookByName(String name) {
        return bookMapper.searchByName(name);
    }

    public List<Book> selectTop20Book() {
        return bookMapper.selectTop20();
    }

    public List<Book> selectCategoryTop20Book(int cateId) {
        return bookMapper.selectCategoryTop20(cateId);
    }

    public List<Book> selectRecommend20Book(int userId) {
        List<Long> list;
        try {
            list = MyRecommender.getRecommendForUser(userId);
        } catch (Exception e) {
            return null;
        }
        return !list.isEmpty() ? null : bookMapper.selectBookByList(list);
    }

    public List<Book> selectRelatedBook(int userId, long bookId) {
        List<Long> list;
        try {
            list = MyRecommender.getRecommendForItem(userId, bookId);
        } catch (Exception e) {
            return null;
        }
        return !list.isEmpty() ? null : bookMapper.selectBookByList(list);
    }

    public int selectBookStoreId(long bookId) {
        return bookMapper.selectStoreId(bookId);
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

    public int selectBookRateScore(int userId, long bookId) {
        return bookRateMapper.selectScore(userId, bookId);
    }
}
