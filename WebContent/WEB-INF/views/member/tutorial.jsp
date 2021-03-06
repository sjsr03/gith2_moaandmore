<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<head>
<title>도움말</title>
</head>

<style>

.container-fluid body{

	background-color:#0071a1;
	}


.container-fluid .tu_bg{
	 background-color:#f8f9fc;
	 text-align:center;
}

.container-fluid .imgbox{
	display:block;
	padding:3%;

}
.container-fluid .tu_img{
	width:40%;
	padding:10px;
	margin:0 auto; 
}


.container-fluid img{
  max-width:100%;
  max-height: 100%;


}

.container-fluid .img_title{
	margin:3%;
}

.container-fluid .sign_btn{
	color: #fff;
    background-color: #f6c23e;
    border-color: #fff;
}

</style>

<jsp:include page="../sidebar.jsp"/>


<div class="container-fluid">
<div class="tu_bg">
		<div class="welcome"><h1 class="text-gray-900 mb-1"> Welcome to Moa & More</h1></div>
		<div class="wel_Title"><p class="mb-4"> 예산을 설정하고, 돈을 모아 목표를 달성하세요!</p></div>
		
		
	  		<div class="imgbox">
	  			<div class="tu_img shadow py-2"><img src="/moamore/resources/img/setBudget.png" /></div>
	  			<p class="img_title text-gray-900 ">1.가입 후 예산을 설정하세요. 기간을설정하고 기간동안 사용할 금액을 입력 후 <br>카테고리별  예산을 총금액에 맞춰 설정해주세요.<br><span style="color:red">*예산을 새로 설정하면 이전 예산은 끝이 납니다.</span></p>
	  		</div>
	  		<div class="imgbox">
	  			<div class="tu_img shadow py-2"><img src="/moamore/resources/img/goalList.png" /> </div>
	  			<p class="img_title text-gray-900">2.목표를 설정하세요. 티클모아 태산! 하루하루 조금씩 모아 목표를 이루는 재미를 느껴봐요.<br>
	  											    다른사람들과 함께 목표를 공유할 수도 있습니다.  </p>
	  		</div>
	  		<div class="imgbox">
	  			<div class="tu_img shadow py-2"><img src="/moamore/resources/img/insert.png" /> </div>
	  			<p class="img_title text-gray-900">3.수입 지출을 입력하세요. 예산외 지출, 예산외 수입, 예산내 지출로 잘 나눠서 입력해주세요.</p>
	  		</div>
	  		<div class="imgbox">
	  			<div class="tu_img shadow py-2"><img src="/moamore/resources/img/transfer.png" /> </div>
	  			<p class="img_title text-gray-900">4.남은 예산을 분배하세요. 현재 예산 재분배와 목표로 보내기 중 선택할 수 있습니다.  </p>
	  		</div>
	  		<div class="imgbox">
	  			<div class="tu_img shadow py-2"><img src="/moamore/resources/img/budgetReport.png" /> </div>
	  			<p class="img_title text-gray-900">5.보고서를 확인하세요. 이번달 예산정보를 한눈에 확인할 수 있습니다.</p>
	  		</div>
	  
</div> 
            
</div>	

<jsp:include page="../footer.jsp"/>