<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>角色列表页面</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="../../css/default.css">
		<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
		<script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<!-- 导入ztree类库 -->
		<link rel="stylesheet" href="../../js/ztree/zTreeStyle.css" type="text/css" />
		<script src="../../js/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				// 数据表格属性
				$("#grid").datagrid({
					toolbar : [
						{
							id : 'add',
							text : '添加角色',
							iconCls : 'icon-add',
							handler : function(){
								location.href='role_add.jsp';
							}
						},
						{
							id : 'edit',
							text : '修改角色',
							iconCls : 'icon-edit',
							handler : function(){
								var rows = $("#grid").datagrid("getSelections");
								if( rows.length != 1){
									$.messager.alert("提示信息","请选择一个进行修改","warning");
									return;
								}
								$("#roleEditWindow").window("open");
								$("input[name=id]").val(rows[0].id);  //确保是同一个角色,不会重新创建一个角色
								$("#roleEditForm").form("load",rows[0]);  //回显名称, 关键字, 描述
								
								/* ========================回显权限选择====================== */
								
								$.post("../../roleAction_showPermission.action",{"id":rows[0].id},function(permissionData){
									if( permissionData.length >0 ){
										for(var i = 0 ; i < permissionData.length; i++){
											var pid = permissionData[i].id;
											$("#"+pid).attr("checked","true");
										}
									}
								});
								/* ========================回显权限选择====================== */
								
								/* ========================回显菜单选择====================== */
								$.post("../../roleAction_showMenu.action",{"id":rows[0].id},function(menuData){
									var treeObj = $.fn.zTree.getZTreeObj("menuTree");
									if(menuData.length >0 ){
										for(var i = 0 ; i<menuData.length ;i++){
											var tnode =treeObj.getNodeByParam("id",menuData[i].id);
											if(menuData[i].pId == 0 && tnode.isParent ){ //跳过父节点, 保证不会出现没有全选,却全部都选中的情况
												continue;
											}
											var node =treeObj.getNodeByParam("id",menuData[i].id);
									    	treeObj.checkNode(node, true, true);
										}
									}
								});
								/* =======================回显菜单选择======================= */
							}
						}
					],
					url : '../../roleAction_pageQuery.action',
					pagination:true,
					fit:true,
					rownumbers: true, // True显示行号列
					columns : [[
						{
							field : 'id',
							title : '编号',
							checkbox : true, //True 显示一个复选框。 复选框列固定宽度
							width : 200
						},
						{
							field : 'name',
							title : '名称',
							width : 200
						}, 
						{
							field : 'keyword',
							title : '关键字',
							width : 200
						}, 
						{
							field : 'description',
							title : '描述',
							width : 200
						} 
					]]
				});
			/* ========================================= */
				// 授权树初始化
				var setting = {
					data : {
						key : {
							title : "t"
						},
						simpleData : {
							enable : true
						}
					},
					check : {
						enable : true//启用ztree复选框勾选效果
					}
				};
				
				$.ajax({
					url : '../../menuAction_listajax.action',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						$.fn.zTree.init($("#menuTree"), setting, data);
					},
					error : function(msg) {
						alert('树加载异常!');
					}
				});
				// 授权树初始化
		/* ==================================================== */
			
		/* =======================点击保存修改======================== */ 
			$('#save').click(function(){
				var v = $("#roleEditForm").form("validate");
				if(v){
					//在表单提交之前，调用ztree的API，动态获得选中的节点，将节点的id拼接为字符串，赋给指定的隐藏域，通过隐藏域提交参数
					var treeObj = $.fn.zTree.getZTreeObj("menuTree");//根据id获取ztree对象
					var nodes = treeObj.getCheckedNodes(true);//获得所有选中的节点
					var array = new Array();
					for(var i=0;i<nodes.length;i++){
						var id = nodes[i].id;
						array.push(id);
					}
					var menuIds = array.join(",");
					$("input[name=menuIds]").val(menuIds);//为隐藏域赋值
					$("#roleEditForm").submit();
				}
			});
		});
		/* =======================点击保存修改======================== */
		</script>
	</head>

	<body class="easyui-layout">
		<div data-options="region:'center'">
			<table id="grid"></table>
		</div>
		
		<!-- 角色修改窗口 -->
	<div class="easyui-window" title="角色修改窗口" closed="true" id="roleEditWindow" collapsible="false" minimizable="false" maximizable="false" fit="true" >
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save"  class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="roleEditForm" method="post" action="../../roleAction_save.action">
				<input type="hidden" name="menuIds">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="keyword" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>权限选择</td>
						<td id="permissionTD">
							<script type="text/javascript">
								$(function(){
									//页面加载完成后，发送ajax请求获取所有的权限数据，使用复选框形式展示到页面中
									$.post("../../permissionAction_listajax.action",function(data){
										for(var i=0;i<data.length;i++){
											var id = data[i].id;
											var name = data[i].name;
											var input = '<input type="checkbox" name="permissionIds" id="'+id+'" value="'+id+'" /> ' + name;
											$("#permissionTD").append(input);
										}
									});
								});
							</script>
						</td>
					</tr>
					<tr>
						<td>菜单授权</td>
						<td>
							<ul id="menuTree" class="ztree"></ul>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>	
		<!-- 角色修改窗口 -->
	</body>

</html>