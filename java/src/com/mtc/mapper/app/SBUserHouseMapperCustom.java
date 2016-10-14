package com.mtc.mapper.app;

import com.mtc.entity.app.SBUserHouse;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SBUserHouseMapperCustom {
    
	public int insertBatch(List<SBUserHouse> items);

	public int deleteByUserId(int id);
	
	public List<SBUserHouse> selectByUserId(int userId);
	
	public List<SBUserHouse>  selectByUserIdHouseId(@Param("userId")int userId,@Param("houseId")int houseId);
	
}