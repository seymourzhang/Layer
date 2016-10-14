angular.module('myApp.controllers', ['ionic','ngCordova','ngTouch', 'ui.grid', 'ui.grid.pinning','ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection', 'ui.grid.resizeColumns','ui.grid.autoResize'])
.controller("myCtrl",function($scope,$state,$http,AppData,$ionicLoading,$timeout,$ionicNavBarDelegate){

	Sparraw.setMyIonicLoading($ionicLoading);
  	Sparraw.setMyHttp($http);
  	Sparraw.setMyTimeout($timeout);
  	Sparraw.setMyIonicNavBarDelegate($ionicNavBarDelegate);
	
	$state.go("landingPage");
	// alarmTest
	//landingPage
	
})	

// 用户注册
.controller("UseRegisteredCtrl",function($scope, $state, $http, $ionicPopup, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));



	$scope.validationPhone = function(){





		
		if($scope.sparraw_user_temp.userinfo.phone && /^1[3|4|5|7|8]\d{9}$/.test($scope.sparraw_user_temp.userinfo.phone)){
			//对的
			console.log("输入正确");
		} else{
			//不对
			Sparraw.myNotice("请输入正确手机号码");
		};
	};



	$scope.save = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    
	    /* 校验信息*/
	    
	    var required = ['name','phone','password','confirmPassword'];
	   	for(i in required){if($scope.sparraw_user_temp.userinfo[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}
	   	//判断手机号码格式
	   	if($scope.sparraw_user_temp.userinfo.phone && /^1[3|4|5|8]\d{9}$/.test($scope.sparraw_user_temp.userinfo.phone)){
		//对的
		console.log("输入正确");
		} else{
		//不对
		return Sparraw.myNotice("请输入正确手机号码");
		};
	   	//判断密码是否相同
	   	if ($scope.sparraw_user_temp.userinfo.password != $scope.sparraw_user_temp.userinfo.confirmPassword) {return Sparraw.myNotice('两次输入密码不同,请重新输入...');};
		


	    var params = {
	      'name'           : $scope.sparraw_user_temp.userinfo.name             ,
	      'tele'           : $scope.sparraw_user_temp.userinfo.phone            ,
	      'pw'             : $scope.sparraw_user_temp.userinfo.confirmPassword  ,
	      'role'           : 1                                                  ,
	      'farmid'         : null                                               ,
	      'houses'         : null                                               ,
	    };
	    
	    
	    Sparraw.ajaxPost('sys/user/save.action', params, function(data){

	    	//判断注册是否成功
	    	if (data.ResponseDetail.ErrorMsg == null) {
			   		
			   		$scope.sparraw_user_temp.profile.id_spa = data.ResponseDetail.userId;

	    			$scope.sparraw_user_temp.userinfo.id = data.ResponseDetail.userId;

	    			sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
			   		
			   		Sparraw.myNotice('保存成功');



			   		//进行登录操作
			   		var params = {
		    	      "userCode"  :  $scope.sparraw_user_temp.userinfo.phone           ,
    				  "pw"        :  $scope.sparraw_user_temp.userinfo.confirmPassword ,
    				  "AndroidImei": ANDROID_IMEI,
    				  "uuid":UUID,
    				  "model":MODELNAME,
    				  "sysVersion":VERSION,
    				  "platForm":PLATFORM
					};

					Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
						console.log(data);
					    if (data.ResponseDetail.LoginResult == 'Success') {

					   		sparraw_user.userinfo    =  data.ResponseDetail.userinfo    ;
					   		sparraw_user.farminfo    =  data.ResponseDetail.farminfo    ;
					   		sparraw_user.houseinfos  =  data.ResponseDetail.houseinfos  ;
					   		sparraw_user.userinfos   =  data.ResponseDetail.userinfos   ;
					   		sparraw_user.Authority   =  data.ResponseDetail.Authority;
					   		sparraw_user.profile = {
											   			'id_spa'      :  data.ResponseDetail.userinfo.id  ,
											   			'secret'      :  'mtc_secret'                     ,
											   			'user_State'  :  true 
											   		} ;
							Sparraw.myNotice('成功登录');
					   		$state.go("home");
					   		sparraw_user.profile.account  = $scope.sparraw_user_temp.userinfo.phone            ;
			   				sparraw_user.profile.password = $scope.sparraw_user_temp.userinfo.confirmPassword  ;
			   				console.log(sparraw_user.profile.account);
			   				console.log(sparraw_user.profile.password);
					   }else{
					   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					   };
					})

			   }else {
			   		if (data.ResponseDetail.ErrorMsg == "保存失败，该手机号已经注册，请联系管理员。") {
						$scope.prompt = function() {
						    app_alert("该用户已注册，请返回登录页面");
						    return; 
						};
						$scope.prompt();
			   		}else{
			   			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   		};
			   		
			   };
	    });
  	};


})
// 农场注册
.controller("FarmRegisteredCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	// 记录此次修改的栋舍数量和旧值的差距
	var houseNumOffsize = 0;
	// 栋舍旧值
	var houseNum = 0;
	if ($scope.sparraw_user_temp.farminfo == "" || !$scope.sparraw_user_temp.farminfo) {
		houseNum = 0;
	}else{
		houseNum = $scope.sparraw_user_temp.farminfo.houseNum;
	}

	
	
	$scope.querySelectData = function(para){
		var temp1;
		if(para == 'add2'){
			temp1 = $scope.sparraw_user_temp.farminfo.address1;
		}else if(para == 'add3'){
			temp1 = $scope.sparraw_user_temp.farminfo.address2;
		}else if(para == 'add4'){
			temp1 = $scope.sparraw_user_temp.farminfo.address3;
		}else{
			return;
		}
		var params = {
				"CodeType":"ChinaRegion",
				"Para1":temp1
				} ;
		Sparraw.ajaxPost('sys/code/getData.action', params, function(data){
	    	if(para == 'add2'){
				$scope.myConfig.address2 = data.ResponseDetail.ResultData;
			}else if(para == 'add3'){
				$scope.myConfig.address3 = data.ResponseDetail.ResultData;
			}else if(para == 'add4'){
				$scope.myConfig.address4 = data.ResponseDetail.ResultData;
			}
			console.log(data.ResponseDetail.ResultData);
	    });
	}

	$scope.inintSelectData = function(){
		var params1 = {
				"CodeType":"ChinaRegion",
				"Para1":$scope.sparraw_user_temp.farminfo.address1
				} ;
		Sparraw.ajaxPost('sys/code/getData.action', params1, function(data){
			$scope.myConfig.address2 = data.ResponseDetail.ResultData;
			var params2 = {
				"CodeType":"ChinaRegion",
				"Para1":$scope.sparraw_user_temp.farminfo.address2
				} ;
			Sparraw.ajaxPost('sys/code/getData.action', params2, function(data){
				$scope.myConfig.address3 = data.ResponseDetail.ResultData;
				var params3 = {
						"CodeType":"ChinaRegion",
						"Para1":$scope.sparraw_user_temp.farminfo.address3
						} ;
				Sparraw.ajaxPost('sys/code/getData.action', params3, function(data){
					$scope.myConfig.address4 = data.ResponseDetail.ResultData;
			    });
		    });
	    });
	};

	//true-隐藏，false-显示
	//判断是否有农场信息

	console.log($scope.sparraw_user_temp.hasOwnProperty("farminfo"));

	var year = new Date().getFullYear();
	//console.log("year:" + year);
	myConfig.foundedTime = {"1989":"1990年之前","1990":"1990","1991":"1991","1992":"1992","1993":"1993","1994":"1994","1995":"1995","1996":"1996","1997":"1997","1998":"1998","1999":"1999","2000":"2000","2001":"2001","2002":"2002","2003":"2003","2004":"2004","2005":"2005","2006":"2006","2007":"2007","2008":"2008","2009":"2009","2010":"2010","2011":"2011","2012":"2012","2013":"2013","2014":"2014","2015":"2015"};
	for(var y = 2015; y <= year; y++){
		myConfig.foundedTime[y] = y;
	}
	

	

	if ($scope.sparraw_user_temp.hasOwnProperty("farminfo")) {//有农场信息
		$scope.registerTitle   =  true   ;
		$scope.saveAddBtn      =  true   ;

		$scope.landingbackBtn  =  false  ;
		$scope.landingTitle    =  false  ;
		$scope.saveUpdateBtn   =  false  ;

		$scope.backBtn    =  false  ;
		$scope.cancelBtn  =  true   ;
		$scope.visible    =  false  ;
		$scope.saveUpdateBtn =  true   ;

		$scope.sheerDiv   =  false  ;
		$scope.inintSelectData();

		console.log("1");
	}else{//无农场信息
		$scope.registerTitle   =  false  ;
		$scope.saveAddBtn      =  false  ;

		$scope.landingbackBtn  =  true   ;
		$scope.landingTitle    =  true   ;

		$scope.saveUpdateBtn   =  true   ;
		$scope.backBtn    =  true  ;
		$scope.cancelBtn  =  true   ;
		$scope.visible    =  true  ;

		$scope.sheerDiv   =  true  ;
		console.log("0");
	};

	//通过服务器取值
	$scope.select = function(para){		
		if(para == "Region1"){
			$scope.sparraw_user_temp.farminfo.address2 = '';
			$scope.sparraw_user_temp.farminfo.address3 = '';
			$scope.sparraw_user_temp.farminfo.address4 = '';

			$scope.querySelectData('add2');
		}else if(para == "Region2"){
			$scope.sparraw_user_temp.farminfo.address3 = '';
			$scope.sparraw_user_temp.farminfo.address4 = '';
			
			$scope.querySelectData('add3');
		}else if(para == "Region3"){
			$scope.sparraw_user_temp.farminfo.address4 = '';
			
			$scope.querySelectData('add4');
		}

	}

	$scope.judgeRaise = function(){

		if (this.sparraw_user_temp.farminfo.feedtype == 3) {
			$scope.farmingSize = true;
		}else {
			$scope.farmingSize = false;
		}
	}


	$scope.saveUpdate = function(){
		if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    
	    /* 校验信息*/
	    var required = [$scope.sparraw_user_temp.farminfo.name,$scope.sparraw_user_temp.farminfo.address1 ,$scope.sparraw_user_temp.farminfo.address2 ,$scope.sparraw_user_temp.farminfo.address3,$scope.sparraw_user_temp.farminfo.housenum];
	   	if ($scope.sparraw_user_temp.farminfo === undefined || $scope.sparraw_user_temp.farminfo.name === "" || $scope.sparraw_user_temp.farminfo.name === undefined) {
	    	return Sparraw.myNotice('请输入农场名称。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address1 === "" || $scope.sparraw_user_temp.farminfo.address1 === undefined){
	    	return Sparraw.myNotice('请输入农场所在省。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address2 === "" || $scope.sparraw_user_temp.farminfo.address2 === undefined){
	    	return Sparraw.myNotice('请输入农场所在地级市。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address3 === "" || $scope.sparraw_user_temp.farminfo.address3 === undefined){
	    	return Sparraw.myNotice('请输入农场所在县。');
	    };
		if ($scope.sparraw_user_temp.farminfo.houseNum === "" || $scope.sparraw_user_temp.farminfo.houseNum === undefined || $scope.sparraw_user_temp.farminfo.houseNum === "0"){
	    	return Sparraw.myNotice('请输入农场栋舍数量。');
	    };
		if ($scope.sparraw_user_temp.farminfo.buildDate === "" || $scope.sparraw_user_temp.farminfo.buildDate === undefined){
	    	return Sparraw.myNotice('请选择农场建厂时间。');
	    };
		houseNumOffsize = $scope.sparraw_user_temp.farminfo.houseNum - houseNum;
		if(houseNumOffsize < 0){
			return Sparraw.myNotice('栋舍数量不能小于上次设置值。');
		}
	   	var params = {
	   			"operate"   :  "UPDATE"  ,
	   			"farmInfo"  :  {
				   				"id"              :  $scope.sparraw_user_temp.farminfo.id              ,
							    "name"            :  $scope.sparraw_user_temp.farminfo.name            , 
							    "address1"        :  $scope.sparraw_user_temp.farminfo.address1        , 
							    "address2"        :  $scope.sparraw_user_temp.farminfo.address2        ,
							    "address3"        :  $scope.sparraw_user_temp.farminfo.address3        ,
							    "address4"        :  $scope.sparraw_user_temp.farminfo.address4        ,
							    // "address5"        :  $scope.sparraw_user_temp.farminfo.address5        ,
							    "feedtype"        :  $scope.sparraw_user_temp.farminfo.feedtype        , // 饲养方式
							    "cageInfo_layer"  :  $scope.sparraw_user_temp.farminfo.cageInfo_layer  , //笼养尺寸-层
							    "cageInfo_row"    :  $scope.sparraw_user_temp.farminfo.cageInfo_row    , //笼养尺寸-排
							    "businessModle"   :  $scope.sparraw_user_temp.farminfo.businessModle   ,// 经营模式
							    // "corporation"     :  $scope.sparraw_user_temp.farminfo.corporation     , 
							    "feedBreeds"      :  $scope.sparraw_user_temp.farminfo.feedBreeds      ,//养殖品种
							    "house_length"    :  $scope.sparraw_user_temp.farminfo.house_length    , //栋舍尺寸-长
							    "house_width"     :  $scope.sparraw_user_temp.farminfo.house_width     ,//栋舍尺寸-宽
							    "house_height"    :  $scope.sparraw_user_temp.farminfo.house_height    ,//栋舍尺寸-高
							    "feedarea"        :  $scope.sparraw_user_temp.farminfo.feedarea ,//养殖面积
								"buildDate"		  :  $scope.sparraw_user_temp.farminfo.buildDate,// 建厂时间
								"houseNum"        :  houseNum ,//初始栋舍数量
								"houseNumOffsize" :  houseNumOffsize  // 栋舍修改后的增量
	   			}

			};
		    
		    Sparraw.ajaxPost('sys/farm/update.action', params, function(data){
		    	  if (data.ResponseDetail.ErrorMsg == null) {
		    		$scope.sparraw_user_temp.farminfo.id = data.ResponseDetail.farmId;
					
					$scope.sparraw_user_temp.farminfo.houseNum = houseNum + houseNumOffsize;
					
					//$scope.sparraw_user_temp.houseinfos = data.ResponseDetail.houseinfos;
			    	//console.log($scope.sparraw_user_temp.houseinfos);
					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	Sparraw.myNotice('保存成功');
					//重新获取服务器最新数据
					if (houseNumOffsize > 0){
						//Sparraw.getLatestData($state,"buildingTable");
					}
					//Sparraw.getLatestData($state,"buildingTable");
					//$scope.queryHouses();
					
    			
				
			    	if (sparraw_user.profile.user_State) {
			    		$state.go("addbuilding");
			    	}else {
			    		$state.go("infoSafeguard");
			    	};
			    	
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };
		    });
	}



	//编辑事件
	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		$scope.landingbackBtn    =  true  ;
		$scope.visible    =  true  ;
		$scope.sheerDiv   =  true  ;
		$scope.cancelBtn  =  false ;
		$scope.saveUpdateBtn =  false   ;
		Sparraw.myNotice('请编辑');
	}

	//取消事件
	$scope.cancelEvent = function(){
		$scope.landingbackBtn    =  false  ;
		$scope.visible    =  false  ;
		$scope.sheerDiv   =  false  ;
		$scope.cancelBtn  =  true   ;
		$scope.saveUpdateBtn =  true   ;
  	}

  	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	

	$scope.saveAdd = function(){
	    if(!Sparraw.isOnline()){
	      // return Sparraw.myNotice('暂无网络连接...');
	    }
	    
	    if ($scope.sparraw_user_temp.farminfo === undefined || $scope.sparraw_user_temp.farminfo.name === "" || $scope.sparraw_user_temp.farminfo.name === undefined) {
	    	return Sparraw.myNotice('请输入农场名称。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address1 === "" || $scope.sparraw_user_temp.farminfo.address1 === undefined){
	    	return Sparraw.myNotice('请输入农场所在省。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address2 === "" || $scope.sparraw_user_temp.farminfo.address2 === undefined){
	    	return Sparraw.myNotice('请输入农场所在地级市。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address3 === "" || $scope.sparraw_user_temp.farminfo.address3 === undefined){
	    	return Sparraw.myNotice('请输入农场所在县。');
	    };
	   

	   if ($scope.sparraw_user_temp.farminfo.houseNum === "" || $scope.sparraw_user_temp.farminfo.houseNum === undefined || $scope.sparraw_user_temp.farminfo.houseNum === "0"){
	    	return Sparraw.myNotice('请输入农场栋舍数量。');
	    };
		
		if ($scope.sparraw_user_temp.farminfo.buildDate === "" || $scope.sparraw_user_temp.farminfo.buildDate === undefined){
	    	return Sparraw.myNotice('请选择农场建厂时间。');
	    };
		
		houseNumOffsize = $scope.sparraw_user_temp.farminfo.houseNum - houseNum;
		if(houseNumOffsize < 0){
			return Sparraw.myNotice('栋舍数量不能小于上次设置值。');
		}
		
	   	var params = {

			  'name'            : $scope.sparraw_user_temp.farminfo.name             ,
		      'address1'        : $scope.sparraw_user_temp.farminfo.address1         ,
		      'address2'        : $scope.sparraw_user_temp.farminfo.address2         ,
		      'address3'        : $scope.sparraw_user_temp.farminfo.address3         ,
		      'address4'        : $scope.sparraw_user_temp.farminfo.address4         ,
		      // 'address5'        : $scope.sparraw_user_temp.farminfo.address5         ,
		      'feedtype'        : $scope.sparraw_user_temp.farminfo.feedtype         ,
		      'cageInfo_layer'  : $scope.sparraw_user_temp.farminfo.cageInfo_layer   ,
		      'cageInfo_row'    : $scope.sparraw_user_temp.farminfo.cageInfo_row     ,
		      'businessModle'   :  $scope.sparraw_user_temp.farminfo.businessModle   ,
		      // 'corporation'     : $scope.sparraw_user_temp.farminfo.corporation      ,
		      "feedBreeds"      :  $scope.sparraw_user_temp.farminfo.feedBreeds      ,
		      'house_length'    : $scope.sparraw_user_temp.farminfo.house_length     ,
		      'house_width'     : $scope.sparraw_user_temp.farminfo.house_width      ,
		      'house_height'    : $scope.sparraw_user_temp.farminfo.house_height     ,
		      'feedarea'        : $scope.sparraw_user_temp.farminfo.feedarea,         
			  "buildDate"		  :  $scope.sparraw_user_temp.farminfo.buildDate,// 建厂时间
			"houseNum"        :  houseNum ,//初始栋舍数量
			"houseNumOffsize" :  houseNumOffsize  // 栋舍修改后的增量
			};
		    
		    Sparraw.ajaxPost('sys/farm/save.action', params, function(data){


		    	  if (data.ResponseDetail.ErrorMsg == null) {
		    		$scope.sparraw_user_temp.farminfo.id = data.ResponseDetail.farmId;

		    		$scope.sparraw_user_temp.Authority = {
		    			"HouseBreed"    : "All" ,
					    "basicInfo"     : "All" ,
					    "DailyInput"    : "All" ,
					    "AlarmSetting"  : "All" ,
					    "role"          : 1     ,
					    "MonitorDeal"   : "All" ,
					    "FarmBreed"     : "All"
		    		};

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
				
					$scope.sparraw_user_temp.farminfo.houseNum = houseNum + houseNumOffsize;
					
					//$scope.sparraw_user_temp.houseinfos = data.ResponseDetail.houseinfos;
					
			    	Sparraw.myNotice('保存成功');
			    	//重新获取服务器最新数据
    				Sparraw.getLatestData($state,"buildingTable");
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };

		    });
  	};


})
// 栋舍列表
.controller("BuildingTableCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	
	// 获取栋舍信息
	$scope.queryHouses = function(){
		
			var params = {

			  'FarmId'            : $scope.sparraw_user_temp.farminfo.id          
		      
			};
		    
		    Sparraw.ajaxPost('sys/house/queryHouses.action', params, function(data){


		    	  if (data.ResponseDetail.ErrorMsg == null) {
		    		
					sparraw_user.houseinfos = data.ResponseDetail.houseinfos;
					$scope.sparraw_user_temp.houseinfos = data.ResponseDetail.houseinfos;
					
			    	
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };

		    });
	}
	
	$scope.queryHouses();
	
	//true-隐藏，false-显示
	if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {//有栋舍
		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//有用户
			$scope.backBtn    =  false ;
			$scope.finishBtn  =  true ;
		}else{//没用户
			$scope.backBtn    =  true  ;
			$scope.finishBtn  =  false ;
		};

	}else{//没有栋舍
		console.log("2");
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	};

	if ($scope.sparraw_user_temp.userinfos.length == "1") {
		$scope.finishBtn  =  false ;
		console.log("对");
	}else{
		$scope.finishBtn  =  true ;
		console.log("错");
	};

	


	$scope.goAddbuilding = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$state.go("addbuilding");
	}
	

	//判断是否有栋舍信息
	/*if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {		//没栋舍
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	}else{//有栋舍
		// $scope.finishBtn  =  true   ;
		// $scope.backBtn    =  true   ;

		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//没用户
			$scope.backBtn    =  false  ;
			$scope.finishBtn  =  true   ;
		}else{//有用户

		};

	};*/

	$scope.remove = function(item){
		Sparraw.myNotice('如需删除该栋舍，请联系我们。');
		/*var params = {
		    	'operate'   : "DELETE" ,
		    	'houseInfo' : {
				      'houseId'        : item.id                ,
				      'houseName'      : item.houseName         ,
				      'h_length'       : item.h_length       	,
				      'h_width'        : item.h_width           ,
				      'h_height'       : item.h_height          ,
				      'feedarea'       : item.feedarea       	,
				      'mtc_device_id'  : item.mtc_device_id  	
				  }
		    };

		


		Sparraw.ajaxPost('sys/house/update.action', params, function(data){


			if (data.ResponseDetail.ErrorMsg == null) {
			   	var delIndex = -1;
				for(i in $scope.sparraw_user_temp.houseinfos){
					if($scope.sparraw_user_temp.houseinfos[i].houseName==item.houseName){
						delIndex = i ;
					}
				}

				$scope.sparraw_user_temp.houseinfos.splice(delIndex,1);

				sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('删除成功');

			}else {
			   Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};


			
	    });*/
	};

	$scope.goEmployeesTable = function(para){
		console.log($scope.sparraw_user_temp.houseinfos);
		if ($scope.sparraw_user_temp.userinfo.houses == 0 || !$scope.sparraw_user_temp.userinfo.houses) {
			app_alert("您还未创建栋舍。");
		}else{
			$state.go("employeesTable");
		};
	};
})

// 栋舍修改
.controller("updateBuildingInfoCtrl",function($scope, $state, $http, $stateParams, AppData,$ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	console.log("houseinfos:" + JSON.stringify($scope.sparraw_user_temp.houseinfos));
	//查询
	for(i in $scope.sparraw_user_temp.houseinfos){
		if($scope.sparraw_user_temp.houseinfos[i].houseName == $stateParams.buildingID){
			$scope.tempVar.houseTemp = JSON.parse(JSON.stringify($scope.sparraw_user_temp.houseinfos[i]));

		}
	}

	$scope.backBtn    =  false  ;
	$scope.cancelBtn  =  true   ;
	$scope.visible    =  false  ;

	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$scope.visible    =!  $scope.visible   ;
		$scope.sheerDiv   =!  $scope.sheerDiv  ;
		$scope.backBtn    =   true             ;
		$scope.cancelBtn  =   false            ;
		Sparraw.myNotice('请编辑');
	}

  	$scope.dealBarCode = function(){
		barScan(function(result){
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
			app_alert("扫描的设备编号是：" + result.text);
			document.getElementById("updateMtcIdInput").focus();
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};

	$scope.saveUpdate = function(){

		/* 校验信息*/
	    var required = [''];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}

			var params = {
		    	'operate'   : "UPDATE" ,
		    	'houseInfo' : {
		    		  'houseId'        : $scope.tempVar.houseTemp.id                ,
				      'houseName'      : $scope.tempVar.houseTemp.houseName         ,
				      'h_length'       : $scope.tempVar.houseTemp.h_length       	,
				      'h_width'        : $scope.tempVar.houseTemp.h_width           ,
				      'h_height'       : $scope.tempVar.houseTemp.h_height          ,
				      'feedarea'       : $scope.tempVar.houseTemp.feedarea       	,
				      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  	,
				      'farmId'         : $scope.sparraw_user_temp.farminfo.id       
				  }
		    };





		    Sparraw.ajaxPost('sys/house/update.action', params, function(data){
		    	

		    	if (data.ResponseDetail.ErrorMsg == null) {
			   		var delIndex = -1;
					for(i in $scope.sparraw_user_temp.houseinfos){
						if($scope.sparraw_user_temp.houseinfos[i].houseName == $scope.tempVar.houseTemp.houseName){
							delIndex = i ;
						}
					}
					$scope.sparraw_user_temp.houseinfos.splice(delIndex,1,JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));

					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
				
			    	Sparraw.myNotice('编辑成功');

			    	$scope.tempVar.houseTemp = {};

			    	$state.go("buildingTable");
			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };




		    	
		    });

	}

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	//返回时清空填写内容 
	$scope.backTable = function(){
		if (!$scope.sheerDiv) {
			$scope.tempVar.houseTemp = {};
	    	$state.go("buildingTable");
		}else {
			Sparraw.myNotice('您还未进行保存，请保存后再返回。');
		}

  	};

  	$scope.cancelEvent = function(){
  		$scope.visible = !$scope.visible;
		$scope.sheerDiv = !$scope.sheerDiv;
		$scope.backBtn = false;
		$scope.cancelBtn = true;
  	}

})

//栋舍添加
.controller("AddbuildingCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	// 栋舍编号，自动生成。并且判断是否有信息
	if (sparraw_user.houseinfos == undefined) {
		var maxID = 1 ;
		$scope.tempVar.houseTemp.houseName = padNumber(maxID,2) ;
	}else{
		var maxID = $scope.sparraw_user_temp.farminfo.house_Maxid + 1 ;
		$scope.tempVar.houseTemp.houseName = padNumber(maxID,2) ;

	};

	//将农场信息中得数据代入到栋舍中
	$scope.tempVar.houseTemp.h_length = $scope.sparraw_user_temp.farminfo.house_length;
	$scope.tempVar.houseTemp.h_width  = $scope.sparraw_user_temp.farminfo.house_width;
	$scope.tempVar.houseTemp.h_height = $scope.sparraw_user_temp.farminfo.house_height;
	$scope.tempVar.houseTemp.feedarea = $scope.sparraw_user_temp.farminfo.feedarea;

	$scope.addHouse = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    /* 校验信息*/
	    var required = ['mtc_device_id'];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}


	    var params = {
	      'farmId'         : $scope.sparraw_user_temp.farminfo.id    ,
	      'houseName'      : $scope.tempVar.houseTemp.houseName      ,
	      'h_length'       : $scope.tempVar.houseTemp.h_length       ,
	      'h_width'        : $scope.tempVar.houseTemp.h_width        ,
	      'h_height'       : $scope.tempVar.houseTemp.h_height       ,
	      'feedarea'       : $scope.tempVar.houseTemp.feedarea       ,
	      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  
	    };



	    Sparraw.ajaxPost('sys/house/save.action', params, function(data){

	    	if (data.ResponseDetail.ErrorMsg == null) {
			   	$scope.sparraw_user_temp.farminfo.house_Maxid = maxID;

		    	$scope.tempVar.houseTemp.id = data.ResponseDetail.houseId;

		    	//判断是否有信息
		    	if (sparraw_user.houseinfos == undefined) {
		    		$scope.sparraw_user_temp.houseinfos = [];
				}else{

				};

		  		$scope.sparraw_user_temp.houseinfos.push(JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));
		    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('保存成功');
		    	
		    	
		    	$scope.tempVar.houseTemp = {};

		    	//重新获取服务器最新数据
    			Sparraw.getLatestData($state,"buildingTable");
			}else {
			    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};

	    });
  	};
  	$scope.dealBarCode = function(){
		barScan(function(result){
			app_alert("您的设备编号是：" + result.text);
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
			document.getElementById("addMtcIdInput").focus();
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};


	$scope.GoBuildingTable = function(){
		$scope.tempVar.houseTemp = {};
		$state.go("buildingTable");
	}
})

//员工列表
.controller("EmployeesTableCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }




	if (sparraw_user.profile.user_State) {
		$scope.finishBtn  =  false  ;
		$scope.backBtn    =  true   ;

	}else {
		$scope.finishBtn  =  true   ;
		$scope.backBtn    =  false  ;

	};

	

	$scope.goAddEmployees = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$state.go("addEmployees");
	}
	

	$scope.remove = function(item){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};	
		if ($scope.sparraw_user_temp.userinfo.tele == item.tele ) {
			Sparraw.myNotice('无法删除自己');
		}else {
             app_confirm('删除该用户之后将无法恢复，请确认！','提示',null,function(buttonIndex){
                    if(buttonIndex == 2){
                     var params = {
			    	'operate'  : "DELETE" ,
			    	'userInfo' : {
					      'id'         : item.id                                            ,
					      'name'       : item.name                                          ,
					      'tele'       : item.tele       	                                ,
					      'pw'         : "123456"                                           ,
					      'role'       : item.role                                          ,
					      'farmid'     : $scope.sparraw_user_temp.farminfo.id               ,
					      'houses'     : null  	                                            
					  }
			        };
				    Sparraw.ajaxPost('sys/user/update.action', params, function(data){
						if (data.ResponseDetail.ErrorMsg == null) {
						   		var delIndex = -1;
								for(i in $scope.sparraw_user_temp.userinfos){
									if($scope.sparraw_user_temp.userinfos[i].id==item.id){
										delIndex = i ;
									}
								}
								$scope.sparraw_user_temp.userinfos.splice(delIndex,1);
								sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
						    	Sparraw.myNotice('删除成功');
						   }else {
						   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						   };
			        });
			        }
              });
		};
	};
	$scope.saveEmployees = function(para){
		Sparraw.myNotice('保存成功');
		sparraw_user.profile.user_State = false;
		$state.go("home");
	};
})


// 员工修改
.controller("updateEmployeesInfoCtrl",function($scope, $state, $http, $stateParams,  $ionicPopup,$ionicModal, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));



	//查询
	for(i in $scope.sparraw_user_temp.userinfos){
		if($scope.sparraw_user_temp.userinfos[i].id == $stateParams.employeesID){
			$scope.tempVar.userTemp = JSON.parse(JSON.stringify($scope.sparraw_user_temp.userinfos[i]));
		}
	}

	

	$scope.backBtn    =  false  ;//返回按钮 默认显示
	$scope.visible    =  false  ;//编辑按钮 默认显示
	$scope.sheerDiv   =  false  ;//覆盖图片 默认显示
	$scope.cancelBtn  =  true   ;//保存按钮 默认隐藏

	
	//编辑事件
	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		//自己无法修改自己角色
	   	if ($scope.tempVar.userTemp.tele == $scope.sparraw_user_temp.userinfo.tele) {return Sparraw.myNotice('禁止编辑当前登陆的用户信息');};
		$scope.backBtn    =  true  ;
		$scope.visible    =  true  ;
		$scope.sheerDiv   =  true  ;
		$scope.cancelBtn  =  false ;
		Sparraw.myNotice('请编辑');
	}

	//取消事件
	$scope.cancelEvent = function(){
		$scope.backBtn    =  false  ;
		$scope.visible    =  false  ;
		$scope.sheerDiv   =  false  ;
		$scope.cancelBtn  =  true   ;
  	}


	for (var i = 0; i < $scope.sparraw_user_temp.userinfos.length; i++) {
		if ($scope.sparraw_user_temp.userinfos[i].tele == $scope.tempVar.userTemp.tele) {

			if ($scope.sparraw_user_temp.userinfos[i].role == 4) {
				$scope.houseAffiliation = true;
			}else{
				$scope.houseAffiliation = false;
			};
		};
	};



	$scope.JudgeHouse = function(){
		if ($scope.tempVar.userTemp.role == 4) {
			$scope.houseAffiliation = true;
		}else {
			$scope.houseAffiliation = false;
		};
	}



	if ($scope.tempVar.userTemp.role == 4) {
		$scope.selectedHouse = [];//获取选取的栋舍id
		for (var x = 0; x < $scope.tempVar.userTemp.houses.length; x++) {
			$scope.selectedHouse.push($scope.tempVar.userTemp.houses[x]);
		};
	}else {
		$scope.tempVar.userTemp.houses = null;
	};
	

	$scope.testAry = [];
	$scope.testAry2 = [];

	$scope.devList = [];//将栋 舍 列 表显示在页面上
    for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
    	$scope.devList.push({"text"     :           $scope.sparraw_user_temp.houseinfos[i].houseName ,
    						 "checked"  :           false                                            ,
    						 "id"       :           $scope.sparraw_user_temp.houseinfos[i].id
    	});    	
    	$scope.testAry.push($scope.devList[i].id);
    };

    

    //对比页面所选栋舍号并且显示所选栋舍
	for(var s in $scope.testAry){
	    for(var x in $scope.selectedHouse){
	        if($scope.testAry[s] === $scope.selectedHouse[x]){
	            $scope.testAry2.push($scope.testAry[s]);
	        }
	    }
	  };

	for (var i = 0; i < $scope.devList.length; i++) {
		for (var x = 0; x < $scope.testAry2.length; x++) {
			if ($scope.testAry2[x] === $scope.devList[i].id) {
				$scope.devList[i].checked = true;
			};
		};

	};



    $scope.judgeHousChange = function(){
    	$scope.tempVar.userTemp.houses = [];
    	for (var i = 0; i < $scope.devList.length; i++) {
	    	if ($scope.devList[i].checked == true) {
	    		$scope.tempVar.userTemp.houses.push($scope.devList[i].id);
	    	}else {

	    	};
	    };
    }


	$scope.saveUpdate = function(){

	   	/* 校验信息*/
	    var required = ['name','tele','role'];
	   	if($scope.tempVar.userTemp.role==4){
	      required = required.concat(['houses']);
	    }
	   	for(i in required){if($scope.tempVar.userTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}

		    var params = {
		    	'operate'  : "UPDATE" ,
		    	'userInfo' : {
				      'id'         : $scope.tempVar.userTemp.id                    ,
				      'name'       : $scope.tempVar.userTemp.name                  ,
				      'tele'       : $scope.tempVar.userTemp.tele       	       ,
				      // 'pw'         : "123456"                                      ,
				      'role'       : $scope.tempVar.userTemp.role                  ,
				      'farmid'     : $scope.sparraw_user_temp.farminfo.id          ,
				      // 'houses'     : selectedHouse  	                           
				      'houses'     : $scope.tempVar.userTemp.houses  	                           
				  }

		    };

		    
		    Sparraw.ajaxPost('sys/user/update.action', params, function(data){

		    	if (data.ResponseDetail.ErrorMsg == null) {
			   		var delIndex = -1;
					for(i in $scope.sparraw_user_temp.userinfos){
						if($scope.sparraw_user_temp.userinfos[i].id == $scope.tempVar.userTemp.id){
							delIndex = i ;
						}
					}

					$scope.sparraw_user_temp.userinfos.splice(delIndex,1,JSON.parse(JSON.stringify($scope.tempVar.userTemp)));

					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));



			    	Sparraw.myNotice('编辑成功');

			    	$scope.tempVar.userTemp = {};

			    	$state.go("employeesTable");
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };



		    	
		    });

	}

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	}
	

	//返回时清空填写内容 
	$scope.backTable = function(){
		if (!$scope.sheerDiv) {
			$scope.tempVar.userTemp = {};
			$state.go("employeesTable");
		}else {

		}
  	};



  	$scope.clickPhone = function(){
  		app_confirm('手机号码暂时无法修改，如需修改手机请删除该员工后再添加。','提示',null,function(buttonIndex){
                    if(buttonIndex == 2){
                     

			        };
              });
  	}
  	



  	$ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

})

//员工添加
.controller("AddEmployeesCtrl",function($scope, $state, $http, $ionicPopup, $ionicModal, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.JudgeHouse = function(){
		if (this.tempVar.userTemp.role == 4) {
			$scope.houseAffiliation = true;
		}else {
			$scope.houseAffiliation = false;
		};
	}


    $scope.devList = [];

    for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
    	$scope.devList.push({"text"     :           $scope.sparraw_user_temp.houseinfos[i].houseName ,
    						 "checked"  :           false                                            ,
    						 "id"       :           $scope.sparraw_user_temp.houseinfos[i].id
    	});
    };





	$scope.save = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    /* 校验信息*/
	   	if ($scope.tempVar.userTemp.name == "" ||
	   		$scope.tempVar.userTemp.tele == "" ||
	   		$scope.tempVar.userTemp.role == "" ||
	   		$scope.tempVar.userTemp.name == undefined ||
	   		$scope.tempVar.userTemp.tele == undefined ||
	   		$scope.tempVar.userTemp.role == undefined ) {
	   		return Sparraw.myNotice('尚有内容未填写...');
	   	};
		if($scope.tempVar.userTemp.tele && /^1[3|4|5|8]\d{9}$/.test($scope.tempVar.userTemp.tele)){
			//对的
			console.log("输入正确");
		} else{
			//不对
			return Sparraw.myNotice("请输入正确手机号码");
		};

		

		var selectedHouse = [];
		for (var i = 0; i < $scope.devList.length; i++) {
	    	if ($scope.devList[i].checked == true) {
	    		selectedHouse.push($scope.devList[i].id);
	    	}else {

	    	};
	    };
	    //判断饲养员是否选择了所属栋舍
	    if($scope.tempVar.userTemp.role==4){
	    	console.log(selectedHouse.length);
	   		if (selectedHouse.length == 0) {
	   			return Sparraw.myNotice("请选择所属栋舍");
	   		}else{

	   		};
	    }else{

	    };


	    var params = {
	      'name'           : $scope.tempVar.userTemp.name                       ,
	      'tele'           : $scope.tempVar.userTemp.tele                       ,
	      'pw'             : "123456"                                           ,
	      'role'           : $scope.tempVar.userTemp.role                       ,
	      'farmid'         : $scope.sparraw_user_temp.farminfo.id               ,
	      'houses'         : selectedHouse                                      	

	    };
	    
	    Sparraw.ajaxPost('sys/user/save.action', params, function(data){
	    	
	    	if (data.ResponseDetail.ErrorMsg == null) {
			   		$scope.tempVar.userTemp.id = data.ResponseDetail.userId;


			   		//判断是否有信息
			    	if (sparraw_user.userinfos == undefined) {
			    		$scope.sparraw_user_temp.userinfos = [];
					}else{

					};

					$scope.sparraw_user_temp.userinfos.push(JSON.parse(JSON.stringify($scope.tempVar.userTemp)));

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	//制空新建员工 列表
			    	$scope.tempVar.userTemp = {};

			    	

			    	//重新获取服务器最新数据
			    	Sparraw.getLatestData($state,'employeesTable');
			    	app_alert('保存成功，新建员工密码为123456。');

			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };


	    });

  	};



  	$ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

})

// 登陆协议
.controller("landingProtocolCtrl",function($scope, $state, $http, AppData) {
	$scope.goLandingPage = function(){
		$state.go("landingPage");
	}

	$scope.closeApp = function(){
		console.log("关闭app");
		var ua = navigator.userAgent.toLowerCase(); 
		if (/iphone|ipad|ipod/.test(ua)) {
			return document.location = "objc://printLog::/";
		} else if (/android/.test(ua)) {
		 	ionic.Platform.exitApp();
		}else{
		 	Sparraw.myNotice("请在手机端点击。");
		}
	}
})

// 用户登录
.controller("landingPageCtrl",function($scope, $state, $http, AppData) {

	// 保持竖屏
	app_lockOrientation('portrait');

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('landingPage_Underlying_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('landingPage_Underlying_DIV').style.height = DIVHEIGHT + 'px';
	};

	var userTemp = getUserInfo();
	var userCode,passWord;

	if(userTemp != null){
		userCode = userTemp.userCode;
		if(userTemp.savePW == "true"){
			passWord = userTemp.pw;	
		}else{
			passWord = "";
		}
	}else{
		userCode = "";
		passWord = "";
	}


	//跳转至协议界面
	console.log(selectBackPage.firstTime);
	if(userTemp == null){
		if (selectBackPage.firstTime != true) {
			selectBackPage.firstTime = true;
			$state.go("landingProtocol");
		}else{
			
		}
	}else{
		setTimeout(function() {
			$scope.automaticLogin();
		}, 500);
	}
	
	$scope.landing = {
		"userCode":userCode,
		"passWord":passWord
	};

	 $scope.pushNotification = { checked: true };
	 var savePWFlag = true;
	 $scope.pushNotificationChange = function() {
        if ($scope.pushNotification.checked) {
        	savePWFlag = true;
        	//console.log("保存");
        }else {
        	savePWFlag = false;
        	//console.log("不保存");
        };
      };

    $scope.Register = function(){
    	sparraw_user.profile.user_State = true;
    	$state.go("useRegistered");
    };

    $scope.tempProductionMode = AppMode;

    if($scope.tempProductionMode == 'Development'){
    	$scope.webUrl = '1';
	    var tempURL = "1" ;
	    API_Host = Development_API_Host ;
	    $scope.changeURL = function(){
	    	if(tempURL == "1"){
	    		API_Host = Production_API_Host ;
	    		tempURL = '2';
	    	}else{
	    		API_Host = Development_API_Host ;
	    		tempURL = '1';
	    	}
	    };    	
    }else if($scope.tempProductionMode == 'Production'){
    	API_Host = Production_API_Host ;
    }else if($scope.tempProductionMode == 'Local'){
    	API_Host = Local_API_Host ;
    }

    $scope.goForgotPassword = function(){
    	//$state.go("alarmTest");
    	$state.go("forgotPassword");
    }

    var demoFlag = false;

	$scope.accountLogin = function(){

		sparraw_user.profile.account  = $scope.landing.userCode;
		sparraw_user.profile.password = $scope.landing.passWord;

		var params = {
		    	      "userCode": $scope.landing.userCode,
    				  "pw": $scope.landing.passWord,
    				  "AndroidImei": ANDROID_IMEI,
    				  "uuid":UUID,
    				  "model":MODELNAME,
    				  "sysVersion":VERSION,
    				  "platForm":PLATFORM
		};

		//校验信息
	 	if (params.userCode == null || params.pw == '') {return Sparraw.myNotice('请输入用户名/密码，或注册用户');};

		Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
			console.log(data);
		    if (data.ResponseDetail.LoginResult == 'Success') {
		    	persistentData.switchRemind = true;
		   		sparraw_user.userinfo = data.ResponseDetail.userinfo;
		   		sparraw_user.farminfo = data.ResponseDetail.farminfo;
		   		sparraw_user.houseinfos = data.ResponseDetail.houseinfos;
		   		sparraw_user.userinfos = data.ResponseDetail.userinfos;
		   		sparraw_user.Authority = data.ResponseDetail.Authority;
		   		sparraw_user.profile = {
								   			'id_spa':data.ResponseDetail.userinfo.id,
								   			'secret':'mtc_secret',
								   			'user_State': false ,
								   			'account':sparraw_user.profile.account,
								   			'password':sparraw_user.profile.password
								   		} ;
				// 缓存用户名密码
				if(!demoFlag){
					saveUserInfo(JSON.stringify({"userCode": $scope.landing.userCode,"pw": $scope.landing.passWord,"savePW":savePWFlag+""}));
				}
				
				try{
					var jpushTags = [];
					for (var i = 0; i < sparraw_user.userinfo.houses.length; i++) {
						jpushTags.push('mtc_tag_' + sparraw_user.userinfo.houses[i].HouseId);
					};
					// 为Jpush 设置别名和标签
					app_setTagsWithAlias(jpushTags, 'mtc_alias_' + sparraw_user.profile.id_spa);	
				}catch(e){
					console.log(e);
				}
					
		   		$state.go("home");

		   }else{
		   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		   };

		});
    };
    //测试账户登陆
    $scope.demoLogin = function(){
		$scope.landing = {
			"userCode":13920160603,
			"passWord":"1"
		};
		demoFlag = true;
		$scope.accountLogin();
    };
	
	// 获取加密公钥
	/*
	Sparraw.ajaxPost('sys/checkrs/reqrskey.action', "{}", function(data){

		//判断注册是否成功
		if (data.ResponseDetail.ErrorMsg == null) {
				sparraw_user.userinfo.publicKeyExponent = data.ResponseDetail.publicKeyExponent;
				sparraw_user.userinfo.publicKeyModulus = data.ResponseDetail.publicKeyModulus;
				//console.log(data.ResponseDetail.publicKeyExponent)
				//console.log(data.ResponseDetail.publicKeyModulus)

		   }else {
				
				
		   }
	});*/




	$scope.automaticLogin = function(){
    	console.log(selectBackPage.NeedLogin);
    	if (selectBackPage.NeedLogin) {
    		if ($scope.landing.userCode != "" || !$scope.landing.userCode &&
	    		$scope.landing.passWord != "" || !$scope.landing.passWord) {
	    		$scope.accountLogin();
	    	}
    	}else{
    		
    	}

    	
    }



    $scope.companyInformation = true;
    $scope.focus = function(){
    	$scope.companyInformation = false;
    }

    $scope.blur = function(){
    	$scope.companyInformation = true;
    }

})
// 忘记密码
.controller("forgotPasswordCtrl",function($scope, $state, $http, AppData) {
	
})
// 免责声明
.controller("disclaimerCtrl",function($scope, $state, $http, AppData) {
	
})



// 主页面
.controller("homeCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}

	if (document.documentElement.clientWidth == 320) {
		document.getElementById('img1').style.height = 1.5 + 'rem';
		document.getElementById('img1').style.width = 1.5 + 'rem';
		document.getElementById('img1').style.top = 0.5 + 'rem';
		document.getElementById('text1').style.top = 0.5 + 'rem';

		document.getElementById('img2').style.height = 1.5 + 'rem';
		document.getElementById('img2').style.width = 1.5 + 'rem';
		document.getElementById('img2').style.top = 0.5 + 'rem';
		document.getElementById('text2').style.top = 0.5 + 'rem';

		document.getElementById('img3').style.height = 1.5 + 'rem';
		document.getElementById('img3').style.width = 1.5 + 'rem';
		document.getElementById('img3').style.top = 0.5 + 'rem';
		document.getElementById('text3').style.top = 0.5 + 'rem';

		document.getElementById('img4').style.height = 1.5 + 'rem';
		document.getElementById('img4').style.width = 1.5 + 'rem';
		document.getElementById('img4').style.top = 0.5 + 'rem';
		document.getElementById('text4').style.top = 0.5 + 'rem';
	}else{

	};


	/*$scope.goDailyReport = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"dailyReport");
		//$state.go("dailyTable");
	}*/


	$scope.goDailyTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"dailyTable");
		//$state.go("dailyTable");
	}
	$scope.gobatchManage = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"batchManage");
		//$state.go("batchManage");
	}
	$scope.goEnvMonitoring = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"envMonitoring");
		//$state.go("envMonitoringTable");
	}
	$scope.godataAnalyseTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"dataAnalyseTable");
	}
	$scope.goTaskRemind = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"taskRemind");
	}
	$scope.goEggSellsReport = function(){
		//console.log("销售报表。。。");
		Sparraw.getInfoStatus($ionicPopup,$state,"eggSellsReportTable");
	}
	$scope.pointDevelop = function() {
		//Sparraw.getInfoStatus($ionicPopup,$state,"");
		pointDevelop();
	    return;
	};


	$scope.weatherSrc1 = "img/icon/weather/05.png";
	$scope.weatherSrc2 = "img/icon/weather/05.png";
	$scope.weatherSrc3 = "img/icon/weather/05.png";






	var myDate = new Date();
	var dateStr = "";
	var TempMonth = (new Date()).getMonth()+1;
	dateStr = myDate.getFullYear() + "年" + TempMonth + "月" + myDate.getDate() + "日";

	var addCode1 = "";
	var addCode2 = "";
	var addCode3 = "";

	if($scope.sparraw_user_temp.hasOwnProperty("farminfo")){
		addCode1 = $scope.sparraw_user_temp.farminfo.address1;
		addCode2 = $scope.sparraw_user_temp.farminfo.address2;
		addCode3 = $scope.sparraw_user_temp.farminfo.address3;
	}

	var curWData = addCode1 + "_" + addCode2 + "_" + addCode3 + "_" + getCurDate().replace(/-/g,'');

	var tempWData = $scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate;
	console.log("当前天气请求参数：" + curWData);
	console.log("缓存天气存在参数：" + tempWData);
	if(curWData != tempWData){
		console.log("加载天气信息");
		var params = {
					      'add1code'    : addCode1 ,
					      'add2code'    : addCode2 ,
					      'add3code'    : addCode3                         
		};
		Sparraw.ajaxPost('sys/home/reqWeather.action', params, function(data){
				
				if(data.ResponseDetail.hasOwnProperty("weatherinfo")){
					$scope.homeData = {};
					$scope.homeData.weatherinfo = data.ResponseDetail.weatherinfo;
					var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
					var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
					var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
					var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
					if (judgeRain >= 0) {
						console.log("雨天");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/10.png');
						$scope.weatherSrc1 = "img/icon/weather/10.png";
					}else if (judgeCloudy >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/07.png');
						$scope.weatherSrc1 = "img/icon/weather/07.png";
					}else if (judgeSnow >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/14.png');
						$scope.weatherSrc1 = "img/icon/weather/14.png";
					}else if (judgeShade >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/05.png');
						$scope.weatherSrc1 = "img/icon/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/00.png');
						$scope.weatherSrc1 = "img/icon/weather/00.png";
					};

					var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
					var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
					var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
					var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
					if (judgeRain1 >= 0) {
						console.log("雨天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/10.png');
						$scope.weatherSrc2 = "img/icon/weather/10.png";
					}else if (judgeCloudy1 >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/07.png');
						$scope.weatherSrc2 = "img/icon/weather/07.png";
					}else if (judgeSnow1 >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/14.png');
						$scope.weatherSrc2 = "img/icon/weather/14.png";
					}else if (judgeShade1 >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/05.png');
						$scope.weatherSrc2 = "img/icon/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/00.png');
						$scope.weatherSrc2 = "img/icon/weather/00.png";
					};
				}

				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 = addCode1;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 = addCode2;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 = addCode3;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 = data.ResponseDetail.cityinfo.cityname1;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 = data.ResponseDetail.cityinfo.cityname2;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 = data.ResponseDetail.cityinfo.cityname3;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate = data.ResponseDetail.cityinfo.date?data.ResponseDetail.cityinfo.date.substr(0,8):"";

				$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;

				$scope.sparraw_user_temp.weather.weatherinfo = data.ResponseDetail.weatherinfo;
				
			    sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
			});
	}else{
		console.log("读取缓存天气");
		$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;

		$scope.homeData = {};
		$scope.homeData.weatherinfo = $scope.sparraw_user_temp.weather.weatherinfo;
		var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
		var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
		var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
		var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
		if (judgeRain >= 0) {
			console.log("雨天");
			//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/10.png');
			$scope.weatherSrc1 = "img/icon/weather/10.png";
		}else if (judgeCloudy >= 0) {
			console.log("多云");
			//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/07.png');
			$scope.weatherSrc1 = "img/icon/weather/07.png";
		}else if (judgeSnow >= 0) {
			console.log("下雪");
			//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/14.png');
			$scope.weatherSrc1 = "img/icon/weather/14.png";
		}else if (judgeShade >= 0) {
			console.log("阴");
			//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/05.png');
			$scope.weatherSrc1 = "img/icon/weather/05.png";
		}else{
			console.log("晴天");
			//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/00.png');
			$scope.weatherSrc1 = "img/icon/weather/00.png";
		};

		var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
		var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
		var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
		var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
		if (judgeRain1 >= 0) {
			console.log("雨天");
			//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/10.png');
			$scope.weatherSrc2 = "img/icon/weather/10.png";
		}else if (judgeCloudy1 >= 0) {
			console.log("多云");
			//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/07.png');
			$scope.weatherSrc2 = "img/icon/weather/07.png";
		}else if (judgeSnow1 >= 0) {
			console.log("下雪");
			//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/14.png');
			$scope.weatherSrc2 = "img/icon/weather/14.png";
		}else if (judgeShade1 >= 0) {
			console.log("阴");
			//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/05.png');
			$scope.weatherSrc2 = "img/icon/weather/05.png";
		}else{
			console.log("晴天");
			//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/00.png');
			$scope.weatherSrc2 = "img/icon/weather/00.png";
		};

		
	}
	
	Sparraw.beginAlarmTask($scope.sparraw_user_temp.userinfo.role);
	
	$scope.judgeAlarm = function(){
		if (sparraw_user.needAlarm == "N") {
			document.getElementById('AlarmImg').setAttribute('src', 'img/icon/farm/home4.png');
		}else{
			document.getElementById('AlarmImg').setAttribute('src', 'img/icon/farm/home_alarm.png');
		};
	}


	persistentData.setVerticalKey = false;

	



	$scope.judgeUpdate = function(){
		//判断是否要更新
		$http.get( API_Host + "checkVersion.jsp")
		.success(function(data) {
			data = JSON.stringify(data);
			var tVersion = data.substring(data.indexOf("$")+1,data.indexOf("$",data.indexOf("$")+1));
			console.log("tVersion:" + tVersion);
			if(App_Version != tVersion){
				var ua = navigator.userAgent.toLowerCase();	
				console.log("客户端:" + ua);
				if (/iphone|ipad|ipod/.test(ua)) {
					console.log("ios");
					$scope.judgePassword();
				} else if (/android/.test(ua)) {
					console.log("android");
					app_confirm('服务器最新版本是：'+tVersion+'，请下载更新，更新过程中您的数据信息将不会丢失。',null,null,function(buttonIndex){
						if(buttonIndex == 1){
							// app_alert('您选择了【取消】');
							$scope.judgePassword();
						}else if(buttonIndex == 2){
							// 当前方法在JSUtil中定义
							appUpGrade($ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout,tVersion);
						}
					});	
				}else{
					Sparraw.myNotice("请在手机端点击。");
					$scope.judgePassword();	
				}
			}else{
				$scope.judgePassword();
			}
		})
		.error(function(data) {
			app_alert("服务器获取信息失败。");
		});
	}


	$scope.judgePassword = function(){
		if($scope.sparraw_user_temp.profile.password == "123456") {
			app_confirm('您的密码是初始密码，是否立即进行修改？', '提示', null, function (button) {
				if (button == 2) {
					$state.go("modifyUserInfo");
				}else{
					$scope.judgeDataInput();
				}
			});
		}else{
			$scope.judgeDataInput();
		};
	}

	$scope.judgeDataInput = function(){
		var params = {
	      	"operation":"needAlarm",
			"FarmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId                
		};
		Sparraw.ajaxPost('message/DataInputAlarm.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			if (data.ResponseDetail.NeedAlarm == 'Y') {
	   				var showList = data.ResponseDetail.AlarmHouseList.toString();
	   				showList = showList.replace(/(,)/g,"栋,");
					var buttonLabels = ['稍后填','立即填'];
					app_confirm('截止到昨天'+ showList +'栋，尚有生产记录未填写。',null,buttonLabels,function(buttonIndex){
						if(buttonIndex == 1){
							
						}else if(buttonIndex == 2){
							$state.go("dailyReport");
						}
					});

	   			}else{

	   			}
		    }else {
		   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		    };
		});
	}

	setTimeout(function () {
		if (persistentData.switchRemind) {
			persistentData.switchRemind = false;
			$scope.judgeUpdate();
		}else{

		}
	},1000);
	


})
// 信息维护
.controller("infoSafeguardCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	

	$scope.goFarmRegistered = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"farmRegistered");
	}

	$scope.goBuildingTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"buildingTable");
	}

	$scope.goEmployeesTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"employeesTable");
	}


	$scope.pointDevelop = function() {
		pointDevelop();
		return;	
	};

     $scope.showConfirm = function() {
          app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        userLogout();
                        $state.go("landingPage");
                   }
              }); 
   };

	$scope.exit = function(){
		userLogout();
		$state.go("landingPage");
	}

})
// 个人信息修改
.controller("modifyUserInfoCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	var oldPw;
	var newPw;
	var confirmPw;
	$scope.oldPassWordLostFocus = function(oldPassWord){
		oldPw = oldPassWord;
	}

	$scope.newPassWordLostFocus = function(newPassWord){
		newPw = newPassWord;
	}

	$scope.confirmPassWordLostFocus = function(confirmPassWord){
		confirmPw = confirmPassWord;
	}

	$scope.saveUpdate = function(){

		if (oldPw == null || newPw == null || confirmPw == null) {
			Sparraw.myNotice("请输入完整密码,谢谢。");
		}else {
			if (newPw != confirmPw) {
				Sparraw.myNotice("两次密码输入不一致请确认后再进行提交,谢谢。");
			}else {
				var params = {
							      'user_id'   : sparraw_user.userinfo.id        ,
							      'old_pw'    : oldPw                           ,
							      'new_pw'    : newPw                           
				   			 };

				Sparraw.ajaxPost('sys/user/upPassword.action', params, function(data){
			   		if (data.ResponseDetail.ErrorMsg == null) {
			   			 Sparraw.myNotice("修改成功，请重新登录！");
			   			 sparraw_user.profile.user_State = true;
						 $scope.sparraw_user_temp.profile.password = newPw;
			   			 $state.go("landingPage");
				    }else {
				   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				    };
				});

			};
		};
		


	}

})
// 关于页面
.controller("aboutCtrl",function($scope, $state, $http, AppData,$cordovaFileTransfer,$ionicLoading,$timeout,$cordovaFileOpener2) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.App_Version = App_Version;
	$scope.dlAPK = function(){
		$http.get( API_Host + "checkVersion.jsp")
		.success(function(data) {
			// console.log("Success:" + data);
			data = JSON.stringify(data);
			var tVersion = data.substring(data.indexOf("$")+1,data.indexOf("$",data.indexOf("$")+1));
			console.log("tVersion:" + tVersion);
			if(App_Version != tVersion){
				var ua = navigator.userAgent.toLowerCase();	
				console.log("客户端:" + ua);
				if (/iphone|ipad|ipod/.test(ua)) {
					console.log("ios");
					Sparraw.myNotice("该功能目前只支持安卓手机。");
				} else if (/android/.test(ua)) {
					console.log("android");
					app_confirm('服务器最新版本是：'+tVersion+'，请下载更新，更新过程中您的数据信息将不会丢失。',null,null,function(buttonIndex){
						if(buttonIndex == 1){
							// app_alert('您选择了【取消】');
						}else if(buttonIndex == 2){
							// 当前方法在JSUtil中定义
							appUpGrade($ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout,tVersion);
						}
					});	
				}else{
					Sparraw.myNotice("请在手机端点击。");	
				}
			}else{
				Sparraw.myNotice("当前已是最新版本。");
			}
		})
		.error(function(data) {
			app_alert("服务器获取信息失败。");
		});
	}
	$scope.queryVersion = function(){
		console.log("downLoad apk");
	};

	$scope.pointDevelop = function() {
		//Sparraw.getInfoStatus($ionicPopup,$state,"");
		pointDevelop();
		return;	
	};
	
})


//功能介绍
.controller("functionIntroduceCtrl",function($scope, $state, $http, AppData) {
	
})
//联系我们
.controller("contactUsCtrl",function($scope, $state, $http, AppData) {
//	var map = new BMap.Map("allmap");
//	var point = new BMap.Point(121.4025800000	,31.1722250000);
//	map.centerAndZoom(point, 35);
//	var marker = new BMap.Marker(point);  // 创建标注
//	map.addOverlay(marker);
})
//意见反馈
.controller("feedBackCtrl",function($scope, $state, $http, AppData) {
	
})
// 权限控制
.controller("accessControlCtrl",function($scope, $state, $http, AppData) {

})


// 农场管理
.controller("batchManageCtrl",function($scope, $ionicLoading, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setPortrait(true,true);
	


	$scope.gonewBatch = function(){
		$state.go("newBatch");
	}
	$scope.goBatchClearing = function(){
		$state.go("batchClearing");
	}
	$scope.goProfitReport = function(){
		$state.go("profitReport");
	}
	$scope.goMoreBatchProfit = function(){
		$state.go("moreBatchProfit");
	}
	$scope.goMoreBatchClearing = function(){
		$state.go("moreBatchClearing");
	}
	$scope.goProductPerformStander = function(){
		$state.go("productPerformStander");
	}
	$scope.goBreedMonthProfit = function(){
		$state.go("breedMonthProfit");
	}
	$scope.goHistoryDataImport = function(){
		$state.go("historyDataImport");
	}	
	$scope.goMonthCost = function(){
		$state.go("monthCost");
	}
	$scope.goTotalProfit = function(){
		$state.go("totalProfit");
	}
	$scope.pointDevelop = function() {
		pointDevelop();
		return;	
	};
})



// 新建批次
.controller("newBatchCtrl",function($scope, $state, $http, AppData,uiGridConstants) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));



	$scope.backBatchManage = function(){
		$state.go("batchManage");
	}
	$scope.batchData = {
		"FarmId"          :  $scope.sparraw_user_temp.farminfo.id                ,//int型，农场id
		"BreedBatchId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
		"farmBreedStatus" :  $scope.sparraw_user_temp.farminfo.farmBreedStatus   ,  //农场批次状态
		"BatchCode"       :  ""  ,//varchar型，批次编号
		"place_date"      :  ""  ,//varchar型，入舍时日期
		"place_day_age"   :  ""  ,//int型，入舍日龄
		"place_week_age"  :  ""  ,//int型，入雏周龄
		"eggType"         :  "01"  ,
		"chickenType"     :  "02"  ,
		"place_detail"    :  []  ,//各栋舍入舍数量
		"Place_Sum"       :  "" //计算合计的入雏总量
	}


	
	$scope.drawTable = function(){
		$scope.gridOptions = {};
		$scope.gridOptions.enableCellEditOnFocus = true;
		$scope.gridOptions.enableSorting = false;
		$scope.gridOptions.enableCellEdit = false;
		$scope.gridOptions.enableHorizontalScrollbar = uiGridConstants.scrollbars.NEVER;
    	$scope.gridOptions.enableVerticalScrollbar = uiGridConstants.scrollbars.NEVER;
    	$scope.gridOptions.columnDefs = [];

    	var HouseNameHeader = '<div style="width:178px;height:30px;"><p style="text-align:center; position:relative; left:0px; top:5px;">栋舍号</div>';

    	$scope.gridOptions.columnDefs.push({name: 'HouseName',  width: '50%', enableColumnMenu: false, headerCellTemplate: HouseNameHeader,
			cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
          		if (newBatchDiv.getCellValue(row,col) === '合计') {
		        	return 'TableTotalStyle';
		        }else{
		        	return 'TableHouseStyle';
		        }
	        }
		});
		var PlaceNumHeader = '<div style="width:178px;height:30px;"><p style="text-align:center; position:relative; left:0px; top:5px;">进鸡数量</div>';
		$scope.gridOptions.columnDefs.push({name: 'place_num', width: '50%', type: 'number', enableCellEdit:true,enableColumnMenu: false, headerCellTemplate: PlaceNumHeader,
			cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
          		if (newBatchDiv.getCellValue(row,col) == $scope.batchData.Place_Sum) {
		        	return 'TableTotalPlaceNumStyle';
		        }else{
		        	return 'TablePlaceNumStyle';
		        }
	        },
	        cellEditableCondition: function($scope){
     			if ($scope.row.entity.HouseName == "合计") {
     				return 0;
     			}else{
     				return 1;
     			};
     		}
		});



		

		$scope.gridOptions.data = $scope.batchData.place_detail;
		console.log($scope.gridOptions.data);
		for (var i = 0; i < $scope.gridOptions.data.length; i++) {
			// console.log($scope.gridOptions.data[i].HouseId);
			if ($scope.gridOptions.data[i].HouseId == 0) {
				$scope.gridOptions.data.pop();
			}else{

			};
		};



		$scope.gridOptions.data.push({
			'HouseName':'合计',
			'place_num':$scope.batchData.Place_Sum
		});
		document.getElementById('newBatchId').style.height = (30 * $scope.gridOptions.data.length + 33) + "px";

    }

	$scope.inquire = function(){
		//显示每栋栋舍信息
		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			$scope.batchData.place_detail.push({
				"HouseId"    :  $scope.sparraw_user_temp.userinfo.houses[i].HouseId    ,
				"HouseName"  :  $scope.sparraw_user_temp.userinfo.houses[i].HouseName  ,
				"place_num"  :  ""
			});
		};

		var params = {
		      "BreedBatchId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId
			};

			Sparraw.ajaxPost('layer_breedBatch/queryBatch.action', params, function(data){
				console.log(data);
		   		if (data.ResponseDetail.Result == "Success") {
					$scope.batchData.BatchCode = data.ResponseDetail.BatchCode;
					//$scope.batchData.place_date = data.ResponseDetail.place_date;
					$scope.batchData.place_day_age = data.ResponseDetail.place_day_age;
					$scope.batchData.place_week_age = data.ResponseDetail.place_week_age;
					$scope.batchData.place_detail = data.ResponseDetail.place_detail;
					$scope.batchData.Place_Sum = data.ResponseDetail.place_num;
					$scope.batchData.chickenType = data.ResponseDetail.place_breed;
					$scope.batchData.eggType = data.ResponseDetail.place_type;
					//日期转换
					var TempDate = new Date(data.ResponseDetail.place_date);
					$scope.batchData.place_date = TempDate;
					$scope.drawTable();
				}else if (data.ResponseDetail.Result == "Fail"){
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
			$scope.drawTable();
	}
	
	$scope.inquire();


	


	if ($scope.batchData.farmBreedStatus != "01") {
		Sparraw.myNotice("目前批次信息为空，您可以添加新的批次信息");
		$scope.saveAddBtn = false;
	}else{
		var para = document.getElementById("place_week_ageId").disabled=true;
		var para = document.getElementById("place_dateId").disabled=true;
		var para = document.getElementById("place_day_ageId").disabled=true;
		var para = document.getElementById("batchCodeId").disabled=true;
		var para = document.getElementById("chickenTypeSelect").disabled=true;
		var para = document.getElementById("eggTypeSelect").disabled=true;
		$scope.saveAddBtn = true;
	};

    
	$scope.judgeDevice = function(){
		var ua = navigator.userAgent.toLowerCase();
    	if (/iphone|ipad|ipod/.test(ua) || /android/.test(ua)) {
			console.log("是手机");
		}else{
			console.log("是电脑");
			document.getElementById("place_dateId").type = "text";
		}
	}

    $scope.GetPlacedate = function(){

    	var ua = navigator.userAgent.toLowerCase();
    	if (/iphone|ipad|ipod/.test(ua) || /android/.test(ua)) {
			console.log("是手机");
		}else{
			console.log("是电脑");
			//console.log(document.getElementById("place_dateId").value);
			
			if (document.getElementById("place_dateId").value.search(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/) != -1) {
				console.log("正确");
			}else{
				$scope.batchData.BatchCode = "";
				return Sparraw.myNotice("请正确输入入舍时日龄格式为：2015-01-01");
			}
		}
    	$scope.batchData.place_date = document.getElementById("place_dateId").value;
    	//转为字符串并且删除“日”字符串，修改“年”“月”“/”为“-”,并且做补零处理                
        var selectDate = $scope.batchData.place_date + '';
        selectDate     = selectDate.replace(/(日)/g,"");
        selectDate     = selectDate.replace(/(月)/g,"-");
        selectDate     = selectDate.replace(/(年)/g,"-");
        selectDate     = selectDate.replace(/\//g,"-");
        if (selectDate[6] == "-") {
        	selectDate = selectDate.replace(/(.{5})/g,'$10');
        };
        if (selectDate[9]) {

        }else{
        	selectDate = selectDate.replace(/(.{8})/g,'$10');
        };
        //将得到的日期放入入雏日期
        $scope.batchData.place_date = selectDate;
        var TempDate = new Date(selectDate);
        //获取批次号
        $scope.batchData.BatchCode = selectDate;
        $scope.batchData.BatchCode = $scope.batchData.BatchCode.replace(/(-)/g,"");
        $scope.batchData.BatchCode = $scope.batchData.BatchCode.substring(2,$scope.batchData.BatchCode.length);

    }


    $scope.getthedate = function(){
    	if ($scope.batchData.place_date == "" || !$scope.batchData.place_date) {
    		$scope.batchData.place_day_age = "";
    		Sparraw.myNotice("请选择入雏日，否则无法计算生长周龄。");
    	}else{
			//  以下程序是参考自然周，计算生长周龄的公式， by guoxiang  2016-05-05
			var initPlaceDate = app_addDate($scope.batchData.place_date,1-$scope.batchData.place_day_age);
			console.log('initPlaceDate=' + initPlaceDate);
			var initPlaceWeekDay = new Date(initPlaceDate).getDay();
			console.log('initPlaceWeekDay=' + initPlaceWeekDay);
			var curDayAge = $scope.batchData.place_day_age;
			console.log('curDayAge1=' + curDayAge);
			if(initPlaceWeekDay <= 3){
				curDayAge = curDayAge + initPlaceWeekDay + 6;
			}else{
				curDayAge = curDayAge - (7- initPlaceWeekDay) + 6;
			}
			console.log('curDayAge2=' + curDayAge);
			$scope.batchData.place_week_age = Math.floor(curDayAge / 7); 
			console.log('$scope.batchData.place_week_age=' + $scope.batchData.place_week_age);
    	};
	}



	$scope.Clicksave = function(){

		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};


		console.log($scope.batchData.eggType);
		app_confirm('新建批次后，录入信息无法修改，您确认要保存吗？','提示',null,function(buttonIndex){
           if(buttonIndex == 2){
           		//判断是否为空

           		if ($scope.batchData.place_day_age == "" || !$scope.batchData.place_day_age) {
           			app_alert('请填写完整信息。');
           		}else{
           			for (var i = 0; i < $scope.batchData.place_detail.length; i++) {
						if ($scope.batchData.place_detail[i].place_num == 0 && $scope.batchData.place_detail[i].HouseName != '合计') {
							app_confirm($scope.batchData.place_detail[i].HouseName + '栋舍入雏数量为0,是否保存？','提示',null,function(buttonIndex){
								if(buttonIndex == 2){
									$scope.save();
								}
							})
						}
					};
					$scope.save();
           		};
           }
	    }); 
	}
	

    $scope.JudgeType = function(){
    	console.log($scope.batchData.chickenType);
    }
    $scope.JudgeEggType = function(){
    	console.log($scope.batchData.eggType);
    }


	$scope.save = function(){
		console.log($scope.batchData.eggType);
		for (var i = 0; i < $scope.batchData.place_detail.length; i++) {
			if ($scope.batchData.place_detail[i].HouseName == '合计') {
				$scope.batchData.place_detail.pop();
			};
		}

		$scope.TempPlaceSum = [];
		for (var i = 0; i < $scope.batchData.place_detail.length; i++) {
			if ($scope.batchData.place_detail[i].place_num === "") {
				$scope.batchData.place_detail[i].place_num = 0;
			};
			$scope.TempPlaceSum.push($scope.batchData.place_detail[i].place_num);
		};

		$scope.batchData.Place_Sum = eval($scope.TempPlaceSum.join('+'));

 		var params = {
	     	"FarmId"          :$scope.batchData.FarmId          ,
			"BatchCode"       :$scope.batchData.BatchCode       ,
			"place_type"      :$scope.batchData.eggType         ,
			"place_breed"     :$scope.batchData.chickenType     ,
			"place_date"      :$scope.batchData.place_date      ,
			"place_day_age"   :$scope.batchData.place_day_age   ,
			"place_week_age"  :$scope.batchData.place_week_age  ,
			"place_detail"    :$scope.batchData.place_detail    ,
			"place_num"       :$scope.batchData.Place_Sum
		};
		Sparraw.ajaxPost('layer_breedBatch/createBatch.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			sparraw_user.farminfo.farmBreedBatchId = data.ResponseDetail.BreedBatchId;
				//重新获取服务器最新数据
				Sparraw.getLatestData($state,"batchManage");
				$scope.inquire();
				Sparraw.myNotice("保存成功。");
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		// $scope.inquire();
	}
})




// 批次结算
.controller("batchClearingCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.batchClearingData = {
		"BreedBatchId"            :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,  //农场批次id
		"farmBreedBatchCode"      :  $scope.sparraw_user_temp.farminfo.farmBreedBatchCode,
		"farmBreedStatus"         :  $scope.sparraw_user_temp.farminfo.farmBreedStatus   ,  //农场批次状态
        "ChickMsg":{                        //鸡苗结算
              "VenderName"        :  ""  ,  //雏源厂家
              "amount"            :  ""  ,  //购雏数量
              "price_p"           :  ""  ,  //单价
              "price_sum"         :  ""  ,  //总金额
              "sys_amount"        :  ""     //系统统计入雏数
              },
        "FeedMsg":{                         //饲料结算
              "VenderName"        :  ""  ,  //饲料厂家
              "FeedInfo":[{                 //饲料信息
                  "FeedCode"      :  ""  ,  //饲料编号id
                  "FeedName"      :  ""  ,  //饲料名称
                  "Weight"        :  ""  ,  //公斤数
                  "Price_p"       :  ""  ,  //单价
                  "Price_sum"     :  ""     //总金额
                }],
              "sys_amount"        :  ""  ,  //系统统计消耗料数

              "total_Weight"      :  0  ,  //合计的 公斤数
              "total_Price_p"     :  0  ,  //合计的 单价
              "total_Price_sum"   :  0  ,  //合计的 总金额
              },
        "OutputMsg":{                       //毛鸡结算
              "VenderName"        :  ""  ,  //屠宰厂家
              "Price_p"           :  ""  ,  //结算价
              "Detail":[{                   //结算细节
                  "HouseId"       :  ""  ,  //栋舍id
                  "HouseName"     :  ""  ,  //栋舍名称
                  "houseBreedId"  :  ""  ,  //栋舍饲养批次
                  "SettleAmount"  :  ""  ,  //只数
                  "SettleWeight"  :  ""  ,  //公斤数
                  "Weight_Avg"    :  ""  ,  //只均重
                  "Price_sum"     :  ""     //总金额
                }],
              "sys_amount"        :  ""  ,  //系统统计出栏数
              "total_SettleAmount":  0  ,  //合计的 只数
              "total_SettleWeight":  0  ,  //合计的 公斤数
              "total_Weight_Avg"  :  0  ,  //合计的 只均重
              "total_Price_sum"   :  0  ,  //合计的 总金额
              },
        "OtherMsg":{                        //农场费用登记
              "VaccineFee"        :  ""  ,  //药品疫苗费
              "FuelFee"           :  ""  ,  //燃料费
              "ManualFee"         :  ""  ,  //人工费
              "OtherFee"          :  ""  ,  //其他费用
              "LossFee"           :  ""  ,  //折旧/租金项
              "OtherFee_IC"       :  ""  ,	//其他收入（减）
			  "totalCost"         :  ""	 ,	//费用合计

			  //3月29日增加
			  "ChickenManure"     :  ""  ,  //鸡粪收入
              "OtherFee_IC"       :  ""  ,  //其他收入
              "UtilityFee"        :  ""  ,  //-水电费
              "PaddingFee"        :  ""  ,  //-垫料费
              "CatcherFee"        :  ""     //-抓鸡费
		}

	}

	$scope.Btnstate = function(){
		if ($scope.batchClearingData.farmBreedStatus != "01") {
			console.log("已经结束了");
			document.getElementById('saveBtn').style.background = "#ECECEC";
			document.getElementById('clearingBtn').style.background = "#ECECEC";
			document.getElementById('navSaveBtn').style.color = "#ECECEC";
		}else{
			document.getElementById('saveBtn').style.background = "#33CD5F";
			document.getElementById('clearingBtn').style.background = "#33CD5F";
			document.getElementById('navSaveBtn').style.color = "#FFF";
		};
	}
	$scope.Btnstate();


	//鸡苗结算
	//结算总金额
	$scope.calcAmouFun = function(){
		$scope.batchClearingData.ChickMsg.price_sum = (parseInt($scope.batchClearingData.ChickMsg.price_p * $scope.batchClearingData.ChickMsg.amount));
		if (isNaN($scope.batchClearingData.ChickMsg.price_sum)) {
			$scope.batchClearingData.ChickMsg.price_sum = 0;
		};
	};

	//饲料结算
	//计算饲料结算的金额
	$scope.forageTotal = function(){
		var total_Weight_temp = 0;
		var total_Price_sum_temp = 0;
		var total_Price_p_temp = 0;

		for (var i = 0; i < $scope.batchClearingData.FeedMsg.FeedInfo.length; i++) {
			//  计算 单个饲料总金额
			$scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum =
					($scope.batchClearingData.FeedMsg.FeedInfo[i].Weight * $scope.batchClearingData.FeedMsg.FeedInfo[i].Price_p);
			if (!app_IsNum($scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum)) {
				$scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum = 0;
			}else{
				$scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum = $scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum.toFixed(0);
			}
			//  计算 合计-公斤数
			if(app_IsNum($scope.batchClearingData.FeedMsg.FeedInfo[i].Weight)){
				total_Weight_temp += parseFloat($scope.batchClearingData.FeedMsg.FeedInfo[i].Weight);
			}
			//  计算 合计-总金额
			if(app_IsNum($scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum)){
				total_Price_sum_temp += parseFloat($scope.batchClearingData.FeedMsg.FeedInfo[i].Price_sum);
			}
		};
		//  计算 合计-单价数
		total_Price_p_temp = total_Price_sum_temp/total_Weight_temp ;
		if(!app_IsNum(total_Price_p_temp)){
			total_Price_p_temp = 0;
		}else{
			total_Price_p_temp = Math.round(total_Price_p_temp*100)/100;
		}
		$scope.batchClearingData.FeedMsg.total_Weight = total_Weight_temp;
		$scope.batchClearingData.FeedMsg.total_Price_sum = total_Price_sum_temp;
		$scope.batchClearingData.FeedMsg.total_Price_p = total_Price_p_temp;
	};

	//毛鸡结算
	/*合计上面的表格*/
	$scope.furChicTotal = function(){
		var total_SettleAmount_temp = 0;   // 总只数 总和
		var total_SettleWeight_temp = 0;   // 总公斤数 总和
		var total_Price_sum_temp = 0;  // 总金额 总和
		var total_Weight_Avg_temp = 0;  // 只均重 平均

		for (var i = 0; i < $scope.batchClearingData.OutputMsg.Detail.length; i++) {
			//计算  单栋总金额
			$scope.batchClearingData.OutputMsg.Detail[i].Price_sum = (parseInt($scope.batchClearingData.OutputMsg.Detail[i].SettleWeight * $scope.batchClearingData.OutputMsg.Price_p));
			if (!app_IsNum($scope.batchClearingData.OutputMsg.Detail[i].Price_sum)) {
				$scope.batchClearingData.OutputMsg.Detail[i].Price_sum = 0;
			}else{
				$scope.batchClearingData.OutputMsg.Detail[i].Price_sum = $scope.batchClearingData.OutputMsg.Detail[i].Price_sum.toFixed(0);
			}
			//计算  单栋只均重
			$scope.batchClearingData.OutputMsg.Detail[i].Weight_Avg = (parseFloat($scope.batchClearingData.OutputMsg.Detail[i].SettleWeight / $scope.batchClearingData.OutputMsg.Detail[i].SettleAmount)).toFixed(2);
			if (!app_IsNum($scope.batchClearingData.OutputMsg.Detail[i].Weight_Avg)) {
				$scope.batchClearingData.OutputMsg.Detail[i].Weight_Avg = 0;
			}

			//  计算 合计-总只数
			if(app_IsNum($scope.batchClearingData.OutputMsg.Detail[i].SettleAmount)){
				total_SettleAmount_temp += parseFloat($scope.batchClearingData.OutputMsg.Detail[i].SettleAmount);
			}
			//  计算 合计-总公斤数
			if(app_IsNum($scope.batchClearingData.OutputMsg.Detail[i].SettleWeight)){
				total_SettleWeight_temp += parseFloat($scope.batchClearingData.OutputMsg.Detail[i].SettleWeight);
			}
			//  计算 合计-总金额
			if(app_IsNum($scope.batchClearingData.OutputMsg.Detail[i].Price_sum)){
				total_Price_sum_temp += parseFloat($scope.batchClearingData.OutputMsg.Detail[i].Price_sum);
			}
		};
		console.log(total_SettleAmount_temp);
		//  计算 合计-只均重
		total_Weight_Avg_temp = total_SettleWeight_temp/total_SettleAmount_temp ;
		if(!app_IsNum(total_Weight_Avg_temp)){
			total_Weight_Avg_temp = 0;
		}else{
			total_Weight_Avg_temp = total_Weight_Avg_temp.toFixed(2);
		}

		$scope.batchClearingData.OutputMsg.total_SettleAmount = total_SettleAmount_temp;
		$scope.batchClearingData.OutputMsg.total_SettleWeight = total_SettleWeight_temp;
		$scope.batchClearingData.OutputMsg.total_Price_sum = total_Price_sum_temp;
		$scope.batchClearingData.OutputMsg.total_Weight_Avg = total_Weight_Avg_temp;

		/*if ($scope.batchClearingData.OutputMsg.total_SettleAmount > $scope.batchClearingData.OutputMsg.sys_amount) {
			Sparraw.myNotice("合计只数大于系统统计出栏数！！！");
		};*/

		//合计费用计算
		if (!app_IsNum($scope.batchClearingData.OtherMsg.VaccineFee)) {
			$scope.batchClearingData.OtherMsg.VaccineFee = 0;
		};
		if (!app_IsNum($scope.batchClearingData.OtherMsg.ManualFee)) {
			$scope.batchClearingData.OtherMsg.ManualFee = 0;
		};
		if (!app_IsNum($scope.batchClearingData.OtherMsg.FuelFee)) {
			$scope.batchClearingData.OtherMsg.FuelFee = 0;
		};
		if (!app_IsNum($scope.batchClearingData.OtherMsg.LossFee)) {
			$scope.batchClearingData.OtherMsg.LossFee = 0;
		};
		if (!app_IsNum($scope.batchClearingData.OtherMsg.OtherFee)) {
			$scope.batchClearingData.OtherMsg.OtherFee = 0;
		};
		if (!app_IsNum($scope.batchClearingData.OtherMsg.OtherFee_IC)) {
			$scope.batchClearingData.OtherMsg.OtherFee_IC = 0;
		};

		$scope.batchClearingData.OtherMsg.totalCost = parseInt($scope.batchClearingData.OtherMsg.VaccineFee) +
													  parseInt($scope.batchClearingData.OtherMsg.ManualFee)  +
													  parseInt($scope.batchClearingData.OtherMsg.FuelFee)    +
													  parseInt($scope.batchClearingData.OtherMsg.LossFee)    +
													  parseInt($scope.batchClearingData.OtherMsg.OtherFee)   -
													  parseInt($scope.batchClearingData.OtherMsg.OtherFee_IC);
		console.log($scope.batchClearingData.OtherMsg.totalCost);
	};



	$scope.inquireBatchClearingData = function(){

		if ($scope.sparraw_user_temp.farminfo.farmBreedBatchId == 0) {
			app_alert("请先创建农场批次。");
			return ;
		}else{
			var params = {
				"BreedBatchId" :  $scope.batchClearingData.BreedBatchId
			};
	    	Sparraw.ajaxPost('breedBatch/settleBatchQuery.action', params, function(data){
				$scope.batchClearingData.FeedMsg = data.ResponseDetail.FeedMsg;
				$scope.batchClearingData.ChickMsg = data.ResponseDetail.ChickMsg;
				$scope.batchClearingData.OutputMsg = data.ResponseDetail.OutputMsg;
				$scope.batchClearingData.OtherMsg = data.ResponseDetail.OtherMsg;








				//计算出合计数据
				$scope.calcAmouFun();
				$scope.forageTotal();
				$scope.furChicTotal();



		    },function(){
		    	Sparraw.myNotice("查询失败");
		    });
		};


	}
	$scope.inquireBatchClearingData();





	$scope.save = function(){
		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		if ($scope.batchClearingData.farmBreedStatus === "02") {
			app_alert("批次已经结算确认,不允许修改。");
			return ;
		}else if ($scope.batchClearingData.farmBreedStatus === "00") {
			app_alert("请先创建农场批次。");
			return ;
		}else{

		};







		var params = {
				"BreedBatchId" :  $scope.batchClearingData.BreedBatchId  ,
				"FarmId"       :  $scope.sparraw_user_temp.farminfo.id   ,
				"ChickMsg"     :  $scope.batchClearingData.ChickMsg      ,
				"FeedMsg"      :  $scope.batchClearingData.FeedMsg       ,
				"OutputMsg"    :  $scope.batchClearingData.OutputMsg     ,
				"OtherMsg"     :  $scope.batchClearingData.OtherMsg

		};
    	Sparraw.ajaxPost('breedBatch/settleBatchSave.action', params, function(data){
			app_alert("保存成功");
	    },function(data){
			console.log(data);
	    });
	}


	$scope.clearingFun = function(){
		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		if ($scope.batchClearingData.farmBreedStatus === "02") {
			app_alert("批次已经结算确认,不允许修改。");
			return ;
		}else if ($scope.batchClearingData.farmBreedStatus === "00") {
			app_alert("请先创建农场批次。");
			return ;
		}else{

		};

		//为空赋0
		for (var i = 0; i < $scope.batchClearingData.OutputMsg.Detail.length; i++) {
			if ($scope.batchClearingData.OutputMsg.Detail[i].SettleAmount == '' ||
				$scope.batchClearingData.OutputMsg.Detail[i].SettleAmount == 0) {
				return Sparraw.myNotice('尚有内容未填写...');
			};
			if ($scope.batchClearingData.OutputMsg.Detail[i].SettleWeight == '' ||
				$scope.batchClearingData.OutputMsg.Detail[i].SettleWeight == 0) {
				return Sparraw.myNotice('尚有内容未填写...');
			};
		};

		for (var i = 0; i < $scope.batchClearingData.FeedMsg.FeedInfo.length; i++) {
			if ($scope.batchClearingData.FeedMsg.FeedInfo[i].Weight == '' ||
				$scope.batchClearingData.FeedMsg.FeedInfo[i].Weight == 0) {
				return Sparraw.myNotice('尚有内容未填写...');
			};
			if ($scope.batchClearingData.FeedMsg.FeedInfo[i].Price_p == '' ||
				$scope.batchClearingData.FeedMsg.FeedInfo[i].Price_p == 0) {
				return Sparraw.myNotice('尚有内容未填写...');
			};
		};

		var required = [$scope.batchClearingData.ChickMsg.amount,
						$scope.batchClearingData.ChickMsg.price_p,
						$scope.batchClearingData.OutputMsg.Price_p,
						$scope.batchClearingData.OtherMsg.VaccineFee,
						$scope.batchClearingData.OtherMsg.FuelFee,
						$scope.batchClearingData.OtherMsg.ManualFee,
						$scope.batchClearingData.OtherMsg.OtherFee,
						$scope.batchClearingData.ChickMsg.VenderName,
						$scope.batchClearingData.FeedMsg.VenderName,
						$scope.batchClearingData.OutputMsg.VenderName];
		for(i in required){if(required[i]=='' || required[i]==0){return Sparraw.myNotice('尚有内容未填写...');}}

		//判断栋舍是否已全部出栏
		$scope.TempStatus = [];
		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "01") {
				$scope.TempStatus = $scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus;
			};
		};
		if ($scope.TempStatus.length != 0) {
			return app_alert("尚未全部出栏，结算无法完成。");
		};

		app_confirm('请确保先点击保存按钮，否则数据不会保存，继续吗？','提示',null,function(buttonIndex){
	           if(buttonIndex == 2){
	              	var params = {
							"BreedBatchId" :  $scope.batchClearingData.BreedBatchId  ,
					};
			    	Sparraw.ajaxPost('breedBatch/settleBatchConfirm.action', params, function(data){
						if (data.ResponseDetail.Result == "Fail") {
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						}else{
							Sparraw.myNotice("结算成功！！");
							//重新获取服务器最新数据
				    		Sparraw.getLatestData($state,"profitReport");
						};
				    },function(data){
						console.log(data);
				    });
	           }
	    });
	}


})

//盈利报告
.controller("profitReportCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.profitReportData = {
				"FarmId" : $scope.sparraw_user_temp.farminfo.id,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
                "Expense": [{
		            "ItemName"          : ""  ,
		            "PricePKilo_last"   : ""  ,//上批次 元/公斤
		            "PricePUnit_last"   : ""  ,//上批次 元/只
		            "SaleChicken_last"  : ""  ,//上批次 万/元
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }],
		        "InCome": [{
		            "ItemName"          : ""  ,
		            "PricePKilo_last"   : ""  ,//上批次 元/公斤
		            "PricePUnit_last"   : ""  ,//上批次 元/只
		            "SaleChicken_last"  : ""  ,//上批次 万/元
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }],
		        "Profits": {
		        	"ItemName"          : ""  ,
		            "PricePKilo"        : ""  ,//上批次 毛鸡价
		            "SaleChicken"       : ""  ,//上批次 鸡苗价
		            "PricePUnit"        : ""  ,//上批次 饲料价
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }
	}



	if ($scope.sparraw_user_temp.farminfo.farmBreedBatchId == 0) {
		app_alert("未查询到批次信息,请先创建批次。");
		return;
		// return $state.go("batchManage");
	}else{

	};


	$scope.judgeIncome = function(item){
		console.log(item.ItemName);
		if (item.ItemName == "收入") {
    		return "{'font-weight': 'bold'}";
    	}else{
    		return;
    	};
	}

	$scope.judgeCost = function(item){
		console.log(item.ItemName);
		if (item.ItemName == "成本") {
    		return "{'font-weight': 'bold'}";
    	}else{
    		return;
    	};
	}




	$scope.inquireProfitReportData = function(){
		var params = {
				"FarmId"       : $scope.profitReportData.FarmId ,
				"BreedBatchId" : $scope.profitReportData.BreedBatchId
		};
    	Sparraw.ajaxPost('breedBatch/getProfitRep.action', params, function(data){
			if (data.ResponseDetail.Result === "Fail") {
				app_alert(data.ResponseDetail.ErrorMsg);
				return $state.go("batchManage");
			}else{
				$scope.profitReportData.Expense = data.ResponseDetail.Expense;
				$scope.profitReportData.InCome = data.ResponseDetail.InCome;
				$scope.profitReportData.Profits = data.ResponseDetail.Profits;
			};

	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });
	}
	$scope.inquireProfitReportData();

})

// 多批盈利查询
.controller("moreBatchProfitCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.moreBatchProfitData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,
				"Detail": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""
		        }],
		        "OverView": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""
		        }],
		        "DealPrice": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""
		        }]
	}








    $scope.inquireMultiProfit = function(){
    	if ($scope.moreBatchProfitData.BreedBatchId === 0) {
			app_alert("未查询到批次信息,请先创建批次。");
			return;
		};

		console.log($scope.moreBatchProfitData.transferUnit);
		if ($scope.moreBatchProfitData.transferUnit == "Money") {
	    	$scope.moreBatchProfitData.ViewUnit = "万元";
	    }else if ($scope.moreBatchProfitData.transferUnit == "quentity") {
	    	$scope.moreBatchProfitData.ViewUnit = "只";
	    }else if ($scope.moreBatchProfitData.transferUnit == "weight") {
	    	$scope.moreBatchProfitData.ViewUnit = "公斤";
	    };


		var params = {
				"FarmId"       : $scope.moreBatchProfitData.FarmId ,
				"BreedBatchId" : $scope.moreBatchProfitData.BreedBatchId,
				"ViewUnit"     : $scope.moreBatchProfitData.transferUnit
		};
    	Sparraw.ajaxPost('breedBatch/batchComparation.action', params, function(data){
			$scope.moreBatchProfitData.Detail = data.ResponseDetail.Detail;
			$scope.moreBatchProfitData.OverView = data.ResponseDetail.OverView;
			$scope.moreBatchProfitData.DealPrice = data.ResponseDetail.DealPrice;

	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });

	}
	$scope.inquireMultiProfit();



	$scope.judgeNeedBold = function(item){
		console.log(item.ItemName);
		if (item.ItemName != "毛鸡" && item.ItemName != "其它") {
    		return "{'font-weight': 'bold'}";
    	}else{
    		return;
    	};
	}


	$scope.judgeCost = function(item){
		console.log(item.ItemName);
		if (item.ItemName == "成本") {
    		return "{'font-weight': 'bold'}";
    	}else{
    		return;
    	};
	}

})

// 多批盈利查询
.controller("moreBatchClearingCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.moreBatchClearingData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,


				"DealPrice":[{
		               "ItemName"  :  ""  ,
		               "index1"    :  ""  ,
		               "index2"    :  ""  ,
		               "index3"    :  ""  ,
		               "index4"    :  ""  ,
		               "index5"    :  ""
		        }]
	}


	$scope.inquireMultiProfit = function(){
    	if ($scope.moreBatchClearingData.BreedBatchId === 0) {
			app_alert("未查询到批次信息,请先创建批次。");
			return;
		};





		var params = {
				"FarmId"       : $scope.moreBatchClearingData.FarmId ,
		};
    	Sparraw.ajaxPost('breedBatch/batchSettleComparation.action', params, function(data){
			/*$scope.moreBatchProfitData.Detail = data.ResponseDetail.Detail;
			$scope.moreBatchProfitData.OverView = data.ResponseDetail.OverView;
			$scope.moreBatchProfitData.DealPrice = data.ResponseDetail.DealPrice;*/
			console.log(data);
			$scope.moreBatchClearingData.DealPrice = data.ResponseDetail.DealPrice;


	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });

	}
	$scope.inquireMultiProfit();

})

//生产性能标准
.controller("productPerformStanderCtrl", function($scope, $state,$ionicLoading, $http, AppData){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));




	$scope.gobatchManage = function(){
		$state.go("batchManage");
	}
	$scope.prodQuotData = {
        "Headers"      :  [],
        "TableDatas"   :  [],
    }

    
	$scope.drawTable = function(){
    	
    	var comKey = ["WeekAge", "day_lay_rate", "acc_cur_lay", "acc_ori_lay", "acc_cd_rate", "chicken_weight", "day_feed", "acc_ori_lay_weight", "lay_weight"];
    	/*for (var i = 1; i <= $scope.prodQuotData.Headers.length; i++) {
    		if (i != 1) {
    			$scope.gridOptions.columnDefs.push({name:comKey[i-1],  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false});
    		}else{
    			$scope.gridOptions.columnDefs.push({name:comKey[i-1],  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false,pinnedLeft:true});
    		};
    	};*/
    	var headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:15px;'><p>" + $scope.prodQuotData.Headers[0] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[0], width:45 ,enableColumnMenu: false,pinnedLeft:true, headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[1] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[1], width:80 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[2].substring(0,4)+"<br>"+ $scope.prodQuotData.Headers[2].substring(4) + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[2], width:100 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		},
    		renderer: function(value, p, record){
				return '<div style="white-space:normal;">' + value + '</div>';
			}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[3].substring(0,4)+"<br>"+ $scope.prodQuotData.Headers[3].substring(4) + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[3], width:100 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[4] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[4], width:80, enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[5] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[5], width:80 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[6] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[6], width:80 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[7] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[7], width:110 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	headerCellTem = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>" + $scope.prodQuotData.Headers[8] + "</p></div>";
    	$scope.gridOptions.columnDefs.push({name:comKey[8], width:80 ,enableColumnMenu: false,headerCellTemplate:headerCellTem,
    		cellClass: function(newBatchDiv, row, col, rowRenderIndex, colRenderIndex) {
        		return 'cellEditStyle';
        	},
       	 	headerCellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
        		return 'headerEditStyle';
    		}
    	});
    	$scope.gridOptions.enableSorting = false;
		$scope.gridOptions.data = $scope.prodQuotData.TableDatas;
		window.onresize = function(){};
    }
    $scope.getTableData = function(){
    	var params = {'IsNeedDelay':'Y'};
		Sparraw.ajaxPost('layer_breedBatch/queryProStandard.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
	            $scope.prodQuotData.Headers = data.ResponseDetail.Headers;
    			$scope.prodQuotData.TableDatas = data.ResponseDetail.TableDatas;
				$scope.drawTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }
    


    setLandscape(false,true);
    $scope.gridOptions = {};
	$scope.gridOptions.columnDefs = [];
	$scope.getTableData();



})

//累计盈利报告
.controller("totalProfitCtrl", function($scope, $state, $http){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setTimeout(
		function (){
			app_lockOrientation('landscape');//进入时横屏
		}
	,500);
	$scope.gobatchManage = function(){
		app_lockOrientation('portrait');//出去时竖屏
		setTimeout(
			function (){
				$state.go("batchManage");
			}
		,1000);
	}

	$scope.gridOptions = {
			// "FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			'showTableData'  :  {
				// 'firstFixed': true  ,
				'rowHeight' : 47    ,//内容高度
				'header'    : []    ,
				'TableData' : []
			}
			/*enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
			enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,*/
	};

	$scope.monthData = {
		'transferUnit': 'Money',
		'transferUnitCh':'万元',
		'TableData' : [{
			"YearMonth"    :  '', //  --年月
			"sale_amount"  :  '', //  --销售量
			"sale_money"   :  '', //  --销售额
			"exp_chick"    :  '', //  --身价
			"exp_feed"     :  '', //  --饲料
			"exp_packing"  :  '', //  --包装
			"exp_vaccine"  :  '', //  --药费
			"exp_fuel"     :  '', //  --燃料
			"exp_utility"  :  '', //  --水电
			"exp_manual"   :  '', //  --人工
			"exp_lossFee"  :  '', //  --折旧费用
			"exp_other"    :  '', // --其它
			"exp_sum"      :  '', // --成本
			"profit"       :  ''//--盈亏
		}]
	};

	$scope.drowTable = function(){
		$scope.gridOptions = {
			// "FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			'showTableData'  :  {
				// 'firstFixed': true  ,
				'rowHeight' : 47    ,//内容高度
				'header'    : []    ,
				'TableData' : []    ,
				'firstFixed': true
			}
			/*enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
			enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
			'firstFixed': '',*/
		};
		var header1 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>年月</p></div>";
    	var header2 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>销售量</p></div>";
    	var header3 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>销售额</p></div>";
    	var header4 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>身价</p></div>";
    	var header5 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>饲料</p></div>";
    	var header6 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>包装</p></div>";
    	var header7 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>药费</p></div>";
    	var header8 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>燃料</p></div>";
    	var header9 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>水电</p></div>";
    	var header10 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>人工</p></div>";
    	var header11 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>折旧</p></div>";
    	var header12 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>其它</p></div>";
    	var header13 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>成本</p></div>";
    	var header14 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>盈亏</p></div>";
	    $scope.gridOptions.showTableData.header = [
	    	{name:'YearMonth',displayName:'年月', width:45,enableColumnMenu:false,headerCellTemplate:header1},
			{name:'sale_amount',displayName:'销售量',width:60,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header2},
			{name:'sale_money',displayName:'销售额',width:60,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header3},
			{name:'exp_chick',displayName:'身价',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header4},
			{name:'exp_feed',displayName:'饲料',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header5},
			{name:'exp_packing',displayName:'包装',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header6},
			{name:'exp_vaccine',displayName:'药费',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header7},
			{name:'exp_fuel',displayName:'燃料',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header8},
			{name:'exp_utility',displayName:'水电',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header9},
			{name:'exp_manual',displayName:'人工',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header10},
			{name:'exp_lossFee',displayName:'折旧',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header11},
			{name:'exp_other',displayName:'其它',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header12},
			{name:'exp_sum',displayName:'成本',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header13},
			{name:'profit',displayName:'盈亏',width:50,enableColumnMenu:false,enableCellEdit: false,headerCellTemplate:header14}
			];
	    $scope.gridOptions.showTableData.TableData = $scope.monthData.TableData;

	    console.log($scope.gridOptions.columnDefs);
	    GetShowTable($scope.gridOptions.showTableData,$scope);
	}

	$scope.getTableData = function(){
		if($scope.monthData.transferUnit == "Money"){
			$scope.monthData.transferUnitCh = "万元";
		}else if($scope.monthData.transferUnit == "weight"){
			$scope.monthData.transferUnitCh = "元/公斤";
		}
		$scope.monthData.TableData = [];
		var params = {
			"FarmId"      :   $scope.sparraw_user_temp.farminfo.id,
			"ViewUnit"    :   $scope.monthData.transferUnit
		};
		Sparraw.ajaxPost('layer_breedSettle/accProfitRep.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				$scope.monthData.TableData = data.ResponseDetail.Datas;
				$scope.drowTable();
				Sparraw.myNotice(data.ResponseDetail.TipMsg);
			} else if (data.ResponseDetail.Result == 'Fail'){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}
		});
	}
	$scope.getTableData();
})

//月度成本核算
.controller("monthCostCtrl", function($scope, $state, $http, AppData){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.monthCostData = {
		'chooseMonth'          :  ''  ,//选择的核算月份
		'chooseFeedNumber'     :  '5' ,//选择的饲料个数
		'foodType'             :  '0' ,//选择的饲料种类
		'FarmId'               :  $scope.sparraw_user_temp.farminfo.id                ,
		"BreedBatchId"         :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,//int型，批次Id
		"SettleMonth"          :  ""  ,//核算月份，必须是 "YYYYMM"
		"BatchStatus"          :  ""  ,//批次状态
		"DailyFeedSum"         :  ""  ,//number类型，生产日报总耗用数
		"Monthlist"            :  [],//核算月份
		"OutputMsg":{//本月收入
	      "GoodSales"          :  ""  ,//number类型，合格蛋销售
	      "GoodSalesWeight"    :  ""  ,//number类型，合格蛋销售公斤
	      "BrokenSales"        :  ""  ,//number类型，破损蛋销售
	      "BrokenSalesWeight"  :  ""  ,//number类型，破损蛋销售公斤
	      "CDChickenSales"     :  ""  ,//number类型，淘汰鸡销售
	      "ChickenManure"      :  ""  ,//number类型，鸡粪销售
	      "OtherSales"         :  ""   //number类型，其它销售
		},
		"FeedMsg":{//本月饲料成本
	      	"FeedInfo":[{
	          "FeedCode"       :  "0"  ,//varchar类型，饲料编号
	          "FeedName"       :  ""  ,//varchar类型，饲料名称
	          "Weight"         :  ""  ,//number类型，公斤数
	          "Price_p"        :  ""  ,//number类型，单价
	          "Price_sum"      :  ""   //number类型，总金额
	        }],
	  	"total_Weight"          :  ""  ,//合计的内容
	  	"total_Price_p"         :  ""  ,
	    "total_Price_sum"       :  ""  ,
		},
		"OtherMsg":{//本月其它生产成本
	      "PackingFee"         :  ""  ,//number类型，包装费用
	      "VaccineFee"         :  ""  ,//number类型，药品疫苗
	      "ManualFee"          :  ""  ,//number类型，人工费
	      "FuelFee"            :  ""  ,//number类型，燃料费
	      "UtilityFee"         :  ""  ,//number类型，水电费
	      "LossFee"            :  ""  ,//number类型，折旧租金
	      "OtherFee"           :  ""   //number类型，其它费用
		}
	}


	function getUpMonth(t){
	    var year =t.substring(0,4);            //获取当前日期的年
	    var month = t.substring(4,6);              //获取当前日期的月
	 
	    var year2 = year;
	    var month2 = parseInt(month)-1;
	    if(month2==0) {
	        year2 = parseInt(year2)-1;
	        month2 = 12;
	    }
	    
	    if(month2<10) {
	        month2 = '0'+month2;
	    }
	    var m = year2.toString();
	    var n= month2.toString();
	    var t2 = m+n;
	    return t2;
	}

	$scope.GetMonth = function(){
		var myDate = new Date();
	    var TempDate = myDate.getFullYear() + "" + (myDate.getMonth()+2);
	    var TempDate0 = getUpMonth(TempDate);
	    var TempDate1 = getUpMonth(TempDate0);
	    var TempDate2 = getUpMonth(TempDate1);
	    var TempDate3 = getUpMonth(TempDate2);
	    var TempDate4 = getUpMonth(TempDate3);
	    var TempDate5 = getUpMonth(TempDate4);


    	$scope.monthCostData.Monthlist.push({
			"key" : TempDate0
		});
    	$scope.monthCostData.Monthlist.push({
			"key" : TempDate1
		});
		$scope.monthCostData.Monthlist.push({
			"key" : TempDate2
		});
		$scope.monthCostData.Monthlist.push({
			"key" : TempDate3
		});
		$scope.monthCostData.Monthlist.push({
			"key" : TempDate4
		});
		$scope.monthCostData.Monthlist.push({
			"key" : TempDate5
		});

		$scope.monthCostData.chooseMonth = $scope.monthCostData.Monthlist[0].key;
    }

    $scope.inquire = function(){

    	console.log(JSON.parse($scope.monthCostData.SettleMonth));
    	var params = {
			"BreedBatchId"     :   $scope.monthCostData.BreedBatchId,
			"SettleMonth"      :   JSON.parse($scope.monthCostData.SettleMonth)
		};
		Sparraw.ajaxPost('layer_breedSettle/monthSettleQuery.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				console.log(data);
				$scope.monthCostData.OutputMsg         =  data.ResponseDetail.OutputMsg         ;
				$scope.monthCostData.FeedMsg.FeedInfo  =  data.ResponseDetail.FeedMsg.FeedInfo  ;
				$scope.monthCostData.OtherMsg          =  data.ResponseDetail.OtherMsg          ;
				$scope.monthCostData.chooseFeedNumber  =  JSON.stringify(data.ResponseDetail.FeedMsg.FeedInfo.length);
				if ($scope.monthCostData.FeedMsg.FeedInfo.length == 0) {
					$scope.monthCostData.FeedMsg.FeedInfo.push({
						"FeedCode"       :  "0"  ,//varchar类型，饲料编号
						"FeedName"       :  ""  ,//varchar类型，饲料名称
						"Weight"         :  ""  ,//number类型，公斤数
						"Price_p"        :  ""  ,//number类型，单价
						"Price_sum"      :  ""   //number类型，总金额
					})
				}
				$scope.judgeFeedNumber();
				$scope.forageTotal();
			} else if (data.ResponseDetail.Result = "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			} else {
				sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }

	$scope.judgeMonth = function(){
		$scope.monthCostData.SettleMonth = JSON.stringify($scope.monthCostData.chooseMonth);
		$scope.inquire();
	}
	$scope.GetMonth();
	$scope.judgeMonth();


	$scope.judgeFeedNumber = function(){
		var TempChangeQuantity = 0;//判断改变的行数
		var judgeChange = true;//true-增加种类，false-减少种类
		if ($scope.monthCostData.chooseFeedNumber < $scope.monthCostData.FeedMsg.FeedInfo.length) {
			TempChangeQuantity = $scope.monthCostData.FeedMsg.FeedInfo.length - $scope.monthCostData.chooseFeedNumber;
			judgeChange = false;
		}else{
			TempChangeQuantity = $scope.monthCostData.chooseFeedNumber - $scope.monthCostData.FeedMsg.FeedInfo.length;
			judgeChange = true;
		}

		if (TempChangeQuantity != 0) {
			if (judgeChange == true) {
				for (var i = 0; i < TempChangeQuantity; i++) {
					console.log(i);
					$scope.monthCostData.FeedMsg.FeedInfo.push({
						"FeedCode"       :  "0"  ,//varchar类型，饲料编号
						"FeedName"       :  0    ,//varchar类型，饲料名称
						"Weight"         :  0    ,//number类型，公斤数
						"Price_p"        :  0    ,//number类型，单价
						"Price_sum"      :  0     //number类型，总金额
					})
				}
			}else{
				for (var i = 1; i <= TempChangeQuantity; i++) {
					$scope.monthCostData.FeedMsg.FeedInfo.pop();
					$scope.forageTotal();
				}
			}
		}else{
			
		}
	}

	$scope.judgeFeedType = function(item){
		console.log(item);
		if (item.FeedCode == 2004) {
			item.FeedName = "玉米";
		}else if (item.FeedCode == 2005) {
			item.FeedName = "豆粕";
		}else if (item.FeedCode == 2006) {
			item.FeedName = "添加剂";
		}else if (item.FeedCode == 2007) {
			item.FeedName = "预混料";
		}else if (item.FeedCode == 2008) {
			item.FeedName = "产前料";
		}else if (item.FeedCode == 2009) {
			item.FeedName = "产前料1";
		}else if (item.FeedCode == 2010) {
			item.FeedName = "产前料2";
		}else if (item.FeedCode == 2011) {
			item.FeedName = "产前料3";
		}else if (item.FeedCode == 2012) {
			item.FeedName = "产前料4";
		}

	}


	//饲料结算
	//计算饲料结算的金额
	$scope.forageTotal = function(){
		var total_Weight_temp = 0;
		var total_Price_sum_temp = 0;
		var total_Price_p_temp = 0;

		for (var i = 0; i < $scope.monthCostData.FeedMsg.FeedInfo.length; i++) {
			//  计算 单个饲料总金额
			$scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum = 
					($scope.monthCostData.FeedMsg.FeedInfo[i].Weight * $scope.monthCostData.FeedMsg.FeedInfo[i].Price_p);
			if (!app_IsNum($scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum)) {
				$scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum = 0;
			}else{
				$scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum = $scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum.toFixed(0); 
			}
			//  计算 合计-公斤数
			if(app_IsNum($scope.monthCostData.FeedMsg.FeedInfo[i].Weight)){
				total_Weight_temp += parseFloat($scope.monthCostData.FeedMsg.FeedInfo[i].Weight);
			}
			//  计算 合计-总金额
			if(app_IsNum($scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum)){
				total_Price_sum_temp += parseFloat($scope.monthCostData.FeedMsg.FeedInfo[i].Price_sum);
			}
		};
		//  计算 合计-单价数
		total_Price_p_temp = total_Price_sum_temp/total_Weight_temp ;
		if(!app_IsNum(total_Price_p_temp)){
			total_Price_p_temp = 0;
		}else{
			total_Price_p_temp = Math.round(total_Price_p_temp*100)/100;  
		}
		$scope.monthCostData.FeedMsg.total_Weight = total_Weight_temp;
		$scope.monthCostData.FeedMsg.total_Price_sum = total_Price_sum_temp;
		$scope.monthCostData.FeedMsg.total_Price_p = total_Price_p_temp;
	};


	$scope.save = function(){

		if ($scope.monthCostData.FeedMsg.FeedInfo.length != 0) {
			for (var i = 0; i < $scope.monthCostData.FeedMsg.FeedInfo.length; i++) {
				if ($scope.monthCostData.FeedMsg.FeedInfo[i].FeedCode == "0") {
					return app_alert("请选择饲料种类。");
				}
			}
		}else{

		}

		var params = {
			"FarmId"     :   $scope.monthCostData.FarmId,
			"BreedBatchId"     :   $scope.monthCostData.BreedBatchId,
			"SettleMonth"      :   JSON.parse($scope.monthCostData.SettleMonth) ,
			"OutputMsg"        :   $scope.monthCostData.OutputMsg   ,
			"FeedMsg"          :   $scope.monthCostData.FeedMsg     ,
			"OtherMsg"         :   $scope.monthCostData.OtherMsg
		};
		Sparraw.ajaxPost('layer_breedSettle/monthSettleSave.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				Sparraw.myNotice("保存成功！");
			} else if (data.ResponseDetail.Result = "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			} else {
				sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.goYoungChickenWorth = function(){		
		$state.go("youngChickenWorth");
	}
})

//青年鸡身价摊销
.controller("youngChickenWorthCtrl",function($scope, $state, $http, AppData,uiGridConstants){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.youngChickData = {
		"FarmId"               :  $scope.sparraw_user_temp.farminfo.id                ,
		"BreedBatchId"         :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,//int型，批次Id
		"PulletMsg":{
              "SumOfAmount"   :""  ,
              "Price"         :""  ,
              "SumOfMoney"    :""  ,
              "SumLayAmount"  :""  ,
              "CostPerUnit"   :""
        }
	}

	

	$scope.inquire = function(){
		var params = {
			"BreedBatchId":$scope.youngChickData.BreedBatchId
		};
		Sparraw.ajaxPost('layer_breedSettle/pulletSettleQuery.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				$scope.youngChickData.PulletMsg = data.ResponseDetail.PulletMsg;
				$scope.calculate();
			} else if (data.ResponseDetail.Result = "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			} else {
				sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.inquire();

	$scope.calculate = function(){
		$scope.youngChickData.PulletMsg.SumOfMoney = ($scope.youngChickData.PulletMsg.SumOfAmount * $scope.youngChickData.PulletMsg.Price).toFixed(2); 
		if (!app_IsNum($scope.youngChickData.PulletMsg.SumOfMoney)) {
			$scope.youngChickData.PulletMsg.SumOfMoney = 0;
		}else{
			$scope.youngChickData.PulletMsg.SumOfMoney = ($scope.youngChickData.PulletMsg.SumOfAmount * $scope.youngChickData.PulletMsg.Price).toFixed(2); 
		}

		$scope.youngChickData.PulletMsg.CostPerUnit = ($scope.youngChickData.PulletMsg.Price/$scope.youngChickData.PulletMsg.SumLayAmount).toFixed(2);
		if (!app_IsNum($scope.youngChickData.PulletMsg.CostPerUnit)) {
			$scope.youngChickData.PulletMsg.CostPerUnit = 0;
		}else{
			$scope.youngChickData.PulletMsg.CostPerUnit = ($scope.youngChickData.PulletMsg.Price/$scope.youngChickData.PulletMsg.SumLayAmount).toFixed(2); 
		}
	}



	$scope.save = function(){

		//判断是否为空
		if ((isNull($scope.youngChickData.PulletMsg.SumOfAmount)) 
			&& (isNull($scope.youngChickData.PulletMsg.Price)) 
			&& (isNull($scope.youngChickData.PulletMsg.SumOfMoney))
			&& (isNull($scope.youngChickData.PulletMsg.SumLayAmount)) 
			&& (isNull($scope.youngChickData.PulletMsg.CostPerUnit))) {
		}else{
			return;
		};


		var params = {
			"BreedBatchId"  :  $scope.youngChickData.BreedBatchId  ,
			"FarmId"        :  $scope.youngChickData.FarmId        ,
	        "PulletMsg":{
	              "SumOfAmount"   :  $scope.youngChickData.PulletMsg.SumOfAmount   ,
	              "Price"         :  $scope.youngChickData.PulletMsg.Price         ,
	              "SumOfMoney"    :  $scope.youngChickData.PulletMsg.SumOfMoney    ,
	              "SumLayAmount"  :  $scope.youngChickData.PulletMsg.SumLayAmount  ,
	              "CostPerUnit"   :  $scope.youngChickData.PulletMsg.CostPerUnit
	              }
		};
		Sparraw.ajaxPost('layer_breedSettle/pulletSettleSave.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				console.log(data);
				Sparraw.myNotice("保存成功!");
			} else if (data.ResponseDetail.Result = "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			} else {
				sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

})

//月度盈利报告
.controller("breedMonthProfitCtrl", function($scope, $state, $http,uiGridConstants){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.gridOptions = {
		// "FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		'showTableData'  :  {
			'firstFixed': true  ,
			'rowHeight' : 47    ,//内容高度
			'header'    : []    ,
			'TableData' : []
		},
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
		enableVerticalScrollbar   : uiGridConstants.scrollbars.NEVER,
	};

	$scope.monthData = {
		"datas" : {
			"ItenName" : "",
			"PriceSum_this" : "",
			"PricePKilo_this" : "",
			"SaleChicken_last" : "",
			"PricePKilo_last" : ""
		}
	};

	$scope.drowTable = function(){
	    var templateTabel = '<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "本月" + '</p></div><div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "总金额" + '</p></div><div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "元/公斤" + '</p></div></div></div>'
	    var templateTabe2 = '<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "上月" + '</p></div><div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "总金额" + '</p></div><div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "元/公斤" + '</p></div></div></div>'
	    $scope.gridOptions.showTableData.header = [
	    	{name:'ItemName',displayName:'', width:'28%',visible:false,
	    		cellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {		
	    			if(rowRenderIndex == 4 || rowRenderIndex == 14 || rowRenderIndex == 15){
	    				return 'bold';
	    			}
	    		return;
        	}},
			{name:'PricSum_this',displayName:'',width:'18%',enableColumnMenu:false,enableCellEdit: false, headerCellTemplate:templateTabel},			
			{name:'PricePKilo_this',displayName:'',width:'18%',enableColumnMenu:false},		
			{name:'SaleChicken_last',displayName:'',width:'18%',enableColumnMenu:false, headerCellTemplate:templateTabe2},			
			{name:'PricePKilo_last',displayName:'',width:'18%',enableColumnMenu:false},			
	    ];
	    $scope.gridOptions.showTableData.TableData = $scope.monthData.datas;
	    // console.log($scope.gridOptions.showTableData.TableData);
	   /* $scope.gridOptions.onRegisterApi()={

	    }
*/
	    GetShowTable($scope.gridOptions.showTableData,$scope);
	}
	$scope.getTableData = function(){
		var params = {
			"BreedBatchId"  :   $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			"FarmId"      :   $scope.sparraw_user_temp.farminfo.id
		};
		Sparraw.ajaxPost('layer_breedSettle/monthProfitRep.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				$scope.monthData.datas = data.ResponseDetail.Datas;
				$scope.drowTable();
				Sparraw.myNotice(data.ResponseDetail.TipMsg);
			} else if (data.ResponseDetail.Result == 'Fail'){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}
		});
	}
	$scope.getTableData();

})

//历史数据导入
.controller("historyDataImportCtrl",function($scope, $state, $http, AppData,uiGridConstants){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	/*setTimeout(
		function (){
			app_lockOrientation('landscape');//进入时横屏
		}
	,500);
	$scope.gobatchManage = function(){
		app_lockOrientation('portrait');//出去时竖屏
		$state.go("batchManage");
	}*/
	$scope.gridOptions = {};
	/*$scope.historyQueryData = {
		"TableDatas" : [],
	};*/
	$scope.historySelectData = {
		"farmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"selectHouse"  :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]),
		"farmBreedBatchCode" : $scope.sparraw_user_temp.farminfo.farmBreedBatchCode,
		"houseId"	   :  "",
		"houseBreedId" :  "",
		// "houseName"    ： "",
		"modifyData"   :  [],
 		"dataInput"    :  {
			"day_age"  : "",
			"week_age" : "",
			"culling_all" : "",
			"curLayNum" : "",
			"layStandard" : "",
			"curBrokenNum" : "",
			"daily_feed"  : "",
			"daily_weight" : "",
			"daily_water" : ""
 		},
	};


	
	
	
	$scope.historySelectData.houseId = JSON.parse($scope.historySelectData.selectHouse).HouseId;
	$scope.historySelectData.houseBreedId = JSON.parse($scope.historySelectData.selectHouse).HouseBreedBatchId;

	$scope.drowTable = function(){
		var headerCellTem1 = "<div style='color:black;text-align:center;position:relative; left:0px; top:15px;'><p>周龄</p></div>";
		var headerCellTem2 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>死淘<br>只</p></div>";
		var headerCellTem3 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>产蛋<br>枚</p></div>";
		var headerCellTem4 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>筐规格<br>斤/筐</p></div>";
		var headerCellTem5 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>破损<br>枚</p></div>";
		var headerCellTem6 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>采食<br>公斤</p></div>";
		var headerCellTem7 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>饮水<br>立方</p></div>";
		var headerCellTem8 = "<div style='color:black;text-align:center;position:relative; left:0px; top:5px;'><p>均重<br>公斤</p></div>";
		$scope.gridOptions.columnDefs = [		
			{name:'day_age',displayName:'日龄', width:'0%',visible:false},
			{name:'week_age',displayName:'周龄',width:'12.5%',enableColumnMenu:false,enableCellEdit: false, headerCellTemplate:headerCellTem1},			
			{name:'culling_all',displayName:'死淘<br>只',width:'12%',enableColumnMenu:false,headerCellTemplate:headerCellTem2},
			{name:'curLayNum',displayName:'产蛋<br>枚',width:'15%',enableColumnMenu:false,headerCellTemplate:headerCellTem3},
			{name:'layStandard',displayName:'筐规格<br>斤/筐',width:'13.5%',enableColumnMenu:false,headerCellTemplate:headerCellTem4},
			{name:'curBrokenNum',displayName:'破损<br>枚',width:'13.5%',enableColumnMenu:false,headerCellTemplate:headerCellTem5},			
			{name:'daily_feed',displayName:'采食<br>公斤',width:'13.5%',enableColumnMenu:false,headerCellTemplate:headerCellTem6},			
			{name:'daily_water',displayName:'饮水<br>立方',width:'12.5%',enableColumnMenu:false,headerCellTemplate:headerCellTem7},		
			{name:'daily_weight',displayName:'均重',width:'12.5%',enableColumnMenu:false,headerCellTemplate:headerCellTem8}
		];
		$scope.gridOptions.enableCellEdit = true;
		$scope.gridOptions.enableCellEditOnFocus = true;
		$scope.gridOptions.enableHorizontalScrollbar = uiGridConstants.scrollbars.NEVER;
    	$scope.gridOptions.enableVerticalScrollbar = uiGridConstants.scrollbars.NEVER;
		$scope.gridOptions.data = $scope.historySelectData.dataInput;

		//判断哪些数据进行过修改
	    $scope.gridOptions.onRegisterApi = function(gridApi){
	      gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
           $scope.historySelectData.modifyData.push(rowEntity.day_age);
           $scope.historySelectData.modifyData = deleteRepeat($scope.historySelectData.modifyData);
          });
	    };

		// console.log("gridOptions=" + $scope.gridOptions.data +"000"+ $scope.historyQueryData.TableDatas);
	}
	$scope.drowTable();
	$scope.getTableData = function(){
		var params = {
			"farmBreedId"  :   $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			"houseId"      :   $scope.historySelectData.houseId
		};
		Sparraw.ajaxPost('layer_breedBatch/historyDataQuery.action', params, function(data){
			if(data.ResponseDetail.Result == 'Success'){
				$scope.historySelectData.dataInput = data.ResponseDetail.TableDatas;
				for (var m = 0; m < $scope.historySelectData.dataInput.length; m++) {
					$scope.historySelectData.dataInput[m].layStandard = $scope.historySelectData.dataInput[m].layStandard*2;
				};
				document.getElementById('inputDivId').style.height = ($scope.historySelectData.dataInput.length * 36) + 'px';
				$scope.drowTable();
			} else if (data.ResponseDetail.Result = "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			} else {
				sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}
	$scope.getTableData();
	$scope.judgeHouse = function(item){
		console.log(JSON.parse($scope.historySelectData.selectHouse));
		$scope.historySelectData.houseId = JSON.parse($scope.historySelectData.selectHouse).HouseId;
		$scope.getTableData();



	}
	$scope.judgeHouse();	
	$scope.save = function(){

		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		

		for (var i = 0; i < $scope.historySelectData.dataInput.length; i++) {
			$scope.historySelectData.dataInput[i].$$hashKey = undefined;
		};
		//删除未修改的数据
		$scope.uploadDataInput = [];
		for (var i = 0; i < $scope.historySelectData.modifyData.length; i++) {
			for (var j = 0; j < $scope.historySelectData.dataInput.length; j++) {
				if ($scope.historySelectData.dataInput[j].day_age == $scope.historySelectData.modifyData[i]) {
					console.log($scope.historySelectData.dataInput[j]);
					$scope.uploadDataInput.push($scope.historySelectData.dataInput[j]);
				};
			};
		};

		if ($scope.uploadDataInput.length == 0) {
			return app_alert('尚未修改信息。');
		};

		for (var i = 0; i < $scope.uploadDataInput.length; i++) {
			$scope.uploadDataInput[i].day_age = NulltoZero($scope.uploadDataInput[i].day_age);
			$scope.uploadDataInput[i].culling_all = NulltoZero($scope.uploadDataInput[i].culling_all);
			$scope.uploadDataInput[i].curLayNum = NulltoZero($scope.uploadDataInput[i].curLayNum);
			$scope.uploadDataInput[i].egg_box_size = NulltoZero($scope.uploadDataInput[i].layStandard/2);
			$scope.uploadDataInput[i].curBrokenNum = NulltoZero($scope.uploadDataInput[i].curBrokenNum);
			$scope.uploadDataInput[i].daily_feed = NulltoZero($scope.uploadDataInput[i].daily_feed);
			$scope.uploadDataInput[i].daily_weight = NulltoZero($scope.uploadDataInput[i].daily_weight);
			$scope.uploadDataInput[i].daily_water = NulltoZero($scope.uploadDataInput[i].daily_water);
			if($scope.uploadDataInput[i].curLayNum != 0 && $scope.uploadDataInput[i].layStandard == 0) {
				return app_alert('产蛋不为零，须输入规格！');
			}
		};

		var params = {
		      	"HouseBreedId" :  $scope.historySelectData.houseBreedId  ,
				"HouseId"      :  $scope.historySelectData.houseId       ,
				"dataInput"    :  $scope.uploadDataInput
			};
			Sparraw.ajaxPost('layer_dataInput/saveDR_v2.action', params, function(data){
		   		if (data.ResponseDetail.LoginResult == "Success") {
					$scope.getTableData();
					$scope.drowTable();
					Sparraw.myNotice("保存成功");
				}else if (data.ResponseDetail.Result == "Fail"){
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
	}
})



//生产报表
.controller("dailyTableCtrl",function($scope, $state, $ionicLoading, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setPortrait(true,true);
	






	$scope.goDailyReport = function(){
		$state.go("dailyReport");
	}

	$scope.goDailyStatistical = function(){
		$state.go("dailyStatistical");
	}
	$scope.goProductionDaily = function(){
		var param = {"fromPage":"dailyTable"};
		$state.go("productionDaily",param);
	}

	$scope.goWeekly = function(){
		$state.go("weekly");
	}



	$scope.goDocPlace = function(){
		$state.go("docPlace");
	}



})

// 生产记录
.controller("dailyReportCtrl",function($scope, $state,$ionicLoading, $http, $ionicPopup, $ionicScrollDelegate, $stateParams, $ionicScrollDelegate, AppData){
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setPortrait(true,true);
	
	var type = 0;
	$scope.goDailyTable = function(){
		if ($scope.sparraw_user_temp.Authority.DailyInput === "All") {

		}else{
			return $state.go("dailyTable");
		};
		if(chang == true){
			type = 1;
			app_confirm('数据已经被修改，请确认是否保存！','提示',null,function(buttonIndex){
				//console.log("buttonIndex:" + buttonIndex);
				//return;
                    if(buttonIndex == 2){
						$scope.save();
			        }else{
						$state.go("dailyTable");
					}
              });
		}else{
			$state.go("dailyTable");
		}
	}
	
	$scope.goProductionDaily = function(){

		if ($scope.sparraw_user_temp.Authority.DailyInput === "All") {

		}else{
			return $state.go("productionDaily",{"fromPage":"dailyReport"});
		};
		
		if(chang == true){
			type = 2;
			app_confirm('数据已经被修改，请确认是否保存！','提示',null,function(buttonIndex){
				
                    if(buttonIndex == 2){
						$scope.save();
			        }else{
						$state.go("productionDaily",{"fromPage":"dailyReport"});
					}
              });
		}else{
			$state.go("productionDaily",{"fromPage":"dailyReport"});
		}
	}

	$scope.dailyReportData = {
		"FarmBreedId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId                ,
        "selectHouse"    :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0])       ,
        "HouseBreedId"   :  ""            ,
        "HouseId"        :  ""            ,
        "HouseName"      :  ""            ,
        "CurDate"        :  ""            ,//varchar型，当前日期
        "CurDayAge"      :  ""            ,//int型，当前生长日龄
        "selectDayAge"   :  -1            ,//选择的日龄
        "GrowthWeekAge"  :  ""            ,//int型，当前生长周龄
        "LayerWeekAge"   :  ""            ,//int型，当前产蛋周龄
        "DataDate"       :  ""            ,//varchar型，数据日期
        "culling_all"    :  ""            ,//int型，总死淘数量
        "curLayNum"      :  ""            ,//int型，产蛋总数量
        "all_box_num"    :  ""            ,
        "egg_box_size"   :  "25.5"        ,//number型，箱子规格，单位：公斤/箱
        "curBrokenNum"   :  ""            ,//int型，其中破损蛋数量
        "daily_feed"     :  ""            ,//number型，日耗料，单位：公斤
        "daily_water"    :  ""            ,//number型，日耗水,单位：立方米
        "daily_weight"   :  ""            ,//number型，鸡体均重,单位：公斤
        "showDate"       :  ""            ,//显示的日龄
        "between"        :  ""            ,//选择日期与当前日期的差
		"dataInput"		 : [{
			"day_age"    : "",
			"culling_all": "",
			"curLayNum":  "",
			"egg_box_size": "25.5",
			"curBrokenNum": "",
			"daily_feed": "",
			"daily_weight": "",
			"daily_water": ""
		}]

	}
	

    $scope.inquire = function() {
		console.log("查询方法");
		var params = {
	      	"FarmBreedId" :  $scope.dailyReportData.FarmBreedId    ,
			"HouseId"     :  $scope.dailyReportData.HouseId        ,
			"DayAge"      :  $scope.dailyReportData.selectDayAge
		};
		Sparraw.ajaxPost('layer_dataInput/queryDR_v2.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			//赋值
				$scope.dailyReportData.CurDate = data.ResponseDetail.CurDate,
				$scope.dailyReportData.GrowthWeekAge = data.ResponseDetail.GrowthWeekAge,
				$scope.dailyReportData.LayerWeekAge = data.ResponseDetail.LayerWeekAge,
				$scope.dailyReportData.culling_all = data.ResponseDetail.culling_all,
				$scope.dailyReportData.curLayNum = data.ResponseDetail.curLayNum,
				//$scope.dailyReportData.egg_box_size = data.ResponseDetail.egg_box_size,
				$scope.dailyReportData.curBrokenNum = data.ResponseDetail.curBrokenNum,
				$scope.dailyReportData.daily_feed = data.ResponseDetail.daily_feed,
				$scope.dailyReportData.daily_water = data.ResponseDetail.daily_water,
				$scope.dailyReportData.daily_weight = data.ResponseDetail.daily_weight,
				$scope.dailyReportData.dataInput.culling_all = data.ResponseDetail.culling_all,
				$scope.dailyReportData.dataInput.curBrokenNum = data.ResponseDetail.curBrokenNum,
				$scope.dailyReportData.dataInput.curLayNum = data.ResponseDetail.curLayNum,
				$scope.dailyReportData.dataInput.egg_box_size = data.ResponseDetail.egg_box_size,
				$scope.dailyReportData.dataInput.daily_feed = data.ResponseDetail.daily_feed,
				$scope.dailyReportData.dataInput.daily_water = data.ResponseDetail.daily_water,
				$scope.dailyReportData.dataInput.daily_weight = data.ResponseDetail.daily_weight

				if ($scope.dailyReportData.CurDayAge == "") {
					var NowDatestr = getCurDate();
					$scope.dailyReportData.DataDate = NowDatestr;
					$scope.dailyReportData.CurDayAge = data.ResponseDetail.CurDayAge;
					$scope.dailyReportData.showDate = $scope.dailyReportData.CurDayAge;
				}else{
					$scope.dailyReportData.showDate = $scope.dailyReportData.selectDayAge;
				}

				if ($scope.dailyReportData.showDate == -1) {
					$scope.dailyReportData.showDate = data.ResponseDetail.CurDayAge;
				}else{
					
				}

				console.log("显示的日龄");
				console.log($scope.dailyReportData.showDate);
				//算出总箱数
				$scope.dailyReportData.all_box_num = ($scope.dailyReportData.curLayNum / 360).toFixed(1);
				//算出规格
				if (data.ResponseDetail.egg_box_size == 0) {
					$scope.dailyReportData.egg_box_size = "25.5";
				}else{
					$scope.dailyReportData.egg_box_size = (data.ResponseDetail.egg_box_size * 2).toFixed(1);
				}


			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};			
		});

		
	}

	//计算日期差值
	$scope.GetDateDiff = function(startDate,endDate){
		var startTime = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();     
	    var endTime = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();     
	    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
	    return  dates;  
	}

	//创建预计出栏时间
	var OverDate;
	//日期选择器 
    $scope.datePopup = new Date("01-01-2016");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();

	$scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '关闭', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2012, 12, 31), //Optional
            to: new Date(2020, 12, 31), //Optional
            callback: function (val) { //Optional
            	datePickerCallbackPopup(val);
            }
    	};
	var datePickerCallbackPopup = function (val) {
            if (typeof(val) === 'undefined') {
                console.log('未选择日期');
            } else {
                $scope.datepickerObjectPopup.inputDate = val;
                $scope.datePopup = val;
                var selectDate = "";
                //选择的时间
                selectDate = val.toLocaleDateString().replace(/(日)/g,"");
                selectDate = selectDate.replace(/\/|(年)|(月)/g,"-");
                selectDate = selectDate.replace(/(-)/g,"/");
                //当天的时间
                var TempNowDate = new Date();
				var NowDatestr = TempNowDate.getFullYear()+"/"+(TempNowDate.getMonth()+1)+"/"+(TempNowDate.getDate()+0);
				var NowDate = new Date(NowDatestr);
				
				
				$scope.dailyReportData.between = $scope.GetDateDiff(selectDate,NowDatestr);
				

				if (val > NowDate) {
					$scope.dailyReportData.selectDayAge = $scope.dailyReportData.CurDayAge+$scope.dailyReportData.between;
				}else if (val < NowDate) {
					$scope.dailyReportData.selectDayAge = $scope.dailyReportData.CurDayAge-$scope.dailyReportData.between;
				}else{
					$scope.dailyReportData.selectDayAge = $scope.dailyReportData.CurDayAge+$scope.dailyReportData.between;
				}

				//将选择的时间赋值给显示DataDate
				$scope.dailyReportData.DataDate = selectDate.substring(0,10);
				if ($scope.dailyReportData.DataDate[6] == "/") {
                	$scope.dailyReportData.DataDate = $scope.dailyReportData.DataDate.replace(/(.{5})/g,'$10');
                };
                if ($scope.dailyReportData.DataDate[9]) {

                }else{
                	$scope.dailyReportData.DataDate = $scope.dailyReportData.DataDate.replace(/(.{8})/g,'$10');
                };
                $scope.dailyReportData.DataDate = $scope.dailyReportData.DataDate.replace(/\//g, "-");
             	$scope.inquire();   
            }
    };




	$scope.judgeHouse = function(item){
		$scope.dailyReportData.HouseBreedId = JSON.parse($scope.dailyReportData.selectHouse).HouseBreedBatchId;
		$scope.dailyReportData.HouseId = JSON.parse($scope.dailyReportData.selectHouse).HouseId;
		$scope.inquire();
	}

	setTimeout(
		function (){
			$scope.judgeHouse();
		}
	,1000);
	


	var chang = false;
	$scope.changeValue = function(){
		chang = true;
		if (isNaN($scope.dailyReportData.curLayNum)) {
			$scope.dailyReportData.all_box_num = 0;
		}else{
			$scope.dailyReportData.all_box_num = ($scope.dailyReportData.curLayNum / 360).toFixed(1);
		}
	}
	

	$scope.save = function(){
		
		

		
		$scope.dailyReportData.egg_box_size = ($scope.dailyReportData.egg_box_size / 2).toFixed(2);
        //删除未修改的数据
        $scope.uploadDataInput = [{
			"day_age"    : NulltoZero($scope.dailyReportData.selectDayAge),
			"culling_all": NulltoZero($scope.dailyReportData.culling_all),
			"curLayNum": NulltoZero($scope.dailyReportData.curLayNum),
			"egg_box_size": NulltoZero($scope.dailyReportData.egg_box_size),
			"curBrokenNum": NulltoZero($scope.dailyReportData.curBrokenNum),
			"daily_feed": NulltoZero($scope.dailyReportData.daily_feed),
			"daily_weight": NulltoZero($scope.dailyReportData.daily_weight),
			"daily_water": NulltoZero($scope.dailyReportData.daily_water)
		}];

		var params = {
	      	"HouseBreedId"  :  $scope.dailyReportData.HouseBreedId  ,
	        "HouseId"       :  $scope.dailyReportData.HouseId       ,
			"dataInput"		:  $scope.uploadDataInput   		    ,
	        "culling_all"   :  $scope.dailyReportData.culling_all   ,
	        "curLayNum"     :  $scope.dailyReportData.curLayNum     ,
	        "egg_box_size"  :  $scope.dailyReportData.egg_box_size  ,
	        "curBrokenNum"  :  $scope.dailyReportData.curBrokenNum  ,
	        "daily_feed"    :  $scope.dailyReportData.daily_feed    ,
	        "daily_weight"  :  $scope.dailyReportData.daily_weight  ,
	        "daily_water"   :  $scope.dailyReportData.daily_water
		};
		console.log($scope.sparraw_user_temp.Authority);
		if ($scope.sparraw_user_temp.Authority.DailyInput === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		Sparraw.ajaxPost('layer_dataInput/saveDR_v2.action', params, function(data){
			if (data.ResponseDetail.LoginResult == "Success") {
				chang = false;
	   			Sparraw.myNotice("保存成功！");
	   			$scope.inquire();
				if(type == 1){
					$state.go("dailyTable");
				}else if(type == 2){
					$state.go("productionDaily",{"fromPage":"dailyReport"});
				}
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};			
		});
	}
})

//生产周报
.controller("weeklyCtrl",function($scope, $state, $http, $ionicLoading,$ionicLoading, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	

	


    $scope.goDailyTable = function(){
    	$state.go("dailyTable");
    }


	

	$scope.weeklyData = {
		"selectHouse"       :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0])  ,
		"selectHouseId"     :  ""    ,
		"FarmBreedId"       :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId           ,
		"PlaceNum"          :  ""    , 
        "HouseBreedId"      :  ""    , 
        "CurLayerWeekAge"   :  ""    , 
        "CurGrowthWeekAge"  :  ""    , 
        "AmountFirstLayer"  :  ""    ,
        "DataInfos"         :  ""    , 
    	"ViewType"          :  '01'  ,
    	"weekData"          :  []    ,   
    	"unit"              :  '累计'                 
	}

	$scope.switchUnit = function(){
		//显示哪个
		if ($scope.weeklyData.unit == "累计") {
			$scope.weeklyDiv = true;
			$scope.totalDiv = false;
			$scope.weeklyData.unit = "每周";
			$scope.weeklyData.ViewType = "02";
		}else{
			$scope.weeklyDiv = false;
			$scope.totalDiv = true;
			$scope.weeklyData.unit = "累计";
			$scope.weeklyData.ViewType = "01";
		}
		$scope.judgeHouse();
	}


	$scope.inquire = function(){
		var params = {
			'IsNeedDelay':'Y',
   			"FarmBreedId":$scope.weeklyData.FarmBreedId,
			"HouseId":$scope.weeklyData.selectHouseId,
			"ViewType":$scope.weeklyData.ViewType
		};
		console.log(params);
		Sparraw.ajaxPost('layer_dataInput/queryWR.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			$scope.weeklyData.weekData          =  data.ResponseDetail.weekData          ;
	   			$scope.weeklyData.DataInfos         =  data.ResponseDetail.DataInfos         ;
				$scope.weeklyData.CurGrowthWeekAge  =  data.ResponseDetail.CurGrowthWeekAge  ;
				$scope.weeklyData.CurLayerWeekAge   =  data.ResponseDetail.CurLayerWeekAge   ;
				$scope.weeklyData.PlaceDate         =  data.ResponseDetail.PlaceDate         ;
				$scope.weeklyData.PlaceNum          =  data.ResponseDetail.PlaceNum          ;
				$scope.weeklyData.AmountFirstLayer  =  data.ResponseDetail.AmountFirstLayer  ;
	   			$scope.GetTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};			
		});
	}



  	$scope.GetTable = function(){
		if ($scope.weeklyData.unit == "每周") {

			console.log("累计！@！！");
			//累计数据
			var TotalHeadName = [
				"growth_weekage"   ,//int型，生长周龄
				"accCD"            ,//int型，累计死淘数，只
				"accCDRate"        ,//varchar型，累计死淘率
				"accLayNum"        ,//int型，累计产蛋数，单位：万枚
				"accLayWeight"     ,//number型，累计产蛋公斤数，单位：公斤
				"accLayNumPer"     ,//int型，只鸡累计产蛋数，单位：枚
				"accLayWeightPer"  ,//number型，只鸡累计产蛋总重量，单位：公斤
				"accFeedWeight"    ,//number型，累计饲料总重量，单位：吨
				"accFeedWeightPer" ,//number型，只鸡累计饲料消耗，单位：公斤/天·只
				"rOfFE"            ,//number型，累计料蛋比
				"accWater"          //number型，累计饮水，单位：立方
			];
			var TotalHeadText = ["生长<br>周龄",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"累计<br>料蛋比",
								"累计饮水<br>立方"];
			var TotalTemplate = ['',
								'<div style="width:100px;height:40px;"><div style="width:100px;height:20px; background:rgba(251, 251, 251, 1);"><p style="text-align:center;">' + "累计死淘" + '</p></div><div style="width:50px;height:20px;"><div style="width:50px;height:20px;"><p style="text-align:center;">' + "只" + '</p></div><div style="width:50px;height:20px; position:relative;left:50px;top: -20px;"><p style="text-align:center;"> ' + "‰" + '</p></div></div></div>',
								'',
								'<div style="width:100px;height:40px;"><div style="width:110px;height:20px; background:rgba(251, 251, 251, 1);"><p style="text-align:center;">' + "累计产蛋" + '</p></div><div style="width:55px;height:20px;"><div style="width:55px;height:20px;"><p style="text-align:center;">' + "万枚" + '</p></div><div style="width:55px;height:20px; position:relative;left:50px;top: -20px;"><p style="text-align:center;"> ' + "公斤" + '</p></div></div></div>',
								'',
								'<div style="width:110px;height:40px;"><div style="width:110px;height:20px; background:rgba(251, 251, 251, 1);"><p style="text-align:center;">' + "只鸡累计产蛋" + '</p></div><div style="width:55px;height:20px;"><div style="width:55px;height:20px;"><p style="text-align:center;">' + "枚" + '</p></div><div style="width:55px;height:20px; position:relative;left:55px;top: -20px;"><p style="text-align:center;"> ' + "公斤" + '</p></div></div></div>',
								'',
								'<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1);"><p style="text-align:center;">' + "累计饲料消耗" + '</p></div><div style="width:70px;height:20px;"><div style="width:70px;height:20px;"><p style="text-align:center;">' + "吨" + '</p></div><div style="width:50px;height:20px; position:relative;left:50px;top: -20px;"><p style="text-align:center;"> ' + "公斤/只" + '</p></div></div></div>',
								'',
								'',
								''];

			var TotalheaderDiv = [];
			for (var i = 0; i < TotalHeadName.length; i++) {
				TotalheaderDiv.push("<div style='color:black;text-align:center;position:relative; left:0px; top:0px;height:40px;'><p>" + TotalHeadText[i] + "</p></div>");
			}

			for (var i = 0; i < TotalHeadName.length; i++) {
				if (i == 0) {
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '40',
						displayName         :  '',
						height				:  '40',
						headerCellTemplate  :  TotalheaderDiv[i],
						cellTemplate        :  '',
						pinnedLeft          :  true,
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}else if (i == 1||i == 2) {
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '50',
						height				:  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}else if (i == 3||i == 4) {
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '55',
						height				:  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}else if (i == 5 || i == 6) {
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '55',
						height				:  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle'
					})
				}else if(i == 8 || i == 7){
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '60',
						height				:  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle'
					})
				}else if (i == 10 || i == 9) {
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '70',
						height				:  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalheaderDiv[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}/*else{
					$scope.gridTotal.columnDefs.push({
						name                :  TotalHeadName[i],
						enableColumnMenu    :  false,
						width               :  '70',
						displayName         :  '',
						headerCellTemplate  :  TotalheaderDiv[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  '' 
					})
				}*/
			}
			for (var i = 0; i < $scope.weeklyData.weekData.length; i++) {
				$scope.weeklyData.weekData[i].accFeedWeight = ($scope.weeklyData.weekData[i].accFeedWeight/1000).toFixed(1)
			}
			$scope.gridTotal.data = $scope.weeklyData.weekData;
			window.onresize = function(){};
		}else{
			console.log("周！！！！@！！");
			var TotalTemplate = ['',
                                '<div style="width:100px;height:40px;"><div style="width:100px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "周死淘" + '</p></div>' +
                                '<div style="width:50px;height:20px;"><div style="width:55px;height:20px;"><p style="text-align:center;">' + "只" + '</p></div>' +
                                '<div style="width:50px;height:20px; position:relative;left:55px; top:-20px;"><p style="text-align:center;"> ' + "‰" + '</p></div></div></div>',
								'',
								'<div style="width:110px;height:40px;"><div style="width:110px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "周产蛋" + '</p></div>' +
								'<div style="width:55px;height:20px;"><div style="width:55px;height:20px;"><p style="text-align:center;">' + "枚" + '</p></div>' +
								'<div style="width:55px;height:20px; position:relative;left:55px; top:-20px;"><p style="text-align:center;"> ' + "%" + '</p></div></div></div>',
							    '',
							    '<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "蛋重量" + '</p></div>' +
								'<div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "公斤" + '</p></div>' +
								'<div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "克/枚" + '</p></div></div></div>',
								'',
								'',
								'<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "饲料消耗" + '</p></div>' +
								'<div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "公斤" + '</p></div>' +
							    '<div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "克/只天" + '</p></div></div></div>',
								'',
								'<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "饮水量" + '</p></div>' +
								'<div style="width:55px;height:20px;"><div style="width:55px;height:20px;"><p style="text-align:center;">' + "立方" + '</p></div>' +
								'<div style="width:65px;height:20px; position:relative;left:55px; top:-20px;"><p style="text-align:center;"> ' + "ml/只天" + '</p></div></div></div>'];
			var TotalHeadName = ["生长<br>周龄",
								 "",
								 "",
								 "",
								 "",
								 "",
								 "",
								 "破损<br>枚",
								 "",
								 "",
								 "",
								 "",
								 "水料<br>比",
								 "体重<br>公斤"];

			var TotalheaderDiv = [];
			for (var i = 0; i < TotalHeadName.length; i++) {
                TotalheaderDiv.push("<div style='color:black;text-align:center;position:relative; left:0px; top:0px;height:40px;'><p>" + TotalHeadName[i] + "</p></div>");
			}
			//周数据
			var WeeklyHeadName = [
				"growth_weekage",//int型，生长周龄
				"culling_all",//int型，当周死淘数
				"cull_rate",//varchar型，当周死淘率
				"curLayNum",//int型，当周产蛋总量
				"curLayRate",//varchar型,当周产蛋率
				"curLaySumWeight",//number型，鸡蛋总重，公斤
				"curLayAvgWeight",//number型，鸡蛋均重，克/枚
				"curBrokenNum",//int型，破损蛋数量
				"weekly_feed",//number型，当周饲料，公斤
				"avg_feed",//number型，平均饲料，g/天·只
				"weekly_water",//number型，当周饮水量，立方
				"avg_water",//number型，平均饮水量，ml/天·只
				"rOfWM",//number型，水料比
				"chickenWeight",//number型，鸡体重，公斤
		    ];
			for (var i = 0; i < WeeklyHeadName.length; i++) {
				if (i == 0) {
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '40',
						displayName         :  '',
						headerCellTemplate  :  TotalheaderDiv[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						pinnedLeft          :  true,
						cellClass           :  'middlebold'
					})
				}else if(i == 1 || i == 3 || i == 2 || i == 4) {
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '55',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle'
					})
				}/*else if (i == 2 || i == 4 ){
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '55',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  ''
					})
				}*/else if(i == 5 || i == 6){
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '60',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle'
					})
				}else if (i == 8||i == 10) {
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '60',
						displayName         :  '',
						headerCellTemplate  :  TotalTemplate[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}else if (i == 9||i == 11) {
					$scope.gridWeekly.columnDefs.push({
						name				: WeeklyHeadName[i],
						enableColumnMenu	: false,
						width				: '70',
						displayName			: '',
						headerCellTemplate	: TotalTemplate[i],
						cellTemplate		: '',
						headerCellClass		: '',
						cellClass			: 'middle'
					})
				}else{
					$scope.gridWeekly.columnDefs.push({
						name                :  WeeklyHeadName[i],
						enableColumnMenu    :  false,
						width               :  '50',
						displayName         :  '',
						headerCellTemplate  :  TotalheaderDiv[i],
						cellTemplate        :  '',
						headerCellClass     :  '',
						cellClass           :  'middle' 
					})
				}
			}
			$scope.gridWeekly.data = $scope.weeklyData.weekData;
			window.onresize = function(){};
		}
  	}
	$scope.judgeHouse = function(){
		$scope.weeklyData.selectHouseId = JSON.parse($scope.weeklyData.selectHouse).HouseId;
		$scope.inquire();
	}
	



	setLandscape(false,true);
	//表格初始化
	$scope.gridWeekly = {};
	$scope.gridWeekly.columnDefs = [];
	$scope.gridTotal  = {};
	$scope.gridTotal.columnDefs = [];
	$scope.judgeHouse();




})

// 日报统计
.controller("dailyStatisticalCtrl",function($scope, $state, $http, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//数据源
	$scope.DaystatisticalData = {
		"HouseBreedId"      :  ""  ,  //int型，栋舍饲养批次Id
		"HouseId"           :  ""  ,  //int型，栋舍Id
		"CurDayAge"         :  ""  ,  //int型，当前日龄
		"CurDate"           :  ""  ,  //varchar型，当前日期
		"cur_amount"        :  ""  ,  //int型，存栏数量
		"original_amount"   :  ""  ,  //int型，入雏数量
		"acc_feed"          :  ""  ,  //varchar型，累计采食量

		"std_acc_cd_rate"   :  ""  ,  //varchar型，警戒累计死淘率
		"std_acc_cd"        :  ""  ,  //int型，警戒累计死淘数量

		"atu_acc_cd_rate"   :  ""  ,  //varchar型，累计死淘率
		"atu_acc_cd"        :  ""  ,  //int型，累计死淘数量






		"dataInput":[{
			"day_age"          :  ""  ,  //int型，对应日龄
			"daily_feed"       :  ""  ,  //varchar型，日采食量
			"daily_weight"     :  ""  ,  //varchar型，日均重
			"atu_cd_num"       :  ""  ,  //日死淘数
			"atu_cd_rate"      :  ""  ,  //varchar型，日死淘率
			"feed_conversion"  :  ""  ,  //varchar型，料肉比
		}]
		
	}

	/*//默认设定默认栋舍
	$scope.takeHouseId;
	for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
		if ($scope.sparraw_user_temp.userinfo.houses[i].HouseId == $stateParams.receiveHouseId) {
			$scope.takeHouseId = $scope.sparraw_user_temp.userinfo.houses[i];
		}; 
	};*/


	//默认设定默认栋舍
	$scope.placeDatas = {
            'selectHouse':  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]), 
            'placeNum'   :  '', 
            'placeDate'  :  '' 
	};

	//查询日报信息
    $scope.judgeHouse = function(){
    	//先获取栋舍信息
		var housebreedid;
		var houseid;
		if (typeof($scope.placeDatas.selectHouse) == "object" && Object.prototype.toString.call($scope.placeDatas.selectHouse).toLowerCase() == "[object object]" && !$scope.placeDatas.selectHouse.length) {
    		housebreedid  =  $scope.placeDatas.selectHouse.HouseBreedBatchId  ,
			houseid       =  $scope.placeDatas.selectHouse.HouseId
    	}else{
    		tempData  =  JSON.parse($scope.placeDatas.selectHouse)  ,
			housebreedid  =  tempData.HouseBreedBatchId             ,
			houseid       =  tempData.HouseId
    	};
    	$scope.DaystatisticalData.HouseBreedId  =  housebreedid  ;
    	$scope.DaystatisticalData.HouseId       =  houseid       ;

    	
    	//查询日 报 统 计信息
    	if ($scope.DaystatisticalData.HouseBreedId == 0) {
    		$scope.DaystatisticalData.HouseBreedId     =  ""   ;
    		$scope.DaystatisticalData.CurDayAge        =  "-"  ;
    		$scope.DaystatisticalData.CurDate          =  Sparraw.getNowFormatDate()  ;//当前日期
    		$scope.DaystatisticalData.cur_amount       =  "-"  ;
    		$scope.DaystatisticalData.original_amount  =  "-"  ;
    		$scope.DaystatisticalData.acc_feed         =  "-"  ;
    		$scope.DaystatisticalData.std_acc_cd_rate  =  "-"  ;
    		$scope.DaystatisticalData.std_acc_cd       =  "-"  ;
    		$scope.DaystatisticalData.atu_acc_cd_rate  =  "-"  ;
    		$scope.DaystatisticalData.atu_acc_cd       =  "-"  ;
    		$scope.DaystatisticalData.dataInput        =  []   ;
    		Sparraw.myNotice("暂无统计信息，请先入雏");
    	}else{
    		var params = {
				//"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"HouseId"       :  $scope.DaystatisticalData.HouseId
			};
			
		Sparraw.ajaxPost('dataInput/DRShow.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
					$scope.DaystatisticalData = data.ResponseDetail;
					$scope.DaystatisticalData.dataInput = data.ResponseDetail.dataInput;
					$scope.DaystatisticalData.CurDate   =  Sparraw.getNowFormatDate();//当前日期
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
    	};
		
    }
    //自动查询
    $scope.judgeHouse();


    /*$scope.judgeDayAge = function(obj){
    	if (obj == $scope.DaystatisticalData.CurDayAge) {
    		return "{background:'#00CD66'}";
    	}else{
    		return "{background:'#fff'}";
    	};
    }
*/	
})

//生产日报
.controller("productionDailyCtrl",function($scope, $state, $http, $ionicLoading, $stateParams, $ionicPopup,$ionicSideMenuDelegate, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


    $scope.goDailyTable = function(){
		if($stateParams.fromPage == "dailyReport"){
			$state.go("dailyReport");
		}else{// dailyReport font-weight: bold;
			$state.go("dailyTable");
		}
    	
    }

    $scope.prodDayData = {
		"FarmBreedId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId           ,
		"selectHouse"    :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0])  ,
		"selectHouseId"  :  ""     ,
		"selectWeek"     :  "0"    ,
		"WeekAgeBegin"   :  ""     ,
		"WeekAgeEnd"     :  ""     ,
		"DateInfos"      :  ""     ,//varchar型，日期信息
		"PlaceNum"       :  ""     ,//int型，入舍数量
        "CurGrowthAge"   :  ""     ,//当前生长日龄
        "CurLayerAge"    :  ""     ,//int型，当前产蛋日龄
        "PlaceDate"      :  ""     ,//varchar型，入舍日
		"weekData"       :  ""     ,
		"original_amount":  ""     ,
		"cur_amount"     :  ""     ,
		"survRate"       :  ""
    }	
    $scope.prodDayData.selectHouseId = JSON.parse($scope.prodDayData.selectHouse).HouseId;

  	$scope.GetTable = function(){
		var showTableData = {
			'rowHeight' : ""    ,//内容高度
			'header'    : []    ,
			'TableData' : []    ,
			'firstFixed': true
		};
		var header = [];

		var headName = ['生长<br>日龄',
		                '',
						'鸡存<br>栏数',
						'',
						'',
						'',
						'',
						'',
						'饲料<br>公斤',
						'饮水<br>立方',
						'',
						'体重<br>公斤'];
		var otherHeadName = ['',
							 '死淘<br>数',
							 '',
							 '',
							 '',
							 '',
							 '',
							 '破损枚',
							 '',
							 '',
							 '水料比',
							 ''];
		var headerDiv = [];
		var otherHeaderDiv = [];


		var TableHeadName = ["growth_age"       ,
							 "culling_all"      ,
							 "curAmount"        ,
							 "curLayNum"        ,
							 "curLayRate"       ,
							 "curLaySumWeight"  ,
							 "curLayAvgWeight"  ,
							 "curBrokenNum"     ,
							 "daily_feed"       ,
							 "daily_water"      ,
							 "rOfWM"            ,
							 "chickenWeight"];



		for (var i = 0; i < headName.length; i++) {
			headerDiv.push("<div style='color:black;text-align:center;position:relative; left:0px; top:0px;height:40px;'><p>" + headName[i] + "</p></div>");
		}

		for (var i = 0; i < otherHeadName.length; i++) {
			if(i == 1){
				otherHeaderDiv.push("<div style='color:black;text-align:center;position:relative; left:0px; top:0px;height:40px;'><p>" + otherHeadName[i] + "</p></div>");
			}else {
				otherHeaderDiv.push("<div style='color:black;text-align:center;position:relative; left:0px; top:8px;height:40px;'><p>" + otherHeadName[i] + "</p></div>");
			}
		}
		var templateTabel = ['','','','<div style="width:120px;height:40px;"><div style="position:relative; left:0px; top:0px; width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "产蛋" + '</p></div><div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "枚" + '</p></div><div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "%" + '</p></div></div></div>','','<div style="width:120px;height:40px;"><div style="width:120px;height:20px; background:rgba(251, 251, 251, 1)"><p style="text-align:center;">' + "蛋重量" + '</p></div><div style="width:60px;height:20px;"><div style="width:60px;height:20px;"><p style="text-align:center;">' + "公斤" + '</p></div><div style="width:60px;height:20px; position:relative;left:60px; top:-20px;"><p style="text-align:center;"> ' + "克/枚" + '</p></div></div></div>',];
		for (var i = 0; i < TableHeadName.length; i++) {
			if(i == 2 || i == 11){
				header.push({
					'name'                :  TableHeadName[i],
					'width'               :  '60',
					'displayName'         :  '',
					'headerCellTemplate'  :  headerDiv[i],
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'cellClass'           :  'middle'
				})
			}else if (i == 0 || i == 8 || i == 9) {
				if(i == 0) {
					header.push({
						'name': TableHeadName[i],
						'width': '40',
						'displayName': '',
						'headerCellTemplate': headerDiv[i],
						'cellTemplate': '',
						'headerCellClass': '',
						'cellClass': 'middle'
					})
				}else{
					header.push({
						'name': TableHeadName[i],
						'width': '80',
						'displayName': '',
						'headerCellTemplate': headerDiv[i],
						'cellTemplate': '',
						'headerCellClass': '',
						'cellClass': 'middle'
					})
				}
			}else{
				if (i == 3 || i == 4 || i == 5 || i == 6) {
					if (i == 3 || i == 5) {
						header.push({
							'name'                :  TableHeadName[i],
							'width'               :  '60',
							'displayName'         :  '',
							'headerCellTemplate'  :  templateTabel[i],
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}else{
						header.push({
							'name'                :  TableHeadName[i],
							'width'               :  '60',
							'displayName'         :  '',
							'headerCellTemplate'  :  '',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}
				}else{
					if(i == 1){
						header.push({
						'name'                :  TableHeadName[i],
						'width'               :  '40',
						'displayName'         :  '',
						'headerCellTemplate'  :  otherHeaderDiv[i],
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'cellClass'           :  'middle'})
					}else {
						header.push({
							'name': TableHeadName[i],
							'width': '60',
							'displayName': '',
							'headerCellTemplate': otherHeaderDiv[i],
							'cellTemplate': '',
							'headerCellClass': '',
							'cellClass': 'middle'
						})
					}
				}
			}
		}

		showTableData.header = header;
		showTableData.TableData = $scope.prodDayData.weekData;
		GetShowTable(showTableData,$scope);
		window.onresize = function(){};
  	}
  	//$scope.GetTable();


  	$scope.inquire = function(){

  		$scope.prodDayData.WeekAgeBegin = $scope.prodDayData.selectWeek;
  		$scope.prodDayData.WeekAgeEnd = $scope.prodDayData.selectWeek;
    	var params = {
    		'IsNeedDelay':'Y',
   			"FarmBreedId":$scope.prodDayData.FarmBreedId,
		  	"HouseId":$scope.prodDayData.selectHouseId,
		   	"WeekAgeBegin":$scope.prodDayData.WeekAgeBegin,
		   	"WeekAgeEnd":$scope.prodDayData.WeekAgeEnd
		};
		console.log(params);
		Sparraw.ajaxPost('layer_dataInput/DailyReport.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			$scope.prodDayData.DateInfos    = data.ResponseDetail.DateInfos    ;
	   			$scope.prodDayData.CurGrowthAge = data.ResponseDetail.CurGrowthAge ;
	   			$scope.prodDayData.PlaceDate    = data.ResponseDetail.PlaceDate    ;
	   			$scope.prodDayData.PlaceNum     = data.ResponseDetail.PlaceNum     ;
	   			$scope.prodDayData.weekData     = data.ResponseDetail.weekData     ;
				$scope.prodDayData.PlaceNum     = data.ResponseDetail.PlaceNum     ;
				$scope.prodDayData.CurGrowthAge = data.ResponseDetail.CurGrowthAge ;
				$scope.prodDayData.CurLayerAge  = data.ResponseDetail.CurLayerAge  ;
				$scope.prodDayData.PlaceDate    = data.ResponseDetail.PlaceDate    ;
				$scope.prodDayData.CurLayerAge  = data.ResponseDetail.CurLayerAge  ;
				var oDate  = new Date();
				var oMonth = ("0" + (oDate.getMonth() + 1)).slice(-2);
				var oDay   = ("0" + (oDate.getDate())).slice(-2);
				var NowDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;
				$scope.prodDayData.DateInfos = NowDate;
	   			if (data.ResponseDetail.weekData.length == 0) {
	   				Sparraw.myNotice("所选数据暂无信息。");
	   			}else{

	   			}


	   			$scope.prodDayData.original_amount = $scope.prodDayData.PlaceNum;
	   			$scope.prodDayData.cur_amount = $scope.prodDayData.weekData[$scope.prodDayData.weekData.length-1].curAmount;


	   			if ($scope.prodDayData.original_amount == $scope.prodDayData.cur_amount) {
					$scope.prodDayData.survRate = 100;
				}else{
					$scope.prodDayData.survRate = parseFloat($scope.prodDayData.original_amount / $scope.prodDayData.cur_amount).toFixed(2);
					$scope.prodDayData.survRate = 100 - $scope.prodDayData.survRate;
					if (!app_IsNum($scope.prodDayData.survRate) || !isFinite($scope.prodDayData.survRate)) {
						$scope.prodDayData.survRate = 0;
					}else{

					}
				}



	   			$scope.GetTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};			
		});
    }

  	$scope.judgeWeek = function(){
  		console.log($scope.prodDayData.selectWeek);
  		$scope.inquire();
  	}

  	$scope.judgeHouse = function(){
  		console.log($scope.prodDayData.selectHouseId);
  		$scope.inquire();
  	}
  	

  	setLandscape(false,true);
  	$scope.gridOptions = {};
	$scope.gridOptions.columnDefs = [];

  	$scope.GetTable();
	$scope.judgeHouse();



})




// 入雏管理
.controller("docPlaceCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//默认设定默认栋舍
	console.log(persistentData.dataEntryReceiveHouse);
	$scope.takeHouse = persistentData.dataEntryReceiveHouse;
	$scope.placeDatas = {
            'selectHouse':  $scope.takeHouse.HouseId                     ,  
            'selectHouseObj': {}                                         ,//包含栋舍状态
            'placeNum'   :  ''                                           , 
            'placeDate'  :  ''
  		};



  		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
  			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseId === $scope.takeHouse.HouseId) {
  				$scope.placeDatas.selectHouseObj = $scope.sparraw_user_temp.userinfo.houses[i];
  			};
  		};

  		//console.log($scope.sparraw_user_temp.userinfo.houses);
	//查询入雏信息
    $scope.judgeHouse = function(){
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "00") {
    		Sparraw.myNotice("该栋舍尚未入雏，您可以在此添加信息。");
    		$scope.placeDatas.placeNum   = ""  ,
			$scope.placeDatas.placeDate  = ""
    	}

    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
    		var params = {
				"HouseBreedId"  :  $scope.takeHouse.HouseBreedBatchId  ,
				"HouseId"       :  $scope.takeHouse.HouseId
			};

			Sparraw.ajaxPost('dataInput/queryDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					$scope.placeDatas.placeNum   = data.ResponseDetail.PlaceNum  ,
					$scope.placeDatas.placeDate  = data.ResponseDetail.PlaceDate
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
		
    		});
    	}
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "02") {
    		Sparraw.myNotice("该批次已出栏，如需添加入雏信息，请先结算批次");
    		$scope.placeDatas.placeNum   = ""  ,
			$scope.placeDatas.placeDate  = ""
    	}
    	
    }
	$scope.judgeHouse();

	//创建预计出栏时间
	var OverDate;
	//日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();

	$scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '关闭', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2012, 12, 31), //Optional
            to: new Date(2020, 12, 31), //Optional
            callback: function (val) { //Optional
            	datePickerCallbackPopup(val);
            }
    	};
	var datePickerCallbackPopup = function (val) {
            if (typeof(val) === 'undefined') {
                console.log('未选择日期');
            } else {
                $scope.datepickerObjectPopup.inputDate = val;
                $scope.datePopup = val;
                console.log('选择的日期是 : ', val)

                //转为字符串并且删除“日”字符串，修改“年”“月”为“-”
                var selectDate = val.toLocaleDateString().replace(/(日)/g,"");
                var showSelectDate = selectDate.replace(/\/|(年)|(月)/g,"-");
                $scope.placeDatas.placeDate = showSelectDate.substring(0,10);
            }
    };


    if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
		//app_alert("已入雏，日期无法修改");
		$scope.datepickerObjectPopup=null;
	}else{

	};

	$scope.judgeCanEditor = function(){
		if ($scope.datepickerObjectPopup === null) {
			return "{'color':'rgba(186, 186, 186, 1)'}";
		};
	};

    /*$scope.testFun = function(){
    	if (true) {
    			//创建预计出栏时间
				var OverDate;
				//日期选择器
				$scope.dateModel = new Date("08-14-2015");
			    $scope.datePopup = new Date("08-14-2015");
			    var disabledDates = [
			        new Date(1437719836326),
			        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
			        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
			        new Date("08-14-2015"), //Short format
			        new Date(1439676000000) //UNIX format
			    ];
			    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
			    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
			    $scope.datepickerObject = {};
			    $scope.datepickerObject.inputDate = new Date();

				$scope.datepickerObjectPopup = {
			            titleLabel: '选择日期', //Optional
			            todayLabel: '今天', //Optional
			            closeLabel: '关闭', //Optional
			            setLabel: '选择', //Optional
			            errorMsgLabel : '请选择时间.', //Optional
			            setButtonType : 'button-assertive', //Optional
			            modalHeaderColor:'bar-positive', //Optional
			            modalFooterColor:'bar-positive', //Optional
			            templateType:'popup', //Optional
			            inputDate: $scope.datepickerObject.inputDate, //Optional
			            //mondayFirst: true, //Optional
			            sundayFirst: true, //Optional
			            disabledDates:disabledDates, //Optional
			            monthList:monthList, //Optional
			            weekDaysList: weekDaysList,
			            from: new Date(2014, 5, 1), //Optional
			            to: new Date(2016, 7, 1), //Optional
			            callback: function (val) { //Optional
			            	datePickerCallbackPopup(val);
			            }
			    	};
			    	var datePickerCallbackPopup = function (val) {
				            if (typeof(val) === 'undefined') {
				                console.log('未选择日期');
				            } else {
				                $scope.datepickerObjectPopup.inputDate = val;
				                $scope.datePopup = val;
				                console.log('选择的日期是 : ', val)

				                //转为字符串并且删除“日”字符串，修改“年”“月”为“-”
				                var selectDate = val.toLocaleDateString().replace(/(日)/g,"");
				                var showSelectDate = selectDate.replace(/\/|(年)|(月)/g,"-");
				                $scope.placeDatas.placeDate = showSelectDate.substring(0,10);
				            }
				    };
	    	}else{
	    		$scope.datepickerObjectPopup = {};
	    		console.log("0-0--0-0-0-0-0-0-0-");
	    	}
	}*/


	

    
	$scope.saveDocPlace = function(){
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "00") {
			//新增
			var params = {
				"HouseId"    :  $scope.takeHouse.HouseId               ,
				"PlaceNum"   :  $scope.placeDatas.placeNum             ,
				"PlaceDate"  :  $scope.placeDatas.placeDate
			};
			Sparraw.ajaxPost('dataInput/saveDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					persistentData.dataEntryReceiveHouse.HouseBreedStatus = "01";
					//重新获取服务器最新数据
					app_alert("入雏成功!");
    				Sparraw.getLatestData($state,"dailyReport");
				}else{
					app_alert(data.ResponseDetail.Exception);
				};
			});
		}

		if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
			var params = {
				"HouseBreedId"  :  $scope.takeHouse.HouseBreedBatchId  ,
				"HouseId"       :  $scope.takeHouse.HouseId            ,
				"PlaceNum"      :  $scope.placeDatas.placeNum                                   ,
				"PlaceDate"     :  $scope.placeDatas.placeDate
			};
			Sparraw.ajaxPost('dataInput/ModifyDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					Sparraw.myNotice("修改成功!");
					$state.go("dailyReport");
				}else{
					app_alert(data.ResponseDetail.Exception);
				};
			});
		};
	}




    $scope.save = function(){
		app_confirm('请确认入雏日期:' + $scope.placeDatas.placeDate +',入雏之后,将无法更改入雏日期。','提示',null,function(buttonIndex){
	                   if(buttonIndex == 2){
	                        $scope.saveDocPlace();
	                   }
	              }); 
    }











		
})



// 环控管理表
.controller("envMonitoringTableCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
})

// 环境监测
.controller("envMonitoringCtrl",function($scope, $state,$ionicLoading, $http, $ionicScrollDelegate, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();

			setTimeout(
				function (){
					$scope.setData();
					$scope.queryDatatt();
				}
			,1000);
		}
	,1000);

	



    $scope.setData = function(){
    	//数据源
	    $scope.envMonitoringData = {
	    	"operation"   :  "realTimeData",
	    	"celsius"     :  "℃"           ,
	    	"MonitorData" :  [{
	    		"houseName"         :  "-"  , //-栋舍名称
				"dayAge"            :  "-"  , //-日龄
				"out_temp"          :  "-"  , //-室外温度
				"tempLeft1"         :  "-"  , //-前区温度
				"tempLeft2"         :  "-"  ,
				"tempMiddle1"       :  "-"  , //-中区温度
				"tempMiddle2"       :  "-"  ,
				"tempRight1"        :  "-"  , //-后区温度
				"tempRight2"	    :  "-"  ,
				"house_id"           :  ""   ,

				"tar_temp"          :  "-"  , //-目标温度
				"avg_temp"          :  "-"  , //-平均温度
				"H_temp"            :  "-"  , //-高报温度
				"L_temp"            :  "-"  , //-低报温度
				"point_temp"        :  "-"  , //-点温差
				"humi"              :  "-"  , //-湿度
				"data_time"         :  "-"  , //数据时间
				"temp_in1_alarm"    :  "N"  , //-前区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in2_alarm"    :  "N"  , //-中区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in3_alarm"    :  "N"  , //-后区温度：高报警（H）,正常（N）,低报警（L）
				"temp_avg_alarm"    :  "N"  , //-平均温度：高报警（H）,正常（N）,低报警（L)
				"point_temp_alarm"  :  "0"  , //-1-点温差报警；0-正常
				"power_status"      :  "-"  ,  
				"power_status_alarm" :  "0"    //1-断电报警；0-不报
	    	}]
	    }
	    $scope.transferHouseId = "";//跨页面传值使用
	    $scope.area = {
	    	"area1"  :  "Outdoor"  ,  //室外
	    	"area2"  :  "Front"    ,  //前区
	    	"area3"  :  "Middle"   ,  //中区
	    	"area4"  :  "Behind"   ,  //后区
	    	"area5"  :  "all"         //所有区域
	    }//判断从哪里跳转至报表界面
    }


    //判断是否是从环控监视进入报 警 设 置
    $scope.goAlarmSettings = function(){
		$state.go("alarmSettings");
    }


	$scope.queryDatatt = function(){
		var params = {
			"operation"   :  "realTimeData"
		};
		Sparraw.ajaxPost('envCtrl/monitoring.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {


				console.log(data);
				/*console.log(data);
				console.log($scope.envMonitoringData.MonitorData);
				return*/
				$scope.envMonitoringData.MonitorData = data.ResponseDetail.MonitorData;

				
				if ($scope.envMonitoringData.MonitorData == "" || !data.ResponseDetail.MonitorData) {
					$scope.dataNullText = true;
				}else{
					$scope.dataNullText = false;
				}

			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			$scope.$broadcast('scroll.refreshComplete');
		});
	};

	$scope.refreshFun = function(){
		$scope.queryDatatt();
	}

	$scope.goHome = function(){
		//Sparraw.getInfoStatus($ionicPopup,$state,"envMonitoringTable");
		$state.go("home");
	}

	


	//控制区域颜色
	$scope.judgeEnter = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{background:'red'}";
		}else{
			return "{background:'#a5d5a9'}";
		};
	}

	//字体颜色
	$scope.labelColor = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{color:'#fff'}";
		}else{
			return "{color:'#5c5c5c'}";
		};
	}




    //判断温度状态
    //前区温度
    $scope.frontStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //中区温度
    $scope.middleStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //后区温度
    $scope.afterStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //平均温度
    $scope.retStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#f4f4f4'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //判断温差点
    $scope.temperatureStyle = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }
    //判断断电情况
    $scope.judgeCurrent = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }


    $scope.goReportTempHumi = function(){
    	//pointDevelop();
		
		$state.go("reportTempHumi");
    }


})

//  温度曲线图
.controller("tempChartCtrl",function($scope, $state, $http, $ionicLoading,$ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	


	$scope.goEnvMonitoring = function(){
		if ($stateParams.area == "") {//判断哪个入口进入，回到哪个页面
			$state.go("envMonitoring");
		}else{
			$state.go("envMonitoring");
		};
	}

	if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}

			

	$scope.tempChartData = {
		
		"batchTable"        :  ""                           ,//批次表
		"selectedHouseId"   :  ""                           ,//选中的栋舍id
		"selectedBatch"     :  ""                           ,//选中批次的key
		"selectedBatchVal"  :  ""                           ,//选中批次的val
		"NavTitle"          :  ""                           ,

		"chartType"         :  ["01","02","03"]             ,
		"chartTitle"        :  ""                           ,//数据展示类型"01"-日龄"02"-小时"03"-分钟
	    "ReqFlag"           :  ""                           ,//varchar型,"Y"-指定参数；"N"-没有指定参数
	    "chartDataTime"     :  null                         ,//表图数据时间
	    "chartDataisNull"   :  0                            ,//判断表图是否已获取数据0-未获取，1-已获取


	    "xData"             :  []                           ,
	    "yData"             :  []                           ,
	    "yName"             :  []                           ,
	    "yColor"            :  []                           ,
	    "hiddenPara"        :  []                           , //隐藏哪些数据
	    "hiddenLine"        :  [["前区温度1",false,"前区温度2",false,"中区温度",false,"后区温度1",false,"后区温度2",false],//室外数据
								["室外温度",false,"中区温度",false,"后区温度1",false,"后区温度2",false]                  ,//前区数据
								["前区温度1",false,"前区温度2",false,"室外温度",false,"后区温度1",false,"后区温度2",false],//中区数据
								["室外温度",false,"前区温度1",false,"前区温度2",false,"中区温度",false]                  ,//后区数据
								["室外温度",false]                                                                    //展示除了室外温度，其他所有数据
								]                           , 
		"DataDate"          :  ""                           , //服务器返回表图数据的时间
		"selectedChartTime" :  ""                           , //用户选择的时间
		"selectUnit"        :  "℃"                          , //y轴的计量单位








		"selectHouse"       :  $stateParams.receiveHouseId  ,//从环控监视进来的栋舍id
		"selectHouseArea"   :  $stateParams.area            ,//从环控监视进来的栋舍区域(Outdoor室外、Front前区、Middle中区、Behind后区)
		
		"leftBtnName"       :  "日龄"                        ,//左边按钮名
		"rightBtnName"      :  "分钟"

	}




	
	
	//判断从哪个入口进入该页面的
	switch($scope.tempChartData.selectHouseArea){
					      case 'Outdoor' :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[0];//隐藏哪些数据
					      break;
					      case 'Front'   :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[1];
					      break;
					      case 'Middle'  :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[2];
					      break;
					      case 'Behind'  :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[3];
					      break;
					      default        :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[4];
					      break;
					    }
	
	
	//获取批次信息
	/*var params = {
		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id
	};
	Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
		if (data.ResponseDetail.Result == "Success") {
			$scope.tempChartData.batchTable = data.ResponseDetail.FarmBreedIdArray;
			//获取key
			for(var key in $scope.tempChartData.batchTable){
			    $scope.tempChartData.selectedBatch = key;
			}
			//获取value  
			 for(var item in $scope.tempChartData.batchTable){  
		        if(item==key){  
		            $scope.tempChartData.selectedBatchVal = $scope.tempChartData.batchTable[item];
		        }  
		    }  
		    $scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
			$scope.tempChartData.chartDataTime = null;

			
			console.log("首次显示曲线图的地方");

			if ($scope.tempChartData.selectHouse != "") {
				$scope.tempChartData.selectedHouseId = $scope.tempChartData.selectHouse;
			}else{

			};

			$scope.DataShow(
						$scope.tempChartData.selectedBatch    ,
						$scope.tempChartData.selectedHouseId  ,
						$scope.tempChartData.chartTitle       ,
						$scope.tempChartData.ReqFlag          ,
						$scope.tempChartData.chartDataTime    ,
						function () {
							if ($scope.tempChartData.chartDataisNull == 0) {
								$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
							}else{

							};
						}
					);
		}else{
			// console.log('898989898');
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
		
	});*/







	





	$scope.switchHouse = function(){
		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
		$scope.DataShow(
					$scope.tempChartData.selectedBatch    ,
					$scope.tempChartData.selectedHouseId  ,
					$scope.tempChartData.chartTitle       , //默认小时数据
					$scope.tempChartData.ReqFlag          ,
					$scope.tempChartData.chartDataTime
		);
		$scope.tempChartData.leftBtnName  = "日龄";
		$scope.tempChartData.rightBtnName = "分钟";
		document.getElementById('rightBtn').style.background = "#33CD5F";
		document.getElementById('leftBtn').style.background = "#33CD5F";
	}

	$scope.switchBatch = function(){
		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
		$scope.DataShow(
			$scope.tempChartData.selectedBatch    ,
			$scope.tempChartData.selectedHouseId  ,
			$scope.tempChartData.chartTitle       , //默认小时数据
			$scope.tempChartData.ReqFlag          ,
			$scope.tempChartData.chartDataTime    
			);
		$scope.tempChartData.leftBtnName  = "日龄";
		$scope.tempChartData.rightBtnName = "分钟";
		document.getElementById('rightBtn').style.background = "#33CD5F";
		document.getElementById('leftBtn').style.background = "#33CD5F";
	}

	//农场批次id//栋舍id//曲线图数据类型//是否选中参数//选择的参数时间//判断数据是否获取成功
	$scope.DataShow = function(BatchKey,HouseId,DataTypeNum,ReqFlag,DataRange,judgeDataFun){

		var oDate  = new Date();
		var oMonth = ("0" + (oDate.getMonth() + 1)).slice(-2);
		var oDay   = ("0" + (oDate.getDate() + 1)).slice(-2);
		var NowDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;
		if (DataRange >= NowDate) {//判断用户所选时间是否大于当日，如果大于当日直接取最新数据
			DataRange = null;
		}else{

		};



		//获取图表数据
		var params = {
			'IsNeedDelay':'Y',
			"FarmBreedId"  :  BatchKey     ,  //农场批次id
			"HouseId"      :  HouseId      ,  //栋舍id
			"DataType"     :  DataTypeNum  ,  //曲线图数据类型 01-日龄曲线；02-小时曲线；03-分钟曲线。
			"ReqFlag"      :  ReqFlag      ,  //varchar型,"Y"-指定参数；"N"-没有指定参数
			"DataRange"    :  DataRange       //选择的时间
		};

		Sparraw.ajaxPost('layer_report/queryTempCurve.action', params, function(data){

					for (var i = 0; i < data.ResponseDetail.TempDatas.length; i++) {
						$scope.tempChartData.yName[i] =  data.ResponseDetail.TempDatas[i].TempAreaName;
						$scope.tempChartData.yData[i] =  data.ResponseDetail.TempDatas[i].TempCurve;
					}



					$scope.tempChartData.xData       =  data.ResponseDetail.xAxis;


					$scope.tempChartData.DataDate    =  data.ResponseDetail.DataDate;
					$scope.tempChartData.data_age    =  data.ResponseDetail.data_age;
					/*$scope.tempChartData.yColor      =  ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];				

					if (data.ResponseDetail.DataDate != "null") {
						console.log(data.ResponseDetail);
						if (data.ResponseDetail.data_age != "null") {
							
							//$scope.tempChartData.NavTitle = data.ResponseDetail.DataDate+data.ResponseDetail.data_age;
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substr(4);
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substring(0,data.ResponseDetail.data_age.length-1);
							$scope.tempChartData.NavTitle =  '(日龄:' + data.ResponseDetail.data_age + ')';
							console.log(data.ResponseDetail.DataDate);//这是数据的日期
							console.log(data.ResponseDetail.data_age);//这是数据的日龄
							
						}else{
							$scope.tempChartData.NavTitle = "";
						};
					}else{
						$scope.tempChartData.NavTitle = "";
					};
					$scope.tempChartData.selectUnit = "℃";*/



					$scope.tempChartData.yColor      =  ['#EE82EE','#FFA500','#CC9933','#CCCC00','#993333','#663366',  '#DC143C','#00FFFF','#00EE76','#D1EEEE'];	
														//前1        、前2、   中区、       后1、    后2、      室外、         高报       低报      目标        湿度
					

					if (data.ResponseDetail.DataDate != "null") {
						if (data.ResponseDetail.data_age != "null") {
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substr(4);
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substring(0,data.ResponseDetail.data_age.length-1);
							$scope.tempChartData.NavTitle =  '(日龄:' + data.ResponseDetail.data_age + ')';
						}else{
							$scope.tempChartData.NavTitle = "";
						};
					}else{
						$scope.tempChartData.NavTitle = "";
					};
					$scope.tempChartData.selectUnit = "℃";


					var allData = [];

					for (var i = 0; i < $scope.tempChartData.yData.length-1; i++) {
						for (var j = 0; j < $scope.tempChartData.yData[i].length; j++) {
							allData.push($scope.tempChartData.yData[i][j]);
						}
					}
					//判断是否要设置最大最小值
					var yLeftRange = [];
					if (Math.max.apply(null, allData) > 40 || Math.min.apply(null, allData) < 15) {
						yLeftRange = [15,40];
					}else{
						yLeftRange = undefined;
					}
					var yRightRange = undefined;

					var yRightShow = true;
					//判断是否要右边的数据
					if (DataTypeNum == "03") {
						yRightShow = true;
					}else{
						yRightShow = false;
						//删除湿度数据
						if ($scope.tempChartData.yData.length == 10) {
							$scope.tempChartData.yData.pop();
							$scope.tempChartData.yName.pop();
						}
					}
					
					getLineChart($scope.tempChartData.xData,
										 $scope.tempChartData.yData,
										 $scope.tempChartData.yName,
										 $scope.tempChartData.yColor,
										 $scope.tempChartData.hiddenPara,
										 $scope.tempChartData.selectUnit,
										 '',
										 function (params) {
											$scope.tempChartData.judgeIsEmpty = params[0].data
											for (var i = 0; i < params.length; i++) {
												if (params[i].data == undefined) {
													params[i].data = "-";
												}else{

												};
											};
					                        $scope.tempChartData.selectedChartTime = params[0].name;//将x轴的字赋到判断向上向下的选择中
					                    	var res = '时间' + ' : ' + params[0].name;//x轴的字
											var tempArray = [];
											
					                    	for (var i = 0; i < params.length; i++) {
					                            tempArray[i] = params[i];
					                    	};
					                        



					                    	//得到点温差
					                        var tempDiffArr = [];
											var tempDiffMax = "";
											var tempDiffMin = "";
											var tempDiff = "";
					                    	//判断从哪个入口进入该页面的
					                    	console.log($scope.tempChartData.selectHouseArea);
											switch($scope.tempChartData.selectHouseArea){
															      case 'Outdoor' ://室外
															      		
															      break;
															      case 'Front'   ://前区
															      		//得到点温差
																		for (var i = 0; i < 2; i++) {
																			tempDiffArr.push(tempArray[i].data);
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															      case 'Middle'  ://中区
															      		
															      break;
															      case 'Behind'  ://后区
															      		//得到点温差
																		for (var i = 0; i < 5; i++) {
																			if (i > 2 && i < 5 ) {
																				tempDiffArr.push(tempArray[i].data);
																			}
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															      default        :
															      		//得到点温差
																		for (var i = 0; i < 5; i++) {
																			tempDiffArr.push(tempArray[i].data);
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		//将新添加的温差点放在第一位，因为放在最后一位会出现选择日期错误的问题。
												                        tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															    }



											
											
											console.log(tempArray);

					                        for (var i = 0; i < tempArray.length; i++) {
					                        	if (i % 2 == 0) {
					                        		$scope.tempChartData.lineDataTime = tempArray[i].name;
					                        		res += '<br/>' + tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
					                        	}else{
					                        		$scope.tempChartData.lineDataTime = tempArray[i].name;
					                        		res +=  tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
					                        	}
					                        };

					                        

					                        return res;					                        
					                    },yLeftRange,yRightRange,yRightShow);
					//每次刷新图数据后自动清空是否点击
					$scope.tempChartData.ReqFlag = "N";
					$scope.tempChartData.chartDataTime = null;
					$scope.tempChartData.selectedChartTime = "";
					//成功
					$scope.tempChartData.chartDataisNull = 1;
						judgeDataFun&&judgeDataFun();
					},function(data){
						//失败
						$scope.tempChartData.chartDataisNull = 0;
						judgeDataFun&&judgeDataFun();
					});

	}




	$scope.inquire = function(){
		$scope.tempChartData.selectedBatch = $scope.sparraw_user_temp.farminfo.farmBreedBatchId;
		$scope.tempChartData.selectedHouseId = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId);
		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
		$scope.tempChartData.ReqFlag = "N";
		$scope.tempChartData.chartDataTime = null;
		$scope.tempChartData.selectedBatchVal = $scope.sparraw_user_temp.farminfo.farmBreedBatchCode;

		$scope.DataShow($scope.tempChartData.selectedBatch    ,
						$scope.tempChartData.selectedHouseId  ,
						$scope.tempChartData.chartTitle       ,
						$scope.tempChartData.ReqFlag          ,
						$scope.tempChartData.chartDataTime    );
	}
	
	
	//向上
	$scope.upMethods = function(){
		$scope.tempChartData.ReqFlag = "Y";
		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		Sparraw.myNotice("横轴已到最大粒度。");
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];//从小时切换成日龄数据
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从分钟切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
					      		$scope.tempChartData.chartDataTime = $scope.tempChartData.DataDate;
					      		console.log($scope.tempChartData.DataDate);
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];
												}else{

												};
											});
					      break;
					    }


		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		//最大值
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
								document.getElementById('leftBtn').style.background = "#ECECEC";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					      case '02'   :
					      		//从小时切换成日龄数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('leftBtn').style.background = "#33CD5F";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					      default     :
					      		//从分钟切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('leftBtn').style.background = "#33CD5F";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					    }

	}
	

	// "ReqFlag"           :  "N"                          ,//varchar型,"Y"-指定参数；"N"-没有指定参数
	// "chartDataisNull"   :  0                            ,//判断表图是否已获取数据0-未获取，1-已获取
	// "chartTitle"        :  ["01","02","03"]             ,//数据展示类型"01"-日龄"02"-小时"03"-分钟

	//向下
	$scope.downMethods = function(){

		if ($scope.tempChartData.selectedChartTime == "") {
			$scope.tempChartData.ReqFlag = "N";
			switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从日龄切换成小时数据
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];
												}else{

												};
					                    	});
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];//从小时切换成分钟数据
					      		$scope.tempChartData.chartDataTime = $scope.tempChartData.DataDate;
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		Sparraw.myNotice("横轴已到最小粒度。");
					      break;
					    }
		}else{

			$scope.tempChartData.ReqFlag = "Y";
			switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从日龄切换成小时数据
								var getYear = $scope.tempChartData.selectedBatchVal.substr(0, 2);
								getYear = '20' + getYear;
								var getMonthAndDay = $scope.tempChartData.lineDataTime.substr(0, 5);
								$scope.tempChartData.chartDataTime = getYear + '-' + getMonthAndDay;
								$scope.DataShow(
											$scope.tempChartData.selectedBatch      ,
											$scope.tempChartData.selectedHouseId    ,
											$scope.tempChartData.chartTitle         , 
											$scope.tempChartData.ReqFlag            ,
											$scope.tempChartData.chartDataTime      ,

											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];
												}else{

												};
					                    	});
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];//从小时切换成分钟数据


					      		var selectedTime = $scope.tempChartData.DataDate;
								selectedTime += " ";
								selectedTime += $scope.tempChartData.selectedChartTime;
								$scope.tempChartData.chartDataTime = selectedTime;

								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		Sparraw.myNotice("横轴已到最小粒度。");
					      break;
					    }
		};




		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		//从日龄切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "小时";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('rightBtn').style.background = "#33CD5F";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					      case '02'   :
								//从小时切换成分钟数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('rightBtn').style.background = "#33CD5F";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					      default     :
					      		//选择了最小值
					      		$scope.tempChartData.leftBtnName  = "小时";
								$scope.tempChartData.rightBtnName = "";
								document.getElementById('rightBtn').style.background = "#ECECEC";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					    }




	}

	setLandscape(true,true);
	$scope.inquire();

})
//  报警延迟处理
.controller("alarmLogDelayCtrl",function($scope, $state, $http,  crisisServiceFactory,  $ionicPopup, $stateParams, $ionicActionSheet, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    //数据源
    $scope.logDelayData = {
	    'alarmMessage':[],
	    'showStatus':""
    };
	//报警查询
	$scope.alarmQuery = function(){
		var params = {
			"FarmId":$scope.sparraw_user_temp.farminfo.id
		};
		Sparraw.ajaxPost('envCtrl/AlarmDealQuery_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				console.log(data.ResponseDetail.alarmMessage);
				$scope.logDelayData.alarmMessage = data.ResponseDetail.alarmMessage;

				for (var i = 0; i < $scope.logDelayData.alarmMessage.length; i++) {
					for (var j = 0; j < $scope.logDelayData.alarmMessage[i].CurAlarmData.length; j++) {
						if ($scope.logDelayData.alarmMessage[i].CurAlarmData[j].process_status == "01") {
							$scope.logDelayData.showStatus = "未处理";
							$scope.logDelayData.alarmMessage[i].CurAlarmData[j].showStatus = $scope.logDelayData.showStatus;
						}else{
							$scope.logDelayData.showStatus = "已处理";
							console.log($scope.logDelayData.alarmMessage[i].CurAlarmData[j]);
							$scope.logDelayData.alarmMessage[i].CurAlarmData[j].showStatus = $scope.logDelayData.showStatus;
						}
					}
					
				}
				$scope.DealFunc();
			}else{
				Sparraw.myNotice("暂无报警信息");
			};
		});
	}
	$scope.alarmQuery();
	//延迟函数
	$scope.DelayFunc = function(deferTime){
		if ($scope.sparraw_user_temp.Authority.MonitorDeal === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};


		var CurAlarmData = [];
		for (var i = 0; i < $scope.logDelayData.alarmMessage.length; i++) {
			for (var j = 0; j < $scope.logDelayData.alarmMessage[i].CurAlarmData.length; j++) {
				if ($scope.logDelayData.alarmMessage[i].CurAlarmData[j].process_status == '01') {
					CurAlarmData.push({
						"alarmID"     :  $scope.logDelayData.alarmMessage[i].CurAlarmData[j].alarmID    ,
		    			"alarmCode"   :  $scope.logDelayData.alarmMessage[i].CurAlarmData[j].alarmCode  ,
		    			"houseId"     :  $scope.logDelayData.alarmMessage[i].HouseId                    ,
		    			"delayTime"   :  deferTime
		    		});
				};
			}
		}
		if (CurAlarmData.length == 0 || !CurAlarmData) {
			return Sparraw.myNotice("暂无需要处理的报警。");
		};
		var params = {
			"CurAlarmData":CurAlarmData,
		};
		Sparraw.ajaxPost('envCtrl/AlarmDealDelay_v2.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				CurAlarmData = [];
				Sparraw.myNotice("处理成功。");
			}else{

			};
			$scope.alarmQuery();//再查询新信息
		});
	}
	//处理点击事件
	$scope.DealFunc = function(){
		if ($scope.sparraw_user_temp.Authority.MonitorDeal === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		var hideSheet = $ionicActionSheet.show({
                      buttons: [
                        { text: '延迟10分钟' },
                        { text: '延迟20分钟' },
                        { text: '延迟30分钟' }
                      ],
                      cancelText: '取消',
                      cancel: function() {
                      },
                      buttonClicked: function(index) {
                      	switch(index){
					      case 0 :
					      	$scope.DelayFunc(10);		//先处理
					      	break;
					      case 1 :
					      	$scope.DelayFunc(20);
					      	break;
					      case 2 :
					      	$scope.DelayFunc(30);
					      	break;
					      default  :
					      	break;
					    }
                        return true;
                      }
                  });
	}
})


// 报警统计
.controller("alarmStatisticsCtrl",function($scope, $state, $http, AppData) {
	
	setPortrait(true,true);//竖屏
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//数据源

	$scope.transferHouseId  =  ""  ;
	$scope.buildingDayAge   =  ""  ;
	$scope.alarmStatisticsData = {
		"houseData":[{
                "houseName"         :  ""                                             ,  //栋舍名称
                "dayAge"            :  ""                                             ,  //日龄
                "temp_avg_alarm"    :  {'temp_avg_alarm_H':"",'temp_avg_alarm_l':""}  ,  //平均
                "temp_in1_alarm"    :  {'temp_in1_alarm_H':"",'temp_in1_alarm_L':""}  ,  //前区
                "temp_in2_alarm"    :  {'temp_in2_alarm_H':"",'temp_in2_alarm_l':""}  ,  //中区
                "temp_in3_alarm"    :  {'temp_in3_alarm_H':"",'temp_in3_alarm_L':""}  ,  //后区
                "point_temp_alarm"  :  ""                                             ,  //点温差
                "power_status"      :  ""                                             ,  //断电报警（0）
          }]
	}

	
	//监控报警
	var params = {
		"FarmId"  :  $scope.sparraw_user_temp.farminfo.id
	};
	Sparraw.ajaxPost('sys/alarm/queryAlarmData.action', params, function(data){
		console.log(data);
		if (data.ResponseDetail.Result == "Success") {
			
			$scope.alarmStatisticsData.houseData = data.ResponseDetail.AlarmData;

		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	},null,200000);

	$scope.goAlarmLog = function(item){		
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseName==item.houseName){
				$scope.transferHouseId = $scope.sparraw_user_temp.userinfo.houses[i].HouseId;
				$scope.buildingDayAge  = $scope.alarmStatisticsData.houseData[i].dayAge;
			}
		}

	}



})

// 报警日志
.controller("alarmLogCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	$scope.setScreenStateFun = function(){
		setLandscape(true,true);//横屏
		var MAXHEIGHT = document.documentElement.clientWidth;

		var MINHEIGHT = MAXHEIGHT - 190;
		document.getElementById('tableDiv').style.minHeight = MINHEIGHT + 'px';
		document.getElementById('tableDiv').minHeight = MINHEIGHT + 'px';

		var MAX_HEIGHT = MAXHEIGHT - 240;
		document.getElementById('tableDiv').style.maxheight = MAX_HEIGHT + 'px';
		document.getElementById('tableDiv').maxheight = MAX_HEIGHT + 'px';
	}

	$scope.setData = function(){
		//数据源
	    $scope.alarmLogData = {	
	    	//"alarmCode"       :  ""  ,  //筛选栏的报警类型
	    	"AlarmCategory"       :  ""  ,  //筛选栏的报警类型
	        "AgeBegin"            :  ""  ,  //开始日龄
	        "AgeEnd"              :  ""  ,	//结束日龄
	        /*查询条件：
			"AlarmCategory":"All"-全部
					        "frontTemp"-前区温度报警
					        "middleTemp"-中区温度报警
					        "backTemp"-后区温度报警
					        "pointTemp"-点温差报警
					        "avgTemp"-平均温度报警
					        "powerStatus"-断电报警
			"AgeBegin"和"AgeEnd" 查询起始和截止日龄*/
			//一条日志的信息
	        "AlarmLog":[{
		            "aDayAge"         :  ""  ,  //日龄
		            "aDate"           :  ""  ,	 
		            "aTime"           :  ""  ,  //时间
		            "alarmID"         :  ""  ,	 
		            "alarmName"       :  ""  ,	 //显示的报警类型
		            "realValue"       :  ""  ,	 //实际温度
		            "targetValue"     :  ""  ,  //目标温度
		            "process_status"  :  ""  ,  //日志状态 01-待处理；02-处理中；03-已结束
		            "values"          :  ""  ,  //实际/目标
		            "process_status"  :  ""  ,  //响应状态
		            "deal_person"     :  ""  ,  //响应人员
		            "deal_time"       :  ""  ,  //响应时间
		            "is_normal"       :  ""  ,  //是否消除
		            "last_time"       :  ""     //持续时间
		            }]

	    };
	}



    

	$scope.houseName      = "" ;
	$scope.buildingDayAge = "" ;
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseId==$stateParams.receiveHouseId){
				$scope.houseName = $scope.sparraw_user_temp.userinfo.houses[i].HouseName;
			}
		}   
    $scope.buildingDayAge = $stateParams.buildingDayAge
	
	
	

		 		
    $scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};

	$scope.queryLog = function(){
		/*校验信息*/
	   	var required = [$scope.alarmLogData.AlarmCategory,$scope.alarmLogData.AgeBegin,$scope.alarmLogData.AgeEnd];
	   	if (parseInt(required[1]) > parseInt(required[2])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };
	    for(i in required){if(required[i]==''){return Sparraw.myNotice('尚有内容未填写...');}}
	    
		var params = {
			"HouseId"        :  parseInt($stateParams.receiveHouseId)                    ,
			"AlarmCategory"  :  $scope.alarmLogData.AlarmCategory                        ,
			"AgeBegin"       :  $scope.alarmLogData.AgeBegin                             ,
			"AgeEnd"         :  $scope.alarmLogData.AgeEnd
		};

		Sparraw.ajaxPost('sys/alarm/queryAlarmLog.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice("暂无报警信息。");
					$scope.alarmLogData.AlarmLog = [];
				}else{
					$scope.alarmLogData.AlarmLog = data.ResponseDetail.AlarmData;
				};

		});
		//$ionicSideMenuDelegate.toggleRight();
	}


	


	$scope.cancel = function(){
		console.log("取消啦");
		$ionicSideMenuDelegate.toggleRight();
	}



	$scope.setScreenStateFun();
    setTimeout(function() {
    	$scope.setData();
    	//设置默认值自动查询   
		$scope.alarmLogData.AlarmCategory  =  "All"  ;
	    $scope.alarmLogData.AgeBegin       =  "1"   ;
	    $scope.alarmLogData.AgeEnd         =  "80"   ;
	    setTimeout(function() {
	    	$scope.queryLog();
	    }, 500);
    }, 1500);

})

// 报警设置
.controller("alarmSettingsCtrl",function($scope, $state, $http, $ionicPopup, AppData, $ionicModal) {

	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    







    /*//////////////////////////////////////////////////////////
	//////////语音报警设置开始
	//////////////////////////////////////////////////////////
	var users = sparraw_user.userinfos;
	var farmId = $scope.sparraw_user_temp.farminfo.id;
	var alarmType = 0;
	var role = 4;
	var houseId = 0;
	var members = new Array();
	for(var index = 0; index < users.length; index++){
		//console.log(index)
		var user = users[index];
		//console.log(index +","+user)
		if(user != null && user.role != 4){
			members.push(user);
		}
		
	}
	
	$scope.getName = function (userId){
		var username = "";
		for(var index = 0; index < users.length; index++){
			//console.log(index)
			var user = users[index];
			//console.log(index +","+user)
			if(user != null && user.id == userId){
				username = user.name;
				break;
			}
		}
		return username;
	}
	
	 $scope.farmData = {
		"checked":true,
		"alarmer1":"饲养员",
		"alarmer2":0,
		"alarmer2s":members,
		"alarmer3s":[],
		"alarmer3":0,
		"alarmers":[],
		"enableds":[],
		"farmAlarmSetting":{}
     };
	 
	 var members3 = [];
	$scope.selectUser = function(){
		members3 = [];
		var alarmer2 = $scope.farmData.alarmer2;
		for(var index = 0; index < members.length; index++){
			var user = members[index];
			if(user != null && user.id != alarmer2){
				members3.push(user);
			}
		}
		$scope.farmData.alarmer3s = members3;
	}
	
	$scope.queryAlarmSetting = function(){
		var params = {
			"FarmId" :  farmId,
			"HouseId":houseId,
			"RemindMethod" :  alarmType
		};
		Sparraw.ajaxPost('sys/farm/remind/querySettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				var enableds = data.ResponseDetail.enableds;
				var alarmers = data.ResponseDetail.alarmers;
				
				var farmAlarmSetting = data.ResponseDetail.farmAlarmSetting;
				
				if(farmAlarmSetting.switchReleHouse == undefined){
					farmAlarmSetting = {"alarmReleHouse":"Y","bak1":"","bak2":"","farmId":farmId,"personReleHouse":"Y","remindMethod":alarmType,"switchReleHouse":"Y","id":-1}
				}
				if(enableds.length > 0){
					if(enableds[0].status == "Y"){
						$scope.farmData.checked = true;
					}else{
						$scope.farmData.checked = false;
					}
				}else{
					var obj = {"farmId":farmId,"houseId":houseId,"remindMethod":alarmType,"status":"Y","id":-1,"bak1":"","bak2":""}
					enableds.push(obj);
				}
				
				if(alarmers.length > 0){
					for(var index = 0; index < alarmers.length; index++){
						//console.log(index)
						var user = alarmers[index];
						//console.log(index +","+user)
						if(user != null){
							var level = user.userOrder;
							var userId = user.userId;
							var userType = user.userType;
							// 0:用户;1:角色
							if(level == 1){
								//role
								if(userType == 0){
									$scope.farmData.alarmer1 = $scope.getName(userId);
								}else if(userType == 1){
									$scope.farmData.alarmer1 = "饲养员";
								}
							}
							
							if(level == 2){
								$scope.farmData.alarmer2 = userId;
							}
							
							if(level == 3){
								$scope.farmData.alarmer3 = userId;
							}
						}
						
					}
				}else{
					
					alarmers.push({"remindMethod":alarmType,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":4,"userOrder":1,"userType":1,"bak1":"","bak2":""});
					alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":2,"userType":0});
					alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":3,"userType":0});
					
					// 默认选择第一个
					if(members.length > 0)
					{
						$scope.farmData.alarmer2 = members[0].id;
						
						$scope.selectUser();
						if(members3.length > 0){
							$scope.farmData.alarmer3 = members3[0].id;
						}
						
					}
				}
				
				$scope.farmData.alarmers = alarmers;
				$scope.farmData.enableds = enableds;
				$scope.farmData.farmAlarmSetting = farmAlarmSetting;
				$scope.selectUser();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
		
	}
	
	
	
	$scope.saveAlarm = function(){
		
		console.log("saveAlarm houseId:" + houseId)
		//return;
		var alarmer2 = $scope.farmData.alarmer2;
		console.log("alarmer2:" + alarmer2);
		var alarmer3 = $scope.farmData.alarmer3;
		console.log("alarmer3:" + alarmer3);
		console.log("checked:" + $scope.farmData.checked);
		
		if(alarmer2 == 0){
			//Sparraw.myNotice("保存成功。");
		}
		

		console.log($scope.farmData);
		
		if($scope.farmData.checked == true){
			$scope.farmData.enableds[0].status = "Y";
		}else{
			$scope.farmData.enableds[0].status = "N";
		}
		
		if($scope.farmData.alarmers[0] == null || $scope.farmData.alarmers[0] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":4,"userOrder":1,"userType":1,"bak1":"","bak2":""});
		}
		if($scope.farmData.alarmers[1] == null || $scope.farmData.alarmers[1] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":2,"userType":0});
		}
		if($scope.farmData.alarmers[2] == null || $scope.farmData.alarmers[2] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":3,"userType":0});
		}
		
		if(members.length > 0)
		{
			$scope.farmData.alarmers[1].userId = alarmer2;
		}
		
		if(members3.length > 0){
			$scope.farmData.alarmers[2].userId = alarmer3;
		}
		
		var params = {
			"FarmId" :  farmId,
			"HouseId": houseId,
			"RemindMethod" :  alarmType,
			"alarmers":$scope.farmData.alarmers,
			"enableds":$scope.farmData.enableds,
			"farmAlarmSetting":$scope.farmData.farmAlarmSetting,
			"alarmCodes":[
							{"houseId":houseId,"alarmCode":"B001H"},
							{"houseId":houseId,"alarmCode":"B001L"},
							{"houseId":houseId,"alarmCode":"C0002"}
			]
		 };



		Sparraw.ajaxPost('sys/farm/remind/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("保存成功。");
				var enableds = data.ResponseDetail.enableds;
				var alarmers = data.ResponseDetail.alarmers;
				var farmAlarmSetting = data.ResponseDetail.farmAlarmSetting;
				
				$scope.farmData.alarmers = alarmers;
				$scope.farmData.enableds = enableds;
				$scope.farmData.farmAlarmSetting = farmAlarmSetting;
				
				//$scope.query();
				//$scope.selectUser();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
		
	}
	
	
	$scope.pushNotificationChange = function(){
		if($scope.farmData.checked == true){
			//console.log("true");
			//document.getElementById('brokenEggsDIV').style.display = '';
			
		}else{
			//console.log("false");
			//document.getElementById('brokenEggsDIV').style.display = 'none';
		}
	}
	//////////////////////////////////////////////////////////
	//////////语音报警设置结束
	//////////////////////////////////////////////////////////*/




























	
	$scope.temperatureOffsetChange = function() {
		if ($scope.tempVar.tempCpsation) {
			$scope.showTempCpsationVal = true;
			$scope.tempVar.AlarmSetting.tempCpsation = 1;
		}else {
			$scope.showTempCpsationVal = false;
			$scope.tempVar.AlarmSetting.tempCpsation = 0;
		};
	}
	//判断温度补偿是否打开
	if ($scope.tempVar.AlarmSetting.tempCpsation == 1) {
		$scope.showTempCpsationVal = true;
	}else{
		$scope.showTempCpsationVal = false;
	};

	$scope.testMethods = function(){

		houseId = $scope.tempVar.AlarmSetting.HouseId;
		// 查询栋舍报警设置
		//$scope.queryAlarmSetting();

		$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = false ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = true ;

        $scope.tempVar.AlarmSetting.Delay            =  0  ;  //报 警 延 迟
        $scope.tempVar.AlarmSetting.tempCpsation     =  '0'  ;  //温度补偿
        $scope.tempVar.AlarmSetting.tempCpsationVal  =  '0'  ;  //补偿数值
        $scope.tempVar.AlarmSetting.alarmProbe       =  ''  ;  //报警探头
        $scope.tempVar.AlarmSetting.pointAlarm       =  5  ;  //点温差报警
        $scope.tempVar.AlarmSetting.tarTemp			 =  '';
        $scope.tempVar.AlarmSetting.minTemp		     =  '';
        $scope.tempVar.AlarmSetting.maxTemp		     =  '';
 

		//查询是否有数据记录
		var params = {
	    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id                     ,
	    		"HouseId"       :  $scope.tempVar.AlarmSetting.HouseId
			};

		Sparraw.ajaxPost('sys/alarm/querySettingData.action', params, function(data){
			//判断传送是否成功
			if (data.ResponseDetail.Result == "Success") {
				//判断第一次还是已有信息
				if (data.ResponseDetail.ResultFlag == "Y") {
					$scope.tempVar.AlarmSetting.Delay            =  JSON.stringify(data.ResponseDetail.AlarmSetting.Delay)  ;
					$scope.tempVar.AlarmSetting.tempCpsation     =  data.ResponseDetail.AlarmSetting.tempCpsation           ;
					$scope.tempVar.AlarmSetting.tempCpsationVal  =  data.ResponseDetail.AlarmSetting.tempCpsationVal        ;
					$scope.tempVar.AlarmSetting.alarmProbe       =  data.ResponseDetail.AlarmSetting.alarmProbe             ;
					$scope.tempVar.AlarmSetting.pointAlarm       =  data.ResponseDetail.AlarmSetting.pointAlarm             ;
					$scope.tempVar.AlarmSetting.tarTemp       =  data.ResponseDetail.AlarmSetting.tarTemp		  ;
					$scope.tempVar.AlarmSetting.minTemp       =  data.ResponseDetail.AlarmSetting.minTemp		  ;
					$scope.tempVar.AlarmSetting.maxTemp       =  data.ResponseDetail.AlarmSetting.maxTemp		  ;
					
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe &&data.ResponseDetail.AlarmSetting.effAlarmProbe.tempLeft1=="true")?true:false;
					
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempLeft2=="true")?true:false;

					$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempMiddle1=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempMiddle2=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempRight1=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempRight2=="true")?true:false;																										

				
					//判断温度补偿是否打开
					console.log($scope.tempVar.AlarmSetting.tempCpsation);
					if ($scope.tempVar.AlarmSetting.tempCpsation == 1) {
						$scope.showTempCpsationVal = true;
						$scope.tempVar.tempCpsation = true;
					}else{
						$scope.showTempCpsationVal = false;
						$scope.tempVar.tempCpsation = false;
					};	
				}else{
						//判断权限
						if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {

						}else{

							return app_alert("暂无数据,请联系场长进行设置。");
						};

						$scope.tempVar.AlarmSetting.Delay = 3;//报警延迟默认参数
						$scope.tempVar.AlarmSetting.alarmProbe = 2;//报警方式默认参数

						//判断用户是否使用默认数据
						app_confirm('该栋舍尚未设置报警信息,是否使用默认值?','提示',null,function(buttonIndex){
		                   if(buttonIndex == 2){
		                        $scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = false ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = true ;
				            	$scope.tempVar.AlarmSetting.Delay            =  data.ResponseDetail.AlarmSetting.Delay            ;
								$scope.tempVar.AlarmSetting.tempCpsation     =  data.ResponseDetail.AlarmSetting.tempCpsation     ;
								$scope.tempVar.AlarmSetting.tempCpsationVal  =  data.ResponseDetail.AlarmSetting.tempCpsationVal  ;
								$scope.tempVar.AlarmSetting.alarmProbe       =  data.ResponseDetail.AlarmSetting.alarmProbe       ;
								$scope.tempVar.AlarmSetting.pointAlarm       =  data.ResponseDetail.AlarmSetting.pointAlarm       ;
								$scope.tempVar.AlarmSetting.tarTemp       =  data.ResponseDetail.AlarmSetting.tarTemp		  ;
								$scope.tempVar.AlarmSetting.minTemp       =  data.ResponseDetail.AlarmSetting.minTemp		  ;
								$scope.tempVar.AlarmSetting.maxTemp       =  data.ResponseDetail.AlarmSetting.maxTemp		  ;

								$scope.save();
		                   }
			            }); 
			            

				};
			}else{
				Sparraw.myNotice("查询失败");
			};
		});
	}


	//默认调用查询
	$scope.tempVar.AlarmSetting.HouseId = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId);
	$scope.testMethods();
	//点击保存时调用
	$scope.Clicksave = function(){
		if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		var judgeClick = "1";
		obj = judgeClick;
		$scope.save(obj);
	}


    $scope.save = function(obj){


    	var params = {
    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id             ,
    		"HouseId"       :  $scope.tempVar.AlarmSetting.HouseId                  ,
    		"AlarmSetting"  : {
					            "Delay"           :  $scope.tempVar.AlarmSetting.Delay            ,
					            "tempCpsation"    :  $scope.tempVar.AlarmSetting.tempCpsation     ,
					            "tempCpsationVal" :  $scope.tempVar.AlarmSetting.tempCpsationVal  ,
					            "alarmProbe"      :  $scope.tempVar.AlarmSetting.alarmProbe       ,
					            "pointAlarm"      :  $scope.tempVar.AlarmSetting.pointAlarm       ,
					            "tarTemp"     	  :  $scope.tempVar.AlarmSetting.tarTemp       ,
					            "minTemp"  		  :  $scope.tempVar.AlarmSetting.minTemp          ,
					            "maxTemp"  		  :  $scope.tempVar.AlarmSetting.maxTemp          ,
					            "effAlarmProbe":  {	
										        "tempLeft1"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1+"",
										        "tempLeft2"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2+"",
										        "tempMiddle1" :$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1+"",
										        "tempMiddle2" :"false",
										        "tempRight1"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1+"",
										        "tempRight2"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2+""
										       }
					          }

		};


    	Sparraw.ajaxPost('sys/alarm/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.ErrorMsg == null) {
	   			houseId = $scope.tempVar.AlarmSetting.HouseId;
	   			Sparraw.myNotice("保存成功！");    
		    }else {
		   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		    };
	    });
    }

















    $scope.copyFun = function(){
    	app_confirm("所有栋舍将更新至当前设置，复制后您可以选择任何一栋单独修改，请确认。",null,null,function(buttonIndex){
			if(buttonIndex == 1){
				
			}else if(buttonIndex == 2){


			    var params = {
		    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id             ,
		    		"HouseId"       :  $scope.tempVar.AlarmSetting.HouseId                  ,
		    		"AlarmSetting"  : {
							            "Delay"           :  $scope.tempVar.AlarmSetting.Delay            ,
							            "tempCpsation"    :  $scope.tempVar.AlarmSetting.tempCpsation     ,
							            "tempCpsationVal" :  $scope.tempVar.AlarmSetting.tempCpsationVal  ,
							            "alarmProbe"      :  $scope.tempVar.AlarmSetting.alarmProbe       ,
							            "pointAlarm"      :  $scope.tempVar.AlarmSetting.pointAlarm       ,
							            "tarTemp"     	  :  $scope.tempVar.AlarmSetting.tarTemp       ,
							            "minTemp"  		  :  $scope.tempVar.AlarmSetting.minTemp          ,
							            "maxTemp"  		  :  $scope.tempVar.AlarmSetting.maxTemp          ,
							            "effAlarmProbe":  {	
												        "tempLeft1"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1+"",
												        "tempLeft2"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2+"",
												        "tempMiddle1" :$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1+"",
												        "tempMiddle2" :"false",
												        "tempRight1"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1+"",
												        "tempRight2"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2+""
												       }
							          }

				};


		    	Sparraw.ajaxPost('sys/alarm/saveSettingBatch.action', params, function(data){
					if (data.ResponseDetail.ErrorMsg == null) {
			   			houseId = $scope.tempVar.AlarmSetting.HouseId; 
			   			Sparraw.myNotice("保存成功！");   
				    }else {
				   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				    };
			    },null,200000);














			}
		});	
    }


    $ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }
})

//语音设置
.controller("voiceSettingsCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();

			setTimeout(
				function (){
					$scope.setData();
					$scope.queryDatatt();
				}
			,500);
		}
	,1000);

    $scope.setData = function(){

    	$scope.voiceSetData = {
    		"housesVoiceInfo":[]
    		/*"housesVoiceInfo":[{
    			"houseName":"01",
                "houseId": 307,
                "status": "Y",
                "alarmers": [{
                    "userId": 476,//第一报警人
                    "userOrder": 1,
                    "userType": 0
                },
                {
                    "userId": 643,//第二报警人
                    "userOrder": 2,
                    "userType": 0
                },
                {
                    "userId": 652,//第三报警人
                    "userOrder": 3,
                    "userType": 0
                }]
            }]*/
    	}
    }




    $scope.voiceSwitch = function(item){
    	console.log(item.status);
    }

    $scope.changeResponseA = function(item){
    	console.log(item);
    }

    $scope.changeResponseB = function(item){
    	console.log(item);
    }

    $scope.changeResponseC = function(item){
    	console.log(item);
    }

    $scope.queryDatatt = function(){
    	
		var params = {
			"FarmId":$scope.sparraw_user_temp.farminfo.id,
			"RemindMethod":"0",
		 };
		Sparraw.ajaxPost('sys/farm/remind/querySettingData_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.voiceSetData.housesVoiceInfo = data.ResponseDetail.houseAlarmSetting;
				for (var i = 0; i < $scope.voiceSetData.housesVoiceInfo.length; i++) {
					if ($scope.voiceSetData.housesVoiceInfo[i].status == "Y") {
						$scope.voiceSetData.housesVoiceInfo[i].status = true;
					}else{
						$scope.voiceSetData.housesVoiceInfo[i].status = false;
					}
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    


    $scope.saveFun = function(item){
    	console.log(item);
    	console.log(item.alarmers);

    	if (item.alarmers.length == 0) {
    		return Sparraw.myNotice("请正确选择报警人信息。");
    	}else{
    		for (var i = 0; i < item.alarmers.length; i++) {
	    		if (item.alarmers[i].userId == "" || !item.alarmers[i].userId) {
	    			return Sparraw.myNotice("请正确选择报警人信息。");
	    		}else{

	    		}
	    	}
    	}

    	
    	
		var switchState = "N";
		if (item.status && item.status != "Y") {
			switchState = "Y";
		}else{
			switchState = "N";
		}
		var params = {
			"FarmId" :  $scope.sparraw_user_temp.farminfo.id,
			"HouseId": item.HouseId,
			"RemindMethod" :  "0",
		    "alarmers":[{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[0].userId,"userOrder":1,"userType":0,"bak1":"","bak2":""},
						{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[1].userId,"userOrder":2,"userType":0,"bak1":"","bak2":""},
						{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[2].userId,"userOrder":3,"userType":0,"bak1":"","bak2":""}],
			"enableds":[{"farmId":$scope.sparraw_user_temp.farminfo.id,	
						"houseId":item.HouseId,
						"remindMethod":"0",
						"status":switchState,
						"id":-1,
						"bak1":"",
						"bak2":""}],
			"farmAlarmSetting":{"bak1":"",
								"bak2":"",
								"farmId":$scope.sparraw_user_temp.farminfo.id,
								"remindMethod":0,
								"switchReleHouse":"Y",
								"alarmReleHouse":"Y",
								"personReleHouse":"Y",
								"id":-1},
			"alarmCodes":[
							{"houseId":item.HouseId,"alarmCode":"B001H"},
							{"houseId":item.HouseId,"alarmCode":"B001L"},
							{"houseId":item.HouseId,"alarmCode":"C0002"}
			]
		 };

		 console.log(params);
		Sparraw.ajaxPost('sys/farm/remind/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.queryDatatt();
				Sparraw.myNotice(item.HouseName+"栋保存成功。");
				
				
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
	}

})

// 数据分析
.controller("dataAnalyseTableCtrl",function($scope, $state, $ionicLoading, $http, $ionicPopup, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    setPortrait(true,true);
    

    $scope.pointDevelop = function(reportId) {
    	if(reportId == 'tempChart'){//温度曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"tempChart");
    	}else if(reportId == 'reportTempHumi'){//温湿度综合
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportTempHumi");
    	}else if(reportId == 'eggWeigLayRate'){//产蛋率曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"eggWeigLayRate");
    	}else if(reportId == 'eggWeigLay'){//蛋重曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"eggWeigLay");
    	}else if(reportId == 'onlyChickEggs'){//只鸡产蛋曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"onlyChickEggs");
    	}else if(reportId == 'onlyChickEggsDemo'){//只鸡产蛋曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"onlyChickEggsDemo");
    	}else if(reportId == 'cullDeathRate'){//死淘率曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"cullDeathRate");
    	}else if(reportId == 'reportFeedWaterRate'){//采食饮水曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportFeedWaterRate");
    	}else if(reportId == 'reportEggWeightFeed'){//料蛋比曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportEggWeightFeed");
    	}else if (reportId == 'waterFeed') {//水料比曲线
    		Sparraw.getInfoStatus($ionicPopup,$state,"waterFeed");
    	}else if (reportId == 'chickenWeightAnalyze'){//体重曲线
			Sparraw.getInfoStatus($ionicPopup,$state,"chickenWeightAnalyze");
		}else if (reportId == 'salesAnalyze'){//销售分析曲线
			Sparraw.getInfoStatus($ionicPopup,$state,"salesAnalyze");
		}else if (reportId == 'coluCurve'){//销售分析曲线
			Sparraw.getInfoStatus($ionicPopup,$state,"coluCurve");
		}else{//建设中
    		pointDevelop();
    	}
	};
})
//  温度测试K线图
.controller("tempKMapCtrl",function($scope, $state, $http, $ionicPopup, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    setTimeout(
		function (){
		app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	app_lockOrientation('portrait');//出去时竖屏
		$state.go("dataAnalyseTable");
    }


    //数据源
	$scope.tempKMapData = {
		"selectHouse"    :  "",
		"selectedBatch"  :  "",
		"farmBatch"      :  ""	
	}




	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '44';
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '44';
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}


	//一共多少条线	
	var sumObj = new Array(3);
	//一共多少数据
	var miniObj = new Array(160);
	for (var i = 0; i <miniObj.length ; i++) {
		miniObj[i] = parseInt(Math.random()*(250-0)+0);
	};
	for (var i = 0; i < sumObj.length; i++) {
		//每条数据有多少个
		var start = i * 55;
		var end = start + 55;
		sumObj[i] = miniObj.slice(start,end);
	};

	//随机颜色
	var getRandomColor = function(){
	  return  '#' +    
	    (function(color){    
	    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])    
	      && (color.length == 6) ?  color : arguments.callee(color);    
	  })('');    
	} 

	/*K线图数据*/
	//一共多少数据
	var kSingleData = new Array(196);
	for (var i = 0; i <kSingleData.length ; i++) {
		kSingleData[i] = parseInt(Math.random()*(100-80)+80);
	};
	//一条k线的数据
	var kTotalData = new Array(49);
	for (var i = 0; i < kTotalData.length; i++) {
		var start = i * 4;
		var end = start + 4;
		kTotalData[i] = kSingleData.slice(start,end);
	};
	console.log(kTotalData);



  var myChart  ;
  var option   ;

  var xData    ;
  var yData1   ;
  var yData2   ;
  var yData3   ;
  var yData4   ;




  xData = ['00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30','24:00'];//X轴数据
  //xData = ["12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)"];
  yData1 = sumObj[0];
  yData2 = sumObj[1];
  yData3 = sumObj[2];
  yData4 = sumObj[1];



  var tTitleName;

  var tLegend;
  var serialsName1;
  var serialsName2;
  var serialsName3;
  var serialsName4;


  var testY = [10,20,30];


  tTitleName = "温度K线图";
  tLegend = ['数据1','数据2','数据3','数据4'];//点击显示一条数据
  serialsName1 = "数据1";
  serialsName2 = "数据2";
  serialsName3 = "数据3";
  serialsName4 = "数据4";


   
  require.config({
      paths: {
          echarts: 'js/echarts-2.2.7'
      }
  });
  //显示多少（什么）数据
  require(
      [
          'echarts',
          'echarts/chart/line',
      ],
      function (ec) {
          myChart = ec.init(document.getElementById('main'));
          option = {
				tooltip : {
                    trigger: 'axis',
                    textStyle:{
                      fontSize:13
                	},
                    backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
                },
                legend: {
                    data:tLegend,
                    selected:{//显示部分数据
                    	"目标温度":false,
                    	"温度移动平均":false,
                    	"湿度":false,
                    	"数据4":false
                    }

                },
                grid://表对应上下左右的大小
                {
                    x:50,
                    y:30,
                    x2:20,
                    y2:30
                },
                xAxis : [
                    {

                        type : 'category',
                        data : xData,
                        nameLocation:'start',
                        show :true,//是否显示x轴
                        axisTick : true,
                        axisLabel:{
                        	//margin:10 //文字与x轴的距离
                        },
                        /*splitLine:{

                        },*/
                        splitLine:true,
                        axisLine:{
                        	lineStyle:{//x轴风格
                        		color: '#000',
                        		width:0.5
                        	}
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                            //formatter: '{value}'//左边的数据
                        	//rotate:45//刻度旋转
                        	//clickable:true//y轴刻度是否能被点击
                        	formatter:function (params,ticket,callback) {
                                var res = "";
                               	for (var i = 0; i < testY.length; i++) {
                               		res =+ testY[i];
                               	}
                                  
                                return res;
                            }
                        },

                        splitNumber:5,//设置y轴刻度分段默认为5
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#000',
                        		width:1
                        	}
                        }
                    },
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                              show:false,//是否显示
                              formatter: '{value}'//右边的数据
                          },
                        splitLine : false,
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#fff',
                        		width:1
                        	}
                        }
                    }
                ],
                series : [
                    {
	                  name:serialsName1,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,//是否折线
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName2,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData2,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName3,

	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData3,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    },{
	                  name:serialsName4,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData4,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    }
                    

                ]
            };

          myChart.on('click',function(param){
			 	console.log(param.name);
			 });



          myChart.on(option);
          myChart.setOption(option);
          window.onresize = myChart.resize;
      }
  );
})


// 死淘率曲线
.controller("cullDeathRateCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"ViewType":"01",
		"compareType":  "" ,
		"selectedHouse": "",
		"containBatchHouse":[]
    };
	
	var type = "01";
	var yLeftRange = [0,10];//undefined;//[0,15];
	//死淘率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率:" + type);
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : type,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        if(params.ViewType==null){
                  params.ViewType='02';
                  console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryDeathCull.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 
						 var yLeftMin = null;
						 var yLeftMax = null;
						 
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
							
							var max = Math.max.apply(null, data.ResponseDetail.LayerRate[i].HouseDatas);
							var min = Math.min.apply(null, data.ResponseDetail.LayerRate[i].HouseDatas);
							
							console.log("max:" + max + ",min；" + min);
							if(yLeftMax == null){
								yLeftMax = max;
							}else if(yLeftMax < max){
								yLeftMax = max;
							}
							
							if(yLeftMin == null){
								yLeftMin = min;
							}else if(yLeftMin > min){
								yLeftMin = min;
							}
						}
						
						
					    var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
					    //console.log($scope.cullDeathRateData.xData);
					    
						
					    if(Maxage < 88){
						  var j = 88 - Maxage;
						  var L = data.ResponseDetail.xAxis.length;
                          for (var i = 0; i < j; i++) {
                            $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
                          }
					    }
						
					    if (type=="01" || type=="03"){
                                var day_age_week  ="周龄"
					    }else{
                                var day_age_week  ="日龄"
					    }
						
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						var tail = "";
						if(type === "01"){
							tail = "‰";
							$scope.cullDeathRateData.selectUnit = '‰';
							if(yLeftMin != null && yLeftMin < 0){
								yLeftMin = 0;
							}
							
							if(yLeftMax != null && yLeftMax > 2){
								yLeftMax = 2;
							}
							yLeftMax = (yLeftMax).toFixed(1);
						}

						if(type === "03"){
							tail = "％";
							$scope.cullDeathRateData.selectUnit = '％';
							if(yLeftMin != null && yLeftMin < 0){
								yLeftMin = 0;
							}
							
							if(yLeftMax != null && yLeftMax > 5){
								yLeftMax = 5;
							}
							yLeftMax = Math.ceil(yLeftMax);
							//yLeftMax = (yLeftMax).toFixed(1);
						}
						yLeftRange = [yLeftMin,yLeftMax];
						

					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '单位：' + tail,
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
									            }
		                                        return    res;
						                     },
											 yLeftRange
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
	 
    
	
	$scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='每周数'){
        	 console.log("这是changes2方法");
            
        	 document.getElementById("chang").innerHTML = '累计数';
             document.getElementById("cullSurTi").innerHTML = '每周死淘率曲线';
             // params.FeedWaterFlag='Feed';
			 yLeftRange = [0,10];
			 type = "01";
             return $scope.changes1();
        }else if (dd=='累计数'){
        	 console.log("这是changes1方法");
			 document.getElementById("chang").innerHTML = '每周数';
        	 document.getElementById("cullSurTi").innerHTML = '累计死淘率曲线';
			 yLeftRange = null;
        	 // params.FeedWaterFlag='Water';
			  type = "03";
       	     return $scope.changes1();
        }
    }


    setLandscape(true,true);
	$scope.changes1();

})

// 温湿度综合报表
.controller("reportTempHumiCtrl",function($scope, $state, $http,$ionicLoading, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	


    $scope.goDataAnalyseTable = function(){
    	$state.go("envMonitoring");   
    }


    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('TempHumi_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('TempHumi_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('TempHumi_DIV').style.height = DIVHEIGHT + 'px';
	}


	$scope.tempChartData = {
		"batchTable"        :  ""                           ,//批次表
		// "selectedHouseId"   :  JSON.stringify($scope.sparraw_user_temp.houseinfos[0].id),//选中的栋舍id
		"selectedHouseId"   :  $scope.sparraw_user_temp.houseinfos[0].id + "",//选中的栋舍id
	}
   
   //获取批次信息
	var params = {
		'IsNeedDelay':'Y',
		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id 
	};
	Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
		if (data.ResponseDetail.Result == "Success") {
			$scope.tempChartData.batchTable = data.ResponseDetail.FarmBreedIdArray;
			//获取key
			for(var key in $scope.tempChartData.batchTable){
			    $scope.tempChartData.selectedBatch = key;
			}
			//获取value  
			for(var item in $scope.tempChartData.batchTable){  
		        if(item==key){  
		            $scope.tempChartData.selectedBatchVal = $scope.tempChartData.batchTable[item];
		        }  
		    }  
            $scope.switchBatch();
		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});

	
	var tFarmCode ;
	var tBatchNum;
	var myChart ;
	var option ;
	var TNata ;
	var kData ;
	var yData1 ;
	var yData2 ;
	var xData ;
	$scope.switchBatch= function(){
		console.log("farmBreedBatchId:" + $scope.sparraw_user_temp.farminfo.farmBreedBatchId)
		console.log("selectedHouseId:" + $scope.tempChartData.selectedHouseId)
		var params = {
			'IsNeedDelay':'Y',
			"FarmBreedId"   : $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			"HouseId"       : $scope.tempChartData.selectedHouseId
			//"FarmBreedId"   : 55,
			//"HouseId"       : 5
		};
		Sparraw.ajaxPost('rep/TempHumi/TempHumiReq.action', params, function(data){
			if (data.ResponseDetail.Result == "Success"){
			 kData  = new Array(data.ResponseDetail.THDatas.length);
			 TNata;
			 yData1 =new Array(data.ResponseDetail.THDatas.length);
			 yData2 = new Array(data.ResponseDetail.THDatas.length);
			 xData  = new Array(data.ResponseDetail.THDatas.length);
				for (var i = data.ResponseDetail.THDatas.length - 1; i >= 0; i--){
					 TNata = new Array(4);
					TNata[0] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[1] = data.ResponseDetail.THDatas[i].MaxTemp;
					TNata[2] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[3] = data.ResponseDetail.THDatas[i].MaxTemp;
					kData[i] = TNata;
				   	yData1[i] = data.ResponseDetail.THDatas[i].TarTemp;
					yData2[i] = data.ResponseDetail.THDatas[i].Humi;
				}
				xData = data.ResponseDetail.xAxis;
				
			
                var L = data.ResponseDetail.xAxis.length
				if(L < 60){
					var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					var j = 60 - L;
					for (var i = 0; i < j; i++) {
						xData[L+i] = Maxage + i + 1;
					}
				}
				
				
				/*
				if(data.ResponseDetail.xAxis.length>46){
                   xData = data.ResponseDetail.xAxis;
				}else{
				   xData = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
				}*/
			    return $scope.goecharts();
			}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			return $scope.goecharts();
			};
		});
	}


	var  tTitleName = "温度湿度综合表";
	var  tLegend = ['平均温度(℃)','目标温度(℃)','湿度%'];
	var  serialsName1 = "平均温度(℃)";
	var  serialsName2 = '目标温度(℃)';
	var  serialsName3 = "湿度%";
    
	$scope.goecharts = function (){ require.config({
	  paths: {
	      echarts: 'js/echarts-2.2.7'
	  }
	});
	require(
	  [
	      'echarts',
	      'echarts/chart/line',   
	      'echarts/chart/k'
	  ],
	  function (ec) {
	      myChart = ec.init(document.getElementById('main'));
	      option = {
				tooltip : {
	                trigger: 'axis',

	                textStyle:{
	                  fontSize:13
	            	},
	            	backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
	                formatter: function (params) {
	                	var res = '日龄：' +params[0].name;
	                	if (params[0].value[3]!=null) {
	                		res += '<br/>  最高温度 : ' + params[0].value[1] + '℃';
	                	}else{
                            res += '<br/>  最高温度 : - ℃';
	                	};
						if (params[0].value[2]!=null) {
	                		 res += '<br/>  最低温度 : ' + params[0].value[0] + '℃';
	                	}else{
	                		 res += '<br/>  最低温度 : - ℃';
	                	};		
	                    if (params[1].value!=null) {
	                		res += '<br/>目标温度' ;
	                        res += ' : ' +params[1].value + '℃';
	                	}else{
	                		res += '<br/>目标温度' ;
	                        res += ' : - ℃';
	                	};
	                	if (params[2].value!=null) {
	                		res += '<br/>湿度' ;
	                        res += ' : ' + params[2].value + '%';
	                	}else{
	                		res += '<br/>湿度' ;
	                        res += ' : - %';
	                	};       
	                    return res;
	                }
	            },
	            grid:
	            {
	                x:50,
	                y:30,
	                x2:50,
	                y2:30,
	                borderColor:'#BBB'
	            },
	            legend: {
	                data:tLegend
	            },
	            xAxis : [
	                {

	                    type : 'category',
	                    data : xData,
	                     splitLine:{show: false},
	                    nameLocation:'start',
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            yAxis : [
	                {
	                	position:'left',
	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          formatter: '{value}°C'
	                      },
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		//color: '#000000',
	                    		width:1
	                    	}
	                    }
	                },
	                {
	                	position:'right',
 	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          show:true,
	                          formatter: '{value}.0%'
	                      },
	                    splitLine : false,
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            series : [
	                {
	                    name:serialsName1,
	                    type:'k',
	                    data:kData,
	                    itemStyle: {
			                normal: {
			                    lineStyle: {
			                        width:0.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName2,
	                  type:'line',
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
	                    itemStyle: {
			                normal: {
			                    lineStyle: {
			                        width:0.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName3,
	                  type:'line',
	                  yAxisIndex: 1,
	                  data:yData2,
	                  smooth:true,
	                   // symbol:'none',
	                   symbolSize:1,
	                    itemStyle: {
			                normal: {
			                	color:"#87CEFA",
			                    lineStyle: {
			                        width:0.5
			                    },

			                    areaStyle: {
			                        // 区域图，纵向渐变填充
			                        color : (function (){
			                            var zrColor = require('zrender/tool/color');
			                            return zrColor.getLinearGradient(
			                                0, 200, 0, 400,
			                                [[1, 'rgba(135,206,250,0.5)'],[1, 'rgba(255,255,255,0.1)']]
			                            )
			                        })()
			                    }

			                }
			            }
	                }
	            ]
	        };
	      myChart.setOption(option);
	      window.onresize = myChart.resize;
	  }
	);

	}

	setLandscape(true,true);
	$scope.switchBatch();


	
})
// 料蛋比
.controller("reportEggWeightFeedCtrl2",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
     Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setTimeout(
		function (){
		app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"ViewType":"02",
		"compareType":  "" ,
		"selectedHouse": "",
		"containBatchHouse":[]
    };
	//蛋料bi
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率");
 		var params = { 
        	'ViewType' : $scope.cullDeathRateData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        if(params.ViewType==null){
                  params.ViewType='02';
                  console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryFeedEggRatio.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
						}
					    
						var day_age_week  ="周龄";
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.cullDeathRateData.xData);
						
					    if ($scope.cullDeathRateData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄"
					    }
						
						
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '单位：％',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value ;
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
	 $scope.changes1();
     //切换查看方式
    $scope.changesCompareType = function(){
	     return $scope.changes1();
	};   
})
// 日采食曲线
.controller("reportFeedWaterCtrl",function($scope, $state,$ionicLoading, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"02",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
    var params = { 
    		'IsNeedDelay':'Y',
        	'FeedWaterFlag' : null,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
	//采食饮水
     $scope.changes1 = function(){
     	console.log("采食饮水");
     	$scope.cullDeathRateData.xData=[];
        console.log(params);
        if(params.FeedWaterFlag==null){
            params.FeedWaterFlag='Feed';
            var yName  ="单位：克/天·只"
		}else if (params.FeedWaterFlag=='Water') {
           var yName  ="单位：毫升/天·只"
		}else if (params.FeedWaterFlag=='Feed') {
           var yName  ="单位：克/天·只"
		}
		 console.log("params");
		Sparraw.ajaxPost('layer_report/queryFeedWater.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
						}
					    var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
					    console.log($scope.cullDeathRateData.xData);
						 
					    
					    var L = data.ResponseDetail.xAxis.length;
					    if($scope.cullDeathRateData.xData.length<60){
							var j = 60-data.ResponseDetail.xAxis.length;
                          for (var i = 0; i < j; i++) {
                            $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
                          }
					    }
					    var day_age_week  ="日龄"
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 yName,
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
	  
	  $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='采食'){
        	 console.log("这是changes2方法");
             document.getElementById("chang").innerHTML = '饮水';
             document.getElementById("cullSurTi").innerHTML = '采食曲线';
              params.FeedWaterFlag='Feed';
             return $scope.changes1();
        }else if (dd=='饮水'){
        	 console.log("这是changes1方法");
        	 document.getElementById("chang").innerHTML = '采食';
        	 document.getElementById("cullSurTi").innerHTML = '饮水曲线';
        	  params.FeedWaterFlag='Water';
       	     return $scope.changes1();
        }
    }


    setLandscape(true,true);
	$scope.changes1();


})


//料蛋比曲线 
.controller("reportEggWeightFeedCtrl",function($scope, $state,$ionicLoading, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	
	


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

	if(navigator.userAgent.indexOf('Android') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('eggWeight_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('eggWeight_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('eggWeight_DIV').style.height = DIVHEIGHT + 'px';
	}


	$scope.eggFeedData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };

	//体重
    $scope.changes1 = function(){
     	$scope.eggFeedData.xData=[];
     	
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.eggFeedData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                params.ViewType='01';
		}
		Sparraw.ajaxPost('layer_report/queryFeedEggRatio.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.eggFeedData.yName =  [];
						 $scope.eggFeedData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.eggFeedData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.eggFeedData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
						}
						
					    var day_age_week  ="周龄";
					    $scope.eggFeedData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.eggFeedData.xData);
						
					    if ($scope.eggFeedData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.eggFeedData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.eggFeedData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄"
					    }
						console.log($scope.eggFeedData.xData);
						
						$scope.eggFeedData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.eggFeedData.selectUnit = '';
						getLineChart($scope.eggFeedData.xData,
											 $scope.eggFeedData.yData,
											 $scope.eggFeedData.yName,
											 $scope.eggFeedData.yColor,
											 $scope.eggFeedData.hiddenPara,
											 $scope.eggFeedData.selectUnit,
											 '',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
						   
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	
	

	$scope.changeViewType = function(){
		$scope.changes1();
	}


	setLandscape(true,true);
	$scope.changes1();


	
})

//水料比曲线
.controller("waterFeedCtrl",function($scope, $state,$ionicLoading, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

	if(navigator.userAgent.indexOf('Android') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('waterFeed_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('waterFeed_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('waterFeed_DIV').style.height = DIVHEIGHT + 'px';
	}

	$scope.waterFeedData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };

	//体重
     $scope.changes1 = function(){
     	$scope.waterFeedData.xData=[];
     	
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.waterFeedData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                params.ViewType='01';
		}
		Sparraw.ajaxPost('layer_report/queryWaterFeed.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.waterFeedData.yName =  [];
						 $scope.waterFeedData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
							$scope.waterFeedData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
							$scope.waterFeedData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
						}
					    
						var day_age_week  ="周龄";
					    $scope.waterFeedData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.waterFeedData.xData);
						
					    if ($scope.waterFeedData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.waterFeedData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.waterFeedData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄"
					    }
						console.log($scope.waterFeedData.xData);
						
						$scope.waterFeedData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.waterFeedData.selectUnit = '';
						getLineChart($scope.waterFeedData.xData,
											 $scope.waterFeedData.yData,
											 $scope.waterFeedData.yName,
											 $scope.waterFeedData.yColor,
											 $scope.waterFeedData.hiddenPara,
											 $scope.waterFeedData.selectUnit,
											 '',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
						   
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	
	

	$scope.changeViewType = function(){
		$scope.changes1();
	}

	setLandscape(true,true);
	$scope.changes1();


})

//销售分析曲线
.controller("salesAnalyzeCtrl",function($scope, $state,$ionicLoading, $http, $stateParams, $ionicPopup, AppData) {
	//salesAnalyze
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	

	


    $scope.goDataAnalyseTable = function(){
    	if($stateParams.lastPage == "eggSellsReport"){
			// 销售日报
			$state.go("eggSellsReport");
		}else if($stateParams.lastPage == "eggSellsList"){
			// 销售记录
			$state.go("eggSellsList");
		}else if($stateParams.lastPage == "eggSellsReportTable"){
			// 销售报表
			$state.go("eggSellsReportTable");
		}else{
			$state.go("dataAnalyseTable");
		}	
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}


	$scope.salesAnalData = {
 		"FarmId"       :  $scope.sparraw_user_temp.farminfo.id,
    	"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        "ViewType"     :  "02"  ,
        "xAxis"        :  []    ,
        "SaleDatas"    :  {},
		"ViewTypes"	 	:{"01":"按周龄","02":"按日期"}
    }
    //表数据
    var tFarmCode ;
	var tBatchNum;
	var myChart ;
	var option ;
	var LData = [];//左边的
	var RData = [];//右边的
	var yData1 = [];//销售量data
	var yData2 = [];//蛋价曲线data
	var xData = [];//日龄
	var  tTitleName = "销售分析曲线";
	var  tLegend = ['销售量','蛋价'];
	var  serialsName2 = '销售量';
	var  serialsName3 = "蛋价曲线";

	var priceType = "01";
	
    $scope.updateData = function(){
	    tFarmCode ;
		tBatchNum;
		myChart ;
		option ;
		LData = [];//左边的
		RData = [];//右边的
		yData1 = $scope.salesAnalData.SaleDatas.SaleAmount;//销售量data
		var vals = [];
		for(var val in yData1){
			var value = $scope.salesAnalData.SaleDatas.SaleAmount[val];
			//console.log("zhongliang:" + $scope.salesAnalData.SaleDatas.SaleAmount[val]);
			vals.push(value);
		}
		yData1 = vals;
		yData2 = $scope.salesAnalData.SaleDatas.SalePrice;//蛋价曲线data
		xData = $scope.salesAnalData.xAxis;//日龄
		tTitleName = "销售分析曲线";
		tLegend = ['销售量','蛋价'];
		serialsName2 = '销售量';
		serialsName3 = "蛋价";
    }


	$scope.goecharts = function (){ require.config({
	  paths: {
	      echarts: 'js/echarts-2.2.7'
	  }
	});
	require(
	  [
	      'echarts',
	      'echarts/chart/line',   
	  ],
	  function (ec) {
	      myChart = ec.init(document.getElementById('main'));
	      option = {
				tooltip : {
	                trigger: 'axis',

	                textStyle:{
	                  fontSize:13
	            	},
	            	backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
	                formatter: function (params) {
						var viewType = $scope.salesAnalData.ViewType;
						var str = "周龄";
						if (viewType == "02") {
							str = "日龄";
						}
	                	var res = str + '：' +params[0].name;
						var v1 = params[0].value*2;
						if (v1 != "-"){
							//v1 = v1.toFixed(2);
						}
						
						var v2 = params[1].value;
						if (v2 != "-"){
							//v2 = v2.toFixed(1);
						}
						
						var unit = "元/斤";
						
						if(priceType == "02"){
							unit = v2 + "元/斤";
						}else{
							unit = v2 + "元/箱";
						}
						
						
	                	res += '<br/>  销售量 : ' + v1 + '';
	                	res += '<br/>  蛋价: ' + v2;
						
	                	return res;
	                    
	                }
	            },
	            grid:
	            {
	                x:50,
	                y:30,
	                x2:50,
	                y2:30,
	                borderColor:'#BBB'
	            },
	            legend: {
	                data:tLegend
	            },
	            xAxis : [
	                {

	                    type : 'category',
	                    data : xData,
	                    splitLine:{show: false},
	                    nameLocation:'start',
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            yAxis : [
	                {	name:"斤",
	                	position:'left',
	                    type : 'value',
	                    data : LData,
	                    nameTextStyle:{
	                      fontSize:13,
						  color: '#000000'
	                    },
	                    axisLabel : {
	                          formatter: '{value}'
	                      },
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		//color: '#000000',
	                    		width:1
	                    	}
	                    }
	                },
	                {	name:"元/斤",
	                	position:'right',
 	                    type : 'value',
 	                    data : RData,
	                    nameTextStyle:{
	                      fontSize:13,
						  color: '#000000'
	                    },
	                    axisLabel : {
	                          show:true,
	                          formatter: '{value}'
	                      },
	                    splitLine : false,
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            series : [
	                {
	                  name:serialsName2,
	                  type:'line',
	                  smooth:false,
	                  //symbol:'none',
	                  symbolSize:0,
	                  data:yData1,
	                    itemStyle: {
			                normal: {
			                    lineStyle: {
			                        width:1.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName3,
	                  type:'line',
	                  yAxisIndex: 1,
	                  data:yData2,
	                  smooth:true,
	                   // symbol:'none',
	                   symbolSize:0,
	                    itemStyle: {
			                normal: {
			                    lineStyle: {
			                        width:1.5
			                    }
			                }
			            }
	                }
	            ]
	        };
	      myChart.setOption(option);
	      window.onresize = myChart.resize;
	  }
	);

	}




	var ArrList=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33];
	function getArrayItems(arr, num) {
	    //新建一个数组,将传入的数组复制过来,用于运算,而不要直接操作传入的数组;
	    var temp_array = new Array();
	    for (var index in arr) {
	        temp_array.push(arr[index]);
	    }
	    //取出的数值项,保存在此数组
	    var return_array = new Array();
	    for (var i = 0; i<num; i++) {
	        //判断如果数组还有可以取出的元素,以防下标越界
	        if (temp_array.length>0) {
	            //在数组中产生一个随机索引
	            var arrIndex = Math.floor(Math.random()*temp_array.length);
	            //将此随机索引的对应的数组元素值复制出来
	            return_array[i] = temp_array[arrIndex];
	            //然后删掉此索引的数组元素,这时候temp_array变为新的数组
	            temp_array.splice(arrIndex, 1);
	        } else {
	            //数组中数据项取完后,退出循环,比如数组本来只有10项,但要求取出20项.
	            break;
	        }
	    }
	    return return_array;
	}




	$scope.inquire = function(){
		var params = {
			'IsNeedDelay':'Y',
			"FarmId"       :  $scope.salesAnalData.FarmId         ,
			"ViewType"     :  $scope.salesAnalData.ViewType       ,
			"FarmBreedId"  :   $scope.salesAnalData.FarmBreedId  
		};
		Sparraw.ajaxPost('layer_report/querySaleEgg.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				var temp = [];
				for(var i = 0 ; i < data.ResponseDetail.xAxis.length; i++){
					if(data.ResponseDetail.xAxis[i] > 88){
						break;
					}
					temp[i] = data.ResponseDetail.xAxis[i];
				}
				$scope.salesAnalData.xAxis =   temp ;
				$scope.salesAnalData.SaleDatas = data.ResponseDetail.SaleDatas ;
				priceType = data.ResponseDetail.priceType;
				$scope.updateData();
    			$scope.goecharts();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		$scope.goecharts();
	}
	
	$scope.changeViewType = function(){
		var viewType = $scope.salesAnalData.ViewType;
		$scope.inquire();
	}

	

	setLandscape(true,true);
	$scope.changeViewType();


})



//体重曲线
.controller("chickenWeightAnalyzeCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('salesAnalyze_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.chickenWeightData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };

	//体重
     $scope.changes1 = function(){
     	$scope.chickenWeightData.xData=[];
     	
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : "01",
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                params.ViewType='01';
                //console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryChickenWeight.action', params, function(data){
			//data.ResponseDetail.Result = "Fail";
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.chickenWeightData.yName =  [];
						 $scope.chickenWeightData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.AvgWeight.length; i++) {
							$scope.chickenWeightData.yName[i] =  data.ResponseDetail.AvgWeight[i].HouseName;
							$scope.chickenWeightData.yData[i] =  data.ResponseDetail.AvgWeight[i].HouseDatas;
						}
					    var day_age_week  ="周龄";
					    $scope.chickenWeightData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.chickenWeightData.xData);
						
					    if ($scope.chickenWeightData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.chickenWeightData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.chickenWeightData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄"
					    }
						
						var temp = [];
						for(var i = 0 ; i < data.ResponseDetail.xAxis.length; i++){
							if(data.ResponseDetail.xAxis[i] > 88){
								break;
							}
							temp[i] = data.ResponseDetail.xAxis[i];
						}
						$scope.chickenWeightData.xData =   temp ;
				
						console.log($scope.chickenWeightData.xData);
					    //console.log($scope.chickenWeightData.xData);
						$scope.chickenWeightData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.chickenWeightData.selectUnit = '';
						getLineChart($scope.chickenWeightData.xData,
											 $scope.chickenWeightData.yData,
											 $scope.chickenWeightData.yName,
											 $scope.chickenWeightData.yColor,
											 $scope.chickenWeightData.hiddenPara,
											 $scope.chickenWeightData.selectUnit,
											 '单位：kg',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value +'';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
						
						//getLineChartNull();

						
						/*
						getLineChart([],
											 [],
											 $scope.chickenWeightData.yName,
											 $scope.chickenWeightData.yColor,
											 $scope.chickenWeightData.hiddenPara,
											 $scope.chickenWeightData.selectUnit,
											 '',
											 function (params){
												var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value +'g';
									            }
												return    res;
											 }
						   );*/
						   
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	
	  

	setLandscape(true,true);
	$scope.changes1();



	  /*
	  $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='蛋重'){
        	 console.log("这是changes2方法");
             document.getElementById("chang").innerHTML = '产蛋率';
             document.getElementById("cullSurTi").innerHTML = '蛋重曲线';
             return $scope.changes2();
        }else if (dd=='产蛋率'){
        	 console.log("这是changes1方法");
        	 document.getElementById("chang").innerHTML = '蛋重';
        	 document.getElementById("cullSurTi").innerHTML = '产蛋率曲线';
       	     return $scope.changes1();
        }
    }
    //切换查看方式
    $scope.changesCompareType = function(){
    	var dd = document.getElementById("chang").innerHTML;
	        if(dd=='产蛋率'){
	             return $scope.changes2();
	        }else if (dd=='蛋重'){
	        	 return $scope.changes1();
	        }
	};*/
})

//生产指标汇总
.controller("productionQuotaCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setTimeout(
		function (){
			app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	app_lockOrientation('portrait');//出去时竖屏
    	setTimeout(
			function (){
				$state.go("dataAnalyseTable");
			}
		,1000); 
		
    }

    $scope.prodQuotData = {
    	// "HouseId"      :  $scope.sparraw_user_temp.userinfo.houses[0].HouseId,
    	"farmId"       :  $scope.sparraw_user_temp.farminfo.id,
    	"farmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        "batchTable"   :  "",
        "selectedBatch":  "",
        "houseTable"   :  [],
        "selectedHouse":  "",
        "compareType"  :  "",//"01"-栋舍对比；"02"-批次对比
        "Headers"      :  [],
        "TableDatas"   :  [],      
    }
    
    $scope.gridOptions = {
	   
	};
	
    $scope.drawTable = function(){
    	$scope.gridOptions.columnDefs = [];
    	for (var i = 1; i <= $scope.prodQuotData.Headers.length; i++) {
    		if (i != 1) {
    			$scope.gridOptions.columnDefs.push({name:"Index" + i ,  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false});
    		}else{
    			$scope.gridOptions.columnDefs.push({name:"Index" + i ,  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false,pinnedLeft:true});
    		};
    	};
		$scope.gridOptions.data = $scope.prodQuotData.TableDatas;
    }
  
    $scope.changesBatch = function(){//按批次
    	var params = {
			"FarmId"       :  $scope.prodQuotData.farmId         ,
			"CompareType"  :  $scope.prodQuotData.compareType    ,
			"FarmBreedId"  :  $scope.prodQuotData.selectedBatch  ,
			"HouseId"      :  JSON.parse($scope.prodQuotData.selectedHouse).HouseId
		};
		console.log(params);
		Sparraw.ajaxPost('rep/ProSUM/SUMReq.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.prodQuotData.Headers = data.ResponseDetail.Headers;
    			$scope.prodQuotData.TableDatas = data.ResponseDetail.TableDatas;
				$scope.drawTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }

    $scope.changesHouses = function(){//按栋舍
    	var params = {
			"FarmId"       :  $scope.prodQuotData.farmId       ,
			"CompareType"  :  $scope.prodQuotData.compareType  ,
			"FarmBreedId"  :  $scope.prodQuotData.selectedBatch  ,
			"HouseId"      :  JSON.parse($scope.prodQuotData.selectedHouse).HouseId
		};
		Sparraw.ajaxPost('rep/ProSUM/SUMReq.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.prodQuotData.Headers = data.ResponseDetail.Headers;
    			$scope.prodQuotData.TableDatas = data.ResponseDetail.TableDatas;
				$scope.drawTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    $scope.prodQuotData.compareType = "01";//默认批次对比
    $scope.changesCompareType = function(){
    	if ($scope.prodQuotData.compareType == "01") {
    		$scope.batchDiv = true;
			$scope.housesDiv = false;

    		return $scope.changesBatch();//选择批次号
    	}else{
    		$scope.batchDiv = false;
			$scope.housesDiv = true;

    		return $scope.changesHouses();//选择栋舍号
    	};
    }

    


    //获取批次列表与栋舍列表
    $scope.getTableData = function(){
    	for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			$scope.prodQuotData.houseTable.push($scope.sparraw_user_temp.userinfo.houses[i]);
		};
		$scope.prodQuotData.selectedHouse = JSON.stringify($scope.prodQuotData.houseTable[0]);
    	var params = {
			"FarmId"     : $scope.prodQuotData.farmId
		};
		Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
	            $scope.prodQuotData.batchTable = data.ResponseDetail.FarmBreedIdArray;
				//获取key
				for(var key in $scope.prodQuotData.batchTable){
				    $scope.prodQuotData.selectedBatch = key;
				}
				//获取value  
				 for(var item in $scope.prodQuotData.batchTable){  
			        if(item==key){  
			            $scope.prodQuotData.selectedBatchVal = $scope.prodQuotData.batchTable[item];
			        }  
			    }
			    // $scope.changesCompareType();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			$scope.changesCompareType();
		});
    }

    $scope.getTableData();
    
})

//任务提醒
.controller("taskRemindCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $ionicPopup, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.taskRemindData = {
		"selectHouse" :  ""     ,
		"UnCompleteTaskNum":  "0"  ,//未完成任务个数
		"delayCount"	   :  "0"  ,//延迟任务个数
		"cancleCount"      :  "0"  ,//完成任务个数
		"AgeBegin"            :  ""  ,  //开始日龄
        "AgeEnd"              :  ""  ,	//结束日龄
        "TaskTimeSlot"        :  "0"  ,	//任务时间段
		"TskInfo"  :[{
			"TskGrpName":"",
			"curAgeFlag":"",
			"TaskDetail":[{
				"TskSN":"",
				"TaskName":"",
				"dealStatus":""
			}]
		}]
	};


	
	//默认选择第一栋
	$scope.taskRemindData.selectHouse = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]);
	//设置默认值自动查询
    $scope.taskRemindData.AgeBegin   =  "0"    ;
    $scope.taskRemindData.AgeEnd     =  "0"    ;
	$scope.judgeHouse = function(IsFirstTime){
		//判断农场状态
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01") {
			$scope.sparraw_user_temp.farminfo.farmBreedBatchId = 0;
		}else{

		};

		var required = [$scope.taskRemindData.AgeBegin,$scope.taskRemindData.AgeEnd];
	   	if (parseInt(required[0]) > parseInt(required[1])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };

	    if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {

			switch($scope.taskRemindData.TaskTimeSlot){
				case "0":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "0";
				  break;
				case "1":
					$scope.taskRemindData.AgeBegin="-1";
				  	$scope.taskRemindData.AgeEnd = "-1";
				  break;
				case "2":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "7";
				  break;
				case "3":
					$scope.taskRemindData.AgeBegin="8";
				  	$scope.taskRemindData.AgeEnd = "14";
				  break;
				case "4":
					$scope.taskRemindData.AgeBegin="15";
					$scope.taskRemindData.AgeEnd = "21";
				  break;
				case "5":
					$scope.taskRemindData.AgeBegin="22";
				  	$scope.taskRemindData.AgeEnd = "28";
				  break;
				case "6":
					$scope.taskRemindData.AgeBegin="29";
				  	$scope.taskRemindData.AgeEnd = "35";
				  break;
				case "7":
					$scope.taskRemindData.AgeBegin="36";
				  	$scope.taskRemindData.AgeEnd = "42";
				  break;
				case "8":
					$scope.taskRemindData.AgeBegin="43";
					$scope.taskRemindData.AgeEnd = "49";
				  break;
				case "9":
					$scope.taskRemindData.AgeBegin="50";
					$scope.taskRemindData.AgeEnd = "56";
				  break;
				case "10":
					$scope.taskRemindData.AgeBegin="57";
					$scope.taskRemindData.AgeEnd = "63";
				  break;
				default:
					$scope.taskRemindData.AgeBegin="64";
					$scope.taskRemindData.AgeEnd = "71";
					
	};
		};



		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"AgeBegin"     :  $scope.taskRemindData.AgeBegin                        ,
   			"AgeEnd"       :  $scope.taskRemindData.AgeEnd
		};
		Sparraw.ajaxPost('tsk/ActualTask/query.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskRemindData.TskInfo = data.ResponseDetail.TskInfo;
				$scope.taskRemindData.UnCompleteTaskNum = data.ResponseDetail.UnCompleteTaskNum;
				$scope.taskRemindData.delayCount = data.ResponseDetail.delayCount;
				$scope.taskRemindData.cancleCount = data.ResponseDetail.cancleCount;
			}else if (data.ResponseDetail.Result == "Fail"){
				$scope.taskRemindData.TskInfo = [];
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {
				$ionicSideMenuDelegate.toggleRight();
			};
		});
	}
	$scope.judgeHouse("firstTime");
	
	$scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};
	
	$scope.cancel = function(){
		$ionicSideMenuDelegate.toggleRight();
	}

	//判断日龄
	$scope.judgeDaysAge = function(item){
		if (item.curAgeFlag == "Y") {
			return "{background:'#71C671'}";
		}else{
			return "{background:'#AAAAAA'}";
		}
	};

	//判断任务状态
	$scope.judgeStateUpdateColor = function(sku){
		if (sku.UpdateFlag=='N' || $scope.sparraw_user_temp.Authority.TaskDeal == 'Read') {

		}else{
			if (sku.dealStatus == "01" || sku.dealStatus == "03") {
				return "{color:'#666666'}";
			}else if (sku.dealStatus == "02" || sku.dealStatus == "00"){
				return "{color:'red'}";
			}else {
				return "{color:'#2f7fff'}";
			}
		};
	}


	//判断权限
	if ($scope.sparraw_user_temp.Authority.TaskSetting === "All") {
		$scope.setBtn = false;
	}else{
		$scope.setBtn = true;
	};



	

	//任务状态选择
	$scope.judgeTaskState = function(choice,sku){
		console.log($scope.sparraw_user_temp.Authority);

		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"TskSN"        :  sku.TskSN                                                    ,
   			"TaskName"     :  sku.TaskName,
   			"dealStatus"   :  choice
		};
		Sparraw.ajaxPost('tsk/ActualTask/deal.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.judgeHouse();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});


	};
})

//任务设定
.controller("taskSetCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.TaskType = ["01","02","03","04","05"];

	$scope.goTaskTable = function(){
		$state.go("taskTable");
	}
})
//任务列表
.controller("taskTableCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//用来判断从哪个入口进入以及标题的显示
	$scope.TaskType = $stateParams.TaskType;
	
	
	$scope.taskTableData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别
		"TaskType"      :  ""                                    ,//用来判断从服务器获取什么类型的任务（任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务）
		"conveyTskID"   :  ""                                    ,

		"TaskDetail"    : [{
			"TskID"         :  ""                                    ,//任务id
			"TaskName"      :  ""                                    ,//任务名称
			"ageInfos"      :  ""                                    ,//任务日期
			"TaskStatus"    :  ""                                     //任务状态Y有效、 N无效（任务状态是指饲养员是否可见Y可见、N隐藏）
		}]
		
	}
	//清空该值
	persistentData.overallTaskId     =  ""  ;
	persistentData.overallTaskState  =  ""  ;



	switch($scope.TaskType){
				case "01":
				  	$scope.taskTableData.showTaskType = "入雏前准备";
				  	$scope.taskTableData.TaskType     = "01";
				  break;
				case "02":
				  	$scope.taskTableData.showTaskType = "入雏后日常工作";
				  	$scope.taskTableData.TaskType     = "02";
				  break;
				case "03":
				  	$scope.taskTableData.showTaskType = "光照程序";
				  	$scope.taskTableData.TaskType     = "03";
				  break;
				case "04":
				  	$scope.taskTableData.showTaskType = "免疫用药程序";
				  	$scope.taskTableData.TaskType     = "04";
				  break;
				default:
					$scope.taskTableData.showTaskType = "其他临时任务";
					$scope.taskTableData.TaskType     = "05";
	};



	$scope.inquire = function(){
		var params = {
			"FarmId"     :    $scope.taskTableData.FarmId      ,
			"TaskType"   :    $scope.taskTableData.TaskType    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskList.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskTableData.TaskDetail = data.ResponseDetail.TaskDetail;
			}else if (data.ResponseDetail.Result == "Fail") {
				if ($scope.taskTableData.TaskDetail.length == 1) {
					$scope.taskTable = true;
				};
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.obtainTskId = function(item){
		$scope.taskTableData.conveyTskID = item.TskID;
	}



})
//新增任务
.controller("addTaskCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.TaskType = $stateParams.TaskType;

	$scope.addTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  ""                                    ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）默认启用
	}

	switch($scope.TaskType){
				case "01":
				  	$scope.addTaskData.showTaskType = "入雏前准备";
				  	$scope.addTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.addTaskData.showTaskType = "入雏后日常工作";
				  	$scope.addTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.addTaskData.showTaskType = "光照程序";
				  	$scope.addTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.addTaskData.showTaskType = "免疫用药程序";
				  	$scope.addTaskData.TaskType = "04";
				  break;
				default:
					$scope.addTaskData.showTaskType = "其他临时任务";
					$scope.addTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.addTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.addTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};

	

	$scope.save = function(){
		$scope.addTaskData.AgeInfos = $scope.addTaskData.AgeInfos.trim();
		//判断是否为空
		if ((isNull($scope.addTaskData.TaskName)) 
			&& (isNull($scope.addTaskData.AgeInfos)) 
			&& (isNull($scope.addTaskData.TaskStatus))) {
		}else{
			return;
		};

		var tempAgeInfos = "";
		if($scope.addTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.addTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!app_IsNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.addTaskData.AgeInfos = tempAgeInfos.substring(1);

		var params = {
			"FarmId"  :    $scope.addTaskData.FarmId      ,
			"TaskName":    $scope.addTaskData.TaskName    ,
          	"TaskType":    $scope.addTaskData.TaskType    ,
          	"AgeInfos":    $scope.addTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.addTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/addTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("添加任务成功！");
				persistentData.overallTaskId     =  data.ResponseDetail.TskId    ;
				persistentData.overallTaskState  =  $scope.addTaskData.TaskType  ;
				console.log(persistentData.overallTaskState);
				$state.go("updateTask");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}



	



})
//查看任务
.controller("updateTaskCtrl",function($scope, $state, $http,  $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.saveBtn = true;
	$scope.cancelBtn = true;
	$scope.sheerDiv = false;
	$scope.backBtn = false;
	$scope.visible = false;

	$scope.updateTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  $stateParams.receiveTskID             ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）
	}

	//判断是从新建任务进入的，还是从任务列表进入的
	if (persistentData.overallTaskId === "") {
		$scope.TaskType = $stateParams.TaskType;
	}else{
		$scope.updateTaskData.TskID  =  persistentData.overallTaskId     ;
		$scope.TaskType              =  persistentData.overallTaskState  ;
	};

	

	$scope.startEdit = function(){
		$scope.saveBtn = false;
		$scope.cancelBtn = false;
		$scope.sheerDiv = true;
		$scope.backBtn = true;
		$scope.visible = true;
	};
	$scope.cancelEvent = function(){
		$scope.saveBtn = true;
		$scope.cancelBtn = true;
		$scope.sheerDiv = false;
		$scope.backBtn = false;
		$scope.visible = false;
	};

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	switch($scope.TaskType){
				case "01":
				  	$scope.updateTaskData.showTaskType = "入雏前准备";
				  	$scope.updateTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.updateTaskData.showTaskType = "入雏后日常工作";
				  	$scope.updateTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.updateTaskData.showTaskType = "光照程序";
				  	$scope.updateTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.updateTaskData.showTaskType = "免疫用药程序";
				  	$scope.updateTaskData.TaskType = "04";
				  break;
				default:
					$scope.updateTaskData.showTaskType = "其他临时任务";
					$scope.updateTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.updateTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.updateTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};


	$scope.inquire = function(){
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TskId"   :    $scope.updateTaskData.TskID    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskDetail.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.updateTaskData.TaskName = data.ResponseDetail.TaskName;
				$scope.updateTaskData.AgeInfos = data.ResponseDetail.AgeInfos;
				$scope.updateTaskData.TaskStatus = data.ResponseDetail.TaskStatus;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();

	$scope.save = function(){
		$scope.updateTaskData.AgeInfos = $scope.updateTaskData.AgeInfos.trim();
		//判断是否为空
		if ((isNull($scope.updateTaskData.TaskName)) 
			&& (isNull($scope.updateTaskData.AgeInfos)) 
			&& (isNull($scope.updateTaskData.TaskStatus))) {
			
		}else{
			return;
		};
		var tempAgeInfos = "";
		if($scope.updateTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.updateTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!app_IsNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.updateTaskData.AgeInfos = tempAgeInfos.substring(1);
		
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TaskName":    $scope.updateTaskData.TaskName    ,
			"TskId"   :    $scope.updateTaskData.TskID       ,
          	"TaskType":    $scope.updateTaskData.TaskType    ,
          	"AgeInfos":    $scope.updateTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.updateTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/updateTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("修改任务成功！");
				$scope.cancelEvent();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}








})


//销售日报
.controller("eggSellsDataCtrl",function($scope, $state, $ionicLoading,$stateParams, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var farmId = $scope.sparraw_user_temp.farminfo.id;
	var farmFeedId = $scope.sparraw_user_temp.farminfo.farmBreedBatchId;
	
	setPortrait(true,true);

	//销售金额结算
	$scope.doBack = function(){
		if($stateParams.fromPage == "eggSellsReportTable"){
			$state.go("eggSellsReportTable");
		}else if($stateParams.fromPage == "eggSellsReport"){
			$state.go("eggSellsReport");
		}else{
			$state.go("eggSellsReportTable");
		}
	};
	
	
	if(farmId == 0){
		Sparraw.myNotice("暂无数据，请先新建农场");
		return;
	}
	
	if(farmFeedId == 0){
		Sparraw.myNotice("暂无数据，请先新建批次");
		return;
	}
	
	
	var date = new Date();

	
	console.log(date.getFullYear() + " " + (date.getMonth() + 1) + ", farmId:" + farmId + ",farmFeedId:" + farmFeedId);
	


 	var todayDate = getShortDate(date);
	
	

 	var months = [];

	var currYear = date.getFullYear();
 	var currMonth = (date.getMonth() + 1);


 	for(var i = currMonth; i > currMonth - 6; i--){

 		if(i <= 0){
 			var lastMonth = 12 + i;
 			var lastYear = (currYear - 1);
			if( lastMonth > 9){
				months.push(lastYear + "-" + lastMonth);
 			}else{
 				months.push(lastYear + "-0" + lastMonth);
 			}

 		}else{
 			if( i > 9){
				months.push(currYear + "-" + i);
 			}else{
 				months.push(currYear + "-0" + i);
 			}
 			
 		}
 	}

	console.log("months:" + months);
	
	var today = currMonth + "月" + date.getDate() + "日";
	// 0 周日 6 周六
	var weekday = date.getDay();
	if(weekday == 0){
		today += ",周日";
	}else if(weekday == 1){
		today += ",周一";
	}else if(weekday == 2){
		today += ",周二";
	}else if(weekday == 3){
		today += ",周三";
	}else if(weekday == 4){
		today += ",周四";
	}else if(weekday == 5){
		today += ",周五";
	}else if(weekday == 6){
		today += ",周六";
	}

	// 合格鸡蛋总重量
	var total_Weight_good = 0;
	var total_Weight_bad = 0;
	// 合格鸡蛋总金额
	var total_Price_sum_good = 0;
	var total_Price_sum_bad = 0;

	// 合格鸡蛋均价
	var total_Price_p_good = 0;
	var total_Price_p_bad = 0;


	// 本批次合格鸡蛋总重量
	var total_Weight_good_breed = 0;
	var total_Weight_bad_breed = 0;
	// 本批次合格鸡蛋总金额
	var total_Price_good_breed = 0;
	var total_Price_bad_breed = 0;

	var total_Price_p_good_breed = 0;
	var total_Price_p_bad_breed = 0;


	$scope.farmData = {
		"FarmId"          :  farmId                ,//int型，农场id
		"BreedBatchId"    :  farmFeedId  ,
		"farmBreedStatus" :  $scope.sparraw_user_temp.farminfo.farmBreedStatus   ,  //农场批次状态
		"BatchCode"       :  ""  ,//varchar型，批次编号
		"place_date"      :  todayDate ,//varchar型，入雏日期
		"place_day_age"   :  ""  ,//int型，入舍日龄
		"place_week_age"  :  ""  ,//int型，入雏周龄
		//"place_num"       :  ""  ,//int型，入雏数量
		"place_detail"    :  []  ,//各栋舍入舍数量
		"Place_Sum"       :  "" ,//计算合计的入雏总量
		"months"		  : months,// 半年内月份 ["2016-01","2016-02","2016-03","2016-04"]
		"selectMonth"	  : todayDate,//
		"dataInput"		  :[],//
		"total_Weight_good":total_Weight_good,
		"total_Weight_bad":total_Weight_bad,
		"total_Price_sum_good":total_Price_sum_good,
		"total_Price_sum_bad":total_Price_sum_bad,
		"total_Price_p_good":total_Price_p_good,
		"total_Price_p_bad":total_Price_p_bad,
		"total_Weight_good_breed":total_Weight_good_breed,
		"total_Weight_bad_breed":total_Weight_bad_breed,
		"total_Price_good_breed":total_Price_good_breed,
		"total_Price_bad_breed":total_Price_bad_breed,
		"total_Price_p_good_breed":total_Price_p_good_breed,
		"total_Price_p_bad_breed":total_Price_p_bad_breed,
		"temp":1,
		"today":today,
	}


	var modifyData = [];

 	//销售金额结算
	$scope.forageTotal = function(sell_date){
		
	};




	$scope.inquire = function(selectMonth){
    	console.log("farmId:" + farmId + "," + "selectMonth:" + selectMonth);
    	

		if (farmId == "") {
			//Sparraw.myNotice("暂无数据，请先登陆");
			console.log("暂无数据，请先登陆");
		}else{
			var params = {
		      	"FarmId"  :  farmId  ,
		      	"FarmBreedId"  :  farmFeedId  ,
				"SelectMonth"  :  selectMonth
			};

			console.log(params);
			
			Sparraw.ajaxPost('layer_salesInput/reportTable.action', params, function(data){
				console.log(data);
		   		if (data.ResponseDetail.Result == "Success") {
					$scope.farmData.dataInput = data.ResponseDetail.saleDetails;
					
					for(var i = 0 ; i < $scope.farmData.dataInput.length;i ++){
						var sell = $scope.farmData.dataInput[i];
						// 单价
						var goodPriceValue = sell.good_price_value;
						// 每箱重量 规格
						var good_box_size = sell.good_box_size;
						// 销售重量
						var good_sale_weight = sell.good_sale_weight;
						// 箱数
						var goodSaleBoxNum = sell.good_salebox_num;
						// 换算为元/斤
						var goodPriceKil = 0;
						// 换算为元/箱
						var goodPriceBox = 0;
						// 销售金额
						var saleMoney = 0;
						// 以箱为单位
						if(sell.good_price_type == "01"){
							if(good_box_size > 0){
								goodPriceKil = goodPriceValue/good_box_size;
							}
							goodPriceBox = goodPriceValue;
							
							saleMoney = goodPriceValue*goodSaleBoxNum;
						}else{
							goodPriceKil = goodPriceValue;
							goodPriceBox = 0;
							saleMoney = goodPriceKil*good_sale_weight;
						}
						// 元/斤
						sell.good_price_kil = (goodPriceKil/2).toFixed(2);
						// 元/箱
						sell.good_price_box = (goodPriceBox);
						// 合格蛋金额
						sell.good_sale_money = (saleMoney).toFixed(0);
						
						
						// 单价
						var broken_price_value = sell.broken_price_value;
						// 每箱重量 规格
						var broken_box_size = sell.broken_box_size;
						// 销售重量
						var broken_sale_weight = sell.broken_sale_weight;
						// 箱数
						var broken_salebox_num = sell.broken_salebox_num;
						// 换算为元/斤
						var badPriceKil = 0;
						// 换算为元/箱
						var badPriceBox = 0;
						// 销售金额
						var badSaleMoney = 0;
						// 以箱为单位
						if(sell.broken_price_type == "01"){
							if(broken_box_size > 0){
								badPriceKil = broken_price_value/broken_box_size;
							}
							badPriceBox = broken_price_value;
							
							badSaleMoney = broken_price_value*broken_salebox_num;
						}else{
							badPriceKil = broken_price_value;
							badPriceBox = 0;
							badSaleMoney = badPriceKil*broken_sale_weight;
						}
						
						
						// 元/斤
						sell.bad_price_kil = (badPriceKil/2).toFixed(0);
						// 元/箱
						sell.bad_price_box = (badPriceBox);
						// 非合格蛋金额
						sell.bad_sale_money = (badSaleMoney).toFixed(0);
						
						
						
						// 重量换算为斤
						sell.good_sale_weight = (good_sale_weight*2).toFixed(0);
						
						
					}

					/**
					$scope.forageTotal();
					
					
					$scope.farmData.total_Weight_good_breed = data.ResponseDetail.accgood_sale_weight;
					$scope.farmData.total_Weight_bad_breed = data.ResponseDetail.accbroken_sale_weight;
					$scope.farmData.total_Price_good_breed = data.ResponseDetail.accgood_sale_money;
					$scope.farmData.total_Price_bad_breed = data.ResponseDetail.accbroken_sale_money;

					total_Price_p_good_breed = data.ResponseDetail.accgood_sale_money / data.ResponseDetail.accgood_sale_weight;
					total_Price_p_bad_breed = data.ResponseDetail.accbroken_sale_money / data.ResponseDetail.accbroken_sale_weight;

					$scope.farmData.total_Price_p_good_breed = total_Price_p_good_breed.toFixed(2);
					$scope.farmData.total_Price_p_bad_breed = total_Price_p_bad_breed.toFixed(2);
					**/
					
				}else if (data.ResponseDetail.Result == "Fail"){
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});

		}
	}

  	$scope.hiddenEmptyData = function(obj){
		// 0 周日 6 周六
		var date = new Date(obj);
		var weekday = date.getDay();
		//console.log("date:" + obj + ",weekday:" + date.getDay());
		
  		var day = weekday;//parseInt(obj.substring(4));

		//每隔七天添加下划线,如果第一天就是周六，就不用划线
		if (day == 6 && date.getDate() > 1) {
			//console.log(day);
    		return "{'border-bottom':'solid 1px #606060'}";
    	}else{
    		return "{'border-bottom':'solid 1px #D0D0D0'}";
    	}
    	
    }

	$scope.chooseDiv = false;
	$scope.getFocus = function(){
		//$scope.chooseDiv = true;
		//document.getElementById('blankDiv').style.height = 3 + 'rem';
	}

	$scope.loseBlur = function(item,judgeType){
		//$scope.chooseDiv = false;
		//document.getElementById('blankDiv').style.height = '6rem';
	}

    $scope.changeMonth = function(val){

    	var selectMonth = $scope.farmData.selectMonth;
    	console.log("selectMonth:" + selectMonth);
    	//var tempData  =  JSON.parse($scope.farmData.selectMonth);
		//console.log(tempData);
		
		modifyData = [];

		// 合格鸡蛋总重量
		 total_Weight_good = 0;
		 total_Weight_bad = 0;
		// 合格鸡蛋总金额
		 total_Price_sum_good = 0;
		 total_Price_sum_bad = 0;

		 total_Price_p_good = 0;
		 total_Price_p_bad = 0;

		$scope.farmData.total_Weight_good = total_Weight_good;
		$scope.farmData.total_Weight_bad = total_Weight_bad;
		$scope.farmData.total_Price_sum_good = total_Price_sum_good;
		$scope.farmData.total_Price_sum_bad = total_Price_sum_bad;
		$scope.farmData.total_Price_p_good = total_Price_p_good;
		$scope.farmData.total_Price_p_bad = total_Price_p_bad;

		$scope.inquire(selectMonth);
		// 测试用
		//$scope.getBreedSum();
    };

 	

 	setTimeout(
		function (){
			$scope.changeMonth();
		}
	,1000);


	$scope.save = function(){

		if(modifyData.length > 0) {
			var selectMonth = $scope.farmData.selectMonth;
			console.log("modifyData:" + JSON.stringify(modifyData));

			var params = {
			      	"FarmId"  :  farmId  ,
					"FarmBreedId"  :  farmFeedId  ,
					"saleDetails"    :  modifyData
				};

				Sparraw.ajaxPost('layer_salesInput/saveDR.action', params, function(data){
			   		if (data.ResponseDetail.Result == "Success") {
						modifyData = [];
						$scope.getBreedSum();
						Sparraw.myNotice("保存成功");
						
					}else if (data.ResponseDetail.Result == "Fail"){
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
				});

			
		}else{
			Sparraw.myNotice("未修改任何数据");
		}
		

	}
	
	
	$scope.doShowSalesAnalyze = function(){
		$state.go("salesAnalyze");
		
	}
	


	//日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();

    $scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '关闭', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2012, 12, 31), //Optional
            to: new Date(2020, 12, 31), //Optional
            callback: function (val) { //Optional
                datePickerCallbackPopup(val);
            }
        };

    var datePickerCallbackPopup = function (val) {
    		//重新选择日期清空入雏日期、批次编号、预计饲养天数、预计出栏日期

			console.log(val);

			//$scope.farmData.place_date = "";

            if (typeof(val) === 'undefined') {
                console.log('未选择日期');
            } else {
                $scope.datepickerObjectPopup.inputDate = val;
                $scope.datePopup = val;
                //转为字符串并且删除“日”字符串，修改“年”“月”“/”为“-”,并且做补零处理                
                var selectDate = val.toLocaleDateString();
                /*
                selectDate     = selectDate.replace(/(日)/g,"");
                selectDate     = selectDate.replace(/(月)/g,"-");
                selectDate     = selectDate.replace(/(年)/g,"-");
                selectDate     = selectDate.replace(/\//g,"-");
                if (selectDate[6] == "-") {
                	selectDate = selectDate.replace(/(.{5})/g,'$10');
                };
                if (selectDate[9]) {

                }else{
                	selectDate = selectDate.replace(/(.{8})/g,'$10');
                };
*/
                var TempDate = new Date(selectDate);

                console.log(selectDate);

				selectDate = getShortDate(TempDate);

                //将得到的日期放入入雏日期
                $scope.farmData.place_date = selectDate;
               
            }
    };
})


//销售记录
.controller("eggSellsDataCtrlV2",function($scope, $state, $ionicLoading, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var farmId = $scope.sparraw_user_temp.farminfo.id;
	var farmFeedId = $scope.sparraw_user_temp.farminfo.farmBreedBatchId;

	if(farmId == 0){
		Sparraw.myNotice("暂无数据，请先新建农场");
		return;
	}
	
	if(farmFeedId == 0){
		Sparraw.myNotice("暂无数据，请先新建批次");
		return;
	}
	
	
	var date = new Date();

	console.log("farmId:" + farmId + ",farmFeedId:" + farmFeedId);
	
	var today = (date.getMonth() + 1) + "月" + date.getDate() + "日";
	// 0 周日 6 周六
	var weekday = date.getDay();
	if(weekday == 0){
		today += ",周日";
	}else if(weekday == 1){
		today += ",周一";
	}else if(weekday == 2){
		today += ",周二";
	}else if(weekday == 3){
		today += ",周三";
	}else if(weekday == 4){
		today += ",周四";
	}else if(weekday == 5){
		today += ",周五";
	}else if(weekday == 6){
		today += ",周六";
	}
	
	var guige = {};
	for(var i = 25; i <= 34; i++){
		var obj = new Object();
		var key = i + 0.5;
		var val = i + "~"+ (i+1);
		guige[""+key+""] = val;
		//guige.push(obj);
	}
	console.log("guige:" + (guige))

	// 合格鸡蛋总重量
	var good_sale_weight = 0;
	// 合格鸡蛋总箱数
	var good_salebox_num = 0;
	// 合格鸡蛋单价
	var good_price_value = 0;
	// 合格鸡蛋每箱规格
	var good_box_size = 0;
	// 合格鸡蛋总金额
	var good_sale_money = 0;
	
	
	// 破损鸡蛋总重量
	var broken_sale_weight = 0;
	// 破损鸡蛋总箱数
	var broken_salebox_num = 0;
	// 破损鸡蛋单价
	var broken_price_value = 0;
	// 破损鸡蛋每箱规格
	var broken_box_size = 0;
	// 破损鸡蛋总金额
	var broken_sale_money = 0;

	// 鸡粪收入
	var chicken_manure = 0;
	
	var curDate = getCurDate();

	var key = "defaultGuige";
	var defaultValue = getValueByKey(key);
	console.log("defaultValue:"+defaultValue);
	if(defaultValue == null || defaultValue == undefined){
		defaultValue = 25.5;
	}

	$scope.farmData = {
		"FarmId"          :  farmId                ,//int型，农场id
		"BreedBatchId"    :  farmFeedId  ,
		"selectDate"	  : curDate,//
		"guige":guige,
		"defaultValue":defaultValue,
		"checked":true,
		"sellData"	  :{
			"good_sale_weight":good_sale_weight,
			"good_salebox_num":good_salebox_num,
			"good_price_value":good_price_value,
			"good_box_size":good_box_size,
			"good_price_type":"01",
			"good_sale_money":good_sale_money,
			"broken_box_size":broken_box_size,
			"broken_price_value":broken_price_value,
			"broken_salebox_num":broken_salebox_num,
			"broken_sale_weight":broken_sale_weight,
			"broken_sale_money":broken_sale_money,
			"broken_price_type":"01",
			"chicken_manure":chicken_manure
			},
		"temp":1,
		"today":today,


		"TempGood_salebox_num":"",
		"TempGood_box_size":"",
		"TempGood_price_value":"",
		"TempGood_sale_money":"",
		"TempChecked":"",
		"TempBroken_salebox_num":"",
		"TempBroken_price_value":"",
		"TempBroken_sale_money":"",
		"TempChicken_manure":""
	}

	$scope.selectGuige = function(){
		console.log($scope.farmData.sellData.good_box_size);
		defaultValue = $scope.farmData.sellData.good_box_size;
		$scope.farmData.sellData.broken_box_size = $scope.farmData.sellData.good_box_size;
		saveValueToStorage(key,$scope.farmData.sellData.good_box_size);
		
		good_sale_weight = $scope.farmData.sellData.good_salebox_num * $scope.farmData.sellData.good_box_size;
		broken_sale_weight = $scope.farmData.sellData.broken_salebox_num * $scope.farmData.sellData.good_box_size;
		
		//console.log("good_sale_weight b:" + good_sale_weight);
		//console.log("broken_sale_weight b:" + broken_sale_weight);
		
		// 重量转为公斤
		$scope.farmData.sellData.good_sale_weight = (good_sale_weight/2).toFixed(2);
		$scope.farmData.sellData.broken_sale_weight = (broken_sale_weight/2).toFixed(2);
	}
	
	$scope.pushNotificationChange = function(){
		if($scope.farmData.checked == true){
			//console.log("true");
			document.getElementById('brokenEggsDIV').style.display = '';
		}else{
			//console.log("false");
			document.getElementById('brokenEggsDIV').style.display = 'none';
		}
	}
	
	
 	//销售金额结算
	$scope.forageTotal = function(inputType){

		good_sale_weight = 0;
		broken_sale_weight = 0;
		
		// 合格鸡蛋总金额
		good_sale_money = 0;
		// 破损蛋总金额
		broken_sale_money = 0;

		
		if (!app_IsNum($scope.farmData.sellData.good_salebox_num)) {
			$scope.farmData.sellData.good_salebox_num = 0;
		}
		if (!app_IsNum($scope.farmData.sellData.good_price_value)) {
			$scope.farmData.sellData.good_price_value = 0;
		}
		
		
		
		if (!app_IsNum($scope.farmData.sellData.broken_salebox_num)) {
			$scope.farmData.sellData.broken_salebox_num = 0;
		}
		if (!app_IsNum($scope.farmData.sellData.broken_price_value)) {
			$scope.farmData.sellData.broken_price_value = 0;
		}
		
		
		if (!app_IsNum($scope.farmData.sellData.chicken_manure)) {
			$scope.farmData.sellData.chicken_manure = 0;
		}
		
		
		good_sale_money = accMul($scope.farmData.sellData.good_salebox_num , $scope.farmData.sellData.good_price_value);
		
		
		
		broken_sale_money = accMul($scope.farmData.sellData.broken_salebox_num , $scope.farmData.sellData.broken_price_value);
		
		
		$scope.farmData.sellData.good_sale_money = good_sale_money;
		$scope.farmData.sellData.broken_sale_money = broken_sale_money;
		
		good_sale_weight = $scope.farmData.sellData.good_salebox_num * $scope.farmData.sellData.good_box_size;
		broken_sale_weight = $scope.farmData.sellData.broken_salebox_num * $scope.farmData.sellData.good_box_size;
		
		//console.log("good_sale_weight a:" + good_sale_weight);
		//console.log("broken_sale_weight a:" + broken_sale_weight);
		
		// 重量转为公斤
		$scope.farmData.sellData.good_sale_weight = (good_sale_weight/2).toFixed(2);
		$scope.farmData.sellData.broken_sale_weight = (broken_sale_weight/2).toFixed(2);

	};



	$scope.inquire = function(selectDate){
    	console.log("farmId:" + farmId + "," + "selectDate:" + selectDate);
    	

		if (farmId == "") {
			//Sparraw.myNotice("暂无数据，请先登陆");
			console.log("暂无数据，请先登陆");
		}else{
			var params = {
		      	"FarmId"  :  farmId  ,
		      	"FarmBreedId"  :  farmFeedId  ,
				"SelectDate"  :  selectDate
			};

			console.log(params);
			
			Sparraw.ajaxPost('layer_salesInput/queryDRByDate.action', params, function(data){
				console.log(data);
		   		if (data.ResponseDetail.Result == "Success") {
					//$scope.farmData.dataInput = data.ResponseDetail.saleDetails;
					console.log("data size :" + data.ResponseDetail.saleDetails.length);
					if(data.ResponseDetail.saleDetails.length > 0) {
						var sellData = data.ResponseDetail.saleDetails[0];
						console.log("sellData:" + (JSON.stringify(sellData)));
						
						$scope.farmData.sellData = sellData;
						
						
						var good_box_size = sellData.good_box_size;
						if(good_box_size == 0){
							good_box_size = defaultValue/2;
						}
						var good_price_value = sellData.good_price_value;
						var broken_box_size = sellData.broken_box_size;
						if(broken_box_size == 0){
							broken_box_size = defaultValue/2;
						}
						var broken_price_value = sellData.broken_price_value;
						
						
						$scope.farmData.sellData.good_box_size = good_box_size*2;
						$scope.farmData.sellData.good_price_value = (good_price_value);
						$scope.farmData.sellData.broken_box_size = broken_box_size*2;
						$scope.farmData.sellData.broken_price_value = (broken_price_value);
						



						$scope.farmData.TempGood_salebox_num = $scope.farmData.sellData.good_salebox_num;
						$scope.farmData.TempGood_box_size = $scope.farmData.sellData.good_box_size;
						$scope.farmData.TempGood_price_value = $scope.farmData.sellData.good_price_value;
						$scope.farmData.TempGood_sale_money = $scope.farmData.sellData.good_sale_money;
						$scope.farmData.TempChecked = $scope.farmData.checked;
						$scope.farmData.TempBroken_salebox_num = $scope.farmData.sellData.broken_salebox_num;
						$scope.farmData.TempBroken_price_value = $scope.farmData.sellData.broken_price_value;
						$scope.farmData.TempBroken_sale_money = $scope.farmData.sellData.broken_sale_money;
						$scope.farmData.TempChicken_manure = $scope.farmData.sellData.chicken_manure;

					}else{
						$scope.farmData.sellData = {
									"good_sale_weight":good_sale_weight,
									"good_salebox_num":good_salebox_num,
									"good_price_value":good_price_value,
									"good_box_size":good_box_size,
									"good_price_type":"01",
									"good_sale_money":good_sale_money,
									"broken_box_size":broken_box_size,
									"broken_price_value":broken_price_value,
									"broken_salebox_num":broken_salebox_num,
									"broken_sale_weight":broken_sale_weight,
									"broken_sale_money":broken_sale_money,
									"broken_price_type":"01",
									"chicken_manure":chicken_manure
								};
						Sparraw.myNotice("日期不在产蛋周期内！");
					}
					
					
					$scope.forageTotal();
					
				}else if (data.ResponseDetail.Result == "Fail"){
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});

		}
	}

  	
	setTimeout(
		function (){
			$scope.inquire(curDate);
		}
	,1000);
	
	

    $scope.changeMonth = function(val){

    	// 合格鸡蛋总重量
		good_sale_weight = 0;
		// 合格鸡蛋总箱数
		good_salebox_num = 0;
		// 合格鸡蛋单价
		good_price_value = 0;
		// 合格鸡蛋每箱规格
		good_box_size = 0;
		// 合格鸡蛋总金额
		good_sale_money = 0;
		
		
		// 破损鸡蛋总重量
		broken_sale_weight = 0;
		// 破损鸡蛋总箱数
		broken_salebox_num = 0;
		// 破损鸡蛋单价
		broken_price_value = 0;
		// 破损鸡蛋每箱规格
		broken_box_size = 0;
		// 破损鸡蛋总金额
		broken_sale_money = 0;

		// 鸡粪收入
		chicken_manure = 0;
		
		

		$scope.inquire(val);
		// 测试用
		//$scope.getBreedSum();
    };




	$scope.save = function(){
		
		if($scope.farmData.sellData.isHistory == undefined){
			Sparraw.myNotice("日期不在产蛋周期内,无法保存。");
			return;
		}

		var selectDate = $scope.farmData.selectDate;
		
		
		var sellData = $scope.farmData.sellData;
		
	
		sellData.good_price_value = ($scope.farmData.sellData.good_price_value);
		sellData.good_box_size = ($scope.farmData.sellData.good_box_size/2).toFixed(2);
	
		sellData.broken_price_value = ($scope.farmData.sellData.broken_price_value);
		sellData.broken_box_size = ($scope.farmData.sellData.broken_box_size/2).toFixed(2);
		
		/**good_sale_weight = sellData.good_sale_weight
		broken_sale_weight = sellData.broken_sale_weight
		
		console.log("good_sale_weight:" + good_sale_weight);
		console.log("broken_sale_weight:" + broken_sale_weight);
		**/
		console.log("modifyData:" + JSON.stringify(sellData));
		
	
			var params = {
				"FarmId"  :  farmId  ,
				"FarmBreedId"  :  farmFeedId  ,
				"saleDetails"    :  [sellData]
			};
			console.log($scope.sparraw_user_temp.Authority);
			if ($scope.sparraw_user_temp.Authority.EggSale === "All") {

			}else{
				return app_alert("该用户无此操作权限。");
			};

			Sparraw.ajaxPost('layer_salesInput/saveDRV2.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					
					$scope.inquire(selectDate);
					Sparraw.myNotice("保存成功");
					
					
				}else if (data.ResponseDetail.Result == "Fail"){
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});

	}
	
	
	$scope.doShowSalesAnalyze = function(){
		//console.log("sdfdfds")
		$state.go("salesAnalyze");
	}
	


	//日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();

    $scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '关闭', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2012, 12, 31), //Optional
            to: new Date(2020, 12, 31), //Optional
            callback: function (val) { //Optional
                datePickerCallbackPopup(val);
            }
        };

    var datePickerCallbackPopup = function (val) {
    		//重新选择日期清空入雏日期、批次编号、预计饲养天数、预计出栏日期

			//console.log(val);

			//$scope.farmData.place_date = "";

            if (typeof(val) === 'undefined') {
                console.log('未选择日期');
            } else {
                $scope.datepickerObjectPopup.inputDate = val;
                $scope.datePopup = val;
                //转为字符串并且删除“日”字符串，修改“年”“月”“/”为“-”,并且做补零处理                
                var selectDate = val.toLocaleDateString();
                
                selectDate     = selectDate.replace(/(日)/g,"");
                selectDate     = selectDate.replace(/(月)/g,"-");
                selectDate     = selectDate.replace(/(年)/g,"-");
                selectDate     = selectDate.replace(/\//g,"-");
                if (selectDate[6] == "-") {
                	selectDate = selectDate.replace(/(.{5})/g,'$10');
                };
                if (selectDate[9]) {

                }else{
                	selectDate = selectDate.replace(/(.{8})/g,'$10');
                };
				
                console.log(selectDate);

				$scope.changeMonth(selectDate);
				
                //将得到的日期放入入雏日期
                $scope.farmData.selectDate = selectDate;
               
            }
    };

    $scope.backFun = function(){

    	if ($scope.sparraw_user_temp.Authority.EggSale === "All") {

		}else{
			return $state.go("eggSellsReportTable");
		};

    	$scope.modifiedStatus = false;
		if ($scope.farmData.TempGood_box_size != $scope.farmData.sellData.good_box_size ||
			$scope.farmData.TempGood_salebox_num != $scope.farmData.sellData.good_salebox_num ||
			$scope.farmData.TempGood_price_value != $scope.farmData.sellData.good_price_value ||
			$scope.farmData.TempChecked != $scope.farmData.checked ||
			$scope.farmData.TempBroken_salebox_num != $scope.farmData.sellData.broken_salebox_num ||
			$scope.farmData.TempBroken_price_value != $scope.farmData.sellData.broken_price_value ||
			$scope.farmData.TempChicken_manure != $scope.farmData.sellData.chicken_manure) {
			$scope.modifiedStatus = true;
		}
		if ($scope.modifiedStatus) {
			app_confirm('数据已经被修改，请确认是否保存！','提示',null,function(buttonIndex){
	                if(buttonIndex == 2){
						$scope.save();
			        }else{
						$state.go("eggSellsReportTable");
					}
	          });
		}else{
			$state.go("eggSellsReportTable");
		}
    }

    $scope.goEggSellsTable = function(){

    	if ($scope.sparraw_user_temp.Authority.EggSale === "All") {

		}else{
			return $state.go("eggSellsList");
		};

    	$scope.modifiedStatus = false;

		if ($scope.farmData.TempGood_box_size != $scope.farmData.sellData.good_box_size ||
			$scope.farmData.TempGood_salebox_num != $scope.farmData.sellData.good_salebox_num ||
			$scope.farmData.TempGood_price_value != $scope.farmData.sellData.good_price_value ||
			$scope.farmData.TempChecked != $scope.farmData.checked ||
			$scope.farmData.TempBroken_salebox_num != $scope.farmData.sellData.broken_salebox_num ||
			$scope.farmData.TempBroken_price_value != $scope.farmData.sellData.broken_price_value ||
			$scope.farmData.TempChicken_manure != $scope.farmData.sellData.chicken_manure) {
			$scope.modifiedStatus = true;
		}
		if ($scope.modifiedStatus) {
			app_confirm('数据已经被修改，请确认是否保存！','提示',null,function(buttonIndex){
	                if(buttonIndex == 2){
						$scope.save();
			        }else{
						$state.go("eggSellsList");
					}
	          });
		}else{
			$state.go("eggSellsList");
		}
    }

})



//销售报表
.controller("eggSellsReportTableCtrl",function($scope, $state, $ionicLoading, $http, $ionicPopup, AppData) {
	
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setPortrait(true,true);
    

    $scope.pointDevelop = function(reportId) {
    	if(reportId == 'xsjl'){
    		if ($scope.sparraw_user_temp.farminfo.farmBreedBatchId == 0) {
    			app_alert("暂无数据，请先新建批次。");
    		}else{
    			$state.go("eggSellsReport");
    		}
    	}else if(reportId == 'xsrb'){
    		$state.go("eggSellsList");
    	}else if (reportId == 'jgqx') {
    		$state.go("salesAnalyze",{"lastPage":"eggSellsReportTable"});
    	}else{
    		pointDevelop();
    	}
	};
})

// 产蛋率曲线
.controller("eggWeigLayRateCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"02",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
    $scope.chooseBtn = true;

	//产蛋率
	// var yLeftRange = [70,100];
	var yLeftRange = null;
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("产蛋率");
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.cullDeathRateData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                  params.ViewType='02';
                  console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryLayerCurve.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var yMaxValue = null;
						 var yMinValue = null;
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
							var tempMax = Math.max.apply(null,$scope.cullDeathRateData.yData[i]);
							var tempMin = Math.min.apply(null,$scope.cullDeathRateData.yData[i]);
							if(yMaxValue == null || yMaxValue < tempMax){
								yMaxValue = tempMax ;
							}
							if(yMinValue == null || yMinValue > tempMin){
								yMinValue = tempMin ;
							}
						}
						if(yMaxValue > 100 ){
							yMaxValue = 100;
						}else{
							yMaxValue = null;
						}
						if(yMinValue < 70 ){
							yMinValue = 70;
						}else{
							yMinValue = null;
						}
						yLeftRange = [yMinValue,yMaxValue];
						console.log(yLeftRange);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.cullDeathRateData.xData);
						
					    if ($scope.cullDeathRateData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
								   for (var i = 0; i < j; i++) {
									 $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                var day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								}
							}
                            var day_age_week  ="日龄"
					    }
						
						
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '单位：％',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
									            }
		                                        return    res;
						                     },yLeftRange
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	//蛋重
	$scope.changes2 = function(){
		console.log("蛋重");
		$scope.cullDeathRateData.xData = [];
			var params = { 
				'IsNeedDelay':'Y',
				'ViewType' : $scope.cullDeathRateData.ViewType,
		    	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		    	"FarmId":$scope.sparraw_user_temp.farminfo.id   
			}
			if(params.ViewType==null){
                  params.ViewType='02';
			}
			Sparraw.ajaxPost('layer_report/queryEggWeightCurve.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.EggAvgWeight.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.EggAvgWeight[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.EggAvgWeight[i].HouseDatas;
						}
					    
						var day_age_week  ="周龄"
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
					    console.log($scope.cullDeathRateData.xData);
					    var j = 60-data.ResponseDetail.xAxis.length;
					    var L = data.ResponseDetail.xAxis.length
					    if($scope.cullDeathRateData.xData.length<60){
                          for (var i = 0; i < j; i++) {
                            $scope.cullDeathRateData.xData[L+i] = Maxage + i;
                          }
					    }
					    if ($scope.cullDeathRateData.ViewType=="01") {
                               day_age_week  ="周龄"
					    }else{
                               day_age_week  ="日龄"
					    }
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '克/枚',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + 'g';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
			});
	}
	  
	  $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='蛋重'){
        	 console.log("这是changes2方法");
             document.getElementById("chang").innerHTML = '产蛋率';
             document.getElementById("cullSurTi").innerHTML = '蛋重曲线';
             return $scope.changes2();
        }else if (dd=='产蛋率'){
        	 console.log("这是changes1方法");
        	 document.getElementById("chang").innerHTML = '蛋重';
        	 document.getElementById("cullSurTi").innerHTML = '产蛋率曲线';
       	     return $scope.changes1();
        }
    }
    //切换查看方式
    $scope.changesCompareType = function(){
    	var dd = "蛋重";//document.getElementById("chang").innerHTML;
	        if(dd=='产蛋率'){
	             return $scope.changes2();
	        }else if (dd=='蛋重'){
	        	 return $scope.changes1();
	        }
	};

	setLandscape(true,true);
	$scope.changes1();


})


//产蛋率曲线(演示)
.controller("coluCurveCtrl",function($scope, $state, $http,$ionicLoading, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
    $scope.chooseBtn = true;

	//产蛋率
	// var yLeftRange = [70,100];
	var yLeftRange = null;
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("产蛋率");
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.cullDeathRateData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                  params.ViewType='02';
                  console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryLayerCurve.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var yMaxValue = null;
						 var yMinValue = null;

						
						for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
							for (var j = 0; j < $scope.cullDeathRateData.yData[i].length; j++) {
								if ($scope.cullDeathRateData.yData[i][j] < 50 ) {
									$scope.cullDeathRateData.yData[i][j] = "-";
								}
							}
							var tempMax = Math.max.apply(null,$scope.cullDeathRateData.yData[i]);
							var tempMin = Math.min.apply(null,$scope.cullDeathRateData.yData[i]);
							if(yMaxValue == null || yMaxValue < tempMax){
								yMaxValue = tempMax ;
							}
							if(yMinValue == null || yMinValue > tempMin){
								yMinValue = tempMin ;
							}
						}

						if(yMaxValue > 100 ){
							yMaxValue = 100;
						}else{
							yMaxValue = null;
						}
						if(yMinValue < 70 ){
							yMinValue = 70;
						}else{
							yMinValue = null;
						}
						
						yLeftRange = [yMinValue,yMaxValue];
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    if ($scope.cullDeathRateData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
								   for (var i = 0; i < j; i++) {
									 $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                var day_age_week  ="周龄";
								
								/**/
								var temp = [];// 22 57 
								var fromIndex = 0;
								var endIndex = 0;
								for(var i = 0 ;i < $scope.cullDeathRateData.xData.length; i++ ){
									var x = $scope.cullDeathRateData.xData[i];
									if(x == 22){
										fromIndex = i;
									}
									
									if(x == 57){
										endIndex = i;
									}
									
									if(x >= 22 && x <= 57){
										temp.push(x);
									}
								}
								yMaxValue = null;
								yMinValue = null;
								for (var i = 0; i < data.ResponseDetail.LayerRate.length; i++) {
									$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.LayerRate[i].HouseName;
									$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.LayerRate[i].HouseDatas;
									var temp2 = [];
									
									for(j=fromIndex; j<endIndex; j++ ){
										temp2.push(data.ResponseDetail.LayerRate[i].HouseDatas[j])
									}
									$scope.cullDeathRateData.yData[i] = temp2;
									
									var tempMax = Math.max.apply(null,$scope.cullDeathRateData.yData[i]);
									var tempMin = Math.min.apply(null,$scope.cullDeathRateData.yData[i]);
									
									console.log("tempMin:"+tempMin + ",tempMax:" +tempMax)
									
									if(yMaxValue == null || yMaxValue < tempMax){
										yMaxValue = tempMax ;
									}
									if(yMinValue == null || yMinValue > tempMin){
										yMinValue = tempMin ;
									}
								}
								
								console.log("yMinValue:"+yMinValue + ",yMaxValue:" +yMaxValue)
								if(yMaxValue > 100 ){
									yMaxValue = 100;
								}else{
									yMaxValue = null;
								}
								if(yMinValue < 85 ){
									yMinValue = 85;
								}else{
									yMinValue = null;
								}
								yMaxValue = 100;
								yMinValue = 80;
								console.log("yMinValue:"+yMinValue + ",yMaxValue:" +yMaxValue)
								$scope.cullDeathRateData.xData = temp;
								yLeftRange = [yMinValue,yMaxValue];
								$scope.cullDeathRateData.yName.unshift("标准");
								$scope.cullDeathRateData.yData.unshift(["80.2","88.3","91.9","93.0","93.7","94.1","94.4","94.7","94.8","94.9","94.8","94.7","94.6","94.4","94.2","93.9","93.7","93.5","93.3","93.0","92.7","92.4","92.1","91.7","91.4","91.1","90.7","90.3","89.8","89.4","89.0","88.6","88.1","87.6","87.1","86.7","86.1","85.6","85.1","84.5","84.0","83.4","82.9","82.4","81.8","81.2","80.6","80.0","79.4","78.8","78.2","77.5","76.8","76.2","75.5","74.9","74.2","73.5","72.7"]); 
					    }else{	
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								}
							}
                            var day_age_week  ="日龄" 
					    }
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '单位：％',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
									            }
		                                        return    res;
						                     },yLeftRange
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	//蛋重
	$scope.changes2 = function(){
		console.log("蛋重");
		$scope.cullDeathRateData.xData = [];
			var params = { 
				'IsNeedDelay':'Y',
				'ViewType' : $scope.cullDeathRateData.ViewType,
		    	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		    	"FarmId":$scope.sparraw_user_temp.farminfo.id   
			}
			if(params.ViewType==null){
                  params.ViewType='02';
			}
			Sparraw.ajaxPost('layer_report/queryEggWeightCurve.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.EggAvgWeight.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.EggAvgWeight[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.EggAvgWeight[i].HouseDatas;
						}
					    
						var day_age_week  ="周龄"
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
					    console.log($scope.cullDeathRateData.xData);
					    var j = 60-data.ResponseDetail.xAxis.length;
					    var L = data.ResponseDetail.xAxis.length
					    if($scope.cullDeathRateData.xData.length<60){
                          for (var i = 0; i < j; i++) {
                            $scope.cullDeathRateData.xData[L+i] = Maxage + i;
                          }
					    }
					    if ($scope.cullDeathRateData.ViewType=="01") {
                               day_age_week  ="周龄"
					    }else{
                               day_age_week  ="日龄"
					    }
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '克/枚',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + 'g';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
			});
	}
	  
	  $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='蛋重'){
        	 console.log("这是changes2方法");
             document.getElementById("chang").innerHTML = '产蛋率';
             document.getElementById("cullSurTi").innerHTML = '蛋重曲线';
             return $scope.changes2();
        }else if (dd=='产蛋率'){
        	 console.log("这是changes1方法");
        	 document.getElementById("chang").innerHTML = '蛋重';
        	 document.getElementById("cullSurTi").innerHTML = '产蛋率曲线';
       	     return $scope.changes1();
        }
    }
    //切换查看方式
    $scope.changesCompareType = function(){
    	var dd = "蛋重";//document.getElementById("chang").innerHTML;
	        if(dd=='产蛋率'){
	             return $scope.changes2();
	        }else if (dd=='蛋重'){
	        	 return $scope.changes1();
	        }
	};


	setLandscape(true,true);
	$scope.changes1();


})

// 蛋重曲线
.controller("eggWeigLayCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
    


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

    if(navigator.userAgent.indexOf('Android') >= 0) {
		//安卓高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;

		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('cullDeath_DIV').style.height = DIVHEIGHT + 'px';
	}

    $scope.cullDeathRateData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"02",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
    $scope.chooseBtn = true;
	//产蛋率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("产蛋率");
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.cullDeathRateData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                  params.ViewType='02';
                  console.log("params");
		}
		Sparraw.ajaxPost('layer_report/queryEggWeightCurve.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						for (var i = 0; i < data.ResponseDetail.EggAvgWeight.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.EggAvgWeight[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.EggAvgWeight[i].HouseDatas;
						}
						 
						var day_age_week  ="周龄";
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.cullDeathRateData.xData);
						
					    if ($scope.cullDeathRateData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.cullDeathRateData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄"
					    }
						
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '单位：克',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	//蛋重
	$scope.changes2 = function(){
		console.log("蛋重");
		$scope.cullDeathRateData.xData = [];
			var params = { 
				'IsNeedDelay':'Y',
				'ViewType' : $scope.cullDeathRateData.ViewType,
		    	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		    	"FarmId":$scope.sparraw_user_temp.farminfo.id   
			}
			if(params.ViewType==null){
                  params.ViewType='02';
			}
			Sparraw.ajaxPost('layer_report/queryEggWeightCurve.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.cullDeathRateData.yName =  [];
						 $scope.cullDeathRateData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.EggAvgWeight.length; i++) {
							$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.EggAvgWeight[i].HouseName;
							$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.EggAvgWeight[i].HouseDatas;
						}
					    var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
					    $scope.cullDeathRateData.xData=data.ResponseDetail.xAxis;
					     console.log($scope.cullDeathRateData.xData);
					    var j = 60-data.ResponseDetail.xAxis.length;
					    var L = data.ResponseDetail.xAxis.length
					    if($scope.cullDeathRateData.xData.length<60){
                          for (var i = 0; i < j; i++) {
                            $scope.cullDeathRateData.xData[L+i] = Maxage + i;
                          }
					    }
					    if ($scope.cullDeathRateData.ViewType=="01") {
                                var day_age_week  ="周龄"
					    }else{
                                var day_age_week  ="日龄"
					    }
					    console.log($scope.cullDeathRateData.xData);
						$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.cullDeathRateData.selectUnit = '';
						getLineChart($scope.cullDeathRateData.xData,
											 $scope.cullDeathRateData.yData,
											 $scope.cullDeathRateData.yName,
											 $scope.cullDeathRateData.yColor,
											 $scope.cullDeathRateData.hiddenPara,
											 $scope.cullDeathRateData.selectUnit,
											 '克/枚',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + 'g';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
			});
	}
	  
	  $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='蛋重'){
        	 console.log("这是changes2方法");
             document.getElementById("chang").innerHTML = '产蛋率';
             document.getElementById("cullSurTi").innerHTML = '蛋重曲线';
             return $scope.changes2();
        }else if (dd=='产蛋率'){
        	 console.log("这是changes1方法");
        	 document.getElementById("chang").innerHTML = '蛋重';
        	 document.getElementById("cullSurTi").innerHTML = '产蛋率曲线';
       	     return $scope.changes1();
        }
    }
    //切换查看方式
    $scope.changesCompareType = function(){
    	var dd = document.getElementById("chang").innerHTML;
	        if(dd=='产蛋率'){
	             return $scope.changes2();
	        }else if (dd=='蛋重'){
	        	 return $scope.changes1();
	        }
	};

	setLandscape(true,true);
	$scope.changes1();



})
//只鸡产蛋曲线
.controller("onlyChickEggsCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	


    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

	if(navigator.userAgent.indexOf('Android') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}

	$scope.onlyChickEggsData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
	
	
	//体重
     $scope.changes1 = function(){
     	$scope.onlyChickEggsData.xData=[];
     	
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.onlyChickEggsData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                params.ViewType='01';
		}
		Sparraw.ajaxPost('layer_report/queryChickenEggs.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.onlyChickEggsData.yName =  [];
						 $scope.onlyChickEggsData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
							$scope.onlyChickEggsData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
							$scope.onlyChickEggsData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
						}
						
						
					    var day_age_week  ="周龄";
					    $scope.onlyChickEggsData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.onlyChickEggsData.xData);
						
					    if ($scope.onlyChickEggsData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.onlyChickEggsData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
								
								/*
								var temp = [];// 22 57 
								var fromIndex = 0;
								var endIndex = 0;
								var standData = [];
								for(var i = 0 ;i < $scope.onlyChickEggsData.xData.length; i++ ){
									var x = $scope.onlyChickEggsData.xData[i];
									if(x == 22){
										fromIndex = i;
									}
									
									if(x == 57){
										endIndex = i;
									}
									
									if(x >= 22 && x <= 57){
										temp.push(x);
										var val = $scope.myConfig.standChickenLayEggs[x];
										standData.push(val);
									}
								}
								console.log("standData:"+standData)
								$scope.onlyChickEggsData.xData = temp;
								
								for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
									$scope.onlyChickEggsData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
									$scope.onlyChickEggsData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
									var temp2 = [];
									
									for(j=fromIndex; j<endIndex; j++ ){
										var val = data.ResponseDetail.yDatas[i].HouseDatas[j];
										if(val > 0){
											temp2.push(val)
										}else{
											break;
										}
										
									}
									$scope.onlyChickEggsData.yData[i] = temp2;
									
								}
								
								$scope.onlyChickEggsData.yName.push("标准");
								$scope.onlyChickEggsData.yData.push(standData);
								*/
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.onlyChickEggsData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄";
							/*
							for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
									$scope.onlyChickEggsData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
									$scope.onlyChickEggsData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
									var temp2 = [];
									
									for(j=0; j< data.ResponseDetail.yDatas[i].HouseDatas.length; j++ ){
										var val = data.ResponseDetail.yDatas[i].HouseDatas[j];
										if(val > 0){
											temp2.push(val)
										}else{
											break;
										}
										
									}
									$scope.onlyChickEggsData.yData[i] = temp2;
									
							}
							*/
					    }
						console.log($scope.onlyChickEggsData.xData);
						
						
						
						
						$scope.onlyChickEggsData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.onlyChickEggsData.selectUnit = '';
						getLineChart($scope.onlyChickEggsData.xData,
											 $scope.onlyChickEggsData.yData,
											 $scope.onlyChickEggsData.yName,
											 $scope.onlyChickEggsData.yColor,
											 $scope.onlyChickEggsData.hiddenPara,
											 $scope.onlyChickEggsData.selectUnit,
											 '枚/只',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value +'';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
						   
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	
	

	$scope.changeViewType = function(){
		$scope.changes1();
	}


	setLandscape(true,true);
	$scope.changes1();


})


//只鸡产蛋曲线Demo
.controller("onlyChickEggsCtrlDemo",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

	if(navigator.userAgent.indexOf('Android') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}else if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.clientWidth;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - 75;
		document.getElementById('onlyChickEggs_DIV').style.height = DIVHEIGHT + 'px';
	}

	$scope.onlyChickEggsData = {
		"xData"       :  [] ,
		"yData"       :  [] ,
		"yName"       :  [] ,
		"yColor"      :  [] ,
		"hiddenPara"  :  [] ,
		"selectedBatch"  :  [] ,
		"selectedBatchKey"  :  [] ,
		"farmBatch"  :  [] ,
		"BatchDate"  :  [] ,
		"farmBatch"  :  [] ,
		"selectUnit" :  "" ,
		"compareType":  "" ,
		"ViewType":"01",
		"selectedHouse": "",
		"containBatchHouse":[]
    };
	
	
	//体重
     $scope.changes1 = function(){
     	$scope.onlyChickEggsData.xData=[];
     	
 		var params = { 
 			'IsNeedDelay':'Y',
        	'ViewType' : $scope.onlyChickEggsData.ViewType,
        	'FarmBreedId' :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        	"FarmId":$scope.sparraw_user_temp.farminfo.id       	
        }
        console.log(params);
        if(params.ViewType==null){
                params.ViewType='01';
		}
		
		var data = {"ResponseStatus":1,"ResponseDetail":{"yDatas":[{"HouseName":"全场平均","HouseDatas":[0,0,0.8,4.7,10.6,17.2,23.9,30.6,37.3,44,50.7,57.4,64.1,70.8,77.5,84.2,90.8,97.5,104.1,110.7,117.4,124,130.6,137.2,143.8,150.4,156.9,163.4,169.8,176.1,182.4,188.7,195,201.3,207.6,213.8,0,0,0,0,0,0.1],"HouseId":"01"},{"HouseName":"01","HouseDatas":[0,0,0.8,4.8,10.8,17.3,24,30.7,37.4,44.1,50.8,57.5,64.2,70.9,77.5,84.2,90.8,97.5,104.1,110.7,117.3,123.9,130.5,137.1,143.7,150.3,156.8,163.3,169.7,176,182.3,188.6,194.9,201.2,207.4,213.7,0,0,0,0,0,0.3],"HouseId":"102"},{"HouseName":"02","HouseDatas":[0,0,0.8,4.8,10.8,17.3,24,30.7,37.4,44.1,50.8,57.5,64.2,70.9,77.5,84.2,90.8,97.5,104.1,110.7,117.4,124,130.6,137.2,143.8,150.3,156.9,163.3,169.6,175.9,182.2,188.5,194.8,201.1,207.4,213.6,0,0,0,0,0,0],"HouseId":"103"},{"HouseName":"03","HouseDatas":[0,0,0.7,4.4,10.3,17,23.7,30.4,37.1,43.9,50.6,57.3,64,70.7,77.4,84.1,90.8,97.4,104.1,110.8,117.4,124.1,130.7,137.3,143.9,150.5,157.1,163.6,170,176.3,182.7,189,195.3,201.7,207.9,214.2,0,0,0,0,0,0],"HouseId":"104"}],"ViewType":"01","Result":"Success","FarmId":26,"FarmBreedId":82,"xAxis":[17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58]}};
		
		var standChickenLayEggs = {"19":0.7,"20":3.9,"21":8.4,"22":14.0,"23":20.2,"24":26.6,"25":33.0,"26":39.6,"27":46.1,"28":52.6,"29":59.2,"30":65.8,"31":72.4,"32":78.9,"33":85.5,"34":92.0,"35":98.5,"36":105.0,"37":111.4,"38":117.9,"39":124.3,"40":130.7,"41":137.1,"42":143.4,"43":149.7,"44":156.0,"45":162.3,"46":168.5,"47":174.7,"48":180.9,"49":187.0,"50":193.1,"51":199.2,"52":205.2,"53":211.2,"54":217.2,"55":223.1,"56":229.0,"57":234.8,"58":240.6,"59":246.4,"60":252.1,"61":257.8,"62":263.4,"63":269.0,"64":274.5,"65":280.0,"66":285.5,"67":290.9,"68":296.3,"69":301.6,"70":306.9,"71":312.1,"72":317.3,"73":322.4,"74":327.5,"75":332.6,"76":337.6,"77":342.5,"78":347.4,"79":352.2,"80":357.0};
		
		Sparraw.ajaxPost('layer_report/queryChickenEggs.action', params, function(data){
					if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.xAxis.length != 0){
						 $scope.onlyChickEggsData.yName =  [];
						 $scope.onlyChickEggsData.yData =  []; 
						 var ageLeng =[];
						for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
							$scope.onlyChickEggsData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
							$scope.onlyChickEggsData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
						}
						
						
					    var day_age_week  ="周龄";
					    $scope.onlyChickEggsData.xData=data.ResponseDetail.xAxis;
						var Maxage = Math.max.apply(null, data.ResponseDetail.xAxis);
						console.log($scope.onlyChickEggsData.xData);
						
					    if ($scope.onlyChickEggsData.ViewType=="01"){
								
								if(Maxage < 88){
									var j = 88 - Maxage;
									var L = data.ResponseDetail.xAxis.length
									
								   for (var i = 0; i < j; i++) {
									 $scope.onlyChickEggsData.xData[L+i] = Maxage + i + 1;
								   }
								}
						
                                day_age_week  ="周龄";
								
								/**/
								var temp = [];// 22 57 
								var fromIndex = 0;
								var endIndex = 0;
								var standData = [];
								for(var i = 0 ;i < $scope.onlyChickEggsData.xData.length; i++ ){
									var x = $scope.onlyChickEggsData.xData[i];
									if(x == 22){
										fromIndex = i;
									}
									
									if(x == 57){
										endIndex = i;
									}
									
									if(x >= 22 && x <= 57){
										temp.push(x);
										var val = standChickenLayEggs[x].toFixed(1);
										standData.push(val);
									}
								}
								console.log("standData:"+standData)
								$scope.onlyChickEggsData.xData = temp;
								
								$scope.onlyChickEggsData.yName = [];
								$scope.onlyChickEggsData.yData = [];
								$scope.onlyChickEggsData.yName.push("标准");
								$scope.onlyChickEggsData.yData.push(standData);
								
								for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
									$scope.onlyChickEggsData.yName.push(data.ResponseDetail.yDatas[i].HouseName);
									
									var temp2 = [];
									
									for(j=fromIndex; j<endIndex; j++ ){
										var val = data.ResponseDetail.yDatas[i].HouseDatas[j];
										if(val > 0){
											temp2.push(val)
										}else{
											break;
										}
										
									}
									//$scope.onlyChickEggsData.yData[i+1] = temp2;
									$scope.onlyChickEggsData.yData.push(temp2);
								}
								
								
								
								/**/
					    }else{
							var L = data.ResponseDetail.xAxis.length
							if(L < 60){
								var j = 60 - L;
								for (var i = 0; i < j; i++) {
									$scope.onlyChickEggsData.xData[L+i] = Maxage + i + 1;
								}
							}
                            day_age_week  ="日龄";
							/**/
							for (var i = 0; i < data.ResponseDetail.yDatas.length; i++) {
									$scope.onlyChickEggsData.yName[i] =  data.ResponseDetail.yDatas[i].HouseName;
									$scope.onlyChickEggsData.yData[i] =  data.ResponseDetail.yDatas[i].HouseDatas;
									var temp2 = [];
									
									for(j=0; j< data.ResponseDetail.yDatas[i].HouseDatas.length; j++ ){
										var val = data.ResponseDetail.yDatas[i].HouseDatas[j];
										if(val > 0){
											temp2.push(val)
										}else{
											break;
										}
										
									}
									$scope.onlyChickEggsData.yData[i] = temp2;
									
							}
							/**/
					    }
						console.log($scope.onlyChickEggsData.xData);
						
						
						
						
						$scope.onlyChickEggsData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
						$scope.onlyChickEggsData.selectUnit = '';
						getLineChart($scope.onlyChickEggsData.xData,
											 $scope.onlyChickEggsData.yData,
											 $scope.onlyChickEggsData.yName,
											 $scope.onlyChickEggsData.yColor,
											 $scope.onlyChickEggsData.hiddenPara,
											 $scope.onlyChickEggsData.selectUnit,
											 '枚/只',
											 function (params){
						                        var res = day_age_week + ' :' + params[0].name;
									            for (var i = 0, l = params.length; i < l; i++) {
									                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value +'';
									            }
		                                        return    res;
						                     }
					   );
					}else if(data.ResponseDetail.Result == 'Fail'){
						Sparraw.myNotice("暂无信息");
						   
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
		});
	}
  	
	

	$scope.changeViewType = function(){
		$scope.changes1();
	}

	setLandscape(true,true);
	$scope.changes1();


})



// 体感温度计算器
.controller("apparentTempCalcCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	
	$scope.calcData = {
		"wind"       :  0 ,// 风速
		"RH"       :  0 ,// 相对湿度
		"envTemp"       :  0 ,// 温度
		"apparentTemp"      :  0
    };
	
	// 基础标准数据
	var baseData = [{'temp':35,'RH':30,'wind':0,'feelsLike':35},
					{'temp':35,'RH':50,'wind':0,'feelsLike':35},
					{'temp':35,'RH':70,'wind':0,'feelsLike':38.3},
					{'temp':35,'RH':80,'wind':0,'feelsLike':40},
					{'temp':32.2,'RH':30,'wind':0,'feelsLike':32.2},
					{'temp':32.2,'RH':50,'wind':0,'feelsLike':32.2},
					{'temp':32.2,'RH':70,'wind':0,'feelsLike':35.5},
					{'temp':32.2,'RH':80,'wind':0,'feelsLike':37.2},
					{'temp':29.4,'RH':30,'wind':0,'feelsLike':29.4},
					{'temp':29.4,'RH':50,'wind':0,'feelsLike':29.4},
					{'temp':29.4,'RH':70,'wind':0,'feelsLike':31.6},
					{'temp':29.4,'RH':80,'wind':0,'feelsLike':33.3},
					{'temp':26.6,'RH':30,'wind':0,'feelsLike':26.6},
					{'temp':26.6,'RH':50,'wind':0,'feelsLike':26.6},
					{'temp':26.6,'RH':70,'wind':0,'feelsLike':28.3},
					{'temp':26.6,'RH':80,'wind':0,'feelsLike':29.4},
					{'temp':23.9,'RH':30,'wind':0,'feelsLike':23.9},
					{'temp':23.9,'RH':50,'wind':0,'feelsLike':23.9},
					{'temp':23.9,'RH':70,'wind':0,'feelsLike':25.5},
					{'temp':23.9,'RH':80,'wind':0,'feelsLike':26.3},
					{'temp':21.1,'RH':30,'wind':0,'feelsLike':21.1},
					{'temp':21.1,'RH':50,'wind':0,'feelsLike':21.1},
					{'temp':21.1,'RH':70,'wind':0,'feelsLike':23.3},
					{'temp':21.1,'RH':80,'wind':0,'feelsLike':24.4},
					{'temp':35,'RH':30,'wind':0.5,'feelsLike':31.6},
					{'temp':35,'RH':50,'wind':0.5,'feelsLike':32.2},
					{'temp':35,'RH':70,'wind':0.5,'feelsLike':35.2},
					{'temp':35,'RH':80,'wind':0.5,'feelsLike':36.7},
					{'temp':32.2,'RH':30,'wind':0.5,'feelsLike':28.8},
					{'temp':32.2,'RH':50,'wind':0.5,'feelsLike':29.4},
					{'temp':32.2,'RH':70,'wind':0.5,'feelsLike':32.7},
					{'temp':32.2,'RH':80,'wind':0.5,'feelsLike':34.2},
					{'temp':29.4,'RH':30,'wind':0.5,'feelsLike':26},
					{'temp':29.4,'RH':50,'wind':0.5,'feelsLike':26.6},
					{'temp':29.4,'RH':70,'wind':0.5,'feelsLike':30},
					{'temp':29.4,'RH':80,'wind':0.5,'feelsLike':31.6},
					{'temp':26.6,'RH':30,'wind':0.5,'feelsLike':23.6},
					{'temp':26.6,'RH':50,'wind':0.5,'feelsLike':24.4},
					{'temp':26.6,'RH':70,'wind':0.5,'feelsLike':26.1},
					{'temp':26.6,'RH':80,'wind':0.5,'feelsLike':27.2},
					{'temp':23.9,'RH':30,'wind':0.5,'feelsLike':22.2},
					{'temp':23.9,'RH':50,'wind':0.5,'feelsLike':22.8},
					{'temp':23.9,'RH':70,'wind':0.5,'feelsLike':24.4},
					{'temp':23.9,'RH':80,'wind':0.5,'feelsLike':25.2},
					{'temp':21.1,'RH':30,'wind':0.5,'feelsLike':18.4},
					{'temp':21.1,'RH':50,'wind':0.5,'feelsLike':18.9},
					{'temp':21.1,'RH':70,'wind':0.5,'feelsLike':20.5},
					{'temp':21.1,'RH':80,'wind':0.5,'feelsLike':21.3},
					{'temp':35,'RH':30,'wind':1.1,'feelsLike':26.1},
					{'temp':35,'RH':50,'wind':1.1,'feelsLike':26.6},
					{'temp':35,'RH':70,'wind':1.1,'feelsLike':30.5},
					{'temp':35,'RH':80,'wind':1.1,'feelsLike':32},
					{'temp':32.2,'RH':30,'wind':1.1,'feelsLike':24.9},
					{'temp':32.2,'RH':50,'wind':1.1,'feelsLike':25.5},
					{'temp':32.2,'RH':70,'wind':1.1,'feelsLike':28.8},
					{'temp':32.2,'RH':80,'wind':1.1,'feelsLike':30},
					{'temp':29.4,'RH':30,'wind':1.1,'feelsLike':23.8},
					{'temp':29.4,'RH':50,'wind':1.1,'feelsLike':24.4},
					{'temp':29.4,'RH':70,'wind':1.1,'feelsLike':27.2},
					{'temp':29.4,'RH':80,'wind':1.1,'feelsLike':28.8},
					{'temp':26.6,'RH':30,'wind':1.1,'feelsLike':21.3},
					{'temp':26.6,'RH':50,'wind':1.1,'feelsLike':22.2},
					{'temp':26.6,'RH':70,'wind':1.1,'feelsLike':24.4},
					{'temp':26.6,'RH':80,'wind':1.1,'feelsLike':25.5},
					{'temp':23.9,'RH':30,'wind':1.1,'feelsLike':20.2},
					{'temp':23.9,'RH':50,'wind':1.1,'feelsLike':21.1},
					{'temp':23.9,'RH':70,'wind':1.1,'feelsLike':23.3},
					{'temp':23.9,'RH':80,'wind':1.1,'feelsLike':24.1},
					{'temp':21.1,'RH':30,'wind':1.1,'feelsLike':17.6},
					{'temp':21.1,'RH':50,'wind':1.1,'feelsLike':18.3},
					{'temp':21.1,'RH':70,'wind':1.1,'feelsLike':19.4},
					{'temp':21.1,'RH':80,'wind':1.1,'feelsLike':20},
					{'temp':35,'RH':30,'wind':1.52,'feelsLike':23.8},
					{'temp':35,'RH':50,'wind':1.52,'feelsLike':24.4},
					{'temp':35,'RH':70,'wind':1.52,'feelsLike':28.8},
					{'temp':35,'RH':80,'wind':1.52,'feelsLike':30},
					{'temp':32.2,'RH':30,'wind':1.52,'feelsLike':23.2},
					{'temp':32.2,'RH':50,'wind':1.52,'feelsLike':23.8},
					{'temp':32.2,'RH':70,'wind':1.52,'feelsLike':27.2},
					{'temp':32.2,'RH':80,'wind':1.52,'feelsLike':28.4},
					{'temp':29.4,'RH':30,'wind':1.52,'feelsLike':22.1},
					{'temp':29.4,'RH':50,'wind':1.52,'feelsLike':22.7},
					{'temp':29.4,'RH':70,'wind':1.52,'feelsLike':25.5},
					{'temp':29.4,'RH':80,'wind':1.52,'feelsLike':26.7},
					{'temp':26.6,'RH':30,'wind':1.52,'feelsLike':20.5},
					{'temp':26.6,'RH':50,'wind':1.52,'feelsLike':21.1},
					{'temp':26.6,'RH':70,'wind':1.52,'feelsLike':23.3},
					{'temp':26.6,'RH':80,'wind':1.52,'feelsLike':24},
					{'temp':23.9,'RH':30,'wind':1.52,'feelsLike':19.4},
					{'temp':23.9,'RH':50,'wind':1.52,'feelsLike':20},
					{'temp':23.9,'RH':70,'wind':1.52,'feelsLike':22.2},
					{'temp':23.9,'RH':80,'wind':1.52,'feelsLike':22.8},
					{'temp':21.1,'RH':30,'wind':1.52,'feelsLike':17.3},
					{'temp':21.1,'RH':50,'wind':1.52,'feelsLike':17.7},
					{'temp':21.1,'RH':70,'wind':1.52,'feelsLike':18.8},
					{'temp':21.1,'RH':80,'wind':1.52,'feelsLike':19.5},
					{'temp':35,'RH':30,'wind':2.03,'feelsLike':22.7},
					{'temp':35,'RH':50,'wind':2.03,'feelsLike':23.3},
					{'temp':35,'RH':70,'wind':2.03,'feelsLike':26.1},
					{'temp':35,'RH':80,'wind':2.03,'feelsLike':27.2},
					{'temp':32.2,'RH':30,'wind':2.03,'feelsLike':22.1},
					{'temp':32.2,'RH':50,'wind':2.03,'feelsLike':22.7},
					{'temp':32.2,'RH':70,'wind':2.03,'feelsLike':25.5},
					{'temp':32.2,'RH':80,'wind':2.03,'feelsLike':26.5},
					{'temp':29.4,'RH':30,'wind':2.03,'feelsLike':20.5},
					{'temp':29.4,'RH':50,'wind':2.03,'feelsLike':21.1},
					{'temp':29.4,'RH':70,'wind':2.03,'feelsLike':24.4},
					{'temp':29.4,'RH':80,'wind':2.03,'feelsLike':25.2},
					{'temp':26.6,'RH':30,'wind':2.03,'feelsLike':18.3},
					{'temp':26.6,'RH':50,'wind':2.03,'feelsLike':18.9},
					{'temp':26.6,'RH':70,'wind':2.03,'feelsLike':20.5},
					{'temp':26.6,'RH':80,'wind':2.03,'feelsLike':21.1},
					{'temp':23.9,'RH':30,'wind':2.03,'feelsLike':17.1},
					{'temp':23.9,'RH':50,'wind':2.03,'feelsLike':17.7},
					{'temp':23.9,'RH':70,'wind':2.03,'feelsLike':20},
					{'temp':23.9,'RH':80,'wind':2.03,'feelsLike':20.8},
					{'temp':21.1,'RH':30,'wind':2.03,'feelsLike':16.1},
					{'temp':21.1,'RH':50,'wind':2.03,'feelsLike':16.6},
					{'temp':21.1,'RH':70,'wind':2.03,'feelsLike':18.3},
					{'temp':21.1,'RH':80,'wind':2.03,'feelsLike':19},
					{'temp':35,'RH':30,'wind':2.54,'feelsLike':21.6},
					{'temp':35,'RH':50,'wind':2.54,'feelsLike':22.2},
					{'temp':35,'RH':70,'wind':2.54,'feelsLike':24.4},
					{'temp':35,'RH':80,'wind':2.54,'feelsLike':25.2},
					{'temp':32.2,'RH':30,'wind':2.54,'feelsLike':20.5},
					{'temp':32.2,'RH':50,'wind':2.54,'feelsLike':21.1},
					{'temp':32.2,'RH':70,'wind':2.54,'feelsLike':23.3},
					{'temp':32.2,'RH':80,'wind':2.54,'feelsLike':24},
					{'temp':29.4,'RH':30,'wind':2.54,'feelsLike':19.4},
					{'temp':29.4,'RH':50,'wind':2.54,'feelsLike':20},
					{'temp':29.4,'RH':70,'wind':2.54,'feelsLike':23.3},
					{'temp':29.4,'RH':80,'wind':2.54,'feelsLike':23.9},
					{'temp':26.6,'RH':30,'wind':2.54,'feelsLike':17.7},
					{'temp':26.6,'RH':50,'wind':2.54,'feelsLike':18.3},
					{'temp':26.6,'RH':70,'wind':2.54,'feelsLike':19.4},
					{'temp':26.6,'RH':80,'wind':2.54,'feelsLike':20},
					{'temp':23.9,'RH':30,'wind':2.54,'feelsLike':16},
					{'temp':23.9,'RH':50,'wind':2.54,'feelsLike':16.6},
					{'temp':23.9,'RH':70,'wind':2.54,'feelsLike':18.8},
					{'temp':23.9,'RH':80,'wind':2.54,'feelsLike':19.9},
					{'temp':21.1,'RH':30,'wind':2.54,'feelsLike':15.5},
					{'temp':21.1,'RH':50,'wind':2.54,'feelsLike':16.1},
					{'temp':21.1,'RH':70,'wind':2.54,'feelsLike':17.2},
					{'temp':21.1,'RH':80,'wind':2.54,'feelsLike':18}
					];
	
	
	$scope.save = function(){
		var wind = $scope.calcData.wind.toFixed(2);
		var RH = $scope.calcData.RH.toFixed(1);
		var envTemp = $scope.calcData.envTemp.toFixed(1);
		var apparentTemp = 0;
		
		console.log("envTemp:" + $scope.calcData.envTemp);
		console.log("RH:" + $scope.calcData.RH);
		console.log("wind:" + $scope.calcData.wind);
		
		var filter = true;
		if(filter){
			if(envTemp > 35 || envTemp < 21.1  ){
				app_alert("环境温度已超出范围(21.1-35)！")
				return;
			}
			
			if( RH > 80 || RH < 30  ){
				app_alert("相对湿度已超出范围(30-80)！")
				return;
			}
			
			if(wind > 2.54 || wind < 0 ){
				app_alert("风速已超出范围(0-2.54)！")
				return;
			}
		}
		
		
		
		// 目标温度
		var targetTemp = 35;
		// 目标温度区间差值
		var targetTempDis = 2.8;
		// 温度区间最小值
		var targetMinTemp = 0;
		// 温度区间最大值
		var targetMaxTemp = 0;
		if(envTemp > 32.2 && envTemp <= 35){
			targetTemp = 35;
			targetTempDis = 2.8;
		}
		if(envTemp > 29.4 && envTemp <= 32.2){
			targetTemp = 32.2;
			targetTempDis = 2.8;
		}
		if(envTemp > 26.6 && envTemp <= 29.4){
			targetTemp = 29.4;
			targetTempDis = 2.8;
		}
		if(envTemp > 23.9 && envTemp <= 26.6){
			targetTemp = 26.6;
			targetTempDis = 2.7;
		}
		if(envTemp >= 21.1 && envTemp <= 23.9){
			targetTemp = 23.9;
			targetTempDis = 2.8;
		}
		
		targetMinTemp = (targetTemp - targetTempDis).toFixed(1);
		targetMaxTemp = targetTemp;

		// 环境温度与目标温度的差距
		var intervalTemp = (targetTemp - envTemp).toFixed(1);
		console.log("targetMinTemp:" + targetMinTemp)
		console.log("targetMaxTemp:" + targetMaxTemp)
		console.log("intervalTemp:" + intervalTemp)
		
		// 目标湿度
		var targetRH = 30;
		// 目标湿度区间差值
		var targetRHDis = 20;
		// 湿度区间最小值
		var targetMinRH = 0;
		// 湿度区间最大值
		var targetMaxRH = 0;
		
		if(RH >= 30 && RH <= 50){
			targetRH = 50;
			targetRHDis = 20;
		}
		if(RH > 50 && RH <= 70){
			targetRH = 70;
			targetRHDis = 20;
		}
		if(RH > 70 && RH <= 80){
			targetRH = 80;
			targetRHDis = 10;
		}
		
		targetMinRH = (targetRH - targetRHDis).toFixed(1);
		targetMaxRH = targetRH;
		
		// 环境湿度与目标湿度的差距
		var intervalRH = (targetRH - RH).toFixed(1);
		console.log("targetMinRH:" + targetMinRH)
		console.log("targetMaxRH:" + targetMaxRH)
		console.log("intervalRH:" + intervalRH)
		
		// 目标风速
		var targetWind = 0;
		// 目标风速区间差值
		var targetWindDis = 0;
		// 风速区间最小值
		var targetMinWind = 0;
		// 风速区间最大值
		var targetMaxWind = 0;
		
		if(wind >= 0 && wind <= 0.5){
			targetWind = 0.5;
			targetWindDis = 0.5;
		}
		if(wind > 0.5 && wind <= 1.1){
			targetWind = 1.1;
			targetWindDis = 0.6;
		}
		if(wind > 1.1 && wind <= 1.52){
			targetWind = 1.52;
			targetWindDis = 0.42;
		}
		if(wind > 1.52 && wind <= 2.03){
			targetWind = 2.03;
			targetWindDis = 0.51;
		}
		if(wind > 2.03 && wind <= 2.54){
			targetWind = 2.54;
			targetWindDis = 0.51;
		}
		
		targetMinWind = (targetWind - targetWindDis).toFixed(2);
		targetMaxWind = targetWind;
		// 环境风速与目标风速的差距
		var intervalWind = (targetWind - wind).toFixed(2);
		console.log("targetMinWind:" + targetMinWind)
		console.log("targetMaxWind:" + targetMaxWind)
		console.log("intervalWind:" + intervalWind)
		
		// 相对湿度和风速在一定条件下，体感温度和环境温度一样
		if(RH >= 30 && RH <= 50 && wind == 0){
			apparentTemp = envTemp;
		}else{
			
			// 体感温度下限
			var feelsLikeMin = 0;
			// 体感温度上限
			var feelsLikeMax = 0;
			
			var fromIndex = 0;
			var endIndex = baseData.length;
			
			/**
			按照从温度由高到低，湿度由低到高，风速由低到高定位
			*/
			var base1 = null;
			var base2 = null;
			var base3 = null;
			var base4 = null;
			var base5 = null;
			var base6 = null;
			var base7 = null;
			var base8 = null;
					
			// 如果温度小于30,从数组后面开始查找，否则从头开始找
			if(targetTemp < 30){
				fromIndex = baseData.length;
				endIndex = 0;
				for(var index = fromIndex - 1; index >= endIndex ; index--){
					var baseInfo = baseData[index];
					//console.log(baseData[index]);
					/***
					1.找到环境温度所在区间
					2.找到环境湿度所在区间
					3.找到环境风速所在区间
					4.计算环境温度下标准相对湿度区间的值
					
					*/
					if(baseInfo.temp == targetMaxTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base1 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base2 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base3 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base4 = baseInfo;
							}
						}
					}
					
					if(baseInfo.temp == targetMinTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base5 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base6 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base7 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base8 = baseInfo;
							}
						}
						
					}
					
					if(base1 != null && base2 != null && base3 != null && base4 != null && base5 != null && base6 != null && base7 != null && base8 != null){
						break;
					}
					
				}
			}else{
				for(var index = fromIndex; index < endIndex ; index++){
					var baseInfo = baseData[index];
					//console.log(baseData[index]);
					if(baseInfo.temp == targetMaxTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base1 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base2 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base3 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base4 = baseInfo;
							}
						}
					}
					
					if(baseInfo.temp == targetMinTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base5 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base6 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base7 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base8 = baseInfo;
							}
						}
						
					}
					
					if(base1 != null && base2 != null && base3 != null && base4 != null && base5 != null && base6 != null && base7 != null && base8 != null){
						break;
					}
				}
			}
			
			// 计算环境温度对应的标准湿度风速数据
			var envData1 = new Object();
			envData1.temp = envTemp;
			envData1.RH = targetMinRH;
			envData1.wind = targetMinWind;
			envData1.feelsLike = base1.feelsLike - (base1.feelsLike - base5.feelsLike)*intervalTemp/(targetTempDis);
			
			var envData2 = new Object();
			envData2.temp = envTemp;
			envData2.RH = targetMinRH;
			envData2.wind = targetMaxWind;
			envData2.feelsLike = base2.feelsLike - (base2.feelsLike - base6.feelsLike)*intervalTemp/(targetTempDis);
			
			
			var envData3 = new Object();
			envData3.temp = envTemp;
			envData3.RH = targetMaxRH;
			envData3.wind = targetMinWind;
			envData3.feelsLike = base3.feelsLike - (base3.feelsLike - base7.feelsLike)*intervalTemp/(targetTempDis);
			
			var envData4 = new Object();
			envData4.temp = envTemp;
			envData4.RH = targetMaxRH;
			envData4.wind = targetMaxWind;
			envData4.feelsLike = base4.feelsLike - (base4.feelsLike - base8.feelsLike)*intervalTemp/(targetTempDis);
			
			// 由4个点的标准值计算环境湿度对应的标准风速值
			var envData5 = new Object();
			envData5.temp = envTemp;
			envData5.RH = RH;
			envData5.wind = targetMinWind;
			envData5.feelsLike = envData3.feelsLike - (envData3.feelsLike - envData1.feelsLike)*intervalRH/(targetRHDis);
			
			var envData6 = new Object();
			envData6.temp = envTemp;
			envData6.RH = RH;
			envData6.wind = targetMaxWind;
			envData6.feelsLike = envData4.feelsLike - (envData4.feelsLike - envData2.feelsLike)*intervalRH/(targetRHDis);
			
			var envData7 = new Object();
			envData7.temp = envTemp;
			envData7.RH = RH;
			envData7.wind = wind;
			envData7.feelsLike = envData6.feelsLike - ((envData6.feelsLike - envData5.feelsLike)*intervalWind/(targetWindDis)).toFixed(1);
			
			console.log("envData1:" + JSON.stringify(envData1))
			console.log("envData2:" + JSON.stringify(envData2))
			console.log("envData3:" + JSON.stringify(envData3))
			console.log("envData4:" + JSON.stringify(envData4))
			console.log("envData5:" + JSON.stringify(envData5))
			console.log("envData6:" + JSON.stringify(envData6))
			console.log("envData7:" + JSON.stringify(envData7))
			
			apparentTemp = envData7.feelsLike;
			
		}
		
		
		//apparentTemp = 37 - (37 - envTemp)/(0.68 - 0.0014*RH + 1/(1.76 + 1.4*Math.pow(wind,0.75))) - 0.29*envTemp*(1 - 0.01*RH);
		
		//console.log(typeof apparentTemp)
		
		$scope.calcData.apparentTemp = Math.round(apparentTemp*10)/10;
		//$scope.calcData.apparentTemp = $scope.calcData.apparentTemp.toFixed(1);
	}
	
	$scope.forageTotal = function(){
		
	}
	
	$scope.backFun = function(){
		$state.go("about");
	}
	
	

})


// 农场报警设置
.controller("farmAlarmSettingCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var users = sparraw_user.userinfos;
	var farmId = $scope.sparraw_user_temp.farminfo.id;
	var alarmType = 0;
	var role = 4;
	var houseId = 0;
	var members = new Array();
	for(var index = 0; index < users.length; index++){
		//console.log(index)
		var user = users[index];
		//console.log(index +","+user)
		if(user != null && user.role != 4){
			members.push(user);
		}
		
	}
	console.log("users:" + JSON.stringify(users));
	//console.log("members:" + JSON.stringify(members));
	
	$scope.backFun = function(){
		$state.go("about");
	}
	
	$scope.getName = function (userId){
		var username = "";
		for(var index = 0; index < users.length; index++){
			//console.log(index)
			var user = users[index];
			//console.log(index +","+user)
			if(user != null && user.id == userId){
				username = user.name;
				break;
			}
		}
		
		return username;
	}
	
	 $scope.farmData = {
		"checked":true,
		"alarmer1":"各栋饲养员",
		"alarmer2":0,
		"alarmer2s":members,
		"alarmer3s":[],
		"alarmer3":0,
		"alarmers":[],
		"enableds":[],
		"farmAlarmSetting":{}
     };
	 
	 
	var members3 = [];
	$scope.selectUser = function(){
		members3 = [];
		var alarmer2 = $scope.farmData.alarmer2;
		//console.log("alarmer2:" + alarmer2);
		//members3.push({"tele":"13920160712","id":0,"name":"请选择","role":2})
		for(var index = 0; index < members.length; index++){
			//console.log(index)
			var user = members[index];
			//console.log(index +","+user)
			if(user != null && user.id != alarmer2){
				members3.push(user);
			}
			
		}
		$scope.farmData.alarmer3s = members3;
	}
	
	$scope.query = function(){
		
		
		var params = {
			"FarmId" :  farmId,
			"HouseId":houseId,
			"RemindMethod" :  alarmType
		};
		
		Sparraw.ajaxPost('sys/farm/remind/querySettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				var enableds = data.ResponseDetail.enableds;
				var alarmers = data.ResponseDetail.alarmers;
				
				var farmAlarmSetting = data.ResponseDetail.farmAlarmSetting;
				
				
				if(farmAlarmSetting.switchReleHouse == undefined){
					//farmAlarmSetting = new Object();
					//farmAlarmSetting.id = 0;
					farmAlarmSetting = {"alarmReleHouse":"N","bak1":"","bak2":"","farmId":farmId,"personReleHouse":"N","remindMethod":alarmType,"switchReleHouse":"N","id":-1}
				}
				if(enableds.length > 0){
					if(enableds[0].status == "Y"){
						$scope.farmData.checked = true;
					}else{
						$scope.farmData.checked = false;
					}
				}else{
					var obj = {"farmId":farmId,"houseId":houseId,"remindMethod":alarmType,"status":"Y","id":-1,"bak1":"","bak2":""}
					
					enableds.push(obj);
				}
				
				if(alarmers.length > 0){
					for(var index = 0; index < alarmers.length; index++){
						//console.log(index)
						var user = alarmers[index];
						//console.log(index +","+user)
						if(user != null){
							var level = user.userOrder;
							var userId = user.userId;
							var userType = user.userType;
							// 0:用户;1:角色
							if(level == 1){
								//role
								if(userType == 0){
									$scope.farmData.alarmer1 = $scope.getName(userId);
								}else if(userType == 1){
									$scope.farmData.alarmer1 = "各栋饲养员";
								}
							}
							
							if(level == 2){
								$scope.farmData.alarmer2 = userId;
								//getElementById("erji").option[index]
							}
							
							if(level == 3){
								$scope.farmData.alarmer3 = userId;
							}
						}
						
					}
				}else{
					
					// 默认选择第一个
					if(members.length > 0)
					{
						$scope.farmData.alarmer2 = members[0].id;
						
						$scope.selectUser();
						if(members3.length > 0){
							$scope.farmData.alarmer3 = members3[0].id;
						}
						
					}
					
					
					alarmers.push({"remindMethod":alarmType,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":4,"userOrder":1,"userType":1,"bak1":"","bak2":""});
					alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":2,"userType":0});
					alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":3,"userType":0});
				}
				
				$scope.farmData.alarmers = alarmers;
				$scope.farmData.enableds = enableds;
				$scope.farmData.farmAlarmSetting = farmAlarmSetting;
				$scope.selectUser();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
		
	}
	
	$scope.query();
	
	$scope.save = function(){
		var alarmer2 = $scope.farmData.alarmer2;
		console.log("alarmer2:" + alarmer2);
		var alarmer3 = $scope.farmData.alarmer3;
		console.log("alarmer3:" + alarmer3);
		console.log("checked:" + $scope.farmData.checked);
		
		if(alarmer2 == 0){
			//Sparraw.myNotice("保存成功。");
		}
		
		if($scope.farmData.checked == true){
			$scope.farmData.enableds[0].status = "Y";
		}else{
			$scope.farmData.enableds[0].status = "N";
		}
		
		
		if($scope.farmData.alarmers[0] == null || $scope.farmData.alarmers[0] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":4,"userOrder":1,"userType":1,"bak1":"","bak2":""});
		}
		if($scope.farmData.alarmers[1] == null || $scope.farmData.alarmers[1] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":2,"userType":0});
		}
		if($scope.farmData.alarmers[2] == null || $scope.farmData.alarmers[2] == undefined){
			$scope.farmData.alarmers.push({"remindMethod":alarmType,"bak1":"","bak2":"","createPerson":0,"createTime":null,"farmId":farmId,"houseId":houseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":0,"userOrder":3,"userType":0});
		}
		
		if(members.length > 0)
		{
			$scope.farmData.alarmers[1].userId = alarmer2;
		}
		
		if(members3.length > 0){
			$scope.farmData.alarmers[2].userId = alarmer3;
		}
		
		var params = {
			"FarmId" :  farmId,
			"HouseId": houseId,
			"RemindMethod" :  alarmType,
			"alarmers":$scope.farmData.alarmers,
			"enableds":$scope.farmData.enableds,
			"farmAlarmSetting":$scope.farmData.farmAlarmSetting,
			"alarmCodes":[
							{"houseId":houseId,"alarmCode":"B001H"},
							{"houseId":houseId,"alarmCode":"B001L"},
							{"houseId":houseId,"alarmCode":"C0002"}
			]
		 };
		Sparraw.ajaxPost('sys/farm/remind/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("保存成功。");
				var enableds = data.ResponseDetail.enableds;
				var alarmers = data.ResponseDetail.alarmers;
				var farmAlarmSetting = data.ResponseDetail.farmAlarmSetting;
				
				$scope.farmData.alarmers = alarmers;
				$scope.farmData.enableds = enableds;
				$scope.farmData.farmAlarmSetting = farmAlarmSetting;
				
				//$scope.query();
				//$scope.selectUser();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
		
	}
	
	
	$scope.pushNotificationChange = function(){
		if($scope.farmData.checked == true){
			//console.log("true");
			//document.getElementById('brokenEggsDIV').style.display = '';
			
		}else{
			//console.log("false");
			//document.getElementById('brokenEggsDIV').style.display = 'none';
		}
	}
	 
		
})

// Cordova测试
.controller("alarmTestCtrl",function($scope, $state) {
	/*var tNotificationID = null;
	$scope.add = function() {
		tNotificationID = APP_Notification.addOneNotification("alarm","title1","text1");
    };
	$scope.update = function() {
        APP_Notification.updateNotification(tNotificationID,'titleUpdate','textUpdate');
    };
    $scope.cancle = function() {
        APP_Notification.cancleNotification(tNotificationID);
    };
	
	var src = "sounds/4611.wav";
	$scope.playd = function() {
	   APP_Media.stopAudio();
	   var realPath = getMediaURL(src);
	   APP_Media.playAudio(realPath);
    }

	$scope.stopd = function() {
	   APP_Media.stopAudio();
    }
	
	$scope.alarm = function(seconds) {
		setTimeout(function(){
			$scope.playd();
			$scope.add();
		}, 1000*seconds);
    }
	
    $scope.deadline = function() {
		var options = {
		  date: new Date(),
		  mode: 'date'
		};
		datePicker.show(options, function(d) {
			 $scope.display = d;
		});
	}

	$scope.detectNet = function() {
		if(isOnLine_Wifi()){
			app_alert('wifi');
		}else if(isOnLine()){
			app_alert('isOnLine');
		}else{
			app_alert('isOffLine');
		}
	}

	$scope.vibrate = function(seconds) {
		setTimeout(function(){
			app_vibrate([3000,1000,3000,1000,3000]);
		}, 1000*seconds);
	};

	$scope.vibrate2 = function() {
		app_vibrate([1000]);	
	};

	$scope.alertClick = function() {
		app_alert('欢迎使用alert对话框',null,null,function(){
			alert("alert完毕。");
		});	
	};
	$scope.confirmClick = function() {
		app_confirm('需要使用Confirm吗？',null,null,function(buttonIndex){
			if(buttonIndex == 1){
				app_alert('您选择了【取消】');
			}else if(buttonIndex == 2){
				app_alert('您选择了【确定】');
			}
		});	
	};
	$scope.promptClick = function() {
		app_prompt('请输入您的银行卡密码',null,null,function(results){
			app_alert(JSON.stringify(results));
			if(results.buttonIndex == 1){
				app_alert('您选择了【取消】,值:' + results.input1);
			}else if(results.buttonIndex == 2){
				app_alert('您选择了【确定】,值:' + results.input1);
			}
		},'1234567');
	};

	Sparraw.intoMyController($scope, $state);
	$scope.times = [] ;
	$scope.results = '' ;
	var taskId = null;
	$scope.startTask = function(){
		taskId = setInterval(function(){
			var params = {
		      'operation'      : 'needAlarm'
		    };
		    
		    Sparraw.ajaxPost('envCtrl/needAlarm.action', params, function(data){ 
				$scope.results = data.ResponseDetail.AlarmStatus;
				var myDate = new Date();
				var mytime=myDate.toLocaleTimeString();
				$scope.times.push(mytime);
		    });
		}, 3000);
		
	}

	$scope.Wakelock = function(){
		window.powerManagement.acquire(function() {
		    app_alert('Wakelock acquired');
		}, function() {
		    app_alert('Failed to acquire wakelock');
		});
	};
	$scope.dim = function(){
		window.powerManagement.dim(function() {
		    app_alert('Wakelock dim acquired');
		}, function() {
		    app_alert('Failed to acquire dim wakelock');
		});
	};
	$scope.release = function(){
		window.powerManagement.release(function() {
		    app_alert('Wakelock released');
		}, function() {
		    app_alert('Failed to release wakelock');
		});
	};
	$scope.setReleaseOnPause = function(){
		window.powerManagement.setReleaseOnPause(false, function() {
		    app_alert('Set successfully');
		}, function() {
		    app_alert('Failed to set');
		});
	};
	$scope.openOldApp =function (){
		var iframe = document.createElement("iframe");
	    iframe.style.cssText = "display:none;width:0px;height:0px;";
	    document.body.appendChild(iframe);
	    iframe.src="smtc999://sparrowOld";
	    alert(tttt);
	}*/

		//RSA加密测试
		/*RSAUtils.setMaxDigits(200);
		var publicKeyExponent = "10001";
		var publicKeyModulus = "b39f2a4ee7392f5e649a189bbc4609b9399b25c5929a9a4ff6738b24df3402a7b727d6e3c177a878eca91519b1c14255889401b9acdc351f215cb809943429794dadb955d8feb4a1865b6b232be2e3ecfd14feb1af78fc04cdd2f0490dc62dd92e6b8c3c89231f84c6d7f1ec65b0300e54bf74404844cf81cee7892b593f28d5";
		var orgPwd = "23!@#$%^&*()_+~,./';‘；【】、32{\"aaa\":we,\"bbb\":23}";
		var key = new RSAUtils.getKeyPair(publicKeyExponent, "",
				publicKeyModulus);
		var encrypedPwd = RSAUtils.encryptedString(key, orgPwd);
		alert(encrypedPwd);*/



	/*var headerStyle = '<div style="width:40px;height:20px; background:red;"></div>销售<br/>日期<div style="width:40px;height:20px; background:red;"></div>';
	var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>'

	var showTableData = {
		'header' : [{
			'name'                :  'TEST',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST2',//key
			'width'               :  '200',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST3',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST4',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST5',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST6',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		},{
			'name'                :  'TEST7',//key
			'width'               :  '100',//宽
			'displayName'         :  '呵呵后',//表头文字
			'headerCellTemplate'  :  headerStyle,//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
			'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
			'headerCellClass'     :  '',//修改表格style(在css里写)
			'cellClass'           :  '' //修改表格style(在css里写)
		}],
		'firstFixed': true, //首列是否固定ture-固定，false-不固定
		'rowHeight' : '',//内容高度
		'TableData' :[{
			'TEST':'红红火火恍恍惚惚',//需要与header.name保持一致
			'TEST2':'红红火火恍恍惚惚',//需要与header.name保持一致
			'TEST3':'红红火火恍恍惚惚',
			'TEST4':'红红火火恍恍惚惚',
			'TEST5':'红红火火恍恍惚惚',
			'TEST6':'红红火火恍恍惚惚',
			'TEST7':'红红火火恍恍惚惚'
		}]
	}
	GetShowTable(showTableData,$scope);*/









	//一共多少条线	
	var sumObj = new Array(3);
	//一共多少数据
	var miniObj = new Array(160);
	for (var i = 0; i <miniObj.length ; i++) {
		miniObj[i] = parseInt(Math.random()*(250-0)+0);
	};
	for (var i = 0; i < sumObj.length; i++) {
		//每条数据有多少个
		var start = i * 55;
		var end = start + 55;
		sumObj[i] = miniObj.slice(start,end);
	};

	//随机颜色
	var getRandomColor = function(){
	  return  '#' +    
	    (function(color){    
	    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])    
	      && (color.length == 6) ?  color : arguments.callee(color);    
	  })('');    
	} 

	/*K线图数据*/
	//一共多少数据
	var kSingleData = new Array(196);
	for (var i = 0; i <kSingleData.length ; i++) {
		kSingleData[i] = parseInt(Math.random()*(100-80)+80);
	};
	//一条k线的数据
	var kTotalData = new Array(49);
	for (var i = 0; i < kTotalData.length; i++) {
		var start = i * 4;
		var end = start + 4;
		kTotalData[i] = kSingleData.slice(start,end);
	};
	console.log(kTotalData);



  var myChart  ;
  var option   ;

  var xData    ;
  var yData1   ;
  var yData2   ;
  var yData3   ;
  var yData4   ;




  xData = ['00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30','24:00'];//X轴数据
  //xData = ["12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)"];
  yData1 = sumObj[0];
  yData2 = sumObj[1];
  yData3 = sumObj[2];
  yData4 = sumObj[1];



  var tTitleName;

  var tLegend;
  var serialsName1;
  var serialsName2;
  var serialsName3;
  var serialsName4;


  var testY = [10,20,30];


  tTitleName = "温度K线图";
  tLegend = ['数据1','数据2','数据3','数据4'];//点击显示一条数据
  serialsName1 = "数据1";
  serialsName2 = "数据2";
  serialsName3 = "数据3";
  serialsName4 = "数据4";


   
  require.config({
      paths: {
          echarts: 'js/echarts-2.2.7'
      }
  });
  //显示多少（什么）数据
  require(
      [
          'echarts',
          'echarts/chart/line',
      ],
      function (ec) {
          myChart = ec.init(document.getElementById('main'));
          option = {
				tooltip : {
                    trigger: 'axis',
                    textStyle:{
                      fontSize:13
                	},
                    backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
                },
                legend: {
                    data:tLegend,
                    selected:{//显示部分数据
                    	"目标温度":false,
                    	"温度移动平均":false,
                    	"湿度":false,
                    	"数据4":false
                    }

                },
                grid://表对应上下左右的大小
                {
                    x:50,
                    y:30,
                    x2:20,
                    y2:30
                },
                xAxis : [
                    {

                        type : 'category',
                        data : xData,
                        nameLocation:'start',
                        show :true,//是否显示x轴
                        axisTick : true,
                        axisLabel:{
                        	//margin:10 //文字与x轴的距离
                        },
                        /*splitLine:{

                        },*/
                        splitLine:true,
                        axisLine:{
                        	lineStyle:{//x轴风格
                        		color: '#000',
                        		width:0.5
                        	}
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                            //formatter: '{value}'//左边的数据
                        	//rotate:45//刻度旋转
                        	//clickable:true//y轴刻度是否能被点击
                        	formatter:function (params,ticket,callback) {
                                var res = "";
                               	for (var i = 0; i < testY.length; i++) {
                               		res =+ testY[i];
                               	}
                                  
                                return res;
                            }
                        },

                        splitNumber:5,//设置y轴刻度分段默认为5
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#000',
                        		width:1
                        	}
                        }
                    },
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                              show:false,//是否显示
                              formatter: '{value}'//右边的数据
                          },
                        splitLine : false,
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#fff',
                        		width:1
                        	}
                        }
                    }
                ],
                series : [
                    {
	                  name:serialsName1,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,//是否折线
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName2,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData2,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName3,

	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData3,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    },{
	                  name:serialsName4,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData4,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    }
                    

                ]
            };

          myChart.on('click',function(param){
			 	console.log(param.name);
			 });



          myChart.on(option);
          myChart.setOption(option);
          window.onresize = myChart.resize;
      }
  );

	
 $scope.apparentTempCalc = function(){
	$state.go("apparentTempCalc");
 };

})


