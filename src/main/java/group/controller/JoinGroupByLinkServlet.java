package group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import group.model.Group_memberVO;
import group.service.GroupMemberService;
import member.model.Member;
import redis.clients.jedis.Jedis;

@WebServlet("/group/join")
public class JoinGroupByLinkServlet extends HttpServlet {

	GroupMemberService service = new GroupMemberService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		String uid = req.getParameter("UID");
		if (member != null) {
			Integer id = member.getId();
			Jedis jedis = new Jedis();
			if(jedis.get("groupLinks:" + uid)!=null) {
				String result = jedis.get("groupLinks:" + uid);
				System.out.println(result);
				jedis.close();
				if (!result.equals("null")) {
					List<Integer> memList = new ArrayList();
					int groupId = Integer.parseInt(result);
					List<Group_memberVO> list = service.getAllMemberList(groupId);
					for (Group_memberVO vo : list) {
						Integer memId = vo.getMemberid();
						memList.add(memId);
					}
					System.out.println(memList);
					if (!memList.contains(id)) {
						int urlId = service.inviteFriendBylink(id, groupId);
						res.sendRedirect(
								req.getContextPath() + "/page/group/group_htmls/new_group_invite.html?Id=" + urlId);
					}else {
						res.sendRedirect(req.getContextPath() + "/page/group/group_htmls/already_in.html");
					}
				}
			}else {
				jedis.close();
				res.sendRedirect(req.getContextPath() + "/page/group/group_htmls/invalid_link.html");
			}

		} else {
			res.sendRedirect(req.getContextPath() + "/page/group/group_htmls/invalid_link.html");
		}
	}
}
