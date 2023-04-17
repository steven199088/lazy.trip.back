package customerService.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

@WebServlet("/customerService/GetMemberImg")
public class GetMemberImg extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("userId");
		MemberService service;
		try {
			service = new MemberServiceImpl();
			Member member = service.findById(Integer.parseInt(id));
			OutputStream out = resp.getOutputStream();
			out.write(member.getImg());
			out.close();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
