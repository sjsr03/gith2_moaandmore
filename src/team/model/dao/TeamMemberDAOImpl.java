package team.model.dao;

import java.sql.SQLException;
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
		System.out.println(member.toString());
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
	public void deleteTeamMemberAll(int teamNo) throws SQLException {
		sqlSession.delete("teamMember.deleteTeamMemberAll", teamNo);
	}

}
