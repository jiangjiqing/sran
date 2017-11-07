package com.hongshen.sran_service.entity;

import java.io.Serializable;

public class UnicomCounterLte implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_counter_lte.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_counter_lte.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_counter_lte.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column unicom_counter_lte.status
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table unicom_counter_lte
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_counter_lte.id
     *
     * @return the value of unicom_counter_lte.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_counter_lte.id
     *
     * @param id the value for unicom_counter_lte.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_counter_lte.name
     *
     * @return the value of unicom_counter_lte.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_counter_lte.name
     *
     * @param name the value for unicom_counter_lte.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_counter_lte.type
     *
     * @return the value of unicom_counter_lte.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_counter_lte.type
     *
     * @param type the value for unicom_counter_lte.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column unicom_counter_lte.status
     *
     * @return the value of unicom_counter_lte.status
     *
     * @mbggenerated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column unicom_counter_lte.status
     *
     * @param status the value for unicom_counter_lte.status
     *
     * @mbggenerated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}