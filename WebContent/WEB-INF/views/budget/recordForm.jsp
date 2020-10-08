<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수입지출내역 추가</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<style>
	ul{
		list-style:none;
		padding-left:5px;
	}
</style>
<script>
	$(function(){
		$("#date").datepicker({
			dateFormat:"yy-mm-dd"
		});
	});
	
	// 체크박스 상태 확인해서 분기처리 체크된 상태면 예산외 아니면 예산 내
	$(document).ready(function(){
		$("#incomecategory").css("display", "none"); // 수입 지출 카테고리 숨기기
		$("#outcomecategory").css("display", "none"); 
		$(".btn").hide();
		$("#checkbox").change(function(){
			if($("#checkbox").is(":checked")){
				alert("예산 외 선택!!");
				$(".btn").show();		
			}else{
				alert("예산 내 선택!!!!");
				$(".btn").hide();	
				
			}
		});	
		
		$("#date").on('change',function(){
			if($("#checkbox").is(":checked")){
				// 작동안하게 막ㅇ아버리고
				//alert("작동 ㄴㄴ");
			}else{
				$("#type").val("budgetOutcome");
				console.log($("#type").val());
				//alert("작동 ㅇㅇ");
				// 작동하게 하기
				// 예산 카테고리 가져오기위해 컨트롤러로 값 보내기 ajax
				var datas;
				$(document).ready(function(){	
					
					$.ajax({
						type : "POST",
						url : "budgetCategory.moa",
						data : {date:$("#date").val()},
						dataType : "json",
						async: false,
						error : function(error){
							console.log("에러!!");
						},
						success : function(data){							
							//console.log(data);

							// 기간에 해당하는 예산의 카테고리로 셀렉트 옵션 새로 바꿔주기
							$("#category").find("option").remove(); // 기존 카테고리 셀렉트 옵션 삭제

							for(var key in data){
								//console.log("컬럼:" + key + "value : " + data[key]);
								//console.log(typeof key);
								if(key != 'budgetNum'){ 
									$("#category").append("<option value='"+data[key]+"'>"+data[key]+"</option>");
								}								
							}
						}
					});
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
			
		});
		$("#income").click(function(){ // 수입 카테고리로 세팅		
			alert("수입");
			$("#incomecategory").css("display", "block");
			$("#outcomecategory").css("display", "none"); 
			$("#type").val("income");
			console.log($("#type").val());
		});
	});

	
</script>
</head>

<body>
<form action="/moamore/record/recordPro.moa" method="post" enctype="multipart/form-data">
<%-- 데이터 넘어갈 때 수입인지 지출인지 예산내 지출인지도 보내줘야한다. --%>
<input type="hidden" id="type" name="type" value="type"/>
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
				<li class="category-section">
					<div class="input-area">
						<select id="outcomecategory" name="outcomecategory">
						<c:forEach var="outcomeCategories" items= "${outcomeCategories}" >
							<option value="${outcomeCategories.category_no}">${outcomeCategories.category_name}</option>
						</c:forEach>
						</select>
					</div>
					<div class="input-area">
						<select id="incomecategory" name="incomecategory">
						<c:forEach var="incomeCategories" items= "${incomeCategories}" >
							<option value="${incomeCategories.category_no}">${incomeCategories.category_name}</option>
						</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div>
						<input type="text" placeholder="내역을 입력하세요" id="subject" name="subject" />
					</div>
				</li>
				<li>
					<div>
						<input type="text" placeholder="금액을 입력하세요" id="money" name="money" />
					</div>
				</li>
				<!-- 날짜 -->
				<li>
					<div>
						<input type="text" id="date" name="date" />
					</div>
				<!-- 시간 -->			
					<div>
						<input type="time" id="time" name="time" />
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
			<input type="button" value="취소" />
			<input type="submit" value="확인"/>
		</div>
	</div>
</form>
</body>
</html>