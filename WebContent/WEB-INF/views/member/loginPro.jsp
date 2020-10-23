<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

	<c:if test="${result==1 }" >
		<script>
			//window.location='/moamore/main.moa';
			var referrer = document.referrer;
			window.location.href = referrer;
		</script>
	</c:if>
	<c:if test="${result!=1 }">
		<script>
			alert("아이디 비밀번호가 일치하지 않습니다.");
			history.go(-1);
		</script>
	</c:if>

</body>
</html>