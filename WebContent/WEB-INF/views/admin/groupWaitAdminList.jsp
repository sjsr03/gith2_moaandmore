<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>그룹 신청 승인 페이지</title>
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
	<div class="middle" style="width: 100%;text-align: center;">
			<table>
				<tr>
					<td>신청자</td>
					<td>목표명</td>
					<td>목표내용</td>
					<td>목표금액</td>
					<td>시작일</td>
					<td>종료일</td>
					<td>참여자 수</td>
					<td>공개여부</td>
					<td>비밀번호</td>
					<td></td>
				</tr>
				<c:forEach var="article" items="${modelDTO.articleList}">
					<form action="/moamore/admin/groupWaitAdminPro.moa" method="post">
						<input type="hidden" name="team_no" value="${article.team_no}"/>
						<tr>
							<td>${article.leader}</td>
							<td>${article.subject}</td>
							<td>${article.content}</td>
							<td>${article.amount}</td>
							<td>${fn:substring(article.start_day,0,10)}</td>
							<td>${fn:substring(article.end_day,0,10)}</td>
							<td>${article.people}</td>
							<td>${article.isopen}</td>
							<td>${article.password}</td>
							<td>
								<select name="status">
									<option value='1'>승인</option>
									<option value='-1'>거절</option>
								</select>
								<input type="submit" value="확인"/>
							</td>
						</tr>
					</form>
				</c:forEach>
			</table>
	</div>
	<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
	<div align="center">
		<c:if test="${modelDTO.count > 0}">
			<fmt:parseNumber var="res" value="${modelDTO.count / modelDTO.pageSize}" integerOnly="true" />
			<c:set var="pageCount" value="${res + (modelDTO.count % modelDTO.pageSize == 0 ? 0 : 1)}" />
			<c:set var="pageBlock" value="10" />
			<fmt:parseNumber var="result" value="${(modelDTO.currPage-1)/pageBlock}" integerOnly="true" />
			<fmt:parseNumber var="startPage" value="${result * pageBlock + 1}" />
			<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}" />
			<c:if test="${endPage > pageCount}">
				<c:set var="endPage" value="${pageCount}" />
			</c:if>

			<c:if test="${startPage > pageBlock}">
				<a href="/moamore/admin/groupWaitAdminList.moa?pageNum=$startPage-pageBlock}"> &lt; </a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<a href="/moamore/admin/groupWaitAdminList.moa?pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="/moamore/admin/groupWaitAdminList.moa?pageNum=${startPage+pageBlock}"> &gt; </a>
			</c:if>
		</c:if>
	</div>
</body>
</html>