package member.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.model.Member;
import member.service.MemberServiceImpl;

@WebServlet("/page/admin/selectAll")
public class MemberSelectAllServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			MemberServiceImpl service = new MemberServiceImpl();
			List<Member> member = service.findAll();
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json;charset=UTF-8");

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Writer writer = resp.getWriter();
			writer.write(gson.toJson(member));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
