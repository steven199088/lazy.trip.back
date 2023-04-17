package admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import admin.model.Admin;
import admin.service.AdminService;


@WebServlet("/page/admin/update")
public class AdminUpadte extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Admin admin = gson.fromJson(req.getReader(), Admin.class);
			
		resp.setContentType("application/json");
		JsonObject jsonObj = new JsonObject();
		if (admin.getId() == 0 || admin.getId() == null) {
			jsonObj.addProperty("message", "修改失敗");
		} else {
			AdminService service = new AdminService();
			String resultStr = service.updateById(admin);
			jsonObj.addProperty("successful", resultStr.equals("修改成功"));
			jsonObj.addProperty("message", resultStr);


		}
		resp.getWriter().append(jsonObj.toString());
	}
}
