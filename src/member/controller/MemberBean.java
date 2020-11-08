package member.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;

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
	
	@Autowired
	private KakaoController kakaoController = null;
	
	@RequestMapping("tutorial.moa")
	public String tutorial() {
		
		
		return "member/tutorial";
	}

	/*
	@RequestMapping("loginForm.moa")
	public String NLCloginForm() {

		
		return "member/loginForm"; 		
	}
	*/
	@RequestMapping("loginForm.moa")
	public ModelAndView NLCloginForm(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		String kakaoUrl = kakaoController.getAuthorizationUrl(session);
		//System.out.println("카카오 URL : " + kakaoUrl);
		mav.addObject("kakao_url", kakaoUrl);
		return mav;
	}
	
	
	
	@RequestMapping("loginPro.moa")
	public String NLloginPro(String id, String pw, String auto, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		
		
		int result = memberService.idPwCheck(id, pw);
		
		if(session.getAttribute("memId") != null) {
			return "main"; 
		}
		
		if(result==1) {	//아이디 비밀번호 일치하면
			

			MemberDTO dto = memberService.selectOne(id);
			String nickname = dto.getNickname();
			
			session.setAttribute("memId", id);	//세션 만들고
			session.setAttribute("memName", nickname);
			session.setAttribute("memImg", dto.getProfile_img());
			
			if(auto != null) {	//자동로그인 체크면 쿠키 추가
				Cookie c1 = new Cookie("autoId", id);
				Cookie c2 = new Cookie("autoName", URLEncoder.encode(nickname, "UTF-8"));
				Cookie c3 = new Cookie("autoImg", URLEncoder.encode(dto.getProfile_img(), "UTF-8"));
			    c1.setPath("/moamore/");
			    c2.setPath("/moamore/");
			    c3.setPath("/moamore/");
				c1.setMaxAge(60*60*24);
				c2.setMaxAge(60*60*24);
				c3.setMaxAge(60*60*24);
				response.addCookie(c1);
				response.addCookie(c2);
				response.addCookie(c3);
			}
		}
		
		model.addAttribute("result",result);

		
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
			if(coo != null) {
				for(Cookie c : coo) {
					if(c.getName().equals("autoId")) {
						c.setMaxAge(0);

						c.setPath("/moamore/");
						response.addCookie(c);
					}
					if(c.getName().equals("autoName")) {
						c.setMaxAge(0);
						c.setPath("/moamore/");
						response.addCookie(c);
					}
					if(c.getName().equals("autoImg")) {
						c.setMaxAge(0);
						c.setPath("/moamore/");

						response.addCookie(c);
					}
				}
			}
			
			return "main";
		} else {
			out.println("<script>alert('아이디 비밀번호가 일치하지 않습니다.');</script>");
			out.flush();
			return "member/deleteForm"; 
		}
	}


	@RequestMapping("logout.moa")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("memId");	//세션 삭제
		session.removeAttribute("memName");	//세션 삭제
		Cookie[] coo = request.getCookies();
		if(coo != null) {
			for(Cookie c : coo) {
				if(c.getName().equals("autoId")) {
					c.setMaxAge(0);

					c.setPath("/moamore/");
					response.addCookie(c);
				}
				if(c.getName().equals("autoName")) {
					c.setMaxAge(0);
					c.setPath("/moamore/");
					response.addCookie(c);
				}
				if(c.getName().equals("autoImg")) {
					c.setMaxAge(0);
					c.setPath("/moamore/");

					response.addCookie(c);
				}
			}
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
	public int NLidCheck(String user_id) throws SQLException{
		return memberService.userIdCheck(user_id);
	}
	
	
	//닉네임 유효성 검사
	@RequestMapping(value = "nicknameCheck.moa", method = RequestMethod.GET)
	@ResponseBody
	public int NLnicknameCheck(@RequestParam("nickname") String nickname) throws SQLException{

		
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
	
	@RequestMapping(value = "/kakaologin.moa", produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
	public String NLCkakaoLogin(@RequestParam("code") String code, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		//System.out.println("카카오 로그인");
		ModelAndView mav = new ModelAndView();
		
		// 결과값을 node에 담아줌
		JsonNode node = kakaoController.getAccessToken(code);
		//  getAccessToken에 사용자의 로그인한 모든 정보가 들어있음
		
		JsonNode accessToken = node.get("access_token");
		//사용자의 정보
		JsonNode userInfo = kakaoController.getKakaoUserInfo(accessToken);
		String kemail = null;
		String kname = null;
		String kimage = null;
		
		// 유저의 정보를 카카오에서 가져오기 get properties
		JsonNode properties = userInfo.path("properties");
		JsonNode kakao_account = userInfo.path("kakao_account");
		kemail = kakao_account.path("email").asText();

		kname = properties.path("nickname").asText();
		kimage = properties.path("profile_image").asText();
		
		session.setAttribute("kemail", kemail);
		session.setAttribute("kname", kname);
		session.setAttribute("kimage", kimage);

		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setId(kemail);
		memberDTO.setNickname(kname);

		
		session.setAttribute("memId", memberDTO.getId());	//세션 만들고
		session.setAttribute("memName", memberDTO.getNickname());
		session.setAttribute("memImg", memberDTO.getProfile_img());
		
		model.addAttribute("result",1);
	
		//현재 진행중인 예산이 있다면
		if(budgetService.selectCurrentOne(memberDTO.getId())!=null) {
			//예산 만료되었는지 확인
			budgetService.updateNewTB(memberDTO.getId());
			//남은돈 계산
			budgetService.calLeftMoney(memberDTO.getId());
			//오늘의 예산 계산하기
			budgetService.calTodayBudget(memberDTO.getId());	
		}
		return "member/loginPro";
		
	}
	
	
	
}
	
	
	


