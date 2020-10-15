<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>

</script>
<title>moneyLog</title>
</head>
<script>
	$(document).ready(function(){
		/*
		$("#modifybtn").click(function(){
			console.log("수정버튼이닷");
			var popup = window.open('팝업 주소 ','팝업창 이름',' 팝업창 설정');
		});
		*/
		$("button[name='btn_delete']").on('click',function(event){
			console.log(event);
			var id = $(this).attr("id");
			
			var number = id.replace("btn_", "");
			console.log("id >> " + id);
				alert(number)
				console.log("삭제버튼이닷");
				var check = false;
				check = confirm("정말 삭제하시겠습니까?");
				if(check){ // check가 true면
					var num = $(this).prev().prev().val();
					console.log(num);
					
					// 댓글 삭제 Ajax
					$.ajax({
						type:"POST",
						url:"budgetRecordDelete.moa",
						dateType: "json",
						data:{"budget_outcome_no":num},
						success: function(result){
							if(result=="OK"){
								// 내용날리기 안되니까 걍 새로고침 ㅎㅎ;
								location.reload();
								alert("삭제완료");
							}else{
								// 삭제 실패
								alert("삭제 실패");
							}
						}				
					});
				}else{// check가 false면 
					alert("삭제를 취소합니다.");
				}
		});	
	});
</script>
<body>
<h2 align="center"> 입출력 내역 </h2>

<div align="center">
	<button onclick="location.href='/moamore/record/recordForm.moa'">입출력 입력</button><br />
</div>
<%-- 테이블 시작 --%>
<div class="boardcss_list_table" align="center">
	<br />
	<table class="list_table">
		<caption>룰루랄라</caption>
		
		<colgroup>
			<col /> 
			<col /> 
			<col /> 
			<col /> 
			<col /> 
		</colgroup>
		<thead>
			<tr>
				<th>날짜/시간</th>
				<th>카테고리번호</th>
				<th>내역</th>
				<th>금액</th>
			</tr>
			
		</thead>
		<tbody>
			<c:forEach var="records" items="${recordPage.recordList}" varStatus="status" >
			<tr>
				<td>
					<fmt:formatDate value="${records.reg}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				${categories[records.category_no]}
				</td>
				<td>${records.content}</td>
				<td>
					<fmt:formatNumber type="number" maxFractionDigits="3"  value="${records.amount}"/>원
				</td>
				<td>
				 <%--
					<input type="hidden" value="${records.budget_outcome_no}" name="currentRecord${status.index}">
					--%>
					<input type="hidden" value="${records.budget_outcome_no}" class="budgetNum">
					<button class="btn" id="btn_modify" name="btn_modify">수정</button>
					
					<button class="btn" id="btn_delete" name="btn_delete">삭제</button>
					
				</td>
			</tr>		
			</c:forEach>
		</tbody>
	</table>
</div>
<%-- 테이블 종료 --%>
<%-- 페이지 번호 처리 --%>
<br /><br />
<div align="center">
	<%-- 가입한 회원이 한명이라도 있어야 보여줌--%>
	<c:if test="${recordPage.count > 0}">
		<fmt:parseNumber var="res" value="${recordPage.count / recordPage.pageSize}" integerOnly="true"/>
		
		<fmt:parseNumber var="pageCount" value="${res + (recordPage.count % recordPage.pageSize == 0 ? 0 : 1 )} "/>
		<c:set var="pageBlock" value="10" />
		
		<fmt:parseNumber var="result" value="${(recordPage.currPage-1)/pageBlock}" integerOnly="true"/>
		<fmt:parseNumber var="startPage" value="${result*pageBlock + 1}"/>
		<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}"/>
		<c:if test="${endPage > pageCount}">
		<c:set var="endPage" value="${pageCount}"/>
		</c:if>
		<c:if test="${startPage>pageBlock}">
			<a href="/moamore/record/moneyLog.moa?pageNum=${startPage-pageBlock}"> %lt;</a>
		</c:if>
		<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
		<a href="/moamore/record/moneyLog.moa?pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>	
		</c:forEach>
		<c:if test="${endPage<pageCount}">
			<a href="/moamore/record/moneyLog.moa?pageNum=${startPage+pageBlock }"> &gt; </a>	  
		</c:if>
	</c:if>
</div>
<%-- 수정 팝업창 날짜, 시간, 카테고리번호
<div id="modifyForm">
	<form >
		
		<input type="text" id="" value=""/>
		<input type="text" id="" value=""/>
		<input type="text" id="" value="" />
	</form>
</div>
 --%>
</body>
</html>