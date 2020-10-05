package goals.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import goals.model.dto.GoalsDTO;
import goals.service.GoalsServiceImpl;

@Controller
@RequestMapping("/goals/")
public class GoalBean {
	
	@Autowired
	private GoalsServiceImpl goalsService = null;
	
	@RequestMapping("myGoalList.moa")
	public String myGoalList(HttpSession session, Model model) {
		//아이디별 리스트. 세션으로 id받아오기
		String id=  "eunjitest@naver.com";
		//id = (String)session.getAttribute("memId");
		List<GoalsDTO> goalList = goalsService.selectAllById(id);
		model.addAttribute("goalList", goalList);
		
		return "goals/myGoalList";
	}

}
