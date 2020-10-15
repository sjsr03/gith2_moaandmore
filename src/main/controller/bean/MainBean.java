package main.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import budget.model.dto.TotalBudgetDTO;
import budget.model.dto.TotalBudgetDetailDTO;
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
			} else {
				return "redirect:dashboard.moa";
			}
		} else {
			return "nonMemberMain";
		}
		
		
	}
	
	
	@RequestMapping("getBudgetState.moa")
	@ResponseBody
	public HashMap getBudgetState(String id) throws SQLException {
		HashMap map = new HashMap();
		TotalBudgetDTO totalBudget = budgetService.selectCurrentOne(id);
		
		//만약 현재 설정된 예산이 없다면
		if(totalBudget == null) {
			return null;
		} else {
			//현재 예산에서 총 소비액 불러오기
			int outcomeSum = mainService.selectOutcomeSumByBudgetId(totalBudget.getBudget_no());
			map.put("totalBudget", totalBudget.getBudget());
			map.put("outcomeSum", totalBudget.getBudget()-totalBudget.getCurrent());
			
			return map;
		}
	}
	
	@RequestMapping("dashboard.moa")
	public String dashboard(HttpServletRequest request) throws SQLException {
		String id = (String) request.getSession().getAttribute("memId");
		//현재 진행중인 총예산 정보
		TotalBudgetDTO totalBudget = budgetService.selectCurrentOne(id);
		long time = totalBudget.getEnd_day().getTime()-totalBudget.getStart_day().getTime();
		int period = (new Date(time).getDate())-1;
		if (period==0) period=1;
		//해당 카테고리별 세부정보
		List TBDlist = budgetService.selectAllbyBudgetNum(totalBudget.getBudget_no()) ;
		
		
		for (Object obj:TBDlist) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) obj;
			dto.setCategory_budget(dto.getCategory_budget()/period);
			//TBDlist에는 하루치 예산이 담기게 된다!
		}
		
		//오늘 소비량
		
		
		
		
		
		return "dashboard";
	}
}
