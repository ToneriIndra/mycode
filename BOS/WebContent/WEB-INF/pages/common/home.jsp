<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript">
	$(function(){
		$('#p1').panel({    
		    href:'${pageContext.request.contextPath}/taskAction_findPersonalTask.action' 
		});  
	});
	$(function(){
		$('#p2').panel({    
		    href:'${pageContext.request.contextPath}/taskAction_findGroupTask.action' 
		});  
	});
	
</script>
</head>
<body>
	<div id="p1" class="easyui-panel" title="代办个人任务"     
        style="width:auto;height:300px;padding:10px;background:#fafafa;"   
        data-options="iconCls:'icon-smartart',collapsible:true">   
	</div> 
	<div id="p2" class="easyui-panel" title="代办组任务"     
        style="width:auto;height:300px;padding:10px;background:#fafafa;"   
        data-options="iconCls:'icon-smartart',collapsible:true">   
	</div>
</body>
</html>