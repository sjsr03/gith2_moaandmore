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
	<div>
		<button onclick="location.href='/moamore/closing/closingAccountForm'">등록</button>
	</div>
	<div style="margin-top: 20px;">
		<table>
			<tr>
				<td>제목</td>
				<td>작성자</td>
				<td>날짜</td>
			</tr>
			<c:forEach var="article" items="${articleList}">
				<tr onclick="location='/moamore/closing/closingAccountDetail.moa?article_no=${article.article_no}'">
					<td>${article.subject}</td>
					<td>${article.id}</td>
					<td>${fn:substring(article.write_day,0,10)}</td>
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
				<a href="/moamore/closing/closingAccountList.moa?pageNum=${startPage-pageBlock}"> &lt; </a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<a href="/moamore/closing/closingAccountList.moa?pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="/moamore/closing/closingAccountList.moa?pageNum=${startPage+pageBlock}"> &gt; </a>
			</c:if>
		</c:if>
	</div>
</div>
<jsp:include page="../footer.jsp"/>