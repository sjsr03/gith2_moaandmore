package search.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SearchDAO {

	// 키워드로 개수  검색
	public int CountAllRecordByKeyword(String keyword)throws SQLException;
	
	
	//id로 모든 지출카테고리 가져오기
	public List selectAllOutcomeCategory(String id) throws SQLException;
	
	public List selectSearchList(Map map) throws SQLException;
	
}
