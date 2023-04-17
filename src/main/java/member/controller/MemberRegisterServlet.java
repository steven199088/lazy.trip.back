package member.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.model.Member;
import member.service.MemberServiceImpl;



@WebServlet("/page/register")
public class MemberRegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberRegisterServlet() {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Member member = gson.fromJson(req.getReader(), Member.class);
			MemberServiceImpl service = new MemberServiceImpl();
			
//			String hashedPassword = hashPassword(member.getPassword());
			String hashedPassword = HashedPassword.hashPassword(member.getPassword());
			member.setPassword(hashedPassword);
			
			final String resultStr = service.register(member);
			
//			System.out.println(resultStr);
			
			resp.setContentType("application/json;charset=UTF-8");
			JsonObject respBody = new JsonObject();
			respBody.addProperty("successful", resultStr.equals("註冊成功"));
			respBody.addProperty("message", resultStr);
			resp.getWriter().append(respBody.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
