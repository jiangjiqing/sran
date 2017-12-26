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
@Path("/sran/service/file")
public class FileController {

    @Autowired
    private HttpServletRequest request;

    @GET
    @Path("/templates/{fileName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("fileName") String fileName,
                                   @HeaderParam("loginName")String loginName) {

        String msg = "";
        JSONObject result =new JSONObject();
        if (fileName != Constants.TEMPLATE_STATION_NAME ||
                fileName != Constants.TEMPLATE_RNC_INFO ||
                fileName != Constants.TEMPLATE_PROTECT ||
                fileName != Constants.TEMPLATE_FORMULA ||
                fileName != Constants.TEMPLATE_COUNTER ||
                fileName != Constants.TEMPLATE_ALARM_LIB ){
            msg += "FileName has error.";

        }else {
//        String filePath = System.getProperty("user.dir")+"/src/main/resources/templates/"+fileName+"_template.xlsx";
//        String path = "/SRAN_SERVICE/src/main/resources/templates/"+fileName+"_template.xlsx";
            String filePath = Constants.TEMPLATE_ROOT_PATH + fileName + Constants.TEMPLATE_FILE_NAME_END;
            File file = new File(filePath);

            if (file.isFile() && file.exists()) {
                result.put("filePath", filePath);
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", result);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", "File does not exist.");
        }

        return result;
    }
}
