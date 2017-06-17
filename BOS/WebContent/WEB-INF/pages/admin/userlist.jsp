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
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	// 工具栏
	var toolbar = [ {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '账号',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'name',
		title : '姓名',
		width : 60,
		rowspan : 2,
		align : 'center'
	},{
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'formatbirthday',
		title : '生日',
		width : 120,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'telephone',
		title : '电话',
		width : 800,
		rowspan : 2
	} ], [ {
		field : 'station',
		title : '单位',
		width : 80,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	} ] ];
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/userAction_pageQuery",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		$("body").css({visibility:"visible"});
		
		// 添加分区
		$('#addUserWindow').window({
	        title: '添加用户',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 修改分区
		$('#editUserWindow').window({
	        title: '修改用户',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		$("#editUserWindow").window("open");
		$("#editUserForm").form("load",rowData);
	}
	
	function doAdd() {
		$("#addUserWindow").window("open");
	}


	function doDelete() {
		var row = $("#grid").datagrid("getSelections");
		if(row == 0) {
			$.messager.alert('提示信息','选中要删除的数据','warning');
		}
		var array = new Array();
		for(var i=0;i<row.length;i++){
			var id = row[i].id;
			array.push(id);
		}
		var ids = array.join(",");
		window.location.href="${pageContext.request.contextPath}/userAction_delete?ids=" + ids;
		$('#grid').datagrid('reload');
		$('#grid').datagrid('uncheckAll');
	}
	$(function(){
		$("body").css({visibility:"visible"});
		$('#save').click(function(){
			$('#form').submit();
		});
	});
	
	$(function(){
		var url = '${pageContext.request.contextPath}/roleAction_ajaxList';
		$.post(url,{},function(data){
			for(var i=0;i<data.length;i++){
				var id = data[i].id;
				var roleName = data[i].name;
				$("#addroleTD").append("<input type='checkbox' value='"+id+"' name='roleIds' />"+roleName);
			}
		},'json');
		$.post(url,{},function(data){
			for(var i=0;i<data.length;i++){
				var id = data[i].id;
				var roleName = data[i].name;
				$("#editroleTD").append("<input type='checkbox' value='"+id+"' name='roleIds' />"+roleName);
			}
		},'json')
	});
	
	$(function(){
		$("#save").click(function(){
			var flag = $("#userForm").form("validate");
			if(flag){
				$("#userForm").submit();
			}
		});
	});
	$(function(){
		$("#edit").click(function(){
			var flag = $("#editUserForm").form("validate");
			if(flag){
				$("#editUserForm").submit();
			}
		});
	});
	
</script>		
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<!-- 添加用户 -->
	<div class="easyui-window" title="添加用户" id="addUserWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="userForm" method="post" action="${pageContext.request.contextPath}/userAction_save" >
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
	           		<td>姓名:</td><td><input type="text" name="name" id="name"  /></td></tr>
				<tr><td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td>
	           	<td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="男">男</option>
	           			<option value="女">女</option>
	           		</select>
	           	</td></tr>
				<tr><td>单位:</td><td>
					<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="总公司">总公司</option>
	           			<option value="分公司">分公司</option>
	           			<option value="厅点">厅点</option>
	           			<option value="基地运转中心">基地运转中心</option>
	           			<option value="营业所">营业所</option>
	           		</select>
				</td>
				
					<td>联系电话</td>
					<td colspan="3">
						<input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true" />
					</td>
				</tr>
	           	<tr><td>备注:</td><td colspan="3"><textarea name="remark" style="width:80%"></textarea></td></tr>
	           	<tr><td>选择角色:</td>
	           		<td colspan="3" id ="addroleTD">
	           		</td>
	           	</tr>
           </table>
       </form>
	</div>
	</div>
	
	<!-- 修改用户 -->
	<div class="easyui-window" title="修改用户" id="editUserWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="editUserForm" method="post" action="${pageContext.request.contextPath}/userAction_edit" >
       <input type="hidden" name="id">
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
	           		<td>姓名:</td><td><input type="text" name="name" id="name" /></td></tr>
				<tr><td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td>
	           	<td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="男">男</option>
	           			<option value="女">女</option>
	           		</select>
	           	</td></tr>
				<tr><td>单位:</td><td>
					<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="总公司">总公司</option>
	           			<option value="分公司">分公司</option>
	           			<option value="厅点">厅点</option>
	           			<option value="基地运转中心">基地运转中心</option>
	           			<option value="营业所">营业所</option>
	           		</select>
				</td>
				
					<td>联系电话</td>
					<td colspan="3">
						<input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true" />
					</td>
				</tr>
	           	<tr><td>备注:</td><td colspan="3"><textarea name="remark" style="width:80%"></textarea></td></tr>
	           	<tr><td>选择角色:</td>
	           		<td colspan="3" id ="editroleTD">
	           		</td>
	           	</tr>
           </table>
       </form>
	</div>
	</div>
</body>
</html>