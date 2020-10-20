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
 		display:none;
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
</style>
<body id="page-top">
<jsp:include page="../sidebar.jsp"/>
	
        <!-- 본문내용 시작 -->
        <div class="container-fluid">

          <!-- 페이지 이름 -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">예산 설정 수정</h1>
            </div>
            

	<!-- 예산 재설정 경고 안내 -->
	<div id="popup1">
		<div class="popup">
			<h2>예산 주기 변경</h2>
			<div>
				<p>예산 주기를 변경할 시 기존의 예산은 종료되며, 오늘부터 새로운 예산이 시작됩니다.<br/>주기를 변경하시겠습니까?</p>
			</div>
			<button id="PeChOk">확인</button>
			<button id="PeChCancel">취소</button>
		</div>
	</div>

	
	<form action="/moamore/budget/setBudgetPro.moa" method="post" >
           	<!-- 첫번째 줄 -->
            <div class="row" style="overflow:hidden;">
            	
	
	<!-- 폼 시작 -->
		<div class="col-lg-12" style="transition: .5s;" id="firstStep">
		<div class="card shadow lg-12" >
            <div class="card-body">
				<ul>
					<li>
						총 예산 : <input type="number" name="totalBudget" id="totalBudget" value="${currentTBudget.budget }"/>원
					</li>
					<li>
						기간 단위 : 
						<select name="period" id="period">
							<option value="7" >7일</option>
							<option value="14" <c:if test="${currentTBudget.period==14}">selected</c:if>>14일</option>
							<option value="30" <c:if test="${currentTBudget.period==30}">selected</c:if>>한달</option>
						</select>
					</li>
					<li id="startday" style="display:<c:if test="${currentTBudget.period==30}">block</c:if><c:if test="${currentTBudget.period!=30}">none</c:if>">월 시작일 : 매월 <input type="number" min="1" max="28" name="firstOfMonth" value="1"/>일
					</li>
				</ul>
				<input type="button" value="세부설정 >" onclick="nextStep()"/>
			</div>
		</div>
		</div>
		<div class="col-lg-12" style="transition: .5s;" id="secondStep">
		<div class="card shadow lg-12" >
        	<div class="card-body" >
		
				<input type="button" value="<이전단계" onclick="preStep()"/>
				<input type="button" value="추가" id="insertLine"/>
				
				<table id="detailBudget">
					<tr>
						<td>카테고리</td>
						<td>금액</td>
						<td>비율</td>
						<td>하루 예산</td>
						<td>&nbsp;</td>
					</tr>
					
					<c:forEach var="k" items="${detailList}">
						<tr>
						<td>
							<select name="category_name" class="category_name" required>
								<option class="none" disabled>==카테고리 선택==</option>
								<c:forEach items="${categoryList}" var="i">
									<option value="${i.category_name }" <c:if test="${i.category_no == k.category_no }">selected</c:if>>${i.category_name }</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="number" name="amount" min="0" required class="amount" value="${k.category_budget}"/>
						</td>
						<td>
							<input type="number" readonly class="rate"/>%
						</td>
						<td>
							<input type="number" readonly class="dayAmount"/>원
						</td>
						<c:if test="${i != 1 }">
							<td>
								<input type="button" class="deleteBtn" value="삭제"/>
							</td>
						</c:if>
						
					</tr>
					</c:forEach>
					
					
				</table>
			<hr>
			카테고리별 예산 합계 : <strong id="amountSum"></strong>원<br/>
			총 예산 : <strong id="TbudgetPrint"></strong>원
			<br/>
			카테고리별 예산 합계와 총 예산이 동일하도록 설정하세요
			<br/>
			<input type="hidden" value="예산 설정 수정" id="createBudget" />
		</div>
	<input type="hidden" name="budget_no" value="${currentTBudget.budget_no }" />
	<input type="hidden" name="isNewBudget" id="isNewBudget" value="0" />
	
	<div><span id="warn" style="color:red; display:none">주기를 변경하셨습니다. 새로운 예산이 생성됩니다.</span></div>
	</div>
	
	</div>
	</div>
	
	</form>
	<div style="display:none;"><h1>예산 수정하면 기존 기록 반영해서 상태 보여주는 부분 아직 안함 <br/>(수입지출 기록 완료되면 수정할 것)</h1></div>
	</div>
	
	<jsp:include page="../footer.jsp"/>
	
