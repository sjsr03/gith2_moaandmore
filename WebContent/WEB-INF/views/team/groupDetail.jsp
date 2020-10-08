<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Group Detail Info</title>
<script>
	function enterTeam(team_no){
	
		window.location.href="/moamore/goals/enterTeam.moa?team_no="+team_no;
	}
</script>
</head>
<body>
	
	<h1>${team.subject}</h1>
	<h2>${team.content}</h2>
	<h3>${team.amount}</h3>
	<span></span>
	<h3>시작날짜 : ${team.start_day}</h3>
	<h3>마감날짜 : ${team.end_day}</h3>
	<h3>개설자 : ${team.leader}</h3>
	<h3>참가인원수 :${team.people} </h3>
	<h3>그룹상태 :  ${team.status}</h3>
	<h3>그룹 타입 : ${team.isopen } </h3>
	
	<c:if test="${1 == 1}">
		<button onclick="enterTeam('${team.team_no}')">팀 참여</button>
	</c:if>
	
	
	<h1>참여인원</h1>
	

</body>
</html>