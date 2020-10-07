package team.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.model.dao.TeamDAOImpl;
import team.model.dto.TeamDTO;

@Controller
@RequestMapping("/team/")
public class TeamBean {
	
	@Autowired
	private TeamDAOImpl teamDAO = null;

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
		int count = teamDAO.getTeamArticleCount();
		
		if(count>0) {
			articleList = teamDAO.getTeamArticles(startRow, endRow);
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
		
		return "team/GroupList";
	}
	
	@RequestMapping("groupOpenForm.moa")
	public String groupOpenForm(@ModelAttribute("dto") TeamDTO dto) {
		return "team/GroupOpenForm";
	}
	
	@RequestMapping("groupOpenPro.moa")
	public String groupOpenPro(TeamDTO dto) throws SQLException{
		teamDAO.insertTeamArticle(dto);
		
		return "team/GroupOpenPro";
	}
	
	@RequestMapping("teamDetail.moa")
	public String teampDetail(@RequestParam("team_no") int team_no){
		
		
		return "team/groupDetail";
	}	
	
		
}