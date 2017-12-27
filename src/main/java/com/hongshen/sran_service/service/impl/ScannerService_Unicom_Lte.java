package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.ScannerService;
import com.hongshen.sran_service.service.util.ScannerHelper;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScannerService_Unicom_Lte implements ScannerService{

    @Autowired
    private UnicomQuotaHistoryCellLteMapper quotaCellMapper;

    @Autowired
    private UnicomQuotaHistoryNodeLteMapper quotaNodeMapper;

    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaGroupMapper;

    @Autowired
    private UnicomCounterHistoryLteMapper counterHistoryMapper;

    @Autowired
    private UnicomCellLteMapper cellMapper;

    @Autowired
    private UnicomNodeLteMapper nodeMapper;

    @Autowired
    private CacheService_Unicom_Lte cacheService;

    @Autowired
    private UnicomQuotaThresholdCellLteMapper quotaThresholdCellMapper;

    @Autowired
    private UnicomQuotaThresholdNodeLteMapper quotaThresholdNodeMapper;

    @Autowired
    private UnicomQuotaThresholdGroupLteMapper quotaThresholdGroupMapper;


    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> quotaThresholdCellMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdCellMapper.getThresholdCellList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaListProcessed(false));

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        List<JSONObject> formulaList = cacheService.getFormulaListProcessed(false);

        for (JSONObject f : formulaList) {

            paramcloumns.add(f.getString("quotaName"));
        }

        paramcloumns.add("level");

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            StringBuffer paramValue = new StringBuffer();

            List<Integer> fmLevelList = new ArrayList<>();

            Integer level = null;

            paramValue.append("('" + counterHistory.getString("name") + "',");
            paramValue.append("'" + time + "',");

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue = null;

                        if (!variable.contains("_")) {

                            String counterNameIndex = counterHistory.getString("name").substring(0, 1);

                            if (counterNameIndex.equals("T")) {

                                String variableTdd = "EUtranCell"+ "T" +"DD_" + variable;

                                pmValue = counterHistory.getString(variableTdd);

                                if ("".equals(pmValue) || pmValue == null ) {

                                    pmValue = "0";
                                }
                            } else if (counterNameIndex.equals("F")) {

                                String variableFdd = "EUtranCell"+ "F" +"DD_" + variable;

                                pmValue = counterHistory.getString(variableFdd);

                                if ("".equals(pmValue) || pmValue == null ) {

                                    pmValue = "0";
                                }
                            } else {

                                break;
                            }
                        } else {

                            pmValue = counterHistory.getString(variable);

                            if ("".equals(pmValue) && pmValue != null ) {

                                pmValue = "0";
                            }
                        }

                        if (pmValue != null && !"".equals(pmValue) && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                        } else if (pmValue != null && !"".equals(pmValue) && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;

                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            value = "-1";
                            //e.getStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdCellMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("'-1',");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        paramValues.size();

        try {

            quotaCellMapper.addQuotaHistoryCellList(paramcloumns, paramValues);

            ret = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            ret = "FAIL";
        }

        return ret;
    }

    @Override
    public JSONObject nodeCalculation(String time) {

        JSONObject resultJson = new JSONObject();

        Map<String, JSONObject> nodeMap = new HashMap<>();

        List<String> counterParams = new ArrayList<>();

        List<String> paramValues = new ArrayList<>();

        Map<String, List<String>> quotaThresholdNodeMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdNodeMapper.getThresholdNodeList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaListProcessed(false));

        List<JSONObject> counterList = cacheService.getCounterListProcessed(false);

        for (JSONObject counter : counterList) {

            counterParams.add(counter.getString("name"));
        }

        List<JSONObject> formulaList = cacheService.getFormulaListProcessed(false);

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        for (JSONObject f : formulaList) {

            paramcloumns.add(f.getString("quotaName"));
        }

        paramcloumns.add("level");

        List<JSONObject> nodeResultList =
                counterHistoryMapper.getSumAllCounterByTimeAndCounterList(time, counterParams);

        if (nodeResultList.size() == 0) {

            return resultJson;
        }

        for (JSONObject nodeResult : nodeResultList) {

            StringBuffer paramValue = new StringBuffer();

            List<Integer> fmLevelList = new ArrayList<>();

            Integer level = null;

            nodeMap.put(nodeResult.getString("nodeName"), nodeResult);

            paramValue.append("('" + nodeResult.getString("nodeName") + "',");
            paramValue.append("'" + time + "',");

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue = null;//nodeResult.getString(variable);

                        if (!variable.contains("_")) {

                            String counterNameIndex = nodeResult.getString("nodeName").substring(0, 1);

                            if (counterNameIndex.equals("T")) {

                                String variableTdd = "EUtranCell"+ "T" +"DD_" + variable;

                                pmValue = nodeResult.getString(variableTdd);

                                if ("".equals(pmValue) || pmValue == null ) {

                                    pmValue = "0";
                                }
                            } else if (counterNameIndex.equals("F")) {

                                String variableFdd = "EUtranCell"+ "F" +"DD_" + variable;

                                pmValue = nodeResult.getString(variableFdd);

                                if ("".equals(pmValue) || pmValue == null ) {

                                    pmValue = "0";
                                }
                            } else {

                                break;
                            }
                        } else {

                            pmValue = nodeResult.getString(variable);
                        }

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;

                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);

                        }catch (Exception e){

                            value = "-1";
                            //e.getStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdNodeMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("'-1',");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        try {

            List<String> testList = new ArrayList<>();

            testList.add(paramValues.get(0));
            testList.add(paramValues.get(1));

            quotaNodeMapper.addQuotaHistoryNodeList(paramcloumns, paramValues);

            resultJson.put("nodeMap", nodeMap);
            resultJson.put("message", "SUCCESS");
        } catch (Exception e) {

            e.printStackTrace();

            resultJson.put("nodeMap", null);
            resultJson.put("message", "FAIL");
        }

        return resultJson;
    }

    @Override
    public String groupCalculation(JSONObject params, String time) {

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> groupNameList = nodeMapper.getGroupNameList();

        if (groupNameList.size() == 0) {

            return result;
        }

        List<String> paramValues = new ArrayList<>();

        List<JSONObject> formulaList = cacheService.getFormulaListProcessed(false);

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        for (JSONObject f : formulaList) {

            paramcloumns.add(f.getString("quotaName"));
        }

        paramcloumns.add("level");

        Map<String, JSONObject> nodeMap = (Map<String, JSONObject>) params.get("nodeMap");

        Map<String, List<String>> quotaThresholdGroupMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdGroupMapper.getThresholdGroupList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaListProcessed(false));

        for (String groupName : groupNameList) {

            List<Integer> fmLevelList = new ArrayList<>();

            StringBuffer paramValue = new StringBuffer();

            List<JSONObject> groupAllGroupList = new ArrayList<>();

            Integer level = null;

            paramValue.append("('" + groupName + "',");
            paramValue.append("'" + time + "',");

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroup(groupName);

            if (nodeNameList.size() == 0) {

                continue;
            }

            for (String nodeName : nodeNameList) {

                if (nodeMap.get(nodeName) != null) {

                    groupAllGroupList.add(nodeMap.get(nodeName));
                }
            }

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.get(quotaName) != null) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                ScannerHelper
                                        .getGroupVariableValueByNodeList(variable, groupAllGroupList);

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);
                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;
                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            //e.getStackTrace();

                            value = "-1";
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdGroupMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("-1,");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        try {

            List<String> testList = new ArrayList<>();

            testList.add(paramValues.get(0));
            testList.add(paramValues.get(1));

            quotaGroupMapper.addQuotaHistoryGroupList(paramcloumns, paramValues);

            result = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            result = "FAIL";
        }

        return result;
    }



    // below method is 12.07 14:39 "counter" update "pmName" previous
    public String cellCalculationOldCounter(String time) {

        String ret = null;

        Map<String, List<String>> quotaThresholdCellMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdCellMapper.getThresholdCellList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

        Map<String, String> counterMap = ScannerHelper.getCounterMap(cacheService.getCounterListProcessed(false));

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        for (JSONObject f : formulaList) {
            paramcloumns.add("formula" + f.getString("id"));
        }

        paramcloumns.add("level");

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            StringBuffer paramValue = new StringBuffer();

            List<Integer> fmLevelList = new ArrayList<>();

            Integer level = null;

            paramValue.append("('" + counterHistory.getString("name") + "',");
            paramValue.append("'" + time + "',");

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue = counterHistory.getString(counterMap.get(variable));

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;

                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            value = "-1";
                            e.getStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdCellMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("'-1',");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        paramValues.size();

        try {

            quotaCellMapper.addQuotaHistoryCellList(paramcloumns, paramValues);

            ret = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            ret = "FAIL";
        }

        return ret;
    }

    public JSONObject nodeCalculationOldCounter(String time) {

        JSONObject resultJson = new JSONObject();

        Map<String, JSONObject> nodeMap = new HashMap<>();

        List<String> counterParams = new ArrayList<>();

        List<String> paramValues = new ArrayList<>();

        Map<String, List<String>> quotaThresholdNodeMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdNodeMapper.getThresholdNodeList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

        Map<String, String> counterMap = ScannerHelper.getCounterMap(cacheService.getCounterListProcessed(false));

        List<JSONObject> counterList = cacheService.getCounterListProcessed(false);

        for (JSONObject counter : counterList) {

            counterParams.add("counter" + counter.getString("id"));
        }

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        for (JSONObject f : formulaList) {
            paramcloumns.add("formula" + f.getString("id"));
        }

        paramcloumns.add("level");

        List<JSONObject> nodeResultList =
                counterHistoryMapper.getSumAllCounterByTimeAndCounterList(time, counterParams);

        if (nodeResultList.size() == 0) {

            return resultJson;
        }

        for (JSONObject nodeResult : nodeResultList) {

            StringBuffer paramValue = new StringBuffer();

            List<Integer> fmLevelList = new ArrayList<>();

            Integer level = null;

            nodeMap.put(nodeResult.getString("nodeName"), nodeResult);

            paramValue.append("('" + nodeResult.getString("nodeName") + "',");
            paramValue.append("'" + time + "',");

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue = nodeResult.getString(counterMap.get(variable));

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;

                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);

                        }catch (Exception e){

                            value = "-1";
                            e.getStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdNodeMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("'-1',");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        try {

            List<String> testList = new ArrayList<>();

            testList.add(paramValues.get(0));
            testList.add(paramValues.get(1));

            quotaNodeMapper.addQuotaHistoryNodeList(paramcloumns, paramValues);

            resultJson.put("nodeMap", nodeMap);
            resultJson.put("message", "SUCCESS");
        } catch (Exception e) {

            e.printStackTrace();

            resultJson.put("nodeMap", null);
            resultJson.put("message", "FAIL");
        }

        return resultJson;
    }

    public String groupCalculationOldCounter(JSONObject params, String time) {

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> groupNameList = nodeMapper.getGroupNameList();

        if (groupNameList.size() == 0) {

            return result;
        }

        List<String> paramValues = new ArrayList<>();

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        for (JSONObject f : formulaList) {

            paramcloumns.add("formula" + f.getString("id"));
        }

        paramcloumns.add("level");

        Map<String, JSONObject> nodeMap = (Map<String, JSONObject>) params.get("nodeMap");

        Map<String, List<String>> quotaThresholdGroupMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdGroupMapper.getThresholdGroupList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

        Map<String, String> counterMap = ScannerHelper.getCounterMap(cacheService.getCounterListProcessed(false));

        for (String groupName : groupNameList) {

            List<Integer> fmLevelList = new ArrayList<>();

            StringBuffer paramValue = new StringBuffer();

            List<JSONObject> groupAllGroupList = new ArrayList<>();

            Integer level = null;

            paramValue.append("('" + groupName + "',");
            paramValue.append("'" + time + "',");

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroup(groupName);

            if (nodeNameList.size() == 0) {

                continue;
            }

            for (String nodeName : nodeNameList) {

                if (nodeMap.get(nodeName) != null) {

                    groupAllGroupList.add(nodeMap.get(nodeName));
                }
            }

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.get(quotaName) != null) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    ScannerHelper.sortStringArray(variableList);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                ScannerHelper
                                        .getGroupVariableValueByNodeList(counterMap.get(variable), groupAllGroupList);

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);
                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;
                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            e.getStackTrace();

                            value = "-1";
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdGroupMap.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("-1,");

                        fmLevelList.add(1);
                    }
                }
            }

            level = ScannerHelper.maxLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        try {

            List<String> testList = new ArrayList<>();

            testList.add(paramValues.get(0));
            testList.add(paramValues.get(1));

            quotaGroupMapper.addQuotaHistoryGroupList(paramcloumns, paramValues);

            result = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            result = "FAIL";
        }

        return result;
    }

    // earlier ...
    public String groupCalculationOld(JSONObject params, String time){

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> expressionSetMap = null;//getVariableListWcdma();

        Map<String, List<String>> nodeCellsMap =  (Map<String, List<String>>) params.get("nodeCellsMap");

        List<String> groupNameList = nodeMapper.getGroupNameList();

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        for (int j = 0; j < formulaList.size(); j ++) {

            JSONObject f = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + f.getString("id") + ",");
            } else {

                paramcloumns.add("formula" + f.getString("id"));
            }
        }

        if (groupNameList.size() == 0) {

            return result;
        }

        for (String groupName : groupNameList) {

            StringBuffer paramValue = new StringBuffer();

            List<String> groupAllNodeAllCellList = new ArrayList<>();

            String level = null;

            paramValue.append("'" + groupName + "'");
            paramValue.append("'" + time + "'");
            paramValue.append("'" + level + "'");

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroup(groupName);

            if (nodeNameList.size() == 0) {

                continue;
            }

            for (String nodeName : nodeNameList) {

                if (nodeCellsMap.containsKey(nodeName)) {

                    List<String> nodeCells = nodeCellsMap.get(nodeName);

                    for (String cell : nodeCells) {

                        groupAllNodeAllCellList.add(cell);
                    }
                }
            }

            for (int j = 0; j < formulaList.size(); j ++) {

                JSONObject f = formulaList.get(j);
                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                counterHistoryMapper.
                                        getSumCounterByCellsAndCounterAndTime(groupAllNodeAllCellList, variable, time);

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);
                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;
                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            value = "-1";
                            e.getStackTrace();
                        }

                        if (j != formulaList.size() -1) {

                            paramValue.append("'" + value + "',");
                        }else {

                            paramValue.append("'" + value + "'");
                        }

                    } else {

                        if (j != formulaList.size() -1) {

                            paramValue.append("-1,");
                        } else {

                            paramValue.append("-1");
                        }
                    }
                }
            }

            paramValues.add(paramValue.toString());
        }

        try {

            quotaGroupMapper.addQuotaHistoryGroupList(paramcloumns, paramValues);

            result = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            result = "FAIL";
        }

        return result;
    }

    public JSONObject nodeCalculationOld(String time) {

        JSONObject resultJson = new JSONObject();

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> nodeCellsMap = new HashMap<>();

        Map<String, List<String>> expressionSetMap = null;//getVariableListWcdma();

        List<String> nodeNameList = nodeMapper.getNodeNameList();

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        for (int j = 0; j < formulaList.size(); j ++) {

            JSONObject f = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + f.getString("id") + ",");
            } else {

                paramcloumns.add("formula" + f.getString("id"));
            }
        }

        if (nodeNameList.size() == 0) {

            return resultJson;
        }

        for (String nodeName : nodeNameList) {

            StringBuffer paramValue = new StringBuffer();

            String level = null;

            paramValue.append("'" + nodeName + "'");
            paramValue.append("'" + time + "'");
            paramValue.append("'" + level + "'");

            List<String> cellNameList = cellMapper.getCellNameListByNode(nodeName);

            if (cellNameList.size() == 0) {

                continue;
            }

            nodeCellsMap.put(nodeName, cellNameList);

            for (int j = 0; j < formulaList.size(); j ++) {

                JSONObject f = formulaList.get(j);

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                counterHistoryMapper.
                                        getSumCounterByCellsAndCounterAndTime(cellNameList, variable, time);

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);
                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;
                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            value = "-1";
                            e.getStackTrace();
                        }

                        if (j != formulaList.size() -1) {

                            paramValue.append("'" + value + "',");
                        }else {

                            paramValue.append("'" + value + "'");
                        }

                    } else {

                        if (j != formulaList.size() -1) {

                            paramValue.append("-1,");
                        } else {

                            paramValue.append("-1");
                        }
                    }
                }
            }

            paramValues.add(paramValue.toString());
        }

        try {

            quotaNodeMapper.addQuotaHistoryNodeList(paramcloumns, paramValues);

            resultJson.put("nodeCellsMap", nodeCellsMap);
            resultJson.put("message", "SUCCESS");
        } catch (Exception e) {

            e.printStackTrace();

            resultJson.put("nodeCellsMap", null);
            resultJson.put("message", "FAIL");
        }

        return resultJson;
    }

    public String cellCalculationOld(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = null;//getVariableListWcdma();

        List<JSONObject> paramList = new ArrayList<>();

        List<JSONObject> formulaList = cacheService.getFormulaList(false);

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            JSONObject resultJson = new JSONObject();

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue = counterHistory.getString(variable);

                        if (pmValue != null && i != variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);
                        } else if (pmValue != null && i == variableList.size() - 1) {

                            expression = expression.replaceAll(variable, pmValue);

                            flag = true;
                        } else {

                            break;
                        }
                    }

                    if (flag) {

                        String value = null;

                        try{

                            Expression exp = new Expression(expression);

                            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

                            value = String.valueOf((double)Math.round(doubleValue*100)/100);
                        }catch (Exception e){

                            value = "NaN";
                            e.getStackTrace();
                        }

                        resultJson
                                .put("formula" + f.getString("id"), value);
                    } else {

                        resultJson
                                .put("formula" + f.getString("id"), "-1");
                    }
                }

            }

            paramList.add(resultJson);
        }

        paramList.size();

        //quotaCell.addQuotaCellLteList(paramList);

        return ret;
    }
}
