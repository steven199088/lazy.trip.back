package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourTagVO;
import tour.service.TourTagService;
import tour.service.TourTagServiceImpl;

@WebServlet("/tourTagDelete")
public class TourTagDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memberId = req.getParameter("memberId");
		String tourTagTitle = req.getParameter("tourTagTitle");
		try {
			TourTagService service = new TourTagServiceImpl();
			TourTagVO tourTagVO = new TourTagVO();
			tourTagVO.setMemberId(Integer.valueOf(memberId));
			tourTagVO.setTourTagTitle(tourTagTitle);
			String resultStr = service.tourTagDelete(tourTagVO);
			System.out.println(tourTagVO.toString());
			Gson gson = new Gson();
			resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(resultStr));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
