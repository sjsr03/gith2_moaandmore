package search.model.dao;

import java.sql.SQLException;

public interface SearchDAO {

	// 키워드로 개수  검색
	public int CountAllRecordByKeyword(String keyword)throws SQLException;
}
