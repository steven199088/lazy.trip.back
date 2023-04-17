package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/tourComDelete")
public class TourComDeleteServlet extends HttpServlet  {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tourComId = req.getParameter("tourComId");
        TourComService service;
		try {
			service = new TourComServiceImpl();
			final String resultStr = service.tourComDelete(Integer.valueOf(tourComId));
			Gson gson = new Gson();
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(resultStr));
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
}
