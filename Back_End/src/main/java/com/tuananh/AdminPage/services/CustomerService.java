package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.enums.VerificationEnum;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.models.dto.ResetPasswordDTO;
import com.tuananh.AdminPage.models.dto.TblCustomerDto;
import com.tuananh.AdminPage.models.dto.TblCustomerOutDto;
import com.tuananh.AdminPage.models.dto.UpdateCustomerDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CustomerService {
    void save(TblCustomerEntity tblCustomerEntity);
    List<TblCustomerOutDto> getAllCustomer();
    TblCustomerEntity addCustomer(TblCustomerDto tblCustomerDto);
    void edit (UpdateCustomerDto updateCustomerDto);
    void softDeleteCustomer(int id);

    @Transactional
    void restore(int id);
    TblCustomerOutDto searchById(int id);
    List<TblCustomerOutDto> searchByFullName(String fullName);
    boolean isOTPRequired(TblCustomerEntity tblCustomerEntity) ;
    boolean isVerificationRequired(TblCustomerEntity tblCustomerEntity);
    RegisterEnum register(TblCustomerDto tblCustomerDto) throws MessagingException;
    VerificationEnum verify(String code);
    void forgetPassword(TblCustomerDto tblCustomerDto);
    VerificationEnum checkOtp(ResetPasswordDTO tblCustomerDto, String inputOtp);
}
