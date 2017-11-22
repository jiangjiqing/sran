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
    public JSONObject  getGroupList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                         @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        /*if (check(url, method, authToken)) {*/
            NetObjBase obj = objFactory.getNetObj(supplier, generation);
            List<JSONObject> grupList = obj.getElementInfoService().getGroupList();
            List<JSONObject> List =new ArrayList<>();
            for (int i = 0;i<grupList.size();i++){
                JSONObject result1 = new JSONObject();
                List<Double[]> list = new ArrayList<>();
                if (grupList.get(i).getString("group_name") != null) {
                    String groupName= grupList.get(i).getString("group_name");
                    List<JSONObject> nodeList =  obj.getElementInfoService().getNodeList(groupName);
                    JSONObject group = obj.getElementInfoService().getGroupByName(groupName);//TODO
                    Double latitude = 0.0;
                    Double longitude = 0.0;
                    int num=0;
                    if (nodeList.size() != 0) {
                        for (int j = 0; j < nodeList.size(); j++) {
                            num++;
                            if(nodeList.get(j).getDouble("latitude")!= 0.0 && nodeList.get(j).getDouble("longitude")!= 0.0){
                                Double[] doubles ={nodeList.get(j).getDouble("latitude"),nodeList.get(j).getDouble("longitude")};
                                list.add(doubles);
                                latitude = latitude  + nodeList.get(j).getDouble("latitude");
                                longitude = longitude + nodeList.get(j).getDouble("longitude");
                            }
                        }
                    }
                    JSONObject json = Scopes(latitude,longitude,num);
                    if(group==null){
                       result1.put("level",-1);
                    }else{
                        result1.putAll(group);
                    }
                    result1.put("name",groupName);
                    result1.put("scope",list);
                    result1.putAll(json);
                    List.add(result1);
                }
            }
            result.put("data", List);
            result.put("result", Constants.SUCCESS);
            return result;
       /* }else{
                result.put("result", Constants.FAIL);
			  result.put("msg", Constants.MSG_NO_PERMISSION);
           return result;
        }*/
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
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getNodeList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken,@PathParam("groupName")String groupName) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> nodeList = obj.getElementInfoService().getNodeList(groupName);
        JSONObject json = new JSONObject();
        List list = new ArrayList();
        if(!nodeList.isEmpty()){
            for(int i=0;i<nodeList.size();i++){
                JSONObject nodeLevel = obj.getQuotaService().getNodeLevel(nodeList.get(i).getString("name"));
                if(!nodeLevel.isEmpty()){
                    json.put("level",nodeLevel.getString("level"));
                    json.putAll(nodeList.get(i));
                    list.add(json);
                }else{

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
    public JSONObject  getCellList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,@PathParam("groupName")String groupName,
                                   @PathParam("nodeName")String nodeName) {
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> cellList = obj.getElementInfoService().getCellList(nodeName);

        return  null;
    }
}
