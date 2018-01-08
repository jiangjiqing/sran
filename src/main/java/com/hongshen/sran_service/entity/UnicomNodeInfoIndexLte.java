package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomNodeInfoIndexLte implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_node_info_index_lte.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_node_info_index_lte.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_node_info_index_lte.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_node_info_index_lte.id
     *
     * @return the value of unicom_node_info_index_lte.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_node_info_index_lte.id
     *
     * @param id the value for unicom_node_info_index_lte.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_node_info_index_lte.name
     *
     * @return the value of unicom_node_info_index_lte.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_node_info_index_lte.name
     *
     * @param name the value for unicom_node_info_index_lte.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_node_info_index_lte.remark
     *
     * @return the value of unicom_node_info_index_lte.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_node_info_index_lte.remark
     *
     * @param remark the value for unicom_node_info_index_lte.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}