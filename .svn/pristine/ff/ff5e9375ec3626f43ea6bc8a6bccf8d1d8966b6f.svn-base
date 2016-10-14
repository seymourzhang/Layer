/*************线性图表数据****************/
  /*
  xData       //x轴数据                                数据样式 = ['1','2','3','4','5'];
  yData       //y轴数据（总数据，包含几条以及所有点的数据）  数据样式:testData = [
                                                                ['10','20','30','40','50'],
                                                                ['50','40','30','20','10'],
                                                                ['60','54','32','12','32']
                                                              ];
  yName       //y轴各条数据的名字                        数据样式:testData = ["数据一","数据二","数据三"];
  yColor      //y轴各条数据的颜色                        数据样式:testData = ['red','blue','#EEEE00'];
  hiddenPara  //需要隐藏的线                            数据样式:testData = ["数据一", false , "数据二", false];
  clickFun    //获取被点击的参数
  yUnit       //Y轴计量单位       
  yLeftRange Y轴左边值区间,类型为数组，包含两个值，最小值，最大值 demo:[0,100]
  yRightRange Y轴右边值区间,类型为数组，包含两个值，最小值，最大值 demo : [[0,100]]
  */
function getLineChart(xShaftSumData,yShaftSumData,yName,yColor,hiddenPara,yLeft,yLeftName,clickFun,yLeftRange,yRightRange,yRightShow){

        var myChart ;
        var option ;

        var xData ;
        var yData;

        var tTitleName;
        var tLegend;
        var yName;
        var serialsName;
        var allYData = [];
        var hiddenData;
        var json = {};
        var hiddenPara;
        var yLeft;
        var yLeftName;

        // var yRightShow;
    
    var ylMin,ylMax,yRMax,yRMin;
    if(yLeftRange != undefined){
      if(yLeftRange[0] != undefined){
        ylMin = yLeftRange[0];
      }
      if(yLeftRange[1] != undefined){
        ylMax = yLeftRange[1];
      }
    }
    if(yRightRange != undefined){
      if(yRightRange[0] != undefined){
        yRMin = yRightRange[0];
      }
      if(yRightRange[1] != undefined){
        yRMax = yRightRange[1];
      }
    }


    if (yRightShow == undefined) {
        yRightShow = false;
    }else{

    }




    //定义线的数据
    xData = xShaftSumData;//X轴数据
    yData = yShaftSumData;//Y轴数据
    tTitleName = "曲线图";
    tLegend = yName;//Y轴名字


        //隐藏哪些数据
        for (var i=0;i<hiddenPara.length;i+=2) {
            json[hiddenPara[i]] = hiddenPara[i + 1];

        }
        hiddenData = {
              data:tLegend,
              selected:json
              
        };


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
                'echarts/chart/bar'
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

                          formatter: clickFun
                      },
                      legend: hiddenData,
                      grid://表对应上下左右的大小
                      {
                          x:50,
                          y:30,
                          x2:50,
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
                              splitLine:false,
                              axisLine:{
                                lineStyle:{//x轴风格
                                  color: '#000',
                                  width:0.5
                                }
                              }
                          }
                      ],
                      yAxis : [
                          {   name:yLeftName,
                              position:'left',
                              type : 'value',
                              nameTextStyle:{
                                fontSize:13
                              },
                              axisLabel : {
                                    formatter: '{value}' + yLeft//左边的数据
                                },
                              //splitLine:true,
                              scale: true,
                              axisLine:{
                                lineStyle:{
                                  color: '#000',
                                  width:1
                                }
                              },
                min:ylMin,
                max:ylMax
                          },{
                              name:yLeftName,
                              show:yRightShow,
                              position:'right',
                              type : 'value',
                              nameTextStyle:{
                                fontSize:13
                              },
                              axisLabel : {
                                    //formatter: '{value}%' + yLeft//右边的数据
                                    formatter:'{value}' + "%"
                                },
                              splitLine:false,
                              scale: true,
                              axisLine:{
                                lineStyle:{
                                  color: '#FFF',
                                  width:1
                                }
                              },
                min:yRMin,
                max:yRMax
                          }
                      ],
                      series : allYData
                  };



                //定义线的样式
                for (var i = 0; i < yName.length; i++) {
                    serialsName = yName[i];
                    if (yName[i] == "标准") {
                      allYData[i] = {
                            name:yName[i],
                            type:'bar',
                            data:yData[i],
                            itemStyle: {
                                normal: {
                                    color:"#E3E3E3",
                                }
                            }
                      }
                    }else{


                      if (yName[i] == "湿度") {
                          allYData[i] = {
                              name:yName[i],
                              type:'line',
                              yAxisIndex: 1,
                              data:yData[i],
                              smooth:true,//是否折线
                                symbolSize:0,//点得大小
                                itemStyle: {
                                normal: {
                                  color:yColor[i],
                                    lineStyle: {
                                      color:yColor[i],
                                      width:1
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



                      }else{
                          allYData[i] = {
                                  name:yName[i],
                                  type:'line',
                                  yAxisIndex: 0,
                                  smooth:true,//是否折线
                                  symbolSize:0,//点得大小
                                  data:yData[i],
                                    itemStyle: {
                                    normal: {
                                        color:yColor[i],
                                        lineStyle: {
                                          color:yColor[i],
                                          width:1
                                        }
                                    }
                                }
                            }
                      }

                      
                    }
                  };



                myChart.setOption(option);
                // window.onresize = myChart.resize;
                window.onresize = function(){};
            }
        );
  }
/*****************************/

//提醒建设中
function pointDevelop() {
  app_alert('模块正在建设中...','提示');
  return; 
};

/*****************************/

function NulltoZero(Obj){
  if (Obj === "" ||!Obj) {
    return 0;
  }else{
    return Obj;
  };
}

function ZerotoNull(Obj){
  if (Obj === 0) {
    return "";
  }else{
    return Obj;
  };
}

function isNull(Obj){
  if (Obj === "" ||!Obj) {
    return app_alert("尚有内容未填写...");
  }else{
    return true;
  };
}

/*****************************/


function GetShowTable(showTableData,scope){

  //数据格式
  /*var showTableData = {
    'header' : [{
      'name'                :  '',//key
      'width'               :  '',//宽
      'displayName'         :  '',//表头文字
      'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
      'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
      'headerCellClass'     :  '',//修改表格style(在css里写)
      'cellClass'           :  '' //修改表格style(在css里写)
    }],
    'firstFixed': '', //首列是否固定ture-固定，false-不固定
    'rowHeight' : '',//内容高度
    'TableData' :[{
      '':''//需要与header.name保持一致
    }]
  }*/
  window.onresize = function(){};
  scope.gridOptions = {
    rowHeight: showTableData.rowHeight,
  };
  scope.gridOptions.columnDefs = [];

  

  for (var i = 0; i < showTableData.header.length; i++) {
    if (i == 0  && showTableData.firstFixed == true) {
      scope.gridOptions.columnDefs.push({ 
                        name                :  showTableData.header[i].name                ,  
                        displayName         :  showTableData.header[i].displayName         , 
                        width               :  showTableData.header[i].width               ,
                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
                        cellClass           :  showTableData.header[i].cellClass           ,
                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
                        pinnedLeft          :  true                                        ,
                        enableColumnMenu    :  false});
    }else{
      scope.gridOptions.columnDefs.push({ 
                        name                :  showTableData.header[i].name                ,  
                        displayName         :  showTableData.header[i].displayName         , 
                        width               :  showTableData.header[i].width               ,
                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
                        cellClass           :  showTableData.header[i].cellClass           ,
                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
                        enableColumnMenu    :  false});

    };
  }
  scope.gridOptions.data = showTableData.TableData;

  //判断哪些数据进行过修改
  scope.gridOptions.onRegisterApi = function(gridApi){
  scope.gridApi = gridApi;
  //input获取焦点的时候
  gridApi.edit.on.beginCellEdit(scope,function(rowEntity, colDef, newValue, oldValue){

  });
  //input失去焦点时调用
  //gridApi.edit.on.afterCellEdit(scope,afterCellEdit);
  };
  
}

/*****************************/

function GetKey(source){
  var result=[], key, _length=0; 
  for(key in source){ 
      if(source.hasOwnProperty(key)){ 
           result[_length++] = key; 
     } 
  } 
  return result;
}

function GetValue(source){
  var result=[],key,_length=0; 
  for(key in source){ 
      if(source.hasOwnProperty(key)){ 
         result[_length++] = source[key]; 
      } 
  } 
  return result; 
}

/*****************************/

function app_lockOrientation(orientation){
  // portrait-保持竖屏;landscape-横屏
  console.log("请求屏幕方向" + orientation);
  console.log("当前屏幕方向" + screen.orientation);
  try{
    screen.lockOrientation(orientation);
  }catch(e){
    console.log(e);
  }
}

function setLandscape(setiOS,setAndroid){
  var ua = navigator.userAgent.toLowerCase(); 
  if (/iphone|ipad|ipod/.test(ua)) {
    console.log("ios");
    if (setiOS == false) {

    }else{
      setTimeout(
        function (){
          app_lockOrientation('landscape');//进入时横屏
        }
      ,1000);
    }
  } else if (/android/.test(ua)) {
    console.log("android");
    if (setAndroid == false) {

    }else{
      setTimeout(
        function (){
          app_lockOrientation('landscape');//进入时横屏
        }
      ,1000);
    }
  }else{
    console.log("________________非手机平台旋转无效。");
  }
}

function setPortrait(setiOS,setAndroid){
  var ua = navigator.userAgent.toLowerCase(); 
  if (/iphone|ipad|ipod/.test(ua)) {
    console.log("ios");
    if (setiOS == false) {

    }else{
      setTimeout(
        function (){
          app_lockOrientation('portrait');//进入时竖屏
        }
      ,1000);
    }
  } else if (/android/.test(ua)) {
    console.log("android");
    if (setAndroid == false) {

    }else{
      setTimeout(
        function (){
          app_lockOrientation('portrait');//进入时竖屏
        }
      ,1000);
    }
  }else{
    console.log("________________非手机平台旋转无效。");
  }
}

/*****************************/

