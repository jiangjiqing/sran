package com.hongshen.sran_service.controller;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.AuthorityService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;

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

    @Autowired
    private Httpclient httpclient;

    //   Query All authority
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/authorities")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAuthorityList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                       @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        List<JSONObject> authorityList = obj.getAuthorityService().getAuthorityList();

        if (!authorityList.isEmpty()){

            result.put("data", authorityList);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_NO_DATA);
            result.put("result", Constants.FAIL);

        }
        return result;

        //        } else {
//
//            return result;
//        }
    }
    //    update specified authority
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/authorities/{authorityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateAuthority(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                      @PathParam("authorityName")String authorityName,@HeaderParam("Auth-Token")String authToken,
                                      JSONObject param) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        int i = obj.getAuthorityService().updateAuthority(authorityName,param);

        if (i > 0){

            result.put("data", Constants.MSG_UPDATE_OK);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_UPDATE_FAILED);
            result.put("result", Constants.FAIL);

        }
        return result;

        //        } else {
//
//            return result;
//        }
    }
    //   Add authority
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/authorities")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addAuthority(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken, JSONObject param) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        int i = obj.getAuthorityService().addAuthority(param);

        if (i > 0){

            result.put("data", Constants.MSG_ADD_OK);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_ADD_FAILED);
            result.put("result", Constants.FAIL);

        }
        return result;

        //        } else {
//
//            return result;
//        }
    }

//    Delete authority
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/authorities/{authorityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteAuthority(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                      @PathParam("authorityName")String authorityName,@HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        int i = obj.getAuthorityService().deleteAuthority(authorityName);

        if (i > 0){

            result.put("data", Constants.MSG_DELETE_OK);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_DELETE_FAILED);
            result.put("result", Constants.FAIL);

        }
        return result;

        //        } else {
//
//            return result;
//        }
    }
}
