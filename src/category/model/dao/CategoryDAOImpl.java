package category.model.dao;

import java.sql.SQLException;
import java.util.List;

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
	
}
