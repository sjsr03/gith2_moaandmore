package aop.model.bean;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDTO;

@Aspect
public class LoginCheckBean {
	
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	
	
	@Around("execution(* LC*(..))")	//LoginCheck의 LC (로그인 해야만 접속가능하게)
	public Object loginCheck(ProceedingJoinPoint j) throws Throwable {
		ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sa.getRequest();
		HttpServletResponse response = sa.getResponse();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		if(id == null) {	//비로그인 상태면
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 후 이용하세요!');</script>");
            out.flush();
			return "member/loginForm";	//로그인페이지로 이동
			
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
