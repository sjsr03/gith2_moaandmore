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
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
</head>
<style type="text/css">

</style>
<script>
var searchDate="${searchForRecordDTO.searchDate}";
var pageNum="${searchForRecordDTO.pageNum}";
var type = "${searchForRecordDTO.type}";
 
	$(document).ready(function(){
		
		console.log("searchDate : " + searchDate);
		console.log("pageNum : " + pageNum);
		console.log("type : " + type);
		$("#searchDate").on('change', function(){			
			searchDate = $("#searchDate").val();	
			type = "budget";
			selectBudget(searchDate, pageNum, type);
		});
		$("#month").on('change', function(){
			searchDate = $("#month").val();
			pageNum="${searchForRecordDTO.pageNum}";
			
			selectNobudget(searchDate, pageNum, type);
		});
		
		if(type == "budget" && searchDate != null){ // budget일경우
			$("#budget").prop("checked", true);
			recordCheck();
			console.log("ㅎㅎㅎ");
		}else if(type == "income" && searchDate != null){
			$("#income").prop("checked", true);
			$("#month").val(searchDate);
			console.log("수우입");
		}else if(type == "outcome" && searchDate != null){
			$("#outcome").prop("checked", true);
			$("#month").val(searchDate);
			console.log("지추울");
		}else{ //여러개 인 경우
			//체크 해주고 recordCheck 호출
			console.log("타입 :   " + type);
			if(type == "budgetincome"){
				$("#budget").prop("checked", true);
				$("#income").prop("checked", true);
			}else if(type == "budgetoutcome" ){
				$("#budget").prop("checked", true);
				$("#outcome").prop("checked", true);
			}else if(type=="incomeoutcome" ){
				$("#income").prop("checked", true);
				$("#outcome").prop("checked", true);
			}else if(type=="budgetincomeoutcome"){
				$("#budget").prop("checked", true);
				$("#income").prop("checked", true);
				$("#outcome").prop("checked", true);
			}
		}
		$("#searchDate").val(searchDate);
		recordCheck();
		// recordChk 선택에 따라 하위내역 보여주기/숨기기
		$(".recordChk").change(function(){
			pageNum="";
			searchDate="";
			recordCheck();
			
			
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
	});
 
 function recordCheck(){
	 //history.replaceState({}, null, location.pathname);
	 console.log("type>>>> : " + type);
		var chkArr = [];
		var chkStr = "";
		$("input[name=recordChk]:checked").each(function(){
			chkArr.push($(this).val());		
			chkStr += $(this).val();
		});
		
		$("#type").val(chkStr);
		type=chkStr;
		if(chkArr.length > 1 ){ // 두개 이상 체크 한 경우 
			
			$("#searchDate").css("display", "none");
			$("#month").css("display", "none");	
			console.log("recordCheckd에서 타입;;; : " + type);
			manycheck(pageNum, type);		
		
		}else if(chkArr.length == 1){// 하나만 했을 때 
			if(chkArr.indexOf("budget") >= 0){ // 값에 budget이 들어가있으면 
				$("#searchDate").css("display", "block");
				$("#month").css("display", "none");
				selectBudget(searchDate, pageNum, type); 		
			}else{
				console.log("타아아아아입  : " + type );
				$("#searchDate").css("display", "none");
				$("#month").css("display", "block");
				//$("#month option:eq(0)").prop("selected", true);
			

				selectNobudget(searchDate, pageNum, type);
				
			}
		}else if(chkArr.length == 0 && type=="" & pageNum=="" && searchDate==""){ 
			
			// 현재 날짜 구하기
			var date = new Date();
			searchDate = moment(date).format('YYYY-MM-DD');
			// 날짜, type budget으로 예산 구하는 함수 호출
			pageNum = "${searchForRecordDTO.pageNum}"
			$("#searchDate").val(searchDate);
			$("#month").css("display", "none");
			$("#budget").prop("checked", true); // 예산으로 체크 
			type="budget";
			selectBudget(searchDate, pageNum, type); 
		}
		console.log(chkStr);
	}
	
	// 수입 or 지출일 때 
	function selectNobudget(searchDate, pageNum, type){
		$.ajax({
			type : "POST",
			url : "selectNoBudgetRecord.moa",
			data : {"searchDate":searchDate,"type":type,"pageNum":pageNum},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});
	}
	// 수입/지출일 떄
	/*
	$("#month").on('change', function(){
		searchDate = $("#month").val();
		type = $("#type").val();
		
		pageNum="${searchForRecordDTO.pageNum}";
		console.log("타입타입 : " + type);
		
		selectNobudget(searchDate, pageNum, type);
	});
	*/
	function checkType(type){
		console.log("checkDateType에서 타입 : "+ type);
		if(type == "income" || type=="outcome"){
			$("#month").on('change', function(){
				searchDate = $("#month").val();
				type = 
				selectNobudget(searchDate, pageNum, type);
			});
		}
	}

	// 예산 내역 볼때
	/*
	$("#searchDate").on('change', function(){
		// 컨트롤러로 값 보내기 ajax				
		// 예산 지출 내역 리스트랑 예산 카테고리 맵으로  list에 통으로 담아서 가져올 것.
		//console.log($("#searchDate").val());	
		// 선택한 날짜 변수에 담아주기 
		searchDate = $("#searchDate").val();
		type=$(".recordChk").val();
		//console.log("데이트 >>" + searchDate);
		//console.log("페이지넘 >>" + pageNum);
		//console.log("타입 >>" + type);
		selectBudget(searchDate, pageNum, type);
	});
	*/
	// 예산 내역 보는 메서드
	function selectBudget(searchDate, pageNum, type){

		$.ajax({
			type : "POST",
			url : "selectBudgetRecord.moa",
			data : {"searchDate":searchDate,"type":type,"pageNum":pageNum},			
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});	
	}	
	// 체크박스에 두개 이상 체크했을 때 호출하는 메서드 
	function manycheck(pageNum, type){ 
		$.ajax({
			type : "POST",
			url : "selectRecords.moa",
			data : {"type":type, "pageNum":pageNum},			
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});
	}	
</script>
<body>
<div align="center">
	<input type="hidden" id="type" name="type" value="type">
	<label><input type="checkbox" name="recordChk" class="recordChk" id="budget" value="budget" /> 예산 </label>
	<label><input type="checkbox" name="recordChk" class="recordChk" id="income" value="income" /> 수입(예산외)</label>
	<label><input type="checkbox" name="recordChk" class="recordChk" id="outcome" value="outcome" /> 지출(예산외)</label>
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
		<option value=2020-08>8월 2020년</option>
		<option value=2020-09>9월 2020년</option>
		<option value=2020-10>10월 2020년</option>
		<option value=2020-11>11월 2020년</option>
		<option value=2020-12>12월 2020년</option>
	</select>
	<%-- 밑의 date는 예산일 때만 뜰 것 --%>
	<input type="date" name="searchDate" id="searchDate" />

</div>	

<div id="moneyLog">


</div>
</body>
</html>