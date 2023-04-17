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

import tour.model.TourComDTO;
import tour.model.TourComVO;
import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/tourComSelected")
public class TourComSelectedServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        req.setCharacterEncoding("UTF-8");
        TourComDTO tourComVO = gson.fromJson(req.getReader(), TourComDTO.class);
		try {
			TourComService service = new TourComServiceImpl();
			final List<TourComVO> result = service.queryAllBySelection(tourComVO);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(gson.toJson(result));
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
}

