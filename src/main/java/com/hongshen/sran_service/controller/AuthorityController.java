package com.hongshen.sran_service.controller;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/authority")
public class AuthorityController {
    @Autowired
    private NetObjFactory objFactory;

    // Query authority list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/authorities")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAuthorityList(@PathParam("supplier")String supplier,
                                       @PathParam("generation")String generation,
                                       @HeaderParam("Auth-Token")String authToken) {

        String msg ="";
        List<JSONObject> authorityList = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            authorityList = obj.getAuthorityService().getAuthorityList();

            if (authorityList == null || authorityList.isEmpty()) {

                msg += "AuthorityList is null.";
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", authorityList);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }

    // Update specified authority
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/authorities/{authorityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateAuthority(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @PathParam("authorityName")String authorityName,
                                      @HeaderParam("Auth-Token")String authToken,
                                      JSONObject param) {
        String msg ="";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            if (authorityName == null){
                msg += "AuthorityName is NULL.";
            }else {
                if (param == null){
                    msg +="Param is null.";
                }else {
                    try {
                        int i = obj.getAuthorityService().updateAuthority(authorityName, param);
//        NetObjBase obj = objFactory.getNetObj(supplier, generation);
//        int i = obj.getAuthorityService().updateAuthority(authorityName,param);

                        if (i <= 0) {
                            msg +="UpdateAuthority Failed";
                        }
                    }catch (Exception e){
                        msg += "Parameters has error:" + e.getMessage();
                    }
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_OK);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED + msg);
        }

        return result;
    }

    // Add authority
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/authorities")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addAuthority(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,
                                   JSONObject param) {
        String msg ="";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            if (param == null){
                msg += "Param is null.";
            }else {
                JSONObject authority = obj.getAuthorityService().getAuthByName(param);
                if (authority == null) {
                    try {
                        int i = obj.getAuthorityService().addAuthority(param);

                        if (i <= 0) {
                            msg += "AddAuthority Failed.";
                        }
                    }catch (Exception e){
                        msg += "Parameters has error:" + e.getMessage();
                    }

                } else {
                    msg +="AuthorityName exity.";
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + msg);
        }
        return result;
    }

    // Delete authority
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/authorities/{authorityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteAuthority(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @PathParam("authorityName")String authorityName,
                                      @HeaderParam("Auth-Token")String authToken) {

        String msg ="";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            if (authorityName == null){
                msg +="AuthorityName is null.";
            }else {
                try {
                    int i = obj.getAuthorityService().deleteAuthority(authorityName);

                    if (i <= 0) {
                        msg +="DeleteAuthority Failed";
                    }
                }catch (Exception e){
                    msg += "Parameters has error:" + e.getMessage();
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DELETE_FAILED + msg);
        }

        return result;
    }
}
