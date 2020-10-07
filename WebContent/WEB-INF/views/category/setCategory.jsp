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
<style>

.modal { 
         position: fixed; 
         left: 0; 
         top: 0; 
         width: 100%; 
         height: 100%; 
         background-color: rgba(0, 0, 0, 0.5); 
         opacity: 0; 
         visibility: hidden; 
         transform: scale(1.1); 
         transition: visibility 0s linear 0.25s, opacity 0.25s 0s, transform 0.25s; 
     } 
     
.modal-content { 
         position: absolute; 
         top: 50%; 
         left: 50%; 
         transform: translate(-50%, -50%); 
         background-color: white; 
         padding: 1rem 1.5rem; 
         width: 500px; 
         height: 350px; 
         border-radius: 0.5rem; 
     }      
 .show-modal { 
         opacity: 1; 
         visibility: visible; 
         transform: scale(1.0); 
         transition: visibility 0s linear 0s, opacity 0.25s 0s, transform 0.25s; 
     }  

</style>

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
			<button class="trigger"><i class="fas fa-ellipsis-v"></i></button>
			<div>${outcome.category_name}</div>
			</td>
		
			<!-- 팝업될 레이어 -->
			<div class="modal">
				<div class="modal-content">
					<span class="close-button">&times;</span>
					<h1 class="title"> ${outcome.category_name} 카테고리 수정하기</h1>
					<form action="/moamore/category/updateCategory.moa" method="post">
						<textarea name="newName" placeholder="카테고리 이름을 입력해주세요"></textarea>
						<button  class="delete" onclick="window.location='/moamore/category/updateCategory.moa?category_no=${outcome.category_no}'">삭제</button>			
						<input type="submit" id="update" value="변경" />
						<input type="button" id="cancel" value="취소"/>
						<input type="hidden" name="category_no" value=${outcome.category_no} />
					</form>
				</div>
			</div>	
		</c:forEach>
	</tr>
</table>		




<c:forEach var="income" items="${income}" varStatus="status">
<h3>[수입]</h3>

<table border="1">
	<tr>
		
			<c:if test="${status.index%3 ==0}">
				</tr><tr>
			</c:if>
			<td style="width:100px; height:100px">
			<i class="fas fa-ellipsis-v"></i>
			<div>${income.category_name}</div>
			</td>
		
	</tr> 
</table>


</c:forEach>
<script type="text/javascript">
	
	var modal = document.querySelector(".modal");
	var trigger = document.querySelector(".trigger");
	var closeButton = document.querySelector(".close-button");
	var cancelButton = document.querySelector("#cancel");
	
	function toggleModal(){
		console.log("1");
		modal.classList.toggle("show-modal");
	}
	
	function windowOnClick(event){
		
		if(event.taget === modal){
			toggleModal();
		}
		
	}
	trigger.addEventListener("click", toggleModal); 
	closeButton.addEventListener("click",toggleModal);
	cancel.addEventListener("click",toggleModal);
	window.addEventListener("click",windowOnClick);
	
	
</script>

</body>
</html>