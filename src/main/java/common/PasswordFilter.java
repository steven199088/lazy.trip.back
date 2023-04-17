package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.Member;

@WebFilter(urlPatterns={ "/page/member/resetps.html"})
public class PasswordFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		Member member = (Member) session.getAttribute("member");
		if(member == null) {
			if(session.getAttribute("verify") == null) {
				if(session.getAttribute("forgetMemId") == null ) {
					resp.sendRedirect(req.getContextPath() + "/page/member/forgetps.html");
					return;
				}else {
					resp.sendRedirect(req.getContextPath() + "/page/member/verify.html");
					return;
				}
			}else {
				chain.doFilter(req, resp);
			}
		}else {
			System.out.println(member.getUsername() + ", " + req.getRequestURI());
			chain.doFilter(req, resp);
			
		}
	}
}
