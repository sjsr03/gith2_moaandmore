<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 제이쿼리 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<title>예산 보고서</title>
<!-- Custom fonts for this template-->
	<link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css">
	
	<!-- Custom styles for this template-->
	<link rel="stylesheet"	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">  
	<link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body id="page-top">
<jsp:include page="../sidebar.jsp"/>


        <!-- 본문내용 시작 -->
        <div class="container-fluid">

          <!-- 페이지 이름 -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">예산 보고서</h1>
            </div>
            
           	<!-- 첫번째 줄 -->
            <div class="row">
				<div class="card mb-4">
				<!-- 
                <div class="card-header">
                  Default Card Example
                </div>
                 -->
                <div class="card-body">
                	<select id="selectBudget">
                		<c:forEach items="${TBList}" var="TB">
	                		<option value="${TB.budget_no}">
	                			<fmt:formatDate pattern="yyyy.MM.dd" value="${TB.start_day}"/> ~ 
	                			<fmt:formatDate pattern="yyyy.MM.dd" value="${TB.end_day}"/>
	                		</option>
                		</c:forEach>
                	</select>
                	<input type="button" value="달력"  />
                	<input type="date" id="datepick" />


                </div>
              </div>
              
            </div>
           	<!-- 두번째 줄 -->
            <div id="reportContent">
            </div>

        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="../footer.jsp"/>

  <!-- Bootstrap core JavaScript -->
  <script src="/moamore/vendor/jquery/jquery.min.js"></script>
  <script src="/moamore/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="/moamore/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="/moamore/js/sb-admin-2.min.js"></script>


	
</body>
<script>


	$(document).ready(function(){
		if($("#selectBudget").val() == null) {
			alert("과거 예산 기록이 없습니다.");
			history.go(-1);
		} else {
			$.ajax({
				url:"reportContent.moa",
				data:{
					"id":"${sessionScope.memId}",
					"budget_no":-1
				},
				success:function(data){
					$("#reportContent").append(data);
				}
			});
		}
		
		$("#selectBudget").on('change', function(){	//예산 선택 바꾸면
			$.ajax({
				url:"reportContent.moa",
				data:{
					"id":"${sessionScope.memId}",
					"budget_no":$(this).val()
				},
				success:function(data){
					$("#reportContent").empty();
					$("#reportContent").append(data);
				}
			});
		});
		
		$("#datepick").on('change', function(){	//날짜를 직접 선택하면
			//해당 예산번호 먼저 가져옴
			$.ajax({
				url:"getBudgetNum.moa",
				data:{
					"id":"${sessionScope.memId}",
					"date":$(this).val()
				},
				success:function(data){
					$("option[value="+data+"]").prop("selected", "true");
					reReport(data);
				}
			});
		});
			

		
	});
	
	function reReport(budget_no){
		$.ajax({
			url:"reportContent.moa",
			data:{
				"id":"${sessionScope.memId}",
				"budget_no":budget_no
			},
			success:function(data){
				$("#reportContent").empty();
				$("#reportContent").append(data);
			}
		});
	};
	
	
</script>
</html>