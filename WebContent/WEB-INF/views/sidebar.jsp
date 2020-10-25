<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="/moamore/js/snowfall.jquery.js"></script>
	<!-- Custom fonts for this template-->
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	
	<!-- Custom styles for this template-->
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">  
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<style>
	ul.toggled > #BudgetState {
		display:none;
	}
	
		
	
	
</style>
<script>
$(document).ready(function(){
	var screenWidth = screen.width;
	
	if(screenWidth < 576) {
		$("#page-top").addClass('toggled');
		$('#accordionSidebar').addClass('toggled');
		$('#accordionSidebar').css('display','block');
		
		$(document).mouseup(function(e){
			var container = $('#accordionSidebar');
			
			if(container.has(e.target).length===0){
				$('#accordionSidebar').addClass('toggled');
				$("#content-wrapper").css('opacity', 1);
				$("#page-top").addClass('toggled');
			}
		});
		
		$("#sidebarToggleTop").on('click', function(){
			if($('#accordionSidebar').hasClass('toggled') == false) {
				$('#accordionSidebar').removeClass('toggled');
				$("#content-wrapper").css('opacity', 0.4);
			}
		});
	}
	
	$.ajax({	//사이드바 그래프 불러오기
		url:"/moamore/getBudgetState.moa",
		type:"post",
		data:{
			id:"${memId}"
		},
		success:function(data){
			var totalBudget = data['totalBudget'];
			totalBudget += "";
			var TBString = totalBudget.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			
			var outcomeSum = data['outcomeSum'];
			var rate = (outcomeSum/totalBudget*100).toFixed(1);
			
			if(totalBudget== null) {	//설정된 현재예산이 없다면
				$("#BudgetState").text("설정된 예산이 없습니다.");
			} else {
				$("#BudgetState").append('<div class="card-header"><h6 class="m-0 font-weight-bold text-primary">총 예산액 : ' + TBString + '원</h6></div><div class="card-body"><div class="progress"><div class="progress-bar" role="progressbar" style="width: ' + rate + '%" aria-valuenow="' + rate + '" aria-valuemin="0" aria-valuemax="100">' + rate + '%</div></div></div>');
			}
		}
	});
});
</script>

