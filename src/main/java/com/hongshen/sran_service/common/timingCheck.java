package com.hongshen.sran_service.common;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomUserTaskGroupWcdmaMapper;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by poplar on 12/27/17.
 */
@Component
public class timingCheck implements ApplicationRunner {
    @Autowired
    private UnicomUserTaskGroupWcdmaMapper unicomUserTaskGroupWcdmaMapper;

    public void startTime() {

        Timer timer = new Timer();

        try {
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    try {

                        List<JSONObject> times = unicomUserTaskGroupWcdmaMapper.getTaskTime();

                        if(times!=null) {
                            for (JSONObject json : times) {
                                int period = 1;//TODO
                                if(json.getDate("startTime")!=null) {
                                    switch (period) {
                                        case 0: {
                                            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            if (date.parse(date.format(json.getDate("startTime"))).getTime()
                                                    == date.parse(date.format(new Date())).getTime()) {

                                                Start(json.getString("login_name"), new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()));
                                            }
                                            break;
                                        }
                                        case 1: {
                                            SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
                                            if (date.parse(date.format(json.getDate("startTime"))).getTime()
                                                    == date.parse(date.format(new Date())).getTime()) {

                                                Start(json.getString("login_name"), new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startChecking(String loginName,String date) {

        String mobatchPath = Constants.MOSHELL_ROOT_PATH + "mobatch";
        String siteFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_SITE + "/" + Constants.TASK_FILE_SITE;
        String cmdFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_CMD + "/" + Constants.TASK_FILE_CMD;
        String logFileDir = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_LOG;
        try {
            //Process process = Runtime.getRuntime().exec("/home/poplar/moShell/moshell123/moshell/mobatch  /home/poplar/Task/site/pll /home/poplar/Task/cmd/pll /home/poplar/Task/"+loginName+"/logs"+"/"+date);
            Process process = Runtime.getRuntime().exec(mobatchPath + " " + siteFilePath + " " + cmdFilePath + " " + logFileDir+"/"+date);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String data = null;
            while ((data = reader.readLine()) != null) {

                System.out.println(data + "*****" + loginName);
            }
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Start(String loginName,String date) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.execute(new Runnable() {
            public void run() {

                startChecking(loginName,date);
            }
        });
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        startTime();
    }
}

