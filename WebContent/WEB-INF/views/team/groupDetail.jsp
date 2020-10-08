<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Group Detail Info</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	function enterTeam(team_no){
		var ch = confirm("팀에 참여하고 팀목표를 내 목표 목록에 추가하시겠습니까?")
		if(ch){
			window.location.href="/moamore/goals/enterTeam.moa?team_no="+team_no;
		}else{
			return;
		}
	}
	
	var team_no  = ${team.team_no};
	var memList;
	$(document).ready(function(){
		$.ajax({
			type:"POST",
			url:"/moamore/teamMember/selectAllByTeamNo.moa?team_no="+team_no,
			data : team_no,
			success : function(data){
				//참여버튼 상태
				
				var btnCh = false;
				
				//팀원 목록
				memList = data;
				for(var i = 0 ; i < memList.length ; i ++){
					if(memList[i].id == "${sessionScope.memId}"){
						btnCh = true;
						
					}
					
					$("#team_memList").append("<li>"+ (i+1)+"등."+memList[i].nickname+"님. 달성액:"+memList[i].saving+"원</li>")
				}
				
				if(btnCh == true){
					$("#enterBtn").text("참여중");
				}else{
					$("#enterBtn").text("참여하기");
				}
			},
			error: function(error){
				console.log("에러!!");
			}
		})
		
		$("#enterBtn").click(function(){
			var status = $(this).text();
			if(status == '참여중'){
				var ch = alert("진행 중인 목표입니다. 참여를 취소하시려면 '목표>해당 목표 상세페이지'에서 삭제를 눌러주세요.");
			
			}else if( status =='참여하기'){
				var ch = confirm("팀에 참여하고 팀목표를 내 목표 목록에 추가하시겠습니까?")
				if(ch){
					window.location.href="/moamore/goals/enterTeam.moa?team_no="+team_no;
				}else{
					return;
				}
				
			}
			
		})
		
	});
	
	
</script>
</head>
<body>
	
	<h1>${team.subject}</h1>
	<h2>${team.content}</h2>
	<h3>${team.amount}</h3>
	<span></span>
	<h3>시작날짜 : ${team.start_day}</h3>
	<h3>마감날짜 : ${team.end_day}</h3>
	<h3>개설자 : ${team.leader}</h3>
	<h3>참가인원수 :${team.people} </h3>
	<h3>그룹상태 :  ${team.status}</h3>
	<h3>그룹 타입 : ${team.isopen } </h3>
	
	<c:if test="${1 == 1}">
		<button id="enterBtn"></button>
	</c:if>
	
	
	<h1>참여인원</h1>
	
	<div >
		<ul id="team_memList">
		</ul>
	</div>
	
	

</body>
</html>