<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
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
		$(document).ready(function(){
			var today = new Date();
			var dd = today.getDate()+1;
			var mm = today.getMonth()+1; //January is 0!
			var yyyy = today.getFullYear();
			
			if(mm==1 || mm==3 || mm==5 || mm==7 || mm==8 || mm==10 || mm==12){
				if(dd>31){
					dd=1;
					mm=mm++;
					
					if(mm>12){
						mm=1;
						yyyy++;
					}
				}
			}else if(mm==4 || mm==6 || mm==9 || mm==11){
				if(dd>30){
					dd=1;
					mm=mm++;
					
					if(mm>12){
						mm=1;
						yyyy++;
					}
				}
			}else{
				if(dd>28){
					dd=1;
					mm=mm++;
					
					if(mm>12){
						mm=1;
						yyyy++;
					}
				}
			}
			
			if(dd<10){
				dd='0'+dd
			} 
			if(mm<10){
			    mm='0'+mm
			} 
		
			today = yyyy+'-'+mm+'-'+dd;
			
		    var input = document.getElementById("start_day");
		    input.setAttribute("min", today);
		    

			
			
			$('#start_day').on('change', function(){
				var input = document.getElementById("end_day");
			    input.setAttribute("min", this.value);
			});
		});
	</script>
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
		
		function check(){
			var inputs = document.inputForm;
			
			if(!inputs.subject.value){	//false일때, 값이 없을때
				alert("목표명을 입력하세요.");
				return false;
			}
			
			if(!inputs.content.value){	//false일때, 값이 없을때
				alert("목표 내용을 입력하세요.");
				return false;
			}
			
			if(!inputs.amount.value){	//false일때, 값이 없을때
				alert("목표 금액을 입력하세요.");
				return false;
			}
			
			if(!inputs.start_day.value){	//false일때, 값이 없을때
				alert("시작일을 입력하세요.");
				return false;
			}
			
			if(!inputs.end_day.value){	//false일때, 값이 없을때
				alert("종료일을 입력하세요.");
				return false;
			}
			
			if(!inputs.people.value){	//false일때, 값이 없을때
				alert("참여자 수를 입력하세요.");
				return false;
			}
			
			if(inputs.isopen.value=="0"){
				if(!inputs.password.value){	//false일때, 값이 없을때
					alert("비밀번호를 입력하세요.");
					return false;
				}
			}
		}
	</script>
</head>
<body>
	<br />
	<h1> 그룹 개설 신청폼 </h1>
	<form action="/moamore/team/groupOpenPro.moa" method="post" name="inputForm" onsubmit="return check()">
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
				<td><input type="date" id="start_day" name="start_day" min="2020-10-20" /></td>
			</tr>
			<tr>
				<td> 종 료 일 </td>
				<td><input type="date" id="end_day" name="end_day"  /></td>
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




