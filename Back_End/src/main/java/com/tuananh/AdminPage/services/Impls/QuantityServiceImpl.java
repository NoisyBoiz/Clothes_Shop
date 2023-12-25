package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.models.dto.TblQuantityDto;
import com.tuananh.AdminPage.repositories.ColorRepository;
import com.tuananh.AdminPage.repositories.ProductRepository;
import com.tuananh.AdminPage.repositories.QuantityRepository;
import com.tuananh.AdminPage.repositories.SizeRepository;
import com.tuananh.AdminPage.services.QuantityService;
import com.tuananh.AdminPage.shareds.Constants;
import com.tuananh.AdminPage.utils.VariableHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class QuantityServiceImpl implements QuantityService {
    private final QuantityRepository quantityRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    String tableName = Constants.TABLE_QUANTITY;

    @Override
    public void save(TblQuantityEntity tblQuantityEntity) {
        quantityRepository.save(tblQuantityEntity);
    }

    @Override
    public void addQuantity(TblQuantityDto newQuantity) {
        if (!productRepository.existsById(newQuantity.getProductId()))
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, newQuantity.getProductId()));
        if (quantityRepository.existsBySku(newQuantity.getSku()))
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_SKU, newQuantity.getSku()));
        if (quantityRepository.existsByProductIdAndSizeIdAndColorId(newQuantity.getProductId(), newQuantity.getSizeId(), newQuantity.getColorId()))
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD, "productID & sizeID & colorID", tableName));

        TblQuantityEntity tblQuantityEntity = TblQuantityEntity.create(
                0,
                newQuantity.getSku(),
                newQuantity.getProductId(),
                newQuantity.getSizeId(),
                newQuantity.getColorId(),
                newQuantity.getValue(),
                newQuantity.getMediaGallery(),
                false
        );
        quantityRepository.save(tblQuantityEntity);
    }

    @Override
    public List<TblQuantityEntity> getAllQuantity() {
        return quantityRepository.findAll();
    }

    @Override
    public void editQuantity(TblQuantityEntity newInfo) {

        if (!productRepository.existsById(newInfo.getProductId()))
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, newInfo.getProductId()));


        TblQuantityEntity quantity = quantityRepository.findFirstById(newInfo.getId());
        if (quantityRepository.findFirstById(newInfo.getId()) == null)
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_PRODUCT_ID_EMPTY, newInfo.getId()));
        // kiểm tra xem giá trị sku có thay đổi không và nó đã tồn tại chưa
        if (!Objects.equals(quantity.getSku(), newInfo.getSku()) && quantityRepository.existsBySku(newInfo.getSku()))
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_SKU, newInfo.getSku()));
        // Kiểm tra xem sizeID có trong bảng tblSize không
        if (!sizeRepository.existsById(Integer.parseInt(newInfo.getSizeId())))
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_SIZE_ID_EMPTY, quantity.getSizeId()));
        // Kiểm tra xem colorID có trong bảng tblColor không
        if (!colorRepository.existsById(Integer.parseInt(newInfo.getColorId())))
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_COLOR_ID_EMPTY, quantity.getColorId()));
        // Kiểm tra xem bộ 3 giá trị productID + sizeID + colorID đã tồn tại chưa
        if (quantityRepository.existsByProductIdAndSizeIdAndColorId(newInfo.getProductId(), newInfo.getSizeId(), newInfo.getColorId())
            && (
                !Objects.equals(quantity.getProductId(), newInfo.getProductId())
                || !Objects.equals(quantity.getSizeId(), newInfo.getSizeId())
                || !Objects.equals(quantity.getColorId(), newInfo.getColorId())
            )
        )
            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_FIELD, "productID & sizeID & colorID", tableName));


        quantity.setSku(VariableHandler.isNullOrEmpty(newInfo.getSku()) ? quantity.getSku() : newInfo.getSku());
        quantity.setColorId(VariableHandler.isNullOrEmpty(newInfo.getColorId()) ? quantity.getColorId() : newInfo.getColorId());
        quantity.setSizeId(VariableHandler.isNullOrEmpty(newInfo.getSizeId()) ? quantity.getSizeId() : newInfo.getSizeId());
        quantity.setValue(VariableHandler.isNullOrEmpty(newInfo.getValue()) ? quantity.getValue() : newInfo.getValue());
        quantity.setMediaGallery(VariableHandler.isNullOrEmpty(newInfo.getMediaGallery()) ? quantity.getMediaGallery() : newInfo.getMediaGallery());

        quantityRepository.save(quantity);
    }

    @Override
    public TblQuantityEntity searchById(int id){
        return quantityRepository.findFirstById(id);
    }

    @Override
    public TblQuantityEntity searchBySku(String sku) {
        List<TblQuantityEntity> quantities = quantityRepository.findAll();
        for (TblQuantityEntity quantity : quantities) {
            if (quantity.getSku().toLowerCase().contains(sku.toLowerCase())) {
                return quantity;
            }
        }
        return null;
    }

    public List<TblQuantityEntity> searchByProductId(int productId) {
        List<TblQuantityEntity> result = new ArrayList<>();
        List<TblQuantityEntity> quantities = quantityRepository.findAll();
        for (TblQuantityEntity quantity : quantities) {
            if (quantity.getProductId() == productId) {
                result.add(quantity);
            }
        }
        return result;
    }

    public List<TblQuantityEntity> searchByColorId(String colorId) {
        List<TblQuantityEntity> result = new ArrayList<>();
        List<TblQuantityEntity> quantities = quantityRepository.findAll();
        for (TblQuantityEntity quantity : quantities) {
            if (quantity.getColorId().equals(colorId.toLowerCase())) {
                result.add(quantity);
            }
        }
        return result;
    }


    public List<TblQuantityEntity> searchBySizeId(String sizeId) {
        List<TblQuantityEntity> result = new ArrayList<>();
        List<TblQuantityEntity> quantities = quantityRepository.findAll();
        for (TblQuantityEntity quantity : quantities) {
            if (quantity.getSizeId().equals(sizeId.toLowerCase())) {
                result.add(quantity);
            }
        }
        return result;
    }

    @Override
    public void deleteById(int id) {
        quantityRepository.deleteById(id);
    }
}
