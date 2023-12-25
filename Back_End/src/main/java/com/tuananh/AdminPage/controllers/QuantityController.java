package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.TblQuantityDto;
import com.tuananh.AdminPage.services.QuantityService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/quantities")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class QuantityController {
    private final QuantityService quantityService;
    String tableName = Constants.TABLE_QUANTITY;

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResult<TblQuantityDto>> addQuantity(@Valid @RequestBody TblQuantityDto tblQuantityDto){
        quantityService.addQuantity(tblQuantityDto);
        ApiResult<TblQuantityDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblQuantityDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/list")
    // [GET] localhost:8080/quantities/list
    public ResponseEntity<ApiResult<List<TblQuantityEntity>>> showQuantityList() {
        List<TblQuantityEntity> TblQuantityEntityList = quantityService.getAllQuantity();
        ApiResult<List<TblQuantityEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblQuantityEntityList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchById")
    // [GET] localhost:8080/sizes/search?id=32
    public ResponseEntity<ApiResult<TblQuantityEntity>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblQuantityEntity tblQuantityEntity = quantityService.searchById(id);
        if (tblQuantityEntity == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<TblQuantityEntity> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), tblQuantityEntity);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchBySku")
    // [GET] localhost:8080/quantities/searchBySku?keyword=6TS21S012-SK010-L
    public ResponseEntity<ApiResult<TblQuantityEntity>> searchBySku(@RequestParam(name = "keyword", required = false, defaultValue = "") String label) {
        TblQuantityEntity TblQuantityEntityList = quantityService.searchBySku(label);
        if (TblQuantityEntityList == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<TblQuantityEntity> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblQuantityEntityList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchByProductId")
    // [GET] localhost:8080/quantities/searchByProductId?id=227962
    public ResponseEntity<ApiResult<List<TblQuantityEntity>>> searchByProductId(@RequestParam(name = "id", required = false, defaultValue = "") int productId) {
        List<TblQuantityEntity> TblQuantityEntityList = quantityService.searchByProductId(productId);
        if (TblQuantityEntityList.isEmpty()) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<List<TblQuantityEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblQuantityEntityList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchBySizeId")
    // [GET] localhost:8080/quantities/searchBySizeId?keyword=2258
    public ResponseEntity<ApiResult<List<TblQuantityEntity>>> searchBySizeId(@RequestParam(name = "keyword", required = false, defaultValue = "") String sizeId) {
        List<TblQuantityEntity> TblQuantityEntityList = quantityService.searchBySizeId(sizeId);
        if (TblQuantityEntityList.isEmpty()) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<List<TblQuantityEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblQuantityEntityList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchByColorId")
    // [GET] localhost:8080/quantities/searchByColorId?keyword=2547
    public ResponseEntity<ApiResult<List<TblQuantityEntity>>> searchByColorId(@RequestParam(name = "keyword", required = false, defaultValue = "") String colorId) {
        List<TblQuantityEntity> TblQuantityEntityList = quantityService.searchByColorId(colorId);
        if (TblQuantityEntityList.isEmpty()) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<List<TblQuantityEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblQuantityEntityList);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<TblQuantityEntity>> editInfoQuantity(@Valid @RequestBody TblQuantityEntity tblQuantityEntity) {
        quantityService.editQuantity(tblQuantityEntity);
        ApiResult<TblQuantityEntity> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblQuantityEntity);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{quantityID}")
    public ResponseEntity<ApiResult<Integer>> deleteQuantity(@PathVariable int quantityID) {
        quantityService.deleteById(quantityID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, quantityID);
        return ResponseEntity.ok(result);
    }
}