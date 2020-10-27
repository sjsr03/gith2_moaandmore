 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수입지출내역 추가</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<style>
ul{
	list-style:none;
	padding-left:5px;
}
.cateSelectBox{
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none; 
	background: url(../resources/img/select_arrow.png) no-repeat 95% 50%;
	width:170px;
	height:38px;
	margin-top: 5px;
	margin-bottom: 5px;
	border-radius: 4px;
	border: solid 1px #d1d3e2;
	padding: 6px 12px;
}
.inputBox{
	width:170px;
	height:38px;
	border-radius: 4px;
	border: solid 1px #d1d3e2;
	margin: 4px 0px;
	padding: 6px 12px;
	background: #FFFFFFF;

}
#checkbox{
	margin-left: 5px;
}
.btn{
	margin: 7px 0px;
	padding: 6px 12px;
	margin-left: 5px;

}
#content{
	margin-top: 7px;
	margin-bottom:7px;
}
#amount{
	margin-top: 7px;
	margin-bottom: 7px;
}
#memo{
	margin-top: 7px;
	margin-bottom: 7px;
}
.fileRegiBtn label {
	display: inline-block; 
	color: #ffffff; 
	font-size: inherit; 
	line-height: normal; 
	vertical-align: middle; 
	background-color: #007bff; 
	cursor: pointer; 
	border: 1px solid #007bff; 
	border-radius: .25em;
	margin: 7px 0px;
	padding: 6px 12px;
}
/* 파일 선택시 선택된 파일명이 붙는 것을 가려줌 */
.fileRegiBtn input[type="file"]{
	position: absolute; 
	width: 1px; 
	height: 1px; 
	padding: 0; 
	margin: -1px; 
	overflow: hidden; 
	clip:rect(0,0,0,0); 
	border: 0;
}
</style>

<!-- Custom fonts for this template-->
<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template-->
<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
<!-- 버튼 관련 템플릿 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>

<!-- 본문내용 시작 -->
 	<div class="container-fluid">
<!-- Page Heading -->
   <div class="d-sm-flex align-items-center justify-content-between mb-4">
     <h1 class="h3 mb-0 text-gray-800"> 수입 지출 입력하기 </h1>
   </div>
   
   <!--  첫번째 줄  -->
   <div class="row">
   	<div class="col-lg-12">
   		<div class="card shadow mb-4">
   		<div class="card-body"> 			
   			<!-- 폼시작  -->
   			<div class="form-group">
 
				<form id="recordForm" action="/moamore/record/recordPro.moa" method="post" enctype="multipart/form-data">
				<%-- 데이터 넘어갈 때 수입인지 지출인지 예산내 지출인지도 보내줘야한다. --%>
				<input type="hidden" id="type" name="type" value="type"/>
				<input type="hidden" id="budget_no" name="budget_no" value="${budgetNum}"/>
				<input type="hidden" id="category_no" name="category_no" value="0"/> 
					
					<div class="animated--fade-in">
						<div class="header">	
							<input id="checkbox" type="checkbox" /> 예산 외 선택 </br>
							<!-- 지출 수입 버튼은 예산외 버튼 선택해야 보임 -->
							<div>
								<input type="button" class="btn btn-light" value="지출" id="outcomebtn" name="outcomebtn" style="position: relative"/>
								<input type="button" class="btn btn-light" value="수입" id="incomebtn" name="incomebtn" style="position: relative"/>
							</div>
						</div>
						<div class="content">
							<ul>
								<li>
								<!-- 날짜 시간  -->
									<div>
										<input type="date" id="date" name="date" class="inputBox"/>
									</div>
								<!-- 시간 -->			
									<div>
										<input type="time" id="time" name="time" class="inputBox"/>
									</div>
								</li>
								<li class="category-section">
									<%-- 지출 카테고리 --%>
									<div class="input-area">
										<select id="outcomecategory" name="outcomecategory" class="cateSelectBox" >
										<c:forEach var="outcomeCategories" items= "${outcomeCategories}" >
											<option value="${outcomeCategories.category_no}">${outcomeCategories.category_name}</option>
										</c:forEach>
										</select>
									</div>
									<%-- 수입 카테고리 --%>
									<div class="input-area">
										<select id="incomecategory" name="incomecategory" class="cateSelectBox">
										<c:forEach var="incomeCategories" items= "${incomeCategories}" >
											<option value="${incomeCategories.category_no}">${incomeCategories.category_name}</option>
										</c:forEach>
										</select>
									</div>
									<%-- 예산내 지출 카테고리 --%>
									<%-- categories 번호가 key 이름이 value --%>
									<div class="input-area">
										<select id="category" name="category" class="cateSelectBox" >
										<c:forEach var="categories" items= "${categories}" >
											<option value="${categories.key}">${categories.value}</option>
										</c:forEach>
										</select>
									</div>
								</li>
								<li>
									<div>
										<input class="form-control form-control-user input"  type="text" placeholder="내역을 입력하세요" id="content" name="content" />
									</div>
								</li>
								<li>
									<div>
		
										<input class="form-control form-control-user input" type="text" placeholder="금액을 입력하세요" id="amount" name="amount" />
									</div>
								</li>			
								<li>
									<div>
										<input class="form-control form-control-user input" type="text" placeholder="메모(최대140자)" id="memo" name="memo" maxlength="140"  />
									</div>
								</li>
								<!-- 이미지 파일 등록 -->
								<li>
									<div class="form-group" style="margin:8px 0 8px;">
										<input id="fileName" class="inputBox" value="파일선택"  disabled="disabled" style="width:65%; display: inline" class="btn btn-light"/>
											<div class="fileRegiBtn">
												<label for="image">파일등록</label>
												<input type="file" id="image" name="image"/>
											</div>
										<!--  커버 이미지 들어오븐 부분(임시로 이미지 넣어줌) -->
										<div class="selectCover" style="padding-left: 0;">
											<img id="cover" src="../resources/img/defaultImg.gif" style="width: 350px; height: 300px;">
										</div>
									</div>
								</li>
							</ul>
							<div class="">
							<input type="reset" value="취소" class="btn btn-light" />
							<input type="button" id="check" value="확인" class="btn btn-primary" />
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>
	</div>
	</div>
	</div>
