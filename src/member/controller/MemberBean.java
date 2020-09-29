package member.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import member.model.dao.MemberDAO;
import member.model.dao.MemberDAOImpl;

@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	
	@Autowired
	private MemberDAOImpl memberDAO = null;

	@RequestMapping("test.git")
	public String signupForm() throws SQLException {
		 memberDAO.testCount();
		return "member/test"; 		
	}
		
}

