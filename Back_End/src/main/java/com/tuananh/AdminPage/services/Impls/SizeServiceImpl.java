package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblSizeEntity;
import com.tuananh.AdminPage.models.dto.TblSizeDto;
import com.tuananh.AdminPage.services.SizeService;
import com.tuananh.AdminPage.repositories.SizeRepository;

import com.tuananh.AdminPage.utils.VariableHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    @Override
    public void save(TblSizeEntity tblSizeEntity) {
        sizeRepository.save(tblSizeEntity);
    }

    @Override
    public void addSize(TblSizeDto newSize) {
//        if(sizeRepository.findFirstById(newSize.getId()) != null)
//            throw new DuplicateRecordException(MessageFormat.format(Constants.DUPLICATE_ERROR_ID, newSize.getId()));
//        else {
            TblSizeEntity tblSizeEntity = TblSizeEntity.create(
                    newSize.getId(),
                    newSize.getLabel()
            );
            System.out.println(tblSizeEntity);
            sizeRepository.save(tblSizeEntity);
//        }
    }

    @Override
    public List<TblSizeEntity> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public TblSizeEntity searchById(int id){
        return sizeRepository.findFirstById(id);
    }

    @Override
    public void editSize(TblSizeEntity newInfo) {
        TblSizeEntity size = sizeRepository.findFirstById(newInfo.getId());
        if (size == null)
            throw new NotFoundException("Not found");

        size.setLabel(VariableHandler.isNullOrEmpty(newInfo.getLabel()) ? size.getLabel() : newInfo.getLabel());

        sizeRepository.save(size);
    }

    @Override
    public List<TblSizeEntity> searchByLabel(String label) {
        List<TblSizeEntity> list = sizeRepository.findByLabel(label);
        if (list == null)
            throw new NotFoundException("Not found");
        return list;
    }

    @Override
    public void deleteById(int id){
        sizeRepository.deleteById(id);
    }
}
