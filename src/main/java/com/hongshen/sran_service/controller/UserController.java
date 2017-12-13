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

    // Query user lists (Step1)
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserListsAndInfo(@HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        List<JSONObject> userList = userAgentService.getUserList();//TODO shiro

        if (userList == null || userList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else{

            for (JSONObject user : userList){
                List<JSONObject> authList = new ArrayList<>();
                String loginName = user.getString("loginName");

                // get lte authority info
                List<String> lteAuth = userAgentService.getLteAuth(loginName);

                if (lteAuth != null && !lteAuth.isEmpty()){

                    JSONObject resultLteAuth = new JSONObject();
                    resultLteAuth.put("generation", Constants.LTE);
                    resultLteAuth.put("list", lteAuth);

                    authList.add(resultLteAuth);
                }

                // get wcdma authority info
                List<String> wcdmaAuth = userAgentService.getWcdmaAuth(loginName);

                if (wcdmaAuth != null && !wcdmaAuth.isEmpty()){

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
    public JSONObject addUser (@HeaderParam("Auth-Token")String authToken,
                               JSONObject param){

        JSONObject result = new JSONObject();
        String msg = "";
        List<JSONObject> userList = userAgentService.getUserlistInfo(param);
        List<JSONObject> userRoleList = userAgentService.getUserRolelistInfo(param);
        if (userList == null && userRoleList == null){
            int i = userAgentService.addUserInfo(param);//TODO shiro
            int j = userAgentService.addUserRoleInfo(param);
            if(i > 0 && j > 0) {
                JSONArray list = param.getJSONArray("authority");
                for (Object l : list) {
                    JSONObject data = JSONObject.parseObject(l.toString());
                    String generation = data.getString("generation");
                    String loginName = param.getString("loginName");
                    if (generation.equals("wcdma")) {

                        JSONArray authList = data.getJSONArray("list");
                        List<String> paramList = new ArrayList<>();

                        for (int r = 0; r < authList.size(); r ++) {

                            paramList.add(authList.get(r).toString());
                        }
                        if (paramList.size() > 0) {

                            int k = userAgentService.addWcdmaAuth(paramList, loginName);
                            if (k <= 0){
                                msg += "add user authority info("+ generation +"-step2) failed.";
                            }
                        }

                    } else if (generation.equals("lte")) {

                        JSONArray authList = data.getJSONArray("list");
                        List<String> paramList = new ArrayList<>();

                        for (int r = 0; r < authList.size(); r ++) {

                            paramList.add(authList.get(r).toString());
                        }
                        if (paramList.size() > 0) {

                            int k = userAgentService.addLteAuth(paramList, loginName);
                            if (k <= 0){
                                msg += "add user authority info("+ generation +"-step2) failed.";
                            }
                        }
                    }
                }

            }else {
               msg += "Add user err.";

            }
        }else {
            msg += "Table is not null.";

        }
//        int i = userAgentService.addUserFromShiro(param);
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        }else{
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_FAILED + msg);
        }

        return result;
    }

    // Update specified user
    @PUT
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateUser (@PathParam("loginName") String loginName,
                                  @HeaderParam("Auth-Token")String authToken,
                                  JSONObject param){

        JSONObject result = new JSONObject();
        String msg = "";

        int i = userAgentService.updateUser(param);//TODO shiro
        if (i <= 0){
            msg += "Update user info failed.";

        }else{
            JSONArray list = param.getJSONArray("authority");

            for (Object l : list) {

                JSONObject data = JSONObject.parseObject(l.toString());
                String generation = data.getString("generation");

                if (generation.equals("wcdma")) {

                    JSONArray authList = data.getJSONArray("list");
                    List<String> paramList = new ArrayList<>();

                    for (int r = 0; r < authList.size(); r ++) {

                        paramList.add(authList.get(r).toString());
                    }
                    // step1 delete, step2 add
                    int j = userAgentService.delteWcdmaAuth(loginName);

                    if (paramList.size() > 0) {

                        int k = userAgentService.addWcdmaAuth(paramList, loginName);
                        if (k <= 0){
                            msg += "Update user authority info("+ generation +"-step2) failed.";
                        }
                    }

                } else if (generation.equals("lte")) {

                    JSONArray authList = data.getJSONArray("list");
                    List<String> paramList = new ArrayList<>();

                    for (int r = 0; r < authList.size(); r++) {

                        paramList.add(authList.get(r).toString());
                    }
                    // step1 delete, step2 add
                    int j = userAgentService.delteLteAuth(loginName);

                    if (paramList.size() > 0) {

                        int k = userAgentService.addLteAuth(paramList, loginName);
                        if (k <= 0){
                            msg += "Update user authority info("+ generation +"-step2) failed.";
                        }
                    }
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_OK);

        }else{
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_FAILED + msg);
        }

        return result;
    }

    // Delete specified user
    @DELETE
    @Path("/users/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteUser (@HeaderParam("Auth-Token")String authToken,@PathParam("loginName")String loginName){

        JSONObject result = new JSONObject();
        String msg = "";

        int i = userAgentService.deleteUser(loginName); //TODO shiro
//        int i = userAgentService.deleteUserFromShiro(loginName);
        if (i <= 0){
            msg += "Delete user info failed.";

        }else {
            int j = userAgentService.deleteLteUserAuthory(loginName);
            if (j <= 0){
                msg += "Delete user authory(lte) faild.";
            }

            int k =userAgentService.deletewcdmaUserAuthory(loginName);
            if (k <= 0){
                msg += "Delete user authory(wcdma) faild.";
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);

        }else{
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_FAILED + msg);
        }

        return result;
    }

    // Query all role
    @GET
    @Path("/users/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getRoleAll(@HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        List<JSONObject> roleList = userAgentService.getRoleList(); //TODO shiro
//        List<JSONObject> roleList = userAgentService.getRoleListFromShiro();

        if (roleList == null || roleList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else{
            result.put("result", Constants.SUCCESS);
            result.put("data", roleList);
        }

        return result;
    }
}
