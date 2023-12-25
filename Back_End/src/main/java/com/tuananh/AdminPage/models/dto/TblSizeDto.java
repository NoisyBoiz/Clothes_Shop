package com.tuananh.AdminPage.models.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblSizeDto {
    @NotNull
    private int id;
    @NotNull
    private String label;
}
