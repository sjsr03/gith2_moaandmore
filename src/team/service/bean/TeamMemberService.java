package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import team.model.dto.TeamMemberDTO;


public interface TeamMemberService {

	public List<TeamMemberDTO> selectAllbyTeamNo(int team_no) throws SQLException;
	
}
