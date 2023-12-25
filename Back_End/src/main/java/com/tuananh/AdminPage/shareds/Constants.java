package com.tuananh.AdminPage.shareds;

import java.sql.Date;

public class Constants {
    /* Table name */
    public static final String TABLE_CUSTOMER = "tbl_customer";
    public static final String TABLE_ADMIN = "tbl_admin";
    public static final String TABLE_CATEGORY = "tbl_category";
    public static final String TABLE_PRODUCT = "tbl_product";
    public static final String TABLE_COLOR = "tbl_color";
    public static final String TABLE_SIZE = "tbl_size";
    public static final String TABLE_QUANTITY = "tbl_quantity";

    /* Data */
    public static final String GET_DATA_SUCCESS = "Lấy dữ liệu của bảng ''{0}'' thành công ^.^";
    public static final String GET_DATA_EMPTY = "Dữ liệu lấy ra từ bảng ''{0}'' trống!!! ";
    public static final String SEARCH_DATA_SUCCESS = "Tìm kiếm dữ liệu từ bảng ''{0}'' thành công ^.^";
    public static final String SEARCH_DATA_EMPTY = "Không tìm thấy dữ liệu phù hợp trong bảng ''{0}''!!! ";
    public static final String SEARCH_CUSTOMER_EMPTY = "Không tìm thấy người dùng phù hợp!";
    public static final String SEARCH_ADMIN_EMPTY = "Không tìm thấy ADMIN phù hợp!";
    public static final String SEARCH_CATEGORY_EMPTY = "Không tìm thấy Category phù hợp!";
    public static final String SEARCH_PRODUCT_EMPTY = "Không tìm thấy Product phù hợp!";
    public static final String SEARCH_SIZE_EMPTY = "Không tìm thấy Size phù hợp!";
    public static final String SEARCH_COLOR_EMPTY = "Không tìm thấy Color phù hợp!";


    public static final String SEARCH_COLOR_ID_EMPTY = "Không tìm thấy Color có Id=`{0}` phù hợp!";
    public static final String SEARCH_SIZE_ID_EMPTY = "Không tìm thấy Size có Id=`{0}` phù hợp!";

    public static final String SEARCH_PRODUCT_ID_EMPTY = "Không tìm thấy Product có Id=`{0}` phù hợp!";

    public static final String DELETE_SUCCESS = "Xoá thành công rồi nhé!";
    public static final String UPDATE_SUCCESS = "Cập nhật thành công rồi nhé!";
    public static final String RESTORE_SUCCESS = "Khôi phục thành công rồi nhé!";

    public static final String REQUIRE_TYPE = "`{1}` phải là dạng `{0}`";

    public static final String DUPLICATE_FIELD = "Trường `{0}` của bảng `{1}` đã tồn tại rồi!";

    /* Add Data */
    public static final String SAVE_DATA_SUCCESS = "Dữ liệu đã được lưu thành công.";
    public static final String DUPLICATE_ERROR_ID = "Id có giá trị ''{0}'' đã được sử dụng. Vui lòng sử dụng Id khác";
    public static final String DUPLICATE_ERROR_SKU = "Sku có giá trị ''{0}'' đã được sử dụng. Vui lòng sử dụng Sku khác";
    /* Register */
    public static final String REGISTER_SUCCESS = "Đăng kí thành công.";
    public static final String CONFIRM_EMAIL = "Vui lòng hãy vào email vừa đăng kí để xác nhận!";

    public static final String DUPLICATE_ERROR_EMAIL = "Email có địa chỉ ''{0}'' đã được đăng ký rồi. Vui lòng dùng Email khác để đăng ký! ";
    public static final String DUPLICATE_ERROR_USERNAME = "Username có giá trị ''{0}'' đã được đăng ký rồi. Vui lòng dùng Username khác để đăng ký! ";

    /* OTP */

    public static final String OTP_SUCCESS = "The entered OTP is correct ^.^ ";
    public static final String OTP_FAILED = "The entered OTP is incorrect :((  ";
    public static final String OTP_TIME_OUT = "The OTP has timed out :(( ";
    public static final String OTP_COUNT_FAIL_ATTEMPT = "You have entered more than 5 times. Please contact the administrator!";


    /* Mail */
    public static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    public static final long VERIFICATION_CODE_DURATION = 5 * 60 * 1000;

    public static final String URL_VERIFICATION_CUSTOMER = "http://localhost:8080/customers/register/verify?code=";
    public static final String URL_VERIFICATION_ADMIN = "http://localhost:8080/admins/register/verify?code=";

    public final static class MESSAGE_CATEGORY{

    }
    public final static class MESSAGE_PRODUCT{

    }
    public final static class MESSAGE_QUANTITY{

    }
    public final static class MESSAGE_COLOR{

    }
    public final static class MESSAGE_SIZE{

    }



    public final static class SEND_MAIL_SUBJECT {
        public final static String CUSTOMER_REGISTER = "XÁC NHẬN THÔNG TIN KHÁCH HÀNG ĐĂNG KÝ";
        public final static String CUSTOMER_FORGET_PASSWORD = "MÃ XÁC NHẬN LẤY LẠI TÀI KHOẢN KHÁCH HÀNG";

        public final static String ADMIN_REGISTER = "XÁC NHẬN THÔNG TIN ADMIN ĐĂNG KÝ";
    }
    public final static class TEMPLATE_FILE_NAME {
        public final static String CUSTOMER_FORGET_PASSWORD = "customer_forget_password";
        public final static String ADMIN_REGISTER = "admin";
        public final static String VERIFY_CUSTOMER = "verify";

    }

    /* Validate */
    public static final String INVALID_DATA_FIELD = "Invalid Data Field!";
    public static final String INVALID_EMAIL = "Invalid email address!";
    public static final String INVALID_NOTNULL = "Must not be null!";
    public static final String INVALID_EMPTY = "Must not be empty!";
    public static final String INVALID_FILE_IMAGE = "The input data must be the image link (.png or .jpg)";
    public static final String INVALID_PASSWORD_MIN_LENGTH = "Password must be between 8 and 20 characters";
    public static final String INVALID_USERNAME_MIN_LENGTH = "Username must be between 6 and 20 characters";
    public static final String INVALID_BIRTHDAY = "Birthday must be before current time!";

    /* Message */
//    public static final String


    /* Function */
    public static Date getCurrentDay(){
        java.util.Date currentDate = new java.util.Date();
        return new Date(currentDate.getTime());
    }

    /* Regex */
    public static final String REGEX_URL_IMAGE = "(https?:\\/\\/.*\\.(?:png|jpg))";

}
