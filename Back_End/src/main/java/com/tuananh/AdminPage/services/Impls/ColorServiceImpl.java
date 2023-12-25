package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.models.dto.TblColorDto;
import com.tuananh.AdminPage.services.ColorService;
import com.tuananh.AdminPage.entities.TblColorEntity;
import com.tuananh.AdminPage.repositories.ColorRepository;
import com.tuananh.AdminPage.utils.VariableHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public void save(TblColorEntity tblColorEntity) {
        colorRepository.save(tblColorEntity);
    }

    @Override
    public List<TblColorEntity> getAllColor() {
        return colorRepository.findAll();
    }

    @Override
    public TblColorEntity searchById(int id){
        return colorRepository.findFirstById(id);
    }
    @Override
    public void addColor(TblColorDto newColor) {
//        if(colorRepository.findFirstById(newColor.getId()) != null)
//            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_ID, newColor.getId()));
//        else {
            TblColorEntity tblColorEntity = TblColorEntity.create(
                    newColor.getId(),
                    newColor.getLabel(),
                    newColor.getImage()
            );
            colorRepository.save(tblColorEntity);
//        }
    }

    @Override
    public void editColor(TblColorEntity newInfo) {
        TblColorEntity color = colorRepository.findFirstById(newInfo.getId());
        if (color == null) 
            throw new NotFoundException("color not found");

        color.setImage(VariableHandler.isNullOrEmpty(newInfo.getImage()) ? color.getImage() : newInfo.getImage());
        color.setLabel(VariableHandler.isNullOrEmpty(newInfo.getLabel()) ? color.getLabel() : newInfo.getLabel());

        colorRepository.save(color);
    }

    @Override
    public List<TblColorEntity> searchByLabel(String label) {
        List<TblColorEntity> list = colorRepository.findByLabel(label);
        if (list == null)
            throw new NotFoundException("Not found");
        return list;
    }
    @Override
    public void deleteById(int id){
        colorRepository.deleteById(id);
    }
}
