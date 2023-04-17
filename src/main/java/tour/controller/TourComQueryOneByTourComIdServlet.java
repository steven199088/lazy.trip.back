package tour.controller;

import java.io.IOException;

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

@WebServlet("/TourComQueryOneByTourComId")
public class TourComQueryOneByTourComIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        String tourComId = req.getParameter("tourComId");
        TourComService service;
		try {
			service = new TourComServiceImpl();
			final TourComVO result = service.getTourInfoByTourComId(Integer.valueOf(tourComId));
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(result));
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
}

