package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.entities.TblCustomerEntity;
import org.springframework.stereotype.Service;



@Service
public interface SendMailService {
    void forgetPasswordCustomer(TblCustomerEntity customer);

    void registerCustomer(TblCustomerEntity customer);
    void forgetPasswordAdmin(TblAdminEntity admin);

    void registerAdmin(TblAdminEntity admin);
}
