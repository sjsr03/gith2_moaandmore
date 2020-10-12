<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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


${map["2020-10-08"]}

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
    	      events:[
    	        {
    	          title: 'All Day Event',
    	          start: '2020-09-01'
    	        },
    	        {
    	          title: 'Long Event',
    	          start: '2020-09-07',
    	          end: '2020-09-10'
    	        },
    	        {
    	          groupId: '999',
    	          title: 'Repeating Event',
    	          start: '2020-09-09T16:00:00'
    	        },
    	        {
    	          groupId: '999',
    	          title: 'Repeating Event',
    	          start: '2020-09-16T16:00:00'
    	        },
    	        {
    	          title: 'Conference',
    	          start: '2020-09-11',
    	          end: '2020-09-13'
    	        },
    	        {
    	          title: 'Meeting',
    	          start: '2020-09-12T10:30:00',
    	          end: '2020-09-12T12:30:00'
    	        },
    	        {
    	          title: 'Lunch',
    	          start: '2020-09-12T12:00:00'
    	        },
    	        {
    	          title: 'Meeting',
    	          start: '2020-09-12T14:30:00'
    	        },
    	        {
    	          title: 'Birthday Party',
    	          start: '2020-09-13T07:00:00'
    	        },
    	        {
    	          title: 'Click for Google',
    	          url: 'http://google.com/',
    	          start: '2020-09-28'
    	        }
    	      ]
    	    	
    	    });

    	    calendar.render();
    	  });
    
    </script>


</html>