package com.mtc.mapper.app;

import org.apache.ibatis.annotations.Param;


public interface SBAbnormalInfoMapperCustom {
	
    int updateToDelayedByHouseId(@Param("modifyUserId") int modifyUserId,
								@Param("delayTime")int delayTime,
								@Param("houseId")int houseId);
    
}