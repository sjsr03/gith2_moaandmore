<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY GOAL DETAIL</title>
</head>
<body>
<jsp:include page="../sidebar.jsp"/>
	<h1>${goal.subject}</h1>
	<button onclick="window.location.href='/moamore/team/teamDetail.moa?team_no=${goal.team_no}'">그룹 페이지로</button>
	<h3>목표액 : ${goal.target_money}원</h3>
	<h3>달성액: ${goal.saving}원</h3>
	<h3>시작날짜:<fmt:formatDate  value="${goal.start_day}" pattern="yyyy.MM.dd"/></h3>
	<c:if test="${goal.public_ch eq'0'.charAt(0)}">
		<h3>유형 : 개인</h3> 
		<button onclick="redir('/moamore/goals/modifyForm.moa?goal_no=','${goal.goal_no}')">수정</button>
		
	</c:if>
	<c:if test="${goal.public_ch eq'1'.charAt(0)}">
		<h3>마감날짜:<fmt:formatDate  value="${goal.end_day}" pattern="yyyy.MM.dd"/></h3>
	
		<h3>유형 : 그룹</h3>
		<c:if test="${goal.public_type eq '0'.charAt(0)}">
			<h3>공개여부 : 비공개</h3>
		</c:if>
		<c:if test="${goal.public_type eq '1'.charAt(0)}">
			<h3>공개여부 : 공개</h3>
		</c:if>	
	</c:if>
	<button onclick="deleteCh('${goal.goal_no}','${goal.public_ch}','${goal.team_no}')">삭제</button>
	
	<br>
	<h3>목표액 기록 내역</h3>
	<table>
		<tr>
			<td>날짜</td>
			<td>금액</td>
		</tr>
	<c:forEach var="record" items="${recordList}">
		<tr>
			<td><fmt:formatDate  value="${record.reg}" pattern="yyyy.MM.dd"/></td>
			<td>${record.amount}</td>		
		</tr>
	
	
	</c:forEach>
	</table>
	
	<button onclick="redir('/moamore/goals/myGoalList.moa?','public_ch=${goal.public_ch}')">목록으로</button>
<jsp:include page="../footer.jsp"/>	
	
<script>
	//삭제여부 확인 
	function deleteCh(goal_no, public_ch, team_no){
		var ch = true;
		
		if(public_ch == '1'){
			ch = confirm("해당 목표를 삭제하면 참여중인 그룹을 탈퇴하며 그룹목표를 다시 진행할 수 없습니다. 삭제하시겠습니까?");
		}
		console.log(ch);
		if(ch){
			window.location.href = "/moamore/goals/deleteGoal.moa?goal_no="+goal_no+"&public_ch="+public_ch+"&team_no="+team_no;
		}else{
			return;
		}
	}
	
	function redir(url, val){
		window.location.href= url+val;
	}

</script>
	

</body>
</html>