<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Goal Form</title>
</head>
<body>
	<h1>목표 추가하기(개인)</h1>
	<form action="/moamore/goals/insertGoalPro.moa" method="post">
		<input type="hidden" name="public_ch" value="0"/>
		<input type="hidden" name="public_type" value="0"/>
		목표명 : <input type="text" name="subject"/> <br/>
		목표금액 : <input type="text" name="target_money"/> <br/>
		<input type="submit" value="추가"/>
	</form>

</body>
</html>