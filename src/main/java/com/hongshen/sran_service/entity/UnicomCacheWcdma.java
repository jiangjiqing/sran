package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomCacheWcdma implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_cache_wcdma.quotaUpdateTime
     *
     * @mbggenerated
     */
    private String quotaupdatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_cache_wcdma
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_cache_wcdma.quotaUpdateTime
     *
     * @return the value of unicom_cache_wcdma.quotaUpdateTime
     *
     * @mbggenerated
     */
    public String getQuotaupdatetime() {
        return quotaupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_cache_wcdma.quotaUpdateTime
     *
     * @param quotaupdatetime the value for unicom_cache_wcdma.quotaUpdateTime
     *
     * @mbggenerated
     */
    public void setQuotaupdatetime(String quotaupdatetime) {
        this.quotaupdatetime = quotaupdatetime == null ? null : quotaupdatetime.trim();
    }
}