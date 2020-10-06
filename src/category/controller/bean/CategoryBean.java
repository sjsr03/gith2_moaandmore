package category.controller.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import category.service.bean.CategoryService;


@Controller
@RequestMapping("/category/")
public class CategoryBean {

	@Autowired
	private CategoryService categoryService = null;


	@RequestMapping("setCategory.moa")
	public String setCategory(Model model) throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
	
		
		
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		
		
		return "category/setCategory";
	}
	
	@RequestMapping("setCategoryPro.moa")
	public String setCategoryPro(String category_name,String categoryOption,Model model) throws SQLException{
		
	
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		
		if(categoryOption.equals("수입")) {
			categoryService.addIncomeCategory(category_name,id);
			
		}else if(categoryOption.equals("지출")) {
			categoryService.addOutcomeCategory(category_name,id);
		}
		
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
		
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		
		
		
		
		return "category/setCategory";
	}

	
	
}
