package com.tuananh.AdminPage.models.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblQuantityDto {
    @NotNull
    private String sku;
    private int productId;
    private String sizeId;
    private String colorId;
    private Long value;
    private String mediaGallery;
}
