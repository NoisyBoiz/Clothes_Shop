package com.tuananh.AdminPage.models.dto;

import com.tuananh.AdminPage.shareds.Constants;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerDto {
    private int id;
    private String phone;
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
    private String fullName;
    private Boolean gender;
    @Past(message = Constants.INVALID_BIRTHDAY)
    private Date birthday;
    @Pattern(regexp = Constants.REGEX_URL_IMAGE, message = Constants.INVALID_FILE_IMAGE)
    private String avatar;
}