<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- Custom fonts for this template-->
<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
</head>
<body>

<label>아이디<input type="text" id="memId"/></label><br/>
<label>하루 기록 개수<input type="number" id="count" min="1" max="10"/></label><br/>
<label>시작일<input type="date" id="startDay"/></label><br/>
<label>종료일<input type="date" id="endDay"/></label><br/>
<label>유형</label>
<select id="type">
	<option value="budget">예산지출</option>
	<option value="outcome">예산외 지출</option>
	<option value="income">예산외 수입</option>
</select><br/>

<input type="button" value="기록" id="start"/>

</body>
<script>
	$(document).ready(function(){
		
		$("#start").on('click', function(){
			
			$.ajax({
				url:"recordMacro.moa",
				data:{
					"id":$("#memId").val(),
					"count":$("#count").val(),
					"StartDay":$("#startDay").val() + " 00:00:00",
					"EndDay":$("#endDay").val() + " 00:00:00",
					"type":"budget"
				},
				success:function(data){
					alert(data);
				},
				error:function() {
					alert("오류");
				},
			});
		});
		
	});

</script>
</html>