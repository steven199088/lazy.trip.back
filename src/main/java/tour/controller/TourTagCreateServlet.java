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

@WebServlet("/tourTagCreate")
public class TourTagCreateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Gson gson = new Gson();
			req.setCharacterEncoding("UTF-8");
			TourTagVO tourTagVO = gson.fromJson(req.getReader(), TourTagVO.class);
			TourTagService service = new TourTagServiceImpl();
			int result = service.tourTagCreate(tourTagVO);
			System.out.println(tourTagVO.toString());
			resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(result));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
