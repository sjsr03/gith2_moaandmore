package member.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import member.model.dao.MemberDAO;
import member.model.dao.MemberDAOImpl;

import member.model.dto.MemberDTO;
import member.service.bean.MemberService;
import member.service.bean.MemberServiceImpl;

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


	@RequestMapping("logout.moa")
	public String LClogout(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		session.removeAttribute("memId");
		return "main";
	}
	
	@RequestMapping("signupForm.moa")
	public String signupForm() {

		
		return "member/signupForm";
	}
	
	
					
	@RequestMapping("signupPro.moa")
	public String signupPro(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException{ 	
	
			
			memberService.insertMember(dto,request);
			
		
			
			return "member/loginForm";
		}
	
		
	
	
	
	@RequestMapping("updateMember.moa")
	public String updateMember(HttpServletRequest request,Model model)throws SQLException{
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		MemberDTO member = memberService.selectOne(id);
		
		model.addAttribute("member", member);
		
		return "member/updateMember";
	}
	
	
	@RequestMapping("updateMemberPro.moa")
	public String updateMemberPro(MemberDTO dto,MultipartHttpServletRequest request, String eximage,Model model) throws SQLException{
		
		
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		dto.setId(id);
		
		  memberService.modifyMember(dto,request,eximage);
		  MemberDTO member = memberService.selectOne(id);
		
		  model.addAttribute("member", member);
		  
		return "member/updateMember";
	}
	
	
	
	
	
	
}
	
	
	


