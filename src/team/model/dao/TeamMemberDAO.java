package team.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import team.model.dto.TeamMemberDTO;

@Repository
public interface TeamMemberDAO {
	
	//그룹참여시 팀원 한명 추가
	public void insertOne(TeamMemberDTO member) throws SQLException;
	
	//그룹개설시 팀원 여러명 추가
	public void insertAll() throws SQLException;
	
	//team_no 팀원리스트 가져오기
	public List<TeamMemberDTO> selectAllByTeam(int team_no) throws SQLException;
	

}
