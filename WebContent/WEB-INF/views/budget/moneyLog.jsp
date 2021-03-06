<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
<meta charset="UTF-8">

</head>
<style>

#mask {
    display: none;
	justify-content: center;
	align-items: center;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, .7);
	z-index: 9999;
	backdrop-filter: blur(4px);
	-webkit-backdrop-filter: blur(4px);
	display:none; 
}

.layerpop {
    padding: 20px;
	background: #fff;
	border-radius: 5px;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, .3);
}

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
}
    

a:link { color: red; text-decoration: none;}
a:visited { color: black; text-decoration: none;}
a:hover { color: blue; text-decoration: underline;}

</style>


<br />

<%-- 입출력 내역 테이블 시작--%>

<div class="container-fluid">
	<br />
	<%-- 첫번째 줄 --%>
	<div class="row">
		<div class="col-lg-12">
			<div class="card shadow mb-4">
				<div class="card-body"> 
					<%--내역이 없을 때  --%>	
					<c:if test="${recordPage.count == 0}">
						내역이 존재하지않습니다.
					</c:if>
					<%-- 내역이 있을 때  --%>
					<c:if test="${recordPage.count > 0}">
					<div class="table-responsive">
						<div  class="col-lg-12 dataTables_wrapper dt-bootstrap4">
							<div class="row">
								<table class="table table-bordered">	
										<thead>
											<tr>
												<th class="forManyType" style="width:8%">구분</th>
												<th style="width:20%">날짜/시간</th>
												<th style="width:20%">카테고리</th>
												<th style="width:30%">내역</th>
												<th style="width:12%">금액</th>
											</tr>
											
										</thead>
										<tbody>
											
											<c:forEach var="records" items="${recordPage.recordList}" varStatus="status" >		
											<tr style="cursor:pointer;" onclick="goDetail('${recordCateNum}','${records.budget_outcome_no}','${records.nobudget_no}', '${records.type}', '${recordCate}','${records.content}','${records.reg}','${records.amount}','${records.memo}','${records.img}');">
												<td class="forManyType">
													<c:if test="${records.type eq 'income'}">수입</c:if>
													<c:if test="${records.type eq 'outcome'}">지출</c:if>
													<c:if test="${records.type eq 'budget'}">예산</c:if>		
												</td>		
												<td>
													<fmt:formatDate value="${records.reg}" pattern="yyyy-MM-dd HH:mm"/>
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
													${records.content}
												</td>
												<td>
													<fmt:formatNumber type="number" maxFractionDigits="3"  value="${records.amount}"/>원
												</td>
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
											</tr>		
											</c:forEach>
										</tbody>
									
								</table>
							</div>
						</div>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
<div class="my-2"></div>
<%-- 입출력 내역 테이블 종료 --%>

<%-- 페이지 번호 처리 --%>
<br /><br />
	<div class="row">
		<div class="col-sm-12 col-md-12"  style="justify-content:center">
			<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate"  style="justify-content:center">
				<c:if test="${recordPage.count > 0}">
					<ul class="pagination" style="justify-content:center">
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
							<li class="paginate_button page-item">
								<a href="/moamore/record/moneyRecord.moa?pageNum=${startPage-pageBlock}"> %lt;</a>
							</li>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage }" step="1">
							<li class="paginate_button page-item <c:if test='${i == searchForRecordDTO.pageNum }'>active</c:if>">
								<a href="/moamore/record/moneyRecord.moa?pageNum=${i}&type=${recordPage.type}&searchDate=${searchDate}&keyword=${keyword}" class="pageNums page-link"> &nbsp; ${i} &nbsp; </a>	
							</li>
						</c:forEach>
						<c:if test="${endPage<pageCount}">
							<li class="paginate_button page-item">
								<a href="/moamore/record/moneyRecord.moa?pageNum=${startPage+pageBlock }"> &gt; </a>	  
							</li>
						</c:if>
					</ul>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!-- 팝업뜰 때 배경 -->
