package company.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import company.model.EquipmentVO;
import company.service.EquipmentService;

@WebServlet("/equipmentServlet")
public class EquipmentServlet extends HttpServlet {

	private static final String EquipmentName = null;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("equipmentID");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入設備編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer equipmentID = null;
			try {
				equipmentID = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("設備編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			EquipmentService equipmentService = new EquipmentService();
			EquipmentVO equipmentVO = equipmentService.getOneEquipment(equipmentID);
			if (equipmentVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("equipmentVO", equipmentVO); // 資料庫取出的empVO物件,存入req
			String url = "/company/listOneCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer equipmentID = Integer.valueOf(req.getParameter("equipmentID"));

			/*************************** 2.開始查詢資料 ****************************************/
			EquipmentService equipmentService = new EquipmentService();
			EquipmentVO equipmentVO = equipmentService.getOneEquipment(equipmentID);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("equipmentVO", equipmentVO); // 資料庫取出的empVO物件,存入req
			String url = "/company/update_company_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_comapny_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer equipmentID = Integer.valueOf(req.getParameter("equipmentID").trim());

			String equipmentName = req.getParameter("equipmentName");
			String equipmentNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
			if (equipmentName == null || equipmentName.trim().length() == 0) {
				errorMsgs.add("設備名稱: 請勿空白");
			} else if (!equipmentName.trim().matches(equipmentNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("設備名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
			}

			String equipmentDesc = req.getParameter("equipmentDesc").trim();
			if (equipmentDesc == null || equipmentDesc.trim().length() == 0) {
				errorMsgs.add("職位請勿空白");
			}

			EquipmentVO equipmentVO = new EquipmentVO();
			equipmentVO.setEquipmentID(equipmentID);
			equipmentVO.setEquipmentName(equipmentName);
			equipmentVO.setEquipmentDesc(equipmentDesc);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("equipmentVO", equipmentVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/company/update_company_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			EquipmentService equipmentService = new EquipmentService();
			equipmentVO = equipmentService.updateEquipment(equipmentID, equipmentName, equipmentDesc);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("equipmentVO", equipmentVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/company/listOneCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer equipmentID = Integer.valueOf(req.getParameter("equipmentID").trim());

			String equipmentName = req.getParameter("equipmentName").trim();
			if (equipmentName == null || equipmentName.trim().length() == 0) {
				errorMsgs.add("請勿空白");
			}

			String equipmentDesc = req.getParameter("equipmentDesc").trim();
			if (equipmentDesc == null || equipmentDesc.trim().length() == 0) {
				errorMsgs.add("請勿空白");
			}

			EquipmentVO equipmentVO = new EquipmentVO();
			equipmentVO.setEquipmentID(123);
			equipmentVO.setEquipmentName("安安");
			equipmentVO.setEquipmentDesc("你好");

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("equipmentVO", equipmentVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/equipment/addequipment.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			EquipmentService equipmentSvc = new EquipmentService();
			equipmentVO = equipmentSvc.addEquipment(equipmentID, equipmentName, equipmentDesc);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/company/addCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer equipmentID = Integer.valueOf(req.getParameter("equipmentID"));

			/*************************** 2.開始刪除資料 ***************************************/
			EquipmentService equipmentService = new EquipmentService();
			equipmentService.deleteEquipment(equipmentID);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/company/listAllCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}

		if ("1".equals(action)) {

			Gson gson = new Gson();
			EquipmentVO jsonObject = gson.fromJson(req.getReader(), EquipmentVO.class);
			System.out.println(jsonObject.getEquipmentName());
			EquipmentService service = new EquipmentService();
			List<EquipmentVO> list = service.getAll();
			res.setContentType("application/json");
		    res.getWriter().write(gson.toJson(list));
		}
	}
}
