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

import tour.model.TourScheduleVO;
import tour.service.TourScheduleService;
import tour.service.TourScheduleServiceImpl;

@WebServlet("/tourScheQueryAll")
public class TourScheQueryAllServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            TourScheduleService service = new TourScheduleServiceImpl();
            final List<TourScheduleVO> resultLists = service.tourScheQueryAll();
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(resultLists));
            System.out.println(gson.toJson(resultLists));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
