package group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import group.model.DiscussionVO;
import group.service.DiscussionService;
import member.model.Member;

@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		DiscussionService service = new DiscussionService();
		
		//新增一筆留言
		if("addContent".equals(action)) {
			Gson gson = new Gson();
			Integer memberid = Integer.valueOf(member.getId());
			DiscussionVO discussionVO = new DiscussionVO();
			discussionVO.setMemberid(memberid);
			discussionVO.setDiscussionContent(req.getParameter("content"));
			discussionVO.setGroupid(Integer.valueOf(req.getParameter("groupid")));
			service.insert(discussionVO);
		}
		
		//取得當前揪團留言內容
		if("getGroupCons".equals(action)) {
			Gson gson = new Gson();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			PrintWriter out = res.getWriter();
			List<DiscussionVO> list = service.getAllContent(groupid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));
		}
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		DiscussionService service = new DiscussionService();
		//刪除留言
		if ("delDiscussion".equals(action)) {
			Integer discussionId = Integer.valueOf(req.getParameter("discussionId"));
			service.delete(discussionId);
		}
		
		if("delAllDis".equals(action)) {
			Integer id = Integer.valueOf(req.getParameter("groupid"));
			service.deleteAll(id);
		}
	}
}
