package friend.controller;

import com.google.gson.Gson;
import friend.json.ModelWrapper;
import friend.repository.MemberPagerAndSorter;
import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;
import member.model.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.List;
import java.util.Map;

@WebServlet("/api/friend/request")
public class FriendRequestController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 新增邀請：邀請方為 requester，接受方為 addressee
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        FriendMemberService service = new FriendMemberServiceImpl();
        int requesterId = Integer.parseInt(request.getParameter("requester_id"));
        int addresseeId = Integer.parseInt(request.getParameter("addressee_id"));

        out.println(gson.toJson(service.createFriendRequest(requesterId, addresseeId)));
    }

    // 按動作更新關係狀態：accept 接受、block 封鎖
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        int requesterId = Integer.parseInt(request.getParameter("requester_id"));
        int addresseeId = Integer.parseInt(request.getParameter("addressee_id"));
        String action = request.getParameter("action");
        
        if (action.equals("accept")) {
        	output = gson.toJson(service.acceptFriendRequest(requesterId, addresseeId));
        } else if (action.equals("block")) {
        	output = gson.toJson(service.blockFriendRequest(requesterId, addresseeId));
        } else {
        	output = "{error:Failed}";
        }

        out.println(output);
    }
    
    // 刪除好友邀請關係：取消邀請、婉拒邀請或解除封鎖
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String output;
        
        FriendMemberService service = new FriendMemberServiceImpl();
        int requesterId = Integer.parseInt(request.getParameter("requester_id"));
        int addresseeId = Integer.parseInt(request.getParameter("addressee_id"));
        output = gson.toJson(service.removeFriendRequest(requesterId, addresseeId));
        
    	out.println(output);
    }
    
    // 按方向查詢好友邀請對象：sent 送出、received 收到
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        Map<String, String[]> parametersMap = request.getParameterMap();
        System.out.println(parametersMap);
        MemberPagerAndSorter pagerAndSorter = new MemberPagerAndSorter();
        int id = Integer.parseInt(parametersMap.get("member_id")[0]);
        String direction = parametersMap.get("direction")[0];
        pagerAndSorter.setLimit(Integer.parseInt(parametersMap.get("limit")[0]));
        pagerAndSorter.setOffset(Integer.parseInt(parametersMap.get("offset")[0]));
        pagerAndSorter.setSortingColumn(parametersMap.get("sortingColumn")[0]);
        pagerAndSorter.setSortingOrder(parametersMap.get("sortingOrder")[0]);

        List<Member> dataList = null;
        if (direction.equals("sent")) {
            dataList = service.getSentRequests(id, pagerAndSorter);
        } else if (direction.equals("received")) {
            dataList = service.getReceivedRequests(id, pagerAndSorter);
        }

        output = gson.toJson(new ModelWrapper(dataList));
        out.println(output);

    }

}
