<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.3.2/main.min.css" />
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.3.2/main.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
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


<input class="checkbox" id="a"  type="checkbox" value="1"/> 지출
<input class="checkbox" id="checkboxb"  type="checkbox" value="2"/> 수입
<input class="checkbox" id="checkboxc"  type="checkbox" value="3"/> 예산 외 지출  </br>
<div id='calendar'></div>

</body>
<script>
	
        	
var checkVal = [];

 
	document.addEventListener('DOMContentLoaded', function() {
		
		var calendarEl = document.getElementById('calendar');	
		var calendar = new FullCalendar.Calendar(calendarEl, {
			
		      initialView: 'dayGridMonth',
		      initialDate: '2020-10-01',
		      headerToolbar: {
		        left: '',
		        center: 'title',
		        right: 'prev,next today'
		      },
		      events:
		    	  function(){
		    	  $(".checkbox").each(function(){
		    		 	$(this).on('change',function(){
		    					if($(this).is(":checked")){
		    						
		    						/*
		    						$.ajax({
		    				   			url: "calendar.moa", 
		    				   			error : function(){
		    								console.log("error");
		    							},
		    				   			success: function(budgetDateAndAmount) {
		    				   				console.log("success");
		    				   				var events = [];
		    		
		    				   				$.each(budgetDateAndAmount,function(){
		    				   					events.push({title:'-${i.value}',start:' ${i.key}',"color": "red"})
		    				   					
		    				   				});
		    				   				console.log("---",budgetDateAndAmount);
		    				   				//console.log(budgetDateAndAmount.budgetDateAndAmount[0].budgetDateAndAmount);//여기도 잘 나옵니다.
//		    			 	   				var events = eval(plan.jsonTxt); 
		    				   				//callback(events); //여기서 오류 나더라구요
		    				   				} 
		    				   		}); 
		    						*/
		    					}else{
		    						console.log(2);
		    					}
		    		 	 
		    		 	});	
		    	 	});   
		    	
		      
		      } 
		      
		   
		
		});
		    calendar.render();	
	 	});
 	
 	
	/*	
	 var expense =  [
  	    		<c:forEach items="${budgetDateAndAmount}" var="i">
    		 	{ title: '-${i.value}', start: ' ${i.key}',"color": "red"},
	    	 	</c:forEach>];
		var nobudgetExpense = [
			<c:forEach items="${nobudgetExpense}" var="i">
    		{ title: '-${i.value}', start:'${i.key}',"color": "gray"},
     		</c:forEach>];
		var test3 = expense.concat(nobudgetExpense);
	 	console.log(test3);
	*/
		

		
	
	
 </script>


</html>