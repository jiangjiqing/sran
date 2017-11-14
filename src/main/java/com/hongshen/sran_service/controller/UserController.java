package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.impl.UserAgentService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
public class UserController extends BaseController{
    @Autowired
    private UserAgentService userAgentService;
    //   Query all user lists and info     (Step1)
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserListsAndInfo(@HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {
            List RoleList = userAgentService.getRoleList();
            Map<String,Object> UserInfo = userAgentService.getUserInfo();

            if (RoleList != null || UserInfo != null ){
                map.put("RoleList",RoleList);
                map.put("UserInfo",UserInfo);
                result.put("data", map);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }
    //    add user            (Step1)
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addUser (@HeaderParam("Auth-Token")String authToken,JSONObject param){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {
            UserAgentService.addUser(param);
//
//            if (unicomAuthorityWcdma != null || unicomUserAuthorityWcdma != null ){
//                map.put("unicomAuthorityWcdma",unicomAuthorityWcdma);
//                map.put("unicomUserAuthorityWcdma",unicomUserAuthorityWcdma);
            result.put("data", map);
            result.put("status", Constants.SUCCESS);
//            } else {
//
//                result.put("status", Constants.FAIL);
//            }
//
            return result;
        } else {

            return result;
        }
    }
}
