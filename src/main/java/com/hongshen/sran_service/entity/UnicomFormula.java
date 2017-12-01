package com.hongshen.sran_service.entity;

public class UnicomFormula {

    private int id;
    private String quota_name;
    private String expression ;
    private String remark;
    private boolean status;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuota_name() {
        return quota_name;
    }

    public void setQuota_name(String quota_name) {
        this.quota_name = quota_name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
