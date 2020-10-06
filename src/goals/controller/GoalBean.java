package goals.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		//String id=  "eunjitest@naver.com";
		//id = (String)session.getAttribute("memId");
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

}
