package tour.controller;

import java.io.IOException;
import java.util.Set;

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

@WebServlet("/tourTagQueryByTour")
public class TourTagQueryByTourIdServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Gson gson = new Gson();
			String memberId = req.getParameter("memberId");
			String tourId = req.getParameter("tourId");
			TourTagVO tourTagVO = new TourTagVO();
			tourTagVO.setMemberId(Integer.valueOf(memberId));
			tourTagVO.setTourId(Integer.valueOf(tourId));
//			System.out.println("tourTagVO:"+tourTagVO.toString());
			TourTagService service = new TourTagServiceImpl();
			final Set<String> resultSet = service.tourQueryByTourId(tourTagVO);
//			System.out.println(tourTagVO.toString());
			resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(resultSet));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
