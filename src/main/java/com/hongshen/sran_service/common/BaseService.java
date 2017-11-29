package com.hongshen.sran_service.common;

import com.hongshen.sran_service.dao.UnicomFormulaLteMapper;
import com.hongshen.sran_service.dao.UnicomFormulaWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomFormula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BaseService {

    @Autowired
    private UnicomFormulaWcdmaMapper formulaWcdmaMapper;

    @Autowired
    private UnicomFormulaLteMapper formulaLteMapper;

    public Map<String, List<String>> getVariableListWcdma () {

        Map<String, List<String>> resultMap = new HashMap<>();

        List<UnicomFormula> formulaList = formulaWcdmaMapper.getFormulaWcdmaList();

        for (UnicomFormula formula : formulaList) {

            List<String> variableList = parseExpression(formula.getExpression());

            if (variableList != null) {

                resultMap.put(formula.getQuota_name(), variableList);
            }
        }

        return resultMap;
    }

    public Map<String, List<String>> getVariableListLte () {

        Map<String, List<String>> resultMap = new HashMap<>();

        List<UnicomFormula> formulaList = formulaLteMapper.getFormulaLteList();

        for (UnicomFormula formula : formulaList) {

            List<String> variableList = parseExpression(formula.getExpression());

            if (variableList != null) {

                resultMap.put(formula.getQuota_name(), variableList);
            }
        }

        return resultMap;
    }

    public List<String> parseExpression (String expression) {

        List<String> resultList = new ArrayList<>();

        Set<String> variableSet = new HashSet<>();

        if (expression.contains(".")) {

            expression = expression.replaceAll(".", "_");
        }

        String regEx="[`/?!@#$%^&*()+=|{}':;',]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(expression);

        String parseExpression = matcher.replaceAll(" ").trim();

        String [] variables = parseExpression.split(" ");

        for (String variable: variables) {

            if (!"".equals(variable) && variable != null && !isNumeric(variable)) {

                variableSet.add(variable);
            }
        }

        for (String variable : variableSet) {

            resultList.add(variable);
        }

        return resultList;
    }

    public boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if( !isNum.matches() ){

            return false;
        }

        return true;
    }
}
