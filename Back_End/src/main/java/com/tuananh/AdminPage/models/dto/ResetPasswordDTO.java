package com.tuananh.AdminPage.models.dto;
import com.tuananh.AdminPage.shareds.Constants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    @NotNull
    @Size(min = 8, max = 20, message = Constants.INVALID_PASSWORD_MIN_LENGTH)
    private String password;
    @NotNull
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
}