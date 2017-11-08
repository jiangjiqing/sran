package com.hongshen.sran_service.common;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class BaseController {

    @Autowired
    private Httpclient httpclient;

    public boolean check(String url, String method, String authToken){

        Boolean flag = false;

        JSONObject checkResult  = new JSONObject();

        checkResult = checkUrlMethod(url, method, authToken);

        if (!checkResult.isEmpty() && !Constants.FAIL.equals(checkResult.getString("status"))) {

            flag = true;
        }

        return flag;
    }

    public JSONObject checkUrlMethod(String url, String method, String token){

        JSONObject result = new JSONObject();

        String authResult = null;

        try {

            if (url != null && method != null && !"".equals(url) && !"".equals(method)) {

                String shiroUrl = Constants.SHIRO_URI+"?checkUrl=" + url + "&checkMethod=" + method;

                authResult = httpclient.httpclient(shiroUrl, token);

                if(authResult.equals(Constants.FAIL)){

                    result.put("status", Constants.FAIL);
                }else if (authResult != null && !"".equals(authResult) && !authResult.equals("FAILED")){

                    result.put("status", Constants.SUCCESS);
                }
            }

        }catch (IOException e) {

            e.printStackTrace();
        }

        return result;
    }

}
