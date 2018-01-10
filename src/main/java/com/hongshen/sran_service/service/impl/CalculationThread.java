package com.hongshen.sran_service.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.ScannerHelper;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

public class CalculationThread extends Thread {

    private ScannerService_Unicom_Wcdma scannerServiceWcdma;

    private ScannerService_Unicom_Lte scannerServiceLte;

    private CacheService_Unicom_Wcdma caCheServiceWcdma;

    private CacheService_Unicom_Lte caCheServiceLte;

    private static ApplicationContext applicationContext;

    private static boolean dead = true;

    public static void setApplicationContext(ApplicationContext applicationContext) {

        CalculationThread.applicationContext =applicationContext;
    }

    public void run() {

        while (dead) {

            synchronized (this) {

                caCheServiceWcdma = applicationContext.getBean(CacheService_Unicom_Wcdma.class);
                caCheServiceLte = applicationContext.getBean(CacheService_Unicom_Lte.class);

                List<String> counterWcdmaTimeList = caCheServiceWcdma.counterWcdmaTimeList;
                List<String> counterLteTimeList = caCheServiceLte.counterLteTimeList;

                if(counterWcdmaTimeList.size() == 0 && counterLteTimeList.size() == 0) {

                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    continue;
                }

                if (counterWcdmaTimeList.size() != 0) {

                    String counterWcdmatime = counterWcdmaTimeList.get(0);
                    caCheServiceWcdma.counterWcdmaTimeList.remove(0);
                    caCheServiceWcdma.setUpdateTimeForQuotaData(counterWcdmatime);

                    scannerServiceWcdma = applicationContext.getBean(ScannerService_Unicom_Wcdma.class);
                    scannerServiceWcdma.cellCalculation(counterWcdmatime);
                    JSONObject params = scannerServiceWcdma.nodeCalculation(counterWcdmatime);

                    if (params != null){

                        scannerServiceWcdma.groupCalculation(params, counterWcdmatime);
                    }

                    //send(Constants.WCDMA, counterWcdmatime);
                } else if (counterLteTimeList.size() != 0) {

                    String counterLtetime = counterLteTimeList.get(0);
                    caCheServiceLte.counterLteTimeList.remove(0);
                    caCheServiceLte.setUpdateTimeForQuotaData(counterLtetime);

                    scannerServiceLte = applicationContext.getBean(ScannerService_Unicom_Lte.class);
                    scannerServiceLte.cellCalculation(counterLtetime);
                    JSONObject params = scannerServiceLte.nodeCalculation(counterLtetime);

                    if (params != null){

                        scannerServiceLte.groupCalculation(params, counterLtetime);
                    }

                    //send(Constants.LTE, counterLtetime);
                }
            }
        }
    }

    public void send (String type, String time) {

        JSONObject param = new JSONObject();

        param.put("supplier", "unicom");
        param.put("generation", type);
        param.put("message", 1);
        param.put("time", time);

        String path = Constants.SCANNER_SEND_LTE;

        try {

            ScannerHelper.httpclientCounterCalculation(path, param);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
