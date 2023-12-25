package com.tuananh.AdminPage.models.dto;

import com.tuananh.AdminPage.shareds.Constants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull
    @Size(min = 6, max = 20, message = Constants.INVALID_USERNAME_MIN_LENGTH)
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @NotNull
    @Size(min = 8, max = 20, message = Constants.INVALID_PASSWORD_MIN_LENGTH)
    private String password;
}