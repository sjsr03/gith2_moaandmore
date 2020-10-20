<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>set category</title>
<script src="https://kit.fontawesome.com/959593ce4b.js" crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

</head>

<style>


a{text-decoration: none;color: #737271;}
    i{font-size: 20px;}
    .cat_btn{position: relative;margin: 200px 0 0 200px;filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));}
    .cat_btn .my_sub p a{
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
    .cat_btn .my_sub.on{display: block;}    /*클릭시 추가해줄 on 클래스 미리 만들어둠.*/


.categorymodal { 
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
     

.categorymodal-content { 
         position: absolute; 
         top: 50%; 
         left: 50%; 
         transform: translate(-50%, -50%); 
         background-color: yellow; 
         padding: 1rem 1.5rem; 
         width: 500px; 
         height: 350px; 
         border-radius: 0.5rem; 
         z-index:5;
     }      
 .show-categorymodal { 
         opacity: 1; 
         visibility: visible; 
         transform: scale(1.0); 
         transition: visibility 0s linear 0s, opacity 0.25s 0s, transform 0.25s; 
   		 
     }  

</style>

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
	


<!-- 본문내용 시작 -->
<div class="container-fluid">

 		<!-- 페이지 이름 -->
         <div class="d-sm-flex align-items-center justify-content-between mb-4">
           <h1 class="h3 mb-0 text-gray-800">카테고리 설정</h1>
           </div>	
	
	
	<!-- 첫번째 줄 -->
    <div class="row">
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
	</div>
	
	<h3>[지출]</h3>
	<form class='allExpense'>
	</form>
	
	
	
	<h3>[수입]</h3>
	<form class='allIncome'>
	</form>
	
	
	<div class='categorymodal'>
		<div class='categorymodal-content'>
			<span class="close-button">&times;</span>
			<h1 class="title">카테고리 수정하기</h1>
			<form class="modifyContent">															
			</form>
		</div>
	</div>	


 </div> 
<script type="text/javascript">	

var inOrOut = '';

$(document).ready(function(){
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
	            	html += "<div class='cat_btn'>";
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
            
          	//점 세개 누르면 수정하기 삭제하기 탭 보여주기
            	updateAndDelete(); 
          
            
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
	            	html += "<div class='cat_btn'>";
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
           
            //점 세개 누르면 수정하기 삭제하기 탭 보여주기
            	updateAndDelete(); 
            	
          
           
           
            
        }
        
        
    });
}



//수정하기,삭제하기 창 띄워주기
function updateAndDelete(){
	
		$('.cat_btn i').click(function(){
			
			$(this).next().closest('.my_sub').addClass('on');
			
			var category_no = $(this).parent().next().next().val();
			
			//수정하기 탭 누르면 모달창 띄어주기
			console.log(category_no);
			cateogryModify(category_no);
		});
	
	
	
    
}

//수정하기 모달창 보여주기
function cateogryModify(category_no){
	
	$('.modify').click(function(){
		console.log(1);
		$('.categorymodal').addClass("show-modal");
		$('.modifyContent').append("<textarea name='newName' class='newName' placeholder='카테고리 이름을 입력해주세요'></textarea>");
		$('.modifyContent').append("<button class='modifyCategory'>변경</button>");								
		$('.modifyContent').append("<button>취소</button>");
		modifyAction(category_no);
	});
	
}

//지출 카테고리 수정하기
function modifyAction(category_no){
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




</script>

</html>