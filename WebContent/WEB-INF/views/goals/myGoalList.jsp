<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myGoalList</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>


	var list_type = 0; // 개인
	var sorting = ""; //정렬 기준
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
			data : {"public_ch" : list_type, "sorting": sorting },
			success:function(data){
				$("#goal_list").empty();
				
				//헤더추가
				var addListHtml = "<tr>";
				if(list_type == 0){//개인
					addListHtml += "<td>목표명</td>";
					addListHtml += "<td>목표액</td>";
					addListHtml += "<td>달성액</td>";
					addListHtml += "<td>달성률</td>";
					addListHtml += "<td>시작날짜</td>";
				}else{
					addListHtml += "<td>목표명</td>";
					addListHtml += "<td>목표액</td>";
					addListHtml += "<td>달성액</td>";
					addListHtml += "<td>달성률</td>";
					addListHtml += "<td>시작날짜</td>";
					addListHtml += "<td>마감날짜</td>";
					addListHtml += "<td>공개여부</td>";
				}
				addListHtml += "</tr>";	
				$("#goal_list").append(addListHtml);
				
				for(var i = 0 ; i<data.length; i++){
					addListHtml = "<tr>";
					addListHtml += "<td onclick='redir("+data[i].goal_no+")'>" + data[i].subject+"</td>";
					
					
					addListHtml += "<td>" + data[i].target_money.format() + "</td>";
					addListHtml += "<td>" + data[i].saving.format()+"</td>";
					addListHtml += "<td><progress value='"+ ((data[i].saving/data[i].target_money)*100).toFixed(2)  +"' max='100'></progress>"+((data[i].saving/data[i].target_money)*100).toFixed(2)+"%</td>";
					addListHtml += "<td>"+getFormatDate(data[i].start_day)+"</td>";
					
					if(data[i].public_ch == '1'){//마감날짜, 공개여부 
						addListHtml += "<td>"+getFormatDate(data[i].end_day)+"</td>";
						if(data[i].public_type =='0'){
							addListHtml += "<td>비공개</td>";
						}else{
							addListHtml += "<td>공개</td>";
						}
					}
					addListHtml += "<tr/>";			
					$("#goal_list").append(addListHtml);
				}//end for
				
				console.log(sorting);
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
		loadSelectVal(list_type);	
		loadList(list_type);		
	})
		
</script>
</head>

<body>
<h1> ${sessionScope.memName} 님의 목표리스트 </h1>


<button onclick="window.location.href='/moamore/goals/insertGoalForm.moa'">+목표</button>

<button onclick="chageType(0)">개인</button>
<button onclick="chageType(1)">그룹</button>

<select id="sorting_val">
</select>

<table border="1" id="goal_list">
</table>


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
	loadSelectVal(_list_type);	
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