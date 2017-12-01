package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerHelper {

    public static Map<String, List<String>> getVariableList (List<JSONObject> formulaList) {

        Map<String, List<String>> resultMap = new HashMap<>();

        for (JSONObject formula : formulaList) {

            List<String> variableList = parseExpression(formula.getString("expression"));

            if (variableList != null) {

                resultMap.put(formula.getString("quota_name"), variableList);
            }
        }

        return resultMap;
    }

    public static String getGroupVariableValueByNodeList (String variable, List<JSONObject> nodeList) {

        String variableValue = null;

        int sum = 0;

        for (JSONObject node : nodeList) {

            int num = Integer.valueOf(node.getString(variable));

            sum = sum + num;
        }

        variableValue = String.valueOf(sum);

        return variableValue;
    }

    public static Map<String, List<String>> getQuotaThresholdMap (List<JSONObject> params) {

        Map<String, List<String>> quotaThresholdMap = new HashMap<>();

        for (JSONObject param : params) {

            List<String> paramList = new ArrayList<>();

            paramList.add(param.getString("threshold_1"));
            paramList.add(param.getString("threshold_2"));
            paramList.add(param.getString("threshold_3"));

            quotaThresholdMap.put(param.getString("quota_name"), paramList);

        }
        return quotaThresholdMap;

    }

    public static String levelCalculation(String value, List<String> thresholdList){

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

    public static String avgFmLevelList (List<String> fmLevelList) {

        String levle = null;

        return levle;
    }

    public static List<String> parseExpression (String expression) {

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

    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if( !isNum.matches() ){

            return false;
        }

        return true;
    }
}
