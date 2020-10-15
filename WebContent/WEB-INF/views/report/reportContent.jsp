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
<!-- Custom fonts for this template-->
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	
	<!-- Custom styles for this template-->
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">  
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
	
</head>
<body>
	 <div class="container-fluid">
		<!-- 보고서 첫줄 -->
			<div class="row">
	           	<!-- 일별 예산 소진율 -->
				<div class="col-xl-9 col-lg-7">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">일별 예산 소진율</h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                  <div class="chart-bar"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
	                    <canvas id="myBarChart" width="657" height="320" class="chartjs-render-monitor" style="display: block; width: 657px; height: 320px;"></canvas>
	                  </div>
	                </div>
	              </div>
	            </div>
	           	<!-- 예산 분석 -->
				<div class="col-xl-3 col-lg-7">
	              <div class="card shadow mb-4">
	              	<!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">예산 분석</h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	예산 기간 : <br/>
	             		<fmt:formatDate pattern="yyyy.MM.dd" value="${TBdto.start_day}"/> ~ 
		                <fmt:formatDate pattern="yyyy.MM.dd" value="${TBdto.end_day}"/>
	                	<br/>
	                	<hr/>
	                	총 예산 : <br/><Strong><fmt:formatNumber value="${TBdto.budget }" pattern="#,###" />원 </Strong><br/>
	                	실제 총 예산 지출 : <br/><Strong><fmt:formatNumber value="${Tsum}" pattern="#,###" />원 </Strong><br/>
	                	<br/>
	                	하루 권장 지출 : <br/><Strong><fmt:formatNumber value="${daily}" pattern="#,###" />원 </Strong><br/>
	                	실제 평균 지출 : <br/><Strong><fmt:formatNumber value="${dailyAvg}" pattern="#,###" />원</Strong><br/>
	                </div>
	              </div>
	            </div>
            </div>
            <!-- 첫줄 끝 -->
            
            <!-- 두번째 줄 -->
            <div class="row">
            	<!-- 지출금액 Top3 -->
				<div class="col-xl-6 col-lg-7">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">지출금액 TOP3</h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	<table class="table table-bordered">
	                	<c:forEach items="${amountList}" var="it" varStatus="st">
							<tr>
								<td>${st.index+1 }</td>
								<td>${it.CONTENT }</td>
								<td>${it.CNT}건</td>
								<td><fmt:formatNumber pattern="#,###" value="${it.TSUM}"/>원</td>
							</tr>	                		
	                	</c:forEach>
	                	</table>
	                </div>
	              </div>
	            </div>
	            
            	<!-- 지출건수 Top3 -->
				<div class="col-xl-6 col-lg-7">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">지출건수 TOP3</h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	<table class="table table-bordered">
	                	<c:forEach items="${countList}" var="it" varStatus="st">
							<tr>
								<td><span class="paginate_button page-item active" >${st.index+1 }</span></td>
								<td>${it.CONTENT }</td>
								<td>${it.CNT}건</td>
								<td><fmt:formatNumber pattern="#,###" value="${it.TSUM}"/>원</td>
							</tr>	                		
	                	</c:forEach>
	                	</table>
	                </div>
	              </div>
	            </div>
            </div>
   </div>
</body>
 <!-- Bootstrap core JavaScript -->
  <script src="/moamore/vendor/jquery/jquery.min.js"></script>
  <script src="/moamore/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="/moamore/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="/moamore/vendor/chart.js/Chart.min.js"></script>
  <script src="/moamore/js/sb-admin-2.min.js"></script>
  <script type="text/javascript">
	  	var ctx = document.getElementById("myBarChart");
		var labelList = ${labelList};
		var dataList = ${dataList};
 </script>
  <script src="/moamore/js/report/dailyBar.js"></script>
 
</html>