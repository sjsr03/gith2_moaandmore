<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>오늘의 예산</title>
</head>
<style>
	#popup1 {
		display: flex;
		justify-content: center;
		align-items: center;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, .7);
		z-index: 1;
		backdrop-filter: blur(4px);
 		-webkit-backdrop-filter: blur(4px);
 		display:none;
	}
	#popup1 * {
		padding:5px;
	}
	.popup {
		padding: 20px;
		background: #fff;
		border-radius: 5px;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, .3);
	}
	input[type=number] {
		width:100px;
	}
	#leftMoneyList, tr, td {
		border:1px solid #ccc;
		border-collapse:collapse;
		padding:2px;
	}
</style>


<jsp:include page="../sidebar.jsp"/>
 <script src="/moamore/vendor/chart.js/Chart.min.js"></script>
 <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">오늘의 예산</h1>
            </div>
            
           	<!-- 첫번째 줄 -->
           	<div class="row">

            <!-- 예산기간 -->
            <div class="col-xl-6 col-md-8 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">예산 기간</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">
                      <fmt:formatDate value="${TBdto.start_day}" pattern="yyyy년 MM월 dd일"/> ~ 
                      <fmt:formatDate value="${TBdto.end_day}" pattern="yyyy년 MM월 dd일"/> (일)</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-calendar-check fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 총예산 금액 -->
            <div class="col-xl-3 col-md-8 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">총예산액</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">
                      <fmt:formatNumber value="${TBdto.budget}" pattern="#,###"/>원</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-won-sign fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 현재 사용 예산 -->
            <div class="col-xl-3 col-md-8 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">현재 사용 예산</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">
                      <fmt:formatNumber value="${TBdto.budget-TBdto.total_budget_current-todaySum}" pattern="#,###"/>원</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-won-sign fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
          </div>
          <!-- 첫줄 끝 -->


			<!-- 두번째줄 -->
			<div class="row">
				<!-- 두번째줄 왼쪽컬럼 -->
				<div class="col-lg-6">
				
				
				
				
				
				
	           	<!-- 오늘의 예산 (간단)-->
	            	<div class="card shadow mb-4" style="display:flex;" id="simple">
		                <div class="card-header py-3 justify-content-between d-sm-flex align-items-center ">
		                  <h6 class="m-0 font-weight-bold text-primary" style="display:inline-block">오늘의 예산</h6>	
		                  
		                  <div style="display:inline-block" >
			                  <button class="btn btn-primary btn-icon-split btn-sm" style="border-radius:0.35em 0em 0em 0.35em;" onclick="simplify()"><span class="text">간단히</span></button>
							  <button class="btn btn-light btn-icon-split btn-sm" style="border-radius:0em 0.35em 0.35em 0em; border:1px solid #ccc;"onclick="specify()"><span class="text">자세히</span></button>
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
            	
            	
            	
            	
            	
            	
            	
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				<!-- 오늘의 예산(자세히) -->
				
					<div class="card shadow mb-4" style="display:none;" id="specific">
		                 <div class="card-header py-3 justify-content-between d-sm-flex align-items-center ">
		                  <h6 class="m-0 font-weight-bold text-primary" style="display:inline-block">오늘의 예산</h6>	
		                  
		                  <div style="display:inline-block" >
			                  <button class="btn btn-light btn-icon-split btn-sm" style="border-radius:0.35em 0em 0em 0.35em; border:1px solid #ccc;" onclick="simplify()"><span class="text">간단히</span></button>
							  <button class="btn btn-primary btn-icon-split btn-sm" style="border-radius:0em 0.35em 0.35em 0em; "onclick="specify()"><span class="text">자세히</span></button>
						  </div>
		                  
		                </div>
		                <div class="card-body">
		                <!-- 
		                	총예산 (${TBdto.budget-TBdto.total_budget_current }/${TBdto.budget })
		                	<div class="progress mb-4">
		                    	<div class="progress-bar" role="progressbar" style="width: ${((TBdto.budget-TBdto.total_budget_current)/TBdto.budget)*100 }%" aria-valuenow="" aria-valuemin="0" aria-valuemax="">${((TBdto.budget-TBdto.total_budget_current)/TBdto.budget)*100 }%</div>
		                 	</div>
	                	<hr/>
						 -->				                 	
	                
                 	<c:forEach items="${todayData}" var="i" varStatus="st">
                 		<div class="card shadow mb-4">
			                <div class="card-header py-3">
			                  <h6 class="m-0 font-weight-bold text-primary">${categories[i.category_no] }
			                  	<c:if test="${i.recommend < i.actual }">
			                  		<span style="color:red;">(초과!)</span>
			                  	</c:if>
		                  	</h6>
			                </div>
			                <div class="card-body">
		                 		
		                 		<!-- 왼쪽컬럼(그래프) -->
		                 		<div class="row" >
			                 		<div class="col-lg-8">
				                 		<div class="chart-bar" style="height:8em"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
						                    <canvas id="myBarChart${i.category_no }" width="657" height="200" class="chartjs-render-monitor" style="display: block; width: 657px; height: 200px;"></canvas>
					                    </div>
				                    </div>
				                    
				                    <div class="col-lg-4">
				                 		하루 권장 지출액 : <br/><strong><fmt:formatNumber value="${i.recommend }" pattern="#,###" /></strong>원 <br/>
				                 		현재 지출액 : <br/><strong><fmt:formatNumber value="${i.actual }" pattern="#,###" /></strong>원 <br/>
				                 		사용 비율 : <br/><strong><fmt:formatNumber value="${i.actual / i.recommend * 100}" pattern="#,###" /></strong>%<br/>
			                 		 </div>
		                    	</div>
	                    	</div>
                 		
                 		<script type="text/javascript">
                 			var ctx = $("#myBarChart${i.category_no}");
							var labelList = ["${categories[i.category_no] }"];
							var dataList = [${i.actual }];
							
							if(${i.recommend} > ${i.actual }) {	//권장액 미만일 시 파란색
								var max = ${i.recommend};
								var backgroundColor="#4e73df";
							    var hoverBackgroundColor= "#2e59d9";
							    var borderColor= "#4e73df";
							} else {	//권장액 초과시 빨간색
								var max = ${i.actual};
								var backgroundColor="#df4e4e";
							    var hoverBackgroundColor= "#d92e2e";
							    var borderColor= "#df4e4e";
							}
						</script>
						<script src="/moamore/js/todayBudgetBar/todayBudgetBar.js"></script>
                 		
                 		</div>
                 		
                 		
                 		
                 		<c:if test="${i.category_no == 0 }" >
                 			<hr/>
                 		</c:if>
                 		
                 	</c:forEach>
		                 	
		                </div>
	                </div>
              </div>
              <!-- 두번째줄 오른쪽컬럼 -->
				<div class="col-lg-6">
					<div class="card shadow mb-4">
		                <div class="card-header py-3">
		                  <h6 class="m-0 font-weight-bold text-primary">예산에서 남은 돈<br/></h6>
		                </div>
		                <div class="card-body">
							총액 : <fmt:formatNumber value="${LMSum}" pattern="#,###"/>원
							<hr/>
		                	<c:forEach items="${leftMoney}" var="i" >
								<div>
									<span>${categories[i.category_no] }</span> : <span class="amount"><fmt:formatNumber value="${i.amount }" pattern="#,###"/></span>원
								</div>
							</c:forEach>
		                 	<hr>
							 <div>
								<button onclick="$('#popup1').css('display','flex')" class="btn btn-primary">전환하기</button>
							</div>
		                </div>
	               </div>

				</div>
			</div>
	<!-- 두번째줄 -->
	</div>	
	
	
	<!-- 남은돈 전환 창 -->
	<div id="popup1">
		<div class="popup">
			<div style="display:inline;text-align:right;"><h2 style="display:inline">남은 돈 전환하기</h2>
			<button onclick="$('#popup1').css('display','none')">X</button></div>
			<form action="/moamore/budget/LeftMoneyTransfer.moa" method="post">
				<div>
					<table id="leftMoneyList">
						<tr>
							<td><input type="checkbox" id="allChk"/></td>
							<td>항목</td>
							<td>잔액</td>
							<td>전환할 금액</td>
							<td>전환 후 남은 금액</td>
						</tr>
					<c:forEach items="${leftMoney}" var="i" >
						<tr>
							<td><input type="checkbox" class="chk" name="category" value="${i.category_no}"/></td>
							<td>${categories[i.category_no] }</td>
							<td><fmt:formatNumber value="${i.amount }" pattern="#,###"/>원</td>
							<td><input type="number" class="inputAmount" name="inputAmount" value="0" max="${i.amount}" min="0"/></td>
							<td><input type="number" readonly class="LeftAfterTrans" value="${i.amount }"/></td>
						</tr>
					</c:forEach>
						<tr>
							<td colspan="5">전환할 총 금액 : <span id="transSum">0</span>원</td>
						</tr>
					</table>
					<div style="display:inline-block">
						<label><input type="radio" name="target_table" value="budget" checked/>현재예산에 재분배</label>
						<br/>
						<label><input type="radio" name="target_table" value="goals" />목표로 보내기</label>
					</div>
					<div style="display:inline-block; width:300px; height:50px; border:1px solid #ccc">
						<div id="subCat">
							<span>재분배할 카테고리</span>
							<select id="targetCat" name="subSel">
								<c:forEach items="${BDdtoList }" var="j">
									<option value=${j.category_no } class="${categories[j.category_no] }">${categories[j.category_no] }</option>
								</c:forEach>
							</select>
						</div>
						<div id="subGoal" style="display:none;">
							<span>목표 선택</span>
							<select id="targetGoal" name="subSel">
								<c:if test="${personalGoals==null && teamGoals==null}" >
									<option disabled>목표가 없습니다</option>
								</c:if>
								<c:if test="${personalGoals!=null}" >
									<optgroup label="==개인목표==">
										<c:forEach items="${personalGoals}" var="j">
											<option value=${j.GOAL_NO } class="${j.SUBJECT } ${j.REST}">${j.SUBJECT }</option>
										</c:forEach>
									</optgroup>
								</c:if>
								<c:if test="${teamGoals!=null}" >
									<optgroup label="==그룹목표==">
										<c:forEach items="${teamGoals}" var="j">
											<option value=${j.GOAL_NO } class="${j.SUBJECT } ${j.REST}">${j.SUBJECT }</option>
											<!-- <input type="hidden" value="${j.REST}"/> -->
										</c:forEach>
									</optgroup>
								</c:if>
							</select>
						</div>
					</div>
					<div>
						<input type="button" id="trans" value="전환하기">
					</div>
				</div>
				</form>
		</div>
	</div>
	
	
	<jsp:include page="../footer.jsp" />
  
