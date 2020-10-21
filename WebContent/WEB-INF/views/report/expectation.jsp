<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>예산 데이터 분석</title>
</head>
<script src="/moamore/vendor/chart.js/Chart.min.js"></script>
<jsp:include page="../sidebar.jsp"/>


        <!-- 본문내용 시작 -->
        <div class="container-fluid">

          <!-- 페이지 이름 -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">예산 데이터 분석</h1>
            </div>
            
            <c:if test="${reject == 1 }" >
            	예산 데이터가 충분하지 않습니다!<br/>
            	데이터 분석에는 최소 2일 이상의 정보가 필요합니다.
            </c:if>
            <c:if test="${reject == 0 }" >
            
            
            
           	<!-- 첫번째 줄 -->
            <div class="row">


			<div class="col-xl-12 col-lg-12">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">예산 데이터</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <div class="chart-area"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
                    <canvas id="myAreaChart" style="display: block; width: 646px; height: 320px;" width="646" height="320" class="chartjs-render-monitor"></canvas>
                  </div>
                </div>
              </div>
             </div>
              <script type="text/javascript">
              	var ctx = $("#myAreaChart");
              	var Datelabels = ${expectation.outcomeDataX};
              	var outcomeData = {
              	      label: "지출액",
              	      lineTension: 0.3,
              	      backgroundColor: "rgba(78, 115, 223, 0.05)",
              	      borderColor: "rgba(78, 115, 223, 1)",
              	      pointRadius: 3,
              	      pointBackgroundColor: "rgba(78, 115, 223, 1)",
              	      pointBorderColor: "rgba(78, 115, 223, 1)",
              	      pointHoverRadius: 3,
              	      pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
              	      pointHoverBorderColor: "rgba(78, 115, 223, 1)",
              	      pointHitRadius: 10,
              	      pointBorderWidth: 2,
              	      data: ${expectation.outcomeDataY},
              	    };
              	
              </script>
              <script src="/moamore/js/report/expectation.js"></script>
              

            </div>
            
            
           	<!-- 두번째 줄 -->
            <div class="row">
				이번 예산 지출 추정액 : ${expectation.predictAmount}원
            </div>

         </c:if>
        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="../footer.jsp"/>
