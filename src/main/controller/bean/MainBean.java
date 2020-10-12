package main.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;
import main.service.bean.MainService;

@Controller
public class MainBean {
	
	@Autowired
	private BudgetService budgetService = null;
	@Autowired
	private MainService mainService = null;
	
	
	@RequestMapping("main.moa")
	public String main(HttpServletRequest request, Model model) throws SQLException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		//로그인했다면
		if (id != null) {
			//현재 총예산 불러오기
			TotalBudgetDTO totalBudget = budgetService.selectCurrentOne(id);
			
			//만약 현재 설정된 예산이 없다면 예산설정 페이지로
			if(totalBudget == null) {
				return "redirect:/budget/setBudget.moa";
			}
			
			//현재 예산에서 총 소비액 불러오기
			int outcomeSum = mainService.selectOutcomeSumByBudgetId(totalBudget.getBudget_no());
			
			boolean t = Timestamp.valueOf("2020-11-10 00:00:00").after(Timestamp.valueOf("2020-11-11 00:00:00"));
			
			
			model.addAttribute("totalBudget",totalBudget);
			model.addAttribute("outcomeSum",outcomeSum);
			model.addAttribute("date",t);
		}
		
		return "main";
	}
}
