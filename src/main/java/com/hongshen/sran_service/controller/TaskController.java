package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.FileHelper;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
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

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> taskInfo = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (loginName == null || loginName.length() == 0 || loginName.isEmpty()){
            msg += "LoginName is null.";

        }else {
            try {
                taskInfo = obj.getTaskService().getTaskInfo(loginName);

                if (taskInfo == null || taskInfo.isEmpty() || taskInfo.size() == 0) {
                    msg += "TaskInfo is null.";

                } else {
                    taskInfo.get(0).put("hasOriginalLog", false);//TODO:delete
                    taskInfo.get(0).put("hasScriptLog", false);//TODO:delete
                }
            }catch  (Exception e){
                msg += "TaskInfo has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", taskInfo.get(0)); //TODO: return list

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }

    // Add task
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/task")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject startTask(@PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("loginName") String loginName,
                                @RequestParam("param") JSONObject param) {

        String msg = "";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (loginName == null || loginName.length() == 0 || loginName.isEmpty()){
            msg += "LoginName is null.";

        }else if (param == null || param.isEmpty()) {
            msg += "Parameters is null.";

        }else{
            try{
                int update = obj.getTaskService().addTask(loginName, param);
                // TODO:insert or update always ok

            }catch (Exception e){
                msg += "Add task has error:" + e.getMessage();
            }

            try {
                String siteDir = Constants.TASK_ROOT_PATH + Constants.TASK_DIR_SITE;
                String cmdDir = Constants.TASK_ROOT_PATH + Constants.TASK_DIR_CMD;
                String logDir = Constants.TASK_ROOT_PATH + Constants.TASK_DIR_LOG;
                String analysisLogDir = Constants.TASK_ROOT_PATH + Constants.TASK_DIR_ANALYSIS_LOG;

                FileHelper.createFile(siteDir, loginName, param.getString("rncList"));
                FileHelper.createFile(cmdDir, loginName, param.getString("cmdList"));
                FileHelper.createDir(logDir + "/" + loginName);
                FileHelper.createDir(analysisLogDir+ "/" + loginName);

            }catch  (Exception e){
                msg += "Create file has error:" + e.getMessage();
            }
            //TODO task plan
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + msg);
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

        String msg = "";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (loginName == null || loginName.length() == 0 || loginName.isEmpty()){
            msg += "LoginName is null.";

        }else {
            try {
                int cacelTask = obj.getTaskService().cacelTask(loginName);
                if (cacelTask <= 0 ){
                    msg +="CacelTask is Failed";
                }
            }catch (Exception e){
                msg += "CacelTask has error.";
            }
            //TODO update end time
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_CACEL_OK);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_CACEL_FAILED + msg);
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

        String msg = "";
        JSONObject result = new JSONObject();
        JSONObject file = new JSONObject();
        String filePath = "";

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (loginName == null || loginName.length() == 0 || loginName.isEmpty()){
            msg += "LoginName is null.";

        }else {
            if (isUseScript.equalsIgnoreCase("true")) {
                filePath = obj.getTaskService().getAnalysisLogPath(loginName);

            } else if (isUseScript.equalsIgnoreCase("false")) {
                filePath = obj.getTaskService().getLogPath(loginName);
            }

            if (filePath == ""){
                msg += "Log is not exist.";
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            file.put("filePath", filePath);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }
}
