<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<!-- 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<title>모아 & More</title>
	<!-- Custom fonts for this template-->
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	
	<!-- Custom styles for this template-->
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body id="page-top">
	<c:if test="${sessionScope.memId==null }" >
		비회원 임시 메인페이지
		<button onclick="window.location='/moamore/member/loginForm.moa'">로그인</button>
		<button onclick="window.location='/moamore/member/signupForm.moa'">회원가입</button>
	</c:if>
	<c:if test="${sessionScope.memId!=null }" >
		<c:redirect url="/moamore/main.moa"/>
	</c:if>
</body>
</html>