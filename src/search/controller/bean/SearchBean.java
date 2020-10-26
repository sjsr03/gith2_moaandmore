package search.controller.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import search.service.bean.SearchService;

@Controller
@RequestMapping("/search/")
public class SearchBean {
	
	@Autowired 
	SearchService searchService = null;
	
	@RequestMapping(value="searchForm.moa")
	public String searchForm(String keyword, Model model)throws SQLException{
		
		//예산 카테고리 가져오기 
		List outcomeCategoryList = searchService.selectAllOutcomeCategory();
		model.addAttribute("outcomeCategoryList", outcomeCategoryList);
		
		return "search/searchForm";
	}
	
	@RequestMapping(value="searchPro.moa")
	public @ResponseBody List searchPro(@RequestBody String queryString) throws SQLException{
		System.out.println(queryString.length());
		List list = new ArrayList();
		if(queryString.length() == 51) {
			return list;
		}else {
			list =	searchService.searchListByContent(queryString);
		}
		return list;
	}
}
