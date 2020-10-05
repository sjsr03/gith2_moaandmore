package member.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import member.model.dao.MemberDAO;
import member.model.dao.MemberDAOImpl;
import member.model.dto.MemberDTO;
import member.service.bean.MemberService;

@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	
	@Autowired
	private MemberDAOImpl memberDAO = null;
	
	
	
	@RequestMapping("loginForm.moa")
	public String loginForm() throws SQLException {
		return "member/loginForm"; 		
	}
	@RequestMapping("loginPro.moa")
	public String loginPro(String id, String pw) throws SQLException {
		
		
		return "member/loginPro"; 		

	}
	
	
	@RequestMapping("signupForm.moa")
	public String signupForm() {
		
		
		
		
		return "member/signupForm";
	}
	
	
	@RequestMapping("signupPro.moa")
	public String signupPro(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException{ 	
	
			
		
		
		
			
			return "member/signupPro";
		}
	
		
		
	
	
	
	
}
	
	
	


