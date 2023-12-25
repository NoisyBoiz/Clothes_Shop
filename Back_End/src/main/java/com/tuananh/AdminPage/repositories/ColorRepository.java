package com.tuananh.AdminPage.repositories;


import com.tuananh.AdminPage.entities.TblColorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<TblColorEntity, Integer> {
    Boolean existsById(int id);
    TblColorEntity findFirstById(int id);

    List<TblColorEntity> findByLabel(String label);

    void deleteById(int id);
}
