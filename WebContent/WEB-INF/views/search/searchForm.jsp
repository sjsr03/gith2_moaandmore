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
				addHtml = "<thead><tr><th>날짜</th>금액<th></th></tr></thead>";
				$("#searchList").append(addHtml);
				addHtml = "<tbody>"
				for(var i = 0 ;i <data.length; i++){
					addHtml+= "<tr><td>"+data[i].reg+"</td><td>"+data[i].amount+"원</td></tr>";
					totalAmount += data[i].amount;
					if(data[i].amount > maxAmount) maxAmount = data[i].amount;
					if(data[i].amount < minAmount) minAmount = data[i].amount;
				}
				
				var avgAmount = (totalAmount/cnt).toFixed(0); //건당 평균액				
				
				addHtml+="</tbody>"
				$("#searchList").append(addHtml);
				
				//검색정보 append
				$('#search_info').append("<span class='text'>총 "+diffDay+"일간,</span>");
				$('#search_info').append("<span class='text'>총 "+cnt+"회,</span>");
				$('#search_info').append("<span class='text'>총액 "+totalAmount+"원</span>");
				$('#search_info2').append("<span class='text'>평균빈도수 "+avgDay+"일/</span>");
				$('#search_info2').append("<span class='text'>건당평균액 "+avgAmount+"원/</span>");
				$('#search_info2').append("<span class='text'>최저금액 "+minAmount+"원/</span>");
				$('#search_info2').append("<span class='text'>최대금액 "+maxAmount+"원</span>");
				
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

