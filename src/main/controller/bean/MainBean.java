package main.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import category.service.bean.CategoryService;
import main.service.bean.MainService;

@Controller
public class MainBean {
	
	@Autowired
	private BudgetService budgetService = null;
	@Autowired
	private MainService mainService = null;
	@Autowired
	private CategoryService categoryService = null;
	
	
	@RequestMapping("main.moa")
	public String NLmain(HttpServletRequest request, Model model) throws SQLException {
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
			return "main";
		}
		
		
	}
	
	
	@RequestMapping("getBudgetState.moa")
	@ResponseBody
	public HashMap NLgetBudgetState(String id) throws SQLException {
		HashMap map = new HashMap();
		TotalBudgetDTO totalBudget = budgetService.selectCurrentOne(id);
		
		//만약 현재 설정된 예산이 없다면
		if(totalBudget == null) {
			return null;
		} else {
			//현재 예산에서 총 소비액 불러오기
			int outcomeSum = mainService.selectOutcomeSumByBudgetId(totalBudget.getBudget_no(), id);
			map.put("totalBudget", totalBudget.getBudget());
			map.put("outcomeSum", outcomeSum);
			
			return map;
		}
	}
	
	@RequestMapping("dashboard.moa")
	public String LCdashboard(HttpServletRequest request, Model model) throws SQLException {
		String id = (String) request.getSession().getAttribute("memId");
		//현재 진행중인 예산 정보 가져오기
		TotalBudgetDTO TBdto = budgetService.selectCurrentOne(id);
		
		List BDdtoList = budgetService.selectAllbyBudgetNum(TBdto.getBudget_no());
		long lt = TBdto.getEnd_day().getTime()-TBdto.getStart_day().getTime();
		int period = Math.round((lt)/(1000*60*60*24)) + 1;
		
		//현재 예산의 카테고리정보 가져오기
		List categoryNums = new ArrayList();
		for (Object obj:BDdtoList) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO)obj;
			categoryNums.add(dto.getCategory_no());
		}
		
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		categories.put(0, "총예산");
				
		
		//오늘 소비량
		List todayData = budgetService.selectTodayBudget(id);
		
		//회원의 총 leftmoney 합산
		int LMsum = budgetService.selectLeftMoneySum(id);
		
		//회원의 목표 중 달성도가 가장 높은 것
		List goalsList = mainService.selectMostGoals(id);
		
		//오늘의 예산 총합
		int todaySum = budgetService.selectSumTodayBudget(id);
		
		model.addAttribute("TBdto", TBdto);
		model.addAttribute("categories", categories);
		model.addAttribute("todayData", todayData);
		model.addAttribute("todaySum", todaySum);
		model.addAttribute("LMsum", LMsum);
		model.addAttribute("MPgoal", (HashMap)goalsList.get(0));
		model.addAttribute("MTgoal", (HashMap)goalsList.get(1));
		
		return "dashboard";
	}
}
