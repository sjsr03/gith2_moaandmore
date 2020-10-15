package calendar.controller.bean;

import java.sql.SQLException;
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

import calendar.model.dto.CalendarDTO;
import calendar.service.bean.CalendarService;



@Controller
@RequestMapping("/calendar/")
public class CalendarBean {

	
	@Autowired
	private CalendarService calendarService = null;
	
	
	
	
	
	@RequestMapping("calendar.moa")
	public String calendar(Model model) throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);

		//budget의 날짜와 총 지출액 map 형태로 받아오기 
		
		
		
	
		
		return "calendar/calendar2";
	}
	    
	   
	   
	
	@RequestMapping(value="getCalendarEvent.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map getCalendarEvent(@RequestParam(value="checkVal[]") List<String> checkVal) throws SQLException{
		
		
		System.out.println(checkVal);
		
		
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		//Map budgetDateAndAmount = calendarService.selectBudgetDateAndAmount(id);
		
		//nobudget의 날짜와 총 지출액 map 형태로 받아오기
		Map noBudgetExpenseDateAndAmount = calendarService.selectNobudgetExpenseDateAndAmount(id);
		
		//nobudget의 날짜와 총 수입액 map 형태로 받아오기
		Map noBudgetIncomeDateAndAmount = calendarService.selectNobudgetIncomeDateAndAmount(id);
		
		Map BudgetDateAndAmount = calendarService.selectBudgetDateAndAmount(id);
		
		Map finalByCheckVal = new HashMap();
		//checkVal로 해당하는 값 맵으로 넘어서 출력
		if(checkVal.contains("1")) {
			finalByCheckVal.put(1, BudgetDateAndAmount);
		}
		if(checkVal.contains("2")) {
			finalByCheckVal.put(2,noBudgetExpenseDateAndAmount);
		}
		if(checkVal.contains("3")) {
			finalByCheckVal.put(3, noBudgetIncomeDateAndAmount);
		}
		
		//System.out.println(finalByCheckVal.get(1).get("2020-10-06"));
		System.out.println(finalByCheckVal);
		
		
		return finalByCheckVal;
	}
	
	
	
	
	
	
	
	
}
