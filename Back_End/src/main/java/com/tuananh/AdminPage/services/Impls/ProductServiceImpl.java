package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblColorEntity;
import com.tuananh.AdminPage.entities.TblProductEntity;
import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.entities.TblSizeEntity;
import com.tuananh.AdminPage.models.dto.DetailProduct;
import com.tuananh.AdminPage.repositories.ColorRepository;
import com.tuananh.AdminPage.repositories.ProductRepository;
import com.tuananh.AdminPage.repositories.QuantityRepository;
import com.tuananh.AdminPage.repositories.SizeRepository;
import com.tuananh.AdminPage.services.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    String tableName = Constants.TABLE_PRODUCT;


    @Override
    public DetailProduct detailProduct(int id) {
        TblProductEntity tblProduct = productRepository.findFirstById(id);

        List<TblSizeEntity> sizes = new ArrayList<>();
        String sizeIdString = tblProduct.getSizeId();
        sizeIdString = sizeIdString.substring(0, sizeIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> sizeIds = Arrays.asList(sizeIdString.split(","));

        int[] intSizeArray = new int[sizeIds.size()];

        for (int i = 0; i < sizeIds.size(); i++) {
            intSizeArray[i] = Integer.parseInt(sizeIds.get(i));
        }
        for (int size : intSizeArray) {
            sizes.add(sizeRepository.findFirstById(size));
        }

        List<TblColorEntity> colors = new ArrayList<>();

        String colorsIdString = tblProduct.getColorId();
        colorsIdString = colorsIdString.substring(0, colorsIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> colorIds = Arrays.asList(colorsIdString.split(","));

        int[] intColorArray = new int[colorIds.size()];

        for (int i = 0; i < colorIds.size(); i++) {
            intColorArray[i] = Integer.parseInt(colorIds.get(i));
        }
        for (int color : intColorArray) {
            colors.add(colorRepository.findFirstById(color));
        }
        List<TblQuantityEntity> quantities = quantityRepository.findAllByProductId(id);


        return DetailProduct.create(
                tblProduct.getId(),
                tblProduct.getCategoryId(),
                sizes,
                colors,
                quantities,
                tblProduct.getSku(),
                tblProduct.getName(),
                tblProduct.getDescription(),
                tblProduct.getRegularPrice(),
                tblProduct.getFinalPrice(),
                tblProduct.getImage(),
                tblProduct.getMaterials(),
                tblProduct.getInstruction()
        );
    }

    @Override
    public void save(TblProductEntity tblProductEntity) {
        productRepository.save(tblProductEntity);
    }

    @Override
    public List<TblProductEntity> getAllProduct() {
        return null;
    }

    @Override
    public void addProduct(TblProductEntity newProduct) {

        String sizeIdString = newProduct.getSizeId();
        sizeIdString = sizeIdString.substring(0, sizeIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> sizeIds = Arrays.asList(sizeIdString.split(","));
        int[] intSizeArray = new int[sizeIds.size()];
        for (int i = 0; i < sizeIds.size(); i++) {
            intSizeArray[i] = Integer.parseInt(sizeIds.get(i));
        }
        String colorIdString = newProduct.getColorId();
        colorIdString = colorIdString.substring(0, colorIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> colorIDs = Arrays.asList(colorIdString.split(","));
        int[] intColorArray = new int[colorIDs.size()];
        for (int i = 0; i < colorIDs.size(); i++) {
            intColorArray[i] = Integer.parseInt(colorIDs.get(i));
        }

        if (productRepository.existsBySku(newProduct.getSku()))
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD, "Sku", tableName));
        if (!productRepository.existsByCategoryId(newProduct.getCategoryId()))
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        for (int size : intSizeArray) {
            if (!sizeRepository.existsById(size))
                throw new NotFoundException(MessageFormat.format(Constants.SEARCH_SIZE_ID_EMPTY, size));
        }
        for (int color : intColorArray) {
            if (!colorRepository.existsById(color))
                throw new NotFoundException(MessageFormat.format(Constants.SEARCH_COLOR_ID_EMPTY, color));
        }

        Date currentDay = DateUtil.getCurrentDay();

        TblProductEntity product = TblProductEntity.create(
                0,
                newProduct.getCategoryId(),
                newProduct.getSizeId(),
                newProduct.getColorId(),
                newProduct.getSku(),
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getRegularPrice(),
                newProduct.getFinalPrice(),
                newProduct.getImage(),
                newProduct.getMaterials(),
                newProduct.getInstruction(),
                false,
                currentDay,
                null,
                null
        );
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void editProduct(TblProductEntity newInfo) {

        String sizeIdString = newInfo.getSizeId();
        sizeIdString = sizeIdString.substring(0, sizeIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> sizeIds = Arrays.asList(sizeIdString.split(","));
        int[] intSizeArray = new int[sizeIds.size()];
        for (int i = 0; i < sizeIds.size(); i++) {
            intSizeArray[i] = Integer.parseInt(sizeIds.get(i));
        }
        String colorIdString = newInfo.getColorId();
        colorIdString = colorIdString.substring(0, colorIdString.length() - 1); // Loại bỏ dấu phẩy cuối cùng
        List<String> colorIDs = Arrays.asList(colorIdString.split(","));
        int[] intColorArray = new int[colorIDs.size()];
        for (int i = 0; i < colorIDs.size(); i++) {
            intColorArray[i] = Integer.parseInt(colorIDs.get(i));
        }

        if (!productRepository.existsByCategoryId(newInfo.getCategoryId()))
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        for (int size : intSizeArray) {
            if (!sizeRepository.existsById(size))
                throw new NotFoundException(MessageFormat.format(Constants.SEARCH_SIZE_ID_EMPTY, size));
        }
        for (int color : intColorArray) {
            if (!colorRepository.existsById(color))
                throw new NotFoundException(MessageFormat.format(Constants.SEARCH_COLOR_ID_EMPTY, color));
        }

        TblProductEntity product = productRepository.findFirstById(newInfo.getId());
        if (product == null)
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, newInfo.getId()));
        // kiểm tra xem giá trị sku có thay đổi không
        if (!Objects.equals(product.getSku(), newInfo.getSku()) && productRepository.existsBySku(newInfo.getSku())) {
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_SKU, newInfo.getSku()));
        }

        product.setCategoryId(VariableHandler.isNullOrEmpty(newInfo.getCategoryId()) ? product.getCategoryId() : newInfo.getCategoryId());
        product.setSizeId(VariableHandler.isNullOrEmpty(newInfo.getSizeId()) ? product.getSizeId() : newInfo.getSizeId());
        product.setColorId(VariableHandler.isNullOrEmpty(newInfo.getColorId()) ? product.getColorId() : newInfo.getColorId());
        product.setSku(VariableHandler.isNullOrEmpty(newInfo.getSku()) ? product.getSku() : newInfo.getSku());
        product.setName(VariableHandler.isNullOrEmpty(newInfo.getName()) ? product.getName() : newInfo.getName());
        product.setDescription(VariableHandler.isNullOrEmpty(newInfo.getDescription()) ? product.getDescription() : newInfo.getDescription());
        product.setRegularPrice(VariableHandler.isNullOrEmpty(newInfo.getRegularPrice()) ? product.getRegularPrice() : newInfo.getRegularPrice());
        product.setFinalPrice(VariableHandler.isNullOrEmpty(newInfo.getFinalPrice()) ? product.getFinalPrice() : newInfo.getFinalPrice());
        product.setImage(VariableHandler.isNullOrEmpty(newInfo.getImage()) ? product.getImage() : newInfo.getImage());
        product.setMaterials(VariableHandler.isNullOrEmpty(newInfo.getMaterials()) ? product.getMaterials() : newInfo.getMaterials());
        product.setInstruction(VariableHandler.isNullOrEmpty(newInfo.getInstruction()) ? product.getInstruction() : newInfo.getInstruction());

        product.setUpdatedAt(DateUtil.getCurrentDay());

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void softDeleteProduct(int id) {
        TblProductEntity product = productRepository.findFirstById(id);
        if (product == null || product.getDeleted())
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, id));

        List<TblQuantityEntity> quantities = quantityRepository.findAllByProductId(product.getId());

        for (TblQuantityEntity quantity : quantities) {
            quantity.setDeleted(true);
            quantityRepository.save(quantity);
        }

        product.setDeleted(true);
        product.setDeletedAt(Constants.getCurrentDay());

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void restore(int id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(Constants.SEARCH_CATEGORY_EMPTY);
        }

        TblProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null) {
            List<TblQuantityEntity> quantities = quantityRepository.findAllByProductId(product.getId());
            for (TblQuantityEntity quantity : quantities) {
                quantity.setDeleted(false);
                quantityRepository.save(quantity);
            }

            product.setDeleted(false);
            product.setDeletedAt(null);
            productRepository.save(product);
        }
    }
    @Override
    public TblProductEntity searchById(int id){
        return productRepository.findFirstById(id);
    }
    @Override
    public List<TblProductEntity> searchByCategory(int categoryId) {
        List<TblProductEntity> rs = new ArrayList<>();
        List<TblProductEntity> allProductEntity = productRepository.findAll();
        for(TblProductEntity i:allProductEntity){
            if(i.getCategoryId()==categoryId){
                rs.add(i);
            }
        }
        return rs;
    }
    @Override
    public List<TblProductEntity> searchByName(String q, int page, int limit, String sort, String order) {
//        TblCategoryEntity category = productRepository.findFirstByName(productName);
        List<TblProductEntity> rs = null;

        if (page == -1 || limit == -1) {
            if (q.equals("")) {
                rs = productRepository.findAll();
            }
            else {
                if (!productRepository.existsByNameContaining(q)) {
//                    throw new NotFoundException(Constants.SEARCH_PRODUCT_EMPTY);
                    return new ArrayList<>();
                }
                rs = productRepository.findByNameContaining(q);
            }
        }
        else {
            // start page of JPA is 0
            int _page = page - 1;
            Pageable pageable;

            if (!sort.equals("") && order.equals("desc")) {
                pageable = PageRequest.of(_page, limit, Sort.by(sort).descending());
            }
            else if (!sort.equals("") && order.equals("asc")) {
                pageable = PageRequest.of(_page, limit, Sort.by(sort).ascending());
            }
            else {
                pageable = PageRequest.of(_page, limit);
            }

            if (q.equals("")) {
                rs = productRepository.findAll(pageable).getContent();
            }
            else {
                if (!productRepository.existsByNameContaining(q)) {
//                    throw new NotFoundException(Constants.SEARCH_PRODUCT_EMPTY);
                    return new ArrayList<>();
                }
                rs = productRepository.findByNameContaining(q, pageable).getContent();
            }
        }
        return rs;
    }
}
