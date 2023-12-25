package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.advice.exceptions.NotFoundException;
import com.tuananh.AdminPage.enums.RegisterEnum;
import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.ResetPasswordDTO;
import com.tuananh.AdminPage.models.dto.TblAdminDto;
import com.tuananh.AdminPage.models.dto.TblAdminOutDto;
import com.tuananh.AdminPage.models.dto.UpdateCustomerDto;
import com.tuananh.AdminPage.services.AdminService;
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

//@RestController
@Controller
@RequestMapping(path = "/admins")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final String tableAdmin = Constants.TABLE_ADMIN;

    @GetMapping(path = "/list")
    // [GET] localhost:8080/admins/list
    public ResponseEntity<ApiResult<List<TblAdminOutDto>>> showCustomerList() {
        List<TblAdminOutDto> tblAdminOutDtoList = adminService.getAllAdmin();
        String message = MessageFormat.format(Constants.GET_DATA_SUCCESS, tableAdmin);
        if (tblAdminOutDtoList.isEmpty()) {
            message = MessageFormat.format(Constants.GET_DATA_EMPTY, tableAdmin);
        }
        ApiResult<List<TblAdminOutDto>> result = ApiResult.create(HttpStatus.OK, message, tblAdminOutDtoList);
        System.out.println("success");
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "searchById")
    public ResponseEntity<ApiResult<TblAdminOutDto>> searchByLabel(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        TblAdminOutDto tblAdminOutDto = adminService.searchById(id);
        if (tblAdminOutDto == null) {
            throw new NotFoundException(MessageFormat.format(Constants.SEARCH_DATA_EMPTY, "Tbl_admin"));
        }
        ApiResult<TblAdminOutDto> result = ApiResult.create(HttpStatus.OK, MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, "Tbl_admin"), tblAdminOutDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/searchByName")
    // [GET] localhost:8080/admins/searchByName?keyword=Tuấn
    public ResponseEntity<ApiResult<List<TblAdminOutDto>>> searchByFullName(@RequestParam(name = "keyword", required = false, defaultValue = "") String fullName) {
        List<TblAdminOutDto> tblAdminOutDtoList = adminService.searchByFullName(fullName);
        String message = MessageFormat.format(Constants.SEARCH_DATA_SUCCESS, tableAdmin);
        if (tblAdminOutDtoList.isEmpty()) {
            message = MessageFormat.format(Constants.SEARCH_DATA_EMPTY, tableAdmin);
        }
        ApiResult<List<TblAdminOutDto>> result = ApiResult.create(HttpStatus.OK, message, tblAdminOutDtoList);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<ApiResult<UpdateCustomerDto>> editInfoCustomer(@Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
        adminService.edit(updateCustomerDto);
        ApiResult<UpdateCustomerDto> result = ApiResult.create(HttpStatus.OK, Constants.UPDATE_SUCCESS, updateCustomerDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete/{customerID}")
    public ResponseEntity<ApiResult<Integer>> deleteCustomer(@PathVariable int customerID) {
        adminService.softDeleteCustomer(customerID);
        ApiResult<Integer> result = ApiResult.create(HttpStatus.OK, Constants.DELETE_SUCCESS, customerID);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/restore/{adminID}/")
    // [PUT] localhost:8080/categories/restore/1
    public ResponseEntity<ApiResult<Integer>> restore( @PathVariable("adminID") int adminID) {
        adminService.restore(adminID);
        ApiResult<Integer> rs = ApiResult.create(
                HttpStatus.OK,
                Constants.RESTORE_SUCCESS,
                adminID
        );
        return ResponseEntity.ok(rs);
    }





    @PostMapping(path = "/register")
    // [POST] http://localhost:8080/admins/register
    public ResponseEntity<ApiResult<TblAdminDto>> registerCustomer(@Valid @RequestBody TblAdminDto tblAdminDto) throws MessagingException {
        RegisterEnum registerEnum = adminService.register(tblAdminDto);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = registerEnum.getDescription();

        switch (registerEnum) {
            case DUPLICATE_EMAIL -> {
                httpStatus = HttpStatus.CONFLICT;
                message = MessageFormat.format(registerEnum.getDescription(), tblAdminDto.getEmail());
            }
            case DUPLICATE_USERNAME -> {
                httpStatus = HttpStatus.CONFLICT;
                message = MessageFormat.format(registerEnum.getDescription(), tblAdminDto.getUsername());
            }
        }

        ApiResult<TblAdminDto> result = ApiResult.create(httpStatus, message, tblAdminDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/register/verify")
    public String verifyUser(@RequestParam(name = "code") String code) {

        switch (adminService.verify(code)) {
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
    // [PATCH] http://localhost:8080/admins/forgetPassword
    public ResponseEntity<ApiResult<TblAdminDto>> forgetPassword(@RequestBody TblAdminDto tblAdminDto) {
        adminService.forgetPassword(tblAdminDto);
        ApiResult<TblAdminDto> result = ApiResult.create(HttpStatus.OK, "Hãy vào email của bạn để lấy mã OTP", tblAdminDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/forgetPassword/checkOtp")
    // [PUT] http://localhost:8080/admins/forgetPassword/checkOtp?otp=013185
    public ResponseEntity<ApiResult<ResetPasswordDTO>> checkOtp(@RequestBody ResetPasswordDTO resetPasswordDTO, @RequestParam(name = "otp") String inputOtp) {

        HttpStatus httpStatus = HttpStatus.OK;
        String message = Constants.OTP_SUCCESS;
        switch (adminService.checkOtp(resetPasswordDTO, inputOtp)) {
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
