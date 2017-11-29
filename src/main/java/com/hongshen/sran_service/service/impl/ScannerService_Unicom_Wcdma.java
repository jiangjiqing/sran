package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseService;
import com.hongshen.sran_service.dao.UnicomCounterHistoryWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomFormulaWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaCellWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryCellWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.service.ScannerService;
import net.java.dev.eval.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScannerService_Unicom_Wcdma extends BaseService implements ScannerService{

    @Autowired
    private UnicomQuotaHistoryCellWcdmaMapper quotaCell;

    @Autowired
    private UnicomCounterHistoryWcdmaMapper counterHistory;

    @Autowired
    private UnicomFormulaWcdmaMapper formula;

    @Override
    public String cellCalculation(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<String> paramValues = new ArrayList<>();

        List<String> paramcloumns = new ArrayList<>();

        List<UnicomFormula> formulaList = formula.getFormulaWcdmaList();

        for (int j = 0; j < formulaList.size(); j ++) {

            UnicomFormula formula = formulaList.get(j);

            if (j != formulaList.size() -1) {

                paramcloumns.add("formula" + formula.getId() + ",");
            } else {

                paramcloumns.add("formula" + formula.getId());
            }
        }

        List<JSONObject> counterHistoryList =
                counterHistory.getCounterHistoryWcdmaListByTime(time);

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

        quotaCell.addQuotaHistoryCellWcdmaList(paramcloumns, paramValues);

        return ret;
    }

    @Override
    public String nodeCalculation(String time) {
        return null;
    }

    @Override
    public String groupCalculation(String time) {
        return null;
    }

    public String cellCalculationOld(String time) {

        String ret = null;

        Map<String, List<String>> expressionSetMap = getVariableListWcdma();

        List<JSONObject> paramList = new ArrayList<>();

        List<UnicomFormula> formulaList = formula.getFormulaWcdmaList();

        List<JSONObject> counterHistoryList =
                counterHistory.getCounterHistoryWcdmaListByTime(time);

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
