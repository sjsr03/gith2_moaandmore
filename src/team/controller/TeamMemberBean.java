package team.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import team.model.dto.TeamMemberDTO;
import team.service.bean.TeamMemberServiceImpl;

@Controller
@EnableWebMvc
@RequestMapping("teamMember")
public class TeamMemberBean {
	
	@Autowired
	private TeamMemberServiceImpl  teamMemberService = null;
	
	@RequestMapping("selectAllByTeamNo.moa")
	public @ResponseBody List<TeamMemberDTO> selectAllByTeamNO(String team_no) throws SQLException {
		
		int	parse_team_no = Integer.parseInt(team_no);
		return teamMemberService.selectAllbyTeamNo(parse_team_no);
	}
	
	
}
