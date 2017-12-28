package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerHelper {

    public static void httpclient(String paramPath) throws IOException {

        String url = paramPath;

        CloseableHttpClient httpclient = null;

        try {

            httpclient = HttpClients.createDefault();

            HttpGet httpget = new HttpGet(url);

            httpget.addHeader("Go", "true");

            httpclient.execute(httpget);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            httpclient.close(); // httpClient关闭
        }
    }

    public static void httpclientCounterCalculation(String paramPath, JSONObject param) throws IOException {

        String url = paramPath;

        CloseableHttpClient httpclient = null;

        try {

            httpclient = HttpClients.createDefault();

            HttpGet httpget = new HttpGet(url);

            httpget.addHeader("supplier", param.getString("supplier"));
            httpget.addHeader("generation", param.getString("generation"));
            httpget.addHeader("message", param.getString("message"));
            httpget.addHeader("time", param.getString("time"));

            httpclient.execute(httpget);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            httpclient.close();
        }
    }

    public static Map<String, List<String>> getVariableList (List<JSONObject> formulaList) {

        Map<String, List<String>> resultMap = new HashMap<>();

        for (JSONObject formula : formulaList) {

            List<String> variableList = parseExpression(formula.getString("expression"));

            if (variableList != null) {

                resultMap.put(formula.getString("quotaName"), variableList);
            }
        }

        return resultMap;
    }

    public static String getGroupVariableValueByNodeListLte (String variable, List<JSONObject> nodeList) {

        String variableValue = null;

        double sum = 0;

        for (JSONObject node : nodeList) {

            if (!variable.contains("_")) {

                String counterNameIndex = node.getString("nodeName").substring(0, 1);

                if (counterNameIndex.equals("T")) {

                    String variableTdd = "EUtranCell"+ "T" +"DD_" + variable;

                    variableValue = node.getString(variableTdd);

                    double num = 0;

                    if (!"".equals(variableValue) && variableValue != null
                            && !variableValue.equals("0.0") && !variableValue.equals("-1")) {

                        num = Double.valueOf(variableValue);
                    }

                    sum = sum + num;
                } else if (counterNameIndex.equals("F")) {

                    String variableFdd = "EUtranCell"+ "F" +"DD_" + variable;

                    variableValue = node.getString(variableFdd);

                    double num = 0;

                    if (!"".equals(variableValue) && variableValue != null
                            && !variableValue.equals("0.0") && !variableValue.equals("-1")) {

                        num = Double.valueOf(variableValue);
                    }

                    sum = sum + num;
                } else {

                    continue;
                }
            } else {

                double num = 0;

                variableValue = node.getString(variable);

                if (!"".equals(variableValue) && variableValue != null
                        && !variableValue.equals("0.0") && !variableValue.equals("-1")) {

                    num = Double.valueOf(node.getString(variable));
                }

                sum = sum + num;
            }
        }

        variableValue = String.valueOf(sum);

        return variableValue;
    }

    public static String getGroupVariableValueByNodeListWcdma (String variable, List<JSONObject> nodeList) {

        String variableValue = null;

        double sum = 0;

        for (JSONObject node : nodeList) {

            double num = 0;

            variableValue = node.getString(variable);

            if (!variableValue.equals("-1")) {

                num = Double.valueOf(node.getString(variable));
            }

            sum = sum + num;
        }

        variableValue = String.valueOf(sum);

        return variableValue;
    }

    public static Map<String, List<String>> getQuotaThresholdMap (List<JSONObject> params) {

        Map<String, List<String>> quotaThresholdMap = new HashMap<>();

        for (JSONObject param : params) {

            List<String> paramList = new ArrayList<>();

            paramList.add(param.getString("threshold3"));
            paramList.add(param.getString("threshold2"));
            paramList.add(param.getString("threshold1"));

            quotaThresholdMap.put(param.getString("quotaName"), paramList);

        }

        return quotaThresholdMap;
    }

    public static int levelCalculation (String value, List<String> thresholdList) {

        Integer level = 1;

        if (value != "-1") {

            double fmValue = Double.valueOf(value);

            double threshold1 = Double.valueOf(thresholdList.get(0));
            double threshold2 = Double.valueOf(thresholdList.get(1));
            double threshold3 = Double.valueOf(thresholdList.get(2));

            if (fmValue <= threshold1) {

                level = 1;
            }

            if (fmValue > threshold1 && fmValue <= threshold2) {

                level = 2;
            }

            if (fmValue > threshold2 && fmValue <= threshold3) {

                level = 3;
            } if (fmValue > threshold3) {

                level = 4;
            }
        } else {

            level = 1;
        }

        return level;
    }

    public static List<String> parseExpression (String expression) {

        List<String> resultList = new ArrayList<>();

        Set<String> variableSet = new HashSet<>();

        String regEx="[-`/?!@#$%^&*()<+=|{}':;',]";

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

    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if( !isNum.matches() ){

            return false;
        }

        return true;
    }

    public static Map<String, String> getCounterMap (List<JSONObject> params) {

        Map<String, String> resultMap = new HashMap<>();

        for (JSONObject param : params) {

            String name = param.getString("name");

            String id = param.getString("id");

            resultMap.put(name, "counter" + id);
        }

        return resultMap;
    }

    public static Map<String, String> getFormulaMap (List<JSONObject> params) {

        Map<String, String> resultMap = new HashMap<>();

        for (JSONObject param : params) {

            String name = param.getString("quotaName");

            String id = param.getString("id");

            resultMap.put(name, "formula" + id);
        }

        return resultMap;
    }

    public static void sortStringArray (List<String> variableList) {

        Collections.sort(variableList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {

                return o1.length() < o2.length() ? 1 : -1;
            }

        });
    }

    public static int maxLevelArray (List<Integer> params) {

        if (params.size() == 0) return 1;

        int max = Collections.max(params);

        return max;
    }
}
