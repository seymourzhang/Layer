/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.mapper.app.BaseQueryMapper;

/**
* @ClassName: BaseService
* @Description: 
* @Date 2015年9月15日 下午7:21:05
* @Author Yin Guo Xiang
* 
*/ 
@Service
public class BaseQueryService {
	
	@Autowired
	private BaseQueryMapper mBaseQueryMapper;

	public String selectStringByAny(String sql){
		return mBaseQueryMapper.selectStringByAny(sql);
	}
    
    public Integer selectIntergerByAny(String sql){
    	return mBaseQueryMapper.selectIntergerByAny(sql);
    };
    
    public List<HashMap<String,Object>> selectMapByAny(String sql){
    	return mBaseQueryMapper.selectMapByAny(sql);
    }
    
    public List<LinkedHashMap<String,Object>> selectMapByAny2(String sql){
    	return mBaseQueryMapper.selectMapByAny2(sql);
    }
    public int updateIntergerByAny(String sql){
    	return mBaseQueryMapper.updateIntergerByAny(sql);
    }
    public int deleteByAny(String sql){
    	return mBaseQueryMapper.deleteByAny(sql);
    };
}
