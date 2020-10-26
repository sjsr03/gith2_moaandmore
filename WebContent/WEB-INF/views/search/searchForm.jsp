<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>검색 페이지</title>
<style>
</style>
<script>
//+start,endday 형식 검사해주는 함수



function checkForm(){
	var res = true;
	var inputSt = $("#startday").val();
	var inputEd=$("#endday").val();
	var inputCt = $("#search_content").val();
	
	if(inputSt == "" || inputEd ==""|| inputCt ==""){
		alert("검색조건을 모두 입력해야 합니다.");
		res= false;
	}
	return res;
}

//숫자 자릿수 포맷(3자리수마다 ,) 
Number.prototype.format = function(){
	if(this ==0) return 0;
	
	var reg = /(^[+-]?\d+)(\d{3})/;
	var n = (this +'');
	
	while(reg.test(n)) n = n.replace(reg, '$1'+','+'$2');
	
	return n;
}

//문자 자릿수 포맷(3자리수마다 ,) 
String.prototype.format = function(){
	var num = parseFloat(this);
	if(isNaN(num)) return "0";
	
	return num.format();
	
}

//데이트 포맷바꾸기 
function getFormatDate(str){
	var date = new Date(str);
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0'+month;
	var day = date.getDate();
	day= day>=10 ? day : '0'+day;
	return year + '.' +month + '.' + day;
}

function longToDate(val){  
	  var date = new Date(val);
	  var yyyy=date.getFullYear().toString(); 
	  var mm = (date.getMonth()+1).toString();
	  var dd = date.getDate().toString();

	  var Str = '';

	  Str += yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' +(dd[1] ? dd : '0' + dd[0]);
	  return Str;
	}


function get_searchInfo(){	
	var chForm = checkForm();
	
	if(chForm == false){ return};
	
	var queryString = $("form[name=searchForm]").serialize();
	queryString = decodeURIComponent(queryString);
	$.ajax({
		type:'post',
		url: '/moamore/search/searchPro.moa',
		contentType : "application/json; charset=UTF-8",
		data : queryString,
		dataType : 'json',
		success : function(data){
			$('#search-wrapper').css('display','block');
			$('#search_info').css('display','none');
			$('#search_info2').css('display','none');
			var cnt = data.length;//총 건수
			var totalAmount = 0;//총 금액
			
			
			

			var st = new Date($('#startday').val());
			var ed = new Date($('#endday').val());
			var diffDay = (ed.getTime()-st.getTime())/(1000*60*60*24);//검색기간 일수
			var avgDay= diffDay/cnt;
			
			
			
			$('#search_info').empty();
			$('#search_keyword').empty();
			$("#searchList").empty();
			
			if(cnt == 0){
				$('#search_keyword').append("<span class='text'>검색결과가 없습니다.</span>")
				return;
			}else{
				$('#search_info').css('display','flex');
				$('#search_info2').css('display','flex');
				
				var maxAmount = data[0].amount; //최고금액
				var minAmount = data[0].amount;//최저금액
				$('#search_keyword').text(data[0].content);
			
				
				var addHtml ="";
				addHtml = "<thead><tr><th>날짜</th><th>금액</th></tr></thead>";
				$("#searchList").append(addHtml);
				addHtml = "<tbody>"
				for(var i = 0 ;i <data.length; i++){
					var dt = longToDate(data[i].reg);
					var amt = data[i].amount;
					addHtml+= "<tr><td>"+dt+"</td><td>"+amt.format()+"원</td></tr>";
					totalAmount += data[i].amount;
					if(data[i].amount > maxAmount) maxAmount = data[i].amount;
					if(data[i].amount < minAmount) minAmount = data[i].amount;
				}
				
				var avgAmount = (totalAmount /cnt).toFixed(0); //건당 평균액				
				
				addHtml+="</tbody>"
				$("#searchList").append(addHtml);
				
				//검색정보 append
				$('#search_info').append("<span class='text'>총 "+diffDay+"일간,</span>");
				$('#search_info').append("<span class='text'>총 "+cnt+"회,</span>");
				$('#search_info').append("<span class='text'>총액 "+totalAmount.format()+"원</span>");
				$('#search_info2').append("<span class='text'>평균빈도수 "+avgDay+"일/</span>");
				$('#search_info2').append("<span class='text'>건당평균액 "+avgAmount.format()+"원/</span>");
				$('#search_info2').append("<span class='text'>최저금액 "+minAmount.format()+"원/</span>");
				$('#search_info2').append("<span class='text'>최대금액 "+maxAmount.format()+"원</span>");
				
			}//end else
			
		},
		error : function(){
			console.log("검색정보 로드 실패");
		}
	})
	
}



</script>
</head>

<jsp:include page="../sidebar.jsp"/>

<div class="container-fluid">
	<div class="row">
		<h5 class="h5">*예산외 내역은 제외되고 검색됩니다.</h5>
	</div>
	<div class="row">
		<form name="searchForm" id="searchForm" >
			<span class="text">시작일</span><input id="startday" type="text" name="startday" placeholder="ex)2020.10.20" size="13"/><br/><span class="text">종료일</span><input name="endday" id="endday" type="text" placeholder="ex)2020.10.25" size="12"/>
			<br/><span class="text">분류</span>
			<select id="search_category" name="search_category">
			<c:forEach var="category" items="${outcomeCategoryList}">
				<option value="${category.category_no}"><c:out value="${category.category_name}"></c:out></option>
			</c:forEach>
			</select>
			<br/><span class="text">검색명</span><input id="search_content" name="search_content" type="text" placeholder="검색 항목을 입력하세요."/>
			<button type="button" onclick="get_searchInfo()">검색</button>
		</form>
	
	</div>
	<div class="row card shadow mb-4" id="search-wrapper" style="display:none;">
		<h3 class="h2 card-body mt-2" id="search_keyword" style="display:flex; align-items: center; justify-content: center;vertical-align:middle; text-align:center;"></h3>
		<div class="card-body" style="display:none; align-items: center; justify-content: center;vertical-align:middle; text-align:center;" id="search_info" style="display:none;"></div>
		<div class="card-body" style="display:none; align-items: center; justify-content: center;vertical-align:middle; text-align:center;" id="search_info2" style="display:none;"></div>
	</div>
	<div class="row">
		<table class="table" id="searchList">
		</table>
	
		
	</div>
</div>
	
<jsp:include page="../footer.jsp"/>
<script>
$('#startday').focusout(function() {
	var inputSt = $("#startday").val()
	//2020.10.25
	if(inputSt.length != 10 || inputSt.charAt(4) != '.' || inputSt.charAt(7) != '.'){
		alert("시작일은 YYYY.MM.dd 형태로 입력해야합니다.");
		$("#startday").val("");
	}
	
});
$('#endday').focusout(function() {
	var inputEd = $("#endday").val()
	//2020.10.25
	if(inputEd.length != 10 || inputEd.charAt(4) != '.' || inputEd.charAt(7) != '.'){
		alert("종료일은 YYYY.MM.dd 형태로 입력해야합니다.");
		$("#endday").val("");
	}
	
});

</script>
