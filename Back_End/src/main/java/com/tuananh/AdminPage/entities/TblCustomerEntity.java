package com.tuananh.AdminPage.entities;

import com.tuananh.AdminPage.shareds.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_customer", schema = "dbo", catalog = "admin")
public class TblCustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 2147483647)
    private String password;
    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    @Basic
    @Column(name = "roles", nullable = true, length = 50)
    private String roles;
    @Basic
    @Column(name = "gender", nullable = true)
    private Boolean gender;
    @Basic
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @Basic
    @Column(name = "avatar", nullable = true, length = 2147483647)
    private String avatar;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
    @Basic
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;
    @Basic
    @Column(name = "otp", nullable = true, length = 2147483647)
    private String otp;
    @Basic
    @Column(name = "otp_fail_attempts", nullable = true)
    private int otpFailAttempts;
    @Basic
    @Column(name = "otp_exp", nullable = true)
    private Date otpExp;
    @Basic
    @Column(name = "status", nullable = true)
    private Boolean status;
    @Basic
    @Column(name = "verification_code", length = 64, nullable = true)
    private String verificationCode;
    @Basic
    @Column(name = "verification_code_exp", nullable = true)
    private Date verificationCodeExp;

}
