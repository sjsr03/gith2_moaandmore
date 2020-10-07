<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>set category</title>
<script src="https://kit.fontawesome.com/959593ce4b.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
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
			<button id="trigger" class="trigger trigger${status.count}"><i class="fas fa-ellipsis-v"></i></button>
			<div>${outcome.category_name}</div>
			</td>
		
			<!-- 팝업될 레이어 -->
			<div  id="modal" class=" modal modal${status.count}">
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


<script type="text/javascript">	
console.log(document.getElementByClassName("modal1"));
 
$(document).ready(function(){
	$(".trigger").on('click',function(){
		foundClass(event);
		//toggleModal(event);
	});
});



function toggleModal(){
	
	console.log(modal);
	//여기서 trigger class에 맞는 modal 클래스를 찾아야 한다
	// trigger1 들어왔으면 modal1에 추가되야 하고
	// trigger2 --> modal2
	
	modal.classList.toggle("show-modal");
}


//클릭된 button의 trigger 클래스를 찾아야 한다. 
function foundClass(event){
	
	
	//var triggers = document.querySelectorAll("#trigger");
	var modals = document.querySelectorAll("#modal");
	
	//클래스속성 찾기
	//console.log($(triggers).attr('class'));
	//console.log($(modals).attr('class'));
	
	var target = event.target;
	var targetClass = $(target).attr('class');
	var targetclIdx = targetClass.substr(15,2);
	console.log(targetclIdx);
	
	
	
	
	for(var i=0; i<modals.length; i++) {
		
		  var mm = modals[i];
		  var modalclass= $(mm).attr('class');
		// console.log(modalclass);
		
		 //이거 가지고 태그 가져오기
		
		
		
		
		 
		 	
		 var modalclIdx = modalclass.substr(12,2);
		 //console.log(targetclIdx);
		 if(targetclIdx === modalclIdx){
			 var modalcl = modalclass.substr(7,13);
			 console.log(modalcl);
			 
			 var aaaa = document.getElementByClassName(modalcl)[0];
			 console.log(aaaa);
			 
			//modalcl.classList.toggle("show-modal");
			
		 
		 }
		
		
	}
	
	
	
}



	
	
	var closeButton = document.querySelector(".close-button");
	var cancel = document.querySelector("#cancel");
	
	
	
	var modal = document.querySelector(".modal");
	var triggers = document.querySelectorAll("trigger");
	//console.log(triggers);
	var modals = document.querySelectorAll(".modal");
	//console.log(modals);
	
	//console.log(triggers.item(4));
	
	
	
	
	
	
	function windowOnClick(event){
		if(event.taget === modal){
			toggleModal();
		}
	}
	
	
	function foundclass(){
		document.querySelector(".modal");
		
	}
	

	
	

	closeButton.addEventListener("click",toggleModal);
	cancel.addEventListener("click",toggleModal);
	window.addEventListener("click",windowOnClick);
	
	/*
	for(var i = 0; i<trigger.length; i++) {
		selectall[i].addEventListener(…);
		}
	*/
	
	//$("[class*='trigger']");
</script>

</body>
</html>