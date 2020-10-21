<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<title>moneyLog</title>

</head>
<style>
#mask {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 999;
    background-color: #000000;
    display: none; }

.layerpop {
    display: none;
    z-index: 1000;
    border: 2px solid #ccc;
    background: #fff;
    cursor: move; }

.layerpop_area .title {
    padding: 10px 10px 10px 10px;
    border: 0px solid #aaaaaa;
    background: #f1f1f1;
    color: #3eb0ce;
    font-size: 1.3em;
    font-weight: bold;
    line-height: 24px; }

.layerpop_area .layerpop_close {
    width: 25px;
    height: 25px;
    display: block;
    position: absolute;
    top: 10px;
    right: 10px;
   	
.layerpop_area .layerpop_close:hover {
    background: transparent url('btn_exit_on.png') no-repeat;
    cursor: pointer; }

.layerpop_area .content {
    width: 96%;    
    margin: 2%;
    color: #828282; }

</style>
<script>
var type;
console.log("레코드페이지 안의 타임 : " + type);
	$(document).ready(function(){
		
		//console.log("레코드페이지 안의 타입222 :" + type);
		//console.log("searchDate :" +  "${searchDate}");
		//console.log("pageNum :" +  "${recordPage.pageNum}");
		//console.log("레코드 안의 type :" +  $("#hiddenType").val());
		
		
		/*
		$("#modifybtn").click(function(){
			console.log("수정버튼이닷");
			var popup = window.open('팝업 주소 ','팝업창 이름',' 팝업창 설정');
		});
		*/
		$("button[name='btn_delete']").on('click',function(event){
			console.log(event);
			var id = $(this).attr("id");
			
			var number = id.replace("btn_", "");
			console.log("id >> " + id);
				alert(number)
				console.log("삭제버튼이닷");
				var check = false;
				check = confirm("정말 삭제하시겠습니까?");
				if(check){ // check가 true면
					var num = $("#number").val();
					var type = $("#recordType").val();
					deleteRecord(num, type);
				}else{// check가 false면 
					alert("삭제를 취소합니다.");
				}	
		});//삭제 
		
	});
	
	// 댓글 삭제 Ajax	
	function deleteRecord(num, type){
		$.ajax({
			type:"POST",
			url:"budgetRecordDelete.moa",
			dateType: "json",
			data:{"number":num, "type":type},
			success: function(result){
				if(result=="OK"){
					// 내용날리기 안되니까 걍 새로고침 ㅎㅎ;
					location.reload();
					alert("삭제완료");
				}else{
					// 삭제 실패
					alert("삭제 실패");
				}
			}				
		});
	}
	
	function wrapWindowByMask() {
	    //화면의 높이와 너비를 구한다.
	    var maskHeight = $(document).height(); 
	    var maskWidth = $(window).width();
	
	    //문서영역의 크기 
	    console.log( "document 사이즈:"+ $(document).width() + "*" + $(document).height()); 
	    //브라우저에서 문서가 보여지는 영역의 크기
	    console.log( "window 사이즈:"+ $(window).width() + "*" + $(window).height());        
	
	    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	    $('#mask').css({
	        'width' : maskWidth,
	        'height' : maskHeight
	    });	
	    //애니메이션 효과
	    //$('#mask').fadeIn(1000);      
	    $('#mask').fadeTo("slow", 0.5);
	}
	
	function popupOpen() {
	    $('.layerpop').css("position", "absolute");
	    //영역 가운에데 레이어를 뛰우기 위해 위치 계산 
	    $('.layerpop').css("top",(($(window).height() - $('.layerpop').outerHeight()) / 2) + $(window).scrollTop());
	    $('.layerpop').css("left",(($(window).width() - $('.layerpop').outerWidth()) / 2) + $(window).scrollLeft());
	    $('#layerbox').show();
	}
	
	function popupClose() {
	    $('#layerbox').hide();
	    $('#mask').hide();
	}
	//, content, reg, amount, memo, img
	function goDetail(num, recordType, recordCategory, content, reg, amount, memo, img) {
		
		// num 앞글자인 0 값 제거
		var subNum = num.substr(1);
		$("#number").val(subNum);
		// 내역 여러개 가져오는거면  records.type(글마다 정해진 type에서 꺼내오기)
		// ${records.type} 이 안에!! 다 들어있음  ${records.type} 값을 recordType으로 받아줬음!
		$("#recordType").val(recordType); 
		
		
		$("#img").val('src','../../resources/img/'+img);
		
		// amount 정수타입으로 형변환
		amount *= 1;
		$("#recordCategory").val(recordCategory);
		$("#recordContent").val(content);
		$("#reg").val(reg);
		$("#amount").val(amount);
		
		$("#memo").val(memo);
	    /*팝업 오픈전 별도의 작업이 있을경우 구현*/ 
	    popupOpen(); //레이어 팝업창 오픈 
	    wrapWindowByMask(); //화면 마스크 효과 
	}


</script>
<body>
<h2 align="center"> 입출력 내역 </h2>
	
	

<div align="center">
	<button onclick="location.href='/moamore/record/recordForm.moa'">입출력 입력</button><br />
</div>
<%-- 테이블 시작 --%>
<div class="boardcss_list_table" align="center">
	<br />
	<table class="list_table">
		<caption>룰루랄라</caption>
		
		<colgroup>
			<col /> 
			<col /> 
			<col /> 
			<col /> 
			<col /> 
		</colgroup>
		<thead>
			<tr>
				<th>날짜/시간</th>
				<th>카테고리</th>
				<th>내역</th>
				<th>금액</th>
			</tr>
			
		</thead>
		<tbody>
			
			<c:forEach var="records" items="${recordPage.recordList}" varStatus="status" >		
			<tr>		
				<td>
					<fmt:formatDate value="${records.reg}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td id="category">
				<c:set var="recordCate" value="" />
		
				<%-- c:set 이용해서 변수에 계속 담아 준 후 마지막에 a태그에 넣어줌 --%>
				<c:if test="${not empty categories}">
					<c:choose>
						<c:when test="${records.type eq 'income'}">
							<c:set var="recordCate" value="${categories[records.income_category_no]}" />
							${categories[records.income_category_no]}
						</c:when>
						<c:otherwise>
							<c:set var="recordCate" value="${categories[records.outcome_category_no]}" />
							${categories[records.outcome_category_no]}
						</c:otherwise>
					</c:choose>	
				</c:if>
				<c:if test="${not empty incomeCategories}">
					<c:choose>
						<c:when test="${records.type eq 'income'}">
							<c:set var="recordCate" value="${incomeCategories[records.income_category_no]}" />
							${incomeCategories[records.income_category_no]}
						</c:when>
						<c:otherwise>
							<c:set var="recordCate" value="${outcomeCategories[records.outcome_category_no]}" />
							${outcomeCategories[records.outcome_category_no]}
						</c:otherwise>
					</c:choose>
				</c:if>	
					<input type="hidden" id="hiddenType" value="${records.type}" />
				</td>
				
				
				<td id="content"> 
					<a href="javascript:void(0)" onclick="goDetail('${records.budget_outcome_no}'+'${records.nobudget_no}', '${records.type}', '${recordCate}','${records.content}','${records.reg}','${records.amount}','${records.memo}','${records.img}');">${records.content}</a>
					
				</td>
				<td>
					<fmt:formatNumber type="number" maxFractionDigits="3"  value="${records.amount}"/>원
				</td>
				<td>
					<c:set var="budget_outcome_no" value="${records.budget_outcome_no}" />
					<c:set var="nobudget_no" value="${records.nobudget_no}" />
					<c:choose>
					<c:when test="${not empty budget_outcome_no}">
						<input type="hidden" value="${records.budget_outcome_no}" class="num">
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${records.nobudget_no}" class="num">
					</c:otherwise>
					</c:choose>
					
					
				</td>
				
			</tr>	
			
			</c:forEach>
		</tbody>
	</table>
</div>
<%-- 테이블 종료 --%>
<%-- 페이지 번호 처리 --%>
<br /><br />
<div align="center">
	<c:if test="${recordPage.count > 0}">
		<fmt:parseNumber var="res" value="${recordPage.count / recordPage.pageSize}" integerOnly="true"/>
		
		<fmt:parseNumber var="pageCount" value="${res + (recordPage.count % recordPage.pageSize == 0 ? 0 : 1 )} "/>
		<c:set var="pageBlock" value="10" />
		
		<fmt:parseNumber var="result" value="${(recordPage.currPage-1)/pageBlock}" integerOnly="true"/>
		<fmt:parseNumber var="startPage" value="${result*pageBlock + 1}"/>
		<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}"/>
		<c:if test="${endPage > pageCount}">
		<c:set var="endPage" value="${pageCount}"/>
		</c:if>
		<c:if test="${startPage>pageBlock}">
			<a href="/moamore/record/moneyRecord.moa?pageNum=${startPage-pageBlock}"> %lt;</a>
		</c:if>
		<c:forEach var="i" begin="${startPage}" end="${endPage }" step="1">
		<a href="/moamore/record/moneyRecord.moa?pageNum=${i}&type=${recordPage.type}&searchDate=${searchDate}" class="pageNums"> &nbsp; ${i} &nbsp; </a>	
		</c:forEach>
		<c:if test="${endPage<pageCount}">
			<a href="/moamore/record/moneyRecord.moa?pageNum=${startPage+pageBlock }"> &gt; </a>	  
		</c:if>
	</c:if>
</div>

<div style="height:1000px;"></div>
<!-- 팝업뜰 때 배경 -->
<div id="mask"></div>
<!-- 팝업창  -->
<!--Popup Start -->
<div id="layerbox" class="layerpop" style="width: 400px; height: 500px;">
    <div class="layerpop_area">
	    <div class="title">상세내역</div>
	    <a href="javascript:popupClose();" class="layerpop_close"
	        id="layerbox_close">X</a> <br>
	    <div class="content">
	    	<form action="#" method="post" enctype="multipart/form-data">
	    		<input type="hidden" id="number" value=0/>
				<table border="1" style="width:390px; height:300px;">
				    <tr>
						<td>타입</td>
						<td>카테고리</td>				
					</tr>
					<tr>
						<td><input type="text" id="recordType" value="recordType"/></td>
						<td><input type="text" id="recordCategory"  value="recordCategory"/></td>	
					</tr>
					<tr>
						<td>날짜,시간</td>
						<td>금액</td>	
					</tr>
					<tr>
						<td id="reg"></td>
						<td><input type="number" id="amount" value=0 /></td>
						
					</tr>
					<tr>
						<td colspan="2">제목</td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="recordContent" value="recordContent" /> </td>
					</tr>
					<tr>
						<td colspan="2">메모</td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="memo" value="memo" /></td>
					</tr>
					<tr>
						<td colspan="2">이미지</td>
					</tr>
					<tr>
						<td colspan="2" >
							<input type="file" id="image" />
							<img id="img" src=""/>
						</td>
					</tr>
			   </table>
		   </form>
		   <button class="btn" id="btn_modify" name="btn_modify">수정</button>
		   <button class="btn" id="btn_delete" name="btn_delete">삭제</button>
	    </div>
    </div>
</div>


</body>
</html>