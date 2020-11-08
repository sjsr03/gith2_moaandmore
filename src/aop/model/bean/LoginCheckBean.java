package aop.model.bean;

import java.io.PrintWriter;
import java.net.CookieStore;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDTO;

@Aspect
public class LoginCheckBean {
	
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	
	
	@Around("bean(*Bean) && !execution(* NL*(..))")	//비로그인 상태에서도 볼 수 있는 페이지만 NL
	public Object loginCheck(ProceedingJoinPoint j) throws Throwable {
		ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sa.getRequest();
		HttpServletResponse response = sa.getResponse();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		
		if(id == null) {	//비로그인 상태면
			//String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
			//Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
			System.out.println("쿠키체크 시작");
			//쿠키 체크
			Cookie[] coo = request.getCookies();
			if(coo != null) {
				for(Cookie c:coo) {
					if(c.getName().equals("autoId")) {
						session.setAttribute("memId", c.getValue());
						System.out.println(c.getValue());
						c.setMaxAge(60*60*24);
						response.addCookie(c);
					}
					if(c.getName().equals("autoName")) {
						session.setAttribute("memName", URLDecoder.decode(c.getValue()));
						c.setMaxAge(60*60*24);
						response.addCookie(c);
					}
					if(c.getName().equals("autoImg")) {
						session.setAttribute("memImg", URLDecoder.decode(c.getValue()));
						c.setMaxAge(60*60*24);
						response.addCookie(c);
					}
				}
				id = (String)session.getAttribute("memId");
			}
		}
		
		if(id == null) {	
			
			/*
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 후 이용하세요!');</script>");
            out.flush();
			 */
			System.out.println("???");
			return "member/loginForm";
		} else {	//로그인 상태인데
//			TotalBudgetDTO TBdto = totalBudgetDAO.selectCurrentOne(id);
//			if(TBdto == null) {	//현재 설정된 예산이 없으면
//				response.setCharacterEncoding("UTF-8"); 
//				response.setContentType("text/html; charset=UTF-8");
//				PrintWriter out = response.getWriter();
//	            out.println("<script>alert('현재 설정된 예산이 없습니다. 예산설정 페이지로 이동합니다');</script>");
//	            out.flush();
//	            return "budget/redirectSetBudget";
//			} else {	//로그인 상태이고 현재예산이 있으면 계속 진행
				Object result = j.proceed();
				return result;
//			}
		}
	}
	
	@Around("execution(* NLC*(..))")	//NotLoginCheck의 NLC (비로그인 상태일때만 접속가능하게)
	public Object nLoginCheck(ProceedingJoinPoint j) throws Throwable {
		ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sa.getRequest();
		HttpServletResponse response = sa.getResponse();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("memId") != null) {	//비로그인 상태면
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그아웃 후 이용하세요!');history.go(-1);</script>");
			out.flush();
//			String url = request.getHeader("referer");
//			url = url.split("/moamore/")[1];
			return null;	
		} else {
			Object result = j.proceed();
			return result;
		}
	}
	
}
