package goals.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import goals.model.dto.GoalsDTO;
import goals.service.GoalsServiceImpl;

@Controller
@RequestMapping("/goals/")
public class GoalBean {
	
	@Autowired
	private GoalsServiceImpl goalsService = null;
	
	
	@RequestMapping("myGoalList.moa")
	public String myGoalList(Model model) throws SQLException {
		//아이디별 리스트. 세션으로 id받아오기
		List<GoalsDTO> goalList = goalsService.selectAllById();
		model.addAttribute("goalList", goalList);
		
		return "goals/myGoalList";
	}
	
	
	@RequestMapping("insertGoalForm.moa")
	public String insertGoalForm() {
		return "goals/insertGoalForm";
	}
	
	
	@RequestMapping(value="insertGoalPro.moa", method=RequestMethod.POST)
	public String insertGoalPro(Model model, GoalsDTO goal) throws SQLException {
	
		goalsService.insertGoal(goal);
	
		return "forward:/goals/myGoalList.moa";
	}
	
	@RequestMapping(value="modifyForm.moa", method=RequestMethod.GET)
	public String modifyForm(@RequestParam("goal_no") int goal_no, Model model) throws SQLException {
		GoalsDTO goal = goalsService.selectOne(goal_no);
		model.addAttribute("goal", goal);
		return "goals/modifyForm";
	}
	
	@RequestMapping(value="modifyPro.moa", method=RequestMethod.POST)
	public String modifyPro(GoalsDTO goal, Model model) throws SQLException {
		goalsService.modifyGoal(goal);
		model.addAttribute("msg", "수정되었습니다.");
		model.addAttribute("url", "/moamore/goals/myGoalList.moa"); // 나중에 상세페이지로 수정하기 
		
		return "goals/redirect";
	}
	
	@RequestMapping(value="deleteGoal.moa", method=RequestMethod.GET)
	public String deleteGoal(int goal_no, int public_ch, int team_no,  Model model, HttpServletRequest request) throws SQLException {
		
		goalsService.deleteGoal(goal_no, public_ch, team_no);

		model.addAttribute("msg", "삭제되었습니다.");
		model.addAttribute("url", "/moamore/goals/myGoalList.moa");	
		
		
		return "goals/redirect";
	}
	
	@RequestMapping(value="myGoalDetail.moa")
	public String myGoalDetail(int goal_no) throws SQLException {
		goalsService.myGoalDetail(goal_no);
		return "goals/myGoalDetail";
	}
	
	@RequestMapping(value="enterTeam.moa", method=RequestMethod.GET)
	public String enterTeam(int team_no) throws SQLException {// 
		goalsService.enterTeam(team_no);
		
		
		return "redirect:/team/teamDetail.moa?team_no="+team_no;
	}

}
