package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.models.dto.DataMailDto;
import com.tuananh.AdminPage.services.MailService;
import com.tuananh.AdminPage.services.SendMailService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {
    private MailService mailService;

    private void putPropsDataMail(DataMailDto dataMail, String URL, String fullName, String username) throws MessagingException {
        Map<String, Object> props = new HashMap<>();
        props.put("name", fullName);
        props.put("username", username);
        props.put("URL", URL);
        dataMail.setProps(props);

        mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.VERIFY_CUSTOMER);
    }

    @Override
    @Async
    public void forgetPasswordCustomer(TblCustomerEntity customer) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(customer.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CUSTOMER_FORGET_PASSWORD);

            Map<String, Object> props = new HashMap<>();
            props.put("name", customer.getFullName());
            props.put("username", customer.getUsername());
            props.put("OTP", customer.getOtp());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.CUSTOMER_FORGET_PASSWORD);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void registerCustomer(TblCustomerEntity customer) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(customer.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CUSTOMER_REGISTER);

            String URL = Constants.URL_VERIFICATION_CUSTOMER + customer.getVerificationCode();

            putPropsDataMail(dataMail, URL, customer.getFullName(), customer.getUsername());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    @Async
    public void forgetPasswordAdmin(TblAdminEntity admin) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(admin.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CUSTOMER_FORGET_PASSWORD);

            Map<String, Object> props = new HashMap<>();
            props.put("name", admin.getFullName());
            props.put("username", admin.getUsername());
            props.put("OTP", admin.getOtp());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Constants.TEMPLATE_FILE_NAME.CUSTOMER_FORGET_PASSWORD);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void registerAdmin(TblAdminEntity admin) {
        try {
            DataMailDto dataMail = new DataMailDto();

            dataMail.setTo(admin.getEmail());
            dataMail.setSubject(Constants.SEND_MAIL_SUBJECT.CUSTOMER_REGISTER);

            String URL = Constants.URL_VERIFICATION_ADMIN + admin.getVerificationCode();

            putPropsDataMail(dataMail, URL, admin.getFullName(), admin.getUsername());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
