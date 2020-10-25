package search.service.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import search.model.dao.SearchDAO;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	SearchDAO searchDAO = null;
	
	@Override
	public int CountAllRecordByKeyword(String keyword) throws SQLException {
		int count = 0;
		count = searchDAO.CountAllRecordByKeyword(keyword);
		return count;
	}

	@Override
	public List selectAllOutcomeCategory() throws SQLException {
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		List OutcomeCategoryList = searchDAO.selectAllOutcomeCategory(id);
		return OutcomeCategoryList;
	}

	@Override
	public List searchListByContent(String queryStr) throws SQLException {
		// startday=2020.10.20&endday=2020.10.25&search_category=5&search_content=커피
		String[] querySplit = queryStr.split("&");
		Map map = new HashMap();
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		map.put("id", id);
		for(int i =0; i< querySplit.length; i++) {
			String[] querySplit2 = querySplit[i].split("=");		
			if(querySplit2[0].equals("search_category")) {
				int parseint = Integer.parseInt(querySplit2[1]);
				map.put(querySplit2[0],parseint);
			}else{
				map.put(querySplit2[0],querySplit2[1]);
			}
		}
		List list = searchDAO.selectSearchList(map);
		
		return list;
		
	}
	
}
