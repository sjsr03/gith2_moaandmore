package team.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import member.model.dao.MemberDAOImpl;
import team.model.dao.TeamMemberDAOImpl;
import team.model.dto.TeamDTO;
import team.service.bean.TeamMemberServiceImpl;
import team.service.bean.TeamServiceImpl;

@Controller
@RequestMapping("/team/")
public class TeamBean {
	
	@Autowired
	private TeamServiceImpl teamService = null;
	
	@Autowired
	private TeamMemberServiceImpl teamMemService = null;
	

	@RequestMapping("groupList.moa")
	public String viewList(String pageNum, Model model) throws SQLException {
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int pageSize = 6;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		int count = teamService.getTeamArticleCount();
		
		if(count>0) {
			articleList = teamService.getTeamArticles(startRow, endRow);
		}
		number = count-(currPage-1)*pageSize;
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("currPage", new Integer(currPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);
		model.addAttribute("count", new Integer(count));
		
		return "team/groupList";
	}
	
	@RequestMapping("groupOpenForm.moa")
	public String groupOpenForm(@ModelAttribute("dto") TeamDTO dto) {
		return "team/groupOpenForm";
	}
	
	@RequestMapping("groupOpenPro.moa")
	public String groupOpenPro(TeamDTO dto, String join_mem_nick) throws SQLException{
		teamService.insertTeamArticle(dto);
		
		if(join_mem_nick!=null) {
			if(!join_mem_nick.equalsIgnoreCase("")) {
				teamMemService.insertAll(dto, join_mem_nick);
			}
		}
		
		return "team/groupOpenPro";
	}
	
	@RequestMapping("teamDetail.moa")
	public String teampDetail(@RequestParam("team_no")int team_no, Model model) throws SQLException{
		TeamDTO team = teamService.selectOne(team_no);
		
		model.addAttribute("team", team);
		return "team/groupDetail";
	}	
	
		
}