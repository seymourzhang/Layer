package com.mtc.app.service;

import com.mtc.entity.app.SBLayerHouseAlarm;
import com.mtc.mapper.app.SBLayerHouseAlarmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ants on 2016/4/18.
 * Author  seymour
 */
@Service
public class SBLayerHouseAlarmService {

    @Autowired
    private SBLayerHouseAlarmMapper tSBLayerHouseAlarmMapper;

    int deleteByPrimaryKey(int farmId, int houseId){
        return tSBLayerHouseAlarmMapper.deleteByPrimaryKey(farmId, houseId);
    }

    public int insert(SBLayerHouseAlarm record){
        return tSBLayerHouseAlarmMapper.insert(record);
    }

    public SBLayerHouseAlarm selectByPrimaryKey(int farmid, int houseId){
        return tSBLayerHouseAlarmMapper.selectByPrimaryKey(farmid, houseId);
    }

    List<SBLayerHouseAlarm> selectAll(){
        return tSBLayerHouseAlarmMapper.selectAll();
    }

    public int updateByPrimaryKey(SBLayerHouseAlarm record){
        return tSBLayerHouseAlarmMapper.updateByPrimaryKey(record);
    }
}
