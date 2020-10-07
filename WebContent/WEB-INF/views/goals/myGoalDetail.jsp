<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY GOAL DETAIL</title>
</head>
<body>
	<h1>${goal.subject}</h1>
	<h3>목표액 : ${goal.target_money}원</h3>
	<h3>달성액: ${goal.saving}원</h3>
	<h3>시작날짜: ${goal.start_day}</h3>
	<c:if test="${goal.public_ch eq'0'.charAt(0)}">
		<h3>유형 : 개인</h3>
	</c:if>
	<c:if test="${goal.public_ch eq'1'.charAt(0)}">
		<h3>유형 : 공개</h3>
		<c:if test="${goal.public_type eq '0'.charAt(0)}">
			<h3>공개여부 : 비공개</h3>
		</c:if>
		<c:if test="${goal.public_type eq '1'.charAt(0)}">
			<h3>공개여부 : 공개</h3>
		</c:if>	
	</c:if>
	
	<button onclick="window.location.href='/moamore/goals/myGoalList.moa'">목록으로</button>
	
	

</body>
</html>