</body>
<script>
	// 체크박스 상태 확인(체크된 상태면 nobudget 아니면 budget)
	$(document).ready(function(){
		console.log("타입 :"  + $("#type").val()); 
		// input 태그 중 date 에도 기본값으로 오늘 날짜 넣어주기 
		$("#date").val("${today}");
		getTime();
		
		$("#time").val(new Date().toISOString().slice(11, 16));
		
		var budget_no = 0;
		// 처음엔 수입 지출 카테고리/수입지출 버튼 숨기기 (예산지출이 기본)
		$("#incomecategory").css("display", "none"); 
		$("#outcomecategory").css("display", "none"); 
		$("#outcomebtn").hide();
		$("#incomebtn").hide();
		
		$("#checkbox").change(function(){
			if($("#checkbox").is(":checked")){ // 예산외 선택에 체크가 되어있으면
				
				// 예산 관련된거 없어져야함
				$("#incomebtn").show();	
				$("#outcomebtn").show();	
				$("#category").css("display", "none");
			}else{
				$("#type").val("budget");
				$("#outcomebtn").hide();	
				$("#incomebtn").hide();	
				$("#category").css("display", "block");
				$("#outcomecategory").css("display", "none"); 
				$("#incomecategory").css("display", "none"); 
			}
		});	
		
		$("#date").on('change',function(){
			if($("#checkbox").is(":checked")){
			}else{
				// 예산일 달력 체크하면 값 가져오게 처리
				//$("#type").val("budgetOutcome");
				$("#type").val("budget");
				
				console.log("date에서 타입 : "+$("#type").val());

				// 예산 카테고리 가져오기위해 컨트롤러로 값 보내기 ajax				
				$(document).ready(function(){
					var start = new Date("${budgetDate.get(1)}");
					var end = new Date("${budgetDate.get(0)}");		
					var selectedDay = new Date($("#date").val());
					console.log("start 날짜 : " + start);
					console.log("end 날짜 : " + end);
					console.log("selectedDay 날짜 : " + selectedDay);
					///////////////////////////////////////////////////////////////////////////////
					console.log("결과>> : " + (start <= selectedDay) && (end >= selectedDay));
					console.log("첫번째결과>> : " + (start <= selectedDay));
					console.log("두번째결과>> : " + (end >= selectedDay));
					
					if((start <= selectedDay) && (end >= selectedDay)){
						$.ajax({
							type : "POST",
							url : "budgetCategory.moa",
							data : {date:$("#date").val(), id:"${id}"},
							dataType : "json", 
							async: false,
							error : function(request,status,error){
								alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

							},
							success : function(data){							
								console.log($("#date").val());
								console.log(typeof date);
								// 기간에 해당하는 예산의 카테고리로 셀렉트 옵션 새로 바꿔주기
								$("#category").find("option").remove(); // 기존 카테고리 셀렉트 옵션 삭제

								for(var key in data){
									console.log("컬럼:" + key + "value : " + data[key]);
									//console.log(typeof key);
									if(key != 'budgetNum'){ 
										$("#category").append("<option value='"+key+"'>"+data[key]+"</option>");
									}else{ // budgetNum 이면 변수에 담아주기
										budget_no = data[key];
									}								
								}
								$("#category").css("display", "block");
							}
						});
					}else{
						alert("날짜에 해당하는 예산이 없습니다. 다시 선택해주세요.");
						$("#date").val('');
						
					}				
				});
			}
		});
		

		// 버튼 누를 때마다 카테고리 나타내기/숨기기
		$("#outcomebtn").click(function(){ // 지출 카테고리로 세팅 
			$("#outcomebtn").attr('class','btn btn-primary');
			$("#incomebtn").attr('class','btn btn-light');
			$("#outcomecategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#type").val("outcome");
			console.log($("#type").val());
			console.log("셀렉트 박스 옵션값은 ???? ");
			console.log($("#outcomecategory option:selected").val())
			$("#category_no").val($("#outcomecategory option:selected").val());
			// 카테고리 선택하면 hidden으로 값넘겨주기
			$("#outcomecategory").change(function(){
				console.log($(this).val())		
				$("#category_no").val($("#outcomecategory option:selected").val());
			});
			
		});
		$("#incomebtn").click(function(){ // 수입 카테고리로 세팅
			$("#incomebtn").attr('class','btn btn-primary');
			$("#outcomebtn").attr('class','btn btn-light');
			$("#incomecategory").css("display", "block");
			$("#outcomecategory").css("display", "none"); 
			$("#type").val("income");
			console.log($("#type").val());
			console.log("셀렉트 박스 옵션값은 ???? ");
			$("#category_no").val($("#incomecategory option:selected").val());
			$("#incomecategory").change(function(){
				console.log($(this).val())
				$("#category_no").val($("#incomecategory option:selected").val());
				$("#type").val("income");

			});

		});
		$("#check").click(function(){
			//////////////////////////////////////
			console.log("타입체크 : "+$("#type").val());
			if($("#type").val() == "type"){
				$("#type").val("budget");
			}
			// budgetNum도 hidden으로 보내주기
			if(budget_no != 0){
				var intBudget_no = Number(budget_no);
				//console.log(typeof intBudget_no);
				$("#budget_no").val(intBudget_no);
			}
			// category_no 예산일 때만 카테고리 넘버 보내주기 
			if($("#type").val() == "budget"){

				//$("#type").val("type");
				type="budget";
				console.log("카테고리 : " +  $("#category option:selected").val());
				var selectedOption = $("#category option:selected").val(); 
				
				var numberOption = Number(selectedOption);
				$("#category_no").val(numberOption);
				console.log(typeof numberOption);
				console.log("카테고리 번호" + numberOption); 	
			}
			
			console.log($("#category_no").val());
			console.log($("#image").val());
			$("#recordForm").submit();
		});
		
		$("#image").change(function(){
			readURL(this);
			console.log("이미지 바뀌었낭?");
		});
	});
// 사진 미리보기 처리	
function readURL(input){
	console.log("버튼클릭함1");
       if (input.files && input.files[0]) {
       var reader = new FileReader();
       reader.onload = function (e) {
           $('#cover').attr('src', e.target.result);    //cover src로 붙여지고
           $('#fileName').val(input.files[0].name);    //파일선택 form으로 파일명이 들어온다
      	}
    	reader.readAsDataURL(input.files[0]);
  	 	}
}	
// 현재 시간 받아서 time에 입력
function getTime(){
	var date = new Date(); 
	var currentTime =
	leadingZeros(date.getHours(), 2) + ':' +
    leadingZeros(date.getMinutes(), 2) + ':' +
    leadingZeros(date.getSeconds(), 2);

	$("#time").val(currentTime);
	console.log(currentTime);
}

// 시간 포맷팅해주는 함수
function leadingZeros(n, digits) {
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}

</script>

<!-- Bootstrap core JavaScript-->
<script src="/moamore/vendor/jquery/jquery.min.js"></script>
<script src="/moamore/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="/moamore/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="/moamore/js/sb-admin-2.min.js"></script>
</html>