package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblSizeEntity;
import com.tuananh.AdminPage.models.dto.TblSizeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface SizeService {
    void save(TblSizeEntity tblSizeEntity);
    List<TblSizeEntity> getAllSize();
    void addSize(TblSizeDto newSize);
    void editSize(TblSizeEntity newInfo);

    List<TblSizeEntity> searchByLabel(String label);
    TblSizeEntity searchById(int id);

    void deleteById(int id);
}
