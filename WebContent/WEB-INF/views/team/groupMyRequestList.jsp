<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>내가 개설 신청한 그룹들</title>
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
<body>
	<div>
		<table>
			<tr>
				<td>목표명</td>
				<td>목표내용</td>
				<td>목표금액</td>
				<td>시작일</td>
				<td>종료일</td>
				<td>참여자 수</td>
				<td>공개여부</td>
				<td>비밀번호</td>
				<td>현재상태</td>
			</tr>
			<c:forEach var="article" items="${articleList}">
				<tr>
					<td>${article.subject}</td>
					<td>${article.content}</td>
					<td>${article.amount}</td>
					<td>${fn:substring(article.start_day,0,10)}</td>
					<td>${fn:substring(article.end_day,0,10)}</td>
					<td>${article.people}</td>
					<c:if test="${article.isopen == 0 }">
						<td>비공개</td>
					</c:if>
					<c:if test="${article.isopen == 1 }">
						<td>공개</td>
					</c:if>
					<td>${article.password}</td>
					<c:if test="${article.status == -1 }">
						<td>거절</td>
					</c:if>
					<c:if test="${article.status == 0 }">
						<td>대기중</td>
					</c:if>
					<c:if test="${article.status == 2 }">
						<td>승인</td>
					</c:if>
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
				<a href="/moamore/team/groupMyRequestList.moa?nickname=${sessionScope.memName}&pageNum=${startPage-pageBlock}"> &lt; </a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<a href="/moamore/team/groupMyRequestList.moa?nickname=${sessionScope.memName}&pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="/moamore/team/groupMyRequestList.moa?nickname=${sessionScope.memName}&pageNum=${startPage+pageBlock}"> &gt; </a>
			</c:if>
		</c:if>
	</div>
	<div align="center">
		<button type="button" onclick="location.href='/moamore/team/groupList.moa'">돌아가기</button>
	</div>
</body>
</html>