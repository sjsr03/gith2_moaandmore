<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>

</script>
<title>moneyLog</title>
</head>
<body>
<h2 align="center"> 입출력 내역 </h2>

<div align="center">
<button onclick="location.href='/moamore/record/recordForm.moa'">입출력 입력</button><br />
<h2> ${recordPage.count}</h2>
	<select>
		<option>예산내역</option>
		<option>수입내역(예산외)</option>
		<option>지출내역(예산외)</option>
	</select>
	<div>
		<input type="date" id="date" name="date" />
	</div>
	
	<table>	
		<tr>
			<td>날짜</td>
			<td>시간</td>
			<td>카테고리</td>
			<td>내역</td>
			<td>금액</td>
		</tr>
		<tr>
			<td>날짜</td>
			<td>시간</td>
			<td>카테고리</td>
			<td>내역</td>
			<td>금액</td>
		</tr>
	</table>
</div>
</body>
</html>