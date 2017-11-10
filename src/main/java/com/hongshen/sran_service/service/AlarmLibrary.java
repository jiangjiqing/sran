package com.hongshen.sran_service.service;

import java.util.Map;

/**
 * Created by poplar on 17-11-8.
 */
public interface AlarmLibrary {
    Map<String,Object> getAlarmLibrary();

    Map<String,Object> getSpecifiedLibrary(String alarmNameId);


    void updateSpecifiedLibrary(String alarmNameId, String name);


    void addSpecifiedLibrary(String alarm_name_id, String alarm_name, String alarm_meaning, String alarm_level_id, String alarm_scope, String recommend);
}
