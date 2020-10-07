<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 예산</title>
</head>
<body>
	예산 기간 :  ~  (일) <br/>
	총 예산 :  원<br/>
	<br/>
	오늘의 예산<br/>
	<div>
	
	</div>
	
	예산에서 남은 돈<br/>
	<div>
		총액 : <span id="totalLeftMoney"></span>원
	</div>
	<div>
		<c:forEach items="${leftMoney}" var="i" >
			<div>
				${i.category_no } : ${i.amount }원
			</div>
		</c:forEach>
	</div>
	<div>
		<button>전환하기</button>
	</div>
	
	
	
</body>
</html>