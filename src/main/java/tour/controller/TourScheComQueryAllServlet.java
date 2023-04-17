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

@WebServlet("/tourScheComQueryAll")
public class TourScheComQueryAllServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
        	String companyId = req.getParameter("companyId");
            TourScheduleComService service = new TourScheduleComServiceImpl();
            final List<TourScheduleComVO> resultLists = service.tourScheduleComQueryAll(Integer.valueOf(companyId));
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(resultLists));
            System.out.println(gson.toJson(resultLists));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
