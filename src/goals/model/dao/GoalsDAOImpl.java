package goals.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
	public List<GoalsDTO> selectAllByPublicCh(HashMap map) throws SQLException {
		
		List<GoalsDTO> goalList = sqlSession.selectList("goals.selectAllByPublicCh", map);
		return goalList;
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
	public void insertGoalByTeam(String id, int team_no) throws SQLException {
		//team정보 와서 goal 셋팅
		TeamDTO teamDetail = sqlSession.selectOne("team.selectOne", team_no);
		//subject, amount, start_day,end_day, isopen

		GoalsDTO goal = new GoalsDTO();
				
		goal.setId(id);
		goal.setTeam_no(team_no);
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
	public void deleteGoal(int goal_no, int public_ch, int team_no) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("goal_no", goal_no);
		map.put("public_ch", public_ch);
		map.put("team_no", team_no);
		
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		map.put("id", id );
		
		
		sqlSession.delete("goals.deleteGoal",map);
	}

	@Override
	public void updateSaving(int goal_no, int sum) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("goal_no",goal_no);
		map.put("sum", sum);
		
		sqlSession.update("goals.updateSaving",map);
		
	}
	
	@Override
	public HashMap selectMostPersonalGoal(String id) throws SQLException {
		HashMap map = sqlSession.selectOne("goals.selectMostPersonalGoal", id);
		return map;
	}
	
	@Override
	public HashMap selectMostTeamGoal(String id) throws SQLException {
		HashMap map = sqlSession.selectOne("goals.selectMostTeamGoal", id);
		return map;
	}

	

	

}
