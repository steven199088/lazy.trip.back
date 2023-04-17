package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourComVO;
import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/tourComUpdate")
@MultipartConfig
public class TourComUpdateServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        TourComVO tourComVO = gson.fromJson(req.getReader(), TourComVO.class);
        TourComService service;
		try {
			service = new TourComServiceImpl();
			final TourComVO result = service.tourComUpdate(tourComVO);
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(result));
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
}
