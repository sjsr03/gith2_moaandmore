<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>
	<form action="/moamore/member/deletePro.moa" method="post">
		탈퇴를 원하시면 비밀번호를 입력하세요
		<br/>
		<input type="password" name="pw">
		<br/>
		<input type="submit" value="탈퇴하기"/>
		<input type="button" value="돌아가기" />
	</form>

</body>
</html>