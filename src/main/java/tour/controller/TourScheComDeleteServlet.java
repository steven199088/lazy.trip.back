package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.service.TourScheduleComService;
import tour.service.TourScheduleComServiceImpl;

@WebServlet("/tourScheComDelete")
public class TourScheComDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tourScheduleComId = req.getParameter("tourScheduleComId");
        try {
            TourScheduleComService service = new TourScheduleComServiceImpl();
            final String resultStr = service.tourScheduleComDelete(Integer.valueOf(tourScheduleComId));
            resp.setContentType("application/json");
            Gson gson = new Gson();
            resp.getWriter().print(gson.toJson(resultStr));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