<script>
var personalGoals = [];
var teamGoals=[];


	$(document).ready(function(){
		//남은 돈 총합 계산
		//calSumFirst();
		
		//분배금액 입력될때마다
		$('.inputAmount').on('keyup', function(){
			//총금액을 넘지 않는지, 음수는 아닌지 계산
			testAmount($(this));
			
			//총합 계산
			calSumTrans();
			//남은금액 계산
			calRest($(this));
		});
		
		//모두 체크하기
		$("#allChk").on('change', function(){
			if($(this).is(':checked')==true) {
				$('.chk').each(function(){
					$(this).attr('checked', true);
				});
			} else {
				$('.chk').each(function(){
					$(this).attr('checked', false);
				});
			}
			
			calSumTrans();
		});
		
		
		//체크될때마다
		$('.chk').on('change',function(){
			//총합 계산
			calSumTrans();
		});
		
		
		
		//타겟테이블이 선택될때마다
		$('input:radio').on('change', function(){
			//하위 셀렉트박스 출력
			printSubSelect();
		});
		
		
		
		
		//전환하기 누르면 확인창 출력 후 submit
		$('#trans').on('click', function(){
			//0원인지부터 확인
			if($('#transSum').text() == "0") {
				alert('전환할 금액을 입력하세요');
				return;
			}
			
			var target_table = $('input:radio:checked').val();
			var subSelect = "<";
			if(target_table == "budget") {
				subSelect += $('#targetCat option:selected').prop('class');
				subSelect += "> 카테고리";
			} else {
				subSelect += $('#targetGoal option:selected').prop('class').split(' ')[0];
				subSelect += "> 목표";
			}
			var ans = confirm("총 " + $('#transSum').text() + "원을 " + subSelect + "로 전환합니다");			
			if(ans == true) {
				if($('input:radio:checked').val()=="goals") {
					var goal_no = $("#targetGoal option:selected").val();
					var rest = $('#targetGoal option:selected').prop('class').split(' ')[1];
					
					if($('#transSum').text() > rest) {
						alert(subSelect + "달성까지 " + rest + "원 남았습니다.\n달성액을 초과할 수 없습니다.");
						return;
					}
				}
				
				$('.chk').each(function(){	//체크안된 항목의 입력값은 전달되지 않도록 하는 작업
					if($(this).is(':checked')==false) {
						$(this).parent().next().next().next().children('.inputAmount').prop('name', '');
					}
				});
				$('#trans').attr('type', 'submit');
			}
		});
	});
	
	
	
	//남은돈 총합 계산하기(페이지 불러올 때 한 번)
	function calSumFirst() {
		var sum = 0;
		
		$('.amount').each(function(){
			sum += parseInt($(this).text());
			alert(sum);
		});
		
		$('#totalLeftMoney').text((sum+"").replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	}
	
	
	//보낼 타겟 체크할때마다 하위 셀렉트박스 출력
	function printSubSelect() {
		var target = $('input:radio:checked').val();
		
		if(target == 'budget') {
			$('#subGoal').css('display','none');
			$('#subCat').css('display','flex');
		} else {
			$('#subCat').css('display','none');
			$('#subGoal').css('display','flex');
		}
	}
	
	
	//전환할 총 금액 합산
	function calSumTrans() {
		var sum = 0;
		//체크된 것들만
		$('.chk').each(function(){
			if($(this).is(':checked')) {
				var inputAmount = $(this).parent().next().next().next().children('.inputAmount').val();
				if(inputAmount=="") {
					sum += 0;
				} else {
					sum += parseInt(inputAmount);
				}
			}
		});
		
		$('#transSum').text(sum);
	}
	
	//입력된 값이 최대값을 넘지 않는지, 음수는 아닌지 계산
	function testAmount(inputAmount) {
		var Ori = parseInt($(inputAmount).prop("max"));
		if(Ori < inputAmount.val()) {
			alert("잔액을 초과할 수 없습니다.");
			
			var num = Math.floor(inputAmount.val()/10);
			while(Ori < num) {
				num = Math.floor(num/10);
			}
			inputAmount.val(num);
		}
		
		if(inputAmount.val() < 0) {
			alert("음수는 입력할 수 없습니다.");
			inputAmount.val(0);
		}
		
	}
	
	
	
	
	
	//전환 후 남은 금액 자동계산
	function calRest(inputAmount) {
		var rest = 0;
		var Ori = parseInt($(inputAmount).prop("max"));
		if(inputAmount.val()=="") {
			rest = Ori;
		} else {
			rest = Ori - parseInt(inputAmount.val());
		}
		inputAmount.parent().next().children('.LeftAfterTrans').val(rest);
	}
	
	
	
	
	//간단히 보기
	function simplify() {
		$("#specific").css('display', 'none');
		$("#simple").css('display','flex');
	}
	//자세히보기
	function specify() {
		$("#specific").css('display', 'flex');
		$("#simple").css('display','none');
		
	}
	
</script>
