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
	
	<div style="width:600px;height:1000px;overflow:hidden;position:absolute;left:0px;">
		<div style="width:600px;height:1000px;display:inline-block;position:absolute;transition: .5s;left:0px;" id="firstStep">
			<ul>
				<li>
					총 예산 : <input type="number" name="budget" id="totalBudget"/>원
				</li>
				<li>
					기간 단위 : 
					<select name="period" id="period">
						<option value="7">7일</option>
						<option value="14">14일</option>
						<option value="30">한달</option>
					</select>
				</li>
				<li id="startday" style="display:none">월 시작일 : 매월 <input type="number" min="1" max="28" name="startday" value="1"/>일
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
				<tr>
					<td>
						<select>
							<c:forEach items="${categoryList}" var="i">
								<option>${i.category_name }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="number" name="amount" class="amount"/>
					</td>
					<td>
						<input type="number" readonly class="rate"/>%
					</td>
					<td>
						<input type="button" class="deleteBtn" value="삭제"/>
					</td>
					
				</tr>
			</table>
			<hr>
			
		</div>
	</div>

</body>

<script>
	var x = document.getElementById("firstStep");
	var y = document.getElementById("secondStep");
	
	$(document).ready(function(){
		$('#insertLine').on('click', function(){ //라인 추가
			$('#detailBudget').append('<tr><td><select><c:forEach items="${categoryList}" var="i"><option>${i.category_name }</option></c:forEach></select></td><td><input type="number" name="amount" class="amount"/></td><td><input type="number" readonly class="rate"/>%</td><td><input type="button" class="deleteBtn" value="삭제"/></td></tr>')
			//삭제버튼 기능
			$('.deleteBtn').on('click', function(){
				$(this).parent().parent().remove();
			});
			//비율 자동계산
			$('.amount').on('keyup', function(){
				var rate = $(this).val()/$('#totalBudget').val()*100;
				$(this).parent().next().children('.rate').val(rate.toFixed(2));
			});
		});
		//한달주기일때만 월 시작일 설정
		$('#period').on('change', function(){
			if($('#period').val()=='30') {
				$('#startday').css("display","block");
			} else {
				$('#startday').css("display","none");
			}
		});
		//비율 자동계산
		$('.amount').on('keyup', function(){
			var rate = $(this).val()/$('#totalBudget').val()*100;
			$(this).parent().next().children('.rate').val(rate.toFixed(2));
		});
		$('')
		
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