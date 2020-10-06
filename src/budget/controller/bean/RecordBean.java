package budget.controller.bean;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.service.bean.RecordService;
import category.service.bean.CategoryService;


@Controller
@RequestMapping("/record/")
public class RecordBean {

	@Autowired
	private RecordService recordService = null;
	
	@Autowired
	private CategoryService categoryService = null;
	
	@RequestMapping("recordForm.moa")
	public String recordForm(HttpServletRequest request, Model model)throws SQLException {
		// 여기에서 해당 회원의 카테고리까지 한번에 다 가져올 것임
		// 가져와서 model로 다 뿌려주기!!! (수입, 지출, 예산지출 list에 담아서)
		
		//String id = (String)request.getSession().getAttribute("memId");
		List outcomeCategories = categoryService.selectAllById("test50");
		model.addAttribute("outcomeCategories", outcomeCategories);
		System.out.println(outcomeCategories.size()); 
		
		return "budget/recordForm";
	}
	
	@RequestMapping(value="recordPro.moa", method=RequestMethod.POST)
	public String recordPro(MultipartHttpServletRequest request) throws Exception{
		System.out.println("category 나오니? :" +request.getParameter("category"));
		return "budget/moneyLog";
	}
	

}
