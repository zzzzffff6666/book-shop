package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Category;
import com.yinchaxian.bookshop.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public boolean insertCategory(Category category) {
        return categoryMapper.insert(category) == 1;
    }

    public boolean deleteCategory(int cateId) {
        return categoryMapper.delete(cateId) == 1;
    }

    public boolean updateCategory(Category category) {
        return categoryMapper.update(category) == 1;
    }

    public Category selectCategory(int cateId) {
        return categoryMapper.select(cateId);
    }

    public List<Category> selectAllCategory(int offset, int amount) {
        return categoryMapper.selectAll(offset, amount);
    }

    public List<Category> searchCategoryByName(String name, int offset, int amount) {
        return categoryMapper.searchByName(name, offset, amount);
    }
}
