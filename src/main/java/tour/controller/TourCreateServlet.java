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

import tour.model.TourVO;
import tour.service.TourService;
import tour.service.TourServiceImpl;

@WebServlet("/tourCreate")
@MultipartConfig
public class TourCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 前端將資料利用JSON格式傳送到controller，req接收下來，用java IO的方法getReader讀進來，把資料轉成我要的型別(GSON)
        try {
            Gson gson = new Gson();
            req.setCharacterEncoding("UTF-8");
            TourVO tourVO = gson.fromJson(req.getReader(), TourVO.class);
            TourService service = new TourServiceImpl();
            int result = service.tourCreate(tourVO);
            tourVO.setTourId(result);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(tourVO));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
