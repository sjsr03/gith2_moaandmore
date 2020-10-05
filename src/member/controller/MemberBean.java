package member.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import member.model.dao.MemberDAO;
import member.model.dao.MemberDAOImpl;
import member.model.dto.MemberDTO;

@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	
	@Autowired
	private MemberDAOImpl memberDAO = null;

	
	@RequestMapping("test.moa")
	public String test() throws SQLException {
		 memberDAO.testCount();
		return "member/test"; 		
	}
	
	
	@RequestMapping("signupForm.moa")
	public String signupForm() {
		
		
		
		
		return "member/signupForm";
	}
	
	
	@RequestMapping("signupPro.moa")
	public String signupPro(MemberDTO dto) throws SQLException{ 	
	
		
		
		
			
			return "member/signupPro";
		}
		
		
	
	
	
	
}
	
	
	


