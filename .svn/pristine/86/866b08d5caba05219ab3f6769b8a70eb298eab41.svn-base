// 
angular.module('myApp.services', [])
.factory('AppData', function() {
	var farminfo = {
						id:'',
						name:'',
						address1:{ccode:'',ctext:''},
						address2:{ccode:'',ctext:''},
						address3:{ccode:'',ctext:''},
						address4:{ccode:'',ctext:''},
						address5:'',
						feedtype:{ccode:'',ctext:''}, // 饲养方式
						hideArea1:true, // 是否隐藏笼养尺寸
						cageInfo:{layer:'',row:''}, // 是否隐藏笼养尺寸
						corporation:{ccode:'',ctext:''}, //合作公司
						house_length:'',
						house_width:'',
						house_height:'',
						feedarea:'', // 养殖面积
						warm_type:{ccode:'',ctext:''}, // 取暖方式
						waterline:'', // 水线
						stockline:'', // 料线
						ventilate:'', // 通风
						spray:'', // 水帘
						controller:'', // 控制器
						houseinfos:[
									/* 格式示例
									{
									houseID:'',
									houseName:'', //栋编号
									h_length:'',
									h_width:'',
									h_height:'',
									feedarea:'', // 养殖面积
									mtc_device_id:''
									}*/
									
									]
					
					};
	var farmstaffs = [

					/*
					{
						id:'1',
						name: 'Sam',
						role:{ccode:'',ctext:''},// 角色信息:01-厂长、02-饲养员、03-技术员、04-农场老板、05-管理员、06-维修员
						tele: '',
						pw1: '',
						pw2: '',
						houses_authority:[]
					}
					*/

					];
	// 临时缓存变量
	var userTemp = {
					id:'',
					name: '',
					role:{ccode:'',ctext:''},// 角色信息:01-厂长、02-饲养员、03-技术员、04-农场老板、05-管理员、06-维修员
					hide_house_input:true, 
					tele: '',
					pw1: '',
					pw2: '',
					houses_authority:[]
				};
	var houseTemp = {
					houseID:'',
					houseName:'', //栋编号
					h_length:'',
					h_width:'',
					h_height:'',
					feedarea:'', // 养殖面积
					mtc_device_id:''
	};

	// 下拉选择页面的设置参数
	var codeSelectPara = {};
	
	var httpServer = 'http://192.168.10.112:8080/ttt/';

	var localJson = 'json/localdata.json';
	
	var selectItem = {ccode:'',ctext:''} ;  // 目前选中的选项

	return {
		// 直接获取农场场长信息
		getFarmer: function() {
			var temp ;
			for(var i in farmstaffs){
				if(farmstaffs[i].role == '01'){
					temp = farmstaffs[i] ;	
					break;
				}
			}
			return temp;
		},
		// 直接获取农场员工信息
		getFarmStaff: function(StaffId) {
			var temp ;
			if(StaffId == null){
				temp = farmstaffs ;
			}else{
				for(var i in farmstaffs){
					if(farmstaffs[i].id == StaffId){
						temp = farmstaffs[i] ;	
						break;
					}
				}
			}
			return temp;
		},
		setValue:function(paraName,paraValue){
			var jsStr = paraName + ' = ' + paraValue;
			eval(jsStr);
		},
		getValue:function(paraName){
			var returnValue ;
			returnValue = eval(paraName);
			return returnValue;
		},
		/**
			title 		: '',					// 下拉页面显示标题，如：'请选择省'
			codeType    : '',   				// 数据源codetype，如：'chinarea'
			paras       : [],   				// 数据源参数值，如 [value1,value2],注意顺序
			afterSelect : '',   				// 当进行选择返回时，执行的js语句
			returnHtml  : '',	      			// 返回页面标志	'farmRegistered'
			isSelect    : false,    			// 是否进行选择 'true'-是；'false'-否
			remoteFlag  : true    				// true-去服务端请求，false-本地请求
		*/
		setSelectPara:function(config){
			
			if(angular.isUndefined(config.remoteFlag)){
				config.remoteFlag = true ;
			}
			// console.log(config);
			angular.copy(config,codeSelectPara);
		},
		setSelectItem:function(item){
			angular.copy(item,selectItem);
		}
  };
})

.factory('crisisServiceFactory', function ($rootScope, $http) {
        return {
            getcrisisList: function () {
                return   $http.get('lib/crisisList.json');
            }
        };
    });
