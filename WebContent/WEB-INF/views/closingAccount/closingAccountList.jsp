<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<title>결산 게시판</title>
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">	
	<style type="text/css">
		table, th, td {
		    border: 1px solid #EAEAEA;
		    padding: 5px;
		    text-align: center;
		}
		
		table {
		    border-collapse: collapse;
		}
	</style>
</head>
<jsp:include page="../sidebar.jsp"/>
<div class="container-fluid">

		<!-- 첫번째 줄 -->
           	<div class="row">
				<div class="col-lg-12">

	              <div class="row" style="display:flex;" align="center">
	                <div class="card-body" class="col-sm-12">


					<!-- 게시글 등록 버튼 -->
						<c:if test="${sessionScope.memId != null}">
							<div class="row" align="right">
								<div class="col-lg-12">
									<a href="/moamore/closing/closingAccountForm.moa" class="btn btn-primary btn-icon-split">
					                    <span class="icon text-white-50">
					                      <i class="fas fa-pen"></i>
					                    </span>
				                   		 <span class="text">글쓰기</span>
				                    </a>
			                    </div>
							</div>
	                    <div class="my-2"></div>
						</c:if>
					
				
				<!-- 게시판 리스트 -->	
					<div class="table-responsive">
						<div class="col-lg-12 dataTables_wrapper dt-bootstrap4" >
							<div class="row">
								<table class="table table-bordered">
									<thead>
										<tr role="row" style="background-color: #C6C6C6;">
											<td style="width:60%">제목</td>
											<td style="width:20%">작성자</td>
											<td style="width:20%">날짜</td>
										</tr>
									</thead>
									<tbody>
									<c:forEach var="article" items="${articleList}">
										<tr>
											<td onclick="location='/moamore/closing/closingAccountDetail.moa?article_no=${article.article_no}'" style="cursor:pointer;">${article.subject}</td>
											<td>${article.id}</td>
											<td>${fn:substring(article.write_day,0,19)}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						
				<div class="my-2"></div>
	<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
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
									<a href="/moamore/closing/closingAccountList.moa?pageNum=${startPage-pageBlock}"> &lt; </a>
								</li>
							</c:if>
							<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
								<li class="paginate_button page-item<c:if test='${i == currPage }'> active</c:if>">
									<a href="/moamore/closing/closingAccountList.moa?pageNum=${i}" class="pageNums page-link">${i}</a>
								</li>
							</c:forEach>
							<c:if test="${endPage < pageCount}">
								<li class="paginate_button page-item">
									<a href="/moamore/closing/closingAccountList.moa?pageNum=${startPage+pageBlock}"> &gt; </a>
								</li>
							</c:if>
							
							</ul>
						</c:if>
					</div>
				</div>
			</div>
			</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
<jsp:include page="../footer.jsp"/>