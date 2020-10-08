package category.controller.bean;

import java.sql.SQLException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

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

	//카테고리 설정 페이지 불러오기
	@RequestMapping("setCategory.moa")
	public String setCategory(Model model,HttpServletRequest request) throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
	
		String already = request.getParameter("already");
			
		model.addAttribute("already", already);
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		

		
		return "category/setCategory";
	}
	//카테고리 추가하기
	@RequestMapping("setCategoryPro.moa")
	public String setCategoryPro(String category_name,String categoryOption,Model model) throws SQLException{
		
	
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		//카테고리명 안겹치는지 확인
		boolean already = false;
		//수입카테고리 추가하기
		if(categoryOption.equals("수입")) {
			
			List incomeCategoryNames = categoryService.selectIncomeCategoryNamesbyId(id);
			//지출카테고리 이름 겹치는지 검사
			for(int i=0;i<incomeCategoryNames.size(); i++) {
				String incomeCategoryName = (String)incomeCategoryNames.get(i);
				
				if(incomeCategoryName.equals(category_name)) {
					
					already = true;
					break;
				}
			}
			//이름이 안겹치면 수정 가능
			if(already == false) {
				categoryService.addIncomeCategory(category_name,id);
			}		
			
			
			
		//지출 카테고리 추가하기
		}else if(categoryOption.equals("지출")) {
			List outcomeCategoryNames = categoryService.selectOutcomeCategoryNamesbyId(id);
			//지출카테고리 이름 겹치는지 검사
			
			
			for(int i=0;i<outcomeCategoryNames.size(); i++) {
				String outcomeCategoryName = (String)outcomeCategoryNames.get(i);
				
				if(outcomeCategoryName.equals(category_name)) {

					
					
					already = true;
					model.addAttribute("already",already);
					break;

				}
			}
			//이름이 안겹치면 수정 가능
			if(already == false) {
				categoryService.addOutcomeCategory(category_name,id);
			}		
			
			
		}
		
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
		
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		model.addAttribute("already",already);
	
	

	
		return "category/setCategoryPro";

	}
	//카테고리 수정하기
	@RequestMapping("updateCategory.moa")
	public String updateoutcomeCategory(Model model,String inorout,int category_no,String newName) throws SQLException {
		
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		String already = "false";
		//지출카테고리 수정
		if(inorout.equals("outcome")) {
			List outcomeCategoryNames = categoryService.selectOutcomeCategoryNamesbyId(id);
			
				
			//지출카테고리 이름 겹치는지 검사
			for(int i=0;i<outcomeCategoryNames.size(); i++) {
				String outcomeCategoryName = (String)outcomeCategoryNames.get(i);
				
				if(outcomeCategoryName.equals(newName)) {
					already ="true";
				}
			}
			//이름이 안겹치면 수정 가능
			if(!already.equals("true")) {
				
				categoryService.updateoutcomeCategory(category_no,newName,id);
				
			}	
			
		
			
		//수입카테고리 수정
		}else if(inorout.equals("income")) {
			
			List incomeCategoryNames = categoryService.selectIncomeCategoryNamesbyId(id);
			
			//수입카테고리 이름 겹치는지 검사
			for(int i=0;i<incomeCategoryNames.size(); i++) {
				String incomeCategoryName = (String)incomeCategoryNames.get(i);
				
				if(incomeCategoryName.equals(newName)) {
					already = "true";
				}
			
			}
			//이름이 안겹치면 수정 가능
			if(!already.equals("true")){
				categoryService.updateincomeCategory(category_no,newName,id);
				
			}
			
		}
		
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
		
		System.out.println(already);
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		model.addAttribute("already", already);
		
		
		return "category/setCategory";
	}
	//카테고리 삭제하기
	@RequestMapping("deleteCategory.moa")
	public String deleteCategory(int category_no,Model model,String inorout) throws SQLException{
		
		String id= (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		//budget테이블,nobudget테이블에 해당 category_no가 있는지 확인
		//exist 1이면 삭제 불가
		int exist = categoryService.selectCategoryInfo(category_no,id);	
		
		
		if(exist == 0) {
		
			if(inorout.equals("outcome")) {
				
				categoryService.deleteOutcomeCategory(category_no,id);
				
			}else if(inorout.equals("income")) {
				categoryService.deleteIncomeCategory(category_no,id);
			}
			
		}
		
		List income = categoryService.selectAllIncomeCategoryById(id);
		List outcome = categoryService.selectAllById(id);
		
		
		
		model.addAttribute("income",income);
		model.addAttribute("outcome", outcome);
		model.addAttribute("exist", exist);
		
		
		return "category/setCategory";
	}
	
	
}
