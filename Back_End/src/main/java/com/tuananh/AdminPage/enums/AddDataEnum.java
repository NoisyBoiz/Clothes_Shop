package com.tuananh.AdminPage.enums;

import com.tuananh.AdminPage.shareds.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddDataEnum {
    SUCCESS(Constants.SAVE_DATA_SUCCESS),
    DUPLICATE_ID(Constants.DUPLICATE_ERROR_ID),
    DUPLICATE_SKU(Constants.DUPLICATE_ERROR_SKU);
    private final String description;

}