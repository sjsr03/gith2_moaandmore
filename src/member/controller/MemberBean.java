package member.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import member.model.dao.MemberDAO;
import member.model.dao.MemberDAOImpl;
import member.service.bean.MemberService;

@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	
	@Autowired
	private MemberService memberService = null;

	@RequestMapping("main.moa")	//테스트용 임시 경로
	public String main() throws SQLException {
		return "main"; 		
	}
	@RequestMapping("loginForm.moa")
	public String NLCloginForm() throws SQLException {
		return "member/loginForm"; 		
	}
	@RequestMapping("loginPro.moa")
	public String NLCloginPro(String id, String pw, HttpServletRequest request,Model model) throws SQLException {
		int result = memberService.idPwCheck(id, pw);
		HttpSession session = request.getSession();
		if(result==1) {
			session.setAttribute("memId", id);
		}
		model.addAttribute("result", result);
		
		return "member/loginPro"; 		
	}
	
		
}

