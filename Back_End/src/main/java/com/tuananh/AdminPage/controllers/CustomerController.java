package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.enums.VerificationEnum;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.*;
import com.tuananh.AdminPage.services.CustomerService;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final String tableCustomer = Constants.TABLE_CUSTOMER;

    @GetMapping(path = "/list")
    // [GET] localhost:8080/customers/list
    public ResponseEntity<ApiResult<List<TblCustomerOutDto>>> showCustomerList() {
        List<TblCustomerOutDto> tblCustomerOutDtoList = customerService.getAllCustomer();
        String message = MessageFormat.format(Constants.GET_DATA_SUCCESS, tableCustomer);
        if (tblCustomerOutDtoList.isEmpty()) {
            message = MessageFormat.format(Constants.GET_DATA_EMPTY, tableCustomer);
        }
        ApiResult<List<TblCustomerOutDto>> result = ApiResult.create(HttpStatus.OK, message, tblCustomerOutDtoList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "searchById")
    public ResponseEntity<ApiResult<TblCustomerOutDto>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblCustomerOutDto tblCustomerOutDto = customerService.searchById(id);
        if (tblCustomerOutDto == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, "Tbl_admin"));
        }
        ApiResult<TblCustomerOutDto> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, "Tbl_admin"), tblCustomerOutDto);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchByName")
    // [GET] localhost:8080/customers/search?keyword=Tuấn
    public ResponseEntity<ApiResult<List<TblCustomerOutDto>>> searchByFullName(@RequestParam(name = "keyword", required = false, defaultValue = "") String fullName) {
        List<TblCustomerOutDto> tblCustomerOutDtoList = customerService.searchByFullName(fullName);
        String message = MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableCustomer);
        if (tblCustomerOutDtoList.isEmpty()) {
            message = MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableCustomer);
        }
        ApiResult<List<TblCustomerOutDto>> result = ApiResult.create(HttpStatus.OK, message, tblCustomerOutDtoList);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<UpdateCustomerDto>> editInfoCustomer(@Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
        customerService.edit(updateCustomerDto);
        ApiResult<UpdateCustomerDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, updateCustomerDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{customerID}")
    public ResponseEntity<ApiResult<Integer>> deleteCustomer(@PathVariable int customerID) {
        customerService.softDeleteCustomer(customerID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, customerID);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/restore/{customerID}/")
    // [PUT] localhost:8080/customers/restore/1
    public ResponseEntity<ApiResult<Integer>> restore( @PathVariable("customerID") int customerID) {
        customerService.restore(customerID);
        ApiResult<Integer> rs = ApiResult.create(
                HttpStatus.OK,
                Constants.RESTORE_SUCCESS,
                customerID
        );
        return ResponseEntity.ok(rs);
    }

    @PostMapping(path = "/register")
    // [POST] http://localhost:8080/customers/register
    public ResponseEntity<ApiResult<TblCustomerDto>> registerCustomer(@Valid @RequestBody TblCustomerDto tblCustomerDto) throws MessagingException {
        RegisterEnum registerEnum = customerService.register(tblCustomerDto);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = registerEnum.getDescription();

        switch (registerEnum) {
            case DUPLICATE_EMAIL -> {
                httpStatus = HttpStatus.CONFLICT;
                message = MessageFormat.format(registerEnum.getDescription(), tblCustomerDto.getEmail());
            }
            case DUPLICATE_USERNAME -> {
                httpStatus = HttpStatus.CONFLICT;
                message = MessageFormat.format(registerEnum.getDescription(), tblCustomerDto.getUsername());
            }
        }

        ApiResult<TblCustomerDto> result = ApiResult.create(httpStatus, message, tblCustomerDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/register/verify")
    public String verifyUser(@RequestParam(name = "code") String code) {
        VerificationEnum verificationEnum = customerService.verify(code);

        switch (verificationEnum) {
            case SUCCESS -> {
                return "verify_success";
            }
            case FAILED -> {
                return "verify_fail";
            }
            case TIME_OUT -> {
                return "verify_time_out";
            }
        }
        return null;

    }

    @PatchMapping(path = "/forgetPassword")
    // [PATCH] http://localhost:8080/customers/forgetPassword
    public ResponseEntity<ApiResult<TblCustomerDto>> forgetPassword(@RequestBody TblCustomerDto tblCustomerDto) {
        customerService.forgetPassword(tblCustomerDto);
        ApiResult<TblCustomerDto> result = ApiResult.create(HttpStatus.OK, "Hãy vào email của bạn để lấy mã OTP", tblCustomerDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/forgetPassword/checkOtp")
    // [PUT] http://localhost:8080/customers/forgetPassword/checkOtp?otp=013185
    public ResponseEntity<ApiResult<ResetPasswordDTO>> checkOtp(@RequestBody ResetPasswordDTO resetPasswordDTO, @RequestParam(name = "otp") String inputOtp) {
        VerificationEnum verificationEnum = customerService.checkOtp(resetPasswordDTO, inputOtp);

        HttpStatus httpStatus = HttpStatus.OK;
        String message = Constants.OTP_SUCCESS;
        switch (verificationEnum) {
            case FAILED -> {
                message = Constants.OTP_FAILED;
            }
            case TIME_OUT -> {
                message = Constants.OTP_TIME_OUT;
            }
            case FAIL_ATTEMPT -> {
                httpStatus = HttpStatus.BAD_REQUEST;
                message = Constants.OTP_COUNT_FAIL_ATTEMPT;
            }
        }
        ApiResult<ResetPasswordDTO> result = ApiResult.create(httpStatus, message, resetPasswordDTO);
        return ResponseEntity.ok(result);
    }

}
