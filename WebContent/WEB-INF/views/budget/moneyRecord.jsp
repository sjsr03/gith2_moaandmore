<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수입 지출 내역</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<style type="text/css">

</style>
<script>
	$(document).ready(function(){
		$("#month").hide();
	
		// recordOpt 선택에 따라 하위내역 보여주기/숨기기
		$("#recordOpt").change(function(){

			if($("#recordOpt").val()=="budget"){
				$("#serachDate").css("display", "block");
				$("#month").css("display", "none");
			}else{
				if($("#recordOpt").val()=="income"){// 예산외 수입일 때
					$("#serachDate").css("display", "none");
					$("#month").css("display", "block");
					$("#month option:eq(0)").prop("selected", true);
				}else if($("#recordOpt").val()=="outcome"){
					$("#serachDate").css("display", "none");
					$("#month").css("display", "block");
					$("#month option:eq(0)").prop("selected", true);
				}
				$("#month").on('change', function(){
					console.log("배고파 1111")
					
					$.ajax({
						type : "POST",
						url : "selectNoBudgetRecord.moa",
						data : {"serachDate":$("#month").val(),"type":$("#recordOpt").val()},
						error : function(request,status,error){
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						},
						success : function(data){		
							$("#moneyLog").empty();
							$("#moneyLog").append(data);									
						}
					});

				});
			}
			/*else if($("#recordOpt").val()=="outcome"){// 예산외 지출일 때 
				$("#serachDate").css("display", "none");
				$("#month").css("display", "block");
				$("#month option:eq(0)").prop("selected", true);
				$("#month").on('change', function(){
					//console.log("배고파 22")
					/*
					$.ajax({
						type : "POST",
						url : "selectBudgetRecord.moa",
						data : {serachDate:$("#serachDate").val()},
						error : function(request,status,error){
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						},
						success : function(data){		
							$("#moneyLog").empty();
							$("#moneyLog").append(data);									
						}
					});
					
				});
				
			}
			*/
			
		});
		// 예산 내역 볼때
		$("#serachDate").on('change', function(){
			// 컨트롤러로 값 보내기 ajax				
			// 예산 지출 내역 리스트랑 예산 카테고리 맵으로  list에 통으로 담아서 가져올 것.
			console.log($("#serachDate").val());
			$(document).ready(function(){
				$.ajax({
					type : "POST",
					url : "selectBudgetRecord.moa",
					data : {serachDate:$("#serachDate").val()},
					error : function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					},
					success : function(data){		
						$("#moneyLog").empty();
						$("#moneyLog").append(data);									
					}
				});
							
			});	
		});	
		
	});	
	
	
	
</script>
<body>
<div align="center">
	<select name="recordOpt" id="recordOpt">
		<option value="budget">예산내역</option>
		<option value="income">수입내역(예산외)</option>
		<option value="outcome">지출내역(예산외)</option>
	</select> 
	<br />
	<%-- month 셀렉트는 지출/수입(예산외) 일때만 뜰 것  --%>
	<select name="month" id="month">
		<option value=2020-01>1월 2020년</option>
		<option value=2020-02>2월 2020년</option>
		<option value=2020-03>3월 2020년</option>
		<option value=2020-04>4월 2020년</option>
		<option value=2020-05>5월 2020년</option>
		<option value=2020-06>6월 2020년</option>
		<option value=2020-07>7월 2020년</option>
		<option value=2020-10>10월 2020년</option>
	</select>
	<%-- 밑의 date는 예산일 때만 뜰 것 --%>
	<input type="date" name="serachDate" id="serachDate" />

</div>	

<div id="moneyLog">


</div>
</body>
</html>