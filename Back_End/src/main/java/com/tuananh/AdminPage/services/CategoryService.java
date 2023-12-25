package com.tuananh.AdminPage.services;


import com.tuananh.AdminPage.entities.TblCategoryEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface CategoryService {
    void save(TblCategoryEntity tblCategoryEntity);

    List<TblCategoryEntity> getAllCategory();

    void addCategory(TblCategoryEntity newCategory);

    void editCategory(TblCategoryEntity newInfo);

    void softDeleteCategory(int id);

    void restore(int id);

    List<TblCategoryEntity> searchByName(String name);

    List<TblProductEntity> getProductsByCategory(String categoryName, String q, int page, int limit, String sort, String order);

    Map<String, Long> getQuantity();

}
