<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Realtime Ranking List</title>
<style>
	.block {border:2px solid #adadad; padding:0 5px; height:30px; overflow:hidden; background:#fff; width:150px; font-family:Gulim; font-size:15px;}
	.block ul,
	.block li {margin:0; padding:0; list-style:none;}
	.block li a {display:block; height:30px; line-height:30px; color:#555; text-decoration:none;}
	.block li span {padding:2px 5px; background:#adadad; color:#fff; font-weight:bold; margin-right:3px;}
	
</style>
<script>
	

	$(function(){
		
		$.ajax({
			type : "POST",
			url : "/moamore/ranking/getRealTimeRankingPro.moa",
			success:function(data){
				var txt ="";
				for(var i = 0 ; i < data.length; i++){
					txt += "<li><a><span>"+(i+1)+"</span> "+ data[i]+"</a>";
					
					
				}
				
				$("#ticker").append(txt);
				
			},
			error : function(e){
				console.log("리스트 로딩 실패");
			}
		})
		//실검 슬라이드
		 var ticker = function(){
		        setTimeout(function(){
		            $('#ticker li:first').animate( {marginTop: '-30px'}, 400, function()
		            {
		                $(this).detach().appendTo('ul#ticker').removeAttr('style');
		            });
		            ticker();
		        }, 3000);
		 };
		 ticker();
	})

</script>
</head>
<body>
	<div id="title"><span>목표 달성 랭킹 순위 </span></div>

	<div class="block">
	    <ul id="ticker">
	    </ul>
	</div>
	
</body>
</html>


