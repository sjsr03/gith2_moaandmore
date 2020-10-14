<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<!-- Custom styles for this template-->
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">  
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body>
	<!-- 보고서 첫줄 -->
            <div class="container-fluid row">
            	<!-- 일별 예산 소진율 -->
				<div class="col-xl-8 col-lg-7">
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