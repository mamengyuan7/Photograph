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
	<c:forEach items="${users }" var="p">
		${p.id }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p.nickName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p.background }
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p.telephone }
		<br>
	</c:forEach>
</body>
</html>