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
.btn{
	display: block;
    position: absolute;
    top: 10px;
    right: 5px; }
</style>
<script>
var type;
var cateType;
var budgetTime;
var budgetDate;
var budgetReg;
console.log("레코드페이지 안의 타임 : " + type);
	$(document).ready(function(){
		
		//console.log("레코드페이지 안의 타입222 :" + type);
		//console.log("searchDate :" +  "${searchDate}");
		//console.log("pageNum :" +  "${recordPage.pageNum}");
		//console.log("레코드 안의 type :" +  $("#hiddenType").val());
		
		
		// 수정버튼 처리
		$("button[name='btn_modify']").on('click',function(event){
			console.log(event);
			var id = $(this).attr("id");
			
			var number = id.replace("btn_", "");
			console.log("id >> " + id);
				alert(number)
				console.log("수정버튼이닷");
				var check = false;
				check = confirm("수정을 하시겠습니까?");
				if(check){ // check가 true면
					//var num = $("#number").val();
					//var type = $("#recordType").val();
					//deleteRecord(num, type);
				}else{// check가 false면 
					alert("수정을 취소합니다.");
				}	
		})
		// 삭제버튼 처리
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
	// 예산 카테고리 가져오는 Ajax
	function getBudgetCategories(){
		$.ajax({
			type : "POST",
			url : "budgetCategory.moa",
			data : {date:$("#date").val(), id:"${id}"},
			dataType : "json", 
			async: false,
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	
			},
			success : function(data){							
				console.log($("#date").val());
				console.log(typeof date);
				// 기간에 해당하는 예산의 카테고리로 셀렉트 옵션 새로 바꿔주기
				$("#category").find("option").remove(); // 기존 카테고리 셀렉트 옵션 삭제
	
				for(var key in data){
					console.log("컬럼:" + key + "value : " + data[key]);
					//console.log(typeof key);
					if(key != 'budgetNum'){ 
						
						console.log("되냐안되냐");
						$("#category").append("<option value='"+key+"'>"+data[key]+"</option>");
					}else{ // budgetNum 이면 변수에 담아주기
						budget_no = data[key];
					}								
				}
				$("#category").css("display", "block");
			}
		});
	}
	// 삭제 Ajax	
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
	function goDetail(cateNum, num, recordType, recordCategory, content, reg, amount, memo, img) {
		// reg를 time과 date로 쪼개서 각각 대입해주기
		budgetReg = reg;
		budgetTime = reg.substr(11);
		console.log("자른 시간 >>> " + budgetTime);
		$("#time").val(budgetTime);
	
		
		budgetDate = reg.substring(0,10);
		console.log("자른 DATE >>>> " + budgetDate);
		$("#date").val(budgetDate);
		// 카테고리 번호도 같이 받아오기
		console.log("번호다~ : " + cateNum);
		
		// num 앞글자인 0 값 제거
		var subNum = num.substr(1);
		$("#number").val(subNum);
		// 내역 여러개 가져오는거면  records.type(글마다 정해진 type에서 꺼내오기)
		// ${records.type} 이 안에!! 다 들어있음  ${records.type} 값을 recordType으로 받아줬음!
		$("#recordType").val(recordType); 
		
		console.log("이미지~~:" + img);
		
		$("#img").attr('src','../resources/img/'+img);
		
		// amount 정수타입으로 형변환
		amount *= 1;
		$("#recordCategory").val(recordCategory);
		$("#recordContent").val(content);
		//$("#reg").val(reg);
		$("#amount").val(amount);
		
		$("#memo").val(memo);
		
		
		// 내역을 클릭했을때 해당 카테고리 빼고 나머지 숨기기
		console.log("체크체크 : " +$("#recordType").val() );
		if($("#recordType").val() == "budget"){
			console.log("예산");
			cateType="budgetcategory";
			$("#budgetcategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#outcomecategory").css("display", "none"); 
			getBudgetCategories();
		}else if($("#recordType").val() == "income"){
			console.log("수입")
			cateType="incomecategory";
			$("#incomecategory").css("display", "block"); 
			$("#budgetcategory").css("display", "none");
			$("#outcomecategory").css("display", "none"); 
		}else if($("#recordType").val() == "outcome"){
			console.log("지출")
			cateType="outcomecategory";
			$("#outcomecategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#budgetcategory").css("display", "none");
		}// 끝

		
		
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
							<c:set var="recordCateNum" value="${records.income_category_no}" />
							${categories[records.income_category_no]}
						</c:when>
						<c:otherwise>
							<c:set var="recordCate" value="${categories[records.outcome_category_no]}" />
							<c:set var="recordCateNum" value="${records.outcome_category_no}" />
							${categories[records.outcome_category_no]}
						</c:otherwise>
					</c:choose>	
				</c:if>
				<c:if test="${not empty incomeCategories}">
					<c:choose>
						<c:when test="${records.type eq 'income'}">
							<c:set var="recordCate" value="${incomeCategories[records.income_category_no]}" />
							<c:set var="recordCateNum" value="${records.income_category_no}" />
							${incomeCategories[records.income_category_no]}
						</c:when>
						<c:otherwise>
							<c:set var="recordCate" value="${outcomeCategories[records.outcome_category_no]}" />
							<c:set var="recordCateNum" value="${records.outcome_category_no}" />
							${outcomeCategories[records.outcome_category_no]}
						</c:otherwise>
					</c:choose>
				</c:if>	
					<input type="hidden" id="hiddenType" value="${records.type}" />
				</td>
				
				
				<td class="content"> 
					<a href="javascript:void(0)" onclick="goDetail('${recordCateNum}','${records.budget_outcome_no}'+'${records.nobudget_no}', '${records.type}', '${recordCate}','${records.content}','${records.reg}','${records.amount}','${records.memo}','${records.img}');">${records.content}</a>
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
	    <div class="title">상세내역
	    <button class="btn" id="btn_modify" name="btn_modify">수정</button>
		<button class="btn" id="btn_delete" name="btn_delete">삭제</button>
	    </div>   
	    <a href="javascript:popupClose();" class="layerpop_close"
	        id="layerbox_close">X</a> <br>
	    <div class="formContent">
	    	<form action="#" method="post" enctype="multipart/form-data">
	    		<input type="hidden" id="number" value=0/>
	    		
				<table border="1" style="width:390px; height:300px;">
				    <tr>
						<td>타입</td>
						<td>카테고리</td>				
					</tr>
					<tr>
					<%--
					<c:if test="${not empty incomeCategories}">
						<c:choose>
							<c:when test="${records.type eq 'income'}">
								<c:set var="recordCate" value="${incomeCategories[records.income_category_no]}" />
								<c:set var="recordCateNum" value="${records.income_category_no}" />
								${incomeCategories[records.income_category_no]}
							</c:when>
							<c:otherwise>
								<c:set var="recordCate" value="${outcomeCategories[records.outcome_category_no]}" />
								<c:set var="recordCateNum" value="${records.outcome_category_no}" />
								${outcomeCategories[records.outcome_category_no]}
							</c:otherwise>
						</c:choose>
					</c:if>	
					 --%>
					
						<td><input type="text" id="recordType" value="recordType"/></td>
						<td>
							<div class="category-section">
								<%-- 수입 지출은 여러개 선택했을 때 카테고리 이름이 달라서 분기처리필요 --%>				
								<%-- 지출 카테고리 --%>
								<div class="input-area">
									<select id="outcomecategory" name="outcomecategory">
									<c:if test="${empty incomeCategories}">
										<c:forEach var="outcomecategories" items= "${categories}" >
											<option value="${outcomecategories.key}">${outcomecategories.value}</option>
										</c:forEach>
									</c:if>	
									<c:if test="${not empty incomeCategories}">
										<c:forEach var="outcomecategories" items= "${incomeCategories}" >
											<option value="${outcomecategories.key}">${outcomecategories.value}</option>
										</c:forEach>
									</c:if>	
									</select>
								</div>
								<%-- 수입 카테고리 --%>
								<div class="input-area">
									<select id="incomecategory" name="incomecategory">
									<c:if test="${empty incomeCategories}">
										<c:forEach var="incomeCategories" items= "${categories}" >
											<option value="${incomeCategories.key}">${incomeCategories.value}</option>
										</c:forEach>
									</c:if>
									<c:if test="${not empty incomeCategories}">
										<c:forEach var="outcomecategories" items= "${outcomeCategories}" >
											<option value="${outcomecategories.key}">${outcomecategories.value}</option>
										</c:forEach>
									</c:if>	
									</select>
								</div>
								<%-- 예산내 지출 카테고리 --%>
								<%-- categories 번호가 key 이름이 value --%>
								<div class="input-area">
									<select id="category" name="category">
									<c:forEach var="categories" items= "${categories}" >
										<option value="${categories.key}">${categories.value}</option>
									</c:forEach>
									</select>
								</div>
							</div>
						</td>	
					</tr>
					<tr>
						<td>날짜,시간</td>
						<td>금액</td>	
					</tr>
					<tr>
						<td><input type="date" id="date"/><input type="time" id="time"/><td>
						<td><input type="number" id="amount" value=0 /></td>	
					</tr>
					<tr>
						<td colspan="2">제목</td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="recordContent" value="recordContent" size="47"/> </td>
					</tr>
					<tr>
						<td colspan="2">메모</td>
						
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="memo" value="memo" size="47" /></td>
					</tr>
					<tr>
						<td colspan="2">이미지</td>
					</tr>
					<tr>
						<td colspan="2" >
							<img id="img" src="" style="width:130px; height:130px;"/>
							<input type="file" id="image" />
						</td>
					</tr>
			   </table>
		   </form>

	    </div>
    </div>
</div>


</body>
</html>