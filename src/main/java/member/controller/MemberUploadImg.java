package member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/upload")
@MultipartConfig
public class MemberUploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Member member = gson.fromJson(req.getReader(), Member.class);
		
		String id = member.getId().toString();
		String base64 = member.getImgBase64Str();
//		System.out.println(id);
//		System.out.println(member.getImgBase64Str());
		if(id != null && !id.isEmpty() && base64 != null && !base64.isEmpty()) {
			byte[] avatar = Base64.getDecoder().decode(base64);
			try {
				MemberService service = new MemberServiceImpl();
				member.setImg(avatar);
				service.changeImgById(member);
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
