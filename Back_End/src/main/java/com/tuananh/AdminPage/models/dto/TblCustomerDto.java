package com.tuananh.AdminPage.models.dto;

import com.tuananh.AdminPage.shareds.Constants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblCustomerDto {
    @NotNull
    @Size(min = 6, max = 20, message = Constants.INVALID_USERNAME_MIN_LENGTH)
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @NotNull
    @Size(min = 8, max = 20, message = Constants.INVALID_PASSWORD_MIN_LENGTH)
    private String password;
    private String phone;
    @NotNull
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
    @NotNull
    private String fullName;
    private Boolean gender;
    @Past(message = Constants.INVALID_BIRTHDAY)
    private Date birthday;
    @Pattern(regexp = Constants.REGEX_URL_IMAGE, message = Constants.INVALID_FILE_IMAGE)
    private String avatar;
}