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
     
.inmodal { 
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


<c:if test="${already == 'true'}">
	<script>
		alert("이미 있는 이름입니다.다른이름을 사용해주세요.");
	</script>
</c:if>


<c:if test="${exist==1}">
	<script>
		alert("해당카테고리에 데이터가 있어 삭제가 불가능 합니다.");
	</script>
</c:if>
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
			<button class="trigger trigger${status.count}"><i class="fas fa-ellipsis-v"></i></button>
			<div>${outcome.category_name}</div>
			</td>
		
			<%-- 팝업될 레이어 --%>
			<div  id="modal" class="modal modal${status.count}">
				<div class="modal-content">
					<span class="close-button">&times;</span>
					<h1 class="title"> ${outcome.category_name} 카테고리 수정하기</h1>
					<form action="/moamore/category/updateCategory.moa" method="post">
						<textarea name="newName" placeholder="카테고리 이름을 입력해주세요"></textarea>								
						<input type="button" value="삭제 " onclick="window.location='/moamore/category/deleteCategory.moa?category_no=${outcome.category_no}&inorout=outcome'"/>
						<input type="submit" value="변경" />
						<input type="button" class ="cancel" id="cancel" value="취소"/>
						<input type="hidden" name="category_no" value=${outcome.category_no} />
						<input type="hidden" name="inorout" value="outcome" />
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
			<button class="intrigger intrigger${status.count}"><i class="fas fa-ellipsis-v"></i></button>
			<div>${income.category_name}</div>
			</td>
			<%-- 팝업될 레이어 --%>
			<div  id="inmodal" class="inmodal inmodal${status.count}">
				<div class="modal-content">
					<span class="close-button">&times;</span>
					<h1 class="title"> ${income.category_name} 카테고리 수정하기</h1>
					<form action="/moamore/category/updateCategory.moa" method="post">
						<textarea name="newName" placeholder="카테고리 이름을 입력해주세요"></textarea>								
						<input type="button" value="삭제 " onclick="window.location='/moamore/category/deleteCategory.moa?category_no=${income.category_no}&inorout=income'"/>
						<input type="submit" value="변경" />
						<input type="button" class ="cancel" id="cancel" value="취소"/>
						<input type="hidden" name="category_no" value=${income.category_no} />
						<input type="hidden" name="inorout" value="income" />														
					</form>
				</div>
			</div>	
		</c:forEach>
	</tr> 
</table>

<script type="text/javascript">	


$(document).ready(function(){
	//수출 카테고리 관련
	$(".trigger").on('click',function(){
		foundClass(event);
	});
	$(".intrigger").on('click',function(){
		incomefoundClass(event);
	});
	$(".close-button").on('click',function(){
		$(this).parent().parent().toggleClass('show-modal');
	});
	$(".cancel").on('click',function(){
		$(this).parent().parent().toggleClass('show-modal');
	});
});



//버튼클릭하면 targetclass 찾아서 해당 modal클래스에 show_modal 추가해주기
function foundClass(event){
	
	var modals = document.querySelectorAll("#modal");
	
	var target = event.target;
	var targetClass = $(target).attr('class');
	var targetclIdx = targetClass.substr(15,2);
	
	for(var i=0; i<modals.length; i++) {
		 var mm = modals[i];
		 var modalclass= $(mm).attr('class');
		 var modalclIdx = modalclass.substr(11,2);
		 if(targetclIdx === modalclIdx){
			 var modalcl = modalclass.substr(6,13);
			 $('.'+modalcl).addClass('show-modal');
		 }
	}
}

function incomefoundClass(event){
	
	var inmodals = document.querySelectorAll("#inmodal");
	
	var target = event.target;
	var targetClass = $(target).attr('class');
	var targetclIdx = targetClass.substr(19,2);
	
	for(var i=0; i<inmodals.length; i++) {
		 var mm = inmodals[i];
		 var inmodalclass= $(mm).attr('class');
		
		 var inmodalclIdx = inmodalclass.substr(15,2);
		
		 if(targetclIdx === inmodalclIdx){
			 console.log(inmodalclass);
			 var inmodalcl = inmodalclass.substr(8,13);
			 $('.'+inmodalcl).addClass('show-modal');
		 }
	}
}
</script>

</body>
</html>