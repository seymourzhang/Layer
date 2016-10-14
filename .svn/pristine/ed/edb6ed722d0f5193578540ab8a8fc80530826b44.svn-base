package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SDDevice;
import com.mtc.mapper.app.SDDeviceMapper;


@Service
public class DeviceService{
	@Autowired
	private  SDDeviceMapper tSDDeviceMapper;
	public SDDevice selectByPrimaryKey(String deviceCode){
		return tSDDeviceMapper.selectByPrimaryKey(deviceCode);
	}
}
