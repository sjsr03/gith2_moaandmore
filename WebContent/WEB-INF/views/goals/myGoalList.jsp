<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myGoalList</title>
</head>
<body>
<h1> ${sessionScope.memName} 님의 목표리스트 </h1>


<button onclick="window.location.href='/moamore/goals/insertGoalForm.moa'">+목표</button>

<table border="1">
	<tr>
		<td>목표명</td>
		<td>목표액</td>
		<td>달성액</td>
		<td>시작날짜</td>
		<td>마감날짜</td>
		<td>유형</td>
		<td>공개여부</td>
	</tr>
	<c:forEach var="goal" items="${goalList}">
			<td>${goal.subject}</td>
			<td>${goal.target_money}원</td>
			<td>${goal.saving}원</td>
			<td>${goal.start_day}</td>
			<td>${goal.end_day}</td>
			<c:if test="${goal.public_ch eq '0'.charAt(0)}">
				<td>개인</td>
				<td>--</td>
			</c:if>
			<c:if test="${goal.public_ch eq '1'.charAt(0)}">
				<td>그룹</td>
				<c:if test="${goal.public_type eq '0'.charAt(0)}">
					<td>비공개</td>
				</c:if>
				<c:if test="${goal.public_type eq '1'.charAt(0)}">
					<td>공개</td>
				</c:if>
			</c:if>
		</tr>
	</c:forEach> 
	 
	

</table>

</body>
</html>