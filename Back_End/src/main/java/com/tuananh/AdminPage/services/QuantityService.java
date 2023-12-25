package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.models.dto.TblQuantityDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface QuantityService {
    void save(TblQuantityEntity tblQuantityEntity);
    List<TblQuantityEntity> getAllQuantity();

    void addQuantity(TblQuantityDto newQuantity);

    void editQuantity(TblQuantityEntity newInfo);

    TblQuantityEntity searchById(int id);
    TblQuantityEntity searchBySku(String sku);
    List<TblQuantityEntity> searchByProductId(int productId);
    List<TblQuantityEntity> searchBySizeId(String sizeId);
    List<TblQuantityEntity> searchByColorId(String colorId);

    void deleteById(int id);

}
