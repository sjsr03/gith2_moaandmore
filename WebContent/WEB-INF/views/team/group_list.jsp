<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>그룹 리스트</title>
<style type="text/css">
	html, body{ width:1800px; height:950px;}
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
<body>
	<div class="header" style="background-color: gray;width:1650px;height:140px;">
		<h1 style="margin-top:0; margin-bottom: 0;">Header</h1>
	</div>
	<div class="menu" style="background-color:#FFD6FF;width:200px;height:810px;float:left;">
	
	</div>
	<div class="content" style="background-color:#8BBDFF;width:1430px;height:790px;margin-left: 200px; padding: 10px;">
		
		<div class="top" style="width: 100%; height: 40px; padding-top: 5px;">
			<button onclick="location='http://www.naver.com'">개설 신청</button>			
			<div style="float:right">
				<button>진행중</button>
				<button>종료</button>
				<button>개설예정</button>
			</div>
		</div>
		<div class="middle" style="width: 100%; height: 690px; text-align: center;">
		
				<div class="group_one" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart1" style="background: conic-gradient(#8b22ff 0% 80%, #BDBDBD 80% 100%);"><span class="center"></span><span class="big">80%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
				<div class="group_two" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80" style="visibility: hidden;"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart2" style="background: conic-gradient(#8b22ff 0% 30%, #BDBDBD 30% 100%);"><span class="center"></span><span class="big">30%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
				<div class="group_three" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart3" style="background: conic-gradient(#8b22ff 0% 90%, #BDBDBD 90% 100%);"><span class="center"></span><span class="big">90%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
				<div class="group_four" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart4" style="background: conic-gradient(#8b22ff 0% 50%, #BDBDBD 50% 100%);"><span class="center"></span><span class="big">50%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
				<div class="group_five" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart5" style="background: conic-gradient(#8b22ff 0% 99%, #BDBDBD 99% 100%);"><span class="center"></span><span class="big">99%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
				<div class="group_six" style="width:400px; height:330px;background-color: white; margin: 5px;display: inline-block;">
					<div style="width: 380px; height: 200px; border: 1px solid red; text-align: center; margin-left: 10px;">
						<div style="float: left;"><img src="/moamore/resources/img/take_part_icon.png" width="80"/></div>
						<div style="float: right; padding-right: 10px; font-size: 22px;">30명</div>
						<div class="pie-chart pie-chart6" style="background: conic-gradient(#8b22ff 0% 10%, #BDBDBD 10% 100%);"><span class="center"></span><span class="big">10%</span><span class="mini">평균달성률</span></span></div>
					</div>
					<table style="width:380px; height:130px;">
						<tr>
							<td>한 달 안에 백만원!</td>
						</tr>
						<tr>
							<td>기간 : 20.10.04~20.11.04</td>
						</tr>
						<tr>
							<td>목표금액 : 백만원</td>
						</tr>
					</table>
				</div>
			
		</div>
		<%-- 게시판 목록 페이지 번호 뷰어 설정 --%>
		<div align="center">
		<a>&lt; &gt;</a>
		</div>
		<div class="bottom"  style="width: 100%; height: 55px; text-align: center; margin-top: 5px;">
			<form action="/moamore/team/groupListPro.moa" method="post" style="align-self: center;">
				<input type="text" class="search_box" placeholder="그룹명 검색"/>
				<input type="submit" value="검색"/>
			</form>
		</div>
	</div>
</body>
</html>