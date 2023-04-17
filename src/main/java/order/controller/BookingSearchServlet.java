package order.controller;

import com.google.gson.Gson;
import order.model.CompanyVO;
import order.service.BookingSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/BookingSearch.do")
public class BookingSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        BookingSearchService bsSvc = new BookingSearchService();
        Gson gson = new Gson();


       if(req.getParameter("type").equals("showCompanyByPosition")){
           System.out.println("addressCounty"+req.getParameter("addressCounty"));

           try {
               String addressCounty = req.getParameter("addressCounty");

               List<CompanyVO> companyVOs = bsSvc.showCompanyAndRoomTypePriceByPosition(addressCounty);

               res.setCharacterEncoding("UTF-8");
               res.setContentType("application/json");
               PrintWriter out = res.getWriter();
               out.print(gson.toJson(companyVOs));
           }catch (Exception e){
               System.out.println("BookingSearch.do_showCompanyByPosition: "+e.getMessage());
           }

       }else if(req.getParameter("type").equals("showCompanyListByCompanyNameOrCountyOrArea")){

           System.out.println("keyword"+req.getParameter("keyword"));

           try {
               String keyword = req.getParameter("keyword");

               List<CompanyVO> companyVOs = bsSvc.showCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(keyword);

               res.setCharacterEncoding("UTF-8");
               res.setContentType("application/json");
               PrintWriter out = res.getWriter();
               out.print(gson.toJson(companyVOs));
           }catch (Exception e){
               System.out.println("BookingSearch.do_showCompanyListByCompanyNameOrCountyOrArea: "+e.getMessage());
           }

       }





    }
}
