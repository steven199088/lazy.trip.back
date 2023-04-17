package admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import admin.model.Admin;
import admin.service.AdminService;


@WebServlet("/page/admin/login")
public class AdminLogin extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Admin admin = gson.fromJson(req.getReader(), Admin.class);
		
		AdminService service = new AdminService();
		admin = service.login(admin);
		
		if (admin == null) {
			JsonObject error = new JsonObject();
		    error.addProperty("errorMessage", "用戶名或密碼錯誤");
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    resp.getWriter().write(error.toString());
		} else {
			if (req.getSession(false) != null) {
				req.changeSessionId(); // ←產生新的Session ID
			}
			
			req.getSession().setAttribute("admin", admin);
			Cookie cookie2 = new Cookie("adminUsername", admin.getName());
			cookie2.setMaxAge(30 * 60);
			cookie2.setPath("/");
			resp.addCookie(cookie2);
			
			resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/" + "page/admin/member_table.html");
			
		}
		
		
		
		
	}
}
