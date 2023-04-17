package tour.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourComVO;
import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/tourComCreate")
@MultipartConfig
public class TourComCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
		req.setCharacterEncoding("UTF-8");
		TourComVO tourComVO = gson.fromJson(req.getReader(), TourComVO.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				// 驗證日期格式
				String startDate = tourComVO.getStartDate();
				dateFormat.parse(startDate);
				
				// 驗證行程名稱
				String tourComTitle = tourComVO.getTourTitle();
				if(tourComTitle.length() > 45) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print("行程名稱長度異常");
					return;
				}
				TourComService service = new TourComServiceImpl();
				int result = service.tourComCreate(tourComVO);
				tourComVO.setTourComId(result);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(gson.toJson(tourComVO));	
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (Exception e) {
			    // 日期格式不正確，回傳錯誤
			    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			    resp.getWriter().write("出發日期不正確");
			    return;
			} 
    }
}
