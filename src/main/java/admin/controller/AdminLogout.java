package admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/page/admin/logout")
public class AdminLogout extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = "adminUsername";
		
		try {
			Cookie[] cookie = req.getCookies();
			for (Cookie c: cookie) {
//				System.out.println(c.getName());
		        if (username.equals(c.getName())) {
		        	c.setPath("/");
		            c.setMaxAge(0);
		            resp.addCookie(c);
		        }
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		req.getSession().invalidate();
		resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/" + "page/admin/login.html");
		
		
	}
}
