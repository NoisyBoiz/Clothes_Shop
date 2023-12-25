package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblColorEntity;
import com.tuananh.AdminPage.models.dto.TblColorDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ColorService {
    void save(TblColorEntity tblColorEntity);
    List<TblColorEntity> getAllColor();

    TblColorEntity searchById(int id);

    void addColor(TblColorDto newColor);
    void editColor(TblColorEntity newInfo);

    List<TblColorEntity> searchByLabel(String label);

    void deleteById(int id);
}
