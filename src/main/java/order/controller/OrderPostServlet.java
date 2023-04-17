package order.controller;

import com.google.gson.reflect.TypeToken;

import member.model.Member;
import order.model.OrderVO;
import order.service.OrderService;
import order.utils.GsonLocalDateAndTimeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/CreateOrderAll")
public class OrderPostServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		GsonLocalDateAndTimeUtils gsonUtils = new GsonLocalDateAndTimeUtils();
		OrderService orderSvc = new OrderService();
		Member memberLogin = (Member) req.getSession().getAttribute("member");
		
		try {
			List<OrderVO> orderVOs = gsonUtils.fromJson(req.getReader(), new TypeToken<List<OrderVO>>() {
			}.getType());
			
			for(int i =0;i<orderVOs.size();i++) {
				System.out.println(orderVOs.get(i).toString());
			}

			int orderID = orderSvc.addOrderAndOrderDetail(orderVOs);

			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			PrintWriter out = res.getWriter();
			out.print(gsonUtils.toJson(orderID));
		} catch (Exception e) {
			System.out.println("Create order all failed: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
