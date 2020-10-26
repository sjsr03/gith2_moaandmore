package team.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import admin.model.dto.ModelDTO;
import team.model.dto.TeamDTO;
import team.model.dto.TeamMemberDTO;
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
	public String viewList(HttpServletRequest request, String isMyTeam, String pageNum, String pageStatus, String isSearch, String search, String range, Model model) throws SQLException {
		if(isMyTeam == null)
			isMyTeam = "0";
		
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
		
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("memName");


		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
		
		Date tmpToday = new Date();
				
		int today = Integer.parseInt(dateFormat.format(tmpToday));
		
		int lastUpdateDate = Integer.parseInt(teamService.getTeamUpdateTime().substring(0, 10).replaceAll("-", ""));
		
		if(today>lastUpdateDate) {

			List<TeamDTO> autoChangeList = null;
			
			autoChangeList = teamService.getTeamAll();
			
			if(autoChangeList != null) {
				for(int i=0;i<autoChangeList.size();i++) {
					int startDate = Integer.parseInt(autoChangeList.get(i).getStart_day().substring(0, 10).replaceAll("-", ""));
					int endDate = Integer.parseInt(autoChangeList.get(i).getEnd_day().substring(0, 10).replaceAll("-", ""));
					
					int tmpStatus=autoChangeList.get(i).getStatus();
					
					if(today<startDate)
						tmpStatus = 1;
					else if(today>endDate) {
						tmpStatus = 3;
						teamMemService.deleteTeamMemberAll(autoChangeList.get(i).getTeam_no(), 1);
					}else {
						tmpStatus = 2;
						teamMemService.deleteTeamMemberAll(autoChangeList.get(i).getTeam_no(), 1);
					}
					
					if(tmpStatus != autoChangeList.get(i).getStatus()) {
						autoChangeList.get(i).setStatus(tmpStatus);
						
						//소진
						teamService.updateTeamStatus(autoChangeList.get(i));
					}
				}
			}
			
			teamService.updateTeamUpdateTime(Integer.toString(today));
		}
		
		int pageSize = 6;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		List articleMemberAvgList = null;
		int count = 0;
		
		if(Integer.parseInt(isMyTeam) == 0) {
			count = teamService.getTeamArticleCount(Integer.parseInt(pageStatus),Integer.parseInt(isSearch),search);
		}else if(Integer.parseInt(isMyTeam) == 1) {
			//TODO 내가 가입된 개설 승인된 그룹들
			count = teamService.getMyOkTeamArticleCount(nickname, Integer.parseInt(pageStatus), Integer.parseInt(isSearch), search);
		}
		
		if(count>0) {
			if(Integer.parseInt(isMyTeam) == 0) {
				articleList = teamService.getTeamArticles(Integer.parseInt(pageStatus),startRow, endRow,Integer.parseInt(isSearch),search,Integer.parseInt(range));
			}else if(Integer.parseInt(isMyTeam) == 1) {
				articleList = teamService.getMyOkTeamArticles(nickname, Integer.parseInt(pageStatus), startRow, endRow, Integer.parseInt(isSearch), search, Integer.parseInt(range));
			}
			articleMemberAvgList = teamMemService.getTeamAvgArticles(articleList);
		}
		
		number = count-(currPage-1)*pageSize;
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageStatus", pageStatus);
		model.addAttribute("today", today);
		model.addAttribute("isMyTeam", isMyTeam);
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
	
	@RequestMapping("groupComeInviteList.moa")
	public String teamComeInviteList(HttpServletRequest request, String pageNum, Model model) throws SQLException {
		if(pageNum == null) {
			pageNum = "1";
		}
		
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("memName");
		
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		int count = teamService.getTeamComeInviteCount(nickname);
		
		if(count>0) {
			articleList = teamService.getTeamComeInvites(nickname,startRow, endRow);
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
		
		return "team/groupComeInviteList";
	}
	
	
	@RequestMapping("groupOpenForm.moa")
	public String groupOpenForm(@ModelAttribute("dto") TeamDTO dto) {
		return "team/groupOpenForm";
	}
	
	@RequestMapping("groupOpenPro.moa")
	public String groupOpenPro(TeamDTO dto, String join_mem_nick) throws SQLException{
		teamService.insertTeamArticle(dto);
		
		//비공개
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
	public String teamDetail(HttpServletRequest request, @RequestParam("team_no")int team_no, Model model, HttpServletResponse response) throws SQLException, IOException{
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("memName");
		
		TeamDTO team = teamService.selectOne(team_no);
		
		List<TeamMemberDTO> memList = teamMemService.selectAllbyTeamNo(team_no);
		
		boolean isMem = false;
		boolean realJoin = false;
		
		for(int i=0;i<memList.size();i++) {
			if(nickname.equals(memList.get(i).getNickname())) {
				isMem = true;
				if(memList.get(i).getIs_join() == 1)
					realJoin = true;
			}
		}
		
		if(Integer.parseInt(team.getIsopen())==0 && isMem == true && realJoin == false) {
			//비공개
			response.sendRedirect("/moamore/team/teamDetailSecurity.moa?team_no="+team_no);
		}else if(Integer.parseInt(team.getIsopen())==0 && isMem == false){
			response.setContentType("text/html; charset=UTF-8");
			 
			PrintWriter out = response.getWriter();
			 
			out.println("<script>alert('가입되어있지않은 비밀 그룹입니다.'); history.go(-1);</script>");
			 
			out.flush();
		}

		model.addAttribute("team", team);
		return "team/groupDetail";
	}
	
	@RequestMapping("teamDetailSecurity.moa")
	public String teamDetailSecurity(int team_no, Model model) throws SQLException{
		model.addAttribute("team_no", team_no);
		
		return "team/groupDetailSecurity";
	}
	
	@RequestMapping("teamDetailSecurityPro.moa")
	public String teamDetailSecurityPro(int team_no, String nickname, String pw, Model model) throws SQLException{
		
		int result = teamService.checkPw(team_no, pw);
		
		if(result == 1) {
			teamMemService.updateTeamMemJoin(team_no, nickname);
		}
		
		model.addAttribute("result",result);
		model.addAttribute("team_no",team_no);
		
		return "team/groupDetailSecurityPro";
	}
	
		
}