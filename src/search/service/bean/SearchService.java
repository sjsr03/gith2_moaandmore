package search.service.bean;

import java.sql.SQLException;
import java.util.List;



public interface SearchService {
//public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum)throws SQLException;
	
	// 키워드로 개수 검색하기
	public int CountAllRecordByKeyword(String keyword)throws SQLException;
	
	public List selectAllOutcomeCategory() throws SQLException;
	
	public List searchListByContent(String queryStr) throws SQLException; 
}
