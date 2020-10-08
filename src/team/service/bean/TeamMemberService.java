<<<<<<< HEAD
package team.service.bean;

import java.sql.SQLException;

import team.model.dto.TeamDTO;

public interface TeamMemberService {
	//그룹개설시 팀원 여러명 추가
	public void insertAll(TeamDTO teamDTO, String nicknames) throws SQLException;
}
=======
package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import team.model.dto.TeamMemberDTO;


public interface TeamMemberService {

	public List<TeamMemberDTO> selectAllbyTeamNo(int team_no) throws SQLException;
	
}
>>>>>>> branch 'develop' of https://github.com/sjsr03/gith2_moaandmore.git
