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

import tour.model.TourVO;
import tour.service.TourService;
import tour.service.TourServiceImpl;

@WebServlet("/TourQueryOneByTourId")
public class TourQueryOneByTourIdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        String tourId = req.getParameter("tourId");
        try {
            TourService service = new TourServiceImpl();
            final TourVO result = service.getTourInfoByTourId(Integer.valueOf(tourId));
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
