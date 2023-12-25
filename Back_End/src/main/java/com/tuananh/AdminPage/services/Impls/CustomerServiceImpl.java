package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.enums.VerificationEnum;
import com.tuananh.AdminPage.models.dto.*;
import com.tuananh.AdminPage.models.mapper.CustomerMapper;
import com.tuananh.AdminPage.repositories.AdminRepository;
import com.tuananh.AdminPage.repositories.CustomerRepository;
import com.tuananh.AdminPage.services.CustomerService;
import com.tuananh.AdminPage.services.SendMailService;
import com.tuananh.AdminPage.shareds.Constants;
import com.tuananh.AdminPage.utils.DateUtil;
import com.tuananh.AdminPage.utils.GenerateOtpUtil;
import com.tuananh.AdminPage.utils.VariableHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final SendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(TblCustomerEntity tblCustomerEntity) {
        customerRepository.save(tblCustomerEntity);
    }

    @Override
    public List<TblCustomerOutDto> getAllCustomer() {
        List<TblCustomerOutDto> result = new ArrayList<>();
        List<TblCustomerEntity> customers = customerRepository.findAll();
        for (TblCustomerEntity customer : customers) {
            result.add(CustomerMapper.toCustomerDto(customer));
        }
        return result;
    }

    @Override
    public TblCustomerEntity addCustomer(TblCustomerDto newCustomer) {
        Date currentDay = DateUtil.getCurrentDay();
        String randomCode = RandomStringUtils.randomAlphanumeric(64);

        return TblCustomerEntity.create(
                0,
                newCustomer.getUsername(),
                passwordEncoder.encode(newCustomer.getPassword()),
                newCustomer.getPhone(),
                newCustomer.getEmail(),
                newCustomer.getFullName(),
                "ROLE_USER",
                newCustomer.getGender(),
                newCustomer.getBirthday(),
                newCustomer.getAvatar(),
                currentDay,
                null,
                null,
                false,
                null,
                0,
                null,
                false,
                randomCode,
                currentDay

        );
    }

    @Override
    public void edit(UpdateCustomerDto newInfo) {
        TblCustomerEntity customer = customerRepository.findFirstById(newInfo.getId());
        if (customer == null) {
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
        }
        if (customerRepository.existsByEmail(newInfo.getEmail()) || adminRepository.existsByEmail(newInfo.getEmail())) {
            throw new DuplicateRecordException(Constants.DUPLICATE_ERROR_EMAIL);
        }

        customer.setPhone(VariableHandler.isNullOrEmpty(newInfo.getPhone()) ? customer.getPhone() : newInfo.getPhone());
        customer.setFullName(VariableHandler.isNullOrEmpty(newInfo.getFullName()) ? customer.getFullName() : newInfo.getFullName());
        customer.setGender(VariableHandler.isNullOrEmpty(newInfo.getGender()) ? customer.getGender() : newInfo.getGender());
        customer.setAvatar(VariableHandler.isNullOrEmpty(newInfo.getAvatar()) ? customer.getAvatar() : newInfo.getAvatar());
        customer.setBirthday(VariableHandler.isNullOrEmpty(newInfo.getBirthday()) ? customer.getBirthday() : newInfo.getBirthday());
        if (!VariableHandler.isNullOrEmpty(newInfo.getEmail())) {
            if (adminRepository.existsByEmail(newInfo.getEmail())) {
                throw new DuplicateRecordException(Constants.DUPLICATE_ERROR_EMAIL);
            } else {
                customer.setEmail(newInfo.getEmail());
            }
        }
        customer.setUpdatedAt(DateUtil.getCurrentDay());

        customerRepository.save(customer);
    }

    @Override
    public void softDeleteCustomer(int id) {
        TblCustomerEntity customer = customerRepository.findFirstById(id);
        if (customer == null || customer.getDeleted())
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);

        customer.setDeleted(true);
        customer.setDeletedAt(Constants.getCurrentDay());

        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void restore(int id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
        }

        TblCustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setDeleted(false);
            customer.setDeletedAt(null);
            customerRepository.save(customer);
        }
    }
    @Override
    public TblCustomerOutDto searchById(int id){
        TblCustomerEntity tblCustomerEntity = customerRepository.findFirstById(id);
        return CustomerMapper.toCustomerDto(tblCustomerEntity);
    }
    @Override
    public List<TblCustomerOutDto> searchByFullName(String fullName) {
        List<TblCustomerOutDto> result = new ArrayList<>();
        List<TblCustomerEntity> customers = customerRepository.findAll();
        for (TblCustomerEntity customer : customers) {
            if (customer.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                result.add(CustomerMapper.toCustomerDto(customer));
            }
        }
        return result;
    }

    @Override
    public boolean isOTPRequired(TblCustomerEntity tblCustomerEntity) {
        if (tblCustomerEntity.getOtp() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = tblCustomerEntity.getOtpExp().getTime();

        return otpRequestedTimeInMillis + Constants.OTP_VALID_DURATION <= currentTimeInMillis;
    }

    @Override
    public boolean isVerificationRequired(TblCustomerEntity tblCustomerEntity) {
        if (tblCustomerEntity.getVerificationCode() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = tblCustomerEntity.getVerificationCodeExp().getTime();

        return otpRequestedTimeInMillis + Constants.VERIFICATION_CODE_DURATION <= currentTimeInMillis;
    }

    @Override
    public RegisterEnum register(TblCustomerDto newCustomer) {
        /* Kiểm tra xem customername và email có tồn tại trong tbl_admin chưa */
        if (adminRepository.existsByUsername(newCustomer.getUsername()))
            return RegisterEnum.DUPLICATE_USERNAME;
        if (adminRepository.existsByEmail(newCustomer.getEmail()))
            return RegisterEnum.DUPLICATE_EMAIL;


        /* Kiểm tra xem customername có tồn tại trong tbl_customer chưa */
        /* 1. Username đã tồn tại */
        if (customerRepository.existsByUsername(newCustomer.getUsername())) {
            TblCustomerEntity customerByUsername = customerRepository.findFirstByUsername(newCustomer.getUsername());
            /* Kiểm tra xem customer sở hữu customername đó đã được kích hoạt chưa */
            /* 1.1 Username chưa được kích hoạt */
            if (!customerByUsername.getStatus()) {
                /* Kiểm tra xem email đã tồn tại trong db chưa */
                /* 1.1.1. Email đã tồn tại trong db */
                if (customerRepository.existsByEmail(newCustomer.getEmail())) {
                    TblCustomerEntity customerByEmail = customerRepository.findFirstByEmail(newCustomer.getEmail());
                    /*  Kiểm tra xem email đó đã được kích hoạt chưa */
                    /* 1.1.1.1. Email chưa được kích hoạt */
                    if (!customerByEmail.getStatus()) {
                        /* Tạo mới customer và xóa 2 đối tượng có customername và email trùng */
                        // Tạo customer +  gửi mail + save
                        TblCustomerEntity tblCustomerEntity = addCustomer(newCustomer);
                        sendMailService.registerCustomer(tblCustomerEntity);
                        customerRepository.save(tblCustomerEntity);
                        // xóa 2 đối tượng cũ
                        customerRepository.deleteById(customerByUsername.getId());
                        customerRepository.deleteById(customerByEmail.getId());

                        return RegisterEnum.SUCCESS;
                    }
                    /* 1.1.1.2. Email đã được kích hoạt */
                    else {
                        /* Thông báo cho người dùng không sử dụng email này */

                        return RegisterEnum.DUPLICATE_EMAIL;
                    }
                }
                /* 1.1.2. Email chưa tồn tại trong db */
                else {
                    /* Tạo customer và xóa customer có customername trùng */
                    // Tạo customer +  gửi mail + save
                    TblCustomerEntity tblCustomerEntity = addCustomer(newCustomer);
                    sendMailService.registerCustomer(tblCustomerEntity);
                    customerRepository.save(tblCustomerEntity);
                    // xóa 1 đối tượng cũ
                    customerRepository.deleteById(customerByUsername.getId());

                    return RegisterEnum.SUCCESS;
                }
            }
            /* 1.2 Username đã được kích hoạt*/
            else {
                /* Thông báo cho người dùng không sử dụng customername này */
                return RegisterEnum.DUPLICATE_USERNAME;
            }
        }
        /* 2. Username chưa tồn tại */
        else {
            /* Kiểm tra xem email đã có người dùng chưa */
            /* 2.1 Email đã tồn tại */
            if (customerRepository.existsByEmail(newCustomer.getEmail())) {
                TblCustomerEntity customerByEmail = customerRepository.findFirstByEmail(newCustomer.getEmail());
                /*Kiểm tra xem email đó đã được kích hoạt chưa */
                /* 2.1.1. Email chưa được kích hoạt */
                if (!customerByEmail.getStatus()) {
                    /* Tạo mới customer và xóa đối tượng có email trùng */
                    // Tạo customer +  gửi mail + save
                    TblCustomerEntity tblCustomerEntity = addCustomer(newCustomer);
                    sendMailService.registerCustomer(tblCustomerEntity);
                    customerRepository.save(tblCustomerEntity);
                    // xóa 1 đối tượng cũ
                    customerRepository.deleteById(customerByEmail.getId());

                    return RegisterEnum.SUCCESS;
                }
                /* 2.1.2. Email đã được kích hoạt */
                else {
                    /*Thông báo cho người dùng không sử dụng email này */
                    return RegisterEnum.DUPLICATE_EMAIL;
                }
            }
            /* 2.2 Email chưa tồn tại */
            else {
                /* Tạo customer mới hoàn toàn */
                // Tạo customer +  gửi mail + save
                TblCustomerEntity tblCustomerEntity = addCustomer(newCustomer);
                sendMailService.registerCustomer(tblCustomerEntity);
                customerRepository.save(tblCustomerEntity);

                return RegisterEnum.SUCCESS;
            }
        }

    }

    @Override
    public void forgetPassword(TblCustomerDto tblCustomerDto) {
        TblCustomerEntity customer = customerRepository.findFirstByEmail(tblCustomerDto.getEmail());

        if (customer == null || !customer.getStatus()) {
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
        } else {
            customer.setOtp(GenerateOtpUtil.create(6));
            customer.setOtpExp(DateUtil.getCurrentDay());
            sendMailService.forgetPasswordCustomer(customer);
            customerRepository.save(customer);
        }
    }

    @Override
    public VerificationEnum checkOtp(ResetPasswordDTO resetPasswordDTO, String inputOtp) {
        try {
            TblCustomerEntity customer = customerRepository.findFirstByEmail(resetPasswordDTO.getEmail());
            if (customer == null || customer.getEmail().isBlank()) {
                throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
            }

            if (customer.getOtpFailAttempts() >= 5)
                return VerificationEnum.FAIL_ATTEMPT;
            if (!isOTPRequired(customer)) {
                if (customer.getOtp().equals(inputOtp)) {
                    customer.setOtp(null);
                    customer.setOtpExp(null);
                    customer.setPassword(resetPasswordDTO.getPassword());
                    customer.setOtpFailAttempts(0);

                    customerRepository.save(customer);
                    return VerificationEnum.SUCCESS;
                } else {
                    customer.setOtpFailAttempts(customer.getOtpFailAttempts() + 1);
                    customerRepository.save(customer);
                    return VerificationEnum.FAILED;
                }
            } else {
                return VerificationEnum.TIME_OUT;
            }
        } catch (Exception ex) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, Constants.TABLE_CUSTOMER));
        }
    }

    @Override
    public VerificationEnum verify(String verificationCode) {
        TblCustomerEntity customer = customerRepository.findFirstByVerificationCode(verificationCode);

        if (customer == null || customer.getStatus())
            return VerificationEnum.FAILED;
        if (!isVerificationRequired(customer)) {
            customer.setVerificationCode(null);
            customer.setVerificationCodeExp(null);
            customer.setStatus(true);
            customerRepository.save(customer);

            return VerificationEnum.SUCCESS;
        } else {
            return VerificationEnum.TIME_OUT;
        }
    }
}
