/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.mapper.app.SBAbnormalInfoMapperCustom;

/**
 * @ClassName: SBAbnormalInfoService
 * @Description: 
 * @Date 2016年1月26日 下午3:42:23
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBAbnormalInfoService {
	@Autowired
	private SBAbnormalInfoMapperCustom tSBAbnormalInfoMapperCustom;
	
	public int updateToDelayedByHouseId(int modifyUserId,int delayTime,int houseId){
		return tSBAbnormalInfoMapperCustom.updateToDelayedByHouseId(modifyUserId, delayTime, houseId);
	}
}
