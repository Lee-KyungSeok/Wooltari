package kr.co.wooltari.constant;

/**
 * Created by Kyung on 2017-11-28.
 */

public class Const {
    // Permission Key
    public static final int PERMISSION_REQ_CAMERA = 800;
    public static final int PERMISSION_REQ_GALLERY = 801;
    public static final int PERMISSION_REQ_NOTIFICATION = 820;

    // Camera & Gallery Request Code
    public static final int POPUP_CAMERA = 810;
    public static final int POPUP_GALLERY = 811;

    // User Key
    public static final String USER_TOKEN = "token";
    public static final String USER_PRIMARY_KEY = "user_pk";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_AUTO_SIGNIN = "user_auto_signin";
    public static final String USER_FACEBOOK_USER_ID = "facebook_id";
    public static final String USER_FACEBOOK_AUTO_SIGNIN = "user_facebook_signin";

    // Pet Key
    public static final String PET_ID = "pet_id";
    public static final String PET_NAME = "pet_name";
    public static final String PET_COLOR = "pet_color";
    public static final String PET_ACTIVE = "pet_active";
    public static final String PET_PROFILE_URL = "pet_profile";
    public static final String PET_INFO = "pet_information";

    // Pet Medical Key
    public static final String PET_MEDICAL_ID = "pet_medical_id";

    // Pet Request Code
    public static final int PET_PROFILE = 700;
    public static final int PET_PROFILE_UPDATE = 701;
    public static final int PET_PROFILE_DELETE = 702;
    public static final int PET_STATE= 710;
    public static final int PET_SCHEDULE= 720;
    public static final int PET_VACCINE= 730;
    public static final int PET_MEDICAL= 740;
    public static final int PET_MEDICAL_ADD = 741;
    public static final int PET_MEDICAL_EDIT = 742;
    public static final int PET_ALARM_NOTIFICATION = 743;

    // alarm Code
    public static final String ALARM_OFF = "alarm_off";
    public static final String ALARM_ON = "alarm_on";
    public static final int ALARM_NOTI_ID = 10;
}
