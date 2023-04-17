package friend.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import friend.json.ModelWrapper;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;
import member.model.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/api/chat/member")
public class ChatMemberController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Gson gson = new Gson();

    @Override // 新增會員到聊天室
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        ChatMemberService service = new ChatMemberServiceImpl();
        Integer chatroomId = Integer.parseInt(request.getParameter("chatroom_id"));
        // Custom type for JSON processing
        Type typeOfMemberIdList = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> memberIds = gson.fromJson(request.getReader(), typeOfMemberIdList);

        output = gson.toJson(service.addNewChatMembers(memberIds, chatroomId));
        out.println(output);
    }

    @Override // 從聊天室刪除特定會員
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String output;

        ChatMemberService service = new ChatMemberServiceImpl();
        int memberId = Integer.parseInt(request.getParameter("member_id"));
        int chatroomId = Integer.parseInt(request.getParameter("chatroom_id"));
        output = gson.toJson(service.removeChatMember(memberId, chatroomId));

        out.println(output);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output = "false";

        ChatMemberService service = new ChatMemberServiceImpl();
        String target = request.getParameter("action");
        List<Member> dataList = null;

        if (target.equals("member")) {
            String searchText = request.getParameter("search_text");
            dataList = service.searchMembersByText(searchText);
        } else if (target.equals("chatroom_member")) {
            Integer id = Integer.parseInt(request.getParameter("chatroom_id"));
            dataList = service.getMembersByChatroom(id);
        }
        
        output = gson.toJson(new ModelWrapper(dataList));
        out.println(output);
    }
}
