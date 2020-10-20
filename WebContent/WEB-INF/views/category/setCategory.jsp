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
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
</head>
<style>

a{text-decoration: none;color: #737271;}
    i{font-size: 20px;}
    .btn{position: relative;margin: 200px 0 0 200px;filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));}
    .btn .my_sub p a{
        display: block;
        padding: 3px 0px;
       
    }
    .my_sub{
        position: absolute;
        top: -90px;
        left: -25px;
        background: #EDEBE8;
        width: 80px;
        text-align: center;
        border-radius: 8px;    /*서브 메뉴에 대한 스타일 값 다 적용 후*/
        display: none;      /*화면에 보이지 않게 하기 위해 display:none;을 사용.*/
    }
    .btn .my_sub.on{display: block;}    /*클릭시 추가해줄 on 클래스 미리 만들어둠.*/


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
<form id="input" name="input">
<table>
	<tr>
		<td>카테고리 추가</td>
	</tr>
	<tr>
		<td>
			<select name="categoryOption" id="categoryOption">
				<option value="수입">수입</option>
				<option value="지출">지출</option>
			</select>
		</td>
		<td><input type="text" name="category_name" id="category_name" placeholder="카테고리명을 입력하세요"/> </td>
		<td><input type="submit" value="추가" id="inputCategory"/></td>
	</tr>
</table>
</form>


<h3>[지출]</h3>
<form class='allExpense'>
</form>

<<<<<<< HEAD
=======

<div class='modal'>
	<div class='modal-content'>
		<span class="close-button">&times;</span>
		<h1 class="title">카테고리 수정하기</h1>
		<form class="modifyContent">
																			
		</form>
	</div>
</div>	


>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore


<h3>[수입]</h3>
<form class='allIncome'>
</form>

<<<<<<< HEAD

<div class='modal'>
	<div class='modal-content'>
		<span class="close-button">&times;</span>
		<h1 class="title">카테고리 수정하기</h1>
		<form class="modifyContent">
																			
		</form>
	</div>
</div>	
=======
<table border="1">
	<tr>
		<c:forEach var="income" items="${income}" varStatus="status">
			<c:if test="${status.index%3 ==0}">
				</tr><tr>
			</c:if>
			<td style="width:100px; height:100px">
			<button class="intrigger intrigger${status.count} btn"><i class="fas fa-ellipsis-v"></i></button>
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
>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore

<script type="text/javascript">	

<<<<<<< HEAD
var inOrOut = '';
=======
$(document).ready(function(){
	//페이지 시작할때 카테고리 목록 불러오기
	 getExpenseCategory();
	
	
	//카테고리 추가 하기 
	$("#inputCategory").click(function(){ 
		$.ajax({
			type : "POST",
			url : "setCategoryPro.moa",
			data : $("#input").serialize(),
			dataType : "json",
			error : function(error){
				console.log("에러!!");
			},
			success : function(data){							
				 getExpenseCategory();
			}
		
		});
  	});    
	
	//수정하기, 삭제하기 탭 보여주기
	/*
	 $('.btn i').click(function(){  
		console.log(1);
        $(this).click(function() {
        	if($(this).children('my_sub').hasClass('on')){
        		$(this).find('.my_sub').removeClass('on');
        	}else{
        		$(this).find('div').addClass('on');
        	}
           // $(this).closest(".my_sub").modal('hide');
            //$(ele).closest('.modal-backdrop').remove()		
           
        	//url = $(this).attr('class') + "?idx=" + $(this).attr('idx');
			//console.log(url);
        });
    });
	*/
});

