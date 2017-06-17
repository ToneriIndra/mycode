<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>泛微商务协作</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/easyui/outOfBounds.js" type="text/javascript"></script>
<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/functionAction_findMenu',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				$.fn.zTree.init($("#treeMenu"), setting, data);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		$.ajax({
			url : '${pageContext.request.contextPath}/json/admin.json',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#adminMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，${session.loginUser.name}！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000
			});
		},3000);
		/*************/
		
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
	});

	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined && treeNode.page!= "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
		.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
			if (isConfirm) {
				location.href = '${pageContext.request.contextPath }/userAction_logout.action';
			}
		});
	}
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');
	}
	// 版权信息
	function showAbout(){
		$.messager.alert("物流系统v1.0","管理员邮箱: 317432944@qq.com");
	}
	
	$(function(){
		$("#suggestionsbtn").click(function(){
			$('#suggestionsWindow').window('open');
		});
	});
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height:80px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">
		<div>
			<img src="${pageContext.request.contextPath }/images/logo.png"
				border="0">
		</div>
		<div id="sessionInfoDiv"
			style="position: absolute;right: 5px;top:10px;">
			[<strong>${session.loginUser.name }</strong>]，欢迎你！
		</div>
		<div id="sessionInfoDiv"
			style="position: absolute;right: 210px;bottom: 10px;">
			<a href="${pageContext.request.contextPath }/page_common_index.action" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">返回主页</a>  
		</div>
		<div style="position: absolute; right: 5px; bottom: 10px; ">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('metro');">metro</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editPassword();">修改密码</div>
			<div onclick="showAbout();">联系管理员</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'"
		style="width:200px">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
				<ul id="treeMenu" class="ztree"></ul>
			</div>
			<shiro:hasPermission name="sysgenerate">
			<div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="adminMenu" class="ztree"></ul>
			</div>
			</shiro:hasPermission>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs" border="false">
			<div title="快捷入口" id="subWarp"
				style="width:100%;height:100%;overflow:hidden">
				<iframe src="${pageContext.request.contextPath }/page_common_home.action"
					style="width:100%;height:100%;border:0;"></iframe>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height:50px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td style="width: 300px;">
						<div style="color: #999; font-size: 8pt;">
							<a id="suggestionsbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:true">意见反馈</a>  
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!--修改密码窗口-->
    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
            	<form id="editPwdForm">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="newPwd" type="Password" class="txt01 easyui-validatebox" 
                        			data-options="required:true,validType:['length[4,20]']"/></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="rePwd" type="Password" class="txt01 easyui-validatebox" 
                        			data-options="required:true,validType:['length[4,20]']" /></td>
                    </tr>
                </table>
        		</form>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
    
    <!--意见提交窗口-->
    <div id="suggestionsWindow" class="easyui-window" title="意见反馈" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false"  style="width: 500px; height: 300px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
           		<form id="suggestionsForm" method="post" enctype="application/x-www-form-urlencoded">
					<textarea name="suggestions_c" id="suggestions_c" cols="50" rows="7" style=" border:solid 3px #E2E2E2;line-height:16px; padding:5px;"></textarea>
					<br />
					<div style="margin-top:8px;"><a href="javascript:void(0);" onclick="$('#suggestionsForm').submit();" id="suggestionsFormbtn" class="easyui-linkbutton">提交</a></div>
				</form>
				</div>
				<script>
				//功能相对独立，应该单独放置
				$('#suggestionsForm').form({  
					url:"${pageContext.request.contextPath}/mailAction_suggestions.action",  
					onSubmit: function(){  
						if($('#suggestions_c').val()==""){
							$.messager.alert('Warning',"内容太少");	
							return false;
						}
					}, 
					success:function(){  
						$.messager.alert('Warning',"提交成功"); 
						$('#suggestions_c').val("");
						$("#suggestionsWindow").window("close");
					}  
				});   
				</script>
            </div>
        </div>
    </div>
    
    <!-- 对修改密码表单校验 -->
    <script type="text/javascript">
    	$("#btnEp").click(function(){
    		var flag = $("#editPwdForm").form("validate");
    		if(flag){
    			var newPwd = $("#newPwd").val();
    			var rePwd = $("#rePwd").val();
    			if(newPwd == rePwd){
    				var url = "${pageContext.request.contextPath}/userAction_editPwd.action";
        			$.post(url,{"password":newPwd},function(data){
        				if(data == '1'){
        					$.messager.alert('提示信息','密码修改成功!','info');
        				} else {
        					$.messager.alert('错误信息','密码修改失败!','warning');
        				}
        				$("#editPwdWindow").window("close");
        			});
    			} else {
    				$.messager.alert('错误信息','两次输入密码不一致,请重新输入!','warning');
    			}
    		}
    	});
    </script>
</body>
</html>