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

	<!-- 예산 재설정 경고 안내 -->
	<div id="popup1" class="overlay" style="position: absolute;	top: 0;bottom: 0;left: 0;right: 0;background: rgba(0,0,0,0.5);transition: opacity 200ms;visibility: show;opacity: 0;">
		<div class="popup" style="margin: 75px auto;padding: 20px;background: #fff;border: 1px solid #666;width: 300px;box-shadow: 0 0 50px rgba(0,0,0,0.5);position: relative;">
			<h2>Info box</h2>
			<a class="close" href="#" style="position: absolute;width: 20px;height: 20px;top: 20px;right: 20px;opacity: 0.8;transition: all 200ms;font-size: 24px;font-weight: bold;text-decoration: none;color: #666;">&times;</a>
			<div>
				<p>This is done totally without JavaScript. Just HTML and CSS.</p>
			</div>
		</div>
	</div>

	
	<form action="/moamore/budget/setBudgetPro.moa" method="post" >
	
	<div style="width:600px;height:1000px;overflow:hidden;position:absolute;left:0px;">
		<div style="width:600px;height:1000px;display:inline-block;position:absolute;transition: .5s;left:0px;" id="firstStep">
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
		<div style="width:600px;height:1000px;display:inline-block;position:absolute;transition: .5s;left:600px;" id="secondStep">
			<input type="button" value="<이전단계" onclick="preStep()"/>
			<input type="button" value="추가" id="insertLine"/>
			
			<table id="detailBudget">
				<tr>
					<td>카테고리</td>
					<td>금액</td>
					<td>비율</td>
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
			<input type="hidden" value="새로운 예산 생성" id="createBudget" />
		</div>
	</div>
	</form>
</body>

<script>
	
	var x = document.getElementById("firstStep");
	var y = document.getElementById("secondStep");
	
	$(document).ready(function(){
		$('#TbudgetPrint').text($('#totalBudget').val());
		$('.amount').each(function(){
			reRate($(this));
		});
		reSum();
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
			});
			
			//카테고리명이 change일 때 옵션 속성 변경
			$('.category_name').on('change', function(){
				optControl();
			});
			
		});	//라인추가 function 끝
		
		//카테고리명이 change일 때 옵션 속성 변경
		$('.category_name').on('change', function(){
			optControl();
		});
		
		//비율 및 총합 자동계산
		$('.amount').on('keyup', function(){
			reRate($(this));
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