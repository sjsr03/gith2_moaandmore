<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberList</title>
</head>
<body id="page-top">
<jsp:include page="../sidebar.jsp"/>	

	<!-- 본문 내용 시작  -->
	<div class="container-fluid">
	
		<!-- 페이지 이름  -->
		<div class="d-sm-flex align-items-center justify-content-between mb-4">
			<h1 class="h3 mb-0 text-gray-80"> 회원 목록 </h1>
		</div>
		
		
		<!-- 첫번째 줄 -->
		<div class="row">
			<div class="col-lg-12">
				<div class="card shadow mb-4">
					<div class="card-body">
					
					
						<%-- 가입한 회원이 없을 때 --%>
										
						<c:if test="${memberList.count == 0}">
							<div class="col-lg-12">	
							가입한 회원이 없습니다.
							</div>
						<div class="my-2"></div>
						</c:if>
						
						<%-- 가입한 회원이 있을 때 --%>
						<div class="table-responsive">
							<div class="col-lg-12 dataTables_wrapper dt-bootstrap4" >
								<div class="row">
									<c:if test="${memberList.count > 0}">
										<table class="table table-bordered">
											<thead>
												<tr role="row">
													<td style="width:30%">사진</td>
													<td style="width:20%">아이디</td>
													<td style="width:20%">닉네임</td>
													<td style="width:20%">가입날짜</td>
													<td style="width:10%">탈퇴버튼</td>
												</tr>
											</thead>
											<tbody>
											<%-- 회원 수 만큼 for문 반복 --%>
											<c:forEach var="members" items="${memberList.members}">
												<tr>
													<%-- 사진 --%>
													<td>
														<img  src="../resources/img/'${members.profile_img}'">	
													</td>
													<%-- 아이디 --%>
													<td>
														${members.id}
													</td>
													<%-- 닉네임 --%>
													<td>
														${members.nickname}
													</td>
													<%-- 가입날짜 --%>
													<td>
														<fmt:formatDate value="${members.reg}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</td>
													<%--탈퇴버튼 --%>
													<td>
														<input type="button" value="삭제" onclick="delete_member('${members.id}','${memberList.pageNum}')"/>
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
									</c:if>	
								</div>
								<div class="my-2"></div>		
								
									<%-- 페이지 번호 처리 --%>
									<div class="row">
										<div class="col-sm-12 col-md-12"  style="justify-content:center">
											<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate" style="justify-content:center">	
												
												<%-- 가입한 회원이 한명이라도 있어야 보여줌--%>
												<c:if test="${memberList.count > 0}">
													<ul class="pagination" style="justify-content:center">
													<fmt:parseNumber var="res" value="${memberList.count / memberList.pageSize}" integerOnly="true"/>
													
													<fmt:parseNumber var="pageCount" value="${res + (memberList.count % memberList.pageSize == 0 ? 0 : 1 )} "/>
													<c:set var="pageBlock" value="10" />
													
													<fmt:parseNumber var="result" value="${(memberList.currPage-1)/pageBlock}" integerOnly="true"/>
													<fmt:parseNumber var="startPage" value="${result*pageBlock + 1}"/>
													<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}"/>
													<c:if test="${endPage > pageCount}">
														<c:set var="endPage" value="${pageCount}"/>
													</c:if>
													
													<c:if test="${startPage>pageBlock}">
														<li class="paginate_button page-item">
															<a href="/moamore/admin/memberList.moa?pageNum=${startPage-pageBlock}"> %lt;</a>
														</li>
													</c:if>
													<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
														<li class="paginate_button page-item">
															<a href="/moamore/admin/memberList.moa?pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>	
														</li>
													</c:forEach>
													<c:if test="${endPage<pageCount}">
														<li class="paginate_button page-item">
															<a href="/moamore/admin/memberList.moa?pageNum=${startPage+pageBlock }"> &gt; </a>	  
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

<script>
	// 회원 탈퇴 
	function delete_member(id,pageNum){
		var msg = confirm(" 회원을 정말로 탈퇴시키겠습니까?");
		if(msg == true){
			window.location.href='/moamore/admin/deleteMember.moa?id='+id+'&pageNum='+pageNum;
		}else{
			alert("취소되었습니다");
		}
	}
</script>
</html>