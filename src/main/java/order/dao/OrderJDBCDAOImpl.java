package order.dao;

import common.HikariDataSource;
import member.model.Member;
import order.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderJDBCDAOImpl implements OrderDAOInterface {

	// 從定位找到該飯店的所有資料(之後再做限制筆數功能，俗稱分頁) 1?
	// 方法已做
	private static final String SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_POSITION = "SELECT cm.company_id, cm.company_name, cm.introduction, cm.address_county, cm.address_area, cm.address_street, cm.company_img, MIN(rt.roomtype_price) FROM lazy.company cm JOIN lazy.roomtype rt ON cm.company_id = rt.company_id  WHERE cm.address_county = ? GROUP BY cm.company_id;";

	// ====================================================================================

	// 透過文字搜尋框 關鍵字輸入模糊比對飯店名稱或飯店縣市或飯店區域 搜尋多個飯店資料 3?
	// 方法已做
	private static final String SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_COMPANY_NAME_OR_COUNTY_OR_AREA = "SELECT cm.company_id, cm.company_name, cm.introduction, cm.address_county, cm.address_area, cm.address_street, cm.company_img, MIN(rt.roomtype_price) FROM lazy.company cm JOIN lazy.roomtype rt ON cm.company_id = rt.company_id  WHERE cm.address_county LIKE ? OR cm.address_area LIKE ? OR cm.company_name LIKE ? GROUP BY cm.company_id;";

	// ====================================================================================

	// 從廠商編號 找到 廠商資料 1?
	// 方法已做
	private static final String SELECT_FIND_COMPANY_BY_COMPANY_ID = "SELECT company_id, company_name, introduction, address_county, address_area, address_street, latitude, longitude, company_img FROM lazy.company WHERE company_id = ?;";

	// 從 廠商編號 找到 廠商名稱 1?
	// 方法已做
	private static final String SELECT_FIND_COMPANY_NAME_BY_COMPANY_ID = "SELECT company_name FROM lazy.company WHERE company_id = ?;";

	// 從 廠商編號 找到所有房型資料 1?
	// 方法已做
	private static final String SELECT_FIND_ALL_ROOMTYPE_BY_COMPANY_ID = "SELECT company_id, roomtype_id, roomtype_name, roomtype_person, roomtype_quantity, roomtype_price FROM lazy.roomtype WHERE company_id = ?;";

	// 從 房型編號找到房型資料 1?
	// 方法已做
	private static final String SELECT_FIND_ROOMTYPE_BY_ROOMTYPE_ID = "SELECT company_id, roomtype_name, roomtype_person, roomtype_quantity, roomtype_price FROM lazy.roomtype WHERE roomtype_id = ?;";

	// 從 房型編號找到房型名稱 1?
	// 方法已做
	private static final String SELECT_FIND_ROOMTYPE_NAME_BY_ROOMTYPE_ID = "SELECT roomtype_name FROM lazy.roomtype WHERE roomtype_id = ?;";

	// 從房型id找到所有房型照片資料 1?
	// 方法已做
	private static final String SELECT_FIND_All_ROOMTYPE_IMG_BY_ROOMTYPE_ID = "SELECT roomtype_id, roomtype_img_id, roomtype_img FROM lazy.roomtype_img WHERE roomtype_id = ?;";

	// 從飯店id找到飯店所有資料(不含帳號密碼統一編號) 1?
	// 方法已做
	private static final String SELECT_FIND_COMPANY_ALL_BY_COMPANY_ID = "SELECT cm.company_id, cm.company_name, cm.introduction, cm.address_county, cm.address_area, cm.address_street, "
			+ "cm.longitude, cm.latitude, cm.company_img, rt.roomtype_id, rt.company_id, rt.roomtype_name, "
			+ "rt.roomtype_person, rt.roomtype_quantity, rt.roomtype_price, rti.roomtype_img_id, rti.roomtype_id, "
			+ "rti.roomtype_img FROM lazy.company cm JOIN lazy.roomtype rt ON cm.company_id = rt.company_id "
			+ "JOIN lazy.roomtype_img rti ON rt.roomtype_id = rti.roomtype_id WHERE cm.company_id = ? ;";

	// ====================================================================================

	// 透過文字搜尋框 下關鍵字顯示飯店名稱、縣市、區域、其餘地址 4?
	// 方法已做
	private static final String SELECT_SHOW_SEARCH_KEY_WORD_BY_COMPANY_NAME_OR_ADDRESS = "SELECT company_id, company_name, address_county, address_area, address_street FROM lazy.company WHERE company_name LIKE '%?%' OR address_county LIKE '%?%' OR address_area LIKE '%?%' OR address_street LIKE '%?%' ;";

	// ====================================================================================

	// 送出訂單與訂單明細 10?
	// 方法已做
	private static final String CREATE_ORDER = "INSERT INTO lazy.order (member_id, company_id, order_check_in_date, order_check_out_date, order_number_of_nights, order_total_price, order_pay_deadline, traveler_name, traveler_id_number, traveler_email, traveler_phone)\n"
			+ "VALUES (?, ?, ?, ?, ?, ?, DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL '23:59:59' HOUR_SECOND), ?, ?, ?, ?);";

	// 7? 方法已做
	private static final String CREATE_ORDER_DETAIL = "INSERT INTO lazy.order_detail (order_id, roomtype_id, roomtype_name, roomtype_person, order_detail_room_price, order_detail_room_quantity)\n"
			+ "VALUES (?, ?, ?, ?, ?, ?);";

	// ====================================================================================

	// 付款(更改付款狀態為"已付款") 3?
	// 方法已做
	private static final String UPDATE_ORDER_PAY = "UPDATE lazy.order SET order_status = ?, order_pay_datetime = NOW() WHERE order_id = ? AND order_status = ?;";

	// ====================================================================================

	// 付款期限到了(更改付款狀態為"已過付款期限")(要設定排程器) 0?
	// 方法已做
	private static final String UPDATE_ORDER_OVER_TIME_FOR_PAY = "UPDATE lazy.order SET order_status = '超過付款時間' WHERE order_status = '未付款' AND order_pay_deadline < NOW() ;";

	// ====================================================================================

	// (廠商使用) 透過廠商編號找到該廠商相關的所有訂單(無訂單明細) 1?
	// 方法已做
	private static final String SELECT_FIND_ORDER_BY_COMPANY_ID = "SELECT order_id, member_id, company_id, order_check_in_date, order_check_out_date, order_number_of_nights,order_total_price, order_status, order_create_datetime, order_pay_deadline, order_pay_datetime, traveler_name, traveler_id_number, traveler_email, traveler_phone FROM lazy.order WHERE company_id = ?;\n";

	// (廠商使用)選取當前訂單，查詢該筆訂單詳細資料 1?
	// 方法已做
	private static final String SELECT_FIND_ORDER_DETAIL_BY_ORDER_ID = "SELECT order_detail_id, order_id, roomtype_id, roomtype_name, roomtype_person, order_detail_room_price, order_detail_room_quantity FROM lazy.order_detail WHERE order_id = ?;";
	// ====================================================================================

	// 從訂單編號找到 "等待付款" 的訂單與訂單明細 2?
	// 方法已做
	private static final String SELECT_FIND_ORDER_ALL_AND_STATUS_WAIT_PAY_BY_ORDER_ID = "SELECT o.order_id, o.member_id, o.company_id, cm.company_name, o.order_check_in_date, o.order_check_out_date, o.order_number_of_nights,\n"
			+ "o.order_total_price, o.order_status, o.order_create_datetime, o.order_pay_deadline, o.order_pay_datetime,\n"
			+ "o.order_pay_card_name, o.order_pay_card_number, o.order_pay_card_year, o.order_pay_card_month, o.traveler_name,\n"
			+ "o.traveler_id_number,  o.traveler_email, o.traveler_phone, od.order_detail_id, od.order_id, od.roomtype_id, od.roomtype_name, \n"
			+ "od.order_detail_room_price, od.order_detail_room_quantity\n"
			+ "FROM lazy.order_detail od JOIN  lazy.order o ON o.order_id = od.order_id "
			+ "JOIN lazy.company cm ON o.company_id = cm.company_id " + "WHERE o.order_status = ? AND o.order_id = ?;";

	// ====================================================================================

	// 從訂單編號找到 "已付款" 的訂單與訂單明細 2?
	// 方法已做
	private static final String SELECT_FIND_ORDER_ALL_AND_STATUS_ALREADY_PAY_BY_ORDER_ID = "SELECT o.order_id, o.member_id, o.company_id, cm.company_name, cm.address_county, cm.address_area, cm.address_street, o.order_check_in_date, o.order_check_out_date, o.order_number_of_nights, "
			+ "o.order_total_price, o.order_status, o.order_create_datetime, o.order_pay_deadline, o.order_pay_datetime, "
			+ "o.order_pay_card_name, o.order_pay_card_number, o.order_pay_card_year, o.order_pay_card_month, o.traveler_name, "
			+ "o.traveler_id_number,  o.traveler_email, o.traveler_phone, od.order_detail_id, od.order_id, od.roomtype_id, od.roomtype_name, "
			+ "od.order_detail_room_price, od.order_detail_room_quantity "
			+ "FROM lazy.order_detail od JOIN  lazy.order o ON o.order_id = od.order_id "
			+ "JOIN lazy.company cm ON o.company_id = cm.company_id WHERE o.order_status = ? AND o.order_id = ?;";
			
	
	// ====================================================================================
	
	
	
	// 從會員編號找到所有訂單與訂單明細和廠商資料(裡面沒有廠商的帳號密碼)並用訂單編號從大到小排序 1?
	// 方法已做
	String SELECT_FIND_ORDER_ALL_BY_MEMBER_ID = "SELECT o.order_id, o.member_id, o.company_id, o.order_check_in_date, \n"
			+ "o.order_check_out_date, o.order_number_of_nights, o.order_total_price, o.order_status, o.order_create_datetime, \n"
			+ "o.order_pay_deadline, o.order_pay_datetime, o.traveler_name, o.traveler_id_number, \n"
			+ "o.traveler_email, o.traveler_phone, od.order_detail_id, od.roomtype_id, od.roomtype_name, \n"
			+ "od.roomtype_person, od.order_detail_room_price, od.order_detail_room_quantity, \n"
			+ "cm.company_name, cm.address_county, cm.address_area, cm.address_street, cm.company_img\n"
			+ "FROM lazy.order_detail od JOIN  lazy.order o ON o.order_id = od.order_id\n"
			+ "JOIN lazy.company cm ON o.company_id = cm.company_id WHERE o.member_id = ? ORDER BY o.order_id DESC;";

	// ====================================================================================

	// 從廠商編號找到 "已付款" 的所有訂單與訂單明細資料並用訂單編號從小到大排序 2?
	// 方法已做
	private static final String SELECT_FIND_ORDER_ALL_AND_ALREADY_PAY_BY_COMPANY_ID = "SELECT o.order_id, o.member_id, o.company_id, o.order_check_in_date, \n"
			+ "o.order_check_out_date, o.order_number_of_nights, o.order_total_price, o.order_status, o.order_create_datetime, \n"
			+ "o.order_pay_deadline, o.order_pay_datetime, o.traveler_name, o.traveler_id_number, \n"
			+ "o.traveler_email, o.traveler_phone, od.order_detail_id, od.order_detail_room_price, \n"
			+ "od.roomtype_id, od.roomtype_name, od.roomtype_person, od.order_detail_room_quantity\n"
			+ "FROM lazy.order o JOIN lazy.order_detail od ON o.order_id = od.order_id \n"
			+ "WHERE o.order_status = ? AND o.company_id = ?";

	// ====================================================================================

	// 從會員編號找到會員ID、帳號、姓名、手機號碼 1?
	// 方法已做
	private static final String SELECT_FIND_MEMBER_BY_MEMBER_ID = "SELECT member_id, member_account, member_name, member_phone FROM lazy.member WHERE member_id = ?;";

	// ====================================================================================

	// 從定位找到該飯店的所有資料(之後再做限制筆數功能，俗稱分頁) 1?
	public List<CompanyVO> selectFindCompanyAndRoomTypePriceByPosition(String addressCounty) {

		List<CompanyVO> companyVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_POSITION);) {

			ps.setString(1, addressCounty);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyID(rs.getInt("cm.company_id"));
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				companyVO.setIntroduction(rs.getString("cm.introduction"));
				companyVO.setAddressCounty(rs.getString("cm.address_county"));
				companyVO.setAddressArea(rs.getString("cm.address_area"));
				companyVO.setCompanyImg(rs.getString("cm.company_img"));
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypePrice(rs.getInt("MIN(rt.roomtype_price)"));
				companyVO.setRoomTypeVO(roomTypeVO);
				companyVOs.add(companyVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindCompanyAndRoomTypePriceByPosition: " + e.getMessage());
		}
		return companyVOs;
	}

	// 透過文字搜尋框 關鍵字輸入飯店名稱或飯店縣市或飯店區域 搜尋多個飯店資料 3?
	public List<CompanyVO> SelectFindCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(String keyword) {

		List<CompanyVO> companyVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con
						.prepareStatement(SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_COMPANY_NAME_OR_COUNTY_OR_AREA);) {

			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyID(rs.getInt("cm.company_id"));
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				companyVO.setIntroduction(rs.getString("cm.introduction"));
				companyVO.setAddressCounty(rs.getString("cm.address_county"));
				companyVO.setAddressArea(rs.getString("cm.address_area"));
				companyVO.setCompanyImg(rs.getString("cm.company_img"));
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypePrice(rs.getInt("MIN(rt.roomtype_price)"));
				companyVO.setRoomTypeVO(roomTypeVO);
				companyVOs.add(companyVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea: " + e.getMessage());
		}
		return companyVOs;
	}

	// 從廠商編號 找到 廠商資料 1?
	public CompanyVO selectFindCompanyByCompanyID(Integer companyID) {

		CompanyVO companyVO = new CompanyVO();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_BY_COMPANY_ID);) {

			ps.setInt(1, companyID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				companyVO.setCompanyID(rs.getInt("company_id"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setIntroduction(rs.getString("introduction"));
				companyVO.setAddressCounty(rs.getString("address_county"));
				companyVO.setAddressArea(rs.getString("address_area"));
				companyVO.setAddressStreet(rs.getString("address_street"));
				companyVO.setLatitude(rs.getDouble("latitude"));
				companyVO.setLongitude(rs.getDouble("longitude"));
				companyVO.setCompanyImg(rs.getString("company_img"));
			}
		} catch (SQLException e) {
			System.out.println("SelectFindCompanyByCompanyID :" + e.getMessage());
		}
		return companyVO;
	}

	// 從 廠商編號 找到 廠商名稱 1?
	public CompanyVO selectFindCompanyNameByCompanyID(CompanyVO companyVO) {

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_NAME_BY_COMPANY_ID);) {

			ps.setInt(1, companyVO.getCompanyID());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				companyVO.setCompanyName(rs.getString("company_name"));
			}
		} catch (SQLException e) {
			System.out.println("selectFindCompanyNameByCompanyID: " + e.getMessage());
		}
		return companyVO;
	}

	// 從 廠商編號 找到所有房型資料 1?
	public List<RoomTypeVO> selectFindAllRoomTypeByCompanyID(Integer companyID) {

		List<RoomTypeVO> roomTypeVOs = new ArrayList<>();
		RoomTypeVO roomTypeVO = new RoomTypeVO();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ALL_ROOMTYPE_BY_COMPANY_ID);) {

			ps.setInt(1, companyID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				roomTypeVO.setCompanyID(rs.getInt("company_id"));
				roomTypeVO.setRoomTypeID(rs.getInt("roomtype_id"));
				roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
				roomTypeVO.setRoomTypePerson(rs.getInt("roomtype_person"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("roomtype_quantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomtype_price"));
				roomTypeVOs.add(roomTypeVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindAllRoomTypeByCompanyID: " + e.getMessage());
		}
		return roomTypeVOs;
	}

	// 從 房型編號找到房型資料 1?
	public RoomTypeVO selectFindRoomTypeByRoomTypeID(RoomTypeVO roomTypeVO) {

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ROOMTYPE_BY_ROOMTYPE_ID);) {

			ps.setInt(1, roomTypeVO.getRoomTypeID());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				roomTypeVO.setCompanyID(rs.getInt("company_id"));
				roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
				roomTypeVO.setRoomTypePerson(rs.getInt("roomtype_person"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("roomtype_quantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomtype_price"));
			}
		} catch (SQLException e) {
			System.out.println("SelectFindRoomTypeByRoomTypeID: " + e.getMessage());
		}
		return roomTypeVO;
	}

	// 從 房型編號找到房型名稱 1?
	public RoomTypeVO selectFindRoomTypeNameByRoomTypeID(RoomTypeVO roomTypeVO) {

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ROOMTYPE_NAME_BY_ROOMTYPE_ID);) {

			ps.setInt(1, roomTypeVO.getRoomTypeID());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
			}
		} catch (SQLException e) {
			System.out.println("selectFindRoomTypeNameByRoomTypeID: " + e.getMessage());
		}
		return roomTypeVO;
	}

	// 從房型id找到所有房型照片資料 1?
	public List<RoomTypeImgVO> selectFindAllRoomTypeImgByRoomTypeID(Integer roomTypeID) {

		List<RoomTypeImgVO> roomTypeImgVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_All_ROOMTYPE_IMG_BY_ROOMTYPE_ID);) {

			ps.setInt(1, roomTypeID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
				roomTypeImgVO.setRoomTypeID(rs.getInt("roomtype_id"));
				roomTypeImgVO.setRoomTypeImgID(rs.getInt("roomtype_img_id"));
				roomTypeImgVO.setRoomTypeImg(rs.getString("roomtype_img"));
				roomTypeImgVOs.add(roomTypeImgVO);
			}
		} catch (SQLException e) {
			System.out.println("selectFindAllRoomTypeImgByRoomTypeID: " + e.getMessage());
		}
		return roomTypeImgVOs;
	}

	// 從飯店id找到飯店所有資料(不含帳號密碼統一編號) 1?
	// 方法已做
	public List<CompanyVO> SelectFindCompanyAllByCompanyID(Integer companyID) {

		List<CompanyVO> companyVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_ALL_BY_COMPANY_ID);) {

			ps.setInt(1, companyID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyID(rs.getInt("cm.company_id"));
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				companyVO.setIntroduction(rs.getString("cm.introduction"));
				companyVO.setAddressCounty(rs.getString("cm.address_county"));
				companyVO.setAddressArea(rs.getString("cm.address_area"));
				companyVO.setAddressStreet(rs.getString("cm.address_street"));
				companyVO.setLongitude(rs.getDouble("cm.longitude"));
				companyVO.setLatitude(rs.getDouble("cm.latitude"));
				companyVO.setCompanyImg(rs.getString("cm.company_img"));
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeID(rs.getInt("rt.roomtype_id"));
				roomTypeVO.setCompanyID(rs.getInt("rt.company_id"));
				roomTypeVO.setRoomTypeName(rs.getString("rt.roomtype_name"));
				roomTypeVO.setRoomTypePerson(rs.getInt("rt.roomtype_person"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("rt.roomtype_quantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("rt.roomtype_price"));
				RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
				roomTypeImgVO.setRoomTypeImgID(rs.getInt("rti.roomtype_img_id"));
				roomTypeImgVO.setRoomTypeID(rs.getInt("rti.roomtype_id"));
				roomTypeImgVO.setRoomTypeImg(rs.getString("rti.roomtype_img"));
				roomTypeVO.setRoomTypeImgVO(roomTypeImgVO);
				companyVO.setRoomTypeVO(roomTypeVO);
				companyVOs.add(companyVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindCompanyAllByCompanyID: " + e.getMessage());
		}
		return companyVOs;
	}

	//
	public List<CompanyVO> selectShowSearchKeyWordByCompanyNameOrAddress(CompanyVO companyVO) {

		List<CompanyVO> companyVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_SHOW_SEARCH_KEY_WORD_BY_COMPANY_NAME_OR_ADDRESS);) {
			ps.setString(1, companyVO.getCompanyName());
			ps.setString(2, companyVO.getAddressCounty());
			ps.setString(3, companyVO.getAddressArea());
			ps.setString(4, companyVO.getAddressStreet());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				companyVO = new CompanyVO();
				companyVO.setCompanyID(rs.getInt("company_id"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setAddressCounty(rs.getString("address_county"));
				companyVO.setAddressArea(rs.getString("address_area"));
				companyVO.setAddressStreet(rs.getString("address_street"));
				companyVOs.add(companyVO);
			}
		} catch (SQLException e) {
			System.out.println("selectShowSearchKeyWordByCompanyNameOrAddress: " + e.getMessage());
		}
		return companyVOs;
	}

	// 新的創立訂單與訂單明細方法
	public int createOrderAndOrderDetail(List<OrderVO> orderVOs) {
		int result = -1;
		Connection conn = null;
		PreparedStatement ps = null;

//        String orderColumns[] = {"order_Id"};
//        String orderDetailColumns[] = {"`order_detail_Id`"};
		int order_ID = -1;

		try {

			conn = HikariDataSource.getConnection();
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, orderVOs.get(0).getMemberID());
			ps.setInt(2, orderVOs.get(0).getCompanyID());
			ps.setObject(3, orderVOs.get(0).getOrderCheckInDate());
			ps.setObject(4, orderVOs.get(0).getOrderCheckOutDate());
			ps.setInt(5, orderVOs.get(0).getOrderNumberOfNights());
			ps.setInt(6, orderVOs.get(0).getOrderTotalPrice());
			ps.setString(7, orderVOs.get(0).getTravelerName());
			ps.setString(8, orderVOs.get(0).getTravelerIDNumber());
			ps.setString(9, orderVOs.get(0).getTravelerEmail());
			ps.setString(10, orderVOs.get(0).getTravelerPhone());

			int rowCount = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				order_ID = rs.getInt(1);
				if (order_ID < 0) {
					System.out.println("order insert failed.");
				}
			}
			// 拿到編號就繼續新增明細

			ps = conn.prepareStatement(CREATE_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS);

			for (OrderVO orderVO : orderVOs) {
				ps.setInt(1, order_ID);
				ps.setInt(2, orderVO.getOrderDetailVO().getRoomTypeID());
				ps.setString(3, orderVO.getOrderDetailVO().getRoomTypeName());
				ps.setInt(4, orderVO.getOrderDetailVO().getRoomTypePerson());
				ps.setInt(5, orderVO.getOrderDetailVO().getOrderDetailRoomPrice());
				ps.setByte(6, orderVO.getOrderDetailVO().getOrderDetailRoomQuantity());
				ps.addBatch();
			}

			int[] rowCount2 = ps.executeBatch();
			int sum = 0;
			for (int rc2 : rowCount2) {
				sum += rc2;
			}
			rs = ps.getGeneratedKeys();

			while (rs.next()) {
				int orderDetailID = rs.getInt(1);
				System.out.println("orderID: " + order_ID + " orderDetailID: " + orderDetailID + " inserted.");
			}

			conn.commit();
			conn.setAutoCommit(true);
			result = 1;
		} catch (SQLException se) {
			result = 0;
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					System.out.println("rollback failed. " + e.getMessage());
				}

			}
			System.out.println("createOrderAndOrderDetail failed :" + se.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (result == 0) {
			System.out.println("result: " + result);
			return result;

		} else {
			System.out.println("order_ID: " + order_ID);
			return order_ID;
		}

	}

	// 付款(更改付款狀態為"已付款") 3?
	public int orderPay(Integer orderID) {

		int result = -1;

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_PAY);) {

			ps.setString(1, "已付款");
			ps.setInt(2, orderID);
			ps.setString(3, "等待付款");
			ps.executeUpdate();

			System.out.println("orderID: " + orderID + " update success");
			result = 1;

		} catch (SQLException e) {
			result = 0;
			System.out.println("OrderPay: " + e.getMessage());
			System.out.print("Update the pay with order failed.");

		}
		return result;
	}

	// 付款期限到了(更改付款狀態為"已過付款期限")(要設定排程器) 0?
	public int updateOrderOverTimeForPay() {

		int result = -1;

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_OVER_TIME_FOR_PAY);) {

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				int orderID = rs.getInt(1);
				System.out.println("orderID :" + orderID + " over pay for deadline and update success.");
			}

			result = 1;

		} catch (SQLException e) {
			System.out.println("updateOrderOverTimeForPay: " + e.getMessage());
			System.out.println("Update over time for pay failed.");
			result = 0;
		}
		return result;
	}

	// (廠商使用) 透過廠商編號找到該廠商相關的所有訂單(無訂單明細) 1?
	// 方法已做
	public List<OrderVO> selectFindOrderByCompanyID(Integer companyID) {

		List<OrderVO> orderVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_BY_COMPANY_ID);) {

			ps.setInt(1, companyID);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOrderID(rs.getInt("order_id"));
				orderVO.setMemberID(rs.getInt("member_id"));
				orderVO.setCompanyID(rs.getInt("company_id"));
				orderVO.setOrderCheckInDate(rs.getObject("order_check_in_date", LocalDate.class));
				orderVO.setOrderCheckOutDate(rs.getObject("order_check_out_date", LocalDate.class));
				orderVO.setOrderNumberOfNights(rs.getInt("order_number_of_nights"));
				orderVO.setOrderTotalPrice(rs.getInt("order_total_price"));
				orderVO.setOrderStatus(rs.getString("order_status"));
				orderVO.setOrderCreateDatetime(rs.getObject("order_create_datetime", LocalDateTime.class));
				orderVO.setOrderPayDeadline(rs.getObject("order_pay_deadline", LocalDateTime.class));
				orderVO.setOrderPayDatetime(rs.getObject("order_pay_datetime", LocalDateTime.class));
				orderVO.setTravelerName(rs.getString("traveler_name"));
				orderVO.setTravelerIDNumber(rs.getString("traveler_id_number"));
				orderVO.setTravelerEmail(rs.getString("traveler_email"));
				orderVO.setTravelerPhone(rs.getString("traveler_phone"));
				orderVOs.add(orderVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindOrderByCompanyID: " + e.getMessage());
		}
		return orderVOs;
	}

	// (廠商使用)選取當前訂單，查詢該筆訂單詳細資料 1?
	public List<OrderDetailVO> selectFindOrderDetailByOrderID(Integer orderID) {

		List<OrderDetailVO> orderDetailVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_DETAIL_BY_ORDER_ID);) {

			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderDetailVO orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderDetailID(rs.getInt("order_detail_id"));
				orderDetailVO.setOrderID(rs.getInt("order_id"));
				orderDetailVO.setRoomTypeID(rs.getInt("roomtype_id"));
				orderDetailVO.setRoomTypeName(rs.getString("roomtype_name"));
				orderDetailVO.setRoomTypePerson(rs.getInt("roomtype_person"));
				orderDetailVO.setOrderDetailRoomPrice(rs.getInt("order_detail_room_price"));
				orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("order_detail_room_quantity"));
				orderDetailVOs.add(orderDetailVO);
			}
		} catch (SQLException e) {
			System.out.println("SelectFindOrderDetailByOrderID: " + e.getMessage());
		}
		return orderDetailVOs;
	}

	// 從訂單編號找到 "等待付款" 的訂單與訂單明細 2?
	// 方法已做
	public List<OrderVO> selectFindOrderAllAndStatusWaitPayByOrderID(Integer orderID) {

		List<OrderVO> orderVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_ALL_AND_STATUS_WAIT_PAY_BY_ORDER_ID);) {

			ps.setString(1, "等待付款");
			ps.setInt(2, orderID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOrderID(rs.getInt("o.order_id"));
				orderVO.setMemberID(rs.getInt("o.member_id"));
				orderVO.setCompanyID(rs.getInt("o.company_id"));
				orderVO.setOrderCheckInDate(rs.getObject("o.order_check_in_date", LocalDate.class));
				orderVO.setOrderCheckOutDate(rs.getObject("o.order_check_out_date", LocalDate.class));
				orderVO.setOrderNumberOfNights(rs.getInt("o.order_number_of_nights"));
				orderVO.setOrderTotalPrice(rs.getInt("o.order_total_price"));
				orderVO.setOrderStatus(rs.getString("o.order_status"));
				orderVO.setOrderCreateDatetime(rs.getObject("o.order_create_datetime", LocalDateTime.class));
				orderVO.setOrderPayDeadline(rs.getObject("o.order_pay_deadline", LocalDateTime.class));
				orderVO.setOrderPayDatetime(rs.getObject("o.order_pay_datetime", LocalDateTime.class));
				orderVO.setOrderPayCardName(rs.getString("o.order_pay_card_name"));
				orderVO.setOrderPayCardNumber(rs.getString("o.order_pay_card_number"));
				orderVO.setOrderPayCardYear(rs.getString("o.order_pay_card_year"));
				orderVO.setOrderPayCardMonth(rs.getString("o.order_pay_card_month"));
				orderVO.setTravelerName(rs.getString("o.traveler_name"));
				orderVO.setTravelerIDNumber(rs.getString("o.traveler_id_number"));
				orderVO.setTravelerEmail(rs.getString("o.traveler_email"));
				orderVO.setTravelerPhone(rs.getString("o.traveler_phone"));
				OrderDetailVO orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderDetailID(rs.getInt("od.order_detail_id"));
				orderDetailVO.setRoomTypeName(rs.getString("od.roomtype_name"));
				orderDetailVO.setOrderID(rs.getInt("od.order_id"));
				orderDetailVO.setRoomTypeID(rs.getInt("od.roomtype_id"));
				orderDetailVO.setOrderDetailRoomPrice(rs.getInt("od.order_detail_room_price"));
				orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("od.order_detail_room_quantity"));
				orderVO.setOrderDetailVO(orderDetailVO);
				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				orderVO.setCompanyVO(companyVO);
				orderVOs.add(orderVO);
			}
		} catch (SQLException e) {
			System.out.println("selectFindOrderAllByOrderID: " + e.getMessage());
		}
		return orderVOs;
	}
	
	
	// 從訂單編號找到 "已付款" 的訂單與訂單明細 2?
	public List<OrderVO> selectFindOrderAllAndStatusAlreadyPayByOrderID(Integer orderID) {

		List<OrderVO> orderVOs = new ArrayList<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_ALL_AND_STATUS_ALREADY_PAY_BY_ORDER_ID);) {

			ps.setString(1, "已付款");
			ps.setInt(2, orderID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOrderID(rs.getInt("o.order_id"));
				orderVO.setMemberID(rs.getInt("o.member_id"));
				orderVO.setCompanyID(rs.getInt("o.company_id"));
				orderVO.setOrderCheckInDate(rs.getObject("o.order_check_in_date", LocalDate.class));
				orderVO.setOrderCheckOutDate(rs.getObject("o.order_check_out_date", LocalDate.class));
				orderVO.setOrderNumberOfNights(rs.getInt("o.order_number_of_nights"));
				orderVO.setOrderTotalPrice(rs.getInt("o.order_total_price"));
				orderVO.setOrderStatus(rs.getString("o.order_status"));
				orderVO.setOrderCreateDatetime(rs.getObject("o.order_create_datetime", LocalDateTime.class));
				orderVO.setOrderPayDeadline(rs.getObject("o.order_pay_deadline", LocalDateTime.class));
				orderVO.setOrderPayDatetime(rs.getObject("o.order_pay_datetime", LocalDateTime.class));
				orderVO.setOrderPayCardName(rs.getString("o.order_pay_card_name"));
				orderVO.setOrderPayCardNumber(rs.getString("o.order_pay_card_number"));
				orderVO.setOrderPayCardYear(rs.getString("o.order_pay_card_year"));
				orderVO.setOrderPayCardMonth(rs.getString("o.order_pay_card_month"));
				orderVO.setTravelerName(rs.getString("o.traveler_name"));
				orderVO.setTravelerIDNumber(rs.getString("o.traveler_id_number"));
				orderVO.setTravelerEmail(rs.getString("o.traveler_email"));
				orderVO.setTravelerPhone(rs.getString("o.traveler_phone"));
				OrderDetailVO orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderDetailID(rs.getInt("od.order_detail_id"));
				orderDetailVO.setRoomTypeName(rs.getString("od.roomtype_name"));
				orderDetailVO.setOrderID(rs.getInt("od.order_id"));
				orderDetailVO.setRoomTypeID(rs.getInt("od.roomtype_id"));
				orderDetailVO.setOrderDetailRoomPrice(rs.getInt("od.order_detail_room_price"));
				orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("od.order_detail_room_quantity"));
				orderVO.setOrderDetailVO(orderDetailVO);
				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				companyVO.setAddressCounty(rs.getString("cm.address_county"));
				companyVO.setAddressArea(rs.getString("cm.address_area"));
				companyVO.setAddressStreet(rs.getString("cm.address_street"));
				orderVO.setCompanyVO(companyVO);
				
				orderVOs.add(orderVO);
			}
		} catch (SQLException e) {
			System.out.println("selectFindOrderAllAndStatusAlreadyPayByOrderID: " + e.getMessage());
		}
		return orderVOs;
	}
	
	
	
	
	
	

	// 新方法
	// 從會員編號找到所有訂單與訂單明細和廠商資料(裡面沒有廠商的帳號密碼)並用訂單編號從小到大排序 1?
	public Map<Integer, OrderVO> selectFindOrderAllByMemberID(Integer memberID) {

		Map<Integer, OrderVO> orderMap = new HashMap<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_ALL_BY_MEMBER_ID);) {
			ps.setInt(1, memberID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				int orderID = rs.getInt("o.order_id");
				orderVO.setOrderID(orderID);
				orderVO.setMemberID(rs.getInt("o.member_id"));
				orderVO.setCompanyID(rs.getInt("o.company_id"));
				orderVO.setOrderCheckInDate(rs.getObject("o.order_check_in_date", LocalDate.class));
				orderVO.setOrderCheckOutDate(rs.getObject("o.order_check_out_date", LocalDate.class));
				orderVO.setOrderNumberOfNights(rs.getInt("o.order_number_of_nights"));
				orderVO.setOrderTotalPrice(rs.getInt("o.order_total_price"));
				orderVO.setOrderStatus(rs.getString("o.order_status"));
				orderVO.setOrderCreateDatetime(rs.getObject("o.order_create_datetime", LocalDateTime.class));
				orderVO.setOrderPayDeadline(rs.getObject("o.order_pay_deadline", LocalDateTime.class));
				orderVO.setOrderPayDatetime(rs.getObject("o.order_pay_datetime", LocalDateTime.class));
				orderVO.setTravelerName(rs.getString("o.traveler_name"));
				orderVO.setTravelerIDNumber(rs.getString("o.traveler_id_number"));
				orderVO.setTravelerEmail(rs.getString("o.traveler_email"));
				orderVO.setTravelerPhone(rs.getString("o.traveler_phone"));

				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyName(rs.getString("cm.company_name"));
				companyVO.setAddressCounty(rs.getString("cm.address_county"));
				companyVO.setAddressArea(rs.getString("cm.address_area"));
				companyVO.setAddressStreet(rs.getString("cm.address_street"));
				companyVO.setCompanyImg(rs.getString("cm.company_img"));

				orderVO.setCompanyVO(companyVO);

				OrderDetailVO orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderDetailID(rs.getInt("od.order_detail_id"));
				orderDetailVO.setRoomTypeID(rs.getInt("od.roomtype_id"));
				orderDetailVO.setRoomTypeName(rs.getString("od.roomtype_name"));
				orderDetailVO.setRoomTypePerson(rs.getInt("od.roomtype_person"));
				orderDetailVO.setOrderDetailRoomPrice(rs.getInt("od.order_detail_room_price"));
				orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("od.order_detail_room_quantity"));

				if (orderMap.containsKey(orderID)) {
					orderMap.get(orderID).getOrderDetailVOList().add(orderDetailVO);
				} else {
					orderMap.put(orderID, orderVO);
//                    List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
//                    orderDetailVOList.add(orderDetailVO);
//                    orderVO.getOrderDetailVOList().addAll(orderDetailVOList);
					orderMap.get(orderID).setOrderDetailVOList(new ArrayList<>());
					orderMap.get(orderID).getOrderDetailVOList().add(orderDetailVO);
				}

			}

		} catch (SQLException e) {
			System.out.println("測試: " + e.getMessage());
		}
		return orderMap;
	}

	// 從廠商編號找到 "已付款" 的所有訂單與訂單明細資料並用訂單編號從小到大排序 2?
	public Map<Integer, OrderVO> selectFindOrderAllAndAlreadyPayByCompanyID(Integer companyID) {

		Map<Integer, OrderVO> orderAllMap = new HashMap<>();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_ALL_AND_ALREADY_PAY_BY_COMPANY_ID);) {
			ps.setString(1, "已付款");
			ps.setInt(2, companyID);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				int orderID = rs.getInt("o.order_id");
				orderVO.setOrderID(orderID);
				orderVO.setMemberID(rs.getInt("o.member_id"));
				orderVO.setCompanyID(rs.getInt("o.company_id"));
				orderVO.setOrderCheckInDate(rs.getObject("o.order_check_in_date", LocalDate.class));
				orderVO.setOrderCheckOutDate(rs.getObject("o.order_check_out_date", LocalDate.class));
				orderVO.setOrderNumberOfNights(rs.getInt("o.order_number_of_nights"));
				orderVO.setOrderTotalPrice(rs.getInt("o.order_total_price"));
				orderVO.setOrderStatus(rs.getString("o.order_status"));
				orderVO.setOrderCreateDatetime(rs.getObject("o.order_create_datetime", LocalDateTime.class));
				orderVO.setOrderPayDeadline(rs.getObject("o.order_pay_deadline", LocalDateTime.class));
				orderVO.setOrderPayDatetime(rs.getObject("o.order_pay_datetime", LocalDateTime.class));
				orderVO.setTravelerName(rs.getString("o.traveler_name"));
				orderVO.setTravelerIDNumber(rs.getString("o.traveler_id_number"));
				orderVO.setTravelerEmail(rs.getString("o.traveler_email"));
				orderVO.setTravelerPhone(rs.getString("o.traveler_phone"));

				OrderDetailVO orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderDetailID(rs.getInt("od.order_detail_id"));
				orderDetailVO.setRoomTypeID(rs.getInt("od.roomtype_id"));
				orderDetailVO.setRoomTypeName(rs.getString("od.roomtype_name"));
				orderDetailVO.setRoomTypePerson(rs.getInt("od.roomtype_person"));
				orderDetailVO.setOrderDetailRoomPrice(rs.getInt("od.order_detail_room_price"));
				orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("od.order_detail_room_quantity"));

				if (orderAllMap.containsKey(orderID)) {
					orderAllMap.get(orderID).getOrderDetailVOList().add(orderDetailVO);
				} else {
					orderAllMap.put(orderID, orderVO);
					orderAllMap.get(orderID).setOrderDetailVOList(new ArrayList<>());
					orderAllMap.get(orderID).getOrderDetailVOList().add(orderDetailVO);
				}

			}
		} catch (SQLException e) {
			System.out.println("SelectFindOrderAllByCompanyID: " + e.getMessage());
		}
		return orderAllMap;
	}

	
	
	// 從會員編號找到會員ID、帳號、姓名、手機號碼 1?
	public Member selectFindMemberByMemberID(Integer id) {

		Member member = new Member();

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_FIND_MEMBER_BY_MEMBER_ID);) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				member.setId(rs.getInt("member_id"));
				member.setAccount(rs.getString("member_account"));
				member.setName(rs.getString("member_name"));
				member.setPhone(rs.getString("member_phone"));
			}
		} catch (SQLException e) {
			System.out.println("selectFindMemberByMemberID: " + e.getMessage());
		}
		return member;
	}

}
