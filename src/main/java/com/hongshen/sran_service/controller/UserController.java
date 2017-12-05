package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.impl.UserAgentService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Path("/sran/service/user")
@Controller
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
//        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {
            List RoleList = userAgentService.getRoleList();
            Map<String,Object> UserInfo = userAgentService.getUserInfo();
            String name = (String) UserInfo.get("name");
            List<JSONObject> lteAuth = userAgentService.getLteAuth(name);
            List<JSONObject> wcdmaAuth = userAgentService.getWcdmaAuth(name);
            if (RoleList != null || UserInfo != null || lteAuth != null || wcdmaAuth != null){
                map.put("lteAuth",lteAuth);
                map.put("wcdmaAuth",wcdmaAuth);
                map.put("RoleList",RoleList);
                map.put("UserInfo",UserInfo);

                result.put("data", map);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
//        } else {
//
//            return result;
//        }
    }
    //    add user            (Step1)
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addUser (@HeaderParam("Auth-Token")String authToken,JSONObject param){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
//        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {
            int i = UserAgentService.addUser(param);
            if(i != 0) {
                String loginName = param.getString("loginName");
                String authorityName = param.getString("role");

                int j = UserAgentService.addLteUserAuthory(loginName, authorityName);

                int z = UserAgentService.addWcdmaUserAuthory(loginName, authorityName);
                if (j != 0 || z != 0){
                    result.put("result", Constants.SUCCESS);
                    result.put("msg", "add user info ok");
                }else {
                    result.put("result",Constants.FAIL);
                    result.put("msg", "add user info fail");
                }
            }else {
                result.put("result",Constants.FAIL);
                result.put("msg", "add user info fail");
            }
            return result;
//        } else {
//
//            return result;
//        }
    }
//    update specified user
    @POST
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateUser (@HeaderParam("Auth-Token")String authToken,JSONObject param,@PathParam("loginName") String loginNmae){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
//        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_POST;

//        if (check(url, method, authToken)) {
            int i = UserAgentService.updateUser(param);

            if (i != 0){
//                String loginName = param.getString("loginName");
                String authorityName = param.getString("role");
                int j = UserAgentService.updateLteUserAuthory(loginNmae, authorityName);

                int z = UserAgentService.updateWcdmaUserAuthory(loginNmae, authorityName);
                if (j != 0 || z != 0){
                    result.put("result", Constants.SUCCESS);
                    result.put("msg", "update user info ok");
                }else {
                    result.put("result",Constants.FAIL);
                    result.put("msg", "update user info fail");
                }
            } else {
                result.put("result",Constants.FAIL);
                result.put("msg", "add user info fail");
            }

            return result;
//        } else {
//
//            return result;
//        }
    }
//    delete specified user
    @DELETE
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteUser (@HeaderParam("Auth-Token")String authToken,@PathParam("loginName")String loginName){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
//        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_POST;

//        if (check(url, method, authToken)) {
            int i =  UserAgentService.deleteUser(loginName);
            if (i != 0 ){
                int j =UserAgentService.deleteLteUserAuthory(loginName);
                int z =UserAgentService.deletewcdmaUserAuthory(loginName);
            }
            result.put("data", map);
            result.put("status", Constants.SUCCESS);

            return result;
//        } else {
//
//            return result;
//        }
    }
//   Query all role
    @GET
    @Path("/users/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getRoleAll(@HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
//        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {
            List RoleAll = userAgentService.getRoleList();
//            Map<String,Object> UserInfo = userAgentService.getUserInfo();

            if (RoleAll != null){
                map.put("RoleList",RoleAll);
//                map.put("UserInfo",UserInfo);
                result.put("data", map);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
//        } else {
//
//            return result;
//        }
    }


}
