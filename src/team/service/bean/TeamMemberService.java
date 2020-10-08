package team.service.bean;

import java.sql.SQLException;

import team.model.dto.TeamDTO;

public interface TeamMemberService {
	//그룹개설시 팀원 여러명 추가
	public void insertAll(TeamDTO teamDTO, String nicknames) throws SQLException;
}
