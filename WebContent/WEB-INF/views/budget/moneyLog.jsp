<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
//달력기능
	$(function(){
		$("#reg").datepicker({
			dateFormat:"yy-mm-dd"
			
		});
	});
</script>
<title>moneyLog</title>
</head>
<body>
<h2 align="center"> 입출력 내역 </h2>

<div align="center">
<button onclick="location.href='/moamore/record/recordForm.moa'">입출력 입력</button><br />
	<select>
		<option>예산내역</option>
		<option>수입내역(예산외)</option>
		<option>지출내역(예산외)</option>
	</select>
	<div>
		<input type="text" id="reg" name="reg" />
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