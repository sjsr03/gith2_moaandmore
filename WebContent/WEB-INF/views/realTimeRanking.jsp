<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<title>Realtime Ranking List</title>
<style>
@import url(https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css);
.normal     { font-weight: 400 }
.bold       { font-weight: 700 }
.bolder     { font-weight: 800 }
.light      { font-weight: 300 }

#ranking-body {
	padding:0px;
	margin:0px;
	font-family: 'NanumSquare', sans-serif;
	position:relative;
}

#ranking-wrap {
	margin:0px;
	padding:0px;
	width:100%;
}
.ranking-container {
	width:800px;
	margin:0 auto;
	margin-top:10px;
}
#ranking-body a,#ranking-body img {
	border:0px;
	text-decoration:none;
}
#ranking-body ul,#ranking-body li {
	list-style:none;
	padding:0px;
	margin:0px;
}
#ranking-body li:nth-child(2) {
	margin-top:15px;
}
#ranking-body h1 {
	color:#525252;
	font-size:40px;
	font-weight:100;
	text-align:center;
	margin:0px;
	margin-top:193px;
}
#ranking-body h1 > b {
	font-weight:900;
}

#best_search {
	width:740px;
	margin:0 auto;
	text-align:center;
}
#best_search li,dd {
	display:inline-block;
	vertical-align:middle;
}

#best_search li p {
	color:#393939;
	font-size:17px;
	font-weight:bold;
	margin:0px;
	padding-right:30px;
}
#ranking-body dd {
	margin:0px;
}
#ranking-body dd  a.t{
	cursor:pointer;
	margin:0px;
	color:#4b4b4b;
	
	overflow:hidden;
	width:94px;
	white-space:nowrap;
	display:inline-block;
	font-size:15px;
	text-align:left;
	padding-right:13px;
	font-weight:700;
	vertical-align:middle;
}
#ranking-body dd .num {
	background-color:#4b4b4b;
	color:#fff;
	font-size:10px;
	margin-right:10px;
	vertical-align:middle;
	width:18px;
	height:18px;
	float:left;
	line-height:18px;
	text-align:center;
}
.best_add {
	border:1px solid #bebebe;
	color:#4b4b4b;
	padding:0px 4px;
	margin-left:10px;
}
	
</style>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>

$(function(){
	
	$.ajax({
		type : "POST",
		url : "/moamore/ranking/getRealTimeRankingPro.moa",
		success:function(data){
			var txt ="";
			for(var i = 0 ; i < 5; i++){
				txt += "<dd><a class='t'><div class='num'>"+(i+1)+"</div>"+data[i]+"</a></dd>";
			}
			
			$(".time1").append(txt);
			
			txt="";
			for(var i = 5 ; i < 10; i++){
				txt += "<dd><a class='t'><div class='num'>"+(i+1)+"</div>"+data[i]+"</a></dd>";
			}
			$(".time4").append(txt);
			
			
			
			
		},
		error : function(e){
			console.log("리스트 로딩 실패");
		}
	})
	
	
})
	function view(arg){
		$(".time1, .time4, .ad1, .ad4").css("display","none");
		if(arg=="0") {
			$(".time4, .ad4").css("display","block");
			viewcount = 3;
		}else if(arg=="3") {
			$(".time1, .ad1").css("display","block");
			viewcount = 0;
		}
	}
	var viewcount = 0;
	var rtcarousel = setInterval(function(){ view(viewcount) },5000);
	
	$("#best_search").mouseenter(function() {
		clearInterval(rtcarousel);
	});
	
	$("#best_search").mouseleave(function() {
		rtcarousel = setInterval(function(){ view(viewcount) },5000);
	});


	

</script>
</head>
<div id="ranking-body">
	<div id="ranking-wrap">
		<div class="ranking-container">
			<ul id="best_search">
				<li><p>목표 달성 랭킹</p></li>
				<li>
					<dl class="time1" style="display:">
					</dl>
					<dl class="time4" style="display:none;">
					</dl>
				</li>
				<li>
					<a class="best_add ad1" style="cursor:pointer" onClick="javascript:view('0')">></a>
					<a class="best_add ad4" onClick="javascript:view('3')" style="display:none;cursor:pointer"><</a>
				</li>
			</ul>
		</div>
   </div>
	
</div>
