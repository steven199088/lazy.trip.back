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

import member.model.Comment;
import member.model.Member;
import member.service.CommentService;

@WebServlet("/page/member/listposts")
public class CommentListAll extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Member memberLogin = (Member) req.getSession().getAttribute("member");
		CommentService service = new CommentService();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Writer writer = resp.getWriter();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		
//		System.out.println(id);
		if(id == null || id.isEmpty()) {
			List<Comment> posts = service.listAll(memberLogin.getId());
			
			writer.write(gson.toJson(posts));
			
		}else {
			List<Comment> posts = service.listAll(Integer.parseInt(id));
			
			writer.write(gson.toJson(posts));
		}
	}
}
