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

@WebServlet("/tourComQueryOne")
public class TourComQueryOneServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String companyId = req.getParameter("companyId");
        TourComService service;
		try {
			service = new TourComServiceImpl();
			final List<TourComVO> resultLists = service.tourComQueryAll(Integer.valueOf(companyId));
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(gson.toJson(resultLists));
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
}

