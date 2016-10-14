package com.mtc.app.controller;

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBHouseBreed;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ants on 2016/9/28.
 */
@Controller
@RequestMapping("/message")
public class DataInputAlarmReqController {
	private static Logger mLogger = Logger.getLogger(DataInputReqController.class);

	@Autowired
	private BaseQueryService baseQueryService;

	@RequestMapping("/DataInputAlarm")
    public void DataInputAlarm(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("=====Now start executing DataInputAlarmReqController.DataInputAlarm");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);

				JSONObject jsonObject = new JSONObject(paraStr);
				int userId = jsonObject.optInt("id_spa");

				JSONObject tJSONObject = jsonObject.optJSONObject("params");
				int FarmBreedId = tJSONObject.optInt("FarmBreedId");

				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				String SQL = "SELECT a.house_id,s_f_getHouseName(a.house_id) as house_name from s_b_layer_house_breed a where a.farm_breed_id = " + FarmBreedId +
						" and exists(SELECT 1 from s_b_layer_breed_detail b where a.id = b.house_breed_id" +
						" and b.is_history = 'N'" +
						" and b.growth_date < curdate()" +
						" and ifnull(b.cur_cd,0) = 0" +
						" and ifnull(b.cur_feed,0) = 0" +
						" and ifnull(b.cur_weight,0) = 0" +
						" and ifnull(b.cur_water,0) = 0" +
						" and ifnull(b.cur_lay_num,0) = 0)" +
						" and exists(SELECT 1 from s_user_house_view c where a.house_id = c.house_id and c.user_id = " + userId + ")";
				mLogger.info("@@@@@@@@@@@@@@@@@@ DataInputAlarmReqController.DataInputAlarm.SQL=" + SQL);
				List<HashMap<String,Object>> tDatas = baseQueryService.selectMapByAny(SQL);
				if (tDatas.size() == 0){
					resJson.put("NeedAlarm", "N");
					resJson.put("Result", "Success");
					resJson.put("ErrorMsg", "");
					dealRes = Constants.RESULT_SUCCESS;
				} else {
					JSONArray arrayData = new JSONArray();
					for (HashMap<String, Object> tData : tDatas) {
						arrayData.put(tData.get("house_name"));
					}
                    resJson.put("NeedAlarm", "Y");
					resJson.put("AlarmHouseList", arrayData);
                    resJson.put("Result", "Success");
                    resJson.put("ErrorMsg", "");
                    dealRes = Constants.RESULT_SUCCESS;
				}
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing DataInputAlarmReqController.DataInputAlarm");
    }

}
