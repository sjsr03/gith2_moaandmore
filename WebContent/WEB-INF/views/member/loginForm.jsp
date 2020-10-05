<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
	<form method="post" action="/moamore/member/loginPro.moa">
		<label> 이메일 아이디
			<input type="text" name="id" required/>
		</label>
		<br/>
		<label>	비밀번호
			<input type="password" name="pw" required/>
		</label>
		<br/>
		<label>
			<input type="checkbox" name="auto" />
			자동로그인
		</label>
		<br/>
		<input type="submit" value="로그인"/>
		<br/>
		아직 회원이 아니세요? <a href="/moamore/member/signupForm.moa">회원가입</a>
	</form>

</body>
</html>