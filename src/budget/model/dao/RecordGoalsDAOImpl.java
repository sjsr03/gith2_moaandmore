package budget.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.RecordGoalsDTO;


@Repository
public class RecordGoalsDAOImpl implements RecordGoalsDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public List<RecordGoalsDTO> selectAll(int goals_no, String id) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("goals_no", goals_no);
		map.put("id", id);

		return sqlSession.selectList("recordGoals.selectAll", map);
	}
	
	@Override
	public List selectAllByIdAndNum(HashMap map) {
		List list = sqlSession.selectList("recordGoals.selectAllByIdAndNum", map);
		return list;
	}
	
	@Override
	public List selectNumListById(String id) {
		List list = sqlSession.selectList("recordGoals.selectNumListById", id);
		return list;
	}
	

}
