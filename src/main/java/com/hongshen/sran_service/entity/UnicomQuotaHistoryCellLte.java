package com.hongshen.sran_service.entity;

import java.io.Serializable;
import java.util.Date;

public class UnicomQuotaHistoryCellLte implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.time
     *
     * @mbggenerated
     */
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.level
     *
     * @mbggenerated
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.RRC_setup_succ_Rate
     *
     * @mbggenerated
     */
    private String rrcSetupSuccRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.S1_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    private String s1SetupSuccRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.ERAB_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    private String erabSetupSuccRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.paging_Succ_Rate
     *
     * @mbggenerated
     */
    private String pagingSuccRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Erab_Drop_Rate
     *
     * @mbggenerated
     */
    private String erabDropRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Within_System_HO_Succ_Rate
     *
     * @mbggenerated
     */
    private String withinSystemHoSuccRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Cell_Utilization_Rate
     *
     * @mbggenerated
     */
    private String cellUtilizationRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.UL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    private String ulPrbOccupyRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.DL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    private String dlPrbOccupyRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Dl_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    private String dlThroughputrateMbps;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Ul_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    private String ulThroughputrateMbps;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.DL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    private String dlUserSpeedMbps;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.UL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    private String ulUserSpeedMbps;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.UE_SessionTime
     *
     * @mbggenerated
     */
    private String ueSessiontime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Drb_pmSessionTime
     *
     * @mbggenerated
     */
    private String drbPmsessiontime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.DL_Active_User_Num
     *
     * @mbggenerated
     */
    private String dlActiveUserNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.UL_Active_User_Num
     *
     * @mbggenerated
     */
    private String ulActiveUserNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_quota_history_cell_lte.Session_Setup_Success_Rate
     *
     * @mbggenerated
     */
    private String sessionSetupSuccessRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_quota_history_cell_lte
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.name
     *
     * @return the value of unicom_quota_history_cell_lte.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.name
     *
     * @param name the value for unicom_quota_history_cell_lte.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.time
     *
     * @return the value of unicom_quota_history_cell_lte.time
     *
     * @mbggenerated
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.time
     *
     * @param time the value for unicom_quota_history_cell_lte.time
     *
     * @mbggenerated
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.level
     *
     * @return the value of unicom_quota_history_cell_lte.level
     *
     * @mbggenerated
     */
    public String getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.level
     *
     * @param level the value for unicom_quota_history_cell_lte.level
     *
     * @mbggenerated
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.RRC_setup_succ_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.RRC_setup_succ_Rate
     *
     * @mbggenerated
     */
    public String getRrcSetupSuccRate() {
        return rrcSetupSuccRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.RRC_setup_succ_Rate
     *
     * @param rrcSetupSuccRate the value for unicom_quota_history_cell_lte.RRC_setup_succ_Rate
     *
     * @mbggenerated
     */
    public void setRrcSetupSuccRate(String rrcSetupSuccRate) {
        this.rrcSetupSuccRate = rrcSetupSuccRate == null ? null : rrcSetupSuccRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.S1_Setup_Succ_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.S1_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    public String getS1SetupSuccRate() {
        return s1SetupSuccRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.S1_Setup_Succ_Rate
     *
     * @param s1SetupSuccRate the value for unicom_quota_history_cell_lte.S1_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    public void setS1SetupSuccRate(String s1SetupSuccRate) {
        this.s1SetupSuccRate = s1SetupSuccRate == null ? null : s1SetupSuccRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.ERAB_Setup_Succ_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.ERAB_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    public String getErabSetupSuccRate() {
        return erabSetupSuccRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.ERAB_Setup_Succ_Rate
     *
     * @param erabSetupSuccRate the value for unicom_quota_history_cell_lte.ERAB_Setup_Succ_Rate
     *
     * @mbggenerated
     */
    public void setErabSetupSuccRate(String erabSetupSuccRate) {
        this.erabSetupSuccRate = erabSetupSuccRate == null ? null : erabSetupSuccRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.paging_Succ_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.paging_Succ_Rate
     *
     * @mbggenerated
     */
    public String getPagingSuccRate() {
        return pagingSuccRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.paging_Succ_Rate
     *
     * @param pagingSuccRate the value for unicom_quota_history_cell_lte.paging_Succ_Rate
     *
     * @mbggenerated
     */
    public void setPagingSuccRate(String pagingSuccRate) {
        this.pagingSuccRate = pagingSuccRate == null ? null : pagingSuccRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Erab_Drop_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.Erab_Drop_Rate
     *
     * @mbggenerated
     */
    public String getErabDropRate() {
        return erabDropRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Erab_Drop_Rate
     *
     * @param erabDropRate the value for unicom_quota_history_cell_lte.Erab_Drop_Rate
     *
     * @mbggenerated
     */
    public void setErabDropRate(String erabDropRate) {
        this.erabDropRate = erabDropRate == null ? null : erabDropRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Within_System_HO_Succ_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.Within_System_HO_Succ_Rate
     *
     * @mbggenerated
     */
    public String getWithinSystemHoSuccRate() {
        return withinSystemHoSuccRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Within_System_HO_Succ_Rate
     *
     * @param withinSystemHoSuccRate the value for unicom_quota_history_cell_lte.Within_System_HO_Succ_Rate
     *
     * @mbggenerated
     */
    public void setWithinSystemHoSuccRate(String withinSystemHoSuccRate) {
        this.withinSystemHoSuccRate = withinSystemHoSuccRate == null ? null : withinSystemHoSuccRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Cell_Utilization_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.Cell_Utilization_Rate
     *
     * @mbggenerated
     */
    public String getCellUtilizationRate() {
        return cellUtilizationRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Cell_Utilization_Rate
     *
     * @param cellUtilizationRate the value for unicom_quota_history_cell_lte.Cell_Utilization_Rate
     *
     * @mbggenerated
     */
    public void setCellUtilizationRate(String cellUtilizationRate) {
        this.cellUtilizationRate = cellUtilizationRate == null ? null : cellUtilizationRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.UL_PRB_Occupy_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.UL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    public String getUlPrbOccupyRate() {
        return ulPrbOccupyRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.UL_PRB_Occupy_Rate
     *
     * @param ulPrbOccupyRate the value for unicom_quota_history_cell_lte.UL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    public void setUlPrbOccupyRate(String ulPrbOccupyRate) {
        this.ulPrbOccupyRate = ulPrbOccupyRate == null ? null : ulPrbOccupyRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.DL_PRB_Occupy_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.DL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    public String getDlPrbOccupyRate() {
        return dlPrbOccupyRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.DL_PRB_Occupy_Rate
     *
     * @param dlPrbOccupyRate the value for unicom_quota_history_cell_lte.DL_PRB_Occupy_Rate
     *
     * @mbggenerated
     */
    public void setDlPrbOccupyRate(String dlPrbOccupyRate) {
        this.dlPrbOccupyRate = dlPrbOccupyRate == null ? null : dlPrbOccupyRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Dl_ThroughputRate_Mbps
     *
     * @return the value of unicom_quota_history_cell_lte.Dl_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    public String getDlThroughputrateMbps() {
        return dlThroughputrateMbps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Dl_ThroughputRate_Mbps
     *
     * @param dlThroughputrateMbps the value for unicom_quota_history_cell_lte.Dl_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    public void setDlThroughputrateMbps(String dlThroughputrateMbps) {
        this.dlThroughputrateMbps = dlThroughputrateMbps == null ? null : dlThroughputrateMbps.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Ul_ThroughputRate_Mbps
     *
     * @return the value of unicom_quota_history_cell_lte.Ul_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    public String getUlThroughputrateMbps() {
        return ulThroughputrateMbps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Ul_ThroughputRate_Mbps
     *
     * @param ulThroughputrateMbps the value for unicom_quota_history_cell_lte.Ul_ThroughputRate_Mbps
     *
     * @mbggenerated
     */
    public void setUlThroughputrateMbps(String ulThroughputrateMbps) {
        this.ulThroughputrateMbps = ulThroughputrateMbps == null ? null : ulThroughputrateMbps.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.DL_User_Speed_Mbps
     *
     * @return the value of unicom_quota_history_cell_lte.DL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    public String getDlUserSpeedMbps() {
        return dlUserSpeedMbps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.DL_User_Speed_Mbps
     *
     * @param dlUserSpeedMbps the value for unicom_quota_history_cell_lte.DL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    public void setDlUserSpeedMbps(String dlUserSpeedMbps) {
        this.dlUserSpeedMbps = dlUserSpeedMbps == null ? null : dlUserSpeedMbps.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.UL_User_Speed_Mbps
     *
     * @return the value of unicom_quota_history_cell_lte.UL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    public String getUlUserSpeedMbps() {
        return ulUserSpeedMbps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.UL_User_Speed_Mbps
     *
     * @param ulUserSpeedMbps the value for unicom_quota_history_cell_lte.UL_User_Speed_Mbps
     *
     * @mbggenerated
     */
    public void setUlUserSpeedMbps(String ulUserSpeedMbps) {
        this.ulUserSpeedMbps = ulUserSpeedMbps == null ? null : ulUserSpeedMbps.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.UE_SessionTime
     *
     * @return the value of unicom_quota_history_cell_lte.UE_SessionTime
     *
     * @mbggenerated
     */
    public String getUeSessiontime() {
        return ueSessiontime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.UE_SessionTime
     *
     * @param ueSessiontime the value for unicom_quota_history_cell_lte.UE_SessionTime
     *
     * @mbggenerated
     */
    public void setUeSessiontime(String ueSessiontime) {
        this.ueSessiontime = ueSessiontime == null ? null : ueSessiontime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Drb_pmSessionTime
     *
     * @return the value of unicom_quota_history_cell_lte.Drb_pmSessionTime
     *
     * @mbggenerated
     */
    public String getDrbPmsessiontime() {
        return drbPmsessiontime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Drb_pmSessionTime
     *
     * @param drbPmsessiontime the value for unicom_quota_history_cell_lte.Drb_pmSessionTime
     *
     * @mbggenerated
     */
    public void setDrbPmsessiontime(String drbPmsessiontime) {
        this.drbPmsessiontime = drbPmsessiontime == null ? null : drbPmsessiontime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.DL_Active_User_Num
     *
     * @return the value of unicom_quota_history_cell_lte.DL_Active_User_Num
     *
     * @mbggenerated
     */
    public String getDlActiveUserNum() {
        return dlActiveUserNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.DL_Active_User_Num
     *
     * @param dlActiveUserNum the value for unicom_quota_history_cell_lte.DL_Active_User_Num
     *
     * @mbggenerated
     */
    public void setDlActiveUserNum(String dlActiveUserNum) {
        this.dlActiveUserNum = dlActiveUserNum == null ? null : dlActiveUserNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.UL_Active_User_Num
     *
     * @return the value of unicom_quota_history_cell_lte.UL_Active_User_Num
     *
     * @mbggenerated
     */
    public String getUlActiveUserNum() {
        return ulActiveUserNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.UL_Active_User_Num
     *
     * @param ulActiveUserNum the value for unicom_quota_history_cell_lte.UL_Active_User_Num
     *
     * @mbggenerated
     */
    public void setUlActiveUserNum(String ulActiveUserNum) {
        this.ulActiveUserNum = ulActiveUserNum == null ? null : ulActiveUserNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_quota_history_cell_lte.Session_Setup_Success_Rate
     *
     * @return the value of unicom_quota_history_cell_lte.Session_Setup_Success_Rate
     *
     * @mbggenerated
     */
    public String getSessionSetupSuccessRate() {
        return sessionSetupSuccessRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_quota_history_cell_lte.Session_Setup_Success_Rate
     *
     * @param sessionSetupSuccessRate the value for unicom_quota_history_cell_lte.Session_Setup_Success_Rate
     *
     * @mbggenerated
     */
    public void setSessionSetupSuccessRate(String sessionSetupSuccessRate) {
        this.sessionSetupSuccessRate = sessionSetupSuccessRate == null ? null : sessionSetupSuccessRate.trim();
    }
}