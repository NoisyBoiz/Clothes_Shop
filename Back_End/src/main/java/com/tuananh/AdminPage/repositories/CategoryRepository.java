package com.tuananh.AdminPage.repositories;

import com.tuananh.AdminPage.entities.TblCategoryEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<TblCategoryEntity, Integer> {
    boolean existsByName(String name);

    TblCategoryEntity findFirstById(int id);
    TblCategoryEntity findFirstByName(String name);

    @Query(value = "SELECT c.name, count(p.id) from TblProductEntity p join TblCategoryEntity c on p.categoryId = c.id GROUP BY c.name order by c.name ")
    List<Object[]> countQuantityProductEachCategory();
}
