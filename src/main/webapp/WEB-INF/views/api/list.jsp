<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="reqUrl" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<html>
<head>
	<title>Restful API 列表</title>
</head>

<body>

<h3>Restful API 列表</h3>
<h4>查询 API</h4>
<ul>
	<li>获取任务列表 ： <a href="${reqUrl}/api/v1/task">${reqUrl}/api/v1/task</a></li>
	<li>获取任务(id=1) ： <a href="${reqUrl}/api/v1/task/1">${reqUrl}/api/v1/task/1</a></li>
</ul>
<ul>
	<li>获取医院列表 ： <a href="${reqUrl}/api/v1/hospital">${reqUrl}/api/v1/hospital</a></li>
	<li>获取医院(id=1) ： <a href="${reqUrl}/api/v1/hospital/1">${reqUrl}/api/v1/hospital/1</a></li>
</ul>

<h4>修改API</h4>
<ul>
	<li>创建任务 ：${reqUrl}/api/v1/task method=Post, consumes=JSON</li>
	<li>修改任务(id=1) ：${reqUrl}/api/v1/task/1 method=Put, consumes=JSON</li>
</ul>
<ul>
	<li>创建医院 ：${reqUrl}/api/v1/hospital method=Post, consumes=JSON</li>
	<li>修改医院(id=1) ：${reqUrl}/api/v1/hospital/1 method=Put, consumes=JSON</li>
</ul>
</body>
</html>
