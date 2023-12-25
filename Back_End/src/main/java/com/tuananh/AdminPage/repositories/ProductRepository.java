package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblCategoryEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ProductRepository extends JpaRepository<TblProductEntity, Integer> {

    Boolean existsByNameContaining(String name);
    Boolean existsBySku(String sku);
    Boolean existsByCategoryId(int categoryID);

    TblProductEntity findFirstById(int id);

    TblProductEntity findFirstBySku(String sku);
    List<TblProductEntity> findByCategoryId(int categoryID);
    List<TblProductEntity> findByNameContaining(String name);
    Page<TblProductEntity> findByNameContaining(String name, Pageable pageable);
    List<TblProductEntity> findByCategoryIdAndNameContaining(int categoryID, String description);

    Page<TblProductEntity> findByCategoryId(int categoryID, Pageable pageable);
    Page<TblProductEntity> findByCategoryIdAndNameContaining(int categoryID, String name, Pageable pageable);


}
