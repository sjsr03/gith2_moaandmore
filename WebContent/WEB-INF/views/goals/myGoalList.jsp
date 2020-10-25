<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myGoalList</title>
<style>
select {

    width: 100px; /* 원하는 너비설정 */
  	padding: .4em .2em;
    font-family: inherit;  /* 폰트 상속 */
    background: url("/moamore/resources/img/select_arrow.png") no-repeat 95% 50%;  /* 화살표 모양의 이미지 */
    border: 1px solid #999;
    border-radius: 5px; /* iOS 둥근모서리 제거 */
    -webkit-appearance: none; /* 네이티브 외형 감추기 */
    -moz-appearance: none;
    appearance: none;

}

#click-title{
	cursor: pointer;
}
#pgbar{
	width : 15em;
}




</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	var list_type = 0; // 개인
	var sorting = "startday_desc"; //정렬 기준
	//컨트롤러에서 넘겨줬을때 
	
	if(${public_ch} != null){
		list_type = ${public_ch};
	}
		
	//ajax로 리스트 가져오기 
	function loadList(_list_type){
		
		list_type = _list_type;	
		console.log("리스트 로드");		
		$.ajax({
			type : "POST",
			url : "/moamore/goals/getMyGoalList.moa",
			data : {"public_ch" : list_type, "sorting": sorting, "list_type" : "proceeding"},
			success:function(data){
				$("#goal_list").empty();
				//헤더추가
				var addListHtml = "<thead><tr>";
				if(list_type == 0){//개인
					addListHtml += "<th>목표명</th>";
					if($(window).width() >= 768){
						addListHtml += "<th>목표액</th>";
						addListHtml += "<th>달성액</th>";
						addListHtml += "<th>달성률</th>";
						addListHtml += "<th>시작날짜</th>";
					}
					
				}else{
					addListHtml += "<th>목표명</th>";
					if($(window).width() >= 768){
						addListHtml += "<th>목표액</th>";
						addListHtml += "<th>달성액</th>";
						addListHtml += "<th>달성률</th>";
						addListHtml += "<th>시작날짜</th>";
						addListHtml += "<th>마감날짜</th>";
						addListHtml += "<th>공개여부</th>";
					}
					
				}
				addListHtml += "</tr></thead>";	
				$("#goal_list").append(addListHtml);
				
				addListHtml = "<tbody>";
				$("#goal_list").append(addListHtml);
				for(var i = 0 ; i<data.length; i++){
					addListHtml = "<tr>";
					addListHtml += "<td id='click-title' onclick='redir("+data[i].goal_no+")'>" + data[i].subject+"</td>";
					if($(window).width() >= 768){
						addListHtml += "<td>" + data[i].target_money.format() + " 원</td>";
						addListHtml += "<td>" + data[i].saving.format()+"원</td>";
						addListHtml += "<td><progress class='pgbar' value='"+ ((data[i].saving/data[i].target_money)*100).toFixed(2)  +"' max='100'></progress> "+ ((data[i].saving/data[i].target_money)*100).toFixed(2)+"%</td>";
						addListHtml += "<td>"+getFormatDate(data[i].start_day)+"</td>";
						if(data[i].public_ch == '1'){//마감날짜, 공개여부 
							addListHtml += "<td>"+getFormatDate(data[i].end_day)+"</td>";
							if(data[i].public_type =='0'){
								addListHtml += "<td>비공개</td>";
							}else{
								addListHtml += "<td>공개</td>";
							}
						}
					}
					addListHtml += "</tr>";			
					$("#goal_list").append(addListHtml);
				}//end for
				addListHtml = "</tbody>";
				$("#goal_list").append(addListHtml);
				
			},
			error : function(e){
				console.log("리스트 로딩 실패");
			}
		})
	}
	

	//개인/그룹 여부에 따라 셀렉트박스 값 채우기
	function loadSelectVal(_list_type){
		list_type = _list_type;
		var addSelectHtml = "";
		console.log("타입:" + list_type);
		$("#sorting_val").empty();
		if(list_type == 0){
			addSelectHtml += "<option value='startday_asc' selected>시작일↑</option>";
			addSelectHtml += "<option value='startday_desc'>시작일↓</option>";
			addSelectHtml += "<option value='achievement_asc'>달성률↑</option>";
			addSelectHtml += "<option value='achievement_desc'>달성률↓</option>";
			$("#sorting_val").append(addSelectHtml);
		}else{
			addSelectHtml += "<option value='startday_asc' selected>시작일↑</option>";
			addSelectHtml += "<option value='startday_desc'>시작일↓</option>";
			addSelectHtml += "<option value='endday_asc'>마감일↑</option>";
			addSelectHtml += "<option value='endday_desc'>마감일↓</option>";
			addSelectHtml += "<option value='achievement_asc'>달성률↑</option>";
			addSelectHtml += "<option value='achievement_desc'>달성률↓</option>";
			addSelectHtml += "<option value='public'>공개</option>";
			addSelectHtml += "<option value='private'>비공개</option>";
			$("#sorting_val").append(addSelectHtml);
		}
		sorting  = $("#sorting_val option:selected").val();
	}
	
