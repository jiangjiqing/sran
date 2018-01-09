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

                List<String> counterWcdmaTimeList = ScannerHelper.counterWcdmaTimeList;

                List<String> counterLteTimeList = ScannerHelper.counterLteTimeList;

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
                    ScannerHelper.counterWcdmaTimeList.remove(0);

                    caCheServiceWcdma = applicationContext.getBean(CacheService_Unicom_Wcdma.class);
                    caCheServiceWcdma.setUpdateTimeForQuotaData(counterWcdmatime);

                    scannerServiceWcdma = applicationContext.getBean(ScannerService_Unicom_Wcdma.class);
                    scannerServiceWcdma.cellCalculation(counterWcdmatime);
                    JSONObject params = scannerServiceWcdma.nodeCalculation(counterWcdmatime);

                    if (params != null){

                        scannerServiceWcdma.groupCalculation(params, counterWcdmatime);
                    }

                    JSONObject param = new JSONObject();

                    param.put("supplier", "unicom");
                    param.put("generation", "wcdma");
                    param.put("message", "The counterHistoryWcdma cell/node/group complete of the "
                            + counterWcdmatime + "calculation");
                    param.put("time", counterWcdmatime);

                    String path = Constants.SCANNER_SEND_WCDMA;

                    try {

                        ScannerHelper.httpclientCounterCalculation(path, param);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                } else if (counterLteTimeList.size() != 0) {

                    String counterLtetime = counterLteTimeList.get(0);
                    ScannerHelper.counterLteTimeList.remove(0);

                    caCheServiceLte = applicationContext.getBean(CacheService_Unicom_Lte.class);
                    caCheServiceLte.setUpdateTimeForQuotaData(counterLtetime);

                    scannerServiceLte = applicationContext.getBean(ScannerService_Unicom_Lte.class);
                    scannerServiceLte.cellCalculation(counterLtetime);
                    JSONObject params = scannerServiceLte.nodeCalculation(counterLtetime);

                    if (params != null){

                        scannerServiceLte.groupCalculation(params, counterLtetime);
                    }

                    JSONObject param = new JSONObject();

                    param.put("supplier", "unicom");
                    param.put("generation", "wcdma");
                    param.put("message", "The counterHistoryLte cell/node/group complete of the "
                            + counterLtetime + "calculation");
                    param.put("time", counterLtetime);

                    String path = Constants.SCANNER_SEND_LTE;

                    try {

                        ScannerHelper.httpclientCounterCalculation(path, param);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
