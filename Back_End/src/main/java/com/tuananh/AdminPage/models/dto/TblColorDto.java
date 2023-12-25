package com.tuananh.AdminPage.models.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblColorDto {
    @NotNull
    private int id;
    @NotNull
    private String label;
    private String image;
}
