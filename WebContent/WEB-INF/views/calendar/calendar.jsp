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

${budgetDateAndAmount}
<div id='calendar'></div>

</body>
<script>
	 document.addEventListener('DOMContentLoaded', function() {
    	    var calendarEl = document.getElementById('calendar');

    	    var calendar = new FullCalendar.Calendar(calendarEl, {
    	      initialView: 'dayGridMonth',
    	      initialDate: '2020-09-07',
    	      headerToolbar: {
    	        left: 'prev,next today',
    	        center: 'title',
    	        right: ''
    	      },
    	      events:
    	    	  [
    	    		 <c:forEach items="${budgetDateAndAmount}" var="i">
    	    		 	 { title: '-${i.value}', start: ' ${i.key}',"color": "red"},
    	    	 	 </c:forEach>
    	    	  {title:'+5000',start:'2020-10-12',"color":"blue"}]
    	      
    	    });

    	    calendar.render();
    	  });
	
    </script>


</html>