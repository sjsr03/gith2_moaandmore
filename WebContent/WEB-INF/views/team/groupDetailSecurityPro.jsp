<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<c:if test="${result==1 }" >
		<script>
			var url = "/moamore/team/teamDetail.moa?team_no=${team_no}";
			window.location.href=url;
		</script>
	</c:if>
	<c:if test="${result!=1 }">
		<script>
			alert("비밀번호가 일치하지 않습니다.");
			history.go(-1);
		</script>
	</c:if>
</body>
</html>