package member.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.HikariDataSource;
import member.model.Member;


public class MemberDAOImpl implements MemberDAO{
//	private DataSource ds;
//
//	public MemberDAOImpl() throws NamingException {
//		ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/lazy");
//	}
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String INSERT = "insert into member(member_account, member_password, member_username, member_gender, member_birth, member_accessnum) values(?, ?, ?, ?, ?, '1')";
	@Override
	public int insert(Member member) {
		int generatedKey = -1;
		try(
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, member.getAccount());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getUsername());
			pstmt.setString(4, member.getGender());
			pstmt.setDate(5, member.getBirthday());
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

	@Override
	public int updateById(Member member) {
		StringBuilder sql = new StringBuilder("update member set ");
		final String name = member.getName();
		final String phone = member.getPhone();
		final String gender = member.getGender();
		final Date birth = member.getBirthday();
		if(birth != null) {
			sql.append("member_birth=?");
		}
		if(gender != null && !gender.isEmpty()) {
			sql.append(", member_gender=?");
		}
		
		if (name != null && !name.isEmpty()) {
			sql.append(", member_name=?");
		}
		if (phone != null && !phone.isEmpty()) {
			sql.append(", member_phone=?");
		}
		
		sql.append(" where member_id = ?");

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());) {
			int nxtSeq = 1;
			
			if(birth != null) {
				pstmt.setDate(nxtSeq++, birth);
			}
			if(gender != null && !gender.isEmpty()) {
				pstmt.setString(nxtSeq++, gender);
			}
			if (name != null && !name.isEmpty()) {
				pstmt.setString(nxtSeq++, name);
			}
			if (phone != null && !phone.isEmpty()) {
				pstmt.setString(nxtSeq++, phone);
			}
			pstmt.setInt(nxtSeq, member.getId());

			
			System.out.println(sql);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static final String LOGIN = "select * from member where member_account = ? and member_password = ?";
	@Override
	public Member find(Member member) {
		try (
			Connection con = HikariDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(LOGIN);
		){
			pstmt.setString(1, member.getAccount());
			pstmt.setString(2, member.getPassword());
			try (ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					member.setId(rs.getInt("member_id"));
					member.setAccount(rs.getString("member_account"));
					member.setPassword(rs.getString("member_password"));
					member.setName(rs.getString("member_name"));
					member.setGender(rs.getString("member_gender"));
					member.setUsername(rs.getString("member_username"));
					member.setPhone(rs.getString("member_phone"));;
					member.setBirthday(rs.getDate("member_birth"));
					member.setReg_date(rs.getTimestamp("member_reg_date"));
					member.setAddress(rs.getString("member_address"));
					member.setIntro(rs.getString("member_intro"));
					member.setImg(rs.getBytes("member_img"));
					member.setBanner_img(rs.getBytes("member_banner_img"));
					member.setAccessnum(rs.getString("member_accessnum"));
					return member;
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String UPDATE_IMG_BY_ID = "update member set member_img = ? where member_id = ?;";
	@Override
	public int updateImgById(Member member) {
		try (	Connection conn = HikariDataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_IMG_BY_ID);
		) {
			pstmt.setBytes(1, member.getImg());
			pstmt.setInt(2, member.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public byte[] selectAvatarById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String SELECT_ONE_BY_ID = "select * from member where member_id = ?";
	@Override
	public Member selectById(Integer id) {
		try(	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ONE_BY_ID);
		) {
			pstmt.setInt(1, id);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Member member = new Member();
					member.setId(rs.getInt("member_id"));
					member.setAccount(rs.getString("member_account"));
					member.setPassword(rs.getString("member_password"));
					member.setName(rs.getString("member_name"));
					member.setGender(rs.getString("member_gender"));
					member.setUsername(rs.getString("member_username"));
					member.setPhone(rs.getString("member_phone"));;
					member.setBirthday(rs.getDate("member_birth"));
					member.setReg_date(rs.getTimestamp("member_reg_date"));
					member.setAddress(rs.getString("member_address"));
					member.setIntro(rs.getString("member_intro"));
					member.setImg(rs.getBytes("member_img"));
					member.setBanner_img(rs.getBytes("member_banner_img"));
					member.setAccessnum(rs.getString("member_accessnum"));
					
					return member;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final String SELECT_ONE_BY_ACCOUNT = "select * from member where member_account = ?";
	@Override
	public Member selectByAccount(String account) {
		try(	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ONE_BY_ACCOUNT);
		) {
			pstmt.setString(1, account);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Member member = new Member();
					member.setId(rs.getInt("member_id"));
					member.setAccount(rs.getString("member_account"));
					member.setPassword(rs.getString("member_password"));
					member.setName(rs.getString("member_name"));
					member.setGender(rs.getString("member_gender"));
					member.setUsername(rs.getString("member_username"));
					member.setPhone(rs.getString("member_phone"));;
					member.setBirthday(rs.getDate("member_birth"));
					member.setReg_date(rs.getTimestamp("member_reg_date"));
					member.setAddress(rs.getString("member_address"));
					member.setIntro(rs.getString("member_intro"));
					member.setImg(rs.getBytes("member_img"));
					member.setBanner_img(rs.getBytes("member_banner_img"));
					member.setAccessnum(rs.getString("member_accessnum"));
					
					return member;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String GET_ALL_STMT = "select * from member order by member_id";
	@Override
	public List<Member> getAll() {
		List<Member> resultList = new ArrayList<Member>();
		Member member;
		try( 	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
				ResultSet rs = pstmt.executeQuery();){
			while(rs.next()) {
				member = new Member();

				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if(img !=null && img.length !=0 ) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
				
				member.setId(rs.getInt("member_id"));
				member.setAccount(rs.getString("member_account"));
				member.setPassword(rs.getString("member_password"));
				member.setName(rs.getString("member_name"));
				member.setGender(rs.getString("member_gender"));
				member.setUsername(rs.getString("member_username"));
				member.setPhone(rs.getString("member_phone"));;
				member.setBirthday(rs.getDate("member_birth"));
				member.setReg_date(rs.getTimestamp("member_reg_date"));
				member.setAddress(rs.getString("member_address"));
				member.setIntro(rs.getString("member_intro"));
				member.setBanner_img(rs.getBytes("member_banner_img"));
				member.setAccessnum(rs.getString("member_accessnum"));
				resultList.add(member);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Override
	public List<Member> getAll(String type, String text) {
		List<Member> resultList = new ArrayList<Member>();
		Member member;
		String getAllByText;

		if(type != null && type.equals("編號")) {
			getAllByText = "select * from member where member_id like \"%"+ text +"%\" order by member_id";
		}else if(type != null && type.equals("帳號")) {
			getAllByText = "select * from member where member_account like \"%"+ text +"%\" order by member_id";
		}else {
			getAllByText = "select * from member where member_username like \"%"+ text +"%\" order by member_id";
		}
		try( 	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(getAllByText);
				ResultSet rs = pstmt.executeQuery();){
			while(rs.next()) {
				member = new Member();
				
				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if(img !=null && img.length !=0 ) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
				
				member.setId(rs.getInt("member_id"));
				member.setAccount(rs.getString("member_account"));
				member.setPassword(rs.getString("member_password"));
				member.setName(rs.getString("member_name"));
				member.setGender(rs.getString("member_gender"));
				member.setUsername(rs.getString("member_username"));
				member.setPhone(rs.getString("member_phone"));;
				member.setBirthday(rs.getDate("member_birth"));
				member.setReg_date(rs.getTimestamp("member_reg_date"));
				member.setAddress(rs.getString("member_address"));
				member.setIntro(rs.getString("member_intro"));
				member.setBanner_img(rs.getBytes("member_banner_img"));
				member.setAccessnum(rs.getString("member_accessnum"));
				resultList.add(member);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public int updateintroById(Member member) {
		StringBuilder sql = new StringBuilder("update member set ");
		final String un = member.getUsername();
		final String address = member.getAddress();
		final String intro = member.getIntro();
		if(un != null && !un.isEmpty()) {
			sql.append("member_username=?");
		}
		if(address != null && !address.isEmpty()) {
			sql.append(", member_address=?");
		}
		
		if (intro != null && !intro.isEmpty()) {
			sql.append(", member_intro=?");
		}
		
		sql.append(" where member_id = ?");

		try (Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());) {
			int nxtSeq = 1;
			
			if(un != null && !un.isEmpty()) {
				pstmt.setString(nxtSeq++, un);
			}
			if(address != null && !address.isEmpty()) {
				pstmt.setString(nxtSeq++, address);
			}
			if (intro != null && !intro.isEmpty()) {
				pstmt.setString(nxtSeq++, intro);
			}
			pstmt.setInt(nxtSeq, member.getId());

			
			System.out.println(sql);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	final static String UPDATE_PASSWORD_BY_ID = "update member set member_password = ? where member_id = ?";	
	@Override
	public int updatePasswordById(Member member) {
		try (	Connection conn = HikariDataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_PASSWORD_BY_ID);
		) {
			pstmt.setString(1, member.getPassword());
			pstmt.setInt(2, member.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	


}