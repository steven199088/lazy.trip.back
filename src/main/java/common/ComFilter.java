package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import company.model.CompanyVO;
//"/page/tour/tour_company.html" ,
@WebFilter(urlPatterns={ "/page/company/roomManagement.html"})
public class ComFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		CompanyVO company = (CompanyVO) session.getAttribute("company");
		if(company == null) {
			String username = "companyUsername";
			String id = "companyId";
			Cookie[] cookie = req.getCookies();
			for (Cookie c: cookie) {
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
			System.out.println(company.getCompanyName() + ", " + req.getRequestURI());
			chain.doFilter(req, resp);
			
		}
		
		
	}

}
