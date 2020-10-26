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
<body id="page-top">
<jsp:include page="../sidebar.jsp"/>
<!-- 본문내용 시작 -->
<div class="container-fluid" align="center">
	<!-- 페이지 이름  -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-80"> 내역보기 </h1>
	</div>

	<!-- 첫번째 줄 -->
	<div class="row">
		<div class="col-lg-12">
			<div class="card shadow mb-4">
				<div class="card-body" align="center">
					
					<div class="input-group" style="justify-content:center;">
						<button class="btn btn-primary" onclick="popupRecordForm()" style="margin-right: 10px;">입출력 입력</button>
						<input type="text" id="searchKeyword" class="form-control bg-light border-0 small" style="max-width:340px; border-top-left-radius:5px; border-bottom-left-radius:5px;" aria-label="Search" aria-describedby="basic-addon2"/>
						<div class="input-group-append"> 
							<button id="searchbth" class="btn btn-primary">
							<i class="fas fa-search fa-sm"></i>
							</button>
						</div>
					</div>
					<br /> <br />
					<input type="hidden" id="type" name="type" value="type">
					<div class="custom-control custom-checkbox">
						<label><input type="checkbox" name="recordChk" class="recordChk" id="budget" value="budget" /> 예산 </label>
						<label><input type="checkbox" name="recordChk" class="recordChk" id="income" value="income" /> 수입(예산외)</label>
						<label><input type="checkbox" name="recordChk" class="recordChk" id="outcome" value="outcome" /> 지출(예산외)</label>
					</div>
					<br />
					<div class="input-group" style="justify-content:center;">
						<button type="button" id="beforeBtn" class="btn btn-default btn-lg">
						  <i class="fas fa-angle-double-left" aria-hidden="true"></i>
						</button>
						
						<%-- month 셀렉트는 지출/수입(예산외) 일때만 뜰 것  --%>
						<select name="month" id="month"></select>
						<%-- 밑의 date는 예산일 때만 뜰 것 --%>
						<input type="date" name="searchDate" id="searchDate" />
						
						<button type="button" id="afterBtn" class="btn btn-default btn-lg">
						  <i class="fas fa-angle-double-right" aria-hidden="true"></i>
						</button>
					</div>
					<br />
					
					
					<div id="moneyLog">
					
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	
<jsp:include page="../footer.jsp"/>

<script>
var searchDate="${searchForRecordDTO.searchDate}";
var pageNum="${searchForRecordDTO.pageNum}";
var type = "${searchForRecordDTO.type}";
var keyword ="${searchForRecordDTO.keyword}";
var nowYear =2020;
var nowMonth ="";

	$(document).ready(function(){
		console.log("시작하자마자 키워드 값 : " + keyword);
		console.log(nowYear);
		$("#beforeBtn").click(function(){
			minusYear();
		});
		$("#afterBtn").click(function(){
			plusYear();
		});

		console.log("searchDate : " + searchDate);
		console.log("pageNum : " + pageNum);
		console.log("type : " + type);
		
		$("#searchDate").on('change', function(){			
			searchDate = $("#searchDate").val();	
			type = "budget";
			$("#searchKeyword").val('');
			selectBudget(searchDate, pageNum, type);
		});
		$("#month").on('change', function(){
			console.log("예산외 연도 월 체크!!:"+$("#month").val());
			searchDate = $("#month").val();
			pageNum="${searchForRecordDTO.pageNum}";
			$("#searchKeyword").val('');
			selectNobudget(searchDate, pageNum, type);
		});
		
		selectRecordByTypeDateKeyword();
		
		// recordChk 선택에 따라 하위내역 보여주기/숨기기
		$(".recordChk").change(function(){
			pageNum ="";
			recordCheck();
			$("#searchKeyword").val('');
			console.log("---");
			console.log("체크하고 타입 체크 : " + type);
			console.log("체크하고 페이지 넘 체크 : " + pageNum);
			console.log("체크하고 데이트 체크 : " + searchDate);
			console.log("---");
			searchDate ="";
			type ="";
			keyword="";
			
			recordCheck();
			
		});	
		// 검색하기 버튼 눌렀을 때 내역 다시 가져오기
		$("#searchbth").click(function(){
			console.log($("#searchKeyword").val());
			keyword = $("#searchKeyword").val();
			pageNum="";
			selectRecordByTypeDateKeyword(type, searchDate, keyword);
		});
	});

	
