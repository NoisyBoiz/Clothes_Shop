package com.tuananh.AdminPage.services;


import com.tuananh.AdminPage.entities.TblProductEntity;
import com.tuananh.AdminPage.models.dto.DetailProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductService {
    DetailProduct detailProduct(int id);

    void save(TblProductEntity tblProductEntity);

    List<TblProductEntity> getAllProduct();

    void addProduct(TblProductEntity newProduct);

    void editProduct(TblProductEntity newInfo);

    void softDeleteProduct(int id);

    void restore(int id);
    TblProductEntity searchById(int id);
    List<TblProductEntity> searchByCategory(int id);
    List<TblProductEntity> searchByName(String q, int page, int limit, String sort, String order);
}
