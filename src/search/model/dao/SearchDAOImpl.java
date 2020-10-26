package search.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import search.model.dto.SearchDTO;

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

	@Override
	public List selectAllOutcomeCategory(String id) throws SQLException {
		List list = sqlSession.selectList("category.selectAllById", id);
		return list;
	}

	@Override
	public List selectSearchList(Map map) throws SQLException {
		List list = sqlSession.selectList("search.SelectSearchList", map);
		return list;
	}

}
