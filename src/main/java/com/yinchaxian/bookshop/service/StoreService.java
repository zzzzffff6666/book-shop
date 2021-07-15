package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Store;
import com.yinchaxian.bookshop.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;

    public boolean insertStore(Store store) {
        return storeMapper.insert(store) == 1;
    }

    public boolean deleteStore(int storeId) {
        return storeMapper.delete(storeId) == 1;
    }

    public boolean updateStore(Store store) {
        return storeMapper.update(store) == 1;
    }

    public Store selectStore(int storeId) {
        return storeMapper.select(storeId);
    }

    public Store selectStoreByManager(int userId) {
        return storeMapper.selectByManager(userId);
    }

    public List<Store> searchStoreByName(String name) {
        return storeMapper.searchByName(name);
    }

    public int selectStoreManagerId(int storeId) {
        return storeMapper.selectManagerId(storeId);
    }

    public Integer selectStoreId(int managerId) {
        return storeMapper.selectStoreId(managerId);
    }
}
