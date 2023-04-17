package tour.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourComVO;
import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/recommendTourByMember")
public class RecommendTourByMemberServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Gson gson = new Gson();
			String memberId = req.getParameter("memberId");
			TourComService service = new TourComServiceImpl();
			final List<TourComVO> result = service.queryAll(Integer.valueOf(memberId));
			if(result != null && result.size() > 0) {
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(gson.toJson(result));
			} else {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
