package order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import member.model.Member;
import order.model.LinePayVO;
import order.model.OrderVO;
import order.service.OrderService;
import order.service.PayMailService;
import order.service.PayService;
import order.utils.GsonLocalDateAndTimeUtils;

@WebServlet("/Pay.do")
public class PayServlet extends HttpServlet {

	LinePayVO linePayVO;

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String channelId = "你的channelId";
		String channelSecret = "你的channelSecret";
		String url = "https://sandbox-api-pay.line.me/v2/payments/request";

		Gson gson = new Gson();

		req.setCharacterEncoding("UTF-8");

		linePayVO = gson.fromJson(req.getReader(), new TypeToken<LinePayVO>() {
		}.getType());

		JSONObject payJson = new JSONObject();

		// 將需要的屬性添加到JSONObject中
		payJson.put("productName", linePayVO.getProductName());
		payJson.put("amount", linePayVO.getAmount());
		payJson.put("currency", linePayVO.getCurrency());
		payJson.put("confirmUrl", linePayVO.getConfirmUrl());
		payJson.put("orderId", linePayVO.getLinePayOrderID());

		// 將JSONObject轉換為字串
		String requestBody = payJson.toString();

		// 設定request header
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-ChannelSecret", channelSecret);
		conn.setDoOutput(true);

		// 設定request body
		OutputStream os = conn.getOutputStream();
		os.write(requestBody.getBytes());
		os.flush();

		// 讀取回應
		int statusCode = conn.getResponseCode();
		if (statusCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			String responseBody = responseBuffer.toString();
			res.getWriter().append(responseBody);
		} else {
			// 回應非200 OK，處理錯誤
			String errorResponse = "API Error: " + conn.getResponseMessage();
			res.getWriter().append(errorResponse);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String channelId = "你的channelId";
		String channelSecret = "你的channelSecret";
		PayService paySvc  = new PayService();
		PayMailService payMailService = new PayMailService();
		OrderService orderService = new OrderService();

		req.setCharacterEncoding("UTF-8");
		String transactionId = req.getParameter("transactionId");
		System.out.println("transactionId: " + transactionId);

		String url = "https://sandbox-api-pay.line.me/v2/payments/" + transactionId + "/confirm";

		// 將需要的屬性添加到JSONObject
		JSONObject payConfirmJson = new JSONObject();
		payConfirmJson.put("amount", linePayVO.getAmount());
		payConfirmJson.put("currency", linePayVO.getCurrency());

		// 將JSONObject轉換為字串
		String requestBody = payConfirmJson.toString();

		// 設定request header
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-ChannelSecret", channelSecret);
		conn.setDoOutput(true);

		// 設定request body
		OutputStream os = conn.getOutputStream();
		os.write(requestBody.getBytes());
		os.flush();
		
		// 創建一個JSONObject
		JSONObject returnResult; 
		

		// 讀取回應
		int statusCode = conn.getResponseCode();
		if (statusCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			String responseBody = responseBuffer.toString();
            //responseBody轉成JSONObject
			returnResult = new JSONObject(responseBody);

			String contextPath = req.getContextPath();
			
			// 從JSONObject中獲取returnMessage的值
			String returnMessage = returnResult.getString("returnMessage");
			
			//值是Success.就導向付款成功網址
			if(returnMessage.equals("Success.")) {
//				寄送訂單到信箱
				int result = paySvc.orderPay(linePayVO.getOrderID());
				if(result == 1) {
	
					
					List<OrderVO> orderVOs  =  orderService.showOrderAllAndStatusAlreadyPayByOrderID(linePayVO.getOrderID());
					Member meber = orderService.showMemberByMemberID(orderVOs.get(0).getMemberID());
					
					
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
							+ "        親愛的&nbsp;"+meber.getName()+"，你的預訂已確認且完成付款&nbsp;<span\r\n"
							+ "          style=\"color: blue\"\r\n"
							+ "          >TWD&nbsp;"+orderVOs.get(0).getOrderTotalPrice()+"。</span\r\n"
							+ "        >\r\n"
							+ "      </div>\r\n"
							+ "      <div style=\"height: 30px\"></div>\r\n"
							+ "      <div style=\"font-size: 20px\">飯店名稱:&emsp;<span>"+orderVOs.get(0).getCompanyVO().getCompanyName()+"</span></div>\r\n"
							+ "      <div style=\"font-size: 20px\">\r\n"
							+ "        飯店地址:&emsp;<span>"+orderVOs.get(0).getCompanyVO().getAddressCounty()+", "+orderVOs.get(0).getCompanyVO().getAddressArea()+", "+orderVOs.get(0).getCompanyVO().getAddressStreet()+"</span>\r\n"
							+ "      </div>\r\n"
							+ "      <div style=\"font-size: 20px\">入住日期:&emsp;<span>"+orderVOs.get(0).getOrderCheckInDate()+"</span></div>\r\n"
							+ "      <div style=\"font-size: 20px\">退房日期:&emsp;<span>"+orderVOs.get(0).getOrderCheckOutDate()+"</span></div>\r\n"
							+ "      <div style=\"font-size: 20px\">住宿期間:&emsp;<span>"+orderVOs.get(0).getOrderNumberOfNights()+"晚</span></div>\r\n"
							+ "      <div style=\"font-size: 20px\">確認號碼:&emsp;<span>"+orderVOs.get(0).getOrderID()+"</span></div>\r\n"
							+ "      <div style=\"height: 20px\"></div>\r\n"
							+ "      <div style=\"font-size: 20px\">感謝您使用LazyTrip.io，祝您旅途愉快！</div>\r\n"
							+ "    </div>";
					payMailService.sendMail(to, subject, messageText);
					
					String successUrl = contextPath + "/page/order/order_pay_success.html";
					res.sendRedirect(successUrl);
				}else if(result == 0){
					String failUrl = contextPath + "/page/order/order_pay_fail.html";
					res.sendRedirect(failUrl);
				}
		    //否則就導向付款失敗網址
			}else {
				String failUrl = contextPath + "/page/order/order_pay_fail.html";
				res.sendRedirect(failUrl);
			}
		} else {
			// 回應非200 OK，處理錯誤
			String errorResponse = "API Error: " + conn.getResponseMessage();
			System.out.println("付款失敗");
		}

	}

}