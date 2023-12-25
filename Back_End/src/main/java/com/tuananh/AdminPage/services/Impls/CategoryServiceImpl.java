package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblCategoryEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import com.tuananh.AdminPage.repositories.CategoryRepository;
import com.tuananh.AdminPage.repositories.ProductRepository;
import com.tuananh.AdminPage.services.CategoryService;
import com.tuananh.AdminPage.shareds.Constants;
import com.tuananh.AdminPage.utils.DateUtil;
import com.tuananh.AdminPage.utils.VariableHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    String tableName = Constants.TABLE_CATEGORY;

    @Override
    public void save(TblCategoryEntity tblCategoryEntity) {
        categoryRepository.save(tblCategoryEntity);
    }

    @Override
    public List<TblCategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(TblCategoryEntity newCategory) {

        String categoryName = newCategory.getName();
        Date currentDay = DateUtil.getCurrentDay();

        if (categoryRepository.existsByName(categoryName)){
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD,"Name", tableName));
        }

        TblCategoryEntity category = TblCategoryEntity.create(
            0,
                categoryName,
                newCategory.getDescription(),
                currentDay,
                null,
                null,
                false
        );
        categoryRepository.save(category);
    }
    @Transactional
    @Override
    public void editCategory(TblCategoryEntity newInfo) {
        TblCategoryEntity category = categoryRepository.findFirstById(newInfo.getId());
        if (category == null)
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        if (categoryRepository.existsByName(newInfo.getName()))
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD,"Name", tableName));

        category.setName(VariableHandler.isNullOrEmpty(newInfo.getName()) ? category.getName() : newInfo.getName());
        category.setDescription(VariableHandler.isNullOrEmpty(newInfo.getDescription()) ? category.getDescription() : newInfo.getDescription());

        category.setUpdatedAt(DateUtil.getCurrentDay());

        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void softDeleteCategory(int id) {
        TblCategoryEntity category = categoryRepository.findFirstById(id);
        if (category == null || category.getDeleted())
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);

        category.setDeleted(true);
        category.setDeletedAt(Constants.getCurrentDay());

        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void restore(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        }

        TblCategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setDeleted(false);
            category.setDeletedAt(null);
            categoryRepository.save(category);
        }
    }

    @Override
    public List<TblCategoryEntity> searchByName(String name) {
        List<TblCategoryEntity> result = new ArrayList<>();
        List<TblCategoryEntity> categories = categoryRepository.findAll();
        for (TblCategoryEntity category : categories) {
            if (category.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(category);
            }
        }
        return result;
    }

    @Override
    public List<TblProductEntity> getProductsByCategory(String categoryName, String q, int page, int limit, String sort, String order) {
        if (!categoryRepository.existsByName(categoryName)) {
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        }

        TblCategoryEntity category = categoryRepository.findFirstByName(categoryName);
        List<TblProductEntity> rs = null;

        if (page == -1 || limit == -1) {
            if (q.equals("")) {
                rs = productRepository.findByCategoryId(category.getId());
            } else {
                rs = productRepository.findByCategoryIdAndNameContaining(category.getId(), q);
            }
        }
        else {
            // start page of JPA is 0
            int _page = page - 1;
            Pageable pageable;

            if (!sort.equals("") && order.equals("desc")) {
                    pageable = PageRequest.of(_page, limit, Sort.by(sort).descending());
                } else if (!sort.equals("") && order.equals("asc")) {
                    pageable = PageRequest.of(_page, limit, Sort.by(sort).ascending());
                } else {
                pageable = PageRequest.of(_page, limit);
            }

            if (q.equals("")) {
                rs = productRepository.findByCategoryId(category.getId(), pageable).getContent();
            } else {
                rs = productRepository.findByCategoryIdAndNameContaining(category.getId(), q, pageable).getContent();
            }
        }
        return rs;
    }


    @Override
    public Map<String, Long> getQuantity() {
        List<Object[]> quantities = categoryRepository.countQuantityProductEachCategory();
        Map<String, Long> result = new HashMap<>();

        for (Object[] quantity : quantities) {
            String categoryName = (String) quantity[0];
            Long count = (Long) quantity[1];
            result.put(categoryName, count);
        }

        return result;
    }
}
