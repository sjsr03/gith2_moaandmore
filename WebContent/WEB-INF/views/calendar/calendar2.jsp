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
    .fc-day-content {height: 130px;}
    @media (max-width: 992px){
        .fc-day-content {height: 90px !important;}
    }
    @media (max-width: 767px){
        .fc-day-content {height: 80px !important;}
    }
    @media (max-width: 640px){
        .fc-day-content {height: 45px !important;}
    }
    @media (max-width: 500px){
        .fc-day-content {height: 35px !important;}
    }
    .fc-event-container > .fc-event-more {display: none;}
    .test { 
         position: absolute; 
         top: 50%; 
         left: 50%; 
         transform: translate(-50%, -50%); 
         background-color: white; 
         padding: 1rem 1.5rem; 
         width: 500px; 
         height: 350px; 
         border-radius: 0.5rem; 
         visibility: hidden; 
     }  
    
    .show{ 
         opacity: 1; 
         visibility: visible; 
         transform: scale(1.0); 
         transition: visibility 0s linear 0s, opacity 0.25s 0s, transform 0.25s; 
     }  
    
    
</style>
<body>
<input class="checkbox" id="checkbox"  type="checkbox" value="1"/> 지출
<input class="checkbox" id="checkbox"  type="checkbox" value="2"/> 수입
<input class="checkbox" id="checkbox"  type="checkbox" value="3"/> 예산 외 지출  </br>

<div id='calendar'></div>
<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/moment.min.js'></script>
<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/jquery.min.js'></script>
<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/lib/jquery-ui.min.js'></script>
<script src='https://fullcalendar.io/js/fullcalendar-3.1.0/fullcalendar.min.js'></script>


<div id="test" class="test">
	
</div>



</body>
<script>

var checkVal = [""];	

var col='';
var pm='';
var events = [];	

$(document).ready(function () {
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
	    				    	    		 		title:pm+finalByCheckVal[i][j],
	    				    	    		 		start:j,
	    				    	    		 		color: 'white',
	    				    	    		 	    textColor: col
	    				    	    		 	});
			    	    		 		}   		
					   				}
					   			 	callback(events);	
					   			}
	    		 		});//ajax
	      },//events
	      eventClick: function(event) {
             	alert(event.start);
             	var date = new Date(event.start);
             	 date = getFormatDate(date);
             	 //console.log(date);
	    	 	
	    	 	$.ajax({
			   			url: "getCalendarEventDetail.moa", 
			   			type :"POST",
			   			data:{date:date},
			   	  	 	dataType : "json",
			   			error : function(){
							console.log("error");
						},
			   			success: function(alldata) {
			   				//console.log(alldata);
							$('#test').append("<tr><td>" +'유형' + "</td><td>" +'금액'+ "</td><td>"+'제목'+"</td><td>"+'메모'+"</td></tr>");
			   				for (var i = 0; i < alldata.length; i+=4) {
    							console.log(alldata[i]);
			   					$('#test').addClass('show');
					    	 	$('#test').append("<tr><td>" +alldata[i] + "</td><td>" +alldata[i+1] + "</td><td>"+alldata[i+2]+"</td><td>"+alldata[i+3]+"</td></tr>");
			   			  	}	
			   				
			   			
			   			}
	    	
              	});
	      
	      		//$(this).find('.div').hasClass('on')){
        		//console.log(1);
        		//$(this).find('.my_sub').removeClass('on');
	  			
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
    
    
    
 	
    </script>


</html>