package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.service.AttractionService;
import tour.service.AttractionServiceImpl;

@WebServlet("/AttractionDelete")
public class AttractionDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attractionId = req.getParameter("attractionId");
        try {
            AttractionService service = new AttractionServiceImpl();
            final String resultStr = service.attrDelete(Integer.valueOf(attractionId));
            resp.setContentType("application/json");
            Gson gson = new Gson();
            resp.getWriter().print(gson.toJson(resultStr));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}


