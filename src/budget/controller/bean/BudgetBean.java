package budget.controller.bean;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import budget.service.bean.BudgetService;
import category.service.bean.CategoryService;

@Controller
@RequestMapping("/budget/")
public class BudgetBean {

	@Autowired
	private BudgetService budgetService = null;
	
	@Autowired
	private CategoryService categoryService = null;
	
	@RequestMapping("setBudget.moa")
	public String LCsetBudgetForm(HttpServletRequest request, Model model) throws SQLException {
		
		String id = (String) request.getSession().getAttribute("memId");
		
		//회원의 지출카테고리 불러오기
		List categoryList = categoryService.selectAllById(id);
		
		
		model.addAttribute("categoryList", categoryList);
		return "budget/setBudget";
	}
}
