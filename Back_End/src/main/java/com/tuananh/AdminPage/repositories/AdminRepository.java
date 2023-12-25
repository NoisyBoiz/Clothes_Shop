package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.entities.TblAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<TblAdminEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    TblAdminEntity findFirstById(int id);
    TblAdminEntity findFirstByUsername(String username);
    TblAdminEntity findFirstByEmail(String email);
    TblAdminEntity findFirstByVerificationCode(String code);

    Optional<TblAdminEntity> findByUsername(String username);
}
