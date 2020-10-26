<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<title>나를 초대한 그룹들</title>
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
<!-- 본문내용 시작 -->
<div class="container-fluid">
	<div class="content">
		<div class="row" style="display:flex;" align="center">
			<table class="col-sm-12" style="margin-top: 40px;">
				<tr style="background-color: #C6C6C6;">
					<td style="width:20%">목표명</td>
					<td style="width:25%">목표내용</td>
					<td style="width:15%">목표금액</td>
					<td style="width:15%">시작일</td>
					<td style="width:15%">종료일</td>
					<td style="width:10%">참여자 수</td>
				</tr>
				<c:forEach var="article" items="${articleList}">
					<tr onclick="location='/moamore/team/teamDetail.moa?team_no=${article.team_no}'">
						<td>${article.subject}</td>
						<td>${article.content}</td>
						<td>${article.amount}</td>
						<td>${fn:substring(article.start_day,0,10)}</td>
						<td>${fn:substring(article.end_day,0,10)}</td>
						<td>${article.people}</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(articleList) == 0}">
					<tr>
						<td colspan="6">초대받은 내역이 없습니다.</td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>
	<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
	<div align="center" style="margin-top: 10px;">
		<c:if test="${count > 0}">
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
				<a href="/moamore/team/groupComeInviteList.moa?nickname=${sessionScope.memName}&pageNum=${startPage-pageBlock}"> &lt; </a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<a href="/moamore/team/groupComeInviteList.moa?nickname=${sessionScope.memName}&pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="/moamore/team/groupComeInviteList.moa?nickname=${sessionScope.memName}&pageNum=${startPage+pageBlock}"> &gt; </a>
			</c:if>
		</c:if>
	</div>
	<div align="center" style="margin-top: 20px;">
		<button type="button" class="btn btn-light btn-icon-split" style="border-radius:0.35em 0.35em 0.35em 0.35em; border:2px solid #ccc;" onclick="location.href='/moamore/team/groupList.moa'"><span class="text">돌아가기</span></button>
	</div>
</div>
<jsp:include page="../footer.jsp"/>