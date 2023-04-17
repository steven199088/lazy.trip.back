package company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.HikariDataSource;
import company.model.EquipmentVO;

public class EquipmentDAO implements EquipmentDAO_interface {


	private static final String INSERT_STMT = 
			"INSERT INTO equipment (equipment_name,equipment_desc) VALUES (?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT equipment_id as equipmentID,equipment_name as equipmentName,equipment_desc as equipmentDesc FROM equipment order by equipment_id";
		private static final String GET_ONE_STMT = 
			"SELECT equipment_id as equipmentID,equipment_name as equipmentName,equipment_desc as equipmentDesc FROM equipment where equipment_id = ?";
		private static final String DELETE = 
			"DELETE FROM equipment where equipment_id = ?";
		private static final String UPDATE = 
			"UPDATE equipment set equipment_name=?, equipment_desc=? where equipment_id = ?";
	@Override
	public void insert(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			 con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, equipmentVO.getEquipmentName());
			pstmt.setString(2, equipmentVO.getEquipmentDesc());
			
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
	public void update(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, equipmentVO.getEquipmentName());
			pstmt.setString(2, equipmentVO.getEquipmentDesc());
			pstmt.setInt(3, equipmentVO.getEquipmentID());
			
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
	public void delete(Integer equipmentID) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, equipmentID);

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
	public EquipmentVO findByPrimaryKey(Integer equipmentID) {

		EquipmentVO equipmentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = HikariDataSource.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, equipmentID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				equipmentVO = new EquipmentVO();
				equipmentVO.setEquipmentID(rs.getInt("equipmentID"));
				equipmentVO.setEquipmentName(rs.getString("equipmentName"));
				equipmentVO.setEquipmentDesc(rs.getString("equipmentDesc"));
				
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
		return equipmentVO;
	}

	@Override
	public List<EquipmentVO> getAll() {
		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO equipmentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = HikariDataSource.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// equipmentVO 也稱為 Domain objects
				equipmentVO = new EquipmentVO();
	equipmentVO.setEquipmentID(rs.getInt("equipmentID"));
	equipmentVO.setEquipmentName(rs.getString("equipmentName"));
	equipmentVO.setEquipmentDesc(rs.getString("equipmentDesc"));
				list.add(equipmentVO); // Store the row in the list
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