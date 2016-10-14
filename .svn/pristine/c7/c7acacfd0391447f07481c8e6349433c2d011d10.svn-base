<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>App接口测试</title>
	<base href="<%=basePath%>">
	<script type="text/javascript" src="common/js/jquery-1.6.1.min.js"></script>
</head>
<body>
	<h2>App接口测试：</h2>
	<table >
		<tr><td style="width:50px;">path:</td><td><input id="path" style="width:300px;height:20px;" value=""/></td></tr>
		<tr><td style="width:50px;">para:</td><td><textarea id="paras" style="width:900px;height:300px;" ></textarea></td></tr>
	</table>
	<button id="butt" style="width:100px;height:30px;" onclick="submit()">提交</button>
	<br>
	<br>
	<table>
	<tr><td style="width:50px;">result:</td><td><textarea id="result" readonly="readonly" style="width:900px;height:100px;"></textarea></td></tr>
	</table>
	
<script>
function submit(){
	if($('#path').val()!='' && $('#paras').val()!=''){
		$('#butt').attr("disabled",true); 
		$('#result').val("正在获取数据。。。");
		
		$.ajax({
			type: "POST" ,
			url:  $('#path').val() ,
			data: $('#paras').val() , 
			async: false,
		    error: function(data){
		    	$('#butt').attr("disabled",false);
	    		alert(data ? 'ErrorCode:' + data.status + ';ErrorText:' +
	    				data.statusText : '未知错误') ;
		    },
		    success: function(data) {
		    	//alert(data);
		    	$('#butt').attr("disabled",false);
		    	var jsonObj = $.parseJSON(data) ;
		    	//alert(JSON.stringify(jsonObj));
		    	$('#result').val(JSON.stringify(jsonObj));
		    }
		});
	}else{
		alert('请输入相关参数') ; 
	}
	
}
</script>
</body>
</html>
