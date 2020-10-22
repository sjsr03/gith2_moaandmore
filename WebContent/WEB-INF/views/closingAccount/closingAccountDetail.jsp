<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<title>결산 게시글</title>
	<style type="text/css">
		table, th, td {
		    border: 1px solid black;
		    padding: 5px;
		}
		
		table {
		    border-collapse: collapse;
		}
	</style>
	<script>
		function check(){
			var inputs = document.inputForm;
			
			if(!inputs.content.value){	//false일때, 값이 없을때
				alert("댓글 내용을 입력하세요.");
				return false;
			}
		}
	</script>
</head>
<jsp:include page="../sidebar.jsp"/>
<div class="container-fluid">
	<div align="center">
		<table>
			<tr>
				<td>제목</td>
				<td>${dto.subject}</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${dto.id}</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${fn:substring(dto.write_day,0,19)}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${dto.content}</td>
			</tr>
		</table>
	</div>
	<div style="margin-top: 40px;" align="center">
		<table>
			<c:forEach var="article" items="${articleList}">
				<tr>
					<td>${article.id}</td>
					<td>${article.content}</td>
					<td>${fn:substring(article.write_day,0,19)}</td>
				</tr>
			</c:forEach>
		</table>
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
					<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${startPage-pageBlock}"> &lt; </a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
				</c:forEach>
				<c:if test="${endPage < pageCount}">
					<a href="/moamore/closing/closingAccountDetail.moa?article_no=${dto.article_no}&pageNum=${startPage+pageBlock}"> &gt; </a>
				</c:if>
			</c:if>
		</div>
		<c:if test="${sessionScope.memId != null}">
			<div style="margin-top: 20px;">
				<form action="/moamore/closing/closingAccountDetailPro.moa" method="post" name="inputForm" onsubmit="return check()">
					<input type="hidden" name="article_no" value="${dto.article_no}"/>
					<input type="text" name="content" placeholder="댓글 내용을 작성해주세요."/>
					<input type="submit" value="등록"/>
				</form>
			</div>
		</c:if>
	</div>
	<div align="right" style="margin-top: 10px;">
		<button type="button" onclick="location.href='/moamore/closing/closingAccountList.moa'">돌아가기</button>
	</div>
</div>
<jsp:include page="../footer.jsp"/>
