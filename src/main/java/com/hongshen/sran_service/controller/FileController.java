package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Created by poplar on 12/14/17.
 */
@Path("/sran/service/net/file")
public class FileController {
    @Autowired
    private HttpServletRequest request;

    @POST
    @Path("/dowload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@RequestParam("fileName")JSONObject fileNames,
                                   @HeaderParam("loginName")String loginName) {


        String filePath = System.getProperty("user.dir")+"/src/main/resources/templates/"+fileNames.getString("fileName");
        String path = "/SRAN_SERVICE/src/main/resources/templates/"+fileNames.getString("fileName");
        JSONObject result =new JSONObject();

        File file = new File(filePath);
        System.out.println(file.isFile());

        if(file.isFile()){
            result.put("result", Constants.SUCCESS);
            result.put("data",request.getHeader("Host")+path);
        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", "file does not exist");
        }
        System.out.println(request.getHeader("Host")+filePath);
        return result;
    }
}
