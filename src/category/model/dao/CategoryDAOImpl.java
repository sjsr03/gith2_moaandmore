package category.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public List selectAllById(String id) throws SQLException {
		List list = sqlSession.selectList("category.selectAllById", id);
		return list;
	}
	
	@Override
	public int selectNumByName(String name, String id) throws SQLException {
		HashMap map = new HashMap();
		map.put("category_name", name);
		map.put("id", id);
		return sqlSession.selectOne("category.selectNumByName", map);
	}


	@Override
	public List selectAllIncomeCategoryById(String id) throws SQLException {
		List list = sqlSession.selectList("category.selectAllIncomeCategoryById",id);
		
		
		return list;
	}

	
	
	
	
	@Override
	public void outcomeInsertCategory(String id) throws SQLException {
		
		List list = new ArrayList();
		list.add("식비");
		list.add("교통/차량");
		list.add("문화생활");
		list.add("패션/미용");
		list.add("생활용품");
		list.add("주거/통신");
		list.add("건강");
		list.add("교육");
		list.add("경조사/회비");
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("id", id);
		
	
		
		sqlSession.insert("category.insertOutComeCategory",map);
		
		
	}


	@Override
	public void incomeInsertCategory(String id) throws SQLException {
		
		List list = new ArrayList();
		list.add("월급");
		list.add("보너스");
		list.add("용돈");
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("id",id);
		
		sqlSession.insert("category.insertInComeCategory",map);
		
		
	}


	@Override
	public void addIncomeCategory(String category_name,String id) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("category_name", category_name);
		map.put("id",id);
		
		sqlSession.insert("category.addIncomeCategory", map);
		
		
	}

	@Override
	public void addOutcomeCategory(String category_name,String id) throws SQLException {
		
		
		HashMap map = new HashMap();
		map.put("category_name", category_name);
		map.put("id",id);
		
		sqlSession.insert("category.addoutcomeCategory", map);
		
	}


	
}
