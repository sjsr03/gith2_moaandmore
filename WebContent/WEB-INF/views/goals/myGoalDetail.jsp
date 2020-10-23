<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>MY GOAL DETAIL</title>
<style>
#run-animation{
	position: relative;
	animation-name : pg_run;
	animation-delay : 300ms;
	animation-duration : 4s;
		animation-duration:leaner;
	animation-fill-mode:both;
}

#pgbar{
	width:100%;
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
.group-type{
	background-color : #3d80eb;
	cursor : pointer;
	
}
.individual-type{
	background-color: #d692af;
}
.group-ttype {
	background-color : #8b8e9e;
}

.goal-explan {
		border-radius: 5px;
		background-color: #e6e6e6;
		padding: 15px;
}
</style>
</head>
<jsp:include page="../sidebar.jsp"/>
<script type="text/javascript">
function setBody(){
	$("#pg").empty();
	$("#pgVal").empty();
	var targetMoney = ${goal.target_money};
	var saving= ${goal.saving};
	var curPg = 0;
	if(saving != 0){
		curPg = ((saving/targetMoney)*100).toFixed(2);
	}
	
	
	var txt = "<progress id='pgbar' value='"+curPg+"' max='100'></progress>";
	$("#pg").append(txt);
	
	txt = "<span>"+curPg+"%</span>";
	$("#pgVal").append(txt);
	
	var spanPdLeft = 0;
	var imgPdLeft = 0;
	
	
	if (curPg != 0){		
		var pgWidthStr = $("#pgbar").css('width').split("px")[0];
		var spanWidth = $("#pgVal span").css('width').split("px")[0];
		var imgWidth = $("#run-animation img").css('width').split("px")[0];
		spanPdLeft= (pgWidthStr * (curPg * 0.01)) - spanWidth/2 ;
		imgPdLeft = (pgWidthStr * (curPg * 0.01)) - imgWidth/2 ;
	}
		
	$("#pgVal").css('padding-left',spanPdLeft+"px");
	
	//동적으로 애니메이션 스타일 추가 
	var styleEle  = document.createElement('style');
	styleEle.id ="keyset";
	document.head.appendChild(styleEle);
	styleEle.innerHTML = "@keyframes pg_run{0%{left: 0px;} 100%{left:"+imgPdLeft+"px;}}";		
}

$(document).ready(function(){
	
	
	if( ${goal.saving} >= ${goal.target_money}){
		if($(window).width() > 375){
			for(var i = 1 ; i<5; i++){
				$(document).snowfall({
			 		 image : "/moamore/resources/img/money"+i+".png",
			         minSize: 40, 
			         maxSize: 60, 
			         flakeIndex : i,
			         flakeCount:10
			    });
			}
				
		}else{
			for(var i = 1 ; i<5; i++){
				$(document).snowfall({
			 		 image : "/moamore/resources/img/money"+i+".png",
			         minSize: 30, 
			         maxSize: 40, 
			         flakeIndex : i,
			         flakeCount:5
			    });
			}
		}
	
 	
	}

 	

	setBody();		
});

	
$(window).resize(function(){
	setBody()
});
	

</script>


<div class="container">
	<div class="row">
		<div>
			<button class="btn btn-light btn-icon-split" style="border-radius:5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="redir('/moamore/goals/myGoalList.moa?','public_ch=${goal.public_ch}')"><span class="text">목록</span></button>
			<c:if test="${goal.public_ch eq'0'.charAt(0)}">
				<button class="btn btn-light btn-icon-split" style="5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="redir('/moamore/goals/modifyForm.moa?goal_no=','${goal.goal_no}')"><span class="text">수정</span></button>
			</c:if>
			<button class="btn btn-light btn-icon-split" style="5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="deleteCh('${goal.goal_no}','${goal.public_ch}','${goal.team_no}')"><span class="text">삭제</span></button>
		</div>
	</div>
	<div class="row mt-5">
		<div class="col-12 pl-0">
			<h2 class="h2">${goal.subject}</h2>
			
		</div>		
	</div>
	<div class="row mb-3">
		<c:if test="${goal.public_ch eq'0'.charAt(0)}">
			<div class="tag-eff individual-type">개인</div>
		</c:if>
		<c:if test="${goal.public_ch eq'1'.charAt(0)}">
			<div class="tag-eff group-type" onclick="window.location.href='/moamore/team/teamDetail.moa?team_no=${goal.team_no}'">그룹</div>
			<c:if test="${goal.public_type eq '0'.charAt(0)}">
				<div class="tag-eff group-ttype">비공개</div>
			</c:if>
			<c:if test="${goal.public_type eq '1'.charAt(0)}">
				<div class="tag-eff group-ttype">공개</div>
			</c:if>	
		</c:if>		
	</div>
	<div class="row">
		<h5 class="h5">
			기간 : <fmt:formatDate  value="${goal.start_day}" pattern="yyyy.MM.dd"/>
			<c:if test="${goal.public_ch eq'1'.charAt(0)}">
				 - <fmt:formatDate  value="${goal.end_day}" pattern="yyyy.MM.dd"/>
			</c:if>
		</h5>
	</div>
	<div class="row">
		<h5 class="h5" >진행도 : <fmt:formatNumber value="${goal.saving}" maxFractionDigits="3"/>원 / <fmt:formatNumber value="${goal.target_money}" maxFractionDigits="3"/>원</h5>
	</div>
	<div class="row" >
		<div id="run-animation" class="col-12 pl-0 pr-0">
			<img src="/moamore/resources/img/pg_character.png"/>
		</div>	
	</div>	
	<div class="row" id="pg"></div>
	<div class="row" id="pgVal"></div>
	<div class="row mt-5">
		<h3>목표액 기록 내역</h3>
	</div>
	<div class="row">
		<table class="table">
		<tr>
			<td>날짜</td>
			<td>금액</td>
		</tr>
		<c:forEach var="record" items="${recordList}">
			<tr>
				<td><fmt:formatDate  value="${record.reg}" pattern="yyyy.MM.dd"/></td>
				<td>+<fmt:formatNumber value="${record.amount}" maxFractionDigits="3"/>원</td>
		
		</c:forEach>
		</table>
	</div>
</div>

<jsp:include page="../footer.jsp"/>	
	
<script>
	//삭제여부 확인 
	function deleteCh(goal_no, public_ch, team_no){
		var ch = true;
		
		if(public_ch == '1'){
			ch = confirm("해당 목표를 삭제하면 참여중인 그룹을 탈퇴하며 그룹목표를 다시 진행할 수 없습니다. 삭제하시겠습니까?");
		}
		console.log(ch);
		if(ch){
			window.location.href = "/moamore/goals/deleteGoal.moa?goal_no="+goal_no+"&public_ch="+public_ch+"&team_no="+team_no;
		}else{
			return;
		}
	}
	
	function redir(url, val){
		window.location.href= url+val;
	}

</script>
