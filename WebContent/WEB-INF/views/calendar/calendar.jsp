<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar</title>
<link rel='stylesheet' href='https://fullcalendar.io/js/fullcalendar-3.1.0/fullcalendar.min.css' />
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.3.2/main.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
</head>							
 <style>
 
  /*
  	 @media (max-width: 992px){
        .fc-scroller {height: 100% !important;}
        .detailModal-content{width:300px !important;}
    }
    @media (max-width: 767px){
        .fc-scroller {height: 100% !important;}
       
    }
    @media (max-width: 640px){
        .fc-scroller {height: 100% !important;}
    }
    */
    @media (max-width: 500px){
        .fc-scroller {height: 100% !important;}
        .detailModal-content{  
        	  width: 90% !important;					
			  top: 250px !important;
			  left: 20px !important;
       	}
       	
    }
    
    @media (max-width: 370px){
        .fc-scroller {height: 100% !important;}
        .detailModal-content{    
        	width: 90% !important;
			 top: 250px !important;
			 left: 20px !important;
       	}
   		
    }
  
    .fc-event-container > .fc-event-more {display: none;}
   
     .cal_modal{
	    position: fixed;
	    left: 0;
	    top: 0;
	    width: 100%;
	    height: 100%;
	    background-color: rgba(0, 0, 0, 0.5);
	    opacity: 0;
	    z-index:-1;
     }
     
     
	.detailModal-content { 
         position: absolute; 
         top: 34%; 
         left: 43%; 
         width: 500px; 
         height: 400px; 
         background-color:#fff;
       	 z-index:2;
    	 border: 1px solid #e3e6f0;
    	 border-radius: .35rem;
     }      
     
     
    .modalShow{ 
         opacity: 1; 
         visibility: visible; 
         transform: scale(1.0); 
         transition: visibility 0s linear 0s, opacity 0.25s 0s, transform 0.25s; 
         z-index:3;
     }  
    
    .close-button{
    	float:right;
    	margin:5px;
    }
    
    .more{
    	width:70px; padding:0 5px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;


    }
</style>
<body>
<jsp:include page="../sidebar.jsp"/>
<!-- 본문내용 시작 -->	
<div class="container-fluid">

	<input class="checkbox box1" id="checkbox" type="checkbox" value="1"/> 지출
	<input class="checkbox" id="checkbox" type="checkbox" value="2"/> 수입
	<input class="checkbox" id="checkbox" type="checkbox" value="3"/> 예산 외 지출  <br/>
	
	<div id='calendar' class="my_cal"></div>
		<div class="cal_modal">
			<div class="detailModal-content card-body">
				<span class="close-button">&times;</span>
				<table  border="1" class="contentTable mytable table table-bordered dataTable" ></table>
			</div>
		</div>
</div>
	



<jsp:include page="../footer.jsp"/>	
</body>
	<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/moment.min.js'></script>
	<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/jquery.min.js'></script>
	<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/jquery-ui.min.js'></script>
	<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/fullcalendar.min.js'></script>

<script>

var checkVal = [""];	

var col='';
var pm='';
var events = [];	

$(document).ready(function () {
	
	
	
	//페이지 시작할때 지출 체크박스에 체크해주기
	$(".box1").prop("checked", true)
	if($(".box1").is(":checked")){
		checkVal.push($(".box1").val());
		$('#calendar').fullCalendar('removeEventSource', events);
	    $('#calendar').fullCalendar('addEventSource', events);
	    $('#calendar').fullCalendar('refetchEvents');
	}
	
	$(".checkbox").each(function(){
		 	$(this).on('change',function(){
	 			//체크된값 checkVal에 넣어주기
				if($(this).is(":checked")){
				   checkVal.push($(this).val());
				}else{
					checkVal.splice(checkVal.indexOf($(this).val()),1);
				}	 	
				$('#calendar').fullCalendar('removeEventSource', events);
			    $('#calendar').fullCalendar('addEventSource', events);
			    $('#calendar').fullCalendar('refetchEvents');
		 	});
	 });
	
	
	
	$('#calendar').fullCalendar({ 
		
	      initialView: 'dayGridMonth',
	      initialDate: '2020-10-01',
	      headerToolbar: {
	        left: '',
	        center: 'title',
	        right: 'prev,next today'
	      },
	      events:
	    	  function(start,end,timezone,callback){
	    	  			$.ajax({
					   			url: "getCalendarEvent.moa", 
					   			type :"POST",
					   			async: false,
					   			data:{
					   	       	 checkVal:checkVal
					   	   		},
					   	  	 	dataType : "json",
					   			error : function(){
									console.log("error");
								},
					   			success: function(finalByCheckVal) {
					   				var events = [];
					   				for(var i in finalByCheckVal){
					   					if(i==1){
					   						col = 'red';
					   						pm = '-';
					   					}else if(i==2){
					   						col = 'blue';
					   						pm = '+';
					   					}else if(i==3){
					   						col='gray';
					   						pm = '-';
					   					}
					   					for(var j in finalByCheckVal[i]){
	    				    	    		 	events.push({
	    				    	    		 		title:pm+finalByCheckVal[i][j].format()+'원',
	    				    	    		 		start:j,
	    				    	    		 		color: '#11ffee00;',
	    				    	    		 	    textColor: col
	    				    	    		 	});
			    	    		 		}   		
					   				}
					   			 	callback(events);	
					   			}
	    		 		});//ajax
	      },//events
	      eventClick: function(event) {
             	
             	var date = new Date(event.start);
             	 date = getFormatDate(date);
	    	 	
	    	 	$.ajax({
			   			url: "getCalendarEventDetail.moa", 
			   			type :"POST",
			   			data:{date:date},
			   	  	 	dataType : "json",
			   			error : function(){
							console.log("error");
						},
			   			success: function(alldata) {
			   				console.log(date);
							$('.contentTable').append("<tr><td>" +'유형' + "</td><td>" +'금액'+ "</td><td>"+'제목'+"</td><td>"+'메모'+"</td></tr>");
			   				for (var i = 0; i < alldata.length; i+=4) {
			   					
			   					$('.cal_modal').addClass('modalShow');
			   					$('.contentTable').append("<tr><td class='more'>" +alldata[i] + "</td><td class='more'>" +alldata[i+1] + "</td><td class='more'>"+alldata[i+2]+"</td><td class='more'>"+alldata[i+3]+"</td></tr>");
			   					if(i>12){
				   					$('.contentTable').append("<tr><td style='border-color:#11ffee00;' colspan='4' align=center ><div class='more'>...더보기</div></td></tr>");
			   						break;
			   					}
			   				
			   				}
			   				//x 버튼 누르면 창 사라지고 데이터 삭제
			   				$('.close-button').on('click',function(){
		   			  			$(this).parent().parent().removeClass('modalShow');
		   			  			$('.contentTable').empty();
		   			  		});
			   				//상세보기 페이지 처리
			   				$(".more").on("click",function(){
			   					window.location.href="/moamore/record/moneyRecord.moa?searchDate="+date+"&type=budget";
			   				});
			   			}
              	});
	      
		}//eventClick
	  });
	   
});


//yyyy-mm-dd
function getFormatDate(date){
    var year = date.getFullYear();
    var month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    var day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
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
/*
String.prototype.format = function(){
	var num = parseFloat(this);
	if(isNan(num)) return "0";
	
	return num.format();
	

}
*/
 	
    </script>


</html>