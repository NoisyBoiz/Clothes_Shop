package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblColorEntity;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.TblColorDto;
import com.tuananh.AdminPage.services.ColorService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/colors")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    String tableName = Constants.TABLE_COLOR;

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResult<TblColorDto>> addColor(@Valid @RequestBody TblColorDto tblColorDto){
        colorService.addColor(tblColorDto);
        ApiResult<TblColorDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblColorDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/list")
    // [GET] localhost:8080/colors/list
    public ResponseEntity<ApiResult<List<TblColorEntity>>> showColorList() {
        List<TblColorEntity> TblColorEntityList = colorService.getAllColor();
        ApiResult<List<TblColorEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblColorEntityList);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/search")
    // [GET] localhost:8080/colors/search?keyword=SK275
    public ResponseEntity<ApiResult<List<TblColorEntity>>> searchByLabel(@RequestParam(name = "keyword", required = false, defaultValue = "") String label) {
        List<TblColorEntity> TblColorEntityList = colorService.searchByLabel(label);
        if (TblColorEntityList.isEmpty()) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<List<TblColorEntity>> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), TblColorEntityList);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/searchById")
    // [GET] localhost:8080/sizes/search?id=32
    public ResponseEntity<ApiResult<TblColorEntity>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblColorEntity tblColorEntity = colorService.searchById(id);
        if (tblColorEntity == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableName));
        }
        ApiResult<TblColorEntity> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableName), tblColorEntity);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<TblColorEntity>> editInfoColor(@Valid @RequestBody TblColorEntity tblColorEntity) {
        colorService.editColor(tblColorEntity);
        ApiResult<TblColorEntity> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblColorEntity);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{colorID}")
    // [DELETE] localhost:8080/colors/delete/3139
    public ResponseEntity<ApiResult<Integer>> deleteColor(@PathVariable int colorID) {
        colorService.deleteById(colorID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, colorID);
        return ResponseEntity.ok(result);
    }
}