function getExpenseCategory(){
	//지출 카테고리 불러오기
    $.ajax({
        type:'GET',
        url : "getExpenseCategoryList.moa",
        dataType : "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
        error:function(request,status,error){
            
        },
        success : function(outcome){
           	var html = "";
            	
       		html += "<table border='1'>";
       		html += "<tr>";
      		for (var i = 0; i < outcome.length; i++) {
      				if(i%3==0){
      					html += "</tr>";
      					html +="<tr>";		
      				}
      				console.log();
	            	html += "<td>";
	            	html += "<div class='btn'>";
	            	html += "<i class='fas fa-ellipsis-v'></i>";
	            	html += "<div class='my_sub'>";
	            	html +="<p>"
	                html +="<a href='deleteCategory.moa?category_no="+outcome[i].category_no+"&inorout=outcome'>삭제하기</a>";
	                html += "<a class='modify'>수정하기</a>";
	           		html += "</p>"; 
	            	html += "</div>";
	            	html += "</div>";
	            	html += "<div>";
	            	html += outcome[i].category_name;
	            	html += "</div>";
	            	html += "</td>";
           		}
      		html +="</tr>";
            html += "</table>";
            $(".allExpense").html(html);
         	
            updateAndDelete(); //수정하기 삭제하기 탭 보여주기
            
            
        }
        
        
    });
}
//한칸 올라갔다가 형제
//수정하기,삭제하기 창 띄워주기
function updateAndDelete(){
	$('.btn i').click(function(){
		$(this).next().closest('.my_sub').addClass('on');
		
		/*
		if($(this).next().closest('.my_sub') ){
			console.log("yes");
	   	 $('.my_sub').removeClass('on'); 
		}else{           
			console.log("no");
			$('.my_sub').addClass('on');       
		}
		*/
	
		//수정하기 탭 누르면 모달창 띄어주기
		cateogryModify();
	});
    
}

function cateogryModify(){
	
	$('.modify').click(function(){
		$('.modal').addClass("show-modal");
		$('.modifyContent').append("<textarea name='newName' placeholder='카테고리 이름을 입력해주세요'></textarea>");
		$('.modifyContent').append("<button>변경</button");								
		$('.modifyContent').append("<button>취소<button>");
	});
}





/*
$(function(){
    $('.btn i').click(function(){           아이콘 클릭시,
        if($('.my_sub').hasClass('on')){    만약 서브메뉴에 on 클래스가 있다면  (두번클릭했을때를 위한 if~else절 사용.)
            $('.my_sub').removeClass('on'); on클래스를 없애주고
        }else{                              서브메뉴에 on클래스가 없다면
        $('.my_sub').addClass('on');        //n클래스를 추가해준다.
        }
    });
});
*/



>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore

$(document).ready(function(){
<<<<<<< HEAD
	//페이지 시작할때 카테고리 목록 불러오기
	 getExpenseCategory();
	 getIncomeCategory();
	
	
	//카테고리 추가 하기 
	$("#inputCategory").click(function(){ 
		$.ajax({
			type : "POST",
			url : "setCategoryPro.moa",
			data : $("#input").serialize(),
			dataType : "json",
			error : function(error){
				console.log("에러!!");
			},
			success : function(data){							
				 getExpenseCategory();
				 getIncomeCategory();
			}
		
		});
  	});    
	
	
});

function getExpenseCategory(){
	//지출 카테고리 불러오기
    $.ajax({
        type:'GET',
        url : "getExpenseCategoryList.moa",
        dataType : "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
        error:function(request,status,error){
            
        },
        success : function(outcome){
           	var html = "";
         
       		html += "<table border='1'>";
       		html += "<tr>";
      		for (var i = 0; i < outcome.length; i++) {
      				if(i%3==0){
      					html += "</tr>";
      					html +="<tr>";		
      				}

	            	html += "<td>";
	            	html += "<div class='btn'>";
	            	html += "<i class='fas fa-ellipsis-v'></i>";
	            	html += "<div class='my_sub'>";
	            	html +="<p>"
	                html +="<a href='deleteCategory.moa?category_no="+outcome[i].category_no+"&inorout=outcome'>삭제하기</a>";
	                html += "<a class='modify'>수정하기</a>";
	           		html += "</p>"; 
	            	html += "</div>";
	            	html += "</div>";
	            	html += "<div>";
	            	html += outcome[i].category_name;
	            	html += "</div>";
	            	html += "<input type='hidden' name='category_no' id='category_no' class='category_no' value='"+outcome[i].category_no+"' />";
	            	html += "<input type='hidden' name='inOrOut' id='inOrOut' class='inOrOut' value='outcome' />";
	            	html += "</td>";
           		}
      		html +="</tr>";
            html += "</table>";
            $(".allExpense").html(html);
            console.log("여기는 한번 맞는디");
            updateAndDelete(); //수정하기 삭제하기 탭 보여주기
            
            
        }
        
        
    });
}

