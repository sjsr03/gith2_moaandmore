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
<body>
	<%-- 회원 탈퇴 시키는 함수 --%>
	<script>
		function delete_member(id,pageNum){
			var msg = confirm(" 회원을 정말로 탈퇴시키겠습니까?");
			if(msg == true){
				window.location.href='/moamore/admin/deleteMember.moa?id='+id+'&pageNum='+pageNum;
			}else{
				alert("취소되었습니다");
			}
		}
	</script>
	<%-- 가입한 회원이 없을 때 --%>
	<c:if test="${memberList.count == 0}">
		가입한 회원이 없습니다.
	</c:if>

	<%-- 가입한 회원이 있을 때 --%>
	<c:if test="${memberList.count > 0}">
		<table>
			<tr>
				<td>사진</td>
				<td>아이디</td>
				<td>닉네임</td>
				<td>가입날짜</td>
				<td>탈퇴버튼</td>
			</tr>
			<%-- 회원 수 만큼 for문 반복 --%>
			<c:forEach var="members" items="${memberList.members}">
				<tr>
					<%-- 사진 --%>
					<td>
						${members.profile_img}
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
						${members.reg}
					</td>
					<%--탈퇴버튼 --%>
					<td>
						<input type="button" value="삭제" onclick="delete_member('${members.id}','${memberList.pageNum}')"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>	
	
	<%-- 페이지 번호 처리 --%>
	<div align="center">
		<%-- 가입한 회원이 한명이라도 있어야 보여줌--%>
		<c:if test="${memberList.count > 0}">
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
				<a href="/moamore/admin/memberList.moa?pageNum=${startPage-pageBlock}"> %lt;</a>
			</c:if>
			<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
			<a href="/moamore/admin/memberList.moa?pageNum=${i}" class="pageNums"> &nbsp; ${i} &nbsp; </a>	
			</c:forEach>
			<c:if test="${endPage<pageCount}">
				<a href="/moamore/admin/memberList.moa?pageNum=${startPage+pageBlock }"> &gt; </a>	  
			</c:if>
		</c:if>

	</div>
</body>
</html>