
// Ionic Starter App

angular.module('myApp', ['ionic','myApp.controllers','myApp.services','ionic-datepicker','ngCordova','ngTouch', 'ui.grid', 'ui.grid.pinning','ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection', 'ui.grid.resizeColumns','ui.grid.autoResize'],function($httpProvider) {
  // Use x-www-form-urlencoded Content-Type
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
 
  /**
   * The workhorse; converts an object to x-www-form-urlencoded serialization.
   * @param {Object} obj
   * @return {String}
   */ 
  var param = function(obj) {
    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
      
    for(name in obj) {
      value = obj[name];
        
      if(value instanceof Array) {
        for(i=0; i<value.length; ++i) {
          subValue = value[i];
          fullSubName = name + '[' + i + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value instanceof Object) {
        for(subName in value) {
          subValue = value[subName];
          fullSubName = name + '[' + subName + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value !== undefined && value !== null)
        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
    }
      
    return query.length ? query.substr(0, query.length - 1) : query;
  };
 
  // Override $http service's default transformRequest
  $httpProvider.defaults.transformRequest = [function(data) {
    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
})



/*.run(function($ionicPlatform,$rootScope) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
*/
.run(['$ionicPlatform','$rootScope', '$location', '$timeout', '$ionicHistory','$cordovaToast','$state', 
    function ($ionicPlatform,$rootScope, $location, $timeout, $ionicHistory,$cordovaToast,$state) {
    $ionicPlatform.ready(function () {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
        //cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      }
      if (window.StatusBar) {
        // org.apache.cordova.statusbar required
        StatusBar.styleLightContent();
      }
      // 设置 设备的Imei号
      setDeviceImei();

      if (judgeDevice()=="iphone") {
        cordova.plugins.Keyboard.disableScroll(true);
      }else{

      };
      // 保持竖屏
      app_lockOrientation('portrait');     

      initJpushUI();
});


    
    //主页面显示退出提示框
    $ionicPlatform.registerBackButtonAction(function (e) {

        //判断处于哪个页面时双击退出
        if ($location.path() == '/homeurl' ) {
              app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        userLogout();
                        $state.go("landingPage");
                   }
              });
        }else if ($location.path().indexOf('/landingPageurl') == 0 || $location.path() == '/landingProtocolurl') {
              // 按钮两次，退出App
              if ($rootScope.backButtonPressedOnceToExit) {
                  ionic.Platform.exitApp();
              } else {
                  $rootScope.backButtonPressedOnceToExit = true;
                  $cordovaToast.showShortCenter('再按一次退出系统');
                  setTimeout(function () {
                      $rootScope.backButtonPressedOnceToExit = false;
                  }, 1500);
              }
        }else if($location.path() == '/farmRegisteredurl' 
                      || $location.path() == '/buildingTable' 
                      || $location.path() == '/employeesTable'){
              // $cordovaToast.showShortCenter('请点击左上角进行返回。');
        }else if($ionicHistory.backView()){
              if($location.path().indexOf('/report') == 0 
                  || $location.path().indexOf('/productionDailyurl') == 0 
                  || $location.path().indexOf('/productPerformStanderurl') == 0 
                  || $location.path().indexOf('/weeklyurl') == 0 
                  ){
                  app_lockOrientation('portrait');
              }
              $ionicHistory.goBack();
        }else {
              // 按钮两次，退出App
              if ($rootScope.backButtonPressedOnceToExit) {
                  ionic.Platform.exitApp();
              } else {
                  $rootScope.backButtonPressedOnceToExit = true;
                  $cordovaToast.showShortCenter('再按一次退出系统');
                  setTimeout(function () {
                      $rootScope.backButtonPressedOnceToExit = false;
                  }, 1500);
              }
        }
        e.preventDefault();
        return false;
    }, 101);
}])

//cache:true-使用缓存 false-不使用缓存
.config(function($stateProvider,$urlRouterProvider,$ionicConfigProvider) {
  //禁止手势返回出现白屏
  $ionicConfigProvider.views.swipeBackEnabled(false);
  $stateProvider
// 用户注册
.state('useRegistered', {
      url:'/useRegisteredurl',
      templateUrl: "module/registered/useRegistered.html",
      controller: 'UseRegisteredCtrl'
    })
// 农场注册
.state('farmRegistered', {
      url:'/farmRegisteredurl',
      cache:false,
      templateUrl: "module/registered/farmRegistered.html",
      controller: 'FarmRegisteredCtrl'
    })
// 栋舍列表
.state('buildingTable', {
      url:'/buildingTable',
      cache:false,
      templateUrl: "module/registered/buildingTable.html",
      controller: 'BuildingTableCtrl'
    })
// 栋舍修改
.state('updateBuildingInfo', {
      url:'/buildingTable/buildingInfo/:buildingID',
      templateUrl: "module/registered/updateBuildingInfo.html",
      controller: 'updateBuildingInfoCtrl'
    })
// 栋舍添加
.state('addbuilding', {
      url:'/addbuildingurl',
      cache:false,
      templateUrl: "module/registered/addbuilding.html",
      controller: 'AddbuildingCtrl'
    })
// 员工列表
.state('employeesTable', {
      url:'/employeesTable',
      cache:false,
      templateUrl: "module/registered/employeesTable.html",
      controller: 'EmployeesTableCtrl'
    })
// 员工修改
.state('updateEmployeesInfo', {
      url:'/employeesTable/employeesInfo/:employeesID',
      templateUrl: "module/registered/updateEmployeesInfo.html",
      controller: 'updateEmployeesInfoCtrl'
    })
// 员工添加
.state('addEmployees', {
      url:'/addEmployeesurl',
      cache:false,
      templateUrl: "module/registered/addEmployees.html",
      controller: 'AddEmployeesCtrl'
    })


// 登陆协议
.state('landingProtocol', {
      url:'/landingProtocolurl',
      cache:false,
      templateUrl: "module/landing/landingProtocol.html",
      controller: 'landingProtocolCtrl'
    })
// 用户登录
.state('landingPage', {
      url:'/landingPageurl',
      cache:false,
      templateUrl: "module/landing/landingPage.html",
      controller: 'landingPageCtrl'
    })
// 忘记密码
.state('forgotPassword', {
      url:'/forgotPasswordurl',
      templateUrl: "module/landing/forgotPassword.html",
      controller: 'forgotPasswordCtrl'
    })
// 免责声明
.state('disclaimer', {
      url:'/disclaimerurl',
      templateUrl: "module/landing/disclaimer.html",
      controller: 'disclaimerCtrl'
    })



// 主页面
.state('home', {
      url:'homeurl',
      cache:false,
      templateUrl: "module/home/home.html",
      controller: 'homeCtrl'
    })
// 信息维护
.state('infoSafeguard', {
      url:'/infoSafeguard',
      cache:false,
      templateUrl: "module/home/infoSafeguard.html",
      controller: 'infoSafeguardCtrl'
    })
// 个人信息修改
.state('modifyUserInfo', {
      url:'/modifyUserInfourl',
      cache:false,
      templateUrl: "module/home/modifyUserInfo.html",
      controller: 'modifyUserInfoCtrl'
    })
// 关于页面
.state('about', {
      url:'/abouturl',
      templateUrl: "module/home/about.html",
      controller: 'aboutCtrl'
    })
//功能介绍
.state('functionIntroduce', {
      url:'/functionIntroduceurl',
      templateUrl: "module/home/functionIntroduce.html",
      controller: 'functionIntroduceCtrl'
    })
//联系我们
.state('contactUs', {
      url:'/contactUsurl',
      templateUrl: "module/home/contactUs.html",
      controller: 'contactUsCtrl'
    })
//意见反馈
.state('feedBack', {
      url:'/feedBackurl',
      templateUrl: "module/home/feedBack.html",
      controller: 'feedBackCtrl'
    })
// 权限控制
.state('accessControl', {
      url:'/accessControlurl',
      templateUrl: "module/home/accessControl.html",
      controller: 'accessControlCtrl'
    })



// 农场管理
.state('batchManage', {
      url:'/batchManageurl',
      cache:false,
      templateUrl: "module/batchManage/batchManage.html",
      controller: 'batchManageCtrl'
    })
// 新建批次
.state('newBatch', {
      url:'/newBatchurl',
      cache:false,
      templateUrl: "module/batchManage/newBatch.html",
      controller: 'newBatchCtrl'
    })
// 批次结算
.state('batchClearing', {
      url:'/batchClearingurl',
      cache:false,
      templateUrl: "module/batchManage/batchClearing.html",
      controller: 'batchClearingCtrl'
    })
//盈利报告
.state('profitReport', {
      url:'/profitReporturl',
      cache:false,
      templateUrl: "module/batchManage/profitReport.html",
      controller: 'profitReportCtrl'
    })
//多批盈利查询
.state('moreBatchProfit', {
      url:'/moreBatchProfiturl',
      cache:false,
      templateUrl: "module/batchManage/moreBatchProfit.html",
      controller: 'moreBatchProfitCtrl'
    })
//多批结算查询
.state('moreBatchClearing', {
      url:'/moreBatchClearingurl',
      cache:false,
      templateUrl: "module/batchManage/moreBatchClearing.html",
      controller: 'moreBatchClearingCtrl'
    })
//生产性能管理
.state('productPerformStander',{
      url:'/productPerformStanderurl',
      cache:false,
      templateUrl:"module/batchManage/productPerformStander.html",
      controller:'productPerformStanderCtrl'
    })
//累计盈利报告
.state('totalProfit',{
      url:'/totalProfiturl',
      cache:false,
      templateUrl:"module/batchManage/totalProfit.html",
      controller:'totalProfitCtrl'
    })


//历史数据导入
.state('historyDataImport',{
    url:'/historyDataImporturl',
    cache:false,
    templateUrl:"module/batchManage/historyDataImport.html",
    controller:'historyDataImportCtrl'
    })
//月度成本核算
.state('monthCost',{
      url:'/monthCosturl',
      cache:false,
      templateUrl:"module/batchManage/monthCost.html",
      controller:'monthCostCtrl'
    })
//青年鸡身价摊销
.state('youngChickenWorth',{
      url:'/youngChickenWorthurl',
      cache:false,
      templateUrl:"module/batchManage/youngChickenWorth.html",
      controller:'youngChickenWorthCtrl'
    })
//月度盈利报告
.state('breedMonthProfit', {
      url:'/breedMonthProfiturl',
      cache:false,
      templateUrl:"module/batchManage/breedMonthProfit.html",
      controller:'breedMonthProfitCtrl'
    })
//生产报表
.state('dailyTable', {
      url:'/dailyTableurl',
      cache:false,
      templateUrl: "module/dataInput/dailyTable.html",
      controller: 'dailyTableCtrl'
    })
// 生产记录
.state('dailyReport', {
      url:'/dailyReporturl/:receiveHouseId',
      cache:false,
      templateUrl: "module/dataInput/dailyReport.html",
      controller: 'dailyReportCtrl'
    })

//生产周报
.state('weekly', {
      url:'/weeklyurl/:receiveHouseId',
      cache:false,
      templateUrl: "module/dataInput/weekly.html",
      controller: 'weeklyCtrl'
    })

// 日报统计
.state('dailyStatistical', {
      url:'/dailyStatisticalurl/:receiveHouseId',
      templateUrl: "module/dataInput/dailyStatistical.html",
      controller: 'dailyStatisticalCtrl'
    })
// 生产日报
.state('productionDaily', {
      url:'/productionDailyurl/:receiveHouseId/:fromPage',
      cache:false,
      templateUrl: "module/dataInput/productionDaily.html",
      controller: 'productionDailyCtrl'
    })
// 入雏管理
.state('docPlace', {
      url:'/dailyReporturl/docPlaceurl/:HouseId',
      cache:false,
      templateUrl: "module/dataInput/docPlace.html",
      controller: 'docPlaceCtrl'
    })
// 环控管理表
.state('envMonitoringTable', {
      url:'/envMonitoringTableurl',
      cache:false,
      templateUrl: "module/envCtrl/envMonitoringTable.html",
      controller: 'envMonitoringTableCtrl'
    })
// 环境监测
.state('envMonitoring', {
      url:'/envMonitoring',
      cache:false,
      templateUrl: "module/envCtrl/envMonitoring.html",
      controller: 'envMonitoringCtrl'
    })
//报警延迟处理
.state('alarmLogDelay', {
      url:'/envMonitoring/alarmLogDelay/:receiveHouseId/:dayAge',
      cache:false,
      templateUrl: "module/envCtrl/alarmLogDelay.html",
      controller: 'alarmLogDelayCtrl'
    })
// 环控温度表
.state('tempChart', {
      url:'/report/tempChart/:receiveHouseId/:area',
      cache:false,
      templateUrl: "module/report/tempChart.html",
      controller: 'tempChartCtrl'
    })

// 环控报警统计
.state('alarmStatistics', {
      url:'/alarmStatistics',
      templateUrl: "module/envCtrl/alarmStatistics.html",
      cache:false,
      controller: 'alarmStatisticsCtrl'
    })
// 报警日志
.state('alarmLog', {
      url:'/alarmStatistics/alarmLog/:receiveHouseId/:buildingDayAge',
      cache:false,
      templateUrl: "module/envCtrl/alarmLog.html",
      controller: 'alarmLogCtrl'
    })
// 报警设置
.state('alarmSettings', {
      url:'alarmSettings',
      cache:false,
      templateUrl: "module/envCtrl/alarmSettings.html",
      controller: 'alarmSettingsCtrl'
    })
// 语音设置
.state('voiceSettings', {
      url:'voiceSettings',
      cache:false,
      templateUrl: "module/envCtrl/voiceSettings.html",
      controller: 'voiceSettingsCtrl'
    })
// 数据分析
.state('dataAnalyseTable', {
      url:'/dataAnalyseTable',
      cache:false,
      templateUrl: "module/dataAnalyse/dataAnalyseTable.html",
      controller: 'dataAnalyseTableCtrl'
    })
// 温度K线图
.state('tempKMap', {
      url:'report/tempKMap',
      templateUrl: "module/dataAnalyse/tempKMap.html",
      controller: 'tempKMapCtrl'
    })

// 温湿度综合报表
.state('reportTempHumi', {
      url:'/report/reportTempHumiurl',
      cache:false,
      templateUrl: "module/report/reportTempHumi.html",
      controller: 'reportTempHumiCtrl'
    })


//死淘(成活率)率图
.state('cullDeathRate', {
      url:'/report/cullDeathRateurl',
      cache:false,
      templateUrl: "module/report/cullDeathRate.html",
      controller: 'cullDeathRateCtrl'
    })
//产蛋曲线
.state('eggWeigLayRate', {
      url:'/report/eggWeigLayRate',
      cache:false,
      templateUrl: "module/report/eggWeigLayRate.html",
      controller: 'eggWeigLayRateCtrl'
    })
//产蛋率曲线(演示)
.state('coluCurve', {
      url:'/coluCurveurl',
      templateUrl: "module/report/coluCurve.html",
      controller: 'coluCurveCtrl'
    })

//蛋重曲线
.state('eggWeigLay', {
      url:'/report/eggWeigLay',
      cache:false,
      templateUrl: "module/report/eggWeigLay.html",
      controller: 'eggWeigLayCtrl'
    })
//只鸡产蛋曲线
.state('onlyChickEggs', {
      url:'/report/onlyChickEggs',
      cache:false,
      templateUrl: "module/report/onlyChickEggs.html",
      controller: 'onlyChickEggsCtrl'
    })
//只鸡产蛋曲线Demo
.state('onlyChickEggsDemo', {
      url:'/report/onlyChickEggsDemo',
      cache:false,
      templateUrl: "module/report/onlyChickEggsDemo.html",
      controller: 'onlyChickEggsCtrlDemo'
    })
// 均重和饲料转换
/*
.state('reportEggWeightFeedRate', {
      url:'/report/reportEggWeightFeed',
      cache:false,
      templateUrl: "module/report/reportEggWeightFeedRate.html",
      controller: 'reportEggWeightFeedCtrl'
    })
*/
// 日采食曲饮水曲线
.state('reportFeedWaterRate', {
      url:'/report/reportFeedWaterRate',
      cache:false,
      templateUrl: "module/report/reportFeedWater.html",
      controller: 'reportFeedWaterCtrl'
    })
//料蛋比曲线 
.state('reportEggWeightFeed', {
      url:'/report/reportEggWeightFeed',
      cache:false,
      templateUrl: "module/report/reportEggWeightFeed.html",
      controller: 'reportEggWeightFeedCtrl'
    })
//水料比曲线
.state('waterFeed', {
      url:'/report/waterFeed',
      cache:false,
      templateUrl: "module/report/waterFeed.html",
      controller: 'waterFeedCtrl'
    })
//销售分析曲线
.state('salesAnalyze', {
      url:'/report/salesAnalyze/:lastPage',
      cache:false,
      templateUrl: "module/report/salesAnalyze.html",
      controller: 'salesAnalyzeCtrl'
    })
//体重曲线
.state('chickenWeightAnalyze', {
      url:'/report/chickenWeight/',
      cache:false,
      templateUrl: "module/report/chickenWeight.html",
      controller: 'chickenWeightAnalyzeCtrl'
    })
//生产指标汇总
.state('productionQuota', {
      url:'/report/productionQuota',
      cache:false,
      templateUrl: "module/report/productionQuota.html",
      controller: 'productionQuotaCtrl'
    })


//任务提醒
.state('taskRemind', {
      url:'/module/task/taskRemind.html',
      cache:false,
      templateUrl: "module/task/taskRemind.html",
      controller: 'taskRemindCtrl'
    })
//任务设定
.state('taskSet', {
      url:'/module/task/taskSet.html',
      cache:false,
      templateUrl: "module/task/taskSet.html",
      controller: 'taskSetCtrl'
    })
//任务列表
.state('taskTable', {
      url:'/module/task/taskTable/:TaskType',
      cache:false,
      templateUrl: "module/task/taskTable.html",
      controller: 'taskTableCtrl'
    })
//新增任务
.state('addTask', {
      url:'/module/task/addTask/:TaskType',
      cache:false,
      templateUrl: "module/task/addTask.html",
      controller: 'addTaskCtrl'
    })
//查看任务
.state('updateTask', {
      url:'/module/task/updateTask/:TaskType/:receiveTskID',
      cache:false,
      templateUrl: "module/task/updateTask.html",
      controller: 'updateTaskCtrl'
    })

//销售记录 2016年4月25日14:59:41
.state('eggSellsReport', {
      url:'/module/eggsells/eggSellsTableV2.html',
      cache:false,
      templateUrl: "module/eggsells/eggSellsTableV2.html",
      controller: 'eggSellsDataCtrlV2'
    })

//销售报表 2016年5月24日12:10:14
.state('eggSellsReportTable', {
      url:'/module/eggsells/eggSellsReport.html',
      cache:false,
      templateUrl: "module/eggsells/eggSellsReport.html",
      controller: 'eggSellsReportTableCtrl'
    })
//销售日报 2016年5月24日12:17:13
.state('eggSellsList', {
      url:'/module/eggsells/eggSellsTable/:fromPage',
      cache:false,
      templateUrl: "module/eggsells/eggSellsTable.html",
      controller: 'eggSellsDataCtrl'
    })

//测试界面
.state('test', {
      url:'/testurl',
      templateUrl: "module/test.html",
      controller: 'testCtrl'
    })
// 测试报警
.state('alarmTest', {
      url:'/alarmTesturl',
      cache:false,
      templateUrl: "module/home/alarmTest.html",
      controller: 'alarmTestCtrl'
    })
// 体感温度计算器
.state('apparentTempCalc', {
      url:'/apparentTempCalc',
      cache:false,
      templateUrl: "module/envCtrl/apparentTempCalc.html",
      controller: 'apparentTempCalcCtrl'
    })
// 农场报警设置
.state('farmAlarmSetting', {
      url:'/farmAlarmSetting',
      cache:false,
      templateUrl: "module/registered/farmAlarmSetting.html",
      controller: 'farmAlarmSettingCtrl'
    })
;

})