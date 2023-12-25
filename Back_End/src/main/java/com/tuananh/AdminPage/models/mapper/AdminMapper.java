package com.tuananh.AdminPage.models.mapper;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.models.dto.TblAdminOutDto;
import org.springframework.beans.BeanUtils;

public class AdminMapper {
    public static TblAdminOutDto toAdminDto(TblAdminEntity tblAdminEntity){
        TblAdminOutDto adminDto = new TblAdminOutDto();
        BeanUtils.copyProperties(tblAdminEntity, adminDto);

        return adminDto;
    }
}