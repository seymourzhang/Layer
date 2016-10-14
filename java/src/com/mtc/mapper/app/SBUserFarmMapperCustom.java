package com.mtc.mapper.app;


import org.apache.ibatis.annotations.Param;

import com.mtc.entity.app.SBUserFarm;

public interface SBUserFarmMapperCustom {
    
	public SBUserFarm selectByUserId(Integer userId);
	
	public SBUserFarm selectByUserIdFarmId(@Param("userId")int userId,@Param("farmId") int farmId);

}