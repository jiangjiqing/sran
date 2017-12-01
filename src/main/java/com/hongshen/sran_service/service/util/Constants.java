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

	public static final String MSG_DELETE_OK = "delete_ok";
	public static final String MSG_DELETE_FAILED = "delete_failed";
	public static final String MSG_ADD_OK = "add_ok";
	public static final String MSG_ADD_FAILED = "add_failed";
	public static final String MSG_UPDATE_OK = "update_ok";
	public static final String MSG_UPDATE_FAILED = "update_failed";

    public static final String INVALID_VUALUE_LEVEL = "-1";
    public static final String INVALID_LOCATION_LEVEL = "-1";
}
