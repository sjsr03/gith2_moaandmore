package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.model.dao.TeamMemberDAOImpl;
import team.model.dto.TeamMemberDTO;
@Service
public class TeamMemberServiceImpl implements TeamMemberService {

	@Autowired
	private TeamMemberDAOImpl teamMemberDAO = null;
	
	@Override
	public List<TeamMemberDTO> selectAllbyTeamNo(int team_no) throws SQLException {
		//String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		return teamMemberDAO.selectAllByTeam(team_no);
	}

}
