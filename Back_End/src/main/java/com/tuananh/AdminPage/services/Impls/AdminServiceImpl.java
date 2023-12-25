package com.tuananh.AdminPage.services.Impls;

import com.tuananh.AdminPage.advice.exceptions.DuplicateRecordException;
import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.enums.VerificationEnum;
import com.tuananh.AdminPage.models.dto.ResetPasswordDTO;
import com.tuananh.AdminPage.models.dto.TblAdminDto;
import com.tuananh.AdminPage.models.dto.TblAdminOutDto;
import com.tuananh.AdminPage.models.dto.UpdateCustomerDto;
import com.tuananh.AdminPage.models.mapper.AdminMapper;
import com.tuananh.AdminPage.repositories.AdminRepository;
import com.tuananh.AdminPage.repositories.CustomerRepository;
import com.tuananh.AdminPage.services.AdminService;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final SendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(TblAdminEntity tblAdminEntity) {
        adminRepository.save(tblAdminEntity);
    }

    @Override
    public List<TblAdminOutDto> getAllAdmin() {
        List<TblAdminOutDto> result = new ArrayList<>();
        List<TblAdminEntity> admins = adminRepository.findAll();
        for (TblAdminEntity admin : admins) {
            result.add(AdminMapper.toAdminDto(admin));
        }
        return result;
    }

    @Override
    public TblAdminEntity addAdmin(TblAdminDto newAdmin) {
        Date currentDay = DateUtil.getCurrentDay();
        String randomCode = RandomStringUtils.randomAlphanumeric(64);

        return TblAdminEntity.create(
                0,
                newAdmin.getUsername(),
                passwordEncoder.encode(newAdmin.getPassword()),
                newAdmin.getPhone(),
                newAdmin.getEmail(),
                newAdmin.getFullName(),
                newAdmin.getGender(),
                "ROLE_ADMIN",
                newAdmin.getBirthday(),
                newAdmin.getAvatar(),
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

    @Transactional
    @Override
    public void edit(UpdateCustomerDto newInfo) {
        TblAdminEntity admin = adminRepository.findFirstById(newInfo.getId());
        if (admin == null) {
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
        }
        if (customerRepository.existsByEmail(newInfo.getEmail()) || adminRepository.existsByEmail(newInfo.getEmail())) {
            throw new DuplicateRecordException(Constants.DUPLICATE_ERROR_EMAIL);
        }

        admin.setPhone(VariableHandler.isNullOrEmpty(newInfo.getPhone()) ? admin.getPhone() : newInfo.getPhone());
        admin.setFullName(VariableHandler.isNullOrEmpty(newInfo.getFullName()) ? admin.getFullName() : newInfo.getFullName());
        admin.setGender(VariableHandler.isNullOrEmpty(newInfo.getGender()) ? admin.getGender() : newInfo.getGender());
        admin.setAvatar(VariableHandler.isNullOrEmpty(newInfo.getAvatar()) ? admin.getAvatar() : newInfo.getAvatar());
        admin.setBirthday(VariableHandler.isNullOrEmpty(newInfo.getBirthday()) ? admin.getBirthday() : newInfo.getBirthday());
        if (!VariableHandler.isNullOrEmpty(newInfo.getEmail())) {
            if (adminRepository.existsByEmail(newInfo.getEmail())) {
                throw new DuplicateRecordException(Constants.DUPLICATE_ERROR_EMAIL);
            } else {
                admin.setEmail(newInfo.getEmail());
            }
        }
        admin.setUpdatedAt(DateUtil.getCurrentDay());

        adminRepository.save(admin);
    }

    @Transactional
    @Override
    public void softDeleteCustomer(int id) {
        TblAdminEntity admin = adminRepository.findFirstById(id);
        if (admin == null || admin.getDeleted())
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);

        admin.setDeleted(true);
        admin.setDeletedAt(Constants.getCurrentDay());

        adminRepository.save(admin);
    }

    @Transactional
    @Override
    public void restore(int id) {
        if (!adminRepository.existsById(id)) {
            throw new NotFoundException(Constants.SEARCH_ADMIN_EMPTY);
        }

        TblAdminEntity admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setDeleted(false);
            admin.setDeletedAt(null);
            adminRepository.save(admin);
        }
    }
    @Override
    public TblAdminOutDto searchById(int id){
        TblAdminEntity tblAdminEntity = adminRepository.findFirstById(id);
        return AdminMapper.toAdminDto(tblAdminEntity);
    }
    @Override
    public List<TblAdminOutDto> searchByFullName(String fullName) {
        List<TblAdminOutDto> result = new ArrayList<>();
        List<TblAdminEntity> admins = adminRepository.findAll();
        for (TblAdminEntity admin : admins) {
            if (admin.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                result.add(AdminMapper.toAdminDto(admin));
            }
        }
        return result;
    }

    @Override
    public boolean isOTPRequired(TblAdminEntity tblAdminEntity) {
        if (tblAdminEntity.getOtp() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = tblAdminEntity.getOtpExp().getTime();

        return otpRequestedTimeInMillis + Constants.OTP_VALID_DURATION <= currentTimeInMillis;
    }

    @Override
    public boolean isVerificationRequired(TblAdminEntity tblAdminEntity) {
        if (tblAdminEntity.getVerificationCode() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = tblAdminEntity.getVerificationCodeExp().getTime();

        return otpRequestedTimeInMillis + Constants.VERIFICATION_CODE_DURATION <= currentTimeInMillis;
    }

    @Override
    public RegisterEnum register(TblAdminDto newAdmin) {
        /* Kiểm tra xem username và email có tồn tại trong tbl_customer chưa */
        if (customerRepository.existsByUsername(newAdmin.getUsername()))
            return RegisterEnum.DUPLICATE_USERNAME;
        if (customerRepository.existsByEmail(newAdmin.getEmail()))
            return RegisterEnum.DUPLICATE_EMAIL;

        /* Kiểm tra xem adminname có tồn tại trong tbl_admin chưa */
        /* 1. Username đã tồn tại */
        if (adminRepository.existsByUsername(newAdmin.getUsername())) {
            TblAdminEntity adminByUsername = adminRepository.findFirstByUsername(newAdmin.getUsername());
            /* Kiểm tra xem admin sở hữu adminname đó đã được kích hoạt chưa */
            /* 1.1 Username chưa được kích hoạt */
            if (!adminByUsername.getStatus()) {
                /* Kiểm tra xem email đã tồn tại trong db chưa */
                /* 1.1.1. Email đã tồn tại trong db */
                if (adminRepository.existsByEmail(newAdmin.getEmail())) {
                    TblAdminEntity adminByEmail = adminRepository.findFirstByEmail(newAdmin.getEmail());
                    /*  Kiểm tra xem email đó đã được kích hoạt chưa */
                    /* 1.1.1.1. Email chưa được kích hoạt */
                    if (!adminByEmail.getStatus()) {
                        /* Tạo mới admin và xóa 2 đối tượng có adminname và email trùng */
                        // Tạo admin +  gửi mail + save
                        TblAdminEntity tblAdminEntity = addAdmin(newAdmin);
                        sendMailService.registerAdmin(tblAdminEntity);
                        adminRepository.save(tblAdminEntity);
                        // xóa 2 đối tượng cũ
                        adminRepository.deleteById(adminByUsername.getId());
                        adminRepository.deleteById(adminByEmail.getId());

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
                    /* Tạo admin và xóa admin có adminname trùng */
                    // Tạo admin +  gửi mail + save
                    TblAdminEntity tblAdminEntity = addAdmin(newAdmin);
                    sendMailService.registerAdmin(tblAdminEntity);
                    adminRepository.save(tblAdminEntity);
                    // xóa 1 đối tượng cũ
                    adminRepository.deleteById(adminByUsername.getId());

                    return RegisterEnum.SUCCESS;
                }
            }
            /* 1.2 Username đã được kích hoạt*/
            else {
                /* Thông báo cho người dùng không sử dụng adminname này */
                return RegisterEnum.DUPLICATE_USERNAME;
            }
        }
        /* 2. Username chưa tồn tại */
        else {
            /* Kiểm tra xem email đã có người dùng chưa */
            /* 2.1 Email đã tồn tại */
            if (adminRepository.existsByEmail(newAdmin.getEmail())) {
                TblAdminEntity adminByEmail = adminRepository.findFirstByEmail(newAdmin.getEmail());
                /*Kiểm tra xem email đó đã được kích hoạt chưa */
                /* 2.1.1. Email chưa được kích hoạt */
                if (!adminByEmail.getStatus()) {
                    /* Tạo mới admin và xóa đối tượng có email trùng */
                    // Tạo admin +  gửi mail + save
                    TblAdminEntity tblAdminEntity = addAdmin(newAdmin);
                    sendMailService.registerAdmin(tblAdminEntity);
                    adminRepository.save(tblAdminEntity);
                    // xóa 1 đối tượng cũ
                    adminRepository.deleteById(adminByEmail.getId());

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
                /* Tạo admin mới hoàn toàn */
                // Tạo admin +  gửi mail + save
                TblAdminEntity tblAdminEntity = addAdmin(newAdmin);
                sendMailService.registerAdmin(tblAdminEntity);
                adminRepository.save(tblAdminEntity);

                return RegisterEnum.SUCCESS;
            }
        }

    }

    @Override
    public void forgetPassword(TblAdminDto tblCustomerDto) {
        TblAdminEntity admin = adminRepository.findFirstByEmail(tblCustomerDto.getEmail());

        if (admin == null || !admin.getStatus()) {
            throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
        } else {
            admin.setOtp(GenerateOtpUtil.create(6));
            admin.setOtpExp(DateUtil.getCurrentDay());
            sendMailService.forgetPasswordAdmin(admin);
            adminRepository.save(admin);
        }
    }

    @Override
    public VerificationEnum checkOtp(ResetPasswordDTO resetPasswordDTO, String inputOtp) {
        try {
            TblAdminEntity admin = adminRepository.findFirstByEmail(resetPasswordDTO.getEmail());
            if (admin == null || admin.getEmail().isBlank()) {
                throw new NotFoundException(Constants.SEARCH_CUSTOMER_EMPTY);
            }

            if (admin.getOtpFailAttempts() >= 5)
                return VerificationEnum.FAIL_ATTEMPT;
            if (!isOTPRequired(admin)) {
                if (admin.getOtp().equals(inputOtp)) {
                    admin.setOtp(null);
                    admin.setOtpExp(null);
                    admin.setPassword(resetPasswordDTO.getPassword());
                    admin.setOtpFailAttempts(0);

                    adminRepository.save(admin);
                    return VerificationEnum.SUCCESS;
                } else {
                    admin.setOtpFailAttempts(admin.getOtpFailAttempts() + 1);
                    adminRepository.save(admin);
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
        TblAdminEntity admin = adminRepository.findFirstByVerificationCode(verificationCode);

        if (admin == null || admin.getStatus())
            return VerificationEnum.FAILED;
        if (!isVerificationRequired(admin)) {
            admin.setVerificationCode(null);
            admin.setVerificationCodeExp(null);
            admin.setStatus(true);
            adminRepository.save(admin);

            return VerificationEnum.SUCCESS;
        } else {
            return VerificationEnum.TIME_OUT;
        }
    }
}
