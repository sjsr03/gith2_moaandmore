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
function check_stday(){
	
}
function check_edday(){
	
}

function checkForm(){
	
}

function get_searchInfo(){
	var queryString = $("form[name=searchForm]").serialize();
	queryString = decodeURIComponent(queryString);
	$.ajax({
		type:'post',
		url: '/moamore/search/searchPro.moa',
		contentType : "application/json; charset=UTF-8",
		data : queryString,
		dataType : 'json',
		success : function(data){
			var cnt = data.length;//총 건수
			var totalAmount = 0;//총 금액
			var maxAmount =data[0].amount; //최고금액
			var minAmount = data[0].amount;//최저금액
			

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
				$('#search_keyword').text(data[0].content);
				$('#search_info').append("<span class='text'>"+cnt+"</span>")
				
				var addHtml ="";
				addHtml = "<thead><tr><th>날짜</th>금액<th></th></tr></thead>";
				$("#searchList").append(addHtml);
				addHtml = "<tbody>"
				for(var i = 0 ;i <data.length; i++){
					addHtml+= "<tr><td>"+data[i].reg+"</td><td>"+data[i].amount+"원</td></tr>";
					totalAmount += data[i].amount;
					if(data[i].amount > maxAmount) maxAmount = data[i].amount;
					if(data[i].amount < minAmount) minAmount = data[i].amount;
				}
				
				var avgAmount = (totalAmount/cnt).toFixed(0);//건당 평균액				
				
				addHtml+="</tbody>"
				$("#searchList").append(addHtml);
				$('#search_info').append("<span class='text'>"+totalAmount+"</span>")
				
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
			<span class="text">시작일</span><input id="startday" type="text" name="startday" placeholder="ex)2020.10.20"/> <span class="text">종료일</span><input name="endday" id="endday" type="text" placeholder="ex)2020.10.25"/>
			<span class="text">분류</span>
			<select id="search_category" name="search_category">
				<c:forEach var="category" items="${outcomeCategoryList}">
					<option value="${category.category_no}"><c:out value="${category.category_name}"></c:out></option>
				</c:forEach>
			</select>
			<span class="text">검색명</span><input id="search_content" name="search_content" type="text" placeholder="검색 항목을 입력하세요."/>
			<button type="button" onclick="get_searchInfo()">검색</button>
		</form>
	
	</div>
	<div class="row card shadow mb-4" >
		<h3 class="h2 card-body" id="search_keyword" style="width:100%; height:100%; display:flex; align-items: center; justify-content: center;vertical-align:middle; text-align:center;"></h3>
		<span></span>
	</div>
	<div class="row card-body" style="vertical-align:middle" id="search_info">
	</div>
		
	<div class="row">
		<table class="table" id="searchList">
		
		</table>
	
		
	</div>
</div>
	
<jsp:include page="../footer.jsp"/>

