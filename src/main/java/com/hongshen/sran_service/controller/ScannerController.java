package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.impl.*;
import com.hongshen.sran_service.service.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/sran/service/net/scanner")
public class ScannerController {

    @Autowired
    private CacheService_Unicom_Wcdma caCheServiceWcdma;

    @Autowired
    private CacheService_Unicom_Lte caCheServiceLte;

    @Autowired
    private  NoticeWSController noticeWS;

    public static boolean threadFlag = true;

    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);

    @POST
    @Path("/notice")
    @Produces(MediaType.APPLICATION_JSON)
    public void getNotice (JSONObject params) {

        Integer message = params.getInteger("message");

        switch (message) {

            case Constants.SERVICE_GET_NOTICE_MESSAGE_COUNTER:

                timeQueue(params);
                break;
            default:

                noticeWS.sendAll(params);
                break;
        }
    }

    public void timeQueue (JSONObject params) {

        String time = params.getString("time");
        String type = params.getString("generation");

        if (params.getString("time") != null &&
                !"".equals(params.getString("time"))) {

            if (type.equals(Constants.WCDMA)) {

                List<String> counterWcdmaTimeList = caCheServiceWcdma.counterWcdmaTimeList;

                if (counterWcdmaTimeList.size() == 0) {

                    caCheServiceWcdma.counterWcdmaTimeList.add(time);
                } else {

                    if (!counterWcdmaTimeList.contains(time)) {

                        caCheServiceWcdma.counterWcdmaTimeList.add(time);
                    }
                }
            } else if (type.equals(Constants.LTE)) {

                List<String> counterLteTimeList = caCheServiceLte.counterLteTimeList;

                if (counterLteTimeList.size() == 0) {

                    caCheServiceLte.counterLteTimeList.add(time);
                } else {

                    if (!counterLteTimeList.contains(time)) {

                        caCheServiceLte.counterLteTimeList.add(time);
                    }
                }
            }
        }

        if (threadFlag) {

            logger.info("******************* 调用线程类 只执行一次! ************************");

            threadFlag = false;

            CalculationThread thread = new CalculationThread();

            thread.start();
        }
    }
}
