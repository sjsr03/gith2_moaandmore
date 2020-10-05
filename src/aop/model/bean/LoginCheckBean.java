package aop.model.bean;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class LoginCheckBean {
	
	@Around("execution(* LC*(..))")
	public Object around(ProceedingJoinPoint j) throws Throwable {
		ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sa.getRequest();
		HttpServletResponse response = sa.getResponse();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("memId") == null) {	//비로그인 상태면
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 후 이용하세요!');</script>");
            out.flush();
			return "member/loginForm";
		}
		Object result = j.proceed();
		return result;
		
	}
	
}
