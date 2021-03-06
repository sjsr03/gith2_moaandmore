package budget.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import budget.model.dao.LeftMoneyDAO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.model.dto.LeftMoneyDTO;
import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;
import category.model.dto.outcome_categoryDTO;
import category.service.bean.CategoryService;
import goals.model.dto.GoalsDTO;
import goals.service.GoalsService;

@Controller
@RequestMapping("/budget/")
public class BudgetBean {

	@Autowired
	private BudgetService budgetService = null;
	@Autowired
	private CategoryService categoryService = null;
	@Autowired
	private GoalsService goalsService = null;
	
	@RequestMapping("setBudget.moa")
	public String LCsetBudgetForm(HttpServletRequest request, Model model) throws SQLException {
		
		String id = (String) request.getSession().getAttribute("memId");
		
		TotalBudgetDTO currentTBudget = budgetService.selectCurrentOne(id);
		
		//회원의 지출카테고리 불러오기
		List categoryList = categoryService.selectAllById(id);
		
		model.addAttribute("categoryList", categoryList);
		
		if(currentTBudget != null) {	//현재 진행중인 예산이 있다면
			model.addAttribute("currentTBudget", currentTBudget);
			model.addAttribute("detailList", budgetService.selectAllbyBudgetNum(currentTBudget.getBudget_no()));
			if(currentTBudget.getPeriod()==30) {
				if(currentTBudget.getEnd_day().getDate() >= 28) {
					model.addAttribute("firstOfMonth", 1);
				} else {
					int firstOfMonth = (currentTBudget.getEnd_day().getDate()+1);
					model.addAttribute("firstOfMonth", firstOfMonth);
				}
			} else {
				model.addAttribute("firstOfMonth", 1);
			}
			return "budget/updateBudget";
		} else {	//진행중인 예산이 없다면 
			return "budget/setBudget";
		}
		
	}
	
	@RequestMapping("setBudgetPro.moa")
	public String setBudgetPro(HttpServletRequest request) throws SQLException {
		String isNewBudget = request.getParameter("isNewBudget");
		if(isNewBudget.equals("1")) { //새로운 예산 생성이면
			budgetService.setBudget();
		} else {//기존 예산 수정이면
			budgetService.updateBudget();
		}
		return "redirect:../main.moa";
	}
	
	@RequestMapping("todayBudget.moa")
	public String LCtodayBudget(HttpServletRequest request, Model model) throws SQLException {
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
		
		
		//회원의 목표 리스트 가져오기
		List goals = goalsService.selectTransferPossibleGoals(id);
		model.addAttribute("personalGoals", (List)goals.get(0));
		model.addAttribute("teamGoals", (List)goals.get(1));
		model.addAttribute("period", period);
		
		
		
		// 남은돈 정보
		
		List leftMoneyList = budgetService.selectLeftMoneyById(id);
		int LMSum = 0;
		for(Object obj:leftMoneyList) {
			LMSum+= ((LeftMoneyDTO)obj).getAmount();
		}
		
		model.addAttribute("leftMoney", leftMoneyList);
		model.addAttribute("LMSum", LMSum);
		model.addAttribute("categories", categories);
		model.addAttribute("TBdto", TBdto);
		model.addAttribute("BDdtoList", BDdtoList);
		model.addAttribute("period", period);
		
		List todayData = budgetService.selectTodayBudget(id);
		
		int todaySum = budgetService.selectSumTodayBudget(id);
		
		model.addAttribute("todayData", todayData);
		model.addAttribute("todaySum", todaySum);
		
		
		
		return "budget/todayBudget";
	}
	
	
	
	@RequestMapping("LeftMoneyTransfer.moa")
	public String LCeftMoneyTransfer() throws SQLException {
		budgetService.LeftMoneyTransfer();
		
		
		return "budget/LeftMoneyTransfer";
	}
	
	@RequestMapping(value = "getWarnMessage.moa", produces = "application/text; charset=utf-8")
	@ResponseBody
	public String getWarnMessage(int firstOfMonth) {
		String message = "이번 예산 주기는 ";
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		message += sdf.format(today) + "부터 다음 월 시작일 전날인 ";
		
		if(today.getDate() >= firstOfMonth-1) {	//설정한 월 시작일이 이번달 기준 이미 지난 경우 = 다음달 월 시작일 전날까지
			today.setMonth(today.getMonth()+1);
		} 
		
		today.setDate(firstOfMonth-1);
		
		message += sdf.format(today.getTime()) + "까지입니다.";
		
		
		return message;
	}
}
