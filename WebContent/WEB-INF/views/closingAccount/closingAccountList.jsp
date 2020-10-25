<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<title>결산 게시판</title>
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
<jsp:include page="../sidebar.jsp"/>
<div class="container-fluid">

		<!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">결산 게시판</h1>
            </div>

		<!-- 첫번째 줄 -->
           	<div class="row">
				<div class="col-lg-12">

	              <div class="card shadow mb-4">
	                <div class="card-body">


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
										<tr role="row">
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