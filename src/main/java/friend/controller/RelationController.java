package friend.controller;

import com.google.gson.Gson;
import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/api/relation")
public class RelationController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 查詢 [本人 specifier] 和 [對方 other] 兩者間是否已建立關係、關係為何
    // isRelated: false 無關係，可邀請成為好友
    // isRelated: true 有關係
    // ---- type: 封鎖對方、邀請對方、對方邀請、彼此好友
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        int specifierId = Integer.parseInt(request.getParameter("specifier_id"));
        int otherId = Integer.parseInt(request.getParameter("other_id"));
        output = gson.toJson(service.checkFriendshipBetween(specifierId, otherId));

        out.println(output);
    }

}
