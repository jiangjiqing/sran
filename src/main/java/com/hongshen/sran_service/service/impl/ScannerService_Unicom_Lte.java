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
    private UnicomQuotaHistoryCellLteMapper quotaCell;

    @Autowired
    private UnicomQuotaHistoryNodeLteMapper quotaNode;

    @Autowired
    private UnicomCounterHistoryLteMapper counterHistory;

    @Autowired
    private UnicomFormulaLteMapper formula;

    @Autowired
    private UnicomNodeLteMapper node;

    @Autowired
    private UnicomCellLteMapper cell;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListLte();

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        List<UnicomFormula> formulaList = formula.getFormulaLteList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
            }
        }

        List<JSONObject> counterHistoryList =
                counterHistory.getCounterHistoryLteListByTime(time);

        if (counterHistoryList.size() == 0) {

            return ret;
        }

        for (JSONObject counterHistory : counterHistoryList) {

            StringBuffer paramValue = new StringBuffer();

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

                            paramValue.append(value + ",");
                        }else {

                            paramValue.append(value);
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

        quotaCell.addQuotaHistoryCellList(paramcloumns, paramValues);

        return ret;
    }

    @Override
    public String nodeCalculation(String time) {

        String ret = null;

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        Map<String, List<String>> nodeCellsMap = new HashMap<>();

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> nodeNameList = node.getNodeNameList();

        List<UnicomFormula> formulaList = formula.getFormulaLteList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
            }
        }

        if (nodeNameList.size() == 0) {

            return ret;
        }

        for (String nodeName : nodeNameList) {

            StringBuffer paramValue = new StringBuffer();

            List<String> cellNameList = cell.getCellNameListByNodeName(nodeName);

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
                                counterHistory.getSumCounterByCellsAndCounterAndTime(cellNameList, variable, time);

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

                            paramValue.append(value + ",");
                        }else {

                            paramValue.append(value);
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

        quotaNode.addQuotaHistoryNodeList(paramcloumns, paramValues);

        return ret;
    }

    @Override
    public String groupCalculation(String time) {
        return null;
    }


    public String cellCalculationOld(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<JSONObject> paramList = new ArrayList<>();

        List<UnicomFormula> formulaList = formula.getFormulaLteList();

        List<JSONObject> counterHistoryList =
                counterHistory.getCounterHistoryLteListByTime(time);

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
