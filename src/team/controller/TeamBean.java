package team.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.tools.DocumentationTool.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;

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
	public String viewList(String pageNum, String pageStatus, String isSearch, String search, String range, Model model) throws SQLException {
		if(pageNum == null) {
			pageNum = "1";
		}
		
		if(pageStatus == null) {
			pageStatus = "2";
		}
		
		if(isSearch == null)
			isSearch="0";
		
		if(search == null || search == "")
			search = "검색어를 입력하세요.";
		
		if(range == null)
			range = "0";
		

		List<TeamDTO> autoChangeList = null;
		
		autoChangeList = teamService.getTeamAll();

		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
		
		Date tmpToday = new Date();
				
		int today = Integer.parseInt(dateFormat.format(tmpToday));
		
		if(autoChangeList != null) {
			for(int i=0;i<autoChangeList.size();i++) {
				int startDate = Integer.parseInt(autoChangeList.get(i).getStart_day().substring(0, 10).replaceAll("-", ""));
				int endDate = Integer.parseInt(autoChangeList.get(i).getEnd_day().substring(0, 10).replaceAll("-", ""));
				
				int tmpStatus=autoChangeList.get(i).getStatus();
				
				if(today<startDate)
					tmpStatus = 1;
				else if(today>endDate)
					tmpStatus = 3;
				else
					tmpStatus = 2;
				
				if(tmpStatus != autoChangeList.get(i).getStatus()) {
					autoChangeList.get(i).setStatus(tmpStatus);
					teamService.updateTeamStatus(autoChangeList.get(i));
				}
			}
		}
		
		int pageSize = 6;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		List articleMemberAvgList = null;
		int count = teamService.getTeamArticleCount(Integer.parseInt(pageStatus),Integer.parseInt(isSearch),search);
		
		if(count>0) {
			articleList = teamService.getTeamArticles(Integer.parseInt(pageStatus),startRow, endRow,Integer.parseInt(isSearch),search,Integer.parseInt(range));
			articleMemberAvgList = teamMemService.getTeamAvgArticles(articleList);
		}
		
		number = count-(currPage-1)*pageSize;
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("isSearch", isSearch);
		model.addAttribute("search", search);
		model.addAttribute("range", Integer.parseInt(range));
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("currPage", new Integer(currPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);
		model.addAttribute("articleMemberAvgList", articleMemberAvgList);
		model.addAttribute("count", new Integer(count));
		
		return "team/groupList";
	}
	
	@RequestMapping("groupMyRequestList.moa")
	public String teamMyRequestList(String nickname, String pageNum, Model model) throws SQLException {
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		int count = teamService.getTeamMyRequestCount(nickname);
		
		if(count>0) {
			articleList = teamService.getTeamMyRequests(nickname,startRow, endRow);
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
		
		return "team/groupMyRequestList";
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
			}else {
				teamMemService.insertOne(dto);
			}
		}else {
			teamMemService.insertOne(dto);
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