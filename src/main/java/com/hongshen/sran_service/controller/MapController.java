package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/mapinfo")
public class MapController extends BaseController{

    @Autowired
    private NetObjFactory objFactory;

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getGroupList(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> groupList = obj.getElementInfoService().getGroupList();

        if(groupList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else{
            List<JSONObject> resultList = new ArrayList<>();

            for (JSONObject group : groupList){

                JSONObject resultOne = new JSONObject();
                Double[] scopeinit = {};
                resultOne.put("scope",scopeinit);
                resultOne.put("latitude",Constants.INVALID_VALUE_LOCATION);
                resultOne.put("longitude",Constants.INVALID_VALUE_LOCATION);
                resultOne.put("infos",null);
                resultOne.put("level",Constants.INVALID_VALUE_LEVEL);
                resultOne.put("favorite",false);

                // name
                String groupName = group.getString("groupName");
                resultOne.put("name",groupName);

                if (groupName != "") {

                    // longitude \ latitude and scope
                    List<JSONObject> nodeList =  obj.getElementInfoService().getNodeListByGroup(groupName);
                    List<Double[]> list = new ArrayList<>();

                    if (nodeList.size() != 0) {
                        for (JSONObject node : nodeList) {

                            Double latitude = node.getDouble("latitude");
                            Double longitude = node.getDouble("longitude");

                            if(latitude!= null && longitude!= null){
                                Double[] doubles ={latitude,longitude};
                                list.add(doubles);
                            }else{
                                Double[] doubles ={Double.parseDouble(Constants.INVALID_VALUE_LOCATION),Double.parseDouble(Constants.INVALID_VALUE_LOCATION)};
                                list.add(doubles);
                            }
                        }
                    }
                    resultOne.putAll(LatitudeAndLongitude(list));

                    // group info
                    JSONObject groupInfo = obj.getElementInfoService().getGroupInfo(groupName);
                    resultOne.put("infos",groupInfo);

                    // level
                    JSONObject level = obj.getQuotaService().getGroupLevel(groupName);
                    if(level != null && level.getIntValue("level") != -1){
                        resultOne.put("level", level);
                    }else{
                        resultOne.put("level",Constants.INVALID_VALUE_LEVEL);
                    }

                    //favorite TODO

                }
                resultList.add(resultOne);
            }
            result.put("result", Constants.SUCCESS);
            result.put("data", resultList);
        }
        return result;
    }

    static JSONObject Scopes(Double latitude,Double longitude,int num){
        DecimalFormat df   = new DecimalFormat("######0.0000");
        String la = df.format(latitude/num);
        String lo = df.format(longitude/num);
        JSONObject j = new JSONObject();
        if(latitude==0.0||longitude==0.0||num==0){
            j.put("latitude",0);
            j.put("longitude",0);
        }else{
            j.put("latitude",la);
            j.put("longitude",lo);
        }
        return j;
    }

    static JSONObject LatitudeAndLongitude(List<Double[]>  list){
        Double t1 = 0.0;
        Double l1 = 0.0;
        Double t2 = 0.0;
        Double l2 = 0.0;
        Double t3 = 0.0;
        Double l3 = 0.0;
        Double t4 = 0.0;
        Double l4 = 0.0;

        JSONObject result = new JSONObject();
        List listJson = new ArrayList();
        JSONObject json = new JSONObject();
        if(!list.isEmpty()){
            if(list.size() > 3){

                for (int i=0;i<list.size(); i++){
                    //MAXlatitude
                    if(list.get(i)[0]>l1){
                        l1 = list.get(i)[0];
                        t1 = list.get(i)[1];
                    }
                    //MAXlongitude
                    if(list.get(i)[1]>t2){
                        l2 = list.get(i)[0];
                        t2 = list.get(i)[1];
                        System.out.println(i);
                    }
                    //MINlatitude
                    if(list.get(i)[0]<=l3||l3==0.0){
                        l3 = list.get(i)[0];
                        t3 = list.get(i)[1];
                    }
                    //MINlongitude
                    if(list.get(i)[1]<=t4||t4==0.0){
                        l4 = list.get(i)[0];
                        t4 = list.get(i)[1];
                    }

                }
                Double[] d1 = {l1,t1};
                Double[] d2 = {l2,t2};
                Double[] d3 = {l3,t3};
                Double[] d4 = {l4,t4};
                listJson.add(d1);
                listJson.add(d2);
                listJson.add(d3);
                listJson.add(d4);
                Double latitude = l1+l2+l3+l4;
                Double longitude = t1+t2+t3+t4;
                json = Scopes(latitude,longitude,4);
            }else if(list.size()==3){
                listJson.add(list);

            }else if(list.size() < 3){
                listJson.add(2);
            }
        }

        result.put("scope",listJson);
        result.putAll(json);
        System.out.println(result);
        return result;
    };

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getNodeList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> nodeList = obj.getElementInfoService().getNodeListByGroup(groupName);
        List list = new ArrayList();

        if(!nodeList.isEmpty()){

            for(JSONObject node : nodeList){

                String nodeName = node.getString("name");

                if (nodeName != null || nodeName != ""){
                    JSONObject json = new JSONObject();
                    JSONObject level = obj.getQuotaService().getNodeLevel(nodeName);

                    if(level != null && level.getIntValue("level") != -1){
                        json.put("level", level);
                    }else{
                        json.put("level",Constants.INVALID_VALUE_LEVEL);
                    }
                    json.putAll(node);
                    list.add(json);
                }

            }
            result.put("data",list);
            result.put("result", Constants.SUCCESS);
        }else{
            result.put("result",Constants.FAIL);
        }
        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getCellList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @PathParam("nodeName")String nodeName,
                                   @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> cellList = obj.getElementInfoService().getCellListByNode(nodeName);

        if(!cellList.isEmpty()) {

            List list = new ArrayList();

            for (JSONObject cell : cellList){

                JSONObject json = new JSONObject();
                String cellName = cell.getString("name");

                if (cellName != null || cellName != "") {

                    JSONObject level = obj.getQuotaService().getCellLevel(cellName);
                    JSONObject NodelatAndlong = obj.getElementInfoService().getNodelatitudeAndlongitude(nodeName);

                    if (level != null && level.getIntValue("level") != -1) {
                        json.put("level", level);
                    } else {
                        json.put("level", Constants.INVALID_VALUE_LEVEL);
                    }
                    json.putAll(NodelatAndlong);
                    json.putAll(cell);
                    list.add(json);
                }
            }
            result.put("data",list);
            result.put("result", Constants.SUCCESS);
        }else{
            result.put("result", Constants.FAIL);
        }

        return  result;
    }
}
