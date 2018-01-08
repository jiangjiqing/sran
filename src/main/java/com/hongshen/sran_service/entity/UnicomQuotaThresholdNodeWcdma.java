package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomQuotaThresholdNodeWcdma implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_threshold_node_wcdma.quota_name
     *
     * @mbggenerated
     */
    private String quotaName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_threshold_node_wcdma.quota_type
     *
     * @mbggenerated
     */
    private Integer quotaType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_threshold_node_wcdma.threshold_1
     *
     * @mbggenerated
     */
    private String threshold1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_threshold_node_wcdma.threshold_2
     *
     * @mbggenerated
     */
    private String threshold2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_threshold_node_wcdma.threshold_3
     *
     * @mbggenerated
     */
    private String threshold3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_quota_threshold_node_wcdma
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_threshold_node_wcdma.quota_name
     *
     * @return the value of unicom_quota_threshold_node_wcdma.quota_name
     *
     * @mbggenerated
     */
    public String getQuotaName() {
        return quotaName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_threshold_node_wcdma.quota_name
     *
     * @param quotaName the value for unicom_quota_threshold_node_wcdma.quota_name
     *
     * @mbggenerated
     */
    public void setQuotaName(String quotaName) {
        this.quotaName = quotaName == null ? null : quotaName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_threshold_node_wcdma.quota_type
     *
     * @return the value of unicom_quota_threshold_node_wcdma.quota_type
     *
     * @mbggenerated
     */
    public Integer getQuotaType() {
        return quotaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_threshold_node_wcdma.quota_type
     *
     * @param quotaType the value for unicom_quota_threshold_node_wcdma.quota_type
     *
     * @mbggenerated
     */
    public void setQuotaType(Integer quotaType) {
        this.quotaType = quotaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_threshold_node_wcdma.threshold_1
     *
     * @return the value of unicom_quota_threshold_node_wcdma.threshold_1
     *
     * @mbggenerated
     */
    public String getThreshold1() {
        return threshold1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_threshold_node_wcdma.threshold_1
     *
     * @param threshold1 the value for unicom_quota_threshold_node_wcdma.threshold_1
     *
     * @mbggenerated
     */
    public void setThreshold1(String threshold1) {
        this.threshold1 = threshold1 == null ? null : threshold1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_threshold_node_wcdma.threshold_2
     *
     * @return the value of unicom_quota_threshold_node_wcdma.threshold_2
     *
     * @mbggenerated
     */
    public String getThreshold2() {
        return threshold2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_threshold_node_wcdma.threshold_2
     *
     * @param threshold2 the value for unicom_quota_threshold_node_wcdma.threshold_2
     *
     * @mbggenerated
     */
    public void setThreshold2(String threshold2) {
        this.threshold2 = threshold2 == null ? null : threshold2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_threshold_node_wcdma.threshold_3
     *
     * @return the value of unicom_quota_threshold_node_wcdma.threshold_3
     *
     * @mbggenerated
     */
    public String getThreshold3() {
        return threshold3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_threshold_node_wcdma.threshold_3
     *
     * @param threshold3 the value for unicom_quota_threshold_node_wcdma.threshold_3
     *
     * @mbggenerated
     */
    public void setThreshold3(String threshold3) {
        this.threshold3 = threshold3 == null ? null : threshold3.trim();
    }
}