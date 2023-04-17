package group.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.model.Group_memberVO;
import group.service.GroupMemberService;
import member.model.Member;

@WebServlet("/groupmembers")
public class GroupMemberServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		GroupMemberService service = new GroupMemberService();


		// 邀請朋友
		if ("inviteFriend".equals(action)) {
			Gson gson = new Gson();
			List<Integer> list = new ArrayList<Integer>();
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = req.getParameter(param);
				if (param.equals("groupid")) {
					list.add(0, Integer.parseInt(value));
				} else if (!param.equals("action") && !param.equals("groupid")) {
					list.add(Integer.parseInt(value));
				}
			}
			service.inviteFriend(list);
//			res.setContentType("application/json");
//			res.getWriter().print(gson.toJson(list));
		}

		// 取得當前揪團所有隸屬成員
		if ("getGroupMems".equals(action)) {
			Gson gson = new Gson();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			List<Member> list = service.getAllMember(groupid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));

		}

		// 取得當前揪團所有任何狀態成員ID
		if ("getGroupMemsInList".equals(action)) {
			Gson gson = new Gson();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			List<Group_memberVO> list = service.getAllMemberList(groupid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));
		}

		// 得到一位會員所加入的揪團
		if ("getAllGroups".equals(action)) {
			Gson gson = new Gson();
			if (member.getId() != null) {
				List<GroupVO> list = service.GetAllGroup(member.getId());
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(list));
			}
		}

		// 查詢邀請
		if ("getAllInvite".equals(action)) {
			Gson gson = new Gson();
			Integer memberid = Integer.valueOf(member.getId());
			List list = service.getAllInvite(memberid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));
		}

		// 得到一筆資料
		if ("getOne".equals(action)) {
			Gson gson = new Gson();
			Integer id = Integer.valueOf(req.getParameter("Id"));
			res.getWriter().print(gson.toJson(service.getOne(id)));
			;

		}
		

		// 接受邀請
		if ("acceptInvite".equals(action)) {
			Gson gson = new Gson();
			Integer id = Integer.valueOf(req.getParameter("gp_member"));
			Integer needApproval = Integer.valueOf(req.getParameter("need_Approval"));
//			System.out.println(needApproval);
			service.acceptInvite(id, needApproval);
		}

		//審核 toDo =1:接受 =4:拒絕
		if("opGroupMember".equals(action)) {
			Enumeration<String> e = req.getParameterNames();
			List list = new ArrayList<Integer>();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = req.getParameter(param);
				Integer toDo;
				if(param.equals("toDo")) {
					toDo = Integer.parseInt(value);
					list.add(1,toDo);
				}
				if (param.equals("groupid")) {
					list.add(0, Integer.parseInt(value));
				} else if (!param.equals("action") && !param.equals("groupid") && !param.equals("toDo")) {
					list.add(Integer.parseInt(value));
				}
			}
			service.opMembers(list);
			System.out.println("list"+list);
		}
		
		// 更新自介相關
		if ("updateInfo".equals(action)) {
			Group_memberVO vo = new Group_memberVO();
			Integer id = Integer.valueOf(req.getParameter("gp_member"));
			String selfintro = req.getParameter("selfintro");
			String specialneed = req.getParameter("specialneed");
			vo.setGroupmember(id);
			vo.setSelfintro(selfintro);
			vo.setSpecialneed(specialneed);
			service.updateInfo(vo);
		}
		
		
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		GroupMemberService service = new GroupMemberService();

		// 會員拒絕邀請
		if ("deleteInvite".equals(action)) {
			Integer id = Integer.valueOf(req.getParameter("gp_member"));
			service.deleteOne(id);
		}
		// 刪除揪團成員
		if ("delGroupMember".equals(action)) {
			Enumeration<String> e = req.getParameterNames();
			List list = new ArrayList<Integer>();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = req.getParameter(param);
				if (param.equals("groupid")) {
					list.add(0, Integer.parseInt(value));
				} else if (!param.equals("action") && !param.equals("groupid")) {
					list.add(Integer.parseInt(value));
				}
			}
			service.delGroupMem(list);
		}
		//刪除揪團全部成員
		if("delAllGroupMember".equals(action)) {
			Integer id = Integer.parseInt(req.getParameter("groupid"));
			service.deleteAll(id);
		}
		//退出群組
		if("exitGroup".equals(action)) {
			Integer id = Integer.parseInt(req.getParameter("member"));
			Integer group = Integer.parseInt(req.getParameter("groupId"));
			service.exitGroup(id, group);
		}
	}
}
