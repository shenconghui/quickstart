<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>医院管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/hospital/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${hospital.id}"/>
		<fieldset>
			<legend><small>医院信息表</small></legend>
			<div class="control-group">
				<label for="hospital_name" class="control-label">医院名称:</label>
				<div class="controls">
					<input type="text" id="hospital_name" name="name"  value="${hospital.name}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="address" class="control-label">医院地址:</label>
				<div class="controls">
					<textarea id="address" name="address" class="input-large">${hospital.address}</textarea>
				</div>
			</div>
				<div class="control-group">
					<label for="phone" class="control-label">医院电话:</label>
					<div class="controls">
						<textarea id="phone" name="phone" class="input-large">${hospital.phone}</textarea>
					</div>
			</div>
			<div class="control-group">
				<label for="level" class="control-label">医院等级:</label>
				<div class="controls">
					<textarea id="level" name="level" class="input-large">${hospital.level}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="website" class="control-label">医院网址:</label>
				<div class="controls">
					<textarea id="website" name="website" class="input-large">${hospital.website}</textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#hospital_name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
