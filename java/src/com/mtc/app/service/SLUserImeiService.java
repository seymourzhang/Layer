package com.mtc.app.service;

import com.mtc.entity.app.SLUserImei;
import com.mtc.mapper.app.SLUserImeiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ants on 2016/9/20.
 */
@Service
public class SLUserImeiService {
    @Autowired
    private SLUserImeiMapper slUserImeiMapper;

    public int delete(String imeiMo){
        return slUserImeiMapper.deleteByPrimaryKey(imeiMo);
    }

    public int insert(SLUserImei slUserImei){
        return slUserImeiMapper.insert(slUserImei);
    }
}
