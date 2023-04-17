package company.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import company.model.CompanyVO;
import company.service.CompanyService;

@WebServlet("/page/company/login")
public class CompanyLogin extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		CompanyVO company = gson.fromJson(req.getReader(), CompanyVO.class);
		System.out.println(company);
		
		CompanyService service = new CompanyService();
		company = service.login(company);
		
//		System.out.println(company);
		if (company == null) {
			JsonObject error = new JsonObject();
			error.addProperty("errorMessage", "用戶名或密碼錯誤");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(error.toString());
		} else {
			if (req.getSession(false) != null) {
				req.changeSessionId(); // ←產生新的Session ID
			}

			req.getSession().setAttribute("company", company);
			req.getSession().setAttribute("login", true);

			Cookie cookie = new Cookie("companyId", company.getCompanyID().toString());
			Cookie cookie2 = new Cookie("companyUsername", company.getCompanyName());
//			Cookie cookie3 = new Cookie("companyImg",company.getCompanyImg());
			cookie.setMaxAge(30 * 60);
			cookie2.setMaxAge(30 * 60);
//			cookie3.setMaxAge(30 * 60);
			cookie.setPath("/");
			cookie2.setPath("/");
//			cookie3.setPath("/");
			resp.addCookie(cookie);
			resp.addCookie(cookie2);
//			resp.addCookie(cookie3);

			String location = (String) req.getSession().getAttribute("location");
			if (location == null || location.isEmpty()) {
				resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
						+ req.getContextPath() + "/" + "page/company/roomManagement.html");
			} else {
				resp.sendRedirect(location);
			}
			// System.out.println(location);

		}
	}

}
