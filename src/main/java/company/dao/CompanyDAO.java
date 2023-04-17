package company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import common.HikariDataSource;
import company.model.CompanyVO;
import company.model.RoomTypeVO;

public class CompanyDAO implements CompanyDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO company (company_username,company_password,taxid,company_name,introduction,address_county,address_area,address_street,latitude,longitude,company_img) VALUES ( ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT company_id as companyID,company_username as companyUserName,company_password as companyPassword, taxid as taxID,company_name as companyName,introduction, address_county as addressCounty,address_area as addressArea, address_street as addressStreet, latitude, longitude,company_img as companyImg FROM company order by company_id";
	private static final String GET_ONE_STMT = "SELECT company_id as companyID,company_username as companyUserName,company_password as companyPassword, taxid as taxID,company_name as companyName,introduction, address_county as addressCounty,address_area as addressArea, address_street as addressStreet, latitude, longitude,company_img as companyImg FROM company where company_id = ?";
	private static final String DELETE = "DELETE FROM company where company_id = ?";
	private static final String UPDATE = "UPDATE company set company_username=?, company_password=?,taxid=?,company_name=?,introduction=?,address_county=?,address_area=?,address_street=?,latitude=?,longitude=?,company_img=? where company_id = ?";
	private static final String GET_ALL_BY_COMPANYID = """
			 SELECT company_id as companyID,company_username as companyUserName,company_password as companyPassword,
			 taxid as taxID,company_name as companyName,introduction, address_county as addressCounty,
			 address_area as addressArea, address_street as addressStreet, latitude, longitude,company_img as companyImg 
			 FROM company where company_id = ? order by company_id";
			""";
	private static final String LOGIN = "select * from company where company_username =? and company_password=?";
	@Override
	public void insert(CompanyVO companyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, companyVO.getCompanyUserName());
			pstmt.setString(2, companyVO.getCompanyPassword());
			pstmt.setString(3, companyVO.getTaxID());
			pstmt.setString(4, companyVO.getCompanyName());
			pstmt.setString(5, companyVO.getIntroduction());
			pstmt.setString(6, companyVO.getAddressCounty());
			pstmt.setString(7, companyVO.getAddressArea());
			pstmt.setString(8, companyVO.getAddressStreet());
			pstmt.setDouble(9, companyVO.getLatitude()); // 緯度
			pstmt.setDouble(10, companyVO.getLongitude()); // 經度
			pstmt.setString(11, companyVO.getCompanyImg());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(CompanyVO companyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, companyVO.getCompanyID());
			pstmt.setString(2, companyVO.getCompanyUserName());
			pstmt.setString(3, companyVO.getCompanyPassword());
			pstmt.setString(4, companyVO.getTaxID());
			pstmt.setString(5, companyVO.getCompanyName());
			pstmt.setString(6, companyVO.getIntroduction());
			pstmt.setString(7, companyVO.getAddressCounty());
			pstmt.setString(8, companyVO.getAddressArea());
			pstmt.setString(9, companyVO.getAddressStreet());
			pstmt.setDouble(10, companyVO.getLatitude()); // 緯度
			pstmt.setDouble(11, companyVO.getLongitude()); // 經度
			pstmt.setString(12, companyVO.getCompanyImg());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer companyID) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, companyID);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public CompanyVO findByPrimaryKey(Integer companyID) {

		CompanyVO companyVO = new CompanyVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, companyID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// companyVO 也稱為 Domain objects

				companyVO.setCompanyID(rs.getInt("companyID"));
				companyVO.setCompanyUserName(rs.getString("companyUserName"));
				companyVO.setCompanyPassword(rs.getString("companyPassword"));
				companyVO.setTaxID(rs.getString("taxID"));
				companyVO.setCompanyName(rs.getString("companyName"));
				companyVO.setIntroduction(rs.getString("introduction"));
				companyVO.setAddressCounty(rs.getString("addressCounty"));
				companyVO.setAddressArea(rs.getString("addressArea"));
				companyVO.setAddressStreet(rs.getString("addressStreet"));
				companyVO.setLatitude(rs.getDouble("latitude"));
				companyVO.setLongitude(rs.getDouble("longitude"));
				companyVO.setCompanyImg(rs.getString("companyImg"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return companyVO;
	}

	@Override
	public List<CompanyVO> getAll() {
		List<CompanyVO> list = new ArrayList<CompanyVO>();
		
		CompanyVO companyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				

				companyVO.setCompanyID(rs.getInt("companyID"));
				companyVO.setCompanyUserName(rs.getString("companyUserName"));
				companyVO.setCompanyPassword(rs.getString("companyPassword"));
				companyVO.setTaxID(rs.getString("taxID"));
				companyVO.setCompanyName(rs.getString("companyName"));
				companyVO.setIntroduction(rs.getString("introduction"));
				companyVO.setAddressCounty(rs.getString("addressCounty"));
				companyVO.setAddressArea(rs.getString("addressArea"));
				companyVO.setAddressStreet(rs.getString("addressStreet"));
				companyVO.setLatitude(rs.getDouble("latitude"));
				companyVO.setLongitude(rs.getDouble("longitude"));
				companyVO.setCompanyImg(rs.getString("companyImg"));

				list.add(companyVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	
	@Override
	public List<CompanyVO> getAllByCompanyID(Integer companyID) {
		List<CompanyVO> list = new ArrayList<CompanyVO>();
//		CompanyVO companyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_COMPANYID);
			pstmt.setInt(1, companyID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				

				CompanyVO companyVO = new CompanyVO();
				companyVO.setCompanyID(rs.getInt("companyID"));
				companyVO.setCompanyUserName(rs.getString("companyUserName"));
				companyVO.setCompanyPassword(rs.getString("companyPassword"));
				companyVO.setTaxID(rs.getString("taxID"));
				companyVO.setCompanyName(rs.getString("companyName"));
				companyVO.setIntroduction(rs.getString("introduction"));
				companyVO.setAddressCounty(rs.getString("addressCounty"));
				companyVO.setAddressArea(rs.getString("addressArea"));
				companyVO.setAddressStreet(rs.getString("addressStreet"));
				companyVO.setLatitude(rs.getDouble("latitude"));
				companyVO.setLongitude(rs.getDouble("longitude"));
				companyVO.setCompanyImg(Base64.getEncoder().encodeToString(rs.getBytes("companyImg")));
//				companyVO.setCompanyImg(rs.getString("companyImg"));
				list.add(companyVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		@Override
	public CompanyVO login(CompanyVO companyVO) {
		try (
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(LOGIN);
			){
				pstmt.setString(1, companyVO.getCompanyUserName());
				pstmt.setString(2, companyVO.getCompanyPassword());
				try (ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						companyVO.setCompanyID(rs.getInt("company_id"));
						companyVO.setCompanyUserName(rs.getString("company_username"));
						companyVO.setCompanyPassword(rs.getString("company_password"));
						companyVO.setTaxID(rs.getString("taxid"));
						companyVO.setCompanyName(rs.getString("company_name"));
						companyVO.setIntroduction(rs.getString("introduction"));
						companyVO.setAddressCounty(rs.getString("address_county"));
						companyVO.setAddressArea(rs.getString("address_area"));
						companyVO.setAddressStreet(rs.getString("address_street"));
						companyVO.setLatitude(rs.getDouble("latitude"));
						companyVO.setLongitude(rs.getDouble("longitude"));
						companyVO.setCompanyImg(rs.getString("company_img"));
						return companyVO;
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
}
