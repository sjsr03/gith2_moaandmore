<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 예산</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
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
	input[type=number] {
		width:70px;
	}
	#leftMoneyList, tr, td {
		border:1px solid #ccc;
		border-collapse:collapse;
		padding:2px;
	}
</style>


<body>
	<div style="border:1px solid black">
		예산 기간 : ${TBdto.start_day} ~ ${TBdto.end_day} (일) <br/>
		총 예산 : ${TBdto.budget}원<br/>
	</div>
	<br/>
	오늘의 예산<br/>
	<div>
	
	</div>
	
	
	
	<div style="border:1px solid black">
		예산에서 남은 돈<br/>
		<div>
			총액 : <span id="totalLeftMoney"></span>원
		</div>
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
							<td><input type="number" class="inputAmount" value="0" max="${i.amount}" min="0"/></td>
							<td><input type="number" readonly class="LeftAfterTrans" value="${i.amount }"/></td>
						</tr>
					</c:forEach>
						<tr>
							<td colspan="5">전환할 총 금액 : <span id="transSum"></span>원</td>
						</tr>
					</table>
					<div>
						<label><input type="radio" name="target_table" value="budget" />현재예산에 재분배</label>
						<br/>
						<label><input type="radio" name="target_table" value="goals" />목표로 보내기</label>
					</div>
					<div>
						재분배할 카테고리
						<select id="targetCat">
							<c:forEach items="${BDdtoList }" var="j">
								<option value=${j.category_no }>${categories[j.category_no] }</option>
							</c:forEach>
						</select>
					</div>
					<div>
						목표 선택
						<select id="targetGoal">
							<c:forEach items="${goals }" var="j">
								<option value=${j.goal_no }>${j.subject }</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<input type="submit" value="전환하기">
					</div>
				</div>
				</form>
		</div>
	</div>
	
	
</body>
<script>
	$(document).ready(function(){
		//남은 돈 총합 계산
		calSumFirst();

		
		//분배금액 바뀔 때마다
		$('.inputAmount').on('keyup', function(){
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
	});
	
	
	
	//남은돈 총합 계산하기(페이지 불러올 때 한 번)
	function calSumFirst() {
		var sum = 0;
		
		$('.amount').each(function(){
			sum += parseInt($(this).text());
		});
		
		$('#totalLeftMoney').text(sum);
	}
	
	
	//전환할 총 금액 합산
	function calSumTrans() {
		var sum = 0;
		//체크된 것들만
		$('.chk:checked').each(function(){
			var inputAmount = $(this).parent().parent().children('.inputAmount').val();
			console.log(inputAmount);
			if(inputAmount==null) {
				sum += 0;
			} else {
				sum += parseInt(inputAmount);
			}
		});
		
		$('#transSum').text(sum);
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