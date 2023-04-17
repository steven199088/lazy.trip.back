package order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.Member;
import order.model.OrderVO;
import order.service.OrderService;
import order.service.PayMailService;

@WebServlet("/Email.do")
public class PayMailServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PayMailService payMailService = new PayMailService();
		OrderService orderService = new OrderService();
		
		List<OrderVO> orderVOs  =  orderService.showOrderAllAndStatusAlreadyPayByOrderID(41);
		Member meber = orderService.showMemberByMemberID(31);
		orderVOs.get(0).getOrderID();
		String to = meber.getAccount();
		String subject = "訂單編號#"+orderVOs.get(0).getOrderID()+" 已確認  │ 入住日期："+orderVOs.get(0).getOrderCheckInDate();;
		String messageText = "<div\r\n"
				+ "      style=\"\r\n"
				+ "        height: 600px;\r\n"
				+ "        width: 700px;\r\n"
				+ "        background-color: white;\r\n"
				+ "        position: absolute;\r\n"
				+ "        top: 50%;\r\n"
				+ "        left: 50%;\r\n"
				+ "\r\n"
				+ "        margin-top: -300px;\r\n"
				+ "        margin-left: -350px;\r\n"
				+ "      \"\r\n"
				+ "    >\r\n"
				+ "      <strong style=\"font-size: 50px; color: green\">預訂成功！</strong>\r\n"
				+ "      <div style=\"height: 20px\"></div>\r\n"
				+ "      <div style=\"font-size: 20px\">\r\n"
				+ "        親愛的&nbsp;CHANG-FU HONG，你的預訂已確認且完成付款&nbsp;<span\r\n"
				+ "          style=\"color: blue\"\r\n"
				+ "          >TWD&nbsp;3635。</span\r\n"
				+ "        >\r\n"
				+ "      </div>\r\n"
				+ "      <div style=\"height: 30px\"></div>\r\n"
				+ "      <div style=\"font-size: 20px\">飯店名稱:&emsp;<span>殺多寶</span></div>\r\n"
				+ "      <div style=\"font-size: 20px\">\r\n"
				+ "        飯店地址:&emsp;<span>台灣台中市北區柳川西路四段1號, 12樓</span>\r\n"
				+ "      </div>\r\n"
				+ "      <div style=\"font-size: 20px\">入住日期:&emsp;<span>2022-10-15</span></div>\r\n"
				+ "      <div style=\"font-size: 20px\">退房日期:&emsp;<span>2022-10-15</span></div>\r\n"
				+ "      <div style=\"font-size: 20px\">住宿期間:&emsp;<span>1晚</span></div>\r\n"
				+ "      <div style=\"font-size: 20px\">確認號碼:&emsp;<span>231</span></div>\r\n"
				+ "      <div style=\"height: 20px\"></div>\r\n"
				+ "      <div style=\"font-size: 20px\">感謝您使用LazyTrip.io，祝您旅途愉快！</div>\r\n"
				+ "    </div>";
		payMailService.sendMail(to, subject, messageText);
		
		
	}

}
