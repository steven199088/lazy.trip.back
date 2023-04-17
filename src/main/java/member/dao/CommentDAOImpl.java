package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.HikariDataSource;
import member.model.Comment;

public class CommentDAOImpl implements CommentDAO{

	private static final String INSERT = "insert into comment(member_id, comment_text, comment_status) values(?, ?, '1')";
	@Override
	public int insert(Comment comment) {
		int generatedKey = -1;
		try(
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setInt(1, comment.getMemberId());
			pstmt.setString(2, comment.getText());
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
	public int updatById(Comment comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	private static final String SELECT_ONE_BY_ID = "select * from comment where member_id= ? order by comment_time desc";
	@Override
	public Comment selectById(Integer id) {
		try(	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ONE_BY_ID);
		) {
			pstmt.setInt(1, id);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Comment comment = new Comment();
					
					comment.setId(rs.getInt("comment_id"));
					comment.setMemberId(rs.getInt("member_id"));
					comment.setTime(rs.getTimestamp("comment_time"));
					comment.setText(rs.getString("comment_text"));
					comment.setStaus(rs.getString("comment_status"));
					
					return comment;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String GET_ALL_IMG = "select c.comment_id,c.member_id, c.comment_time, c.comment_status, ci.comment_img "
			+ "from comment c "
			+ "left join comment_img ci on c.comment_id = ci.comment_id "
			+ "where c.comment_text = '' and c.member_id = ? and ci.comment_img is not null "
			+ "order by comment_time desc ";
	@Override
	public List<Comment> getAllImg(Integer memId) {
		List<Comment> resultList = new ArrayList<Comment>();
		Comment comment;
		
		try( 	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_IMG);
				){
			pstmt.setInt(1, memId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				comment = new Comment();

				comment.setId(rs.getInt("comment_id"));
				comment.setMemberId(rs.getInt("member_id"));
				comment.setTime(rs.getTimestamp("comment_time"));
				comment.setStaus(rs.getString("comment_status"));
				
				byte[] img = rs.getBytes("comment_img");
				comment.setImgbase64Str(Base64.getEncoder().encodeToString(img));
				
				resultList.add(comment);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	final static String GET_ALL_STMT = "select c.comment_id,c.member_id, c.comment_time, c.comment_status, c.comment_text, ci.comment_img  "
			+ "from comment c "
			+ "left join comment_img ci on c.comment_id = ci.comment_id "
			+ "where c.member_id = ? "
			+ "order by comment_time desc;";
	@Override
	public List<Comment> getAll(Integer memId) {
		List<Comment> resultList = new ArrayList<Comment>();
		Comment comment;
		
		try( 	Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
				){
			pstmt.setInt(1, memId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				comment = new Comment();

				comment.setId(rs.getInt("comment_id"));
				comment.setMemberId(rs.getInt("member_id"));
				comment.setTime(rs.getTimestamp("comment_time"));
				comment.setText(rs.getString("comment_text"));
				comment.setStaus(rs.getString("comment_status"));
				
				byte[] img = rs.getBytes("comment_img");
				if(img == null) {
					
				}else {
					comment.setImgbase64Str(Base64.getEncoder().encodeToString(img));
				}
				
				resultList.add(comment);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	private static final String GET_ALL_COMMENT = "select c.comment_id,c.member_id, c.comment_time, c.comment_status, c.comment_text, ci.comment_img  "
			+ "from comment c "
			+ "left join comment_img ci on c.comment_id = ci.comment_id "
			+ "where c.comment_text != '' and c.member_id = ? "
			+ "order by comment_time desc";

	@Override
	public List<Comment> getAllComment(Integer memId) {
		List<Comment> resultList = new ArrayList<Comment>();
		Comment comment;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_COMMENT);) {
			pstmt.setInt(1, memId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				comment = new Comment();

				comment.setId(rs.getInt("comment_id"));
				comment.setMemberId(rs.getInt("member_id"));
				comment.setTime(rs.getTimestamp("comment_time"));
				comment.setText(rs.getString("comment_text"));
				comment.setStaus(rs.getString("comment_status"));

				byte[] img = rs.getBytes("comment_img");
				if (img == null) {

				} else {
					comment.setImgbase64Str(Base64.getEncoder().encodeToString(img));
				}

				resultList.add(comment);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
