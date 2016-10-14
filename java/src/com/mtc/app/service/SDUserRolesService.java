/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBUserRole;
import com.mtc.mapper.app.SBUserRoleMapperCustom;

/**
 * @ClassName: SDUserRolesService
 * @Description: 
 * @Date 2015年11月25日 下午1:34:39
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDUserRolesService {
	
	@Autowired
	private SBUserRoleMapperCustom tSBUserRoleMapperCustom ;
	
	public int insertBatch(List<SBUserRole> items){
		return tSBUserRoleMapperCustom.insertBatch(items);
	}
	public SBUserRole selectByUserId(int userId){
		return tSBUserRoleMapperCustom.selectByUserId(userId);
	}
	public int deleteByUserId(int userId){
		return tSBUserRoleMapperCustom.deleteByUserId(userId);
	}
	
}
