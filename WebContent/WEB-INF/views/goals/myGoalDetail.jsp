<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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


</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	$(document).ready(function(){
		var targetMoney = ${goal.target_money};
		var saving= ${goal.saving};
		var curPg = ((saving/targetMoney)*100).toFixed(2);
		var txt = "<progress id='pgbar' value='"+curPg+"' max='100'></progress>";
		$("#pg").append(txt);
		
		txt = "<span>"+curPg+"%</span>";
		$("#pgVal").append(txt);
		
		var pgWidthStr = $("#pgbar").css('width').split("px")[0];
		var spanWidth = $("#pgVal span").css('width').split("px")[0];
		var imgWidth = $("#run-animation img").css('width').split("px")[0];	
		var spanPdLeft= (pgWidthStr * (curPg * 0.01)) - spanWidth/2 ;
		var imgPdLeft = (pgWidthStr * (curPg * 0.01)) - imgWidth/2 ;
		$("#pgVal").css('padding-left',spanPdLeft+"px");
		
		//동적으로 애니메이션 스타일 추가 
		var styleEle  = document.createElement('style');
		styleEle.id ="keyset";
		document.head.appendChild(styleEle);		
		styleEle.innerHTML = "@keyframes pg_run{0%{left: 0px;} 100%{left:"+imgPdLeft+"px;}}";		
				
	});
	

</script>
</head>
<body>
<jsp:include page="../sidebar.jsp"/>
<div class="container">
	<div class="row">
		<div class="col-1 offset-11">
			<button class="btn btn-light btn-icon-split" style="border-radius:5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="redir('/moamore/goals/myGoalList.moa?','public_ch=${goal.public_ch}')"><span class="text">목록</span></button>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<span class="h1">${goal.subject}</span>
			<c:if test="${goal.public_ch eq'0'.charAt(0)}">
				<button class="btn btn-light btn-icon-split" style="5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="redir('/moamore/goals/modifyForm.moa?goal_no=','${goal.goal_no}')"><span class="text">수정</span></button>
			</c:if>
			<button class="btn btn-light btn-icon-split" style="5px; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="deleteCh('${goal.goal_no}','${goal.public_ch}','${goal.team_no}')"><span class="text">삭제</span></button>
		</div>		
	</div>
	<div class="row mb-2 mt-1">
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
		<span class="h3">
			기간 : <fmt:formatDate  value="${goal.start_day}" pattern="yyyy.MM.dd"/>
			<c:if test="${goal.public_ch eq'1'.charAt(0)}">
				 - <fmt:formatDate  value="${goal.end_day}" pattern="yyyy.MM.dd"/>
			</c:if>
		</span>
	</div>
	<div class="row">
		<h3>목표액 : ${goal.target_money}원</h3>
	</div>
	<div class="row">
		<h3>달성액: ${goal.saving}원</h3>
	</div>
	<div class="row" >
		<div id="run-animation" class="col-2">
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
				<td>+${record.amount}</td>		
			</tr>
		
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