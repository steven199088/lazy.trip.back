package member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import member.model.Comment;
import member.model.CommentImg;
import member.model.Member;
import member.service.CommentImgService;
import member.service.CommentService;

@WebServlet("/page/member/post")
@MultipartConfig
public class CommentPost extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String commentText = req.getParameter("comment");
		Collection<Part> imgs = req.getParts();
		
		Member memberLogin = (Member) req.getSession().getAttribute("member");
		Comment comment = new Comment();
		if(commentText == null || commentText.isEmpty()) {
			comment.setMemberId(memberLogin.getId());
			comment.setText("");
		}else {
			comment.setMemberId(memberLogin.getId());
			comment.setText(commentText);

		}
		
		CommentService service = new CommentService();
		String resultStr = service.post(comment);
		
		if (resultStr.equals("發佈成功")) {
			comment = service.find(comment);
//				System.out.println(comment.getId());
			for (Part part : imgs) {
//					System.out.println(part.getContentType() + ", " + part.getSize());
				if (part.getContentType() != null && part.getSize() != 0) {
					InputStream is = part.getInputStream();
					byte[] image = is.readAllBytes();
					
					CommentImg commentImg = new CommentImg();
					CommentImgService serviceImg = new CommentImgService();
					commentImg.setCommentId(comment.getId());
					commentImg.setImg(image);
					serviceImg.insert(commentImg);
					
				}
			}
			resp.setContentType("application/json;charset=UTF-8");
			resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/page/member/" + "main.html?message=success");
		}

	}
}
