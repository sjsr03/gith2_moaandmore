package category.controller.bean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import category.service.bean.CategoryService;

@Controller
@RequestMapping("/category/")
public class CategoryBean {

	@Autowired
	private CategoryService categoryService = null;

	//카테고리 설정 페이지 불러오기
	@RequestMapping("setCategory.moa")
	public String setCategory(Model model,HttpServletRequest request) throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
	
		String already = request.getParameter("already");
		String exist = request.getParameter("exist");
		System.out.println("setCategory의 already : "+already);
		
		model.addAttribute("already", already);
		model.addAttribute("exist", exist);
		
		return "category/setCategory";
	}
	
	//지출카테고리 불러오기
	@RequestMapping("getExpenseCategoryList.moa")
	public @ResponseBody List getExpenseCateogryList() throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		List outcome = categoryService.selectAllById(id);

		return outcome;
	}
	
	
	//수입 카테고리 불러오기
	@RequestMapping("getIncomeCategoryList.moa")
	public @ResponseBody List getIncomeCategoryList() throws SQLException{
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);

		List income = categoryService.selectAllIncomeCategoryById(id);
		
		return income;
	}
	
	
	//카테고리 추가하기
	@RequestMapping(value="setCategoryPro.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public String setCategoryPro(String category_name,String categoryOption,Model model) throws SQLException{
	
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		//카테고리명 안겹치는지 확인
		int already =0;
		
		//수입카테고리 추가하기
		if(categoryOption.equals("수입")) {
			already = categoryService.addIncomeCategory(category_name,id);
		//지출 카테고리 추가하기
		}else if(categoryOption.equals("지출")) {
			 already = categoryService.addOutcomeCategory(category_name,id);	
			
		}
		
		
		//model.addAttribute("already",already);
		
	
		return "category/setCategoryPro";

	}
	
	//카테고리 수정하기
	@RequestMapping("updateCategory.moa")
	public String updateCategory(Model model,String inorout,int category_no,String newName) throws SQLException {
		
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		int  already = 0; // 카테고리 이름이 있는지
		
		//지출카테고리 수정
		if(inorout.equals("outcome")) {
			already = categoryService.updateoutcomeCategory(category_no,newName,id);
		//수입카테고리 수정
		}else if(inorout.equals("income")) {
			already = categoryService.updateincomeCategory(category_no,newName,id);
		}
		
		model.addAttribute("already", already);
		
		
		return "category/setCategoryPro";
	}
	
	//카테고리 삭제하기
	@RequestMapping("deleteCategory.moa")
	public String deleteCategory(int category_no,Model model,String inorout) throws SQLException{
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		
			int exist = 0; //exist 가 1이면 삭제 불가
			
			//지출 삭제
			if(inorout.equals("outcome")) {
				 exist = categoryService.deleteOutcomeCategory(category_no,id);
			//수입 삭제	
			}else if(inorout.equals("income")) {
				exist = categoryService.deleteIncomeCategory(category_no,id);
			}
			
		
		model.addAttribute("exist", exist);
		
		
		return "category/setCategoryPro";
	}

	
	
	
	
	
}
