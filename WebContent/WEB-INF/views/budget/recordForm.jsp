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
</style>
<script>
	// 체크박스 상태 확인(체크된 상태면 nobudget 아니면 budget)
	$(document).ready(function(){
		// input 태그 중 date 에도 기본값으로 오늘 날짜 넣어주기 
		$("#date").val("${today}");
		
		var budget_no = 0;
		// 처음엔 수입 지출 카테고리/수입지출 버튼 숨기기 (예산지출이 기본)
		$("#incomecategory").css("display", "none"); 
		$("#outcomecategory").css("display", "none"); 
		$(".btn").hide();
		
		$("#checkbox").change(function(){
			if($("#checkbox").is(":checked")){
				// 예산 관련된거 없어져야함
				alert("예산 외 선택!!");
				$(".btn").show();	
				$("#category").css("display", "none");
			}else{
				alert("예산 내 선택!!!!");
				$(".btn").hide();	
				$("#category").css("display", "block");
				$("#outcomecategory").css("display", "none"); 
				$("#incomecategory").css("display", "none"); 
			}
		});	
		
		$("#date").on('change',function(){
			if($("#checkbox").is(":checked")){
			}else{
				// 예산일 때만 달력 체크하면 값 가져오게 처리
				$("#type").val("budgetOutcome");
				console.log($("#type").val());

				// 예산 카테고리 가져오기위해 컨트롤러로 값 보내기 ajax				
				$(document).ready(function(){
					var start = new Date("${budgetDate.get(1)}");
					var end = new Date("${budgetDate.get(0)}");		
					console.log("시작:::" + start);
					console.log("R끝:::: " + end);
					var selectedDay = new Date($("#date").val());
					
					if((start <=selectedDay) && (end >= selectedDay)){
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
		$("#outcome").click(function(){ // 지출 카테고리로 세팅 
			alert("지출");
			$("#outcomecategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#type").val("outcome");
			console.log($("#type").val());
			console.log("셀렉트 박스 옵션값은 ???? ");
			console.log($("#outcomecategory option:selected").val())

			// 카테고리 선택하면 hidden으로 값넘겨주기
			$("#outcomecategory").change(function(){
				console.log($(this).val())		
				$("#category_no").val($("#outcomecategory option:selected").val());
			});
			
		});
		$("#income").click(function(){ // 수입 카테고리로 세팅		
			alert("수입");
			$("#incomecategory").css("display", "block");
			$("#outcomecategory").css("display", "none"); 
			$("#type").val("income");
			console.log($("#type").val());
			console.log("셀렉트 박스 옵션값은 ???? ");
			$("#incomecategory").change(function(){
				console.log($(this).val())
				$("#category_no").val($("#incomecategory option:selected").val());
				$("#type").val("income");
				
			});
		});
		$("#check").click(function(){
			
			
			console.log("버겟 넘~~~ : " + budget_no);
			// budgetNum도 hidden으로 보내주기
			if(budget_no != 0){
				var intBudget_no = Number(budget_no);
				//console.log(typeof intBudget_no);
				$("#budget_no").val(intBudget_no);
			}
			
			// category_no 예산일 때 카테고리 넘버 보내주기 
			var selectedOption = $("#category option:selected").val(); 
			var numberOption = Number(selectedOption);
			$("#category_no").val(numberOption);
			console.log(typeof numberOption);
			console.log("카테고리 번호" + numberOption); 
			console.log($("#category_no").val());
			
			$("#recordForm").submit();
		});
	});
</script>
</head>

<body>
<form id="recordForm" action="/moamore/record/recordPro.moa" method="post" enctype="multipart/form-data">
<%-- 데이터 넘어갈 때 수입인지 지출인지 예산내 지출인지도 보내줘야한다. --%>
<input type="hidden" id="type" name="type" value="type"/>
<input type="hidden" id="budget_no" name="budget_no" value="${budgetNum}"/>
<input type="hidden" id="category_no" name="category_no" value="0"/> 

	<div>
		<div class="header">
			<h2> 수입 지출 내역 추가 </h2>	
			<input id="checkbox" type="checkbox" /> 예산 외 선택 </br>
			<!-- 지출 수입 버튼은 예산외 버튼 선택해야 보임 -->
			<input type="button" class="btn" value="지출" id="outcome" name="outcome"/>
			<input type="button" class="btn" value="수입" id="income" name="income" />
		</div>
		<div class="content">
			<ul>
				<li>
				<!-- 날짜 시간  -->
					<div>
						<input type="date" id="date" name="date" />
					</div>
				<!-- 시간 -->			
					<div>
						<input type="time" id="time" name="time" />
					</div>
				</li>
				<li class="category-section">
					<%-- 지출 카테고리 --%>
					<div class="input-area">
						<select id="outcomecategory" name="outcomecategory">
						<c:forEach var="outcomeCategories" items= "${outcomeCategories}" >
							<option value="${outcomeCategories.category_no}">${outcomeCategories.category_name}</option>
						</c:forEach>
						</select>
					</div>
					<%-- 수입 카테고리 --%>
					<div class="input-area">
						<select id="incomecategory" name="incomecategory">
						<c:forEach var="incomeCategories" items= "${incomeCategories}" >
							<option value="${incomeCategories.category_no}">${incomeCategories.category_name}</option>
						</c:forEach>
						</select>
					</div>
					<%-- 예산내 지출 카테고리 --%>
					<%-- categories 번호가 key 이름이 value --%>
					<div class="input-area">
						<select id="category" name="category">
						<c:forEach var="categories" items= "${categories}" >
							<option value="${categories.key}">${categories.value}</option>
						</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div>
						<input type="text" placeholder="내역을 입력하세요" id="content" name="content" />
					</div>
				</li>
				<li>
					<div>
						<input type="number" placeholder="금액을 입력하세요" id="amount" name="amount" />
					</div>
				</li>			
				<li>
					<div>
						<input type="text" placeholder="메모(최대140자)" id="memo" name="memo" maxlength="140"  />
					</div>
				</li>
				<!-- 이미지  -->
				<li>
					<div>
						<input type="file" id="image" name="image" />
					</div>
				</li>
			</ul>
			<input type="reset" value="취소" />
			<input type="button" id="check" value="확인"/>
		</div>
	</div>
</form>
</body>
</html>