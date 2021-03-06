package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.ScannerService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.ScannerHelper;
import com.hongshen.sran_service.service.util.DataHelper;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScannerService_Unicom_Wcdma implements ScannerService{

    @Autowired
    private UnicomQuotaHistoryCellWcdmaMapper quotaCellMapper;

    @Autowired
    private UnicomQuotaHistoryNodeWcdmaMapper quotaNodeMapper;

    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper quotaGroupMapper;

    @Autowired
    private UnicomCounterHistoryWcdmaMapper counterHistoryMapper;

    @Autowired
    private UnicomCellWcdmaMapper cellMapper;

    @Autowired
    private UnicomNodeWcdmaMapper nodeMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private CacheService_Unicom_Wcdma cacheService;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

//        Map<String, List<String>> quotaThresholdCellMap =
//                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdCellList());

        Map<String, JSONObject> quotaThresholdCellMapJson =
                DataHelper.JsonListToJsonMap(cacheService.getThresholdCellList(), "quotaName");

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

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

                        String pmValue = counterHistory.getString(variable);

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

                            value = "0";
                            //e.printStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        /*int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdCellMap.get(quotaName));*/

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculationByThresholdAndType
                                                (value,quotaThresholdCellMapJson.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("'0',");

                        fmLevelList.add(1);
                    }
                }
            }

            /*level = ScannerHelper.maxLevelArray(fmLevelList);*/
            level = ScannerHelper.minLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        paramValues.size();

        try {

            int paramValuesSize = paramValues.size();
            int division = Constants.SCANNER_CALCULATION_DIVISION;
            int cyc = paramValuesSize / division;

            if (cyc <= 1){

                List<String> insertList = paramValues.subList(0, paramValuesSize);
                quotaCellMapper.addQuotaHistoryCellList(paramcloumns, insertList);
            }else {

                for (int i = 0; i <= cyc; i ++) {

                    if (i == 0) {

                        int before = 0;
                        int after = division + 1;

                        List<String> insertList = paramValues.subList(before, after);
                        quotaCellMapper.addQuotaHistoryCellList(paramcloumns, insertList);
                    }else if (i == cyc){

                        int before = i * division + 1;
                        int after = paramValuesSize;

                        List<String> insertList = paramValues.subList(before, after);
                        quotaCellMapper.addQuotaHistoryCellList(paramcloumns, insertList);
                    }else{

                        int before = i * division + 1;
                        int after = (i + 1) * division + 1;

                        List<String> insertList = paramValues.subList(before, after);
                        quotaCellMapper.addQuotaHistoryCellList(paramcloumns, insertList);
                    }
                }
            }

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

//        Map<String, List<String>> quotaThresholdNodeMap =
//                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdNodeList());

        Map<String, JSONObject> quotaThresholdNodeMapJson =
                DataHelper.JsonListToJsonMap(cacheService.getThresholdNodeList(), "quotaName");

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

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

                        String pmValue = nodeResult.getString(variable);

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

                            value = "0";
                            //e.printStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        /*int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdNodeMap.get(quotaName));*/

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculationByThresholdAndType
                                                (value,quotaThresholdNodeMapJson.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("0,");

                        fmLevelList.add(1);
                    }
                }
            }

            /*level = ScannerHelper.maxLevelArray(fmLevelList);*/

            level = ScannerHelper.minLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

            paramValues.add(paramValue.toString());
        }

        try {

            paramValues.size();

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
    public String groupCalculation(JSONObject params, String time){

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> groupNameList = groupMapper.getGroupNameList();

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

//        Map<String, List<String>> quotaThresholdGroupMap =
//                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdGroupList());

        Map<String, JSONObject> quotaThresholdGroupMapJson =
                DataHelper.JsonListToJsonMap(cacheService.getThresholdGroupList(), "quotaName");

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

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

            if (groupAllGroupList.size() == 0) {

                continue;
            }

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                ScannerHelper
                                        .getGroupVariableValueByNodeListWcdma(variable, groupAllGroupList);

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

                            //e.printStackTrace();

                            value = "0";
                        }

                        paramValue.append("'" + value + "',");

                        /*int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdGroupMap.get(quotaName));*/

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculationByThresholdAndType
                                                (value,quotaThresholdGroupMapJson.get(quotaName));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("0,");

                        fmLevelList.add(1);
                    }
                }
            }

            /*level = ScannerHelper.maxLevelArray(fmLevelList);*/

            level = ScannerHelper.minLevelArray(fmLevelList);

            paramValue.append("'" + level + "')");

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

    // below method is 12.07 14:39 "counter" update "pmName" previous
    public String cellCalculationOldCounter(String time) {

        String ret = null;

        Map<String, List<String>> quotaThresholdCellMap =
                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdCellList());

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
                            e.printStackTrace();
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
                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdNodeList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(cacheService.getFormulaList(false));

        Map<String, String> counterMap = ScannerHelper.getCounterMap(cacheService.getCounterListProcessed(false));

        List<JSONObject> counterList = cacheService.getCounterList(false);

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
                            e.printStackTrace();
                        }

                        paramValue.append("'" + value + "',");

                        int fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdNodeMap.get(quotaName));

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

            paramValues.size();

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

    public String groupCalculationOldCounter(JSONObject params, String time){

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> groupNameList = groupMapper.getGroupNameList();

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
                ScannerHelper.getQuotaThresholdMap(cacheService.getThresholdGroupList());

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

            if (groupAllGroupList.size() == 0) {

                continue;
            }

            for (JSONObject f : formulaList) {

                String quotaName = f.getString("quotaName");

                if (expressionSetMap.containsKey(quotaName)) {

                    List<String> variableList = expressionSetMap.get(quotaName);

                    String expression = f.getString("expression");

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                ScannerHelper
                                        .getGroupVariableValueByNodeListWcdma(counterMap.get(variable), groupAllGroupList);

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

                            e.printStackTrace();

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

            quotaGroupMapper.addQuotaHistoryGroupList(paramcloumns, paramValues);

            result = "SUCCESS";
        } catch (Exception e) {

            e.printStackTrace();

            result = "FAIL";
        }
        return result;
    }

    public List<JSONObject> groupHasTopTenOldCounter(String groupName, String quotaName, String time) {

        List<JSONObject> resultList = new ArrayList<>();

        Map<String, String> counterMap = ScannerHelper.getCounterMap(cacheService.getCounterListProcessed(false));

        JSONObject formula = cacheService.getFormulaProcessedByName(quotaName);

        if (formula == null) {

            return resultList;
        }

        String expression = formula.getString("expression");

        List<String> variableList = ScannerHelper.parseExpression(expression);

        if (variableList.size() == 0) {

            return resultList;
        }

        Boolean flag = false;

        for (int i = 0; i < variableList.size(); i ++) {

            String variable = variableList.get(i);

            if (counterMap.get(variable) != null) {

                String counter = counterMap.get(variable);

                if (i != variableList.size() - 1) {

                     expression = expression.replaceAll(variable, counter);
                } else {

                    expression = expression.replaceAll(variable, counter);

                    flag = true;
                }
            }else {

                break;
            }
        }

        if (!flag) {

            return resultList;
        }

        List<String> nodeNameList = nodeMapper.getNodeNameListByGroup(groupName);

        if (nodeNameList.size() == 0) {

            return resultList;
        }

        List<String> cellList = cellMapper.getCellNameListByNodeNameList(nodeNameList);

        if (cellList.size() == 0) {

            return resultList;
        }

        resultList = counterHistoryMapper.getBadTenCell(expression, cellList, time);

        return resultList;
    }
}
