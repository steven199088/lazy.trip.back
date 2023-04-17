package common;

import member.model.Member;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/page/member/main.html"
		, "/page/group/group_htmls/*"
		, "/page/tour/tour.html"
		, "/page/tour/tourSchedule.html"
		, "/page/friend/friend_main.html"})
public class MemFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		if(member == null) {
			String username = "memUsername";
			String id = "memId";
			Cookie[] cookie = req.getCookies();
			for (Cookie c: cookie) {
//				System.out.println(c.getName());
		        if (username.equals(c.getName())) {
		        	c.setPath("/");
		            c.setMaxAge(0);
		            resp.addCookie(c);
		        }
		        if (id.equals(c.getName())) {
		        	c.setPath("/");
		        	c.setMaxAge(0);
		        	resp.addCookie(c);
		        }
		    }
			
			session.setAttribute("location", req.getRequestURI());
			resp.sendRedirect(req.getContextPath() + "/page/login.html");
			return;
		}else {
			System.out.println(member.getUsername() + ", " + req.getRequestURI());
			chain.doFilter(req, resp);
			
		}
		
		
	}

}
