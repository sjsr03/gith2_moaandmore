<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>set category</title>
<script src="https://kit.fontawesome.com/959593ce4b.js" crossorigin="anonymous"></script>
</head>
<body>
<br />
<h2>카테고리 설정</h2>

<form action="/moamore/category/setCategoryPro.moa" method="post">
<table>
	<tr>
		<td>카테고리 추가</td>
	</tr>
	<tr>
		<td>
			<select name="categoryOption">
				<option value="수입">수입</option>
				<option value="지출">지출</option>
			</select>
		</td>
		<td><input type="text" name="category_name" placeholder="카테고리명을 입력하세요"/> </td>
		<td><input type="submit" value="추가" /></td>
	</tr>
</table>
</form>



<h3>[지출]</h3>
<table border="1">
	<tr>
		<c:forEach var="outcome" items="${outcome}" varStatus="status">
			<c:if test="${status.index%3 == 0}">
			</tr><tr>
			</c:if>
			<td style="width:100px; height:100px">
			<button onclick="buttonClick();"><i class="fas fa-ellipsis-v"></i></button>
			<div>${outcome.category_name}</div>
			</td>
		</c:forEach>
	</tr>
</table>		




<h3>[수입]</h3>

<table border="1">
	<tr>
		<c:forEach var="income" items="${income}" varStatus="status">
			<c:if test="${status.index%3 ==0}">
				</tr><tr>
			</c:if>
			<td style="width:100px; height:100px">
			<i class="fas fa-ellipsis-v"></i>
			<div>${income.category_name}</div>
			</td>
		</c:forEach>
	</tr> 
</table>

<script>
	function buttonClick(){
		
	} 
</script>

</body>
</html>