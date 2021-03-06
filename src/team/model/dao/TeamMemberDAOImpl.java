package team.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import team.model.dto.TeamMemberDTO;

@Repository
public class TeamMemberDAOImpl implements TeamMemberDAO {

	@Autowired 
	SqlSessionTemplate sqlSession = null;

	@Override
	public void insertOne(TeamMemberDTO member) throws SQLException {
		sqlSession.insert("teamMember.insertOne", member);
		
	}
	
	@Override
	public void insertAll() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TeamMemberDTO> selectAllByTeam(int team_no) throws SQLException {
		List<TeamMemberDTO> list = sqlSession.selectList("teamMember.selectAllByTeamNo", team_no);
		
		return list;
		
		
		
	}

	@Override
	public void deleteTeamMemberAll(int teamNo, int is_no_join) throws SQLException {
		HashMap map = new HashMap();
		map.put("teamNo", teamNo);
		map.put("is_no_join", is_no_join);
		
		sqlSession.delete("teamMember.deleteTeamMemberAll", map);
	}

	@Override
	public void updateTeamMemJoin(int team_no, String nickname) throws SQLException {
		HashMap map = new HashMap();
		map.put("team_no", team_no);
		map.put("nickname", nickname);
		
		sqlSession.update("teamMember.updateTeamMemJoin", map);		
	}

}
