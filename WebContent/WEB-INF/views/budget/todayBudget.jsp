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
<meta name="description" content="">
<meta name="author" content="">
<!-- 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<title>오늘의 예산</title>
	<!-- Custom fonts for this template-->
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	
	<!-- Custom styles for this template-->
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"> 
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
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
		width:70px;
	}
	#leftMoneyList, tr, td {
		border:1px solid #ccc;
		border-collapse:collapse;
		padding:2px;
	}
</style>


<body id="page-top">
<jsp:include page="../sidebar.jsp"/>
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
            <div class="col-xl-6 col-md-8 mb-4">
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
            
          </div>
          <!-- 첫줄 끝 -->


			<!-- 두번째줄 -->
			<div class="row">
				
	
	
	<div style="border:1px solid black; width:300px;">
		예산에서 남은 돈<br/>
		<div>
			총액 : <span id="totalLeftMoney"></span>원
		</div>
		<hr>
		<div>
			<c:forEach items="${leftMoney}" var="i" >
				<div>
					<span>${categories[i.category_no] }</span> : <span class="amount">${i.amount }</span>원
				</div>
			</c:forEach>
		</div>
		<div>
			<button onclick="$('#popup1').css('display','flex')">전환하기</button>
		</div>
	</div>
	
	
	</div>
	<!-- 두번째줄 -->
	
	
	
	<!-- 남은돈 전환 창 -->
	<div id="popup1">
		<div class="popup">
			<div style="display:inline;text-align:right;"><h2 style="display:inline">남은 돈 전환하기</h2>
			<button onclick="$('#popup1').css('display','none')">X</button></div>
			<form action="/moamore/budget/LeftMoneyTransfer.moa" method="post">
				<div>
					<table id="leftMoneyList">
						<tr>
							<td><!-- 체크박스 -->&nbsp;</td>
							<td>항목</td>
							<td>잔액</td>
							<td>전환할 금액</td>
							<td>전환 후 남은 금액</td>
						</tr>
					<c:forEach items="${leftMoney}" var="i" >
						<tr>
							<td><input type="checkbox" class="chk" name="category" value="${i.category_no}"/></td>
							<td>${categories[i.category_no] }</td>
							<td>${i.amount }</td>
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
							재분배할 카테고리
							<select id="targetCat" name="subSel">
								<c:forEach items="${BDdtoList }" var="j">
									<option value=${j.category_no } class="${categories[j.category_no] }">${categories[j.category_no] }</option>
								</c:forEach>
							</select>
						</div>
						<div id="subGoal" style="display:none;">
							목표 선택
							<select id="targetGoal" name="subSel">
								<c:forEach items="${goals }" var="j">
									<option value=${j.goal_no } class="${j.subject }">${j.subject }</option>
								</c:forEach>
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
	
	</div>
	
	<jsp:include page="../footer.jsp" />
	
	<!-- Bootstrap core JavaScript-->
	  <script src="/moamore/vendor/jquery/jquery.min.js"></script>
	  <script src="/moamore/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  
  
</body>
<script>
	$(document).ready(function(){
		//남은 돈 총합 계산
		calSumFirst();

		
		//분배금액 입력될때마다
		$('.inputAmount').on('keyup', function(){
			//총금액을 넘지 않는지, 음수는 아닌지 계산
			testAmount($(this));
			
			//총합 계산
			calSumTrans();
			//남은금액 계산
			calRest($(this));
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
				subSelect += $('#targetCat').children(':selected').attr('class');
				subSelect += "> 카테고리";
			} else {
				subSelect += $('#targetGoal').children(':selected').attr('class');
				subSelect += "> 목표";
			}
			var ans = confirm("총 " + $('#transSum').text() + "원을 " + subSelect + "로 전환합니다");			
			if(ans == true) {
				$('.chk').each(function(){
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
		});
		
		$('#totalLeftMoney').text(sum);
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
		var Ori = parseInt(inputAmount.parent().prev().text());
		if(Ori < inputAmount.val()) {
			alert("잔액을 초과할 수 없습니다.");
			var num = Math.floor(inputAmount.val()/10);
			inputAmount.val(num);
		}
		
	}
	
	
	
	
	
	//전환 후 남은 금액 자동계산
	function calRest(inputAmount) {
		var rest = 0;
		var Ori = parseInt(inputAmount.parent().prev().text());
		if(inputAmount.val()=="") {
			rest = Ori;
		} else {
			rest = Ori - parseInt(inputAmount.val());
		}
		inputAmount.parent().next().children('.LeftAfterTrans').val(rest);
	}
</script>
</html>