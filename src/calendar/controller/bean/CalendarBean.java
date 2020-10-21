package calendar.controller.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


import calendar.service.bean.CalendarService;



@Controller
@RequestMapping("/calendar/")
public class CalendarBean {

	
	@Autowired
	private CalendarService calendarService = null;
	
	

	
	@RequestMapping("calendar.moa")
	public String calendar(Model model) throws SQLException{
		

		
		return "calendar/calendar2";
	}
	    
	   
	   
	
	@RequestMapping(value="getCalendarEvent.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map getCalendarEvent(@RequestParam(value="checkVal[]") List<String> checkVal) throws SQLException{
		
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		
		//budget의 날짜와 총 지출액 받아오기(1)
		Map BudgetDateAndAmount = calendarService.selectBudgetDateAndAmount(id);
		
		//nobudget의 날짜와 총 지출액 map 형태로 받아오기(2)
		Map noBudgetExpenseDateAndAmount = calendarService.selectNobudgetExpenseDateAndAmount(id);
		
		//nobudget의 날짜와 총 수입액 map 형태로 받아오기(3)
		Map noBudgetIncomeDateAndAmount = calendarService.selectNobudgetIncomeDateAndAmount(id);
		
		//페이지에 넘겨줄 map
		Map finalByCheckVal = new HashMap();
		
		//checkVal로 체크해서 뿌려줄값 map에 담아주기
		if(checkVal.contains("1")) {
			finalByCheckVal.put(1, BudgetDateAndAmount);
		}
		if(checkVal.contains("2")) {
			finalByCheckVal.put(2,noBudgetExpenseDateAndAmount);
		}
		if(checkVal.contains("3")) {
			finalByCheckVal.put(3, noBudgetIncomeDateAndAmount);
		}
		

		
		return finalByCheckVal;
	}
	
	
	
	
	@RequestMapping(value="getCalendarEventDetail.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List getCalendarEventDetail(String date) throws SQLException{
		
		
		List alldata= calendarService.getAlldata(date);
		
		//전체 list들 list에 담아주기
		return alldata;
	}
	
	
}
