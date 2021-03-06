<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<title>결산 게시글 작성</title>
	<style type="text/css">
		table, th, td {
		    border: 1px solid black;
		    padding: 5px;
		}
		
		table {
		    border-collapse: collapse;
		}
	</style>
	<script>
	$(document).ready(function(){
		$("#selectBudget").on('change', function(){	//예산 선택 바꾸면
			$.ajax({
				url:"/moamore/report/reportContent.moa",
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
	});
	
	
		function check(){
			var inputs = document.inputForm;
			
			if(!inputs.subject.value){	//false일때, 값이 없을때
				alert("제목을 입력하세요.");
				return false;
			}
			
			if(!inputs.content.value){	//false일때, 값이 없을때
				alert("내용을 입력하세요.");
				return false;
			}
		}
	</script>
</head>
<jsp:include page="../sidebar.jsp"/>


        <!-- 본문내용 시작 -->
        <div class="container-fluid">
           <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">결산 작성</h1>
            </div>
            
            
            <div class="row">
				<div class="col-lg-12">

	              <div class="card shadow mb-4">
	                <div class="card-body">
            
            
			<form action="/moamore/closing/closingAccountFormPro.moa" method="post" name="inputForm" onsubmit="return check()">
				<div class="table-responsive">
					<div class="col-lg-12 dataTables_wrapper dt-bootstrap4" >
						<div class="row">
							<table class="table table-bordered">						
								<tr>
									<td style="width:100px;"> 작성자 </td>
									<td><input type="text" name="id" readonly="readonly" value="${sessionScope.memId}"/></td>
								</tr>
								<tr>
									<td> 제목 </td>
									<td><input type="text" name="subject"/></td>
								</tr>
								<tr>
									<td> 예산 선택 </td>
									<td>
										<select name="budget_no" id="selectBudget">
											<option disabled selected>예산 기간을 선택하세요</option>
											<c:forEach var="article" items="${list}">
												<option value='${article.budget_no}'>${fn:substring(article.start_day,0,10)} ~ ${fn:substring(article.end_day,0,10)}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<div id="reportContent">
		            					</div>
									</td>
								</tr>
								<tr>
									<td> 내용 </td>
									<!-- <td><input type="text" name="content"  /></td> -->
									<td><textarea name="content" style="width:100%; min-height:200px; height:auto;"></textarea></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;">
										<input type="submit" value="작성" />					
										<input type="reset" value="재작성" />					
										<input type="button" value="돌아가기" onclick="window.location='/moamore/closing/closingAccountList.moa'" />					
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
        </div>
        </div>
        </div>
        </div>
        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

<jsp:include page="../footer.jsp"/>