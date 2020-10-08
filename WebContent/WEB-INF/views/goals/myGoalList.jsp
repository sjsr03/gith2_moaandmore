<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myGoalList</title>
<script>
	function deleteCh(goal_no, public_ch){
		var ch = true;
		
		console.log(public_ch);
		if(public_ch == '1'){
			ch = confirm("해당 목표를 삭제하면 참여중인 그룹을 탈퇴하며 그룹목표를 다시 진행할 수 없습니다. 삭제하시겠습니까?");
			
		}
		
		console.log(ch);
		if(ch){
			window.location.href = "/moamore/goals/deleteGoal.moa?goal_no="+goal_no+"&public_ch="+public_ch;
		}else{
			return;
		}
	}
	
	

</script>
</head>

<body>
<h1> ${sessionScope.memName} 님의 목표리스트 </h1>


<button onclick="window.location.href='/moamore/goals/insertGoalForm.moa'">+목표</button>
<select>
	<option>정렬</option>
	<option>정렬</option>
	<option>정렬</option>
	<option>정렬</option>
	<option>정렬</option>
	

</select>

<table border="1">
	<tr>
		<td>목표명</td>
		<td>목표액</td>
		<td>달성액</td>
		<td>시작날짜</td>
		<td>마감날짜</td>
		<td>유형</td>
		<td>공개여부</td>
		<td>수정</td>
		<td>삭제</td>
	</tr>
	<c:forEach var="goal" items="${goalList}">
		<tr>
			<td onclick="window.location.href='/moamore/goals/myGoalDetail.moa?goal_no=${goal.goal_no}'">${goal.subject}</td>
			<td>${goal.target_money}원</td>
			<td>${goal.saving}원</td>
			<td>${goal.start_day}</td>
			<td>${goal.end_day}</td>
			<c:if test="${goal.public_ch eq '0'.charAt(0)}">
				<td>개인</td>
				<td>--</td>
				<td><button onclick="window.location.href='/moamore/goals/modifyForm.moa?goal_no=${goal.goal_no}'">수정</button></td>
			</c:if>
			<c:if test="${goal.public_ch eq '1'.charAt(0)}">
				<td>그룹</td>
				<c:if test="${goal.public_type eq '0'.charAt(0)}">
					<td>비공개</td>
				</c:if>
				<c:if test="${goal.public_type eq '1'.charAt(0)}">
					<td>공개</td>
				</c:if>
				<td>수정불가</td>				
			</c:if>
			<td><button onclick="deleteCh('${goal.goal_no}','${goal.public_ch}')">삭제</button></td>
		</tr>	
	</c:forEach> 
	
	
</table>

</body>
</html>