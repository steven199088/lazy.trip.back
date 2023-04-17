package group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import group.model.GroupVO;
import group.model.Group_memberVO;
import group.service.GroupService;
import member.model.Member;
import redis.clients.jedis.Jedis;
import tour.model.TourVO;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
	// Service
	GroupService service = new GroupService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		JsonObject error = new JsonObject();

		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if ("getUsingMember".equals(action)) {
			PrintWriter out = res.getWriter();
			String jsonStr1 = new Gson().toJson(member);
			System.out.println(jsonStr1);
			out.println(jsonStr1);
			return;
		}

		if ("getOneGroup".equals(action)) {
			// 進入main頁面顯示揪團資訊
			GroupVO groupVO = new GroupVO();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			PrintWriter out = res.getWriter();
			groupVO = service.getOneGroupInfo(groupid);
			List list = new ArrayList();
			list.add(groupVO);
			list.add(member);
//			String jsonStr1 = new Gson().toJson(groupVO);
			String jsonStr1 = new Gson().toJson(list);
//			System.out.println(jsonStr1);
			System.out.println(jsonStr1);
			out.println(jsonStr1);
			return;
		}

		if ("addOneGroup".equals(action)) {
			Gson gson = new Gson();
			GroupVO groupVO = new GroupVO();
			Group_memberVO groupmemberVO = new Group_memberVO();
			// groupVO:創建時新增揪團
			// groupmemberVO:創建時將創辦人加入groupmember
			try {
				Integer gpCount = Integer.valueOf(req.getParameter("groupmembercount"));
				if (gpCount >= 0 && gpCount != null) {
					groupVO.setGroupmembercount(gpCount);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
//				res.sendRedirect(req.getRequestURL().toString());
			}
			Integer groupOwner = Integer.valueOf(member.getId());
			Integer howTojoin = Integer.valueOf(req.getParameter("how_2_join"));
			String name = req.getParameter("groupname");

			if (name.trim().length() != 0 && name != null && groupOwner != null && howTojoin != null && howTojoin > 0
					&& howTojoin < 3) {

				groupVO.setGroupname(name);
				groupVO.setMemberid(groupOwner);
				groupVO.setIfjoingroupdirectly(howTojoin);

				int pk = service.addGroup(groupVO);
				groupmemberVO.setMemberid(groupOwner);
				groupmemberVO.setGroupid(pk);
				service.InsertWhenCreate(groupmemberVO);
				groupVO.setGroupid(pk);
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(groupVO));
				return;
			} else {
				error.addProperty("errorMessage", "請輸入正確資訊");
				PrintWriter out = res.getWriter();
				out.println(new Gson().toJson(error));
				return;

			}

		}

		// 取得揪團當前的行程名稱
		if ("getTourInfo".equals(action)) {
			// 進入main頁面顯示揪團資訊
			Integer tourid = Integer.parseInt(req.getParameter("tourid"));
			PrintWriter out = res.getWriter();
			TourVO tourvo = new TourVO();
			tourvo = service.getOneTourInfo(tourid);
			String jsonStr = new Gson().toJson(tourvo);
			out.println(jsonStr);
			return;
		}
		
		//產生邀請連結
		if("generateLink".equals(action)) {
			Jedis jedis = new Jedis();
			String id = req.getParameter("groupId");
			String link = UUID.randomUUID().toString().replace("-", "");
			jedis.setex("groupLinks:"+link,300,id);
			jedis.close();
			PrintWriter out = res.getWriter();
			out.println(link);
			return;
		}
		
		if (action.equals("") || action.trim().length() == 0 || !action.isEmpty()) {
			error.addProperty("errorMessage", "Unknown Action");
			PrintWriter out = res.getWriter();
			out.println(new Gson().toJson(error));
			return;
		}
	}

	public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		JsonObject error = new JsonObject();
		GroupService service = new GroupService();
		// 修改揪團資訊
		if ("groupSetting".equals(action)) {
			GroupVO groupVO = new GroupVO();
			Integer groupId = Integer.parseInt(req.getParameter("groupid"));
			String groupName = req.getParameter("groupname");
			Integer groupMemberCount = Integer.parseInt(req.getParameter("groupmembercount"));
			Integer ifjoingroupdirectly = Integer.parseInt(req.getParameter("ifjoingroupdirectly"));

			groupVO.setGroupid(groupId);
			groupVO.setGroupmembercount(groupMemberCount);
			groupVO.setGroupname(groupName);
			groupVO.setIfjoingroupdirectly(ifjoingroupdirectly);
			service.updateGroupInfo(groupVO);
			PrintWriter out = res.getWriter();
			String jsonStr = new Gson().toJson(groupVO);
			out.println(jsonStr);
			return;
		}
		if ("groupSetTour".equals(action)) {
			GroupVO groupVO = new GroupVO();
			Integer groupId = Integer.parseInt(req.getParameter("groupId"));
			Integer tourId = Integer.parseInt(req.getParameter("tourId"));
			groupVO.setGroupid(groupId);
			groupVO.setTourid(tourId);
			service.updateGroupTour(groupVO);
			return;
		}

		if (action.equals("") || action.trim().length() == 0 || !action.isEmpty()) {
			error.addProperty("errorMessage", "Unknown Action");
			PrintWriter out = res.getWriter();
			out.println(new Gson().toJson(error));
			return;
		}
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");

		if ("delOneGroup".equals(action)) {
			Integer id = Integer.parseInt(req.getParameter("groupid"));
			service.delOneGroup(id);
		}
	}
}
