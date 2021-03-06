<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>예산 설정</title>
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
	}
	.popup {
		padding: 20px;
		background: #fff;
		border-radius: 5px;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, .3);
	}
	p span {
		color:red;
	}
	input[type=number] {
		width:100px;
	}
	.helpSpan {
		color:blue;
		font-size:0.8em;
	}
	#setTB li{
		margin-bottom : 10px;
	}
</style>
<body id="page-top">
<jsp:include page="../sidebar.jsp"/>
	
        <!-- 본문내용 시작 -->
        <div class="container-fluid">

          <!-- 페이지 이름 -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">예산 설정</h1>
            </div>
            
    <div id="popup1">
		<div class="popup">
			<h2>예산 설정하기</h2>
			<hr/>
			<br/>
			<div id="step1" style="transition:.5s;" class="animated--fade-in">
				<p>현재 진행중인 예산이 없습니다!</p>
				<p>예산설정을 완료해주세요.</p>
				<div style="text-align:center;">
					<button id="nextBtn1" class="btn btn-primary">새로운 예산 만들기</button>
				</div>
			</div>
			<form action="/moamore/budget/setBudgetPro.moa" method="post" >
			<div id="step2" style="transition:.5s;display:none;" class="animated--fade-in">
				<ul id="setTB">
					<li>
						<div>
						기간 단위 : 
						<select name="period" id="period">
							<option value="7">7일</option>
							<option value="14">14일</option>
							<option value="30">한달</option>
						</select>
						<br/>
						<span class="helpSpan">(예산 기간을 고르세요.)</span>
						</div>
					</li>
					<jsp:useBean id="now" class="java.util.Date" />
					<fmt:formatDate value="${now}" pattern="dd" var="todayDate" />
					<fmt:formatDate value="${now}" pattern="yyyy년 MM월 dd일" var="today" />
					<li id="startday" style="display:none">월 시작일 : 매월 <input type="number" min="1" max="28" name="firstOfMonth" id="inputSD" value="${todayDate }"/>일<br/><span style="color:blue">0부터 28까지의 값만 입력할 수 있습니다.</span>
						<br/>
					</li>
					<li>
						<div>
						총 예산 : <input type="number" name="totalBudget" id="totalBudget"/>원
						<br/>
						<span class="helpSpan">(총예산 금액을 입력하세요.)</span>
						</div>
					</li>
				</ul>
				<div style="text-align:center">
					<input type="button" value="세부설정 >" id="nextBtn2" class="btn btn-primary"/>
				</div>
			</div>
			<div id="step3" style="transition:.5s;display:none;" class="animated--fade-in">
				<input type="button" value="<이전 단계" id="prevBtn" class="btn btn-secondary" />
				<div style="text-align:right;">
					<input type="button" value="추가" id="insertLine" class="btn btn-primary"/>
				</div>
				
				<table id="detailBudget">
					<tr>
						<td>카테고리</td>
						<td>금액</td>
						<td>비율</td>
						<td>하루 예산</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<select name="category_name" class="category_name" required>
								<option class="none" disabled selected>==카테고리 선택==</option>
								<c:forEach items="${categoryList}" var="i">
									<option value="${i.category_name }">${i.category_name }</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="number" name="amount" min="0" required class="amount"/>
						</td>
						<td>
							<input type="number" class="rate"/>%
						</td>
						<td>
							<input type="number" readonly class="dayAmount"/>원
						</td>
						<!-- 맨위라인은 삭제 안되게
						<td>
							<input type="button" class="deleteBtn" value="삭제"/>
						</td>
						 -->
						
					</tr>
				</table>
			<hr>
			카테고리별 예산 합계 : <strong id="amountSum"></strong>원<br/>
			총 예산 : <strong id="TbudgetPrint"></strong>원
			<br/>
			카테고리별 예산 합계와 총 예산이 동일하도록 설정하세요
			<br/>
			<input type="hidden" value="1" name="isNewBudget" />
			
			<input type="hidden" value="새로운 예산 생성" id="createBudget" class="btn btn-primary"/>
			</div>
		</form>
		</div>
	</div>
            
            
    
	
	
	</div>
	
	<jsp:include page="../footer.jsp"/>
	
