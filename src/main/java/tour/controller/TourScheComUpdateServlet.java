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

import tour.model.TourScheduleComVO;
import tour.service.TourScheduleComService;
import tour.service.TourScheduleComServiceImpl;

@WebServlet("/tourScheComUpdate")
@MultipartConfig
public class TourScheComUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        req.setCharacterEncoding("UTF-8");
        TourScheduleComVO tourScheduleComVO = gson.fromJson(req.getReader(), TourScheduleComVO.class);
        try {
            TourScheduleComService service = new TourScheduleComServiceImpl();
            final TourScheduleComVO result = service.tourScheduleComUpdate(tourScheduleComVO);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}