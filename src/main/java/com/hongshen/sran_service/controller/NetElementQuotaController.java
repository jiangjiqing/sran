package com.hongshen.sran_service.controller;



import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by poplar on 17-10-26.
 */


@Path("/sran/service")
public class NetElementQuotaController extends BaseController {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

    @RequestMapping(value = "/group/quota")
    public Map getGroupQuotaInfo (){
        boolean sign = false;
        Map jsonResult = new HashMap();
        String supplier = "unicom";
        String generation  = "lte";
        Role role = new Role();
        role.setRoleId(1);
        int id=role.getRoleId();
        //

        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        DataProviderBase dataProvider = obj.getDataProvider();
        Role role1 = dataProvider.getGroupQuotaInfo(id);

        jsonResult.put("message","success");
        jsonResult.put("data", role1);
        jsonResult.put("sign", sign);
        return jsonResult;
    }
    @RequestMapping(value = "/v1/dc-map")
    public Map test() {
        boolean sign = false;
        String result = "";
        Map jsonResult = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();
        String authResult = null;
        Map<String,Object> role2 = new HashMap<String,Object>();

        String supplier = "unicom";
        String generation  = "lte";
        Role role = new Role();
        Role role1 = null;
        role.setRoleId(1);
        int id=role.getRoleId();
        //Shiro authorization
        String checkUrl = "/service/v1/dc-map";
        String checkMethod = "GET";
        String url = Constants.SHIRO_URI+"?checkUrl="+checkUrl+"&checkMethod="+checkMethod;
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiJlY2FwIiwiaXNzIjoiRXJpY3Nzb24iLCJ1c2VybmFtZSI6InBldGVyIn0.IbXgDC975i4M4D3AVeeaWFLC3YD3zY9-6XiNbiocxNo";
        try {
            authResult = httpclient.httpclient(url,token);
            data.put("BB",authResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(authResult.equals("FAILED")){
            System.out.println("555");
            role1 = null;
            sign = false;
            result = "failed";
        }else {

            //
            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            DataProviderBase dataProvider = obj.getDataProvider();
            role1 = dataProvider.getGroupQuotaInfo(id);

            role2.put("id",role1.getRoleId());
            role2.put("name",role1.getRoleName());
            sign = true;
            result= "success";
        }
        jsonResult.put("data",role2);
        jsonResult.put("result", result);
        jsonResult.put("status", sign);

        return jsonResult;
    }
    @RequestMapping(value = "/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes")
    public Map getProtect(@PathVariable String supplier, @PathVariable String generation, @PathVariable String groupname){

        boolean sign = false;
        String result = "";
        Map jsonResult = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();
        String authResult = null;
        Map<String,Object> Protect = new HashMap<String,Object>();
        Map<Object,String> map = new HashMap<Object,String>();

        String checkUrl = "/service/v1/node";
        String checkMethod = "GET";
        String url = Constants.SHIRO_URI+"?checkUrl="+checkUrl+"&checkMethod="+checkMethod;
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiJlY2FwIiwiaXNzIjoiRXJpY3Nzb24iLCJ1c2VybmFtZSI6InBldGVyIn0.IbXgDC975i4M4D3AVeeaWFLC3YD3zY9-6XiNbiocxNo";
        try {
            authResult = httpclient.httpclient(url,token);
            data.put("BB",authResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(authResult.equals("FAILED")){
            System.out.println("555");
            sign = false;
            result = "failed";
        }else {
            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            DataProviderBase dataProvider = obj.getDataProvider();
            Protect =dataProvider.getProtect();
            Iterator<Map.Entry<String, Object>> iter = Protect.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, Object> entry = iter.next();
                String value = (String) entry.getValue();
                map.put("nodeName",value);
            }
            sign = true;
            result= "success";
        }

        jsonResult.put("data",map);
        jsonResult.put("result", result);
        jsonResult.put("status", sign);

        return jsonResult;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroups(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.GROUP_QUERY;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier,generation);

            GroupService groupService = base.getGroupService();

            List<JSONObject> resultList = groupService.getGroups(authToken);

            if (!resultList.isEmpty()){

                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodes(@PathParam("supplier") String supplier, @PathParam("generation")String generation,
                               @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCells(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                               @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                               @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @PathParam("cellname")String cellname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @PathParam("cellname")String cellname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/infos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupInfo(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/infos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeInfo(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                  @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                  @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/infos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellInfo(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                  @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                  @PathParam("cellname")String cellname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();


        return result;
    }
    //Re guarantee network element
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/protectednets")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getZBElement(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            DataProviderBase dataProvider = obj.getDataProvider();
            Map<String,Object> protect =dataProvider.getProtect();

            if (!protect.isEmpty()){

                result.put("data", protect);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }

    }

}