<script>
	
	var x = document.getElementById("firstStep");
	var y = document.getElementById("secondStep");
	
	$(document).ready(function(){
		$("#nextBtn1").on("click", function(){
			$("#step1").css("display", "none");
			$("#step2").css("display", "block");
			
		});
		$("#nextBtn2").on("click", function(){
			if($('#totalBudget').val() != 0) {
				$("#step2").css("display", "none");
				$("#step3").css("display", "block");
			} else {
				alert('총 예산 값을 입력하세요!');
			}
		});
		$("#prevBtn").on("click", function(){
			$("#step2").css("display", "block");
			$("#step3").css("display", "none");
		});
		
		//월 시작일을 변경하는 경우
		$('#startday > input').on('change', function(){
			$.ajax({
				type:"post",
				url:"getWarnMessage.moa",
				data: {
					firstOfMonth:$(this).val()
				},
				success: function(data){
					$("#warn").text(data);
				}
				
			})
		});
		
		$('#insertLine').on('click', function(){ //라인 추가
			$('#detailBudget').append('<tr><td><select name="category_name" class="category_name" required><option class="none" disabled selected>==카테고리 선택==</option><c:forEach items="${categoryList}" var="i"><option value="${i.category_name }">${i.category_name }</option></c:forEach></select></td><td><input type="number" name="amount" min="0" required class="amount"/></td><td><input type="number" class="rate"/>%</td><td><input type="number" readonly class="dayAmount"/>원</td><td><input type="button" class="deleteBtn" value="삭제"/></td></tr>');
			optControl();
			
			//삭제버튼 기능
			$('.deleteBtn').on('click', function(){
				//라인 삭제하면 해당 카테고리 사용가능
				var x = $(this).parent().parent().children('option:selected').val();
				$('option').each(function(){
					if($(this).val()==x){
						$(this).prop('hidden', true);
					}
				});
				//삭제
				$(this).parent().parent().remove();
				
				//합계 자동계산
				reSum();
				//모든 none은 선택불가
				$('.none').prop('disabled',true);
				$('.none').prop('hidden',false);
			});
		
			//비율 및 총합 자동계산
			$('.amount').on('keyup', function(){
				reRate($(this));
				reSum();
				calDay($(this));
			});
			//비율 및 총합 자동계산
			$('.rate').on('keyup', function(){
				reCalRate($(this));
				reSum();
				calDay($(this).parent().prev().children('.amount'));
			});
			
			//카테고리명이 change일 때 옵션 속성 변경
			$('.category_name').on('change', function(){
				optControl();
			});
			
		});	//라인추가 function 끝
		
		//총예산, 카테고리별 금액, 주기가 변경될때마다 '하루예산'계산
		function calDay(amount) {
			amount.parent().next().next().children('.dayAmount').val((amount.val()/$('#period').val()).toFixed(1));		
		}
		
		//카테고리명이 change일 때 옵션 속성 변경
		$('.category_name').on('change', function(){
			optControl();
		});
		
		//이미 선택된 카테고리명 옵션은 재선택 못하게 disabled / 선택된게 아니면 disabled false
		function optControl(){
			$('.category_name option').prop('hidden', false);
			$('.category_name').each(function(){
				var x = $(this).children('option:selected').val();
				$('.category_name option').each(function(){
					if($(this).val()==x){
						$(this).prop('hidden', true);
					}
				});
			});
			//모든 none은 선택불가
			$('.none').prop('disabled',true);
			$('.none').prop('hidden',false);
		};
		
		//한달주기일때만 월 시작일 설정
		$('#period').on('change', function(){
			if($('#period').val()=='30') {
				$('#startday').css("display","block");
			} else {
				$('#startday').css("display","none");
			}
		});
		
		//적은 금액 변하면 비율 및 총합 자동계산
		$('.amount').on('keyup', function(){
			reRate($(this));
			reSum();
			calDay($(this));
		});
		//비율 및 총합 자동계산
		$('.rate').on('keyup', function(){
			reCalRate($(this));
			reSum();
			calDay($(this).parent().prev().children('.amount'));
		});
		
		//비율 자동계산
		function reRate(i){
			var rate = i.val()/$('#totalBudget').val()*100;
			i.parent().next().children('.rate').val(rate.toFixed(1));
		};
		//금액 자동계산
		function reCalRate(i){
			var amount = $('#totalBudget').val()*i.val()/100;
			i.parent().prev().children('.amount').val(amount.toFixed());
		};
		
		//총합 자동계산
		function reSum(){
			var sum = 0;
			$('.amount').each(function(){
				if($(this).val()==""){
					sum += 0;
				} else {
					sum += parseInt($(this).val());
				}
			});
			$('#amountSum').text(sum);
			
			//총예산=총합이면 생성버튼 출력
			if(sum==$('#totalBudget').val()){
				$('#createBudget').prop("type","submit");
			} else {
				$('#createBudget').prop("type","hidden");
			}
		};
		
		//총예산 입력시 다음단계에도 자동기입
		$('#totalBudget').on('change',function(){
			var Tbudget = $(this).val();
			$('#TbudgetPrint').text(Tbudget);
			
			//적힌 비율도 다시 계산
			$('.amount').each(function(){
				var rate = $(this).val()/$('#totalBudget').val()*100;
				$(this).parent().next().children('.rate').val(rate.toFixed(1));
				calDay($(this));
			});
			reSum();
		});
		$('#inputSD').on('keyup', function(){
			if($(this).val()>28) {
				$(this).val(28);
			}
		});
		
	});
	
</script>