function getIncomeCategory(){
	//지출 카테고리 불러오기
    $.ajax({
        type:'GET',
        url : "getIncomeCategoryList.moa",
        dataType : "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
        error:function(request,status,error){
            
        },
        success : function(income){
           	var html = "";
            var category_no = '';
            
       		html += "<table border='1'>";
       		html += "<tr>";
      		for (var i = 0; i < income.length; i++) {
      				if(i%3==0){
      					html += "</tr>";
      					html +="<tr>";		
      				}

	            	html += "<td>";
	            	html += "<div class='btn'>";
	            	html += "<i class='fas fa-ellipsis-v'></i>";
	            	html += "<div class='my_sub'>";
	            	html +="<p>"
	                html +="<a href='deleteCategory.moa?category_no="+income[i].category_no+"&inorout=income'>삭제하기</a>";
	                html += "<a class='modify'>수정하기</a>";
	           		html += "</p>"; 
	            	html += "</div>";
	            	html += "</div>";
	            	html += "<div>";
	            	html += income[i].category_name;
	            	html += "</div>";
	            	html += "<input type='hidden' name='category_no' id='category_no' class='category_no' value='"+income[i].category_no+"' />";
	            	html += "<input type='hidden' name='inOrOut' id='inOrOut' class='inOrOut' value='income' />";
	            	html += "</td>";
           		}
      		html +="</tr>";
            html += "</table>";
            $(".allIncome").html(html);
           
            console.log("왜두번");
            updateAndDelete(); //수정하기 삭제하기 탭 보여주기
            
            
        }
        
        
    });
}

//수정하기,삭제하기 창 띄워주기
function updateAndDelete(){
	$('.btn i').click(function(){
		
		$(this).next().closest('.my_sub').addClass('on');
		
		var category_no = $(this).parent().next().next().val();
		inOrOut = $(this).parent().next().next().next().val();
		console.log("aaa",inOrOut);
		
		//수정하기 탭 누르면 모달창 띄어주기
		cateogryModify(category_no);
=======
	
	$(".trigger").on('click',function(){
		foundClass(event);
>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore
	});
    
}

//수정하기 모달창 보여주기
function cateogryModify(category_no){
	$('.modify').click(function(){
		$('.modal').addClass("show-modal");
		$('.modifyContent').append("<textarea name='newName' class='newName' placeholder='카테고리 이름을 입력해주세요'></textarea>");
		$('.modifyContent').append("<button class='modifyCategory'>변경</button>");								
		$('.modifyContent').append("<button>취소</button>");
		modifyAction(category_no);
	});
	
}

//지출 카테고리 수정하기
function expenseModifyAction(category_no){
	$('.modifyCategory').click(function(event){
		var newName = $('.newName').val();
		console.log(newName);
		//$(location).attr('href','/moamore/category/updateCategory.moa');
		window.location.href="/moamore/category/updateCategory.moa?newName="+newName+"&category_no="+category_no;
		event.preventDefault();
		console.log("페이지 이동");
		getExpenseCategory();
	});
}
//수입 카테고리 수정하기
function expenseModifyAction(category_no){
	$('.modifyCategory').click(function(event){
		var newName = $('.newName').val();
		console.log(newName);
		//$(location).attr('href','/moamore/category/updateCategory.moa');
		window.location.href="/moamore/category/updateCategory.moa?newName="+newName+"&category_no="+category_no;
		event.preventDefault();
		console.log("페이지 이동");
		getExpenseCategory();
	});
}




$(document).ready(function(){
	
	$(".close-button").on('click',function(){
		$(this).parent().parent().toggleClass('show-modal');
		$(".modifyContent").empty();
		
	});
	$(".cancel").on('click',function(event){
		event.preventDefault();
		
		$(this).parent().parent().toggleClass('show-modal');
		$(".modifyContent").empty();
	});
});



<<<<<<< HEAD

=======
//버튼클릭하면 targetclass 찾아서 해당 modal클래스에 show_modal 추가해주기
/*
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
*/
/*
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
*/
>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore
</script>

</body>
</html>