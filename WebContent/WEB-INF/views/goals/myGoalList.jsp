<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myGoalList</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>

	var list_type = 0; // 개인
	if(${public_ch} != null){
		list_type = ${public_ch};
	}
	var sorting = "reg_desc"; //최신순
	
	$(document).ready(function(){
		loadList(list_type);
		
	})

</script>
</head>

<body>
<h1> ${sessionScope.memName} 님의 목표리스트 </h1>


<button onclick="window.location.href='/moamore/goals/insertGoalForm.moa'">+목표</button>

<button onclick="loadList(0)">개인</button>
<button onclick="loadList(1)">그룹</button>

<select id="sorting_val">
</select>

<table border="1" id="goal_list">
</table>

<script>
	//ajax로 리스트 가져오기 
	function loadList(_list_type){
		list_type = _list_type;
		$.ajax({
			type : "POST",
			url : "/moamore/goals/getMyGoalList.moa",
			data : {"public_ch" : list_type },
			success:function(data){
				$("#goal_list").empty();
				
				//헤더추가
				var addListHtml = "<tr>";
				if(list_type == 0){//개인
					addListHtml += "<td>목표명</td>";
					addListHtml += "<td>목표액</td>";
					addListHtml += "<td>달성액</td>";
				}else{
					addListHtml += "<td>목표명</td>";
					addListHtml += "<td>목표액</td>";
					addListHtml += "<td>달성액</td>";
					addListHtml += "<td>시작날짜</td>";
					addListHtml += "<td>마감날짜</td>";
					addListHtml += "<td>공개여부</td>";
				}
				addListHtml += "</tr>";	
				$("#goal_list").append(addListHtml);
				
				for(var i = 0 ; i<data.length; i++){
					addListHtml = "<tr>";
					addListHtml += "<td onclick='redir("+data[i].goal_no+")'>" + data[i].subject+"</td>";
					addListHtml += "<td>" + data[i].target_money+"</td>";
					addListHtml += "<td>" + data[i].saving+"</td>";
					if(data[i].public_ch == '1'){
						addListHtml += "<td>"+data[i].start_day+"</td>";
						addListHtml += "<td>"+data[i].end_day+"</td>";
						if(data[i].public_type =='0'){
							addListHtml += "<td>비공개</td>";
						}else{
							addListHtml += "<td>공개</td>";
						}
					}
					if(data[i].public_ch == '0'){
					}
					addListHtml += "<tr/>";			
					$("#goal_list").append(addListHtml);
				}//end for
				loadSelectVal(list_type);
			},
			error : function(e){
				console.log("리스트 로딩 실패");
			}
		})
		
	}

	//개인/그룹 여부에 따라 셀렉트박스 값 채우기
	function loadSelectVal(list_type){
		var addSelectHtml = "";
		$("#sorting_val").empty();
		if(list_type == 0){
			addSelectHtml += "<option value='startday_desc'>시작일↑</option>";
			addSelectHtml += "<option value='startday_asc'>시작일↓</option>";
			addSelectHtml += "<option value='achievement_asc'>달성률↑</option>";
			addSelectHtml += "<option value='achievement_desc'>달성률↓</option>";
			$("#sorting_val").append(addSelectHtml);
		}else{
			addSelectHtml += "<option value='startday_asc'>시작일↑</option>";
			addSelectHtml += "<option value='startday_desc'>시작일↓</option>";
			addSelectHtml += "<option value='endday_asc'>마감일↑</option>";
			addSelectHtml += "<option value='endday_desc'>마감일↓</option>";
			addSelectHtml += "<option value='achievement_asc'>달성률↑</option>";
			addSelectHtml += "<option value='achievement_desc'>달성률↓</option>";
			addSelectHtml += "<option value='public'>공개</option>";
			addSelectHtml += "<option value='private'>비공개</option>";
			$("#sorting_val").append(addSelectHtml);
		}
		
	}
	
	function redir(goal_no){
		window.location.href="/moamore/goals/myGoalDetail.moa?goal_no="+goal_no;
	}
	
	

</script>
</body>
</html>