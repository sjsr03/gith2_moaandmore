<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Group Detail Info</title>
<style>
	.goal-explan {
		border-radius: 5px;
		background-color: #e6e6e6;
		padding: 15px;
	}
	.tag-eff{
	width : 60px;
	height: 35px;
	border-radius: 5px;
	font-size: 17px;
	color: white;
	text-align: center;
	line-height: 35px;
	margin : 0px 2px;
	}
	.before{
		background-color : #8b8e9e;
	}
	.proceeding{
		background-color: #d692af;
	}
	.end{
		background-color : #414142;
	}
	.group-type{
		background-color: #577de6;
	}
	
	#team_memList{
		list-style : none;
	
	}
	.pg-text{
		padding: 0px 5px;
	}

</style>
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
				
				//참여자수
				var enterPeopleCount = memList.length;
				$("#pCnt").text( enterPeopleCount+"명");
				
				console.log(enterPeopleCount);
				
				
				if($(window).width >= 768){
					$("#team_memList").append("<thead><th>랭크</th><th>활동명</th><th>진행도</th></thead>");
					$("#team_memList").append("<tbody>");
					for(var i = 0 ; i < memList.length ; i ++){
						if(memList[i].id == "${sessionScope.memId}"){
							btnCh = true;
						}
						var pgVal = ((memList[i].saving/ ${team.amount})*100 ).toFixed(2);
				
						if(memList[i].tmp_rank == 1){
							$("#team_memList").append("<tr><td class='h3'>"+ memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><progress value='"+ pgVal  +"' max='100'></progress><span class='pg-text'>"+pgVal+"%</span><span class='pg-text'>"+(memList[i].saving).format()+"/"+ (${team.amount}).format()+"(원)</span></td></tr>");
						}else if(memList[i].tmp_rank ==2){
							$("#team_memList").append("<tr><td class='h4'>"+  memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><progress value='"+ pgVal  +"' max='100'></progress><span class='pg-text'>"+pgVal+"%</span><span class='pg-text'>"+(memList[i].saving).format()+"/"+ (${team.amount}).format()+"(원)</span></td></tr>");
						}else if(memList[i].tmp_rank ==3){
							$("#team_memList").append("<tr><td class='h5'>"+ memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><progress value='"+ pgVal  +"' max='100'></progress><span class='pg-text'>"+pgVal+"%</span><span class='pg-text'>"+(memList[i].saving).format()+"/"+ (${team.amount}).format()+"(원)</span></td></tr>");
						}else{
							$("#team_memList").append("<tr><td class='text'>"+  memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><progress value='"+ pgVal  +"' max='100'></progress><span class='pg-text'>"+pgVal+"%</span><span class='pg-text'>"+(memList[i].saving).format()+"/"+ (${team.amount}).format()+"(원)</span></td></tr>");
						}
					}
					$("#team_memList").append("</tbody>");
					
				}else{
					$("#team_memList").append("<thead><th>랭크</th><th>활동명</th><th>진행도</th></thead>");
					$("#team_memList").append("<tbody>");
					for(var i = 0 ; i < memList.length ; i ++){
						if(memList[i].id == "${sessionScope.memId}"){
							btnCh = true;
						}
						var pgVal = ((memList[i].saving/ ${team.amount})*100 ).toFixed(2);
				
						if(memList[i].tmp_rank == 1){
							$("#team_memList").append("<tr><td class='h3'>"+ memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><span class='pg-text'>"+pgVal+"%</span></td></tr>");
						}else if(memList[i].tmp_rank ==2){
							$("#team_memList").append("<tr><td class='h4'>"+  memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><span class='pg-text'>"+pgVal+"%</span></td></tr>");
						}else if(memList[i].tmp_rank ==3){
							$("#team_memList").append("<tr><td class='h5'>"+ memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><span class='pg-text'>"+pgVal+"%</span></tr>");
						}else{
							$("#team_memList").append("<tr><td class='text'>"+  memList[i].tmp_rank+"</td><td>"+memList[i].nickname+"</td><td><span class='pg-text'>"+pgVal+"%</span></td></tr>");
						}
					}
					$("#team_memList").append("</tbody>");
					
				}
				
				
				if(btnCh == true){
					$("#enterBtnText").text("참여중");
				}else{
					$("#enterBtnText").text("참여하기");
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
<jsp:include page="../sidebar.jsp"/>
<div class="container">
	<div class="row mt-3">
		<div>
			<button class="btn btn-light btn-icon-split" style="border-radius:5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="window.location.href='/moamore/team/groupList.moa'"><span class="text">목록</span></button>
		</div>
	</div>
	<div class="row mt-3"><span class="t4">개설자 : ${team.leader}</span></div>
	<div class="row mb-1">
		<fmt:parseDate var="stFmt" pattern="yyyy-MM-dd HH:mm:ss.SSS" value="${team.start_day}"/>
		<fmt:parseDate var="edFmt" pattern="yyyy-MM-dd HH:mm:ss.SSS" value="${team.end_day}"/>
		<span>기간 : <fmt:formatDate value="${stFmt}" pattern="yyyy.MM.dd" /> - <fmt:formatDate value="${edFmt}" pattern="yyyy.MM.dd" /></span>
	</div>
	<div class="row mt-1 mb-2">
		<h2 class="h2">${team.subject}</h2>
		<c:if test="${team.status == 1}">
			<div class="tag-eff before">시작전</div>
		</c:if>
		<c:if test="${team.status ==2 }">
			<div class="tag-eff proceeding">진행중</div>
			<c:if test="${team.isopen == 0 }">
				<div class="tag-eff group-type">비공개</div>
			</c:if>	
			<c:if test="${team.isopen == 1 }">
				<div class="tag-eff group-type">공개</div>
			</c:if>
		</c:if>
		<c:if test="${team.status ==3}">
			<div class="tag-eff end">종료</div>
		</c:if>
		<c:if test="${team.status == 1}">
			<button class="btn btn-light btn-icon-split" style="height:35px; border-radius:5px; border:2px solid #ccc; border-right:1px solid #ccc;" id="enterBtn"><span id="enterBtnText" class="text"></span></button>
		</c:if>	
	</div>
	<div class="row mb-1"><h5 class="h5">목표액 : ${team.amount}원</h5></div>
	<div class="row goal-explan">
		<span class="text">${team.content}</span>
	</div>
	
	
	<div class="row mt-5">
		<span class="h2">참여인원 목록</span>
	</div>
	<div class="row">
		<span class="h5"><span class="h4" id="pCnt" style="color: #d692af; font-weight: bold; "></span>의 회원들이 참여중입니다.</span>
	</div>	
	<div class="row mt-2">
		<div class="col-12">
			<table id="team_memList" class="table">
			</table>
		</div>
	</div>
	
	
</div>
<jsp:include page="../footer.jsp"/>
<script>
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

</script>
</body>
</html>