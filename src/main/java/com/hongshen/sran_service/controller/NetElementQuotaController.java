package com.hongshen.sran_service.controller;



import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.*;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    private AlarmService alarmService;

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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroups(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                @HeaderParam("Auth-Token")String authToken, @HeaderParam("Login-Name")String name){

        JSONObject result = new JSONObject();

        String time = "";

        String url = Constants.GROUP_QUERY;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            GroupService groupService = base.getGroupService();

            List<JSONObject> resultList = groupService.getGroups(name, time);

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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodes(@PathParam("supplier") String supplier, @PathParam("generation")String generation,
                               @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken,
                               @HeaderParam("Login-Name")String name){

        JSONObject result = new JSONObject();

        String time = "";

        String url = Constants.NODE_QUERY;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            NodeService nodeService = base.getNodeService();

            List<JSONObject> resultList = nodeService.getNodes(name, groupname, time);

            if (!resultList.isEmpty()) {

                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }
        } else {

            return result;
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCells(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                               @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                               @HeaderParam("Auth-Token")String authToken, @HeaderParam("Login-Name")String name){

        JSONObject result = new JSONObject();

        String time = "";

        String url = Constants.CELL_QUERY;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            CellService cellService = base.getCellService();

            List<JSONObject> resultList = cellService.getCells(name, nodename, time);

            if (!resultList.isEmpty()) {

                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }
        } else {

            return result;
        }

        return result;
    }

    @GET
    @Path("/net/alarm/nets/nodes/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAllAlarms (@HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        String url = Constants.GROUP_ALARM;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            List<JSONObject> resultList = alarmService.getAllAlarmSerice();

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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.GROUP_ALARM;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            GroupService groupService = base.getGroupService();

            List<JSONObject> resultList = groupService.getGroupAlarmByGroupName(groupname);

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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupName, @PathParam("nodename")String nodeName,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.NODE_ALARM;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            NodeService nodeService = base.getNodeService();

            JSONObject resultNode = nodeService.getNodeAlarmByNodeName(nodeName);

            if (!resultNode.isEmpty()){

                result.put("data", resultNode);
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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellAlarm(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupName, @PathParam("nodename")String nodeName,
                                   @PathParam("cellname")String cellName, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.CELL_ALARM;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            CellService cellService = base.getCellService();

            JSONObject resultNode = cellService.getCellAlarmByCellName(cellName);

            if (!resultNode.isEmpty()){

                result.put("data", resultNode);
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
//    Network element topology
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/roomlist")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getElementTopology(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                         @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            ElementTopology elementTopology = obj.getElementTopologyr();
            Map<String,Object> GroupWcdma =elementTopology.getElementTopologyr();

            if (!GroupWcdma.isEmpty()){

                result.put("data", GroupWcdma);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }
//    Query all alarms
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAlarmLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                         @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            AlarmLibrary alarmLibrary = obj.getAlarmLibrary();
            Map<String,Object> alarm_Library =alarmLibrary.getAlarmLibrary();

            if (!alarm_Library.isEmpty()){

                result.put("data", alarm_Library);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }
//    Query specified alarm
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmNameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                          @PathParam("alarmNameId")String alarmNameId,@HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            AlarmLibrary alarmLibrary = obj.getSpecifiedLibrary();
            Map<String,Object> specifiedLibrary =alarmLibrary.getSpecifiedLibrary(alarmNameId);

            if (!specifiedLibrary.isEmpty()){

                result.put("data", specifiedLibrary);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }
//update specified alarm
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmNameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                             @PathParam("alarmNameId")String alarmNameId, @HeaderParam("Auth-Token")String authToken,
                                             JSONObject param){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;
        String name = param.getString("name");
        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            AlarmLibrary alarmLibrary = obj.updateSpecifiedLibrary();
            alarmLibrary.updateSpecifiedLibrary(alarmNameId,name);

//            if (!specifiedLibrary.isEmpty()){
//
//                result.put("data", specifiedLibrary);
//                result.put("status", Constants.SUCCESS);
//            } else {
//
//                result.put("status", Constants.FAIL);
//            }
            result.put("status", Constants.SUCCESS);
            return result;
        } else {

            return result;
        }
    }
//add specified alarm
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                             @HeaderParam("Auth-Token")String authToken,JSONObject param){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;
        String alarm_name_id = param.getString("alarm_name_id");
        String alarm_name = param.getString("alarm_name");
        String alarm_meaning = param.getString("alarm_meaning");
        String alarm_level_id = param.getString("alarm_level_id");
        String alarm_scope = param.getString("alarm_scope");
        String recommend = param.getString("recommend");


//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            AlarmLibrary alarmLibrary = obj.addSpecifiedLibrary();
            alarmLibrary.addSpecifiedLibrary(alarm_name_id,alarm_name,alarm_meaning,alarm_level_id,alarm_scope,recommend);

//            if (!specifiedLibrary.isEmpty()){
//
//                result.put("data", specifiedLibrary);
//                result.put("status", Constants.SUCCESS);
//            } else {
//
//                result.put("status", Constants.FAIL);
//            }
            result.put("status", Constants.SUCCESS);
//            return result;
//        } else {

            return result;
//        }
    }
}
