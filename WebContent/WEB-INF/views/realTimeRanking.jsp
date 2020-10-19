<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Realtime Ranking List</title>
<style>
	.block {border:2px solid #d81f25; padding:0 5px; height:20px; overflow:hidden; background:#fff; width:100px; font-family:Gulim; font-size:12px;}
	.block ul,
	.block li {margin:0; padding:0; list-style:none;}
	.block li a {display:block; height:20px; line-height:20px; color:#555; text-decoration:none;}
	.block li span {padding:2px 5px; background:#d81f25; color:#fff; font-weight:bold; margin-right:3px;}
</style>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	


	$(function()
		{
		
		
		
		
		//실검 슬라이드
		    var ticker = function()
		    {
		        setTimeout(function(){
		            $('#ticker li:first').animate( {marginTop: '-20px'}, 400, function()
		            {
		                $(this).detach().appendTo('ul#ticker').removeAttr('style');
		            });
		            ticker();
		        }, 3000);
		    };
		    ticker();
		});

</script>
</head>
<body>
	<div class="block">
	    <ul id="ticker">
	        <li><a href="#"><span>1</span> 김연아</a></li>
	        <li><a href="#"><span>2</span> 손연재</a></li>
	        <li><a href="#"><span>3</span> 유아니</a></li>
	        <li><a href="#"><span>4</span> 차승원</a></li>
	        <li><a href="#"><span>5</span> 전지현</a></li>
	        <li><a href="#"><span>6</span> 유이</a></li>
	        <li><a href="#"><span>7</span> 손연재</a></li>
	        <li><a href="#"><span>8</span> 손연재</a></li>
	        <li><a href="#"><span>9</span> 손연재</a></li>
	    </ul>
	</div>
</body>
</html>