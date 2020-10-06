package goals.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import goals.model.dto.GoalsDTO;


@Repository
public class GoalsDAOImpl implements GoalsDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	public GoalsDTO selectOne(int goal_no) throws SQLException {
		return sqlSession.selectOne("goals.selectOne", goal_no);
	}

	@Override
	public List<GoalsDTO> selectAllById(String id) throws SQLException {
		List<GoalsDTO> goalList = sqlSession.selectList("goals.selectAllById", id);
		return goalList;
	}

	@Override
	public boolean insertGoal(GoalsDTO goal) throws SQLException {
		sqlSession.insert("goals.insertGoal",goal);
		return false;
	}

	@Override
	public void modifyGoal(GoalsDTO goal) throws SQLException {
		sqlSession.update("goals.modifyGoal", goal);
	}

	@Override
	public void deleteGoal(int goal_no) throws SQLException {
		sqlSession.delete("goals.deleteGoal",goal_no);
	}

}
