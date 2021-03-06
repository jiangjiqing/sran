package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomAlarmLibraryWcdma implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.alarm_name
     *
     * @mbggenerated
     */
    private String alarmName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.alarm_meaning
     *
     * @mbggenerated
     */
    private String alarmMeaning;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.alarm_level_id
     *
     * @mbggenerated
     */
    private String alarmLevelId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.alarm_type
     *
     * @mbggenerated
     */
    private String alarmType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.alarm_scope
     *
     * @mbggenerated
     */
    private String alarmScope;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_library_wcdma.recommend
     *
     * @mbggenerated
     */
    private String recommend;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.alarm_name
     *
     * @return the value of unicom_alarm_library_wcdma.alarm_name
     *
     * @mbggenerated
     */
    public String getAlarmName() {
        return alarmName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.alarm_name
     *
     * @param alarmName the value for unicom_alarm_library_wcdma.alarm_name
     *
     * @mbggenerated
     */
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName == null ? null : alarmName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.alarm_meaning
     *
     * @return the value of unicom_alarm_library_wcdma.alarm_meaning
     *
     * @mbggenerated
     */
    public String getAlarmMeaning() {
        return alarmMeaning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.alarm_meaning
     *
     * @param alarmMeaning the value for unicom_alarm_library_wcdma.alarm_meaning
     *
     * @mbggenerated
     */
    public void setAlarmMeaning(String alarmMeaning) {
        this.alarmMeaning = alarmMeaning == null ? null : alarmMeaning.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.alarm_level_id
     *
     * @return the value of unicom_alarm_library_wcdma.alarm_level_id
     *
     * @mbggenerated
     */
    public String getAlarmLevelId() {
        return alarmLevelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.alarm_level_id
     *
     * @param alarmLevelId the value for unicom_alarm_library_wcdma.alarm_level_id
     *
     * @mbggenerated
     */
    public void setAlarmLevelId(String alarmLevelId) {
        this.alarmLevelId = alarmLevelId == null ? null : alarmLevelId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.alarm_type
     *
     * @return the value of unicom_alarm_library_wcdma.alarm_type
     *
     * @mbggenerated
     */
    public String getAlarmType() {
        return alarmType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.alarm_type
     *
     * @param alarmType the value for unicom_alarm_library_wcdma.alarm_type
     *
     * @mbggenerated
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType == null ? null : alarmType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.alarm_scope
     *
     * @return the value of unicom_alarm_library_wcdma.alarm_scope
     *
     * @mbggenerated
     */
    public String getAlarmScope() {
        return alarmScope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.alarm_scope
     *
     * @param alarmScope the value for unicom_alarm_library_wcdma.alarm_scope
     *
     * @mbggenerated
     */
    public void setAlarmScope(String alarmScope) {
        this.alarmScope = alarmScope == null ? null : alarmScope.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_library_wcdma.recommend
     *
     * @return the value of unicom_alarm_library_wcdma.recommend
     *
     * @mbggenerated
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_library_wcdma.recommend
     *
     * @param recommend the value for unicom_alarm_library_wcdma.recommend
     *
     * @mbggenerated
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }
}