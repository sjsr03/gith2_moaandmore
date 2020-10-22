package search.service.bean;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
