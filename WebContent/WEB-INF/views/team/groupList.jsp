<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<title>그룹 리스트</title>
<style type="text/css">
	html, body{ width:1800px; height:980px;}
	span.center{
	  background: #fff;
	  display : block;
	  position: absolute;
	  top:50%; left:50%;
	  width:130px; height:130px;
	  border-radius: 50%;
	  text-align:center;
	  line-height: 130px;
	  transform: translate(-50%, -50%);
	}
	span.big{
	  position: absolute;
	  top:50%; left:50%;
	  width:130px; height:130px;
	  margin-top:-15px;
	  border-radius: 50%;
	  text-align:center;
	  line-height: 130px;
	  font-size:30px;
	  transform: translate(-50%, -50%);
	}
	span.mini{
	  position: absolute;
	  top:50%; left:50%;
	  width:130px; height:130px;
	  margin-top:15px;
	  border-radius: 50%;
	  text-align:center;
	  line-height: 130px;
	  font-size:15px;
	  transform: translate(-50%, -50%);
	}
	.pie-chart {
	  margin-left:115px;
	  margin-top:25px;
	  position: relative;
	  width: 150px;
	  height: 150px;
	  border-radius: 50%;
	  transition: 0.3s;
	}
</style>
</head>
<script>
	$(document).ready(function(){
		$('#range option:eq(${range})').prop("selected", true);
		
		$('#range').on('change', function(){
			if(${isSearch}==0 || ${isSearch}==null){
				var url = "groupList.moa?pageStatus=${pageStatus}&range=" + $("select[name='range']").val(); 
			} else {
				var url = "groupList.moa?pageStatus=${pageStatus}&isSearch=${isSearch}&search=${search}&range=" + $("select[name='range']").val(); 
			}
			window.location.replace(url);
		});
	});
</script>
<body>
	<div class="header" style="background-color: gray;width:1650px;height:140px;">
		<h1 style="margin-top:0; margin-bottom: 0;">Header</h1>
	</div>
	<div class="menu" style="background-color:#FFD6FF;width:200px;height:840px;float:left;">
		<c:if test="${sessionScope.memName != null}">
			<a href="/moamore/team/groupMyRequestList.moa?nickname=${sessionScope.memName}">My 개설 신청 리스트</a>
		</c:if>
	</div>
	<div class="content" style="background-color:#8BBDFF;width:1430px;height:820px;margin-left: 200px; padding: 10px;">
		
		<div class="top" style="width: 100%; height: 40px; padding-top: 5px;">
			<c:if test="${sessionScope.memName != null}">
				<button onclick="window.location='/moamore/team/groupOpenForm.moa'">개설 신청</button>	
			</c:if>
			<c:if test="${sessionScope.memName == null}">
			<button onclick="alert('로그인을 해주세요.');document.location.href='/moamore/member/loginForm.moa'">개설 신청</button>
			</c:if>
			<div style="float:right">
				<button onclick="location='/moamore/team/groupList.moa?pageStatus=2&range=${range}'">진행중</button>
				<button onclick="location='/moamore/team/groupList.moa?pageStatus=3&range=${range}'">종료</button>
				<button onclick="location='/moamore/team/groupList.moa?pageStatus=1&range=${range}'">개설예정</button>
			</div>
		</div>
		<div class="middle" style="width: 100%; height: 720px; text-align: center;">
		
			<select name="range" id="range">
				<option value="0">그룹 등록 최신순</option>
				<option value="1">그룹 등록 오래된순</option>
				<option value="2">목표 금액 높은순</option>
				<option value="3">목표 금액 낮은순</option>
				<option value="4">시작일 빠른순</option>
				<option value="5">시작일 느린순</option>
				<option value="6">종료일 빠른순</option>
				<option value="7">종료일 느린순</option>
				<option value="8">인원수 많은순</option>
				<option value="9">인원수 적은순</option>
			</select>
			
			<br/>
			<c:if test="${articleList == null}">
				<script>
					alert('해당되는 조건의 그룹이 없습니다.');
					history.back();
				</script>
			</c:if>
		
			<c:if test="${articleList != null}">
				<c:forEach var="article" items="${articleList}" varStatus="stat">
					<div style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;" onclick="window.location.href='/moamore/team/teamDetail.moa?team_no=${article.team_no}'">
							<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
								<c:forEach var="mem" items="${articleMemberAvgList[0][stat.index]}">
									<c:if test="${sessionScope.memName == mem.nickname}">
										<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
									</c:if>
								</c:forEach>
								<div style="float: right; padding-right: 10px; font-size: 22px;">${article.people}명</div>
								<div class="pie-chart pie-chart1" style="background: conic-gradient(#8b22ff 0% ${articleMemberAvgList[1][stat.index]}%, #BDBDBD ${articleMemberAvgList[1][stat.index]}% 100%);"><span class="center"></span><span class="big">${articleMemberAvgList[1][stat.index]}%</span><span class="mini">평균달성률</span></span></div>
							</div>
							<table style="width:380px; height:130px;">
								<tr>
									<td>${article.subject}</td>
								</tr>
								<tr>
									<td>기간 : ${fn:substring(article.start_day,0,10)}~${fn:substring(article.end_day,0,10)}</td>
								</tr>
								<tr>
									<td>목표금액 : ${article.amount}원</td>
								</tr>
							</table>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
		<div align="center">
			<c:if test="${count > 0}">
				<fmt:parseNumber var="res" value="${count / pageSize}"  integerOnly="true"/>
				<c:set var="pageCount" value="${res + (count % pageSize == 0 ? 0 : 1)}"  />
				<c:set var="pageBlock" value="10" />
				<fmt:parseNumber var="result" value="${(currPage-1)/pageBlock}" integerOnly="true" />
				<fmt:parseNumber var="startPage" value="${result * pageBlock + 1}" />
				<fmt:parseNumber var="endPage" value="${startPage + pageBlock - 1}" />
				<c:if test="${endPage > pageCount}">
					<c:set var="endPage" value="${pageCount}" />
				</c:if>
				
				<c:if test="${startPage > pageBlock}">
					<c:if test="${isSearch==0}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&pageNum=${startPage-pageBlock}&range=${range}" > &lt; </a>
					</c:if>
					<c:if test="${isSearch==1}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&isSearch=1&search=${search}&pageNum=${startPage-pageBlock}&range=${range}" > &lt; </a>
					</c:if>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1" >
					<c:if test="${isSearch==0}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&pageNum=${i}&range=${range}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
					</c:if>
					<c:if test="${isSearch==1}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&isSearch=1&search=${search}&pageNum=${i}&range=${range}" class="pageNums"> &nbsp; ${i} &nbsp; </a>
					</c:if>
				</c:forEach>
				<c:if test="${endPage < pageCount}">
					<c:if test="${isSearch==0}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&pageNum=${startPage+pageBlock}&range=${range}" > &gt; </a>
					</c:if>
					<c:if test="${isSearch==1}">
						<a href="/moamore/team/groupList.moa?pageStatus=${pageStatus}&isSearch=1&search=${search}&pageNum=${startPage+pageBlock}&range=${range}" > &gt; </a>
					</c:if>
				</c:if>
			
			</c:if>
		</div>
		<div class="bottom"  style="width: 100%; height: 55px; text-align: center; margin-top: 5px;">
			<form action="/moamore/team/groupList.moa?pageStatus=${pageStatus}&isSearch=1&range=${range}" method="post" style="align-self: center;">
				<input type="text" name="search" placeholder="그룹명 검색"/>
				<input type="submit" value="검색"/>
			</form>
		</div>
	</div>
</body>
</html>