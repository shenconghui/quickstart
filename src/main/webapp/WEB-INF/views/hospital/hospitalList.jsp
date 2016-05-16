<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>医院管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}">
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>医院名称</th><th>医院地址</th><th>医院电话</th><th>医院等级</th><th>医院网址</th><th>医院管理</th></tr></thead>
		<tbody>
		<c:forEach items="${hospitals.content}" var="hospital">
			<tr>
				<td><a href="${ctx}/hospital/update/${hospital.id}">${hospital.name}</a></td>
				<td><a href="${ctx}/hospital/update/${hospital.id}">${hospital.address}</a></td>
				<td><a href="${ctx}/hospital/update/${hospital.id}">${hospital.phone}</a></td>
				<td><a href="${ctx}/hospital/update/${hospital.id}">${hospital.level}</a></td>
				<td><a href="${ctx}/hospital/update/${hospital.id}">${hospital.website}</a></td>
				<td><a href="${ctx}/hospital/delete/${hospital.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${hospitals}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/hospital/create">新增医院</a></div>
</body>
</html>
