<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>대시보드</title>
</head>
<jsp:include page="sidebar.jsp"/>
<script src="/moamore/vendor/chart.js/Chart.min.js"></script>
        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">대시보드</h1>
            </div>
            
           	<!-- 첫번째 줄 -->
            <div class="row" style="display:flex;">
            
            	<!-- 오늘의 예산 -->
            	<div class="col-xl-6 col-lg-6">
	            	<div class="card shadow mb-4" style="height:96%">
		               <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
		                  <h6 class="m-0 font-weight-bold text-primary">오늘의 예산</h6>
		                  <div class="dropdown no-arrow">
		                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
		                    </a>
		                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
		                      <a class="dropdown-item" href="/moamore/budget/todayBudget.moa">자세히 보기</a>
		                    </div>
		                  </div>
		                </div>
		                <div class="card-body">
		                	<c:forEach items="${todayData }" var="i">
		                		<c:if test="${i.category_no != 0 }" >
		                		<div class="row">
			                		<div class="col-lg-9">
					                  <h4 class="small font-weight-bold">${categories[i.category_no] }<span class="float-right"><fmt:formatNumber value="${i.actual / i.recommend * 100}" pattern="#,###" />%</span></h4>
					                  <div class="progress mb-4">
					                    <div class="progress-bar 
						                    <c:if test="${(i.actual / i.recommend * 100) > 100}">
						                    	 bg-danger
						                    </c:if>"
					                    role="progressbar" style="width: ${i.actual / i.recommend * 100}%" aria-valuenow="${i.actual / i.recommend * 100}" aria-valuemin="0" aria-valuemax="100"></div>
					                  </div>
				                  	</div>
				                  	<div class="col-lg-3" >
				                  		<h4 class="small" style="margin-top:0.8em">총 <strong><fmt:formatNumber value="${i.recommend }" pattern="#,###" /></strong>원 중<br/>
				                  		<strong><fmt:formatNumber value="${i.actual }" pattern="#,###" /></strong>원 사용</h4>
				                  	</div>
				                  </div>
				                  <c:if test="${i.category_no == 0 }">
				                  	<hr/>
				                  </c:if>
			                  </c:if>
		                	</c:forEach>
		                
		                </div>
	             	 </div>
	             	 </div>
            	
            	
            	
            	
            	
            	
            	
            	<!-- 목표 -->
            	<div class="col-xl-6 col-lg-6">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">나의 목표</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/goals/myGoalList.moa">자세히 보기</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	<div class="row">
	                		<!-- 개인목표 -->
	                		<div class="col-xl-6 col-lg-6">
	                			<div class="card shadow mb-4">
	                				<div class="card-header py-3">
	                					<h6 class="m-0 font-weight-bold text-primary">개인 목표</h6>
	                				</div>
	                				<div class="card-body" style="text-align:center;">
	                					<c:if test="${MPgoal == null }">
	                						<h6>진행중인 개인목표가 없습니다.</h6>
	                					</c:if>
	                					<c:if test="${MPgoal != null }">
	                					<h6 class="m-0 font-weight-bold">${MPgoal.SUBJECT}</h6>
	                					<div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
						                    <canvas id="MPgoal" width="150" height="100" class="chartjs-render-monitor" style="display: block; width: 8vw; height: 8vh;"></canvas>
					                    </div>
					                    달성액 : <span class="font-weight-bold text-primary"><fmt:formatNumber value="${MPgoal.SAVING }" pattern="#,###" /></span>원
					                    <BR/>
					                    목표액 : <span class="font-weight-bold text-primary"><fmt:formatNumber value="${MPgoal.TARGET_MONEY }" pattern="#,###" /></span>원
					                    
					                    <script type="text/javascript">
					                   	 	var ctx = $("#MPgoal");
					                    	var dataSet = [ ${MPgoal.SAVING} , ${MPgoal.TARGET_MONEY - MPgoal.SAVING} ];
					                    	var ratio = ${MPgoal.RATE} ;
					                    </script>
					                    <script src="/moamore/js/dashboard/MPgoal-pie.js"></script>
					                    </c:if>
	                				</div>
	                			</div>
	                		</div>
	                		<!-- 그룹목표 -->
	                		<div class="col-xl-6 col-lg-6">
	                			<div class="card shadow mb-4">
	                				<div class="card-header py-3">
	                					<h6 class="m-0 font-weight-bold text-primary">그룹 목표</h6>
	                				</div>
	                				<div class="card-body" style="text-align:center;">
	                					<c:if test="${MTgoal == null }">
	                						<h6>진행중인 그룹목표가 없습니다.</h6>
	                					</c:if>
	                					<c:if test="${MTgoal != null }">
	                					<h6 class="m-0 font-weight-bold">${MTgoal.SUBJECT}</h6>
	                					<div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
						                    <canvas id="MTgoal" width="150" height="100" class="chartjs-render-monitor" style="display: block; width: 8vw; height: 8vh;"></canvas>
					                    </div>
					                    달성액 : <span class="font-weight-bold text-primary"><fmt:formatNumber value="${MTgoal.SAVING }" pattern="#,###" /></span>원
					                    <BR/>
					                    목표액 : <span class="font-weight-bold text-primary"><fmt:formatNumber value="${MTgoal.TARGET_MONEY }" pattern="#,###" /></span>원
					                    
					                    <script type="text/javascript">
					                   	 	var ctx = $("#MTgoal");
					                    	var dataSet = [ ${MTgoal.SAVING} , ${MTgoal.TARGET_MONEY - MTgoal.SAVING} ];
					                    	var ratio = ${MTgoal.RATE} ;
					                    </script>
					                    <script src="/moamore/js/dashboard/MTgoal-pie.js"></script>
					                    </c:if>
	                				</div>
	                			</div>
	                		</div>
                		</div>
	                </div>
	              </div>
	            </div>
	                <!-- 목표카드 끝 -->
            </div>
	            
	            
	            
	            <!-- 두번째줄 -->
	            <div class="row">
	            <!-- 총예산 -->
	            <div class="col-xl-4 col-lg-4">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">총 예산</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/budget/setBudget.moa">예산 설정</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body" style="text-align:center;">
			            <div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
		                    <canvas id="myPieChart" width="327" height="245" class="chartjs-render-monitor" style="display: block; width: 327px; height: 245px;"></canvas>
	                    </div>
	                    남은 예산 : <span class="font-weight-bold text-primary"><fmt:formatNumber value="${TBdto.total_budget_current+todaySum}" pattern="#,###" /></span>원
	                    
	                    <script type="text/javascript">
	                   	 	var ctx = $("#myPieChart");
	                    	var dataSet = [ ${TBdto.budget - TBdto.total_budget_current - todaySum} , ${TBdto.total_budget_current+todaySum}];
	                    	var ratio = ${100-(((TBdto.total_budget_current + todaySum)/TBdto.budget)*100)} ;
	                    </script>
	                    <script src="/moamore/js/dashboard/totalbudget-pie.js"></script>
                  </div>
                  </div>
                 </div><!-- 총예산 카드 끝 -->
                 
	            <!-- 현재 하루 지출 -->
	            <div class="col-xl-4 col-lg-4">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">현재 하루 지출</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/budget/todayBudget.moa">오늘의 예산</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body" style="text-align:center;">
				            <div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
			                    <canvas id="myHalfPieChart" width="200" height="100" class="chartjs-render-monitor" style="display: block; width: 10vw; height: 10vh;"></canvas>
		                    </div>
	                    	오늘 <span class="font-weight-bold text-primary"><fmt:formatNumber value="${todayData[0].actual}" pattern="#,###" /></span>원 사용
	                    
	                    <script type="text/javascript">
	                   	 	var ctx = $("#myHalfPieChart");
	                    	var dataSet = [ ${todayData[0].actual} , ${todayData[0].recommend-todayData[0].actual} ];
	                   	 	if((${todayData[0].recommend-todayData[0].actual}) < 0) {
		                    	var dataSet = [ ${todayData[0].actual} , 0 ];
	                   	 	} 
	                    	var ratio = ${(todayData[0].actual/todayData[0].recommend)*100} ;
	                    	if (ratio < 80) {
	                    		var status = "여유";
	                    		var color = "#4e73df";
	                    		var hoverBackgroundColor = "#2e59d9";
	                    	} else if (ratio < 100) {
	                    		var status = "주의";
	                    		var color = "#dfc44e";
	                    		var hoverBackgroundColor = "#d9c22e";
	                    	} else {
	                    		var status = "초과";	
	                    		var color = "#df4e4e";
	                    		var hoverBackgroundColor = "#d92e2e";
	                    	}
	                    </script>
	                    <script src="/moamore/js/dashboard/todaybudget-halfpie.js"></script>
                  </div>
                  </div>
                 </div><!-- 현재 하루 지출 카드 끝 -->
                 
                 
	            <!-- 총 모은 금액 -->
	            <div class="col-xl-4 col-lg-4">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">총 모은 금액</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/budget/todayBudget.moa">오늘의 예산</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body" style="text-align:center;">
	                	<br/>
	                	<br/>
	                		<h1>현재까지<br/><span class="font-weight-bold text-primary"><fmt:formatNumber pattern="#,###" value="${LMsum}" /></span>원<br/>모았습니다!</h1>
	                	<br/>
	                	<br/>
                  </div>
                  </div>
                 </div><!-- 총 모은 금액 카드 끝 -->
	            
            
            
            </div><!-- 두번째줄 끝 -->

          </div>

        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="footer.jsp"/>
