<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<br />
	<h1 align="center"> 회원가입 </h1>							
	<form action="/maomore/member/signupPro.moa" method="post" name="inputForm" >
		<table>
			<tr>
				<td>아이디*</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" value="아이디 중복 확인" onclick="confirmId(this.form)" /></td> 
			</tr>																
			<tr>
				<td>비밀번호*</td>
				<td><input type="password" name="pw" /></td>
			</tr>
			<tr>
				<td>비밀번호 확인*</td>
				<td><input type="password" name="pwCh" /></td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td><input type="text" name="nickname" /></td>
			</tr>
			<tr>
				<td> 사진 </td>
				<td><input type="file" name="img"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"> 
					<input type="submit" value="가입"/>
					<input type="reset" name="reset" value="재입력" />
					<input type="button" value="취소" onclick="window.location='/moamore/member/main.moa'"/>
				</td>
			</tr>
		</table>
	</form>







</body>
</html>