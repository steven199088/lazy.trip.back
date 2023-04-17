package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.model.VerifyCode;
import redis.clients.jedis.Jedis;

@WebServlet("/page/member/verify")
public class MemberVerify extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Jedis jedis = new Jedis("localhost", 6379);
		
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		VerifyCode verifyCode = gson.fromJson(req.getReader(), VerifyCode.class);
		
		Object memId = req.getSession().getAttribute("forgetMemId");
//		System.out.println(memId.toString());
		StringBuilder db = new StringBuilder("Member:").append(memId);
		String code = jedis.get(db.toString());
		
		
		JsonObject errorMsgs = new JsonObject();
		if(code == null || verifyCode == null) {
			resp.setContentType("application/json;charset=UTF-8");
			errorMsgs.addProperty("msg", "驗證碼已逾時，請重新申請");
			resp.getWriter().append(errorMsgs.toString());
		}else if(verifyCode.getCode().equals(code)){
			req.getSession().setAttribute("verify", true);
			resp.sendRedirect("resetps.html");
		}else{
			resp.setContentType("application/json;charset=UTF-8");
			errorMsgs.addProperty("msg", "驗證失敗");
			resp.getWriter().append(errorMsgs.toString());
		}
		
	}
}
