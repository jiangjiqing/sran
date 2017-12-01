package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.service.ScannerService;
import com.hongshen.sran_service.service.util.ScannerHelper;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private UnicomFormulaWcdmaMapper formulaMapper;

    @Autowired
    private UnicomCellWcdmaMapper cellMapper;

    @Autowired
    private UnicomNodeWcdmaMapper nodeMapper;

    @Autowired
    private UnicomCounterWcdmaMapper counterMapper;

    @Autowired
    private UnicomQuotaThresholdCellWcdmaMapper quotaThresholdCellMapper;

    @Autowired
    private UnicomQuotaThresholdNodeWcdmaMapper quotaThresholdNodeMapper;

    @Autowired
    private UnicomQuotaThresholdGroupWcdmaMapper quotaThresholdGroupMapper;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> quotaThresholdCellMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdCellMapper.getThresholdCellList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(formulaMapper.getFormulaList());

        Map<String, String> counterMap = ScannerHelper.getCounterMap(counterMapper.getCounterList());

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            paramcloumns.add("formula" + formula.getId() + ",");
        }

        paramcloumns.add("level");

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            StringBuffer paramValue = new StringBuffer();

            List<String> fmLevelList = new ArrayList<>();

            String level = null;

            paramValue.append("'" + counterHistory.getString("name") + "',");
            paramValue.append("'" + time + "',");

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

                        String fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdCellMap.get(formula.getQuota_name()));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("-1,");

                        fmLevelList.add("1");
                    }
                }
            }

            level = ScannerHelper.avgFmLevelList(fmLevelList);

            paramValue.append("'" + level + "'");

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
                ScannerHelper.getVariableList(formulaMapper.getFormulaList());

        Map<String, String> counterMap = ScannerHelper.getCounterMap(counterMapper.getCounterList());

        List<JSONObject> counterList = counterMapper.getCounterList();

        for (JSONObject counter : counterList) {

            counterParams.add("counter" + counter.getString("id"));
        }

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name,");
        paramcloumns.add("time,");

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            paramcloumns.add("formula" + formula.getId() + ",");
        }

        paramcloumns.add("level");

        List<JSONObject> nodeResultList =
                counterHistoryMapper.getSumAllCounterByTimeAndCounterList(time, counterParams);

        if (nodeResultList.size() == 0) {

            return resultJson;
        }

        for (JSONObject nodeResult : nodeResultList) {

            StringBuffer paramValue = new StringBuffer();

            List<String> fmLevelList = new ArrayList<>();

            String level = null;

            nodeMap.put(nodeResult.getString("nodeName"), nodeResult);

            paramValue.append("'" + nodeResult.getString("nodeName") + "',");
            paramValue.append("'" + time + "',");

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

                        String fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdNodeMap.get(formula.getQuota_name()));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("-1,");

                        fmLevelList.add("1");
                    }
                }
            }

            level = ScannerHelper.avgFmLevelList(fmLevelList);

            paramValue.append("'" + level + "'");

            paramValues.add(paramValue.toString());
        }

        try {

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

        List<String> groupNameList = nodeMapper.getGroupNameList();

        if (groupNameList.size() == 0) {

            return result;
        }

        List<String> paramValues = new ArrayList<>();

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name,");
        paramcloumns.add("time,");

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            paramcloumns.add("formula" + formula.getId() + ",");
        }

        paramcloumns.add("level");

        Map<String, JSONObject> nodeMap = (Map<String, JSONObject>) params.get("nodeMap");

        Map<String, List<String>> quotaThresholdGroupMap =
                ScannerHelper.getQuotaThresholdMap(quotaThresholdGroupMapper.getThresholdGroupList());

        Map<String, List<String>> expressionSetMap =
                ScannerHelper.getVariableList(formulaMapper.getFormulaList());

        Map<String, String> counterMap = ScannerHelper.getCounterMap(counterMapper.getCounterList());

        for (String groupName : groupNameList) {

            List<String> fmLevelList = new ArrayList<>();

            StringBuffer paramValue = new StringBuffer();

            List<JSONObject> groupAllGroupList = new ArrayList<>();

            String level = null;

            paramValue.append("'" + groupName + "'");
            paramValue.append("'" + time + "'");

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroupName(groupName);

            if (nodeNameList.size() == 0) {

                continue;
            }

            for (String nodeName : nodeNameList) {

                if (nodeMap.containsKey(nodeName)) {

                    groupAllGroupList.add(nodeMap.get(nodeName));
                }
            }

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

                        String fmLevel =
                                ScannerHelper
                                        .levelCalculation(value, quotaThresholdGroupMap.get(formula.getQuota_name()));

                        fmLevelList.add(fmLevel);

                    } else {

                        paramValue.append("-1,");

                        fmLevelList.add("1");
                    }
                }
            }

            level = ScannerHelper.avgFmLevelList(fmLevelList);

            paramValue.append("'" + level + "'");

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
}
