package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseService;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.service.ScannerService;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScannerService_Unicom_Lte extends BaseService implements ScannerService{

    @Autowired
    private UnicomQuotaHistoryCellLteMapper quotaCellMapper;

    @Autowired
    private UnicomQuotaHistoryNodeLteMapper quotaNodeMapper;

    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaGroupMapper;

    @Autowired
    private UnicomCounterHistoryLteMapper counterHistoryMapper;

    @Autowired
    private UnicomFormulaLteMapper formulaMapper;

    @Autowired
    private UnicomCellLteMapper cellMapper;

    @Autowired
    private UnicomNodeLteMapper nodeMapper;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        List<UnicomFormula> formulaList = formulaMapper.getFormulaLteList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
            }
        }

        List<JSONObject> counterHistoryList =
                counterHistoryMapper.getCounterHistoryListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            StringBuffer paramValue = new StringBuffer();

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

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        paramcloumns.add("name");
        paramcloumns.add("time");
        paramcloumns.add("level");

        Map<String, List<String>> nodeCellsMap = new HashMap<>();

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> nodeNameList = nodeMapper.getNodeNameList();

        List<UnicomFormula> formulaList = formulaMapper.getFormulaLteList();

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

    @Override
    public String groupCalculation(JSONObject params, String time) {

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

        List<UnicomFormula> formulaList = formulaMapper.getFormulaLteList();

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

        List<UnicomFormula> formulaList = formulaMapper.getFormulaLteList();

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

        //quotaCell.addQuotaCellLteList(paramList);

        return ret;
    }
}
