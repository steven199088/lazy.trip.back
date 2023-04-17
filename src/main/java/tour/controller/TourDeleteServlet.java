package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.service.TourService;
import tour.service.TourServiceImpl;

@WebServlet("/tourDelete")
public class TourDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tourId = req.getParameter("tourId");
        try {
            TourService service = new TourServiceImpl();
            final String resultStr = service.tourDelete(Integer.valueOf(tourId));
            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(resultStr));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}


