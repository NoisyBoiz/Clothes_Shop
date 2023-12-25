package com.tuananh.AdminPage.enums;

import com.tuananh.AdminPage.shareds.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum VerificationEnum {
    SUCCESS,
    FAILED,
    TIME_OUT,
    FAIL_ATTEMPT;

}
