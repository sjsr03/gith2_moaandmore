<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>그룹 비밀번호 입력</title>
</head>
<body>
	<form action="/moamore/team/teamDetailSecurityPro.moa" method="post">
		<input type="hidden" name="team_no" value="${team_no}"/>
		<input type="password" name="pw" placeholder="그룹의 비밀번호를 입력해주세요."/>
		<input type="submit" value="확인"/>
	</form>
</body>
</html>