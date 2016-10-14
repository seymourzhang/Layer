<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>上传数据查询</title>
	<link rel="stylesheet" type="text/css" href="common/easyui-1.4.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="common/easyui-1.4.3/themes/icon.css">
	<script type="text/javascript" src="common/easyui-1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="common/easyui-1.4.3/jquery.easyui.min.js"></script>
<style type="text/css">
body {
*{
	font-size:12px;
}
body {
    font-family:verdana,helvetica,arial,sans-serif;
    padding:20px;
    font-size:12px;
    margin:0;
}
</style>
</head>
<body>
	<table id="House_table" class="easyui-datagrid" title="实时上传数据" style="width:1000px"
		data-options="singleSelect:true,
		collapsible:true,
		rownumbers:true,
		singleSelect:true,
		url:'sys/device/showData.action', 
		method:'POST'">
		<thead>
			<tr>
				<th data-options="field:'keyid',width:150,halign:'center'">KeyId</th>
				<th data-options="field:'bak1',width:150,halign:'center'">IMEI</th>
				<th data-options="field:'bak2',width:150,halign:'center'">ICCID</th>
				<th data-options="field:'date_time',width:120,halign:'center'">DateTime</th>
				<th data-options="field:'t1',width:50,halign:'center'">t1</th>
				<th data-options="field:'t2',width:50,halign:'center'">t2</th>
				<th data-options="field:'t3',width:50,halign:'center'">t3</th>
				<th data-options="field:'t4',width:50,halign:'center'">t4</th>
				<th data-options="field:'t5',width:50,halign:'center'">t5</th>
				<th data-options="field:'t6',width:50,halign:'center'">t6</th>
				<th data-options="field:'h1',width:50,halign:'center'">h1</th>
				<th data-options="field:'h2',width:50,halign:'center'">h2</th>
				<th data-options="field:'p',width:30,halign:'center'">p</th>
				<th data-options="field:'station',width:130,halign:'center'">station</th>
				<th data-options="field:'gps',width:130,halign:'center'">gps</th>
			</tr>
		</thead>
	</table>
</body>
</html>