<div id="mask">
<!-- 팝업창  -->
<!-- 상세내역을 form 형식으로 보여주기 -->
<div id="layerbox" class="layerpop">
    <div class="layerpop_area">
	    <div class="title">상세내역
			<a href="javascript:popupClose();" class="layerpop_close" id="layerbox_close" style="color:#858796; justify-content:flex-end;">X</a>
	    </div>   
	    
	    <div class="formContent">
	    	<form action="#" method="post" id="recordDetail" enctype="multipart/form-data">
	    	<input type="hidden" id="id" value="${sessionScope.memId}"/>
				<table border="1" style="width:390px; height:300px;">
				    <tr>
						<td>타입</td>
						<td>카테고리</td>				
					</tr>
					<tr>
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
										<c:forEach var="outcomecategories" items= "${outcomeCategories}" >
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
										<c:forEach var="incomecategories" items= "${incomeCategories}" >
											<option value="${incomecategories.key}">${incomecategories.value}</option>
										</c:forEach>
									</c:if>	
									</select>
								</div>
								<%-- 예산내 지출 카테고리 --%>
								<%-- categories 번호가 key 이름이 value --%>
								<div class="input-area">
									<select id="budgetcategory" name="budgetcategory">
									<c:forEach var="categories" items= "${categories}" >
										<option value="${categories.key}">${categories.value}</option>
									</c:forEach>
									</select>
								</div>
							</div>
						</td>	
					</tr>
					<tr>
						<td>일시</td>
						<td>금액</td>	
					</tr>
					<tr>
					
						<td><input type="date" id="date"/><input type="time" id="time"/></td>
						<td><input type="text" id="amount" value=0 style="max-width:100px;" onkeyup="numberWithCommas(this.value)"/></td>	
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
			<button class="btn" id="btn_modify" name="btn_modify" >수정</button>
			<button class="btn" id="btn_delete" name="btn_delete">삭제</button>
	    </div>
    </div>
</div>
</div>


