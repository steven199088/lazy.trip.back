package company.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.HikariDataSource;
import company.model.RoomTypeImgVO;

public class RoomTypeImgDAO implements RoomTypeImgDAO_interface {


	private static final String INSERT_STMT = 
			"INSERT INTO roomtype_img (roomtype_id,roomtype_img) VALUES (?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT roomtype_img_id as roomTypeImgID,roomtype_id as roomTypeID,roomtype_img as roomTypeImg FROM roomtype_img order by roomtype_id";
		private static final String GET_ONE_STMT = 
			"SELECT roomtype_img_id as roomTypeImgID,roomtype_id as roomTypeID,roomtype_img as roomTypeImg FROM roomtype_img where roomtype_id = ?";
		private static final String DELETE = 
			"DELETE FROM roomtype_img where roomtype_id = ?";
		private static final String UPDATE = 
			"UPDATE roomtype_img set roomtype_img=? where roomtype_id = ?";
		private static final String GET_ALL_BY_ROOMTYPEIMGID = """
				SELECT roomtype_img_id as roomTypeImgID,roomtype_id as roomTypeID,roomtype_img as roomTypeImg 
				FROM roomtype_img  where roomtype_img_id = ? order by roomtype_id;
				""";
		
	@Override
	public void insert(RoomTypeImgVO roomTypeImgVO) {

			
		try( Connection con = HikariDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(INSERT_STMT);) 
		{
		
			pstmt.setInt(1, roomTypeImgVO.getRoomTypeID());
			pstmt.setBytes(2, roomTypeImgVO.getRoomTypeImg());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} 

	}

	@Override
	public void update(RoomTypeImgVO roomTypeImgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setInt(2, roomTypeImgVO.getRoomTypeID());
			pstmt.setString(1,Base64.getEncoder().encodeToString(roomTypeImgVO.getRoomTypeImg()));
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer roomTypeImgID) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, roomTypeImgID);

			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public RoomTypeImgVO findByRoomTypeID(Integer roomtTypeID) {

		RoomTypeImgVO roomTypeImgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = HikariDataSource.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, roomtTypeID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				roomTypeImgVO = new RoomTypeImgVO();
				roomTypeImgVO.setRoomTypeImgID(rs.getInt("roomTypeImgID"));
				roomTypeImgVO.setRoomTypeID(rs.getInt("roomTypeID"));
				roomTypeImgVO.setRoomTypeImg(rs.getBytes("roomTypeImg"));
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return roomTypeImgVO;
	}

	@Override
	public List<RoomTypeImgVO> getAllByRoomTypeImgID(Integer roomTypeImgID) {
		List<RoomTypeImgVO> list = new ArrayList<RoomTypeImgVO>();
		RoomTypeImgVO roomTypeImgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = HikariDataSource.getConnection();

			pstmt = con.prepareStatement(GET_ALL_BY_ROOMTYPEIMGID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				roomTypeImgVO = new RoomTypeImgVO();
				roomTypeImgVO.setRoomTypeImgID(rs.getInt("roomTypeImgID"));
				roomTypeImgVO.setRoomTypeID(rs.getInt("roomTypeID"));
				roomTypeImgVO.setRoomTypeImg(rs.getBytes("roomTypeImg"));
				list.add(roomTypeImgVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
}