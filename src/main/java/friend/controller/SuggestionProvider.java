package friend.controller;

import com.google.gson.Gson;
import friend.json.ModelWrapper;
import friend.repository.MemberPagerAndSorter;
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
import java.util.Map;

@WebServlet("/api/friend-suggestions")
public class SuggestionProvider extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 查詢沒有任何好友關係的會員，作為建議
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        FriendMemberService service = new FriendMemberServiceImpl();
        Map<String, String[]> parametersMap = request.getParameterMap();
        MemberPagerAndSorter pagerAndSorter = new MemberPagerAndSorter();

        int id = Integer.parseInt(parametersMap.get("member_id")[0]);
        pagerAndSorter.setLimit(Integer.parseInt(parametersMap.get("limit")[0]));
        pagerAndSorter.setOffset(Integer.parseInt(parametersMap.get("offset")[0]));
        pagerAndSorter.setSortingColumn(parametersMap.get("sortingColumn")[0]);
        pagerAndSorter.setSortingOrder(parametersMap.get("sortingOrder")[0]);

        ModelWrapper wrapper = new ModelWrapper(service.getFriendSuggestions(id, pagerAndSorter));
        out.println(gson.toJson(wrapper));
    }

}
