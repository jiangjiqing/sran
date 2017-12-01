package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseService;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdCellWcdma;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdGroupWcdma;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdNodeWcdma;
import com.hongshen.sran_service.service.ScannerService;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScannerService_Unicom_Wcdma extends BaseService implements ScannerService{

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
    private UnicomQuotaThresholdCellWcdmaMapper quotaThresholdMapper;

    @Autowired
    private UnicomQuotaThresholdNodeWcdmaMapper quotaThresholdNodeMapper;

    @Autowired
    private UnicomQuotaThresholdGroupWcdmaMapper quotaThresholdGroupMapper;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> quotaThresholdCellMap = getQuotaThresholdMap("cell");

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");

            } else {

                paramcloumns.add("formula" + formula.getId());
            }
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

            paramValue.append("'" + counterHistory.getString("name") + "'");
            paramValue.append("'" + time + "'");
            paramValue.append("'" + level + "'");

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

                            value = "-1";
                            e.getStackTrace();
                        }

                        if (j != formulaList.size() -1) {

                            paramValue.append("'" + value + "',");
                        }else {

                            paramValue.append("'" + value + "'");
                        }

                        String fmLevel = levelCalculation(value, quotaThresholdCellMap.get(formula.getQuota_name()));

                        fmLevelList.add(fmLevel);

                    } else {

                        if (j != formulaList.size() -1) {

                            paramValue.append("-1,");
                        } else {

                            paramValue.append("-1");
                        }

                        fmLevelList.add("1");
                    }
                }

            }

            level = avgFmLevelList(fmLevelList);

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

        Map<String, List<String>> quotaThresholdNodeMap = getQuotaThresholdMap("node");

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        List<JSONObject> counterList = counterMapper.getCounterList();

        for (JSONObject counter : counterList) {

            counterParams.add("counter" + counter.getString("id"));
        }

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
            }
        }

        List<JSONObject> nodeResultList =
                counterHistoryMapper.getSumAllCounterByTimeAndCounterList(time, counterParams);

        if (nodeResultList.size() == 0) {

            return resultJson;
        }

        for (JSONObject nodeResult : nodeResultList) {

            String level = null;

            nodeMap.put(nodeResult.getString("nodeName"), nodeResult);

            StringBuffer paramValue = new StringBuffer();

            paramValue.append("'" + nodeResult.getString("nodeName") + "'");
            paramValue.append("'" + time + "'");
            paramValue.append("'" + level + "'");

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        Map<String, JSONObject> nodeMap =  (Map<String, JSONObject>) params.get("nodeMap");

        List<String> groupNameList = nodeMapper.getGroupNameList();

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
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

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroupName(groupName);

            if (nodeNameList.size() == 0) {

                continue;
            }

            /*for (String nodeName : nodeNameList) {

                if (nodeCellsMap.containsKey(nodeName)) {

                    List<String> nodeCells = nodeCellsMap.get(nodeName);

                    for (String cell : nodeCells) {

                        groupAllNodeAllCellList.add(cell);
                    }
                }
            }*/

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

    String avgFmLevelList (List<String> fmLevelList) {

        String levle = null;

        return levle;
    }

    Map<String, List<String>> getQuotaThresholdMap(String type){

        Map<String, List<String>> quotaThresholdMap = new HashMap<>();

        if (type.equals("cell")) {

            List<UnicomQuotaThresholdCellWcdma> quotaThresholdCellList = quotaThresholdMapper.getAllQuotaThresholdCell();

            for (UnicomQuotaThresholdCellWcdma quotaThresholdCell : quotaThresholdCellList) {

                List<String> paramList = new ArrayList<>();

                paramList.add(quotaThresholdCell.getThreshold1());
                paramList.add(quotaThresholdCell.getThreshold2());
                paramList.add(quotaThresholdCell.getThreshold3());

                quotaThresholdMap.put(quotaThresholdCell.getQuotaName(), paramList);
            }

        } else if (type.equals("node")) {

            List<UnicomQuotaThresholdNodeWcdma> quotaThresholdNodeList =
                    quotaThresholdNodeMapper.getAllQuotaThresholdNode();

            for (UnicomQuotaThresholdNodeWcdma quotaThresholdNode : quotaThresholdNodeList) {

                List<String> paramList = new ArrayList<>();

                paramList.add(quotaThresholdNode.getThreshold1());
                paramList.add(quotaThresholdNode.getThreshold2());
                paramList.add(quotaThresholdNode.getThreshold3());

                quotaThresholdMap.put(quotaThresholdNode.getQuotaName(), paramList);
            }

        } else if (type.equals("group")) {

            List<UnicomQuotaThresholdGroupWcdma> quotaThresholdGroupList =
                    quotaThresholdGroupMapper.getAllQuotaThresholdGroup();

            for (UnicomQuotaThresholdGroupWcdma quotaThresholdGroup : quotaThresholdGroupList) {

                List<String> paramList = new ArrayList<>();

                paramList.add(quotaThresholdGroup.getThreshold1());
                paramList.add(quotaThresholdGroup.getThreshold2());
                paramList.add(quotaThresholdGroup.getThreshold3());

                quotaThresholdMap.put(quotaThresholdGroup.getQuotaName(), paramList);
            }
        }

        return quotaThresholdMap;
    }

    String levelCalculation(String value, List<String> thresholdList){

        String level = null;

        if (value != "-1") {

            int fmValue = Integer.valueOf(value);

            int threshold1 = Integer.valueOf(thresholdList.get(0));
            int threshold2 = Integer.valueOf(thresholdList.get(1));
            int threshold3 = Integer.valueOf(thresholdList.get(2));

            if (fmValue <= threshold1) {

                level = "1";
            }

            if (fmValue > threshold1 && fmValue <= threshold2) {

                level = "2";
            }

            if (fmValue > threshold2 && fmValue <= threshold3) {

                level = "3";
            }
        } else {

            level = "1";
        }

        return level;
    }

    public JSONObject nodeCalculationOld(String time) {

        JSONObject resultJson = new JSONObject();

        List<String> paramValues = new ArrayList<>();

        List<String> counterParams = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> nodeCellsMap = new HashMap<>();

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> nodeNameList = nodeMapper.getNodeNameList();

        List<JSONObject> counterList = counterMapper.getCounterList();

        for (JSONObject counter : counterList) {

            counterParams.add("counter" + counter.getString("id"));
        }

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
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

            List<String> cellNameList = cellMapper.getCellNameListByNodeName(nodeName);

            if (cellNameList.size() == 0) {

                continue;
            }

            nodeCellsMap.put(nodeName, cellNameList);

            for (int j = 0; j < formulaList.size(); j ++) {

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

                    boolean flag = false;

                    for (int i = 0; i < variableList.size(); i ++) {

                        String variable = variableList.get(i);

                        String pmValue =
                                counterHistoryMapper.getSumCounterByCellsAndCounterAndTime(cellNameList, variable, time);

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

    public String groupCalculationOld(JSONObject params, String time) {

        String result = null;

        if (params == null || time == null) {

            return null;
        }

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        Map<String, List<String>> nodeCellsMap =  (Map<String, List<String>>) params.get("nodeCellsMap");

        List<String> groupNameList = nodeMapper.getGroupNameList();

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
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

            List<String> nodeNameList = nodeMapper.getNodeNameListByGroupName(groupName);

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

                UnicomFormula formula = formulaList.get(j);

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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

    public String cellCalculationOld(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<JSONObject> paramList = new ArrayList<>();

        List<UnicomFormula> formulaList = formulaMapper.getFormulaWcdmaList();

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            JSONObject resultJson = new JSONObject();

            for (UnicomFormula formula : formulaList) {

                if (expressionSetMap.containsKey(formula.getQuota_name())) {

                    List<String> variableList = expressionSetMap.get(formula.getQuota_name());

                    String expression = formula.getExpression();

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
                                .put("formula" + formula.getId(), value);
                    } else {

                        resultJson
                                .put("formula" + formula.getId(), "-1");
                    }
                }

            }

            paramList.add(resultJson);
        }

        paramList.size();

        //quotaCell.addQuotaCellWcdmaList(paramList);

        return ret;
    }
}
