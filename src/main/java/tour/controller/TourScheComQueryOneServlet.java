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

import tour.model.TourScheduleComVO;
import tour.service.TourScheduleComService;
import tour.service.TourScheduleComServiceImpl;

@WebServlet("/tourScheComQueryOne")
public class TourScheComQueryOneServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Gson gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        String tourComId = req.getParameter("tourComId");
        try {
            TourScheduleComService service = new TourScheduleComServiceImpl();
            final List<TourScheduleComVO> resultLists = service.tourScheComQueryOne(Integer.valueOf(tourComId));
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(resultLists));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
