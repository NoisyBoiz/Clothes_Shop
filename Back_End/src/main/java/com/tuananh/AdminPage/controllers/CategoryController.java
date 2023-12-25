package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.entities.TblCategoryEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.services.CategoryService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    String tableCategory = Constants.TABLE_ADMIN;


    @GetMapping(path = "/list")
    // [GET] localhost:8080/categories/list
    public ResponseEntity<ApiResult<List<TblCategoryEntity>>> showCategoryList() {
        List<TblCategoryEntity> categoryEntityList = categoryService.getAllCategory();
        String message = MessageFormat.format(Constants.GET_DATA_SUCCESS, tableCategory);
        if (categoryEntityList.isEmpty()) {
            message = MessageFormat.format(Constants.GET_DATA_EMPTY, tableCategory);
        }
        ApiResult<List<TblCategoryEntity>> result = ApiResult.create(HttpStatus.OK, message, categoryEntityList);
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/add")
    // [POST] http://localhost:8080/categories/add
    public ResponseEntity<ApiResult<TblCategoryEntity>> addCategory(@Valid @RequestBody TblCategoryEntity category) {
        categoryService.addCategory(category);
        ApiResult<TblCategoryEntity> rs = ApiResult.create(HttpStatus.OK, "Thêm thành công category rồi nha", category);
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<TblCategoryEntity>> editInfoCategory(@Valid @RequestBody TblCategoryEntity category) {
        categoryService.editCategory(category);
        ApiResult<TblCategoryEntity> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, category);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{categoryID}")
    public ResponseEntity<ApiResult<Integer>> deleteCategory(@PathVariable("categoryID") int categoryID) {
        categoryService.softDeleteCategory(categoryID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, categoryID);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/restore/{categoryID}/")
    // [PUT] localhost:8080/categories/restore/1
    public ResponseEntity<ApiResult<Integer>> restore(@PathVariable("categoryID") int categoryID) {
        categoryService.restore(categoryID);
        ApiResult<Integer> rs = ApiResult.create(HttpStatus.OK, Constants.RESTORE_SUCCESS, categoryID);
        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<ApiResult<List<TblCategoryEntity>>> searchByName(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        List<TblCategoryEntity> categoryEntityList = categoryService.searchByName(name);
        ApiResult<List<TblCategoryEntity>> rs = ApiResult.create(HttpStatus.OK, Constants.SEARCH_DATA_SUCCESS, categoryEntityList);
        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "{categoryName}")
    // [GET] localhost:8080/categories/Áo-Nữ?q=áo&_page=1&_limit=12&_order=asc&_sort=name
    public ResponseEntity<ApiResult<List<TblProductEntity>>> getProductsByCategoryName(
            @PathVariable("categoryName") String categoryName,
            @RequestParam(name = "_page", required = false, defaultValue = "-1") String page,
            @RequestParam(name = "_limit", required = false, defaultValue = "-1") String limit,
            @RequestParam(name = "q", required = false, defaultValue = "") String q,
            @RequestParam(name = "_order", required = false, defaultValue = "") String order,
            @RequestParam(name = "_sort", required = false, defaultValue = "") String sort)
    {
        int pageInt;
        int limitInt;

        ApiResult<List<TblProductEntity>> rs = ApiResult.create(null, null, null);

        try {
            pageInt = Integer.parseInt(page);
            limitInt = Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            rs.setStatus(HttpStatus.BAD_REQUEST);
            rs.setMessage(MessageFormat.format(Constants.REQUIRE_TYPE, "số", "_page, _limit"));
            return ResponseEntity.badRequest().body(rs);
        }

        List<TblProductEntity> data = categoryService.getProductsByCategory(categoryName, q, pageInt, limitInt, sort, order);

        rs.setStatus(HttpStatus.OK);
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, "Tbl_Category"));
        rs.setData(data);

        return ResponseEntity.ok(rs);
    }


    @GetMapping(path = "getQuantity")
    // [GET] http://localhost:8080/categories/getQuantity
    public ResponseEntity<ApiResult<?>> getQuantity() {
        ApiResult<?> rs = ApiResult.create(HttpStatus.OK, "Lấy thành công", categoryService.getQuantity());
        return ResponseEntity.ok(rs);
    }
}
