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
	public void insertCategory(String id) throws SQLException {
		
		List list = new ArrayList();
		list.add("병원비");
		list.add("교통비");
		list.add(id);
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("id", id);
		
		
		
		sqlSession.insert("category.insertOutComeCategory",map);
		
		
	}
	
}
