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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/api/friend")
public class FriendController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 按類型查詢會員：friend 好友、好友建議 suggestion、已封鎖會員 blocklist
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        Map<String, String[]> parametersMap = request.getParameterMap();
        MemberPagerAndSorter pagerAndSorter = new MemberPagerAndSorter();

        int id = Integer.parseInt(parametersMap.get("member_id")[0]);
        String queryType = parametersMap.get("query_type")[0];
        pagerAndSorter.setLimit(Integer.parseInt(parametersMap.get("limit")[0]));
        pagerAndSorter.setOffset(Integer.parseInt(parametersMap.get("offset")[0]));
        pagerAndSorter.setSortingColumn(parametersMap.get("sortingColumn")[0]);
        pagerAndSorter.setSortingOrder(parametersMap.get("sortingOrder")[0]);

        List<Member> dataList = null;
        if (parametersMap.containsKey("search_text") && parametersMap.get("search_text")[0].length() != 0) {
            String text = parametersMap.get("search_text")[0];
            dataList = switch (queryType) {
                case "friend" -> service.getFriends(id, text, pagerAndSorter);
                case "suggestion" -> service.getFriendSuggestions(id, text, pagerAndSorter);
                case "blocklist" -> service.getBlockedMembers(id, text, pagerAndSorter);
                default -> new ArrayList<>();
            };
        } else {
            dataList = switch (queryType) {
                case "friend" -> service.getFriends(id, pagerAndSorter);
                case "suggestion" -> service.getFriendSuggestions(id, pagerAndSorter);
                case "blocklist" -> service.getBlockedMembers(id, pagerAndSorter);
                default -> new ArrayList<>();
            };
        }

//        if (dataList.size() == 0) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        } else {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }

        output = gson.toJson(new ModelWrapper(dataList));
        out.println(output);
    }

}
