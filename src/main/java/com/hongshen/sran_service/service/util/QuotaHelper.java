package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;
import net.java.dev.eval.Expression;

import java.util.List;

public class QuotaHelper {


    public static Boolean checkExpression(String expression) {

        if (expression == null || expression.length() == 0){
            return false;
        }

        // 可以替换大部分空白字符， 不限于空格. "\s" 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        expression = expression.replaceAll("\\s*", "");

        // remove .
        //expression = expression.replace(".","");

        // change  chars & . & _   to numbers
        expression = expression.replaceAll("[a-zA-Z_.]","1");

        // check expression
        try{
            Expression exp = new Expression(expression);
            Double doubleValue = Double.parseDouble(String.valueOf(exp.eval()));

        }catch (Exception e){
            return false;
        }

        return true;
    }

    public static Boolean checkExpressionCounters(String expression) {

        if (expression == null || expression.length() == 0){
            return false;
        }

        // 可以替换大部分空白字符， 不限于空格. "\s" 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        expression = expression.replaceAll("\\s*", "");

        // remove .
        expression = expression.replace(".","");

        // TODO check counters

        return true;
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
