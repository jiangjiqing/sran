package com.hongshen.sran_service.controller;



import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by poplar on 17-10-26.
 */

@RestController
@RequestMapping(value = "sran/service")
public class NetElementQuotaController {
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

}
