<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>그룹 개설 신청</title>
	<style type="text/css">
		table, th, td {
		    border: 1px solid black;
		    padding: 5px;
		}
		
		table {
		    border-collapse: collapse;
		}
	</style>
	<script>
		function setDisplay(value){
			if(value=="1"){
				var input_pw = document.getElementsByName('password');
				var input_mem_nick = document.getElementsByName('join_mem_nick');
				
				for(var i=0; i<input_pw.length; i++){
					input_pw[i].value = '';
				}
				for(var i=0; i<input_mem_nick.length; i++){
					input_mem_nick[i].value = '';
				}
				
				pw_area.style.display = 'none';
				mem_area.style.display = 'none';
			} else{
				pw_area.style.display = '';
				mem_area.style.display = '';
			}
		}
	</script>
</head>
<body>
	<br />
	<h1> 그룹 개설 신청폼 </h1>
	<form action="/moamore/team/groupOpenPro.moa" method="post">
		<input type="hidden" name="status" value=0 />
		<table>
			<tr>
				<td> 신 청 자 </td>
				<td><input type="text" name="leader" readonly="readonly" value="${sessionScope.memName}"/></td>
			</tr>
			<tr>
				<td> 목 표 명 </td>
				<td><input type="text" name="subject"/></td>
			</tr>
			<tr>
				<td> 목표내용 </td>
				<td><input type="text" name="content"  /></td>
			</tr>
			<tr>
				<td> 목표금액 </td>
				<td><input type="number" name="amount"  /></td>
			</tr>
			<tr>
				<td> 시 작 일 </td>
				<td><input type="date" name="start_day"  /></td>
			</tr>
			<tr>
				<td> 종 료 일 </td>
				<td><input type="date" name="end_day"  /></td>
			</tr>
			<tr>
				<td> 참여자 수 </td>
				<td><input type="number" name="people"  /></td>
			</tr>
			<tr>
				<td> 공개여부 </td>
				<td>
					<input type="radio" name="isopen" id="open" value="1" checked="checked" onchange="setDisplay(this.value)"/>공개
					<input type="radio" name="isopen" id="close" value="0" onchange="setDisplay(this.value)"/>비공개
				</td>
			</tr>
			<tr id="pw_area" style="display: none;">
				<td> 비밀번호 </td>
				<td><input type="number" name="password"/></td>
			</tr>
			<tr id="mem_area" style="display: none;">
				<td style="text-align: center;"> 참가 멤버<br/>닉네임 </td>
				<td><input type="text" name="join_mem_nick" placeholder=",로 구분해주세요"/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" value="신청" />					
					<input type="reset" value="재작성" />					
					<input type="button" value="돌아가기" onclick="window.location='/moamore/team/groupList.moa'" />					
				</td>
			</tr>
		</table>
	</form>
</body>
</html>




