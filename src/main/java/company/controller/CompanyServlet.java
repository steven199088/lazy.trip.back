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

import company.model.CompanyVO;
import company.model.RoomTypeVO;
import company.service.CompanyService;
import company.service.RoomTypeService;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {

	private static final String CompanyUserName = null;

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
			String str = req.getParameter("companyID");

			Integer companyID = null;



			/*************************** 2.開始查詢資料 *****************************************/
			CompanyService companyService = new CompanyService();

			CompanyVO companyVO = companyService.getOneCompany(companyID);


			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("companyVO", companyVO); // 資料庫取出的compVO物件,存入req

			Gson gson = new Gson();
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(companyVO));

		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer companyID = Integer.valueOf(req.getParameter("companyID"));

			/*************************** 2.開始查詢資料 ****************************************/
			CompanyService companyService = new CompanyService();
			CompanyVO companyVO = companyService.getOneCompany(companyID);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			
			Gson gson = new Gson();
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(companyVO));

		}

		if ("update".equals(action)) { // 來自update_comapny_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer companyID = Integer.valueOf(req.getParameter("companyID").trim());

			String companyUserName = req.getParameter("companyUserName");
			String companyPassword = req.getParameter("companyPassword");
			String taxID = req.getParameter("taxID");
			String companyName = req.getParameter("companyName");
			String introduction = req.getParameter("introduction");
			String addressCounty = req.getParameter("addressCounty");
			String addressArea = req.getParameter("addressArea");
			String addressStreet = req.getParameter("addressStreet");
			Double latitude = Double.valueOf(req.getParameter("Latitude"));
			Double longitude = Double.valueOf(req.getParameter("Longitude"));
			String companyImg = req.getParameter("companyImg");

			String str = req.getParameter("");

			String companyNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
			if (companyName == null || companyName.trim().length() == 0) {
				errorMsgs.add("設備名稱: 請勿空白");
			} else if (!companyName.trim().matches(companyNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("設備名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
			}

			CompanyVO companyVO = new CompanyVO();
			companyVO.setCompanyID(companyID);
			companyVO.setCompanyUserName(companyUserName);
			companyVO.setCompanyPassword(companyPassword);
			companyVO.setTaxID(taxID);
			companyVO.setCompanyName(companyName);
			companyVO.setIntroduction(introduction);
			companyVO.setAddressCounty(addressCounty);
			companyVO.setAddressArea(addressArea);
			companyVO.setAddressStreet(addressStreet);
			companyVO.setLatitude(latitude);
			companyVO.setLongitude(longitude);
			companyVO.setCompanyImg(companyImg);

			

			/*************************** 2.開始修改資料 *****************************************/
			CompanyService companyService = new CompanyService();
			companyVO = companyService.updateCompany(companyID, companyUserName, companyPassword, taxID, companyName,
					introduction, addressCounty, addressArea, addressStreet, latitude, longitude, companyImg);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			
			Gson gson = new Gson();
			res.setContentType("application/json");

			res.getWriter().print(gson.toJson(companyVO));

		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer companyID = Integer.valueOf(req.getParameter("companyID").trim());



			CompanyVO companyVO = new CompanyVO();


			/*************************** 2.開始新增資料 ***************************************/
			CompanyService companyService = new CompanyService();
			String companyUserName = req.getParameter("companyUserName");
			String companyPassword = req.getParameter("companyPassword");
			String taxID = req.getParameter("taxID");
			String companyName = req.getParameter("companyName");
			String introduction = req.getParameter("introduction");
			String addressCounty = req.getParameter("addressCounty");
			String addressArea = req.getParameter("addressArea");
			String addressStreet = req.getParameter("addressStreet");
			Double latitude = Double.parseDouble(req.getParameter("latitude"));
			Double longitude = Double.parseDouble(req.getParameter("longitude"));
			String companyImg = req.getParameter("companyImg");

			companyVO = companyService.addCompany(companyID, companyUserName, companyPassword, taxID, companyName,
					introduction, addressCounty, addressArea, addressStreet, latitude, longitude, companyImg);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

			Gson gson = new Gson();

			res.setContentType("application/json");

			res.getWriter().print(gson.toJson(companyVO));

		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer companyID = Integer.valueOf(req.getParameter("companyID"));

			/*************************** 2.開始刪除資料 ***************************************/
			CompanyService companyService = new CompanyService();
			companyService.deleteCompany(companyID);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/company/table company test.jsp";
			Gson gson = new Gson();
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(companyID));
		}

		if ("getAllByCompanyID".equals(action)) { // 來自前端的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("companyID");


			Integer companyID = null;



			/*************************** 2.開始查詢資料 *****************************************/
			CompanyService companyService = new CompanyService();

			List<CompanyVO> companyVOList = companyService.getAllByCompanyID(companyID);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("CompanyVOList", companyVOList); // 資料庫取出的companyVOList物件,存入req
			String url = "/roomType/table roomType test.jsp";
			Gson gson = new Gson();
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(companyVOList));
		}
	
		
		
	}
}
