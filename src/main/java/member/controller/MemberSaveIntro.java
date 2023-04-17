package member.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/saveintro")
public class MemberSaveIntro extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Member member = gson.fromJson(req.getReader(), Member.class);
		try {
			resp.setContentType("application/json");
			JsonObject jsonObj = new JsonObject();
			if (member.getId() == 0 || member.getId() == null) {
				jsonObj.addProperty("message", "修改失敗");
			} else {
				MemberService service = new MemberServiceImpl();
				String resultStr = service.saveintro(member);
				jsonObj.addProperty("successful", resultStr.equals("修改成功"));
				jsonObj.addProperty("message", resultStr);

				String username = "memUsername";
				Cookie[] cookie = req.getCookies();
				for (Cookie c : cookie) {
//					System.out.println(c.getName());
					if (username.equals(c.getName())) {
						c.setValue(member.getUsername());
						c.setPath("/");
						resp.addCookie(c);
					}
				}

			}
			resp.getWriter().append(jsonObj.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}