<script>
	
	var x = document.getElementById("firstStep");
	var y = document.getElementById("secondStep");
	
	$(document).ready(function(){
		
		//페이지 불러오면 미리 계산되어있게
		$('#TbudgetPrint').text($('#totalBudget').val());
		$('.amount').each(function(){
			reRate($(this));
			calDay($(this));
		});
		reSum();
		
		//period가 변경되면 경고창 출력
		$('#period').on('change', function(){
			$('#popup1').css({display: 'flex'});
		});
		
		//period 변경을 취소하면 다시 원래값으로
		$('#PeChCancel').on('click', function(){
			$('#popup1').css('display','none');
			$('#period').children('option[value=${currentTBudget.period}]').prop('selected',true);
			setStartDay(); //월 시작일 출력여부 결정
		});
		
		//period 변경에서 확인을 누르면 값 더 바꿔도 경고창 다시 안뜨게(대신 경고문 띄워져있음)
		$('#PeChOk').on('click', function(){
			$('.amount').each(function(){
				calDay($(this));
			});
			
			$('#popup1').remove();
			$('#warn').css('display', 'flex');
			
				
			//경고문 출력 여부
			$('#period').on('change', function(){
				$('.amount').each(function(){
					calDay($(this));
				});
				
				
				if($('#period').val() != ${currentTBudget.period}) {
					$('#warn').css('display', 'flex');
					$('#isNewBudget').val(1);
				} else {
					$('#warn').css('display', 'none');
					$('#isNewBudget').val(0);
				}
				
			});
		});
		
		
		
		$('#insertLine').on('click', function(){ //라인 추가
			$('#detailBudget').append('<tr><td><select name="category_name" class="category_name" required><option class="none" disabled selected>==카테고리 선택==</option><c:forEach items="${categoryList}" var="i"><option value="${i.category_name }">${i.category_name }</option></c:forEach></select></td><td><input type="number" name="amount" min="0" required class="amount"/></td><td><input type="number" readonly class="rate"/>%</td><td><input type="number" readonly class="dayAmount"/>원</td><td><input type="button" class="deleteBtn" value="삭제"/></td></tr>');
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
		
		//비율 및 총합 자동계산
		$('.amount').on('keyup', function(){
			reRate($(this));
			calDay($(this));
			reSum();
		});
		
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
			setStartDay();
		});
		//월 시작일 설정 출력 여부
		function setStartDay(){
			if($('#period').val()=='30') {
				$('#startday').css("display","block");
			} else {
				$('#startday').css("display","none");
			}
		};
		//적은 금액 변하면 비율 및 총합 자동계산
		$('.amount').on('keyup', function(){
			reRate($(this));
			reSum();
		});
		
		//비율 자동계산
		function reRate(i){
			var rate = i.val()/$('#totalBudget').val()*100;
			i.parent().next().children('.rate').val(rate.toFixed(1));
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
			
			//적힌 비율과 합계도 다시 계산
			$('.amount').each(function(){
				reRate($(this));
				calDay($(this));
			});
			reSum();
		});
		
	});
	
	//단계 이동
	function nextStep() {
		if($('#totalBudget').val() != 0) {
			x.style.left="-600px";
			y.style.left="0px";
		} else {
			alert('총 예산 값을 입력하세요!');
		}
	}
	function preStep() {
		x.style.left="0px";
		y.style.left="600px";
	}
	
</script>
