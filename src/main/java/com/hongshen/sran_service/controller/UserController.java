package com.hongshen.sran_service.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.impl.UserAgentService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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

    // Query all user lists and info (Step1)
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserListsAndInfo(@HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
//        Map<String, Object> map = null;
//            List<JSONObject> RoleList = userAgentService.getUserList();
//            List RoleList = userAgentService.getRoleList();
//            Map<String,Object> UserInfo = userAgentService.getUserInfo();
//            String name = (String) UserInfo.get("name");
//            List<JSONObject> lteAuth = userAgentService.getLteAuth(name);
//            List<JSONObject> wcdmaAuth = userAgentService.getWcdmaAuth(name);
//            if (RoleList != null || UserInfo != null || lteAuth != null || wcdmaAuth != null){
//                map.put("lteAuth",lteAuth);
//                map.put("wcdmaAuth",wcdmaAuth);
//                map.put("RoleList",RoleList);
//                map.put("UserInfo",UserInfo);
//
//                result.put("data", map);
//                result.put("result", Constants.SUCCESS);
//            } else {
//
//                result.put("result", Constants.FAIL);
//            }
        List<JSONObject> userList = userAgentService.getUserList();

        if (userList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else{

            for (JSONObject user : userList){
                List<JSONObject> authList = new ArrayList<>();
                String loginName = user.getString("loginName");


                // get lte authority info
                List<String> lteAuth = userAgentService.getLteAuth(loginName);

                if (!lteAuth.isEmpty()){
                    JSONObject resultLteAuth = new JSONObject();
                    resultLteAuth.put("generation", Constants.LTE);
                    resultLteAuth.put("list", lteAuth);

                    authList.add(resultLteAuth);
                }
                // get wcdma authority info
                List<String> wcdmaAuth = userAgentService.getWcdmaAuth(loginName);

                if (!wcdmaAuth.isEmpty()){
                    JSONObject resultWcdmaAuth = new JSONObject();
                    resultWcdmaAuth.put("generation", Constants.WCDMA);
                    resultWcdmaAuth.put("list", wcdmaAuth);

                    authList.add(resultWcdmaAuth);
                }


                user.put("authority",authList);
            }

            result.put("result", Constants.SUCCESS);
            result.put("data", userList);
        }

        return result;
    }

    // Add user (Step1)
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addUser (@HeaderParam("Auth-Token")String authToken,JSONObject param){

        JSONObject result = new JSONObject();
        Map<String, Object> map = null;

        int i = userAgentService.addUser(param);
        if(i != 0) {
            String loginName = param.getString("loginName");
            String authorityName = param.getString("role");

            int j = userAgentService.addLteUserAuthory(loginName, authorityName);

            int z = userAgentService.addWcdmaUserAuthory(loginName, authorityName);
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
    }

    // Update specified user
    @PUT
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateUser (@HeaderParam("Auth-Token")String authToken,JSONObject param,@PathParam("loginName") String loginNmae) {

        JSONObject result = new JSONObject();
        Map<String, Object> map = null;

        int i = userAgentService.updateUserInfo(param);
        if (i > 0) {
//            JSONObject jUser = jsonArray.getJSONObject(0).getJSONObject("user");
//            List<Object> list = (List<Object>) param.get("authority");
            JSONArray list = param.getJSONArray("authority");
//            JSONObject a = list.get(0);
            System.out.println(list);
            String loginName = param.getString("loginName");
//            for (int a = 0;i<list.size();i++){
//                System.out.println(list.get(a));
//            }
            for (Object list1 : list) {
                System.out.println(list1);

                JSONObject data = JSONObject.parseObject(list1.toString());

                String generation = data.getString("generation");
                System.out.println(generation);


                if (generation.equals("wcdma")) {
                    JSONArray roleList = data.getJSONArray("list");
                    List<String> paramList = new ArrayList<>();

                    for (int r = 0; r < roleList.size(); r ++) {

                        paramList.add(roleList.get(r).toString());
                    }
//                    1 delte 2 add
                    int j = userAgentService.delteWcdmaAuth(loginName);

                    if (paramList.size() > 0) {

                        int k = userAgentService.addWcdmaAuth(paramList, loginName);
                    }

                    System.out.println(roleList);
//                  int j = userAgentService.updateWcdmaAuth(roleList,loginName);
                } else if (generation.equals("lte")) {

                    JSONArray roleList = data.getJSONArray("list");

                    List<String> paramList = new ArrayList<>();

                    for (int r = 0; r < roleList.size(); r ++) {

                        paramList.add(roleList.get(r).toString());
                    }

                    int j = userAgentService.delteLteAuth(loginName);

                    if (paramList.size() > 0) {

                        int k = userAgentService.addLteAuth(paramList, loginName);
                    }
//                    int j = userAgentService.updateLteAuth(roleList,loginName);
//                }
                }

            }
        }

//        int i = userAgentService.updateUser(param);
//
//        if (i != 0){
////                String loginName = param.getString("loginName");
//            String authorityName = param.getString("role");
//            int j = userAgentService.updateLteUserAuthory(loginNmae, authorityName);
//
//            int z = userAgentService.updateWcdmaUserAuthory(loginNmae, authorityName);
//            if (j != 0 || z != 0){
//                result.put("result", Constants.SUCCESS);
//                result.put("msg", "update user info ok");
//            }else {
//                result.put("result",Constants.FAIL);
//                result.put("msg", "update user info fail");
//            }
//        } else {
//            result.put("result",Constants.FAIL);
//            result.put("msg", "add user info fail");
//        }

            return result;
        }

    // Delete specified user
    @DELETE
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteUser (@HeaderParam("Auth-Token")String authToken,@PathParam("loginName")String loginName){

        JSONObject result = new JSONObject();
        Map<String, Object> map = null;

        int i =  userAgentService.deleteUser(loginName);
        if (i != 0 ){
            int j =userAgentService.deleteLteUserAuthory(loginName);
            int z =userAgentService.deletewcdmaUserAuthory(loginName);
        }
        result.put("data", map);
        result.put("result", Constants.SUCCESS);

        return result;
    }

    // Query all role
    @GET
    @Path("/users/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getRoleAll(@HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        Map<String, Object> map = null;

        List RoleAll = userAgentService.getRoleList();
//            Map<String,Object> UserInfo = userAgentService.getUserInfo();

        if (RoleAll != null){
            map.put("RoleList",RoleAll);
//                map.put("UserInfo",UserInfo);
            result.put("data", map);
            result.put("result", Constants.SUCCESS);
        } else {

            result.put("result", Constants.FAIL);
        }

        return result;
    }
}
