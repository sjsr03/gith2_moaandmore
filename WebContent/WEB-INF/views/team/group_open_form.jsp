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
</head>
<body>
	<br />
	<h1> 그룹 개설 신청폼 </h1>
	<form action="/moamore/team/groupOpenPro.moa" method="post">
		<input type="hidden" name="status" value=0 />
		<table>
			<tr>
				<td> 신 청 자 </td>
				<td><input type="text" name="leader" readonly="readonly" value="${dto.leader}"/></td>
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
				<td><input type="text" name="amount"  /></td>
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
				<td><input type="text" name="people"  /></td>
			</tr>
			<tr>
				<td> 목표내용 </td>
				<td><input type="text" name="content"  /></td>
			</tr>
			<tr>
				<td> 공개여부 </td>
				<td><input type="radio" name="isopen" value="공개" checked="checked"/>공개 <input type="radio" name="isopen" value="비공개"/>비공개</td>
			</tr>
			<tr>
				<td> 비밀번호<br/><span style="font-size: 7px">비공개 그룹만</span></td>
				<td><input type="password" name="pw"  /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" value="신청" />					
					<input type="reset" value="재작성" />					
					<input type="button" value="돌아가기" onclick="window.location='/moamore/team/group_list.moa'" />					
				</td>
			</tr>
		</table>
	</form>
</body>
</html>




