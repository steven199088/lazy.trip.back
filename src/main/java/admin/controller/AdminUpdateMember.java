package admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import admin.service.AdminService;
import member.model.Member;

@WebServlet("/page/admin/updatemember")
@MultipartConfig
public class AdminUpdateMember extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Member member = gson.fromJson(req.getReader(), Member.class);
		System.out.println(member.toString());
				
		resp.setContentType("application/json");
		JsonObject jsonObj = new JsonObject();
		if (member.getId() == 0 || member.getId() == null) {
			jsonObj.addProperty("message", "修改失敗");
		} else {
			AdminService service = new AdminService();
			String resultStr = service.updateMemberById(member);
			jsonObj.addProperty("message", resultStr);

		}
		resp.getWriter().append(jsonObj.toString());
	}
}
