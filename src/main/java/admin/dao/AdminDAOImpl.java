package admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import admin.model.Admin;
import common.HikariDataSource;
import member.model.Member;

public class AdminDAOImpl implements AdminDAO{

	private static final String INSERT = "insert into admin(admin_account, admin_password, admin_name, admin_status) values(?, ?, ?, '1')";
	@Override
	public int insert(Admin admin) {
		int generatedKey = -1;
		try(
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, admin.getAccount());
			pstmt.setString(2, admin.getPassword());
			pstmt.setString(3, admin.getName());
			pstmt.executeUpdate();
			
			try (ResultSet rs = pstmt.getGeneratedKeys()){
				if(rs.next()) {
					generatedKey = rs.getInt(1);
					System.out.println("GeneratedKey: " + generatedKey);
				}
			}
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return generatedKey;
	}

	private static final String UPDATE = "update admin set admin_account=?, admin_password=?, admin_name=?, admin_status=? where admin_id=?";
	@Override
	public int updateById(Admin admin) {
		try (	Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE);
		) {
				pstmt.setString(1, admin.getAccount());
				pstmt.setString(2, admin.getPassword());
				pstmt.setString(3, admin.getName());
				pstmt.setString(4, admin.getStatus());
				pstmt.setInt(5, admin.getId());
				return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	private static final String STOP_ACCESS = "update admin set admin_status=? where admin_id=?";
	@Override
	public int stopAccess(Admin admin) {
		try (	Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(STOP_ACCESS);
		) {
				pstmt.setString(1, admin.getStatus());
				pstmt.setInt(2, admin.getId());
				return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static final String LOGIN = "select * from admin where admin_account = ? and admin_password = ?";
	@Override
	public Admin login(Admin admin) {
		try (
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(LOGIN);
			){
				pstmt.setString(1, admin.getAccount());
				pstmt.setString(2, admin.getPassword());
				try (ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						admin.setId(rs.getInt("admin_id"));
						admin.setAccount(rs.getString("admin_account"));
						admin.setPassword(rs.getString("admin_password"));
						admin.setName(rs.getString("admin_name"));
						admin.setStatus(rs.getString("admin_status"));
						return admin;
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public Admin selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String SELECT_ONE_BY_ACCOUNT = "select * from admin where admin_account = ?";
	@Override
	public Admin selectByAccount(String account) {
		try (
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT_ONE_BY_ACCOUNT);
			){
				pstmt.setString(1, account);
				try (ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						Admin admin = new Admin();
						admin.setId(rs.getInt("admin_id"));
						admin.setAccount(rs.getString("admin_account"));
						admin.setPassword(rs.getString("admin_password"));
						admin.setName(rs.getString("admin_name"));
						admin.setStatus(rs.getString("admin_status"));
						return admin;
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public Admin selectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	final static String GET_ALL_STMT = "select admin_id, admin_account, admin_password, admin_name, admin_status, s.status_des "
			+ "from admin a "
			+ "join status s "
			+ "on a.admin_status = s.status_code "
			+ "order by a.admin_id;";
	@Override
	public List<Admin> getAll() {
		List<Admin> resultList = new ArrayList<Admin>();
		Admin admin;
		try( 	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
				ResultSet rs = pstmt.executeQuery();){
			while(rs.next()) {
				admin = new Admin();

				admin.setId(rs.getInt("admin_id"));
				admin.setAccount(rs.getString("admin_account"));
				admin.setPassword(rs.getString("admin_password"));
				admin.setName(rs.getString("admin_name"));
				admin.setStatus(rs.getString("admin_status"));
				admin.setStatus_desciption(rs.getString("status_des"));
				resultList.add(admin);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	final static String UPDATE_MEMBER = "update member set member_account=?, member_password=?, member_username=?, member_gender=?, member_birth=?, member_accessnum=? where member_id=?";
	@Override
	public int updateMemberById(Member member) {
		final String acc = member.getAccount();
		final String ps = member.getPassword();
		final String un = member.getUsername();
		final String gender = member.getGender();
		final Date birth = member.getBirthday();
		final String status = member.getAccessnum();
		
		StringBuilder sql = new StringBuilder("update member set ");
		if(acc != null && !acc.isEmpty()) {
			sql.append("member_account=?");
		}
		if(ps != null && !ps.isEmpty() && !ps.equals("")) {
			sql.append(", member_password=?");
		}
		
		if (un != null && !un.isEmpty()) {
			sql.append(", member_username=?");
		}
		if (gender != null && !gender.isEmpty()) {
			sql.append(", member_gender=?");
		}
		if (birth != null) {
			sql.append(", member_birth=?");
		}
		if (status != null && !status.isEmpty()) {
			sql.append(", member_accessnum=?");
		}
		
		sql.append(" where member_id = ?");
		try (	Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
		) {
				int nxtSeq = 1;
				
				if(acc != null && !acc.isEmpty()) {
					pstmt.setString(nxtSeq++, member.getAccount());
				}
				if(ps != null && !ps.isEmpty() && !ps.equals("")) {
					pstmt.setString(nxtSeq++, member.getPassword());
				}
				if (un != null && !un.isEmpty()) {
					pstmt.setString(nxtSeq++, member.getUsername());
				}
				if (gender != null && !gender.isEmpty()) {
					pstmt.setString(nxtSeq++, member.getGender());
				}
				if (birth != null) {
					pstmt.setDate(nxtSeq++, member.getBirthday());
				}
				if (status != null && !status.isEmpty()) {
					pstmt.setString(nxtSeq++, member.getAccessnum());
				}
				pstmt.setInt(nxtSeq, member.getId());
				System.out.println(sql);
				return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
