package company.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import company.model.CompanyVO;
import company.service.CompanyService;

@WebServlet("/page/company/check")
public class CompanyCheckLogin extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CompanyVO companyLogin = (CompanyVO) req.getSession().getAttribute("company");
		if(companyLogin == null) {
			resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/" + "page/login.html");
		}else {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json;charset=UTF-8");
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Writer writer = resp.getWriter();
			writer.write(gson.toJson(companyLogin));
			
		}
	}
}
