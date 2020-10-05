package team.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import team.model.dao.TeamDAOImpl;

@Controller
@RequestMapping("/team/")
public class TeamBean {
	
	@Autowired
	private TeamDAOImpl teamDAO = null;

	@RequestMapping("group_list.git")
	public String viewList() throws SQLException {
		return "community/group_list";
	}
		
}