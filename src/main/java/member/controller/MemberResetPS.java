package member.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.model.Member;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/resetps")
public class MemberResetPS extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			MemberServiceImpl service = new MemberServiceImpl();
			Gson gson = new Gson();
			JsonObject resObject = new JsonObject();
//			接收前端json資料塞進member裡，對密碼進行加密
			Member member = gson.fromJson(req.getReader(), Member.class);
//			String hashedPassword = hashPassword(member.getPassword());
			String hashedPassword = HashedPassword.hashPassword(member.getPassword());
			member.setPassword(hashedPassword);
			
			if(	req.getSession().getAttribute("verify") == null || 
				req.getSession().getAttribute("forgetMemId") == null) {
				Member memberlogin = (Member) req.getSession().getAttribute("member");
				member.setId(memberlogin.getId());
				
			}else {
				Integer id = (Integer) req.getSession().getAttribute("forgetMemId");
				member.setId(id);
			}
			
//				修改密碼後，註銷登入
				String username = "memUsername";
				String id = "memId";
				req.getSession().invalidate();
				Cookie[] cookie = req.getCookies();
				for (Cookie c: cookie) {
			        if (username.equals(c.getName())) {
			        	c.setPath("/");
			            c.setMaxAge(0);
			            resp.addCookie(c);
			        }
			        if (id.equals(c.getName())) {
			        	c.setPath("/");
			        	c.setMaxAge(0);
			        	resp.addCookie(c);
			        }
				}
			final String resultStr = service.savePassword(member);
			if(resultStr.equals("密碼修改成功")) {
				resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + 
									req.getContextPath() + "/page/" + "login.html");
			}else {
				resp.setContentType("application/json;charset=UTF-8");
				resObject.addProperty("msg", resultStr);
				resp.getWriter().append(resObject.toString());
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
