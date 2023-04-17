package member.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/listall")
public class MemberListAll extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private MemberService service;
	
	public MemberListAll() throws NamingException{
		service = new MemberServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		String type = req.getParameter("type");
		String search =req.getParameter("text");
		System.out.println("type: " + type + " ,text: " + search);
		List<Member> members ;
		if(type != null && type.equals("編號") && search!=null) {
			members = service.findAll(type, search);
		}else if(type != null && type.equals("帳號") && search!=null) {
			members = service.findAll(type, search);
		}else if(type != null && type.equals("暱稱") && search!=null) {
			members = service.findAll(type, search);
		}else {
			members = service.findAll();
		}
		
		
		Writer writer = resp.getWriter();
		writer.write(gson.toJson(members));
		
	}
}
