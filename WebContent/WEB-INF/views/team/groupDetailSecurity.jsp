<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<meta charset="UTF-8">
	<title>그룹 비밀번호 입력</title>
</head>
<jsp:include page="../sidebar.jsp"/>


        <!-- 본문내용 시작 -->
        <div class="container-fluid">
			<div class="content">
				<div class="row text-center" style="width: 100%; display:flex;" align="center">
					<form class="col-xl-12 col-lg-12" action="/moamore/team/teamDetailSecurityPro.moa" method="post">
						<input type="hidden" name="team_no" value="${team_no}"/>
						<input type="hidden" name="nickname" value="${sessionScope.memName}"/>
						<input class="col-xl-6 col-lg-6" type="password" name="pw" placeholder="그룹의 비밀번호를 입력해주세요."/>
						<input type="submit" value="확인"/>
					</form>
				</div>
            </div>
        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="../footer.jsp"/>
