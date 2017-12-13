package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class QuotaHelper {


    public static Boolean checkExpression(String expression) {

        if (expression == null || expression.length() == 0){
            return false;
        }

        // 可以替换大部分空白字符， 不限于空格. "\s" 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        expression = expression.replaceAll("\\s*", "");

        // remove .
        expression = expression.replaceAll(".","");

        // change chars to numbers
        expression = expression.replaceAll("[a-zA-Z]","9");

//        while(expression.matches(".*\\([0-9]+([\\+\\-\\*\\/][0-9]+)+\\).*")){
//            expression.replaceAll("\\([0-9]+([\\+\\-\\*\\/][0-9]+)+\\)","0");
//        }
        return expression.matches("^[0-9]+([\\+\\-\\*\\/][0-9]+)+$");
    }

    public static String convertExpression(String expression) {

        if (expression == null || expression.length() == 0){
            return "";
        }

        // 可以替换大部分空白字符， 不限于空格. "\s" 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        expression = expression.replaceAll("\\s*", "");

        // "." change to "_"
        expression = expression.replace('.','_');

        return expression;
    }
}
