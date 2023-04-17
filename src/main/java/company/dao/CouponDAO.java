package company.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import company.model.CouponVO;

//public class CouponDAO implements CouponDAO_interface {
//		private static final String INSERT_STMT = "INSERT INTO coupon (coupon_username,coupon_password,taxid,coupon_name,introduction,address_county,address_area,address_street,latitude,longitude,coupon_img) VALUES ( ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)";
//		private static final String GET_ALL_STMT = "SELECT coupon_id as couponID,coupon_username as couponUserName,coupon_password as couponPassword, taxid as taxID,coupon_name as couponName,introduction, address_county as addressCounty,address_area as addressArea, address_street as addressStreet, latitude, longitude,coupon_img as couponImg FROM coupon order by coupon_id";
//		private static final String GET_ONE_STMT = "SELECT coupon_id as couponID,coupon_username as couponUserName,coupon_password as couponPassword, taxid as taxID,coupon_name as couponName,introduction, address_county as addressCounty,address_area as addressArea, address_street as addressStreet, latitude, longitude,coupon_img as couponImg FROM coupon where coupon_id = ?";
//		private static final String DELETE = "DELETE FROM coupon where coupon_id = ?";
//		private static final String UPDATE = "UPDATE coupon set coupon_username=?, coupon_password=?,taxid=?,coupon_name=?,introduction=?,address_county=?,address_area=?,address_street=?,latitude=?,longitude=?,coupon_img=? where coupon_id = ?";
//		private static final String GET_ALL_BY_COMPANYID = """
//				SELECT SELECT coupon_id as couponID,coupon_username as couponUserName,coupon_password as couponPassword,
//				 taxid as taxID,coupon_name as couponName,introduction, address_county as addressCounty,
//				 address_area as addressArea, address_street as addressStreet, latitude, longitude,coupon_img as couponImg 
//				 FROM coupon where coupon_id = ? order by coupon_id";
//				""";
//		
//		@Override
//		public void insert(CouponVO couponVO) {
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(INSERT_STMT);
//
//				pstmt.setString(1, couponVO.getCouponUserName());
//				pstmt.setString(2, couponVO.getCouponPassword());
//				pstmt.setString(3, couponVO.getTaxID());
//				pstmt.setString(4, couponVO.getCouponName());
//				pstmt.setString(5, couponVO.getIntroduction());
//				pstmt.setString(6, couponVO.getAddressCounty());
//				pstmt.setString(7, couponVO.getAddressArea());
//				pstmt.setString(8, couponVO.getAddressStreet());
//				pstmt.setDouble(9, couponVO.getLatitude()); // 緯度
//				pstmt.setDouble(10, couponVO.getLongitude()); // 經度
//				pstmt.setString(11, couponVO.getCouponImg());
//
//				pstmt.executeUpdate();
//
//				// Handle any SQL errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//
//		}
//
//		@Override
//		public void update(CouponVO couponVO) {
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(UPDATE);
//
//				pstmt.setInt(1, couponVO.getCouponID());
//				pstmt.setString(2, couponVO.getCouponUserName());
//				pstmt.setString(3, couponVO.getCouponPassword());
//				pstmt.setString(4, couponVO.getTaxID());
//				pstmt.setString(5, couponVO.getCouponName());
//				pstmt.setString(6, couponVO.getIntroduction());
//				pstmt.setString(7, couponVO.getAddressCounty());
//				pstmt.setString(8, couponVO.getAddressArea());
//				pstmt.setString(9, couponVO.getAddressStreet());
//				pstmt.setDouble(10, couponVO.getLatitude()); // 緯度
//				pstmt.setDouble(11, couponVO.getLongitude()); // 經度
//				pstmt.setString(12, couponVO.getCouponImg());
//
//				pstmt.executeUpdate();
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//
//		}
//
//		@Override
//		public void delete(Integer couponID) {
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(DELETE);
//
//				pstmt.setInt(1, couponID);
//
//				pstmt.executeUpdate();
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//
//		}
//
//		@Override
//		public CouponVO findByPrimaryKey(Integer couponID) {
//
//			CouponVO couponVO = new CouponVO();
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(GET_ONE_STMT);
//
//				pstmt.setInt(1, couponID);
//
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					// couponVO 也稱為 Domain objects
//
//					couponVO.setCouponID(rs.getInt("couponID"));
//					couponVO.setCouponUserName(rs.getString("couponUserName"));
//					couponVO.setCouponPassword(rs.getString("couponPassword"));
//					couponVO.setTaxID(rs.getString("taxID"));
//					couponVO.setCouponName(rs.getString("couponName"));
//					couponVO.setIntroduction(rs.getString("introduction"));
//					couponVO.setAddressCounty(rs.getString("addressCounty"));
//					couponVO.setAddressArea(rs.getString("addressArea"));
//					couponVO.setAddressStreet(rs.getString("addressStreet"));
//					couponVO.setLatitude(rs.getDouble("latitude"));
//					couponVO.setLongitude(rs.getDouble("longitude"));
//					couponVO.setCouponImg(rs.getString("couponImg"));
//
//				}
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return couponVO;
//		}
//
//		@Override
//		public List<CouponVO> getAll() {
//			List<CouponVO> list = new ArrayList<CouponVO>();
//			
//			CouponVO couponVO = null;
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(GET_ALL_STMT);
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					
//
//					couponVO.setCouponID(rs.getInt("couponID"));
//					couponVO.setCouponUserName(rs.getString("couponUserName"));
//					couponVO.setCouponPassword(rs.getString("couponPassword"));
//					couponVO.setTaxID(rs.getString("taxID"));
//					couponVO.setCouponName(rs.getString("couponName"));
//					couponVO.setIntroduction(rs.getString("introduction"));
//					couponVO.setAddressCounty(rs.getString("addressCounty"));
//					couponVO.setAddressArea(rs.getString("addressArea"));
//					couponVO.setAddressStreet(rs.getString("addressStreet"));
//					couponVO.setLatitude(rs.getDouble("latitude"));
//					couponVO.setLongitude(rs.getDouble("longitude"));
//					couponVO.setCouponImg(rs.getString("couponImg"));
//
//					list.add(couponVO); // Store the row in the list
//				}
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		}
//
//		
//		@Override
//		public List<CouponVO> getAllByCouponID(Integer couponID) {
//			List<CouponVO> list = new ArrayList<CouponVO>();
////			CouponVO couponVO = null;
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//
//				con = HikariDataSource.getConnection();
//				pstmt = con.prepareStatement(GET_ALL_BY_COMPANYID);
//				pstmt.setInt(1, couponID);
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					
//
//					CouponVO couponVO = new CouponVO();
//					couponVO.setCouponID(rs.getInt("couponID"));
//					couponVO.setCouponUserName(rs.getString("couponUserName"));
//					couponVO.setCouponPassword(rs.getString("couponPassword"));
//					couponVO.setTaxID(rs.getString("taxID"));
//					couponVO.setCouponName(rs.getString("couponName"));
//					couponVO.setIntroduction(rs.getString("introduction"));
//					couponVO.setAddressCounty(rs.getString("addressCounty"));
//					couponVO.setAddressArea(rs.getString("addressArea"));
//					couponVO.setAddressStreet(rs.getString("addressStreet"));
//					couponVO.setLatitude(rs.getDouble("latitude"));
//					couponVO.setLongitude(rs.getDouble("longitude"));
//					couponVO.setCouponImg(rs.getString("couponImg"));
//					list.add(couponVO); // Store the row in the list
//				}
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		}
//		
//	
//}
	
	

