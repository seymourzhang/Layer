package com.mtc.app.service;

import com.mtc.entity.app.SLUserLogon;
import com.mtc.mapper.app.SLUserLogonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ants on 2016/9/19.
 */
@Service
public class SLUserLogonService {
    @Autowired
    private SLUserLogonMapper slUserLogonMapper;
    public int insert(SLUserLogon slUserLogon){
        return slUserLogonMapper.insert(slUserLogon);
    }
}
