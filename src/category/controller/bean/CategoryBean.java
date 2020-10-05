package category.controller.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import category.service.bean.CategoryService;

@Controller
public class CategoryBean {

	@Autowired
	private CategoryService categoryService = null;
}
