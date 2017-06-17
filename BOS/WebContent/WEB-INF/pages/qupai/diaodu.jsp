<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
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
	$(function() {
		$("#grid").datagrid({
			singleSelect : true,
			toolbar : [ {
				id : 'diaodu',
				text : '人工调度',
				iconCls : 'icon-edit',
				handler : function() {
					// 弹出窗口
					var row = $("#grid").datagrid("getSelections");
					if(row == 0) {
						$.messager.alert('提示信息','先选中一行数据','info');
					}
					var id = row[0].id;
					$("input[name=id]").val(id);
					$("#diaoduWindow").window('open');
				}
			} ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 100
			}, {
				field : 'delegater',
				title : '联系人',
				width : 100
			}, {
				field : 'telephone',
				title : '电话',
				width : 100
			}, {
				field : 'pickaddress',
				title : '取件地址',
				width : 100
			}, {
				field : 'product',
				title : '商品名称',
				width : 100
			}, {
				field : 'pickdate',
				title : '取件日期',
				width : 100,
				formatter : function(val) {
					return formattime(val);
				}
			} ] ],
			url : '${pageContext.request.contextPath}/noticebillAction_findNoAssociations.action'
		});
		
		function formattime(val){
			if(val=="" || val==null) {
				return "";
			}
			var year = parseInt(val.year)+1900;
			var month = (parseInt(val.month)+1);
			month=month>9?month:("0"+month);
			var day = parseInt(val.day);
			day = day>9? day:("0"+day);
			return year +"-" + month +"-" +day;
		}
		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function() {
			var flag = $("#diaoduForm").form("validate");
			if(flag) {
				$("#diaoduForm").submit();
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:100px;left:200px;width: 500px; height: 300px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="diaoduForm" action="${pageContext.request.contextPath }/noticebillAction_associationStaff.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">人工调度</td>
					</tr>
					<tr>
						<td>工作单编号</td>
						<td><input type="text" name="id" readOnly="readonly"></td>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td><input class="easyui-combobox" required="true"
							name="staff.id"
							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/staffAction_ajaxList.action'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>