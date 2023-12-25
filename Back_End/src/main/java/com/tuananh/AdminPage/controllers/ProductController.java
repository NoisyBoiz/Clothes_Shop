package com.tuananh.AdminPage.controllers;


import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblProductEntity;
import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.DetailProduct;
import com.tuananh.AdminPage.models.dto.TblProductDto;
import com.tuananh.AdminPage.models.dto.TblQuantityDto;
import com.tuananh.AdminPage.repositories.ColorRepository;
import com.tuananh.AdminPage.repositories.ProductRepository;
import com.tuananh.AdminPage.repositories.QuantityRepository;
import com.tuananh.AdminPage.repositories.SizeRepository;
import com.tuananh.AdminPage.services.ProductService;
import com.tuananh.AdminPage.services.QuantityService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final QuantityRepository quantityRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    private final ProductService productService;
    private final QuantityService quantityService;

    @GetMapping(path = "/detail/{productID}")
    public ResponseEntity<ApiResult<DetailProduct>> detailProduct(@PathVariable("productID") int productID) {
        ApiResult<DetailProduct> rs = ApiResult.create(HttpStatus.OK, "Lấy thành công thông tin chi tiết sản phẩm!", productService.detailProduct(productID));
        return ResponseEntity.ok(rs);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResult<TblProductDto>> addProduct(@RequestBody TblProductDto tblProductDto) {

        List<TblQuantityEntity> quantities = tblProductDto.getQuantities();

        TblProductEntity tblProductEntity = new TblProductEntity();
        BeanUtils.copyProperties(tblProductDto, tblProductEntity);

        if (quantities == null) {
            productService.addProduct(tblProductEntity);
        } else {
            // Kiểm tra xem tất cả các quantity có phù hợp không rồi mới add tất cả
            for (TblQuantityEntity quantity : quantities) {
                if (quantityRepository.existsBySku(quantity.getSku()))
                    throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_SKU, quantity.getSku()));
                if (quantityRepository.existsByProductIdAndSizeIdAndColorId(quantity.getProductId(), quantity.getSizeId(), quantity.getColorId()))
                    throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD, "productID & sizeID & colorID", Constants.TABLE_QUANTITY));
            }

            productService.addProduct(tblProductEntity);

            TblProductEntity currentProduct = productRepository.findFirstBySku(tblProductDto.getSku());
            // Lấy giá trị id trong db để so sánh lại vì lúc add không biết được id tự sinh
            int currentProductID = currentProduct.getId();

            for (TblQuantityEntity quantity : quantities) {
                TblQuantityDto tblQuantityDto = new TblQuantityDto();
                BeanUtils.copyProperties(quantity, tblQuantityDto);
                tblQuantityDto.setProductId(currentProductID);
                quantityService.addQuantity(tblQuantityDto);
            }
        }

        ApiResult<TblProductDto> rs = ApiResult.create(HttpStatus.OK, Constants.SAVE_DATA_SUCCESS, tblProductDto);
        return ResponseEntity.ok(rs);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<TblProductDto>> editInfoCategory(@Valid @RequestBody TblProductDto tblProductDto) {


        List<TblQuantityEntity> quantities = tblProductDto.getQuantities();

        TblProductEntity tblProductEntity = new TblProductEntity();
        BeanUtils.copyProperties(tblProductDto, tblProductEntity);

        if (quantities == null) {
            productService.editProduct(tblProductEntity);
            ApiResult<TblProductDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblProductDto);
            return ResponseEntity.ok(result);
        } else {
            // Kiểm tra xem tất cả các quantity có phù hợp không rồi mới update tất cả
            // Kiểm tra giống y hệt khi update tblQuantity
            for (TblQuantityEntity quantity : quantities) {
                if (!productRepository.existsById(quantity.getProductId()))
                    throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, quantity.getProductId()));
                TblQuantityEntity quantityFindByID = quantityRepository.findFirstById(quantity.getId());
                if (quantityRepository.findFirstById(quantity.getId()) == null)
                    throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, quantity.getId()));
                // kiểm tra xem giá trị sku có thay đổi không và nó đã tồn tại chưa
                if (!Objects.equals(quantityFindByID.getSku(), quantity.getSku()) && quantityRepository.existsBySku(quantity.getSku())) {
                    throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_SKU, quantity.getSku()));
                }
                // Kiểm tra xem sizeID có trong bảng tblSize không
                if (!sizeRepository.existsById(Integer.parseInt(quantity.getSizeId())))
                    throw new NotFoundException(MessageFormat.format(Constants.SEARCH_SIZE_ID_EMPTY, quantity.getSizeId()));
                // Kiểm tra xem colorID có trong bảng tblColor không
                if (!colorRepository.existsById(Integer.parseInt(quantity.getColorId())))
                    throw new NotFoundException(MessageFormat.format(Constants.SEARCH_COLOR_ID_EMPTY, quantity.getColorId()));
                // Kiểm tra xem bộ 3 giá trị productID + sizeID + colorID đã tồn tại chưa
                if (quantityRepository.existsByProductIdAndSizeIdAndColorId(quantity.getProductId(), quantity.getSizeId(), quantity.getColorId())
                    && (
                        !Objects.equals(quantityFindByID.getProductId(), quantity.getProductId())
                        || !Objects.equals(quantityFindByID.getSizeId(), quantity.getSizeId())
                        || !Objects.equals(quantityFindByID.getColorId(), quantity.getColorId())
                    )
                )
                    throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD, "productID & sizeID & colorID", Constants.TABLE_QUANTITY));
            }
            productService.editProduct(tblProductEntity);
            for (TblQuantityEntity quantity : quantities) {
                quantityService.editQuantity(quantity);
            }

            ApiResult<TblProductDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, tblProductDto);
            return ResponseEntity.ok(result);
        }


    }


    @DeleteMapping(path = "/delete/{productID}")
    public ResponseEntity<ApiResult<Integer>> deleteCategory(@PathVariable("productID") int productID) {
        productService.softDeleteProduct(productID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, productID);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/restore/{productID}/")
    // [PUT] localhost:8080/products/restore/1
    public ResponseEntity<ApiResult<Integer>> restore(@PathVariable("productID") int productID) {
        productService.restore(productID);
        ApiResult<Integer> rs = ApiResult.create(HttpStatus.OK, Constants.RESTORE_SUCCESS, productID);
        return ResponseEntity.ok(rs);
    }
    @GetMapping(path = "/searchById")
    // [GET] localhost:8080/product/searchById?id=
    public ResponseEntity<ApiResult<TblProductEntity>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblProductEntity tblProductEntity = productService.searchById(id);
        if (tblProductEntity == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY,"Tbl_product"));
        }
        ApiResult<TblProductEntity> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, "Tbl_product"), tblProductEntity);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchByCategory")
    public ResponseEntity<ApiResult<List<TblProductEntity>>> searchProductByCategory(@RequestParam(name = "id", required = false, defaultValue = "")  int categoryID) {
        ApiResult<List<TblProductEntity>> rs = ApiResult.create(HttpStatus.OK, "Lấy thành công thông tin chi tiết sản phẩm!", productService.searchByCategory(categoryID));
        return ResponseEntity.ok(rs);
    }

    @GetMapping(path = "/searchName")
    // [GET] localhost:8080/products/searchName?q=áo&_page=1&_limit=12&_order=asc&_sort=name
    public ResponseEntity<ApiResult<List<TblProductEntity>>> searchProductByName(
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

        List<TblProductEntity> data = productService.searchByName( q, pageInt, limitInt, sort, order);

        rs.setStatus(HttpStatus.OK);
        rs.setMessage(MessageFormat.format(Constants.GET_DATA_SUCCESS, "Tbl_product"));
        rs.setData(data);

        return ResponseEntity.ok(rs);
    }

}
