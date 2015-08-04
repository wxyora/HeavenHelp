package com.heaven.heavenhelp.utils;

/**
 * Created by Zhengliang on 15/3/31.
 */
public class Constants {
    public enum PageType {
        PAGE_MAIN,
        PAGE_LOGIN,
        PAGE_TRANSCATION_MANAGER,
        PAGE_INVESTMENT_MANAGER,
        PAGE_ACCOUNT_MANAGER,
        PAGE_PRODUCT_INTR
    }

    public static final String HOST = "https://www.happyfi.com";
    //public static final String HOST = "http://192.168.0.88:8088";
    public static final boolean OPEN_LOG_FLAG = false;
    public static final boolean HIT_DATA_FLAG = false;

    public static final int REWARD_DIBI = 50000;
    public static final String JSON_STATUS = "status";
    public static final String JSON_MSG = "msg";
    public static final String JSON_STATUS_SUCCESS = "0000";
    public static final String JSON_STATUS_FAIL = "1001";
    public static final String JSON_NOT_LOGIN = "0001";

    public static final String API_REGISTER = "/c2a/service/check_user";
    public static final String API_GET_VERIFY_CODE = "/c2a/service/get_checkcode";
    public static final String API_REGISTER_LAST = "/c2a/service/user_register";

    public static final String API_HIT_BEGIN_URL = "http://192.168.0.164:8080/analytics/c2a/am/createRecord.dox";
    public static final String API_HIT_END_URL = "http://192.168.0.164:8080/analytics/c2a/am/updateRecord.dox";
    public static final String API_GET_APP_VERSION = HOST + "/mydididai/apk/android.txt";
    public static final String API_DOWNLOAD_APK = HOST + "/mydididai/apk/";
    public static final String APP_NAME = "DidiCredit";
    public static final String API_LOGIN = "/c2a/service/user_login";
    public static final String API_EXIT = "/c2a/service/logout";
    public static final String API_UPLOAD = "/c2a/service/uploadFile";
    public static final String API_MODIFY_PW = "/c2a/service/change_passwd";
    public static final String API_GET_LIST = "/c2a/service/user_queryLoan";
    public static final String API_GET_DETAIL_ORDER = "/c2a/service/user_queryLoanDetai";
    public static final String API_GET_PERSONAL_STATUS = "/c2a/service/user_getStatus";
    public static final String API_VERIFY_ONE = "/c2a/service/user_docApp";
    public static final String API_DIBI_DETAIL_QUERY = "/c2a/service/user_DBQuery";
    public static final String API_ROB_ORDER = "/c2a/service/user_Buy";
    public static final String API_ROB_SETTING = "/c2a/service/user_querySet";
    public static final String API_GET_VERIFIED_INFO = "/c2a/service/user_checkAuthInf";
    public static final String API_ADD_DIBI = "/c2a/service/user_DBRecharge";
    public static final String API_RETRIEVE_DATA = "/c2a/service/user_queryLoan";
    public static final String API_GET_ROBED_ORDERS = "/c2a/service/user_queryOrder";
    public static final String API_UPLOAD_ALL_PIC = "/c2a/service/user_SubmitdocApp";
    public static final String API_VERIFY_EMAIL = "/c2a/service/user_EmailApp";
    public static final String API_GET_SET_PARAMS = "/c2a/service/user_querySetMsg";
    public static final String API_INVITED_DB = "/c2a/service/user_invitation";
    public static final String API_GET_MY_DB = "/c2a/service/user_getDB";
    public static final String API_FORGOT_PW = "/c2a/service/check_forgetPasswd";
    public static final String API_GET_VERIFY_INFO = "/c2a/service/user_queryAuthInf";
    public static final String API_RESET_PW = "/c2a/service/reset_Passwd";
    public static final String API_SET_PUSH_MSG = "/c2a/service/user_reciveSysMsg";
    public static final String API_GET_SINGLE_DETAIL = "/c2a/service/user_queryLoanDetail";
    public static final String API_GPS = "http://api.map.baidu.com/geocoder/v2/?ak=gLvQIMvBENbYpEXoC5oHaX93";
    public static final String API_IP = "http://api.map.baidu.com/location/ip?ak=gLvQIMvBENbYpEXoC5oHaX93";
    public static final String APP_KEY = "fAihIDGd9S7V8EN45D0yD";
    public static final String APP_ID = "fZzMnmiGfD7UUWJGuqbyP9";
    public static final String APP_SECRET = "v8OJSnskJS80sogdZC1vo";
    public static final int CALL_BACK_SUCCESS = 10000;
    public static final int CALL_BACK_FAIL = 11001;
    public static final int CALL_BACK_NET_FAIL = 11111;

    //--API
    //USER LOGIN CODE
    public static final int UPDATE_TIME = 666666;
    public static final int CALL_BACK_LOGIN_OK = 20000;
    public static final int CALL_BACK_LOGIN_ERROR = 20001;

    public static final int CALL_BACK_USER_DB_RECORD_OK = 20002;
    public static final int CALL_BACK_USER_DB_RECORD_ERROR = 20003;
    public static final int CALL_BACK_USER_NOT_LOGIN = 9990;

    public static final int CALL_BACK_VERIFY_USER_INFO_SUCCESS = 30000;
    public static final int CALL_BACK_VERIFY_USER_INFO_FAIL = 30001;

    public static final int CALL_BACK_VERIFY_INVITED_AVA = 31000;
    public static final int CALL_BACK_VERIFY_INVITED_NOAVA = 31001;

    public static final int CALL_BACK_GPS_SUCCESS = 32000;
    public static final int CALL_BACK_GAP_FAIL = 32001;

    public static final int CALL_BACK_IP_SUCCESS = 33000;
    public static final int CALL_BACK_IP_FAIL = 33001;

    public static final int CALL_ROB_BACK_SUCCESS = 40000;
    public static final int CALL_ROB_BACK_FAIL_ROBED = 41001;
    public static final int CALL_ROB_BACK_FAIL_NO_MONEY = 41002;
    public static final int CALL_ROB_BACK_FAIL_NO_VERIFY = 41003;
    public static final int CALL_NO_LOGIN = 900001;

    public static final int CALL_MORE_GET_PUSH_SETTING = 42000;
    public static final int CALL_MORE_GET_PUSH_SETTING_FAIL = 42001;
    public static final int CALL_MORE_SET_PUSH_SETTING = 43000;
    public static final int CALL_MORE_SET_PUSH_SETTING_FAIL = 43001;

    public static final int CALL_BACK_GET_VERIFY_RESULT = 50000;
    public static final int CALL_BACK_GET_VERIFY_RESULT_ERROR = 50001;

    public static final int CALL_BACK_SUBMIT_ALL = 60000;
    public static final int CALL_BACK_SUBMIT_ALL_FAIL = 60001;

    public static final int CALL_BACK_GET_SETING = 70000;
    public static final int CALL_BACK_GET_SETTING_ERROR = 70001;


    public static final int CALL_BACK_GET_FW = 80000;
    public static final int CALL_BACK_GET_FW_ERROR = 80001;

    //NETwork error
    public static final int CALL_BACK_OVER_TIME = 9999;
    public static final int CALL_BACK_SYSTEM_ERROR = 8888;

    public static final int REQUEST_CODE_ADD_MONEY = 7777;
}
