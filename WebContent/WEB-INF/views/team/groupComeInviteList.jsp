<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<meta charset="UTF-8">
	<title>나를 초대한 그룹들</title>
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

<!-- 본문내용 시작 -->
<div class="container-fluid">
	<div>
		<table>
			<tr>
				<td>목표명</td>
				<td>목표내용</td>
				<td>목표금액</td>
				<td>시작일</td>
				<td>종료일</td>
				<td>참여자 수</td>
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
		</table>
	</div>
	<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
	<div align="center">
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
	<div align="center">
		<button type="button" onclick="location.href='/moamore/team/groupList.moa'">돌아가기</button>
	</div>
</div>
<jsp:include page="../footer.jsp"/>