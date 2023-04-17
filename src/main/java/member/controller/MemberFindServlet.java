package member.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Base64;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import member.model.Member;
import member.service.MemberServiceImpl;


@WebServlet("/page/member/find")
public class MemberFindServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
    
    public MemberFindServlet() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	final String id = req.getParameter("id");
    	try {
			MemberServiceImpl service = new MemberServiceImpl();
			Member member = service.findById(Integer.parseInt(id));
			final byte[] img = member.getImg();
			if(member != null && img !=null && img.length !=0 ) {
				member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
				member.setImg(null);
			}
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json;charset=UTF-8");
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Writer writer = resp.getWriter();
			writer.write(gson.toJson(member));
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
}