$(document).ready(function(){
	if($(window).width() >= 768){
		loadSelectVal(list_type);	
	}else{
		$("#sorting_val").css('display','none');
	}
	loadList(list_type);		
})
	
$(window).resize(function(){
	if($(window).width()>= 768){
		$("#sorting_val").css('display','block');
	}
	loadList(list_type);
})
		
</script>
</head>

<body>
	<jsp:include page="../sidebar.jsp"/>
	<!-- 본문 -->

	<div class="container-fluid">
		<div class="row mb-5"></div>
		 <div class="row d-sm-flex align-items-center justify-content-between mb-4">
		 	<div class="col-12 text-center">
	            <h3 class="h3 mb-0 text-gray-800">[${sessionScope.memName}]의 목표리스트</h1>
            </div>
	     </div>
		<div class="row mt-2 mb-2">
			<div class="col-6">	
			<button class="btn btn-light btn-icon-split" style="border-radius:0.35em 0em 0em 0.35em; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="chageType(0)"><span class="text">개인</span></button>
				<button class="btn btn-light btn-icon-split" style="border-radius:0em 0.35em 0.35em 0em; border:2px solid #ccc; border-left:1px solid #ccc;" onclick="chageType(1)"><span class="text">그룹</span></button>
			</div>
			<div class="col-6 text-right" >
				<button class="btn btn-light btn-icon-split" style="border-radius:0.35em 0.35em 0.35em 0.35em; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="window.location.href='/moamore/goals/insertGoalForm.moa'"><span class="text">+목표</span></button>	
				<select id="sorting_val">
				</select>
			</div>
		</div>
		<div class="row">	
			<div class="col-12">
				<table id="goal_list" class="table text-center">
				</table>
			</div>
		</div>
		
	</div>
	
	
	
	
	<jsp:include page="../footer.jsp"/>
	
	
	
	
	
<script>
//페이지 이동 
function redir(goal_no){
	window.location.href="/moamore/goals/myGoalDetail.moa?goal_no="+goal_no;
}

//숫자 자릿수 포맷(3자리수마다 ,) 
Number.prototype.format = function(){
	if(this ==0) return 0;
	
	var reg = /(^[+-]?\d+)(\d{3})/;
	var n = (this +'');
	
	while(reg.test(n)) n = n.replace(reg, '$1'+','+'$2');
	
	return n;
}

//문자 자릿수 포맷(3자리수마다 ,) 
String.prototype.format = function(){
	var num = parseFloat(this);
	if(isNan(num)) return "0";
	
	return num.format();
	
}

$(document).on('change','#sorting_val',function(){	
	sorting  = $("#sorting_val option:selected").val();
	loadList(list_type);
	
})





function chageType(_list_type){
	if($(window).width()< 768){
		loadSelectVal(_list_type);	
	}
	loadList(_list_type);
	
}

//데이트 포맷바꾸기 
function getFormatDate(str){
	var date = new Date(str);
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0'+month;
	var day = date.getDate();
	day= day>=10 ? day : '0'+day;
	return year + '.' +month + '.' + day;
}

</script>
</body>
</html>