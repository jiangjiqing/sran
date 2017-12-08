package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
                                  @HeaderParam("Auth-Token") String authToken) {

        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject taskInfo = obj.getTaskService().getTaskInfo(loginName);
        ;

        if (taskInfo.isEmpty()) {
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

    // Start task -- Add or Update task info TODO :websocket
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task/start")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject startTask(@PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("Auth-Token") String authToken,
                                JSONObject param) {

        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        int update = obj.getTaskService().startTask(loginName, param);

        if (update > 0) {
            result.put("result", Constants.SUCCESS);
            result.put("data", Constants.MSG_UPDATE_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED);
        }

        return result;
    }

    // Cacel task TODO : break thread
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task/cacel")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject cacelTask(@PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("Auth-Token") String authToken) {

        String loginName = "tom";// TODO loginName
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        int cacelTask = obj.getTaskService().cacelTask(loginName);

        if (cacelTask > 0) {
            result.put("result", Constants.SUCCESS);
            result.put("data", Constants.MSG_CACEL_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_CACEL_FAILED);
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
                                      @HeaderParam("Auth-Token") String authToken) {

        String loginName = "tom";// TODO loginName
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
