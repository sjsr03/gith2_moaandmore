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

</head>

<style>

 
  @media (max-width: 800px){
      my_td{
        	width:50px !important;
        	height:50px !important;
        	
        	
        }
       .my_tab{
       	border-spacing: 10px !important;
       }
       
       .cat_text{
       		text-align:center;
       		font-size:1em !important;
       	}
    
   		
    }
    
    @media (max-width: 500px){
       .my_td{
        	width:50px !important;
        	height:50px !important;
        	
        	
        }
       .my_tab{
       	border-spacing: 10px !important;
       }
       
       .cat_text{
       		text-align:center;
       		font-size:1em !important;
       	}
    
    }
    
    @media (max-width: 370px){
        my_td{
        	width:50px !important;
        	height:50px !important;
        	
        	
        }
       .my_tab{
       	border-spacing: 10px !important;
       }
       
       .cat_text{
       		text-align:center;
       		font-size:1em !important;
       	}
    
   		
    }
  
 
a{text-decoration: none;color: #737271;}
    i{font-size: 20px;}
    .cat_btn{
    	position: relative;
    	float:right;
    	top: -20%;
    	padding:8px;
    
    		
    }
    .cat_btn .my_sub p a{
        display: block;
        padding: 3px 0px;
       
    }
    
    .cat_text{
    	text-align:center;
    	top:-20%;
    	font-size: 1em;
    }
    .my_sub{
        position: absolute;
        top: 32px;
        left: -5px;
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
         background-color: white; 
         padding: 1rem 1.5rem; 
         width: 400px; 
         height: 300px; 
         border-radius: 0.5rem; 
         z-index:5;
     }      
 .show-categorymodal { 
         opacity: 1; 
         visibility: visible; 
         transform: scale(1.0); 
         transition: visibility 0s linear 0s, opacity 0.25s 0s, transform 0.25s; 
   		 
     }  
.my_tab{
	border-collapse:separate;
	border-spacing:26px;
	
}
.my_td{
	width:250px;
	height:100px;
	border-radius: 10px;
	
}
.modify{
	cursor:pointer;
}
button{
	/* display: inline-block; */
    padding: .5em .75em;
    color: #333;
    font-size: inherit;
    /* line-height: normal; */
    /* vertical-align: middle; */
    /* background-color: #fdfdfd; */
    cursor: pointer;
    border: 1px solid #ebebeb;
    border-bottom-color: #e2e2e2;
    border-radius: .25em;
    text-align: center;
	
}
.close-button{
	float:right;
	cursor:pointer;
}
.btn-gradient {
	margin: 5px;
}
.buttonDiv{
	float:right;
	padding:5px;
	
}
</style>
<c:if test="${already==1}">
	<script>
		alert("이미 있는 이름입니다.다른이름을 사용해주세요.");
	</script>
</c:if>


<c:if test="${exist==1}">
	<script>
		alert("해당카테고리에 데이터가 있어 삭제가 불가능 합니다.");
	</script>
</c:if>
	
<jsp:include page="../sidebar.jsp"/>

<!-- 본문내용 시작 -->
<div class="container-fluid">


 		<!-- 페이지 이름 -->
         <div class="d-sm-flex align-items-center justify-content-between mb-4">
           <h1 class="h3 mb-0 text-gray-800">카테고리 설정</h1>
           </div>	
	
	
	<!-- 첫번째 줄 -->
    <div class="row">
    	<div class="card mb-4">
			<form id="inputCateogry" name="input">
				<table>
					<tr>
						<td>카테고리 추가</td>
					</tr>
					<tr>
						<td>
							<select name="categoryOption" id="categoryOption">
								<option value="지출">지출</option>
								<option value="수입">수입</option>
							</select>
						</td>
						<td><input type="text" name="category_name" id="category_name" placeholder="카테고리명을 입력하세요"/> </td>
						<td><input type="submit" value="추가" id="inputCategory"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	 <div class="card-body">
		<h4 class="mb-0 text-gray-800">지출 카테고리</h4>
		<form class='allExpense'>
		</form>
	</div>
	
	 <div class="card-body">
		<h4>수입 카테고리</h4>
		<form class='allIncome'>
		</form>
	</div>
	
	<div class='categorymodal'>
		<div class='categorymodal-content'>
			<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
				<h6 class="title m-0 font-weight-bold text-primary">modify category</h6>
				<span class="close-button">&times;</span>
			</div>
			<div class="modifyContent card-body">															
				<textarea rows="2" style="width:310px" name='newName' class='newName' placeholder='카테고리 이름을 입력해주세요'></textarea>
				<div class="buttonDiv">
					<button class='modifyCategory'>변경</button>
					<button class='cancel'>취소</button>
				</div>
			</div>
		</div>
	</div>	


 </div> 
 <jsp:include page="../footer.jsp"/>
<script type="text/javascript">	
var inOrOut = '';
$(document).ready(function(){
	
	//페이지 시작할때 카테고리 목록 불러오기
	 getExpenseCategory();
	 getIncomeCategory();
	 
		
	setTimeout(function() {
		 updateAndDelete(); 
	 },1000);

	//수정창 x누를때 
	$(".close-button").on('click',function(){
			$(this).parent().parent().parent().toggleClass('show-categorymodal');
			
	});
	$(".cancel").on('click',function(event){
		$(this).parent().parent().parent().parent().toggleClass('show-categorymodal');
	});
	 
	//카테고리 추가 하기 
	$("#inputCategory").click(function(event){ 
		if($('#category_name').val() == ""){
			alert("카테고리를 입력해주세요");
			event.preventdefault();
			
		}else{
			$.ajax({
				type : "POST",
				url : "setCategoryPro.moa",
				data :$("#inputCateogry").serialize(),   
				dataType : "json",
				error : function(error){
					console.log("에러!!");
					
				},
				success : function(data){		
					 getExpenseCategory();
					getIncomeCategory();
				}
			
			});
		}
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
         
       		html += "<table class='my_tab' >";
       		html += "<tr>";
      		for (var i = 0; i < outcome.length; i++) {
      				if(i%4==0){
      					html += "</tr>";
      					html +="<tr>";		
      				}
	            	html += "<td class='my_td border-left-primary shadow'>";
	            	html += "<div class='cat_btn'>";
	            	html += "<i class='fas fa-ellipsis-v'></i>";
	            	html += "<div class='my_sub'>";
	            	html +="<p>"
	                html +="<a href='deleteCategory.moa?category_no="+outcome[i].category_no+"&inorout=outcome'>삭제하기</a>";
	                html += "<a class='modify'>수정하기</a>";
	           		html += "</p>"; 
	            	html += "</div>"; 
	            	html += "</div>";
	            	html += "<div class='cat_text font-weight-bold'>";
	            	html += outcome[i].category_name;
	            	html += "</div>";
	            	html += "<input type='hidden' name='category_no' class='category_no' value='"+outcome[i].category_no+"' />";
	            	html += "<input type='hidden' name='inOrOut' class='inOrOut' value='outcome' />";
	            	html += "</td>";
           		}
      		html +="</tr>";
            html += "</table>";
            $(".allExpense").html(html);
          
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
             
       		html += "<table class='my_tab'>";
       		html += "<tr>";
      		for (var i = 0; i < income.length; i++) {
      				if(i%4==0){
      					html += "</tr>";
      					html +="<tr>";		
      				}
	            	html += "<td class='my_td border-left-warning shadow'>";
	            	html += "<div class='cat_btn'>";
	            	html += "<i class='fas fa-ellipsis-v'></i>";
	            	html += "<div class='my_sub'>";
	            	html +="<p>"
	                html +="<a href='deleteCategory.moa?category_no="+income[i].category_no+"&inorout=income'>삭제하기</a>";
	                html += "<a class='modify'>수정하기</a>";
	           		html += "</p>"; 
	            	html += "</div>";
	            	html += "</div>";
	            	html += "<div class='cat_text font-weight-bold text-gray-800'>";
	            	html += income[i].category_name;
	            	html += "</div>";
	            	html += "<input type='hidden' name='category_no' class='category_no' value='"+income[i].category_no+"' />";
	            	html += "<input type='hidden' name='inOrOut' class='inOrOut' value='income' />";
	            	html += "</td>";
           		
      		
      		}
      		html +="</tr>";
            html += "</table>";
            $(".allIncome").html(html);
        }
       
    });
}
//수정 삭제 창이 열려있을때 창밖을 클릭하면 remove 클래스 

$(document).mouseup(function(e){
	var container = $('.my_sub');
	
	if(container.has(e.target).length===0 && $('.cat_btn').has(e.target).length===0){
		$('.my_sub').removeClass('on');
	}
});

//수정하기,삭제하기 창 띄워주기
function updateAndDelete(){
		$('.cat_btn i').each(function(){
			$(this).on('click', function(){
				
				if($(this).next().closest('.my_sub').hasClass('on')){
					//수정.삭제 창에 on클래스가 있으면 on클래스 없애주기 
					$(this).next().closest('.my_sub').removeClass('on');
						
				}else{
					//버튼 누를때 수정삭제 창에 클래스 'on' 넣어주기 
					$(this).next().closest('.my_sub').addClass('on');
				}
				var category_no = $(this).parent().next().next().val();
				var inorout = $(this).parent().next().next().next().val();
				
				//수정하기 탭 누르면 모달창 띄어주기
				cateogryModify(category_no,inorout);
				
				
			});
		});
		
}
//수정하기 모달창 보여주기
function cateogryModify(category_no,inorout){
	
	$('.modify').each(function(){
		$(this).on('click', function(){
			$('.categorymodal').addClass("show-categorymodal");
			modifyAction(category_no,inorout);
		});	
	});
	
}


function toggleWin() {
	$(".cat_btn").on('click', function(){
		alert($(this).children('.my_sub').hasClass("on"));
		if($(this).children('.my_sub').hasClass("on")==true) {
			$(this).children('.my_sub').removeClass('on');
		} else {
			$(this).children('.my_sub').addClass('on');
		}
	});
};


//카테고리 수정하기
function modifyAction(category_no,inorout){
	$('.modifyCategory').unbind("click").bind("click",function(){
		var newName = $('.newName').val();
		if(newName == ""){
			alert("카테고리를 입력해주세요");
		}else{
		window.location.href="/moamore/category/updateCategory.moa?newName="+newName+"&category_no="+category_no+"&inorout="+inorout;
		getExpenseCategory();
		}
	
	});
}
</script>