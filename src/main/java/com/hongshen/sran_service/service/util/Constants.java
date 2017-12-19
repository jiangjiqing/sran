package com.hongshen.sran_service.service.util;

/**
 * Created by poplar on 17-10-27.
 */
public class Constants {

    // request
    public static final String WCDMA = "wcdma";//3G  
    public static final String LTE = "lte";//4G   
    public static final String UNICOM = "unicom";//中国联通   
    public static final String SHIRO_URI = "http://localhost:8989/service/v1/check";



    public static final String GROUP_QUERY = "";
    public static final String GROUP_ALARM = "";
    public static final String GROUP_QUOTA = "";
    public static final String GROUP_INFO = "";

    public static final String NODE_QUERY = "";
    public static final String NODE_ALARM = "";
    public static final String NODE_QUOTA = "";
    public static final String NODE_INFO = "";

    public static final String CELL_QUERY = "";
    public static final String CELL_ALARM = "";
    public static final String CELL_QUOTA = "";
    public static final String CELL_INFO = "";

    // shiro
    public static final String PATH_DUMMY = "/service/v1/node";

    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";

    // result
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAILED";

    public static final String MSG_NO_DATA = "Data is empty.";
	public static final String MSG_NO_PERMISSION = "Permission denied.";
    public static final String MSG_EXPRESSION_INVALID = "Invalid expression.";

	public static final String MSG_DELETE_OK = "Delete ok.";
	public static final String MSG_DELETE_FAILED = "Delete failed.";
	public static final String MSG_ADD_OK = "Add ok.";
	public static final String MSG_ADD_FAILED = "Add failed.";
	public static final String MSG_UPDATE_OK = "Update ok.";
	public static final String MSG_UPDATE_FAILED = "Update failed.";
    public static final String MSG_CACEL_OK = "Cacel ok.";
    public static final String MSG_CACEL_FAILED = "Cacel failed.";
    public static final String MSG_TASK_CANCLE_OK = "task cancle ok";
    public static final String MSG_DOWNLOAD_FORMULAS_FAILED = "download formulas file failed";
    public static final String MSG_DOWNLOAD_FORMULAS_OK = "download formulas file ok";
    public static final String UPLOAD_RNC_INFOS_OK = "upload rnc infos ok";
    public static final String UPLOAD_RNC_INFOS_FAILED = "upload rnc infos failed";
    public static final String INVALID_VALUE_LEVEL = "-1";
    public static final String INVALID_VALUE_LOCATION = "-1";
    public static final String INVALID_VALUE_QUOTA = "-1";


    // task
    public static final String TASK_LOG_PATH_ORIGINAL = "/TASKLOG/XXX.zip";
    public static final String TASK_LOG_PATH_SCRIPT = "/SCRIPTLOG/XXX.log";

    public final static String SCANNER_SEND_HAND = "http://192.168.0.145:8080";

    public final static String SCANNER_SEND_UTIL = "/sran/service/net/scanner/suppliers/unicom";

    public final static String SCANNER_SEND_TYPE_WCDMA = "/generations/wcdma/send";

    public final static String SCANNER_SEND_TYPE_LTE = "/generations/lte/send";

    public final static String SCANNER_SEND_WCDMA = SCANNER_SEND_HAND + SCANNER_SEND_UTIL + SCANNER_SEND_TYPE_WCDMA;

    public final static String SCANNER_SEND_LTE = SCANNER_SEND_HAND + SCANNER_SEND_UTIL + SCANNER_SEND_TYPE_LTE;
}
