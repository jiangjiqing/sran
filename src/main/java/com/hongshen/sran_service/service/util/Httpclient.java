package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 17-11-3.
 */
@Controller
public class Httpclient {
    public String httpclient(String url, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);

        httpget.addHeader("Auth-Token",token);
        CloseableHttpResponse response = httpclient.execute(httpget);
        System.out.println("Status:"+response.getStatusLine().getStatusCode());
        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            return EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return "FAILED";
    }
    public int addUser(JSONObject param)throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("bbb");

        httpget.addHeader("parm", String.valueOf(param));
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            return 1;
//            EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return 0;
    }

    public int updateUser(JSONObject param)throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("a");

        httpget.addHeader("parm", String.valueOf(param));
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            return 1;
//            EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return 0;
    }

    public int deleteUser(String param)throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("cc");

        httpget.addHeader("parm", param);
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            return 1;
//            EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return 0;
    }

    public Map<String,Object> getUser(String method)throws IOException{
        Map map = new HashMap();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("dd");

//        httpget.addHeader();
        httpget.addHeader("method",method);
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            map.put("a",entity);
            return map;
//            EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return null;
    }
    public List getRole(String method)throws IOException{
        List list = new ArrayList();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("ee");

//        httpget.addHeader();
        httpget.addHeader("method",method);
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity=response.getEntity(); // 获取返回实体

        if(entity.getContentType() != null){
            System.out.println("Content-Type:"+entity.getContentType().getValue());
//            System.out.println("网页内容："+EntityUtils.toString(entity,"utf-8"));// 获取网页内容
            list.add(entity);
            return list;
//            EntityUtils.toString(entity,"utf-8");
        }


        response.close();// response关闭
        httpclient.close(); // httpClient关闭
        return null;
    }
}
