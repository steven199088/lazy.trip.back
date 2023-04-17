package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/page/logout")
public class MemberLogout extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = "memUsername";
		String id = "memId";
		
		try {
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
			req.getSession().invalidate();
			resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/" );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
