package com.hongshen.sran_service.service.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
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
}
