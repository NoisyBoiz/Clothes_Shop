package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.enums.VerificationEnum;
import com.tuananh.AdminPage.models.dto.*;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface AdminService {
    void save(TblAdminEntity tblAdminEntity);
    List<TblAdminOutDto> getAllAdmin();

    TblAdminEntity addAdmin(TblAdminDto newAdmin);

    void edit(UpdateCustomerDto newInfo);

    void softDeleteCustomer(int id);

    void restore(int id);

    TblAdminOutDto searchById (int id);
    List<TblAdminOutDto> searchByFullName(String fullName);
    boolean isOTPRequired(TblAdminEntity tblCustomerEntity) ;
    boolean isVerificationRequired(TblAdminEntity tblCustomerEntity);
    RegisterEnum register(TblAdminDto tblAdminDto) throws MessagingException;
    VerificationEnum verify(String code);
    void forgetPassword(TblAdminDto tblAdminDto);
    VerificationEnum checkOtp(ResetPasswordDTO tblAdminEntity, String inputOtp);
}
