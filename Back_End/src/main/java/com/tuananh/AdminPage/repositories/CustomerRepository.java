package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<TblCustomerEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    TblCustomerEntity findFirstById(int id);
    TblCustomerEntity findFirstByUsername(String username);
    TblCustomerEntity findFirstByEmail(String email);
    TblCustomerEntity findFirstByVerificationCode(String code);

    Optional<TblCustomerEntity> findByUsername(String username);
}
