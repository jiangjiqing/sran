package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
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
@Path("/sran/service/net/favorite")
public class FavoriteContrller {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/favorites/mapinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProtectionList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                        @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        List<JSONObject> favoriteList =  obj.getElementInfoService().getFavoriteList();

        for (int i=0 ; i <favoriteList.size();i++) {
            if (favoriteList.get(i).getString("nodeName") !=null) {
                String nodeName = String.valueOf(favoriteList.get(i).get("nodeName"));
                int level = obj.getElementInfoService().getLevelByName(nodeName);

                if (level > 0) {
                    favoriteList.get(i).put("level", level );
                }else {
                    favoriteList.get(i).put("level", "null");
                }
            }

        }
        if (!favoriteList.isEmpty()){

            result.put("data", favoriteList);
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
}
