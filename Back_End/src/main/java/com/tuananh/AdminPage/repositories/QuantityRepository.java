package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblQuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityRepository extends JpaRepository<TblQuantityEntity, Integer> {

    Boolean existsByProductId(int productID);
    Boolean existsBySku (String sku);
    Boolean existsByColorId(String colorID);
    Boolean existsByProductIdAndSizeIdAndColorId(int productID, String sizeID, String colorID);
    TblQuantityEntity findFirstById(int id);

    TblQuantityEntity findFirstBySku(String sku);

    void deleteById(long id);

    List<TblQuantityEntity> findAllByProductId(int id);
}