<body id="page-top">
	<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/moamore/main.moa">
        <div class="sidebar-brand-icon">
          <!-- <i class="fas fa-laugh-wink"></i> -->
          <img src="/moamore/resources/img/whitepigF.png" width="50" height="50" />
        </div>
        <div class="sidebar-brand-text mx-3">모아 & More</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">
      
      
      <!-- 예산 사용액 그래프 -->
      <div class="card" id="BudgetState" style="border-radius:0;height:100px;">
   		
      </div>
      
      
      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="/moamore/main.moa">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>대시보드</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">
      
      
      <!-- 예산탭 시작 -->
	<c:if test="${path.indexOf('/team/') < 0 && path.indexOf('/closing/') < 0}" >
      <!-- Heading -->
      <div class="sidebar-heading">
        예산 관리
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseBudget" aria-expanded="true" aria-controls="collapseBudget">
          <i class="fas fa-fw fa-coins"></i>
          <span>예산</span>
        </a>
        <div id="collapseBudget" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">예산</h6>
            <a class="collapse-item" href="/moamore/budget/todayBudget.moa">오늘의 예산</a>
            <a class="collapse-item" href="/moamore/budget/setBudget.moa">예산 설정</a>
          </div>
        </div>
      </li>


      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="/moamore/record/moneyRecord.moa">
          <i class="fas fa-fw fa-list-ul"></i>
          <span>수입/지출내역</span></a>
      </li>

      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="/moamore/calendar/calendar.moa">
          <i class="fas fa-fw fa-calendar-alt"></i>
          <span>달력</span></a>
      </li>
       <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseReport" aria-expanded="true" aria-controls="collapseReport">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>보고서</span>
        </a>
        <div id="collapseReport" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">보고서</h6>
            <a class="collapse-item" href="/moamore/report/report.moa">예산 보고서</a>
            <a class="collapse-item" href="/moamore/report/expectation.moa" id="expectation" onclick="$('#loading').css('display','flex');">데이터 분석</a>
          </div>
        </div>
      </li>
      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="/moamore/search/searchForm.moa">
          <i class="fas fa-fw fa-search"></i>
          <span>검색</span></a>
      </li>
      <!-- Nav Item - Tables -->
      <li class="nav-item">
       <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseGoal" aria-expanded="true" aria-controls="#collapseGoal">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>목표</span>
        </a>
          <div id="collapseGoal" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
           <h6 class="collapse-header">목표</h6>
            <a class="collapse-item" href="/moamore/goals/myGoalList.moa">진행중인 목표</a>
            <a class="collapse-item" href="/moamore/goals/completeGoalList.moa">완료한 목표</a>
          </div>
        </div>
      </li>
      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="/moamore/category/setCategory.moa">
          <i class="fas fa-fw fa-cog"></i>
          <span>설정</span></a>
      </li>	   
      
	</c:if>
      
      <!-- 예산 탭 끝 -->
      <!-- 커뮤니티탭 시작 -->
      <c:if test="${path.indexOf('/team/') >= 0 || path.indexOf('/closing/') >= 0}" >
		 <!-- Heading -->
      <div class="sidebar-heading">
        커뮤니티
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-coins"></i>
          <span>그룹</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">그룹</h6>
            <a class="collapse-item" href="/moamore/team/groupList.moa?isMyTeam=0">전체 그룹 보기</a>
            <c:if test="${sessionScope.memName != null }" >
            <a class="collapse-item" href="/moamore/team/groupMyRequestList.moa?nickname=${sessionScope.memName}">My 개설 신청 리스트</a>
            <a class="collapse-item" href="/moamore/team/groupList.moa?isMyTeam=1">내가 가입한 그룹 보기</a>
            <a class="collapse-item" href="/moamore/team/groupComeInviteList.moa">나를 초대한 그룹 보기</a>
            </c:if>
            <c:if test="${sessionScope.memName == null }" >
            <span class="text">로그인 후 접근가능한 게시판입니다.</span>
            </c:if>
          </div>
        </div>
      </li>


      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="#">
          <i class="fas fa-fw fa-list-ul"></i>
          <span>랭킹 보기</span></a>
      </li>
      <!-- Nav Item - Charts -->
      <li class="nav-item">
        <a class="nav-link" href="/moamore/closing/closingAccountList.moa">
          <i class="fas fa-fw fa-list-ul"></i>
          <span>결산 게시판</span></a>
      </li>
      
	</c:if>
      
      

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>
          
          <!-- 상단 메뉴 (예산 / 커뮤니티) -->
          
			<div class="input-group" style="width:100%">
				<button class="btn btn-light btn-icon-split" style="border-radius:0.35em 0em 0em 0.35em; border:2px solid #ccc; border-right:1px solid #ccc;" onclick="window.location.href='/moamore/dashboard.moa'"><span class="text">예산</span></button>
				<button class="btn btn-light btn-icon-split" style="border-radius:0em 0.35em 0.35em 0em; border:2px solid #ccc; border-left:1px solid #ccc;"onclick="window.location.href='/moamore/team/groupList.moa'"><span class="text">커뮤니티</span></button>		
			</div>
			
          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">
          <li class="nav-item mx-5" id="ranking">
                <jsp:include page="./realTimeRankingForMobile.jsp"/>
            </li>
            
			<!-- 로그인 상태일 때 -->
			<c:if test="${sessionScope.memId != null }" >
	            <!-- Nav Item - User Information -->
	            <li class="nav-item dropdown no-arrow">
	              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${sessionScope.memId}</span>
	                <img class="img-profile rounded-circle" src="/moamore/save/${sessionScope.memImg}">
	              </a>
	              <!-- Dropdown - User Information -->
	              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
	                <a class="dropdown-item" href="/moamore/member/updateMember.moa">
	                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
	                  정보수정
	                </a>
	                <div class="dropdown-divider"></div>
	                <a class="dropdown-item" href="/moamore/member/logout.moa" data-toggle="modal" data-target="#logoutModal">
	                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
	                  로그아웃
	                </a>
	              </div>
	            </li>
            </c:if>
            <!-- 비로그인 상태일 때 -->
            <c:if test="${sessionScope.memId == null }">
            	<a href="/moamore/member/loginForm.moa">로그인하세요</a>
            </c:if>

          </ul>

        </nav>
        <!-- End of Topbar -->
        
<div id="loading" style="display: none;justify-content: center;background: rgba(0, 0, 0, .7);align-items: center;position: fixed;top: 0;left: 0;width: 100%;height: 100%;z-index: 1;backdrop-filter: blur(4px);-webkit-backdrop-filter: blur(4px);">
	<div style="text-align:center;">
		<img src="/moamore/resources/img/Loading_2.gif" style="width:60px"/>
		<br/>
		&nbsp;
		<br/>
		<div>
			<h4 style="color:white;">데이터를 불러오고 있습니다.</h4>
		</div>
	</div>
</div>