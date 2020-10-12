package member.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import category.service.bean.CategoryService;
import member.model.dto.MemberDTO;
import member.service.bean.CalendarService;
import member.service.bean.MemberService;



@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	

	
	
	@Autowired
	private MemberService memberService = null;
	
	
	@Autowired
	private CalendarService calendarService = null;
	


	@RequestMapping("loginForm.moa")
	public String NLCloginForm() throws SQLException {
		

		
		return "member/loginForm"; 		
	}
	@RequestMapping("loginPro.moa")
	public String loginPro(String id, String pw, String auto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = memberService.idPwCheck(id, pw);
		HttpSession session = request.getSession();
		
		if(result==1) {	//아이디 비밀번호 일치하면
			

			MemberDTO dto = memberService.selectOne(id);
			String nickname = dto.getNickname();
			
			session.setAttribute("memId", id);	//세션 만들고
			session.setAttribute("memName", nickname);
			
			if(auto != null) {	//자동로그인 체크면 쿠키 추가
				Cookie c1 = new Cookie("autoId", id);
				Cookie c2 = new Cookie("autoPw", pw);
				Cookie c3 = new Cookie("autoCh", auto);
				c1.setMaxAge(60*60*24);
				c2.setMaxAge(60*60*24);
				c3.setMaxAge(60*60*24);
				response.addCookie(c1);
				response.addCookie(c2);
				response.addCookie(c3);
			}
			
			return "redirect:main.moa";
			
		} else {
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('아이디 비밀번호가 일치하지 않습니다');</script>");
			out.flush();
			
			return "member/loginForm";
		}

	}
	
	@RequestMapping("deleteForm.moa")
	public String LCdeleteForm() {
		return "member/deleteForm";
	}
	
	@RequestMapping("deletePro.moa")
	public String deletePro(String pw, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = (String)request.getSession().getAttribute("memId");
		int result = memberService.idPwCheck(id, pw);
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 1) {
			memberService.deleteMember(id);
			out.println("<script>alert('회원탈퇴가 완료되었습니다.');</script>");
			out.flush();
			
			//세션 쿠키 삭제
			HttpSession session = request.getSession();
			session.removeAttribute("memId");	//세션 삭제
			Cookie[] coo = request.getCookies();
			for(Cookie c : coo) {
				if(c.getName().equals("autoId")) c.setMaxAge(0);
				if(c.getName().equals("autoPw")) c.setMaxAge(0);
				if(c.getName().equals("autoCh")) c.setMaxAge(0);
			}
			
			return "main";
		} else {
			out.println("<script>alert('아이디 비밀번호가 일치하지 않습니다.');</script>");
			out.flush();
			return "member/deleteForm"; 
		}
	}


	@RequestMapping("logout.moa")
	public String LClogout(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		session.removeAttribute("memId");	//세션 삭제
		Cookie[] coo = request.getCookies();
		for(Cookie c : coo) {
			if(c.getName().equals("autoId")) c.setMaxAge(0);
			if(c.getName().equals("autoPw")) c.setMaxAge(0);
			if(c.getName().equals("autoCh")) c.setMaxAge(0);
		}
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
		
		MemberDTO dto = memberService.selectOne(id);
		
		model.addAttribute("dto", dto);
		
		return "member/updateMember";
	}
	
	
	@RequestMapping("updateMemberPro.moa")
	public String updateMemberPro(MemberDTO dto,MultipartHttpServletRequest request, String eximage,Model model) throws SQLException{
		
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		dto.setId(id);
		
		  memberService.modifyMember(dto,request,eximage);
		  dto = memberService.selectOne(id);
		
		  model.addAttribute("dto", dto);
		  
		return "member/updateMember";
	}
	
	
	@RequestMapping("calendar.moa")
	public String calendar(Model model) throws SQLException{
		
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);

		//id로 budget테이블에서 데이터가 있는 날짜 가져오기
		List<String> budgetAlldate = calendarService.selectBudgetDatebyId(id);
		
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> AllbudgetAmount = calendarService.selectBudgetAmount(id,budgetAlldate);
		
		Map map = new HashMap();
		
		for(int i = 0; i<budgetAlldate.size(); i++) {

			map.put(budgetAlldate.get(i), AllbudgetAmount.get(i));
			
		}
		
		System.out.println(map);
		
		
		
		return "calendar/calendar";
	}
	
	
	
}
	
	
	


