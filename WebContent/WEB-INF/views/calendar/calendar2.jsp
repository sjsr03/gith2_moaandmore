<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</body>
<script>

var checkVal = [];	
var events = [];	


$(document).ready(function () {
	 $(".checkbox").each(function(){
		 	$(this).on('change',function(){
	 		
				if($(this).is(":checked")){
				   checkVal.push($(this).val());
				}else{
					checkVal.splice(checkVal.indexOf($(this).val()),1);
				}
				console.log("최종",checkVal);
				
		    	 
				$.ajax({
		   			url: "getCalendarEvent.moa", 
		   			type :"POST",
		   			data:{
		   	       	 checkVal:checkVal
		   	   		},
		   	  	 	dataType : "json",
		   			error : function(){
						console.log("error");
						$('#calendar').fullCalendar('removeEventSource', events);
					},
		   			success: function(finalByCheckVal) {
		   				var events = [];
		   				console.log("success");
		   				console.log(finalByCheckVal);
		   				for(var i in finalByCheckVal){
		   					console.log("i",i);
		   					for(var j in finalByCheckVal[i]){
		   						console.log(j);
		   						console.log(finalByCheckVal[i][j]);
			    	    		 	events.push({
			    	    		 		title:finalByCheckVal[i][j],
			    	    		 		start:j
			    	    		 		
			    	    		 	});
    	    		 		}   		
		   				}
		   				console.log("events"+events);
		   			}
				});
		   			 	
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
					   			data:{
					   	       	 checkVal:checkVal
					   	   		},
					   	  	 	dataType : "json",
					   			error : function(){
									console.log("error");
								},
					   			success: function(finalByCheckVal) {
					   				var events = [];
					   				console.log("success");
					   				console.log(finalByCheckVal);
					   				for(var i in finalByCheckVal){
					   					console.log("i",i);
					   					for(var j in finalByCheckVal[i]){
					   						console.log(j);
					   						console.log(finalByCheckVal[i][j]);
	    				    	    		 	events.push({
	    				    	    		 		title:finalByCheckVal[i][j],
	    				    	    		 		start:j
	    				    	    		 		
	    				    	    		 	});
			    	    		 		}   		
					   				}
					   				console.log("events"+events);
					   			 	callback(events);	
					   			}
	    		 		});
	    	
	    	 
	      }//events 
	      
	   
	
	});
	   // calendar.render();	
 	});
    
 	
    </script>


</html>