package com.tuananh.AdminPage.controllers;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.models.dto.AuthRequest;
import com.tuananh.AdminPage.models.dto.TblAdminOutDto;
import com.tuananh.AdminPage.repositories.AdminRepository;
import com.tuananh.AdminPage.repositories.CustomerRepository;
import com.tuananh.AdminPage.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tuananh.AdminPage.models.ApiResult;
import com.tuananh.AdminPage.models.dto.AuthOut;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;


//    @PostMapping("/authenticate")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            TblCustomerEntity customer = customerRepository.findFirstByUsername(authRequest.getUsername());
//            TblAdminEntity admin = adminRepository.findFirstByUsername(authRequest.getUsername());
//            if ((customer != null && customer.getStatus()) || (admin != null && admin.getStatus())) {
//                return jwtService.generateToken(authRequest.getUsername());
//            }
//            return "tài khoản chưa được kích hoạt";
//        } else {
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResult<AuthOut>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            TblCustomerEntity customer = customerRepository.findFirstByUsername(authRequest.getUsername());
            TblAdminEntity admin = adminRepository.findFirstByUsername(authRequest.getUsername());
            if (customer != null && customer.getStatus()){
                AuthOut authOut = AuthOut.create(
                        customer.getId(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getFullName(),
                        customer.getRoles(),
                        customer.getGender(),
                        customer.getBirthday(),
                        customer.getAvatar(),
                        jwtService.generateToken(authRequest.getUsername()),
                        "customer"
                );
                ApiResult<AuthOut> result = ApiResult.create(HttpStatus.OK,"success", authOut);
                return ResponseEntity.ok(result);
            }
            else if(admin != null && admin.getStatus()){
                AuthOut authOut = AuthOut.create(
                        admin.getId(),
                        admin.getPhone(),
                        admin.getEmail(),
                        admin.getFullName(),
                        admin.getRoles(),
                        admin.getGender(),
                        admin.getBirthday(),
                        admin.getAvatar(),
                        jwtService.generateToken(authRequest.getUsername()),
                        "admin"
                );
                ApiResult<AuthOut> result = ApiResult.create(HttpStatus.OK,"success", authOut);
                return ResponseEntity.ok(result);
            }
            ApiResult<AuthOut> result = ApiResult.create(HttpStatus.BAD_REQUEST,"tài khoản chưa được kích hoạt", null);
            return ResponseEntity.ok(result);
        }
        else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


}
