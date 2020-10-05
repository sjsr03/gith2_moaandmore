package goals.model.dao;

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
	public GoalsDTO selectOne(int goal_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GoalsDTO> selectAllById(String id) {
		List<GoalsDTO> goalList = sqlSession.selectList("goals.selectAllById", id);
		return goalList;
	}

	@Override
	public boolean insertGoal(GoalsDTO goal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyGoal(GoalsDTO goal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGoal(int goal_no) {
		// TODO Auto-generated method stub
		return false;
	}

}