<script>
var type;
var cateType;
var budgetTime;
var budgetDate;
var budgetReg;
var budget_no = 0;
var uniqueNum =0;

	$(document).ready(function(){	
		if(type =="income" || type =="outcome" || type =="budget"){				
			$(".forManyType").hide();
		}else{		
			$(".forManyType").show();
		}		

		
		// 타입이 예산이고 date를 변경하면 해당 카테고리 불러오기 
		$("#date").on('change', function(){
			
			if($("#recordType").val() == "budget"){
				getBudgetCategories();
			}
		});
		
		
		// 수정버튼 처리
		$("button[name='btn_modify']").on('click',function(event){
			modifyRecord();
		
			//console.log(event);
			var id = $(this).attr("id");
			
			var number = id.replace("btn_", "");
			console.log("id >> " + id);

				var check = false;
				check = confirm("수정을 하시겠습니까?");
				if(check){ // check가 true면
					modifyRecord();
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
	
				var check = false;
				check = confirm("정말 삭제하시겠습니까?");
				if(check){ // check가 true면
					var type = $("#recordType").val();
					deleteRecord(uniqueNum, type);
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
				//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	
			},
			success : function(data){							
				console.log($("#date").val());
				console.log(typeof date);
				// 기간에 해당하는 예산의 카테고리로 셀렉트 옵션 새로 바꿔주기
				$("#budgetcategory").find("option").remove(); // 기존 카테고리 셀렉트 옵션 삭제
	
				for(var key in data){
					console.log("컬럼:" + key + "value : " + data[key]);
					//console.log(typeof key);
					if(key != 'budgetNum'){ 
						$("#budgetcategory").append("<option value='"+key+"'>"+data[key]+"</option>");
					}else{ // budgetNum 이면 변수에 담아주기
						budget_no = data[key];
					}								
				}
				$("#budgetcategory").css("display", "block");
			}
		});
	}
	// 삭제 Ajax	
	function deleteRecord(num, type){
		console.log("deleteRecord function에서의 num : " + num);
	
		$.ajax({
			type:"POST",
			url:"budgetRecordDelete.moa",
			dateType: "json",
			data:{"number":num, "type":type},
			success: function(result){
				if(result=="OK"){
					// 실행되면 새로고침
					location.reload();
					alert("삭제완료");
				}else{
					// 삭제 실패
					alert("삭제 실패");
				}
			}				
		});
	}
	
	// 수정 Ajax(사진포함)
	function modifyRecord(){
		console.log(budget_no);
	

		var form = $("#recordDetail")[0];
		var formData = new FormData(form);
		
		formData.append("type", $("#recordType").val());
		formData.append("id", $("#id").val());
		
		formData.append("date", $("#date").val());
		
		formData.append("time", $("#time").val());
		
		formData.append("amount", $("#amount").val());
		formData.append("content", $("#recordContent").val());
		formData.append("memo", $("#memo").val());
		formData.append("uniqueNum ", uniqueNum);
		formData.append("budget_no", budget_no);
		// 카테고리 번호들은 비어있어도 그냥 한번에 보내주기
		formData.append("outcome_category_no", $("#outcomecategory option:selected").val());
		formData.append("income_category_no", $("#incomecategory option:selected").val());
		formData.append("budget_category_no", $("#budgetcategory option:selected").val());
		
		
		console.log("타입 : " + $("#recordType").val());
		console.log("날짜 : " + $("#date").val());
		console.log("시간 : " + $("#time").val());
		console.log("금액 : " + $("#amount").val());
		console.log("메모 : " + $("#memo").val());
		console.log("uniqueNum : " + uniqueNum);
		console.log("budget_no : " + budget_no);
		console.log("outcomecategoryNum : " + $("#outcomecategory option:selected").val());
		console.log("incomecategoryNum : " + $("#incomecategory option:selected").val());
		console.log("budgetcategoryNum: " +  $("#budgetcategory option:selected").val());
		// 파일 
		console.log($("#image").val());
		formData.append("image", $("#image")[0].files[0]);
		
		$.ajax({
			type:"POST",
			url:"modifyRecord.moa",
			processData : false,
			contentType : false,
			data:formData,
			success: function(data){			
				alert(data);
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
	    $('#mask').css('display', 'flex');	
	    //애니메이션 효과
	    //$('#mask').fadeIn(1000);      
	    //$('#mask').fadeTo("slow", 0.5);
	}
	
	function popupOpen() {
	    //영역 가운에데 레이어를 뛰우기 위해 위치 계산 
	    $('#layerbox').show();
	}
	
	function popupClose() {
	    $('#layerbox').hide();
	    $('#mask').hide();
	}
	//, content, reg, amount, memo, img
	function goDetail(cateNum, budget_outcome_num, nobuget_num, recordType, recordCategory, content, reg, amount, memo, img) {
		console.log("godetail에서 budget_outcome_num : " +  budget_outcome_num );
		console.log("godetail에서 nobuget_num : " +  nobuget_num );
		
		// reg를 time과 date로 쪼개서 각각 대입해주기
		budgetReg = reg;
		budgetTime = reg.substr(11);
		$("#time").val(budgetTime);

		budgetDate = reg.substring(0,10);		
		$("#date").val(budgetDate);
		
		// num 은 ${records.budget_outcome_no}', '${records.nobudget_no} 값 각각 받아줘서
		// 값이 들어있는 것만 전역변수인 uniqueNum에게 넣어줌
		if(budget_outcome_num > 0){
			//$("#number").val(budget_num);
			uniqueNum = budget_outcome_num;
		}else if(nobuget_num > 0){
			//$("#number").val(nobuget_num);
			uniqueNum = nobuget_num;
		}	
		
		console.log($("#number").val());
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
			getBudgetCategories();
			console.log("예산");
			cateType="budgetcategory";
			$("#budgetcategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#outcomecategory").css("display", "none"); 
			//$(".forManyType").css("display", "none"); 
		}else if($("#recordType").val() == "income"){
			console.log("수입")
			cateType="incomecategory";
			$("#incomecategory").css("display", "block"); 
			$("#budgetcategory").css("display", "none");
			$("#outcomecategory").css("display", "none"); 
		//	$(".forManyType").css("display", "none"); 
		}else if($("#recordType").val() == "outcome"){
			console.log("지출")
			cateType="outcomecategory";
			$("#outcomecategory").css("display", "block"); 
			$("#incomecategory").css("display", "none"); 
			$("#budgetcategory").css("display", "none");
			//$(".forManyType").css("display", "none"); 
		}// 끝

	    /*팝업 오픈전 별도의 작업이 있을경우 구현*/ 
	    popupOpen(); //레이어 팝업창 오픈 
	    wrapWindowByMask(); //화면 마스크 효과 
	}
	
	// 수정창에서 금액 입력받을 때 콤마 찍어주기
	function numberWithCommas(x){
		x = x.replace(/[^0-9]/g,'');   // 입력값이 숫자가 아니면 공백
		x = x.replace(/,/g,'');          // ,값 공백처리
		$("#amount").val(x.replace(/\B(?=(\d{3})+(?!\d))/g, ",")); // 정규식을 이용해서 3자리 마다 , 추가 
	}
</script>