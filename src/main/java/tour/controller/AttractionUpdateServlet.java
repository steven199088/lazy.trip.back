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

import tour.model.AttractionVO;
import tour.service.AttractionService;
import tour.service.AttractionServiceImpl;

@WebServlet("/attractionUpdate")
@MultipartConfig
public class AttractionUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        AttractionVO attractionVO = gson.fromJson(req.getReader(), AttractionVO.class);
        try {
            AttractionService service = new AttractionServiceImpl();
            final AttractionVO result = service.attrUpdate(attractionVO);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(result));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
