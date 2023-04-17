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

import tour.model.TourScheduleVO;
import tour.service.TourScheduleService;
import tour.service.TourScheduleServiceImpl;

@WebServlet("/tourScheUpdate")
@MultipartConfig
public class TourScheUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        req.setCharacterEncoding("UTF-8");
        TourScheduleVO tourScheduleVO = gson.fromJson(req.getReader(), TourScheduleVO.class);
        try {
            TourScheduleService service = new TourScheduleServiceImpl();
            final TourScheduleVO result = service.tourScheUpdate(tourScheduleVO);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
