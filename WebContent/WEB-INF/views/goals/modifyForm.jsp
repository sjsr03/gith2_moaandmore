<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title>개인목표 추가하기</title>
</head>
<jsp:include page="../sidebar.jsp"/>
<div class="container-fluid">
	<div class="row">
		<div class="col-10 text-center offset-1 mt-5">
			<h2 class="h2">개인목표 추가하기</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-10 offset-1">
			<div class="p-3">
				<form action="/moamore/goals/modifyPro.moa" method="post">
					<input type="hidden" name="goal_no" value="${goal.goal_no}"/>
					<div class="from-group mb-4">
						<span class="text">목표명</span><input class="form-control" type="text" name="subject" value="${goal.subject}" required/>
					</div>
					<div class="form-group mb-4">
						<span class="text">목표금액(원)</span> <input class="form-control" type="text" name="target_money" value="${goal.target_money}" required/>
					</div>
					<input type="submit" class="btn btn-primary" value="추가"/>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp"/>
