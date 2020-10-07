package goals.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import goals.model.dto.GoalsDTO;
import team.model.dto.TeamDTO;


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
	public void insertGoalByTeam(GoalsDTO goal) throws SQLException {
		//team정보 와서 goal 셋팅
		TeamDTO teamDetail = sqlSession.selectOne("team.selectOne", goal.getTeam_no());
		//subject, amount, start_day,end_day, isopen
		goal.setSubject(teamDetail.getSubject());
		goal.setTarget_money(teamDetail.getAmount());
		Timestamp st_day = Timestamp.valueOf(teamDetail.getStart_day());
		Timestamp ed_day = Timestamp.valueOf(teamDetail.getEnd_day());	
		goal.setStart_day(st_day);
		goal.setEnd_day(ed_day);
		goal.setPublic_ch('1');
		char pb_type = teamDetail.getIsopen().charAt(0);
		goal.setPublic_type(pb_type);
		
		System.out.println(goal.toString());
		
		sqlSession.insert("goals.insertGoal",goal);
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
