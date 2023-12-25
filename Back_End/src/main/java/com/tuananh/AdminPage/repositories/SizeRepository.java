package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<TblSizeEntity, Integer> {

    Boolean existsById(int id);
    TblSizeEntity findFirstById(int id);

    List<TblSizeEntity> findByLabel(String label);

    void deleteById(int id);
}
