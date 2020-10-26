package member.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;
import category.service.bean.CategoryService;
import member.model.dto.MemberDTO;

import member.service.bean.MemberService;



@Controller
@RequestMapping("/member/") // 클래스 레벨
public class MemberBean {
	

	
	
	@Autowired
	private MemberService memberService = null;
	@Autowired
	private BudgetService budgetService = null;
	
	@RequestMapping("tutorial.moa")
	public String tutorial() {
		
		
		return "member/tutorial";
	}


	@RequestMapping("loginForm.moa")
	public String NLCloginForm() {

		
		return "member/loginForm"; 		
	}
	@RequestMapping("loginPro.moa")
	public String NLloginPro(String id, String pw, String auto, String referrer, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int result = memberService.idPwCheck(id, pw);
		HttpSession session = request.getSession();
		
		if(result==1) {	//아이디 비밀번호 일치하면
			

			MemberDTO dto = memberService.selectOne(id);
			String nickname = dto.getNickname();
			
			session.setAttribute("memId", id);	//세션 만들고
			session.setAttribute("memName", nickname);
			session.setAttribute("memImg", dto.getProfile_img());
			
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
		}
		
		model.addAttribute("result",result);
		model.addAttribute("referrer",referrer);
		System.out.println("referrer : " + referrer);

		
		//현재 진행중인 예산이 있다면
		if(budgetService.selectCurrentOne(id)!=null) {
			//예산 만료되었는지 확인
			budgetService.updateNewTB(id);
			//남은돈 계산
			budgetService.calLeftMoney(id);
			//오늘의 예산 계산하기
			budgetService.calTodayBudget(id);
			
		}
		
		return "member/loginPro";
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
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("memId");	//세션 삭제
		session.removeAttribute("memName");	//세션 삭제
		Cookie[] coo = request.getCookies();
		for(Cookie c : coo) {
			if(c.getName().equals("autoId")) c.setMaxAge(0);
			if(c.getName().equals("autoPw")) c.setMaxAge(0);
			if(c.getName().equals("autoCh")) c.setMaxAge(0);
		}
		return "redirect:../main.moa";
	}
	
	@RequestMapping("signupForm.moa")
	public String NLCsignupForm() {

		
		return "member/signupForm";
	}
	
	
					
	@RequestMapping("signupPro.moa")
	public String NLCsignupPro(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException{ 	
	
			
			
			
			memberService.insertMember(dto,request);
			
			
			
			return "member/loginForm";
		}
	
	
	//아이디 유효성 검사
	@RequestMapping(value = "idCheck.moa", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(@RequestParam("userId") String user_id) throws SQLException{

		System.out.println(user_id);
		
		return memberService.userIdCheck(user_id);
	}
	
	
	//닉네임 유효성 검사
	@RequestMapping(value = "nicknameCheck.moa", method = RequestMethod.GET)
	@ResponseBody
	public int nicknameCheck(@RequestParam("nickname") String nickname) throws SQLException{
		System.out.println(1);
		System.out.println(nickname);
		
		return memberService.nicknameCheck(nickname);
	}
	
	@RequestMapping("updateMember.moa")
	public String LCupdateMember(Model model)throws SQLException{
		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);

		
		
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
		  
		request.getSession().setAttribute("memImg", dto.getProfile_img());
		
		
		
		
		  model.addAttribute("dto", dto);
		  
		return "member/updateMember";
	}
	
	
	
	
	
	
}
	
	
	


