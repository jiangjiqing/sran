package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomUserTaskGroupWcdmaWithBLOBs extends UnicomUserTaskGroupWcdma implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_user_task_group_wcdma.rncList
     *
     * @mbggenerated
     */
    private String rnclist;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_user_task_group_wcdma.cmdList
     *
     * @mbggenerated
     */
    private String cmdlist;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_user_task_group_wcdma.logScript
     *
     * @mbggenerated
     */
    private String logscript;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_user_task_group_wcdma.rncList
     *
     * @return the value of unicom_user_task_group_wcdma.rncList
     *
     * @mbggenerated
     */
    public String getRnclist() {
        return rnclist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_user_task_group_wcdma.rncList
     *
     * @param rnclist the value for unicom_user_task_group_wcdma.rncList
     *
     * @mbggenerated
     */
    public void setRnclist(String rnclist) {
        this.rnclist = rnclist == null ? null : rnclist.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_user_task_group_wcdma.cmdList
     *
     * @return the value of unicom_user_task_group_wcdma.cmdList
     *
     * @mbggenerated
     */
    public String getCmdlist() {
        return cmdlist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_user_task_group_wcdma.cmdList
     *
     * @param cmdlist the value for unicom_user_task_group_wcdma.cmdList
     *
     * @mbggenerated
     */
    public void setCmdlist(String cmdlist) {
        this.cmdlist = cmdlist == null ? null : cmdlist.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_user_task_group_wcdma.logScript
     *
     * @return the value of unicom_user_task_group_wcdma.logScript
     *
     * @mbggenerated
     */
    public String getLogscript() {
        return logscript;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_user_task_group_wcdma.logScript
     *
     * @param logscript the value for unicom_user_task_group_wcdma.logScript
     *
     * @mbggenerated
     */
    public void setLogscript(String logscript) {
        this.logscript = logscript == null ? null : logscript.trim();
    }
}