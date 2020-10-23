<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<title>결산 게시글</title>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<style type="text/css">
		table, th, td {
		    border: 1px solid black;
		    padding: 5px;
		}
		
		table {
		    border-collapse: collapse;
		}
	</style>
</head>
<script>
$(document).ready(function(){
	var budget_no = ${dto.budget_no};
	$.ajax({
		url:"/moamore/report/reportContent.moa",
		data:{
			"id":"${dto.id}",
			"budget_no": budget_no
		},
		success:function(data){
			$("#reportContent").empty();
			$("#reportContent").append(data);
		}
	});
});





	function check(){
		var inputs = document.inputForm;
		
		if(!inputs.content.value){	//false일때, 값이 없을때
			alert("댓글 내용을 입력하세요.");
			return false;
		}
	}
</script>
<jsp:include page="../sidebar.jsp"/>
<div class="container-fluid">
 <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
      <h1 class="h3 mb-0 text-gray-800">결산 게시판 <button type="button" onclick="location.href='/moamore/closing/closingAccountList.moa'" class="btn btn-secondary">돌아가기</button></h1>
     </div>
         
         
       <div class="row">
		<div class="col-lg-12">

            <div class="card shadow mb-4">
              <div class="card-body">
              
					<div class="table-responsive">
						<div class="col-lg-12 dataTables_wrapper dt-bootstrap4" >
							<div class="row">
								<table class="table table-bordered">
									<tr>
										<td style="width:100px">제목</td>
										<td colspan="3">${dto.subject}</td>
									</tr>
									<tr>
										<td style="width:100px">작성자</td>
										<td>${dto.id}</td>
										<td style="width:100px">작성일</td>
										<td>${fn:substring(dto.write_day,0,19)}</td>
									</tr>
									<tr>
										<td colspan="4">
											<div id="reportContent">
											</div>
										</td>
									</tr>
									<tr>
										<td>내용</td>
										<td colspan="3">${dto.content}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 게시글 끝 -->
	
	
	<div class="row">
		<div class="col-lg-12">

            <div class="card shadow mb-4">
            
	            <div class="card-header py-3 justify-content-between d-sm-flex align-items-center ">
	              <h6 class="m-0 font-weight-bold text-primary" style="display:inline-block">댓글</h6>	
	            </div>
            
              <div class="card-body">
              
					<div class="table-responsive">
						<div class="col-lg-12 dataTables_wrapper dt-bootstrap4" >
							<div class="row">
								<table class="table table-bordered">
									<c:if test="${articleList == null }">
										<tr>
											<td style="text-align:center">댓글이 없습니다</td>
										</tr>
									</c:if>
									<c:forEach var="article" items="${articleList}">
										<tr>
											<td>${article.id}</td>
											<td>${article.content}</td>
											<td>${fn:substring(article.write_day,0,19)}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
								
		<%-- 댓글 페이지 번호 뷰어 설정 --%>
		<div class="row">
			<div class="col-sm-12 col-md-12"  style="justify-content:center">
				<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate" style="justify-content:center">
					<c:if test="${count > 0}">
						<ul class="pagination" style="justify-content:center">	
							<fmt:parseNumber var="res" value="${count / pageSize}" integerOnly="true" />
							<c:set var="pageCount" value="${res + (count % pageSize == 0 ? 0 : 1)}" />
							<c:set var="pageBlock" value="10" />
							<fmt:parseNumber var="result" value="${(currPage-1)/pageBlock}" integerOnly="true" />
							<fmt:parseNumber var="startPage" value="${result * pageBlock + 1}" />
							<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}" />
							<c:if test="${endPage > pageCount}">
								<c:set var="endPage" value="${pageCount}" />
							</c:if>
				
							<c:if test="${startPage > pageBlock}">
								<li class="paginate_button page-item">
									<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${startPage-pageBlock}"> &lt; </a>
								</li>
							</c:if>
							<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
								<li class="paginate_button page-item<c:if test='${i == currPage }'> active</c:if>">
									<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
								</li>
							</c:forEach>
							<c:if test="${endPage < pageCount}">
								<li class="paginate_button page-item">
									<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${startPage+pageBlock}"> &gt; </a>
								</li>
							</c:if>
						</ul>
					</c:if>
				</div>
			</div>
		</div>
		
		<!-- 댓글 페이지뷰어 종료 -->
			
			
					<!-- 댓글 작성 폼 -->
					<c:if test="${sessionScope.memId != null}">
					<div class="row">
						<div class="col-sm-12 col-md-12"  style="justify-content:center">
							<div style="margin-top: 20px;">
								<form action="/moamore/closing/closingAccountDetailPro.moa" method="post" name="inputForm" onsubmit="return check()">
									<input type="hidden" name="article_no" value="${dto.article_no}"/>
									<!-- <input type="text" name="content" placeholder="댓글 내용을 작성해주세요."/> -->
									<div class="row">
										<div class="col-lg-11">
											<textarea style="width:100%; min-height:100px; height:auto;" name="content" placeholder="댓글 내용을 작성해주세요."></textarea>
										</div>
										<div class="col-lg-1">
											<input type="submit" value="등록" class="btn btn-primary" style="width:100%; height:100px;"/>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
</div>
<jsp:include page="../footer.jsp"/>