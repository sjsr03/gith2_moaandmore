package main.controller.bean;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;

@Controller
@RequestMapping("*")
public class mainBean {
	
	@Autowired
	private BudgetService budgetService = null;
	
	
	
	@RequestMapping("main.moa")
	public String main(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		//로그인했다면
		if (id != null) {
			//현재 총예산 불러오기
			TotalBudgetDTO totalBudget = budgetService.selectCurrentOne(id);
			
			
			
		}
		
		
		
		return "main";
	}
}
