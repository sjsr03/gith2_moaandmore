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
		});
	});
	// 예산 외 버튼 클릭시
	$(document).ready(function(){
		$("#btn1").click(function(){
			window.location.href='/moamore/record/recordForm.moa';
		});
	});

	// 예산 내 버튼 클릭시
	$(document).ready(function(){
		$("#btn2").click(function(){
			alert("예산내 선택");
		});
	});

</script>
</head>

<body>
<form action="/moamore/record/recordPro.moa" method="post" enctype="multipart/form-data">
	<div>
		<div class="header">
			<h2> 수입 지출 내역 추가 </h2>	
			<input id="btn1" type="button" value="예산외" />
			<input id="btn2" type="button" value="예산내" /> </br>
			<input type="button" value="지출" />
			<input type="button" value="수입" />
		</div>
		<div class="content">
			<ul>
				<li class="category-section">
					<div class="input-area">
						<select id="category" name="category">
						<c:forEach var="outcomeCategories" items="${outcomeCategories}">
							<option value="${outcomeCategories.category_no}">${outcomeCategories.category_name}</option>
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