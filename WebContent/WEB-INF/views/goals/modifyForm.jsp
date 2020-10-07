<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목표 수정</title>
</head>
<body>
	<h1>목표 수정하기</h1>
	<form action="/moamore/goals/modifyPro.moa" method="post">
		<input type="hidden" name="goal_no" value="${goal.goal_no}"/>
		목표명 : <input type="text" name="subject" value="${goal.subject}"/> <br/>
		목표금액 : <input type="text" name="target_money" value="${goal.target_money}"/> <br/>
		모은 금액 : <input type="text" name="saving" value="${goal.saving}" disabled/> <br/>
		<input type="submit" value="수정"/>
	</form>
</body>
</html>