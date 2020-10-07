<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<c:if test="${sessionScope.memId==null }" >
		비회원 임시 메인페이지
		<button onclick="window.location='/moamore/member/loginForm.moa'">로그인</button>
		<button onclick="window.location='/moamore/member/signupForm.moa'">회원가입</button>
	</c:if>
	<c:if test="${sessionScope.memId!=null }" >
		회원 임시 메인페이지
		<button onclick="window.location='/moamore/member/logout.moa'">로그아웃</button>
		<button onclick="window.location='/moamore/budget/setBudget.moa'">예산설정</button>
	</c:if>
</body>
</html>