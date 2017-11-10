package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomAlarmLibraryMapper;
import com.hongshen.sran_service.service.AlarmLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 17-11-8.
 */
@Service
public class AlarmLibrary_Lte implements AlarmLibrary{
    @Autowired
    private UnicomAlarmLibraryMapper unicomAlarmLibraryMapper;
    @Override
    public Map<String, Object> getAlarmLibrary() {
        return unicomAlarmLibraryMapper.getAlarmLibrary();
    }

    @Override
    public Map<String, Object> getSpecifiedLibrary(String alarmNameId) {
        return unicomAlarmLibraryMapper.getSpecifiedLibrary(alarmNameId);
    }

    @Override
    public void updateSpecifiedLibrary(String alarmNameId, String name) {
        unicomAlarmLibraryMapper.updateSpecifiedLibrary(alarmNameId,name);
    }

    @Override
    public void addSpecifiedLibrary(String alarm_name_id, String alarm_name, String alarm_meaning, String alarm_level_id, String alarm_scope, String recommend) {
        unicomAlarmLibraryMapper.addSpecifiedLibrary(alarm_name_id,alarm_name,alarm_meaning,alarm_level_id,alarm_scope,recommend);

    }


}
