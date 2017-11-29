package com.hongshen.sran_service.service;

public interface ScannerService {

    String cellCalculation(String time);

    String nodeCalculation(String time);

    String groupCalculation(String time);
}
