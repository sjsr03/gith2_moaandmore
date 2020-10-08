<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예산 설정</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	
	<form action="/moamore/budget/setBudgetPro.moa" method="post" >
	
	<div style="width:600px;height:1000px;overflow:hidden;position:absolute;left:0px;">
		<div style="width:600px;height:1000px;display:inline-block;position:absolute;transition: .5s;left:0px;" id="firstStep">
			<ul>
				<li>
					총 예산 : <input type="number" name="totalBudget" id="totalBudget"/>원
				</li>
				<li>
					기간 단위 : 
					<select name="period" id="period">
						<option value="7">7일</option>
						<option value="14">14일</option>
						<option value="30">한달</option>
					</select>
				</li>
				<li id="startday" style="display:none">월 시작일 : 매월 <input type="number" min="1" max="28" name="firstOfMonth" value="1"/>일
				</li>
			</ul>
			<input type="button" value="세부설정 >" onclick="nextStep()"/>
		</div>
		<div style="width:600px;height:1000px;display:inline-block;position:absolute;transition: .5s;left:600px;" id="secondStep">
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
						<input type="number" readonly class="rate"/>%
					</td>
					<td>
						<input type="number" readonly class="dayAmount"/>%
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
			<input type="hidden" value="새로운 예산 생성" id="createBudget" />
		</div>
	</div>
	</form>
</body>

<script>
	
	var x = document.getElementById("firstStep");
	var y = document.getElementById("secondStep");
	
	$(document).ready(function(){
		$('#insertLine').on('click', function(){ //라인 추가
			$('#detailBudget').append('<tr><td><select name="category_name" class="category_name" required><option class="none" disabled selected>==카테고리 선택==</option><c:forEach items="${categoryList}" var="i"><option value="${i.category_name }">${i.category_name }</option></c:forEach></select></td><td><input type="number" name="amount" min="0" required class="amount"/></td><td><input type="number" readonly class="rate"/>%</td><td><input type="button" class="deleteBtn" value="삭제"/></td></tr>');
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
			
			//적힌 비율도 다시 계산
			$('.amount').each(function(){
				var rate = $(this).val()/$('#totalBudget').val()*100;
				$(this).parent().next().children('.rate').val(rate.toFixed(1));
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
</html>