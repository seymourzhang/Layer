package com.mtc.mapper.app;

import com.mtc.entity.app.SBTempSettingSub;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SBTempSettingSubMapperCustom {
   
	 List<SBTempSettingSub> selectByOnePrimaryKey(@Param("uidNum") Integer uidNum);
}
