package budget.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import budget.model.dao.LeftMoneyDAO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.model.dto.LeftMoneyDTO;
import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;
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
			
			return "budget/updateBudget";
		} else {	//진행중인 예산이 없다면 
			return "budget/setBudget";
		}
		
	}
	
	@RequestMapping("setBudgetPro.moa")
	public String setBudgetForm(HttpServletRequest request) throws SQLException {
		String isNewBudget = request.getParameter("isNewBudget");
		if(isNewBudget.equals("1")) { //새로운 예산 생성이면
			budgetService.setBudget();
		} else {//기존 예산 수정이면
			budgetService.updateBudget();
		}
		return "main";
	}
	
	@RequestMapping("todayBudget.moa")
	public String LCtodayBudget(HttpServletRequest request, Model model) throws SQLException {
		String id = (String) request.getSession().getAttribute("memId");
		//현재 진행중인 예산 정보 가져오기
		TotalBudgetDTO TBdto = budgetService.selectCurrentOne(id);
		List BDdtoList = budgetService.selectAllbyBudgetNum(TBdto.getBudget_no());
		
		//현재 예산의 카테고리정보 가져오기
		List categoryNums = new ArrayList();
		for (Object obj:BDdtoList) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO)obj;
			categoryNums.add(dto.getCategory_no());
		}
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		
		
		//회원의 목표 리스트 가져오기
		List<GoalsDTO> goals = goalsService.selectAllById();
		
		
		// 남은돈 정보
		
		List leftMoneyList = budgetService.selectLeftMoneyById(id);
		
		
		model.addAttribute("leftMoney", leftMoneyList);
		model.addAttribute("categories", categories);
		model.addAttribute("TBdto", TBdto);
		model.addAttribute("BDdtoList", BDdtoList);
		model.addAttribute("goals", goals);
		
		return "budget/todayBudget";
	}
	
	
	
	@RequestMapping("LeftMoneyTransfer.moa")
	public String LeftMoneyTransfer() {
		
		
		
		return "budget/LeftMoneyTransfer";
	}
}
