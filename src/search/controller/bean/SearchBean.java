package search.controller.bean;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import search.service.bean.SearchService;

@Controller
@RequestMapping("/search/")
public class SearchBean {
	
	//@Autowired 
	//SearchService searchService = null;
	
	@RequestMapping(value="searchForm.moa")
	public String searchForm(String keyword)throws SQLException{
		
		return "search/searchForm";
	}
	
	public String searchPro()throws SQLException{
		
		return "search/searchPro";
	}
}
