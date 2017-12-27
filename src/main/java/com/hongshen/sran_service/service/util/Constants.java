package com.hongshen.sran_service.service.util;

/**
 * Created by poplar on 17-10-27.
 */
public class Constants {

    // server config

    public final static String SRAN_WEB_URI = "http://42.202.149.82:8080";
    public final static String SRAN_SERVICE_URI = "http://42.202.149.82:8082";
    public static final String SRAN_SHIRO_URI = "http://42.202.149.82:8299";
//    public final static String SRAN_WEB_URI = "http://192.168.0.117:8080";//42.202.149.82
//    public final static String SRAN_SERVICE_URI = "http://192.168.0.117:8082";
//    public static final String SRAN_SHIRO_URI = "http://42.202.149.82:8989";
    public static final String SRAN_ROOT_PATH = "/root/apache-tomcat-8.5.16/webapps/sran/";
    public static final String MOSHELL_ROOT_PATH = "/root/sran/moshell/";

    // request

    public static final String WCDMA = "wcdma";//3G  
    public static final String LTE = "lte";//4G   
    public static final String UNICOM = "unicom";//中国联通

    // level

    public static final String LEVEL_GROUP = "groups";
    public static final String LEVEL_NODE = "nodes";
    public static final String LEVEL_CELL = "cells";

    // file
    public static final String TEMPLATE_ROOT_PATH = SRAN_WEB_URI + "/sran/templates/";
    public static final String TEMPLATE_FILE_NAME_END = "_template.xlsx";
    public static final String TEMPLATE_FORMULA = "formula_list";
    public static final String TEMPLATE_PROTECT = "protect_net_list";
    public static final String TEMPLATE_RNC_INFO = "rnc_info_list";
    public static final String TEMPLATE_STATION_NAME = "station_name_list";
    public static final String TEMPLATE_COUNTER = "counter_list"; // TODO
    public static final String TEMPLATE_ALARM_LIB = "alarm_lib_list"; // TODO

    // method

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
    public static final String MSG_DOWNLOAD_FAILED = "Download file failed.";
    public static final String MSG_DOWNLOAD_OK = "Download file ok.";
    public static final String MSG_UPLOAD_OK = "Upload infos ok.";
    public static final String MSG_UPLOAD_FAILED = "Upload infos failed.";

    public static final Integer INVALID_VALUE_LEVEL = -1;
    public static final Integer INVALID_ALARM_LEVEL = 0;
    public static final String INVALID_VALUE_LOCATION = "-1";
    public static final Integer INVALID_VALUE_QUOTA = -1;

    // task

    // /root/apache-tomcat-8.5.16/webapps/sran/task
    public static final String TASK_ROOT_PATH = SRAN_ROOT_PATH + "task/";
    // /sran/task/loginName/createTime/site/sitelist
    public static final String TASK_DIR_SITE = "site";
    public static final String TASK_FILE_SITE = "sitelist";
    // /sran/task/loginName/createTime/cmd/cmdlist
    public static final String TASK_DIR_CMD = "cmd";
    public static final String TASK_FILE_CMD = "cmdlist";
    // /sran/task/loginName/createTime/log/log.zip
    public static final String TASK_DIR_LOG = "log";
    public static final String TASK_FILE_LOG = "log.tar";
    // /sran/task/loginName/createTime/analysislog/analysis.log
    public static final String TASK_DIR_ANALYSIS_LOG = "analysislog";
    public static final String TASK_FILE_ANALYSIS_LOG = "analysis.log";

    // scanner

    public final static String SCANNER_SEND_UTIL = "/sran/service/net/scanner/suppliers/unicom";
    public final static String SCANNER_SEND_TYPE_WCDMA = "/generations/wcdma/send";
    public final static String SCANNER_SEND_TYPE_LTE = "/generations/lte/send";
    public final static String SCANNER_SEND_WCDMA = SRAN_SERVICE_URI + SCANNER_SEND_UTIL + SCANNER_SEND_TYPE_WCDMA;
    public final static String SCANNER_SEND_LTE = SRAN_SERVICE_URI + SCANNER_SEND_UTIL + SCANNER_SEND_TYPE_LTE;

    // shiro

    public static final String SHIRO_URI = SRAN_SHIRO_URI + "/service/v1/check";
}
