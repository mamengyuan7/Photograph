<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <a href="user/list">user模块的信息维护功能</a> 
 
 <a href="picture/listall">所有图片</a> 
 
  
 <a href="picture/typepic?typeId=1">类型</a> 
 
  <a href="picture/listpart?userId=1">uid</a>
</body>
</html>