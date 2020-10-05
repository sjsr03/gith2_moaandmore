<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<br>
	<h1 align = "center">회원정보 수정</h1>
	
	<form action="/moamore/member/updateMemberPro.moa" method="post" name="updateMember" enctype="multipart/form-data">
		<table>
			<tr>
				<td>아이디*</td>
				<td>${member.id}</td> 
			</tr>
			<tr>
				<td>비밀번호*</td>
				<td><input type="password" name="pw" value="${member.pw}"/></td>
			</tr>
			<tr>
				<td>비밀번호 확인*</td>
				<td><input type="password" name="pwCh" /></td>
			</tr>
			<tr>
				<td>닉네임*</td>
				<td>
					<input type="text" name="nickname" value="${member.nickname}" />
					<input type="button" value="닉네임 중복 확인" onclick="confirmId(this.form)" />
				</td> 
			</tr>
		
			<tr>
				<td>사진*</td>
				<td>
					<img src="/moamore/save/${member.profile_img}" />
					<input type="file" name="image">
					<input type="hidden" name="eximage" value="${member.profile_img}" />
				</td> 
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="수정"/>
					<input type="button" value="취소" onclick="window.location='/moamore/member/main.moa'"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>