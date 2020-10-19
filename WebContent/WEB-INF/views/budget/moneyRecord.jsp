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
	
		// recordChk 선택에 따라 하위내역 보여주기/숨기기
		$(".recordChk").change(function(){
			
			
			var checkedList= [];
			var checkedStr = "";
			
			
			
			$(".recordChk").each(function(){
				if($(this).is(":checked")==true) {
					checkedList.push($(this).val());
					checkedStr += $(this).val();
				}
			});
			
			
			if(checkedList.length > 1) {
				$("#searchDate").css("display", "none");
				$("#month").css("display", "none");
				manycheck();
				
				if(checkedList.length == 3) {
					$("#type").val("all");
				} else {
					$("#type").val(checkedStr);
				}
			} else {
				if(checkedList.indexOf("budget") == 0) {
					$("#searchDate").css("display", "block");
					$("#month").css("display", "none");
				} else {
					$("#searchDate").css("display", "none");
					$("#month").css("display", "block");
					$("#month option:eq(0)").prop("selected", true);
				}
			}
			
			
			
			
			/*
			if($(".recordChk:checked").length==2){// 2개 선택했을 때
				// 하위항목 다 숨김
				$("#searchDate").css("display", "none");
				$("#month").css("display", "none");

				if($("#budget").is(":checked") == true && $('#income').is(":checked") == true){ // budget, income
					$("#type").val("budgetincome");
					manycheck();
				}else if($("#budget").is(":checked") == true && $('#outcome').is(":checked") == true){// budget, outcome
					$("#type").val("budgetoutcome");
					manycheck();
				}else if($("#income").is(":checked") == true && $('#outcome').is(":checked") == true){// income, outcome
					$("#type").val("incomeoutcome");
					manycheck();
				}
			}else if($(".recordChk:checked").length==3){ // budget, income, outcome
				$("#type").val("all");
				manycheck();
			}else{	
				if($("#budget").is(":checked") == true){
					console.log("예싼만");
					$("#searchDate").css("display", "block");
					$("#month").css("display", "none");
					$("#type").val("budget");
				}else if($('#income').is(":checked") == true){//수입일 때
					$("#searchDate").css("display", "none");
					$("#month").css("display", "block");
					$("#month option:eq(0)").prop("selected", true);
					$("#type").val("income");
				}else if($('#outcome').is(":checked") == true){//지출일 때
					$("#searchDate").css("display", "none");
					$("#month").css("display", "block");
					$("#month option:eq(0)").prop("selected", true);
					$("#type").val("outcome");
				}
			}
			*/
		});	
		
		// 수입/지출일 떄
		$("#month").on('change', function(){
			console.log("배고파 1111")
			
			$.ajax({
				type : "POST",
				url : "selectNoBudgetRecord.moa",
				data : {"searchDate":$("#month").val(),"type":$("#type").val()},
				error : function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				},
				success : function(data){		
					$("#moneyLog").empty();
					$("#moneyLog").append(data);									
				}
			});

		});
		
		// 예산 내역 볼때
		$("#searchDate").on('change', function(){
			// 컨트롤러로 값 보내기 ajax				
			// 예산 지출 내역 리스트랑 예산 카테고리 맵으로  list에 통으로 담아서 가져올 것.
			console.log($("#searchDate").val());	
			$.ajax({
				type : "POST",
				url : "selectBudgetRecord.moa",
				data : {"searchDate":$("#searchDate").val(),"type":$("#type").val()},			
				error : function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				},
				success : function(data){		
					$("#moneyLog").empty();
					$("#moneyLog").append(data);									
				}
			});					
		});
		
		function manycheck(){ // 체크박스에 두개 이상 체크했을 때 에이작스
			$.ajax({
				type : "POST",
				url : "selectRecords.moa",
				data : {"type":$("#type").val()},			
				error : function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				},
				success : function(data){		
					$("#moneyLog").empty();
					$("#moneyLog").append(data);									
				}
			});
		}
	});
	
</script>
<body>
<div align="center">
	<input type="hidden" id="type" name="type" value="type">
	<label><input type="checkbox" class="recordChk" id="budget" value="budget" checked/> 예산 </label>
	<label><input type="checkbox" class="recordChk" id="income" value="income" /> 수입(예산외)</label>
	<label><input type="checkbox" class="recordChk" id="outcome" value="outcome" /> 지출(예산외)</label>
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
	<input type="date" name="searchDate" id="searchDate" />

</div>	

<div id="moneyLog">


</div>
</body>
</html>