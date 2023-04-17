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

@WebServlet("/tourUpdate")
@MultipartConfig
public class TourUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        TourVO tourVO = gson.fromJson(req.getReader(), TourVO.class);
        try {
            TourService service = new TourServiceImpl();
            final TourVO result = service.tourUpdate(tourVO);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }


//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Gson gson = new Gson();
//		TourVO tourVO = gson.fromJson(req.getReader(), TourVO.class);
//		String tourId = req.getParameter("tourId");
//		String tourTitle = req.getParameter("tourTitle");
//		String startDate = req.getParameter("startDate");
//		String endDate = req.getParameter("endDate");
//		String tourImg = req.getParameter("endDate");
//		final Part part = req.getPart("avatar");
//		try {
//			TourService service = new TourServiceImpl();
//			final TourVO result = service.tourUpdate(tourVO);
//			resp.setContentType("application/json");
//			resp.getWriter().print(gson.toJson(result));
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		
//	}
}

