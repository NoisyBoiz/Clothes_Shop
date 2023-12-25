package com.tuananh.AdminPage.models.mapper;

import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.models.dto.TblCustomerOutDto;
import org.springframework.beans.BeanUtils;

public class CustomerMapper {
    public static TblCustomerOutDto toCustomerDto(TblCustomerEntity tblCustomerEntity){
        TblCustomerOutDto customerDto = new TblCustomerOutDto();
        BeanUtils.copyProperties(tblCustomerEntity, customerDto);

        return customerDto;
    }
}
