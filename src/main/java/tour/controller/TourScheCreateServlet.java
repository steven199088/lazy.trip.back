package tour.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tour.model.TourScheduleVO;
import tour.service.TourScheduleService;
import tour.service.TourScheduleServiceImpl;

@WebServlet("/tourScheCreate")
@MultipartConfig
public class TourScheCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Gson gson = new Gson();
            req.setCharacterEncoding("UTF-8");
            final List<TourScheduleVO> lists = gson.fromJson(req.getReader(), new TypeToken<List<TourScheduleVO>>(){}.getType());
            System.out.println(lists.toString());
            TourScheduleService service = new TourScheduleServiceImpl();
            List<Integer> result = service.tourScheCreate(lists);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
