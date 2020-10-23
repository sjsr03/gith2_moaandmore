package search.model.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDAOImpl implements SearchDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public int CountAllRecordByKeyword(String keyword) throws SQLException {
		int count = 0;
		// budget+nobudget 값 더해서 가져옴 
		
		count = sqlSession.selectOne("search.CountAllBudgetRecordByKeyword", keyword);
		return count;
	}

}
