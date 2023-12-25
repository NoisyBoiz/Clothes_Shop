package com.tuananh.AdminPage.models.dto;

import com.tuananh.AdminPage.shareds.Constants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class AuthOut {
    private int id;
    private String phone;
    private String email;
    private String fullName;
    private String roles;
    private Boolean gender;
    private Date birthday;
    private String avatar;
    private String token;
    private String permissions;
}
