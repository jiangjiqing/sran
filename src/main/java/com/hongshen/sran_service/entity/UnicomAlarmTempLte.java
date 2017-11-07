package com.hongshen.sran_service.entity;

import java.io.Serializable;
import java.util.Date;

public class UnicomAlarmTempLte implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_temp_lte.node_name
     *
     * @mbggenerated
     */
    private String nodeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_temp_lte.mo
     *
     * @mbggenerated
     */
    private String mo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_temp_lte.alarm_time
     *
     * @mbggenerated
     */
    private Date alarmTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_temp_lte.alarm_name_id
     *
     * @mbggenerated
     */
    private String alarmNameId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_alarm_temp_lte.alarm_type_id
     *
     * @mbggenerated
     */
    private String alarmTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_alarm_temp_lte
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_temp_lte.node_name
     *
     * @return the value of unicom_alarm_temp_lte.node_name
     *
     * @mbggenerated
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_temp_lte.node_name
     *
     * @param nodeName the value for unicom_alarm_temp_lte.node_name
     *
     * @mbggenerated
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_temp_lte.mo
     *
     * @return the value of unicom_alarm_temp_lte.mo
     *
     * @mbggenerated
     */
    public String getMo() {
        return mo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_temp_lte.mo
     *
     * @param mo the value for unicom_alarm_temp_lte.mo
     *
     * @mbggenerated
     */
    public void setMo(String mo) {
        this.mo = mo == null ? null : mo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_temp_lte.alarm_time
     *
     * @return the value of unicom_alarm_temp_lte.alarm_time
     *
     * @mbggenerated
     */
    public Date getAlarmTime() {
        return alarmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_temp_lte.alarm_time
     *
     * @param alarmTime the value for unicom_alarm_temp_lte.alarm_time
     *
     * @mbggenerated
     */
    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_temp_lte.alarm_name_id
     *
     * @return the value of unicom_alarm_temp_lte.alarm_name_id
     *
     * @mbggenerated
     */
    public String getAlarmNameId() {
        return alarmNameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_temp_lte.alarm_name_id
     *
     * @param alarmNameId the value for unicom_alarm_temp_lte.alarm_name_id
     *
     * @mbggenerated
     */
    public void setAlarmNameId(String alarmNameId) {
        this.alarmNameId = alarmNameId == null ? null : alarmNameId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_alarm_temp_lte.alarm_type_id
     *
     * @return the value of unicom_alarm_temp_lte.alarm_type_id
     *
     * @mbggenerated
     */
    public String getAlarmTypeId() {
        return alarmTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_alarm_temp_lte.alarm_type_id
     *
     * @param alarmTypeId the value for unicom_alarm_temp_lte.alarm_type_id
     *
     * @mbggenerated
     */
    public void setAlarmTypeId(String alarmTypeId) {
        this.alarmTypeId = alarmTypeId == null ? null : alarmTypeId.trim();
    }
}