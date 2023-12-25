package com.tuananh.AdminPage.controllers;


import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblSizeEntity;
import com.tuananh.AdminPage.models.dto.TblSizeDto;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.tuananh.AdminPage.services.SizeService;
import com.tuananh.AdminPage.models.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/sizes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;
    String tableName = Constants.TABLE_SIZE;

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResult<TblSizeDto>> addSize(@Valid @RequestBody TblSizeDto tblSizeDto){
        sizeService.addSize(tblSizeDto);
        ApiResult<TblSizeDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblSizeDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/list")
    // [GET] localhost:8080/sizes/list
    public ResponseEntity<ApiResult<List<TblSizeEntity>>> showSizeList() {
        List<TblSizeEntity> TblSizeEntityList = sizeService.getAllSize();
        ApiResult<List<TblSizeEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblSizeEntityList);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/search")
    // [GET] localhost:8080/sizes/search?keyword=M
    public ResponseEntity<ApiResult<List<TblSizeEntity>>> searchByLabel(@RequestParam(name = "keyword", required = false, defaultValue = "") String label) {
        List<TblSizeEntity> TblSizeEntityList = sizeService.searchByLabel(label);
        if (TblSizeEntityList.isEmpty()) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<List<TblSizeEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblSizeEntityList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchById")
    // [GET] localhost:8080/sizes/search?id=32
    public ResponseEntity<ApiResult<TblSizeEntity>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblSizeEntity tblSizeEntity = sizeService.searchById(id);
        if (tblSizeEntity == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<TblSizeEntity> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), tblSizeEntity);
        return ResponseEntity.ok(result);
    }
    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<TblSizeEntity>> editInfoSize(@Valid @RequestBody TblSizeEntity tblSizeEntity) {
        sizeService.editSize(tblSizeEntity);
        ApiResult<TblSizeEntity> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblSizeEntity);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{sizeID}")
    public ResponseEntity<ApiResult<Integer>> deleteSize(@PathVariable int sizeID) {
        sizeService.deleteById(sizeID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, sizeID);
        return ResponseEntity.ok(result);
    }
}