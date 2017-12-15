package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static com.hongshen.sran_service.controller.TaskWSController.taskStatusMap;
import static com.hongshen.sran_service.controller.TaskWSController.taskStatusSession;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/task")
public class TaskController extends BaseController {

    @Autowired
    private NetObjFactory objFactory;

    // Query task info
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTaskInfo(@PathParam("supplier") String supplier,
                                  @PathParam("generation") String generation,
                                  @HeaderParam("loginName") String loginName) {

//        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> taskInfo = obj.getTaskService().getTaskInfo(loginName);

        if (taskInfo.size() == 0 || taskInfo.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            Boolean hasOriginalLog = obj.getTaskService().hasOriginalLog(loginName);
            Boolean hasScriptLog = obj.getTaskService().hasScriptLog(loginName);

            result.put("hasOriginalLog", hasOriginalLog);
            result.put("hasScriptLog", hasScriptLog);

            result.put("result", Constants.SUCCESS);
            result.put("data", taskInfo);
        }

        return result;
    }

    // Add task
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject startTask(@PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("loginNmae") String loginName,
                                JSONObject param) {

//        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        int update = obj.getTaskService().addTask(loginName, param);
        //TODO update start time
        if (update <= 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", Constants.MSG_ADD_OK);
        }

        return result;
    }

    // Cacel task
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject cacelTask(@PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("loginName") String loginName) {

//        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        int cacelTask = obj.getTaskService().cacelTask(loginName);
        //TODO update end time

        if (cacelTask <= 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_CACEL_FAILED);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", Constants.MSG_CACEL_OK);
        }

        return result;
    }

    // download log
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task/log/{isUseScript}/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject downloadTasklog(@PathParam("supplier") String supplier,
                                      @PathParam("generation") String generation,
                                      @PathParam("isUseScript") String isUseScript,
                                      @HeaderParam("loginName") String loginName) {

//        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        JSONObject file = new JSONObject();
        String filePath = "";

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (isUseScript.equalsIgnoreCase("true") &&
                obj.getTaskService().hasScriptLog(loginName)) {

            filePath = loginName + Constants.TASK_LOG_PATH_SCRIPT;

        } else if (isUseScript.equalsIgnoreCase("false") &&
                obj.getTaskService().hasOriginalLog(loginName)) {

            filePath = loginName + Constants.TASK_LOG_PATH_ORIGINAL;
        }

        if (filePath == "") {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            file.put("filePath", filePath);

            result.put("result", Constants.SUCCESS);
            result.put("data", file);
        }

        return result;
    }
}