// 타입에 따라 체크박스 체크처리해주고 내역가져오는 함수 
function selectRecordByTypeDateKeyword(){
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
	}else if(type == "" && searchDate == ""){
		console.log("다 비어있다");
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
	if(keyword != ""){
		$("#searchKeyword").val(keyword);
	}
	$("#searchDate").val(searchDate);
	recordCheck();
}
// 체크박스와 날짜 기준으로 해당 내역 가져오는 함수 
 function recordCheck(){

	 console.log("type>>>> : " + type);
		var chkArr = [];
		var chkStr = "";
		$("input[name=recordChk]:checked").each(function(){
			chkArr.push($(this).val());		
			chkStr += $(this).val();
		});
		console.log("recordCheck 타입 체크 : " + chkStr);
		$("#type").val(chkStr);
		type=chkStr;
		if(chkArr.length > 1 ){ // 두개 이상 체크 한 경우 
			$("#beforeBtn").css("display", "none");
			$("#afterBtn").css("display", "none");
			$("#searchDate").css("display", "none");
			$("#month").css("display", "none");	
			console.log("recordCheckd에서 타입;;; : " + type);
			manycheck(pageNum, type, keyword);
		
		}else if(chkArr.length == 1){// 하나만 했을 때 
			if(chkArr.indexOf("budget") >= 0){ // 값에 budget이 들어가있으면 
				console.log("체크박스에 budget체크");
				$("#searchDate").css("display", "block");
				$("#month").css("display", "none");
				$("#beforeBtn").css("display", "none");
				$("#afterBtn").css("display", "none");
				if(searchDate != ""){
					
					selectBudget(searchDate, pageNum, type, keyword); 	
				}else{// searchDate가비어있으면 현재날짜로 가져오기
					console.log("체크박스에 수입 or 지출체크");
					// 현재 날짜 구하기
					var date = new Date();
					searchDate = moment(date).format('YYYY-MM-DD');
					console.log("지금 날짜 나와라 :::" + searchDate);
					$("#searchDate").val(searchDate);
					selectBudget(searchDate, pageNum, type, keyword); 
				}	
			}else{  // 값이 budget이 아니면 
				$("#month").empty();
				$("#beforeBtn").css("display", "block");
				$("#afterBtn").css("display", "block");
				nowYear = 2020;
				makeMonth();
				checkedMonth();

				console.log("타아아아아입  : " + type );
				$("#searchDate").css("display", "none");
				$("#month").css("display", "block");

				selectNobudget(searchDate, pageNum, type, keyword);
				
			}
		}else if(chkArr.length == 0 && type=="" & pageNum=="" && searchDate==""){  
			console.log("여기니?????????????????????///");
			// 체크박스에 체크가 없으면 무조건 예산내역 뜨게 처리!
			
			// 현재 날짜 구하기
			var date = new Date();
			searchDate = moment(date).format('YYYY-MM-DD');
			// 날짜, type budget으로 예산 구하는 함수 호출
			//pageNum = "${searchForRecordDTO.pageNum}"
			$("#searchDate").val(searchDate);
			$("#month").css("display", "none");
			$("#beforeBtn").css("display", "none");
			$("#afterBtn").css("display", "none");
			$("#searchDate").css("display", "block");
			$("#budget").prop("checked", true); // 예산으로 체크 
			type="budget";
			keyword="";
			pageNum="";
			
			selectBudget(searchDate, pageNum, type, keyword); 
		}
		console.log(chkStr);
	}
	
	// 수입 or 지출일 때 
	function selectNobudget(searchDate, pageNum, type, keyword){
		$.ajax({
			type : "POST",
			url : "selectNoBudgetRecord.moa",
			data : {"searchDate":searchDate,"type":type,"pageNum":pageNum, "keyword":keyword},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});
	}

	function checkType(type){
		console.log("checkDateType에서 타입 : "+ type);
		if(type == "income" || type=="outcome"){
			$("#month").on('change', function(){
				searchDate = $("#month").val();
				selectNobudget(searchDate, pageNum, type);
			});
		}
	}

	// 예산 내역 보는 함수
	function selectBudget(searchDate, pageNum, type, keyword){
		console.log("selectBudget()내에서 키워드 확인 : " +  keyword);
		$.ajax({
			type : "POST",
			url : "selectBudgetRecord.moa",
			data : {"searchDate":searchDate,"type":type,"pageNum":pageNum, "keyword":keyword},			
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});	
	}	
	// 체크박스에 두개 이상 체크했을 때 내역 가져오는 함수 
	function manycheck(pageNum, type, keyword){ 
		console.log("manycheck 함수에서 키워드 값 : " + keyword);
		$.ajax({
			type : "POST",
			url : "selectRecords.moa",
			data : {"type":type, "pageNum":pageNum, "keyword":keyword},			
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){		
				$("#moneyLog").empty();
				$("#moneyLog").append(data);									
			}
		});
	}	
	
	// 연도 더해주기
	function plusYear(){  
		nowYear += 1;

		console.log(nowYear);
		makeMonth();
		selectNobudget(searchDate, pageNum, type, keyword);
	}
	// 연도 빼주기
	function minusYear(){
		nowYear -= 1;

		console.log(nowYear);
		makeMonth();
		selectNobudget(searchDate, pageNum, type, keyword);
	}
	
	// 수입, 지출 처음 실행시 당월로 체크후 실행
	function checkedMonth(){
		// 현재 날짜 구하기
		var date = new Date();
		var currMonth = moment(date).format('YYYY-MM');
		$("#month").val(currMonth).prop("selected", true);
		searchDate = currMonth;
		selectNobudget(searchDate, pageNum, type, keyword);
	}
	// month selectbox 연도, 월 표시
	function makeMonth(){
		$("#month").empty();
		for(var i = 1; i <= 12; i++){
			var month = i > 9 ? i : "0" + i;
			//$("#month").append('<option value="+'+month+'">'+ month + '월</option>');
			$("#month").append("<option value='"+nowYear+"-"+month+"'>"+nowYear+"년 "+month + "월"+"</option>");
			console.log("makeMonth안에서 날짜 : " + searchDate);
			searchDate="";
		}
	}
</script>
</html>