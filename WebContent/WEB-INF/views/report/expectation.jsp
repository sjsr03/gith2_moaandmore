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
                	<c:if test="${goalsList != null}">
                	<label class="text-primary"><input type="checkbox" id="outcomeChk" checked/>지출 데이터</label>&nbsp;&nbsp;
	                	<label class="text-warning"><input type="checkbox" id="goalChk"/>목표 달성 데이터</label>&nbsp;
	                	<select id="goalsList" style="display:none;">
	                		<option selected disabled>목표명 선택</option>
	                		<c:forEach var="i" items="${goalsList}">
	                			<c:forEach var="k" items="${i}">
		                			<option value="${k.key}">${k.value}</option>
	                			</c:forEach>
	                		</c:forEach>
	                	</select>
                	</c:if>
                	
                	
                	<script>
                		$("#goalChk").on('change', function(){
                			if($(this).is(":checked")==true) {
                				$("#goalsList").css("display","inline-block");
                			} else {
                				$("#goalsList").css("display","none");
                				$("#goalsList option:eq(0)").prop("selected", true);
                			}
                		});
                		function selectGoal() {
                			$.ajax({
                				url:"selectGoal.moa",
                				data:{
                					"goal_no" : $("#goalsList").val(),
                					"id" : ${sessionScope.memId}
                				},
                				success:function(data){
                					if(data==null) {
                						alert("표시할 데이터가 없습니다.");
                					} else {
	                					var goalX = data.get("goalX");
	                					var goalY = data.get("goalY");
                						var predictedDate = data.get("predictedDate");
                						
                						var goalData = {
                			              	      label: "목표달성액",
                			              	      lineTension: 0.3,
                			              	      showLine: true,
                			              	      backgroundColor: "rgba(246, 194, 62, 0.05)",
                			              	      borderColor: "rgba(246, 194, 62, 1)",
                			              	      pointRadius: 3,
                			              	      pointBackgroundColor: "rgba(246, 194, 62, 1)",
                			              	      pointBorderColor: "rgba(246, 194, 62, 1)",
                			              	      pointHoverRadius: 3,
                			              	      pointHoverBackgroundColor: "rgba(246, 194, 62, 1)",
                			              	      pointHoverBorderColor: "rgba(246, 194, 62, 1)",
                			              	      pointHitRadius: 10,
                			              	      pointBorderWidth: 2,
                			              	      data: goalY,
                			              	    };
                					}
                				},
                				error:function(){
                					alert("오류!");
                				},
                			});
                		}
                		
                	</script>
                	
                	
                  <div class="chart-area"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
                    <canvas id="myAreaChart" style="display: block; width: 646px; height: 320px;" width="646" height="320" class="chartjs-render-monitor"></canvas>
                  </div>
                </div>
              </div>
             </div>
              <script type="text/javascript">
              	var ctx = $("#myAreaChart");
              	var Datelabels = ${expectOutcome.outcomeDataX};
              	var outcomeDataY = ${expectOutcome.outcomeDataY};
              	var goalDataY;
              	var goalDataX;
              	
              	
              	var outcomeData = {
              	      label: "지출액",
              	      lineTension: 0.3,
              	      showLine: true,
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
              	      data: outcomeDataY,
              	    };
              	
              </script>
              <script src="/moamore/js/report/expectation.js"></script>
              

            </div>
            
            
           	<!-- 두번째 줄 -->
            <div class="row">
            	<div class="col-xl-6 col-lg-6">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">이번 예산 지출 추정액</h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
			            <h1><span class="font-weight-bold text-primary"><fmt:formatNumber pattern="#,###" value="${expectOutcome.predictAmount}"/></span>원</h1>
			            
	                </div>
                </div>
              </div>	
              
            	<div class="col-xl-6 col-lg-6">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">목표명 : <span id="goal_subject"></span></h6>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	지금 속도로는 
			            <h1><span class="font-weight-bold text-primary" id="predictDate"></span></h1>
			            에 목표를 달성할 수 있어요
			            
	                </div>
                </div>
              </div>	
            </div>
            

         </c:if>
        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="../footer.jsp"/>
