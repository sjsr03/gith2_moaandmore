package category.controller.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import category.service.bean.CategoryService;

@Controller
public class CategoryBean {

	@Autowired
	private CategoryService categoryService = null;


	@RequestMapping("setCategory.moa")
	public String setCategory() {
		
		//categoryService.
		
		return "";
	}
	
	


}
