package com.tuananh.AdminPage.models.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblCustomerOutDto {
    private int id;
    private String username;
    private String phone;
    private String email;
    private String fullName;
    private String roles;
    private Boolean gender;
    private Date birthday;
    private String avatar;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Boolean deleted;
    private String otp;
    private int otpFailAttempts;
    private Date otpExp;
    private Boolean status;
}
