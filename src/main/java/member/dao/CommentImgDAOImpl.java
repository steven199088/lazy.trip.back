package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import common.HikariDataSource;
import member.model.CommentImg;

public class CommentImgDAOImpl implements CommentImgDAO{

	
	public static final String INSERT = "insert into comment_img(comment_id, comment_img) values(?, ?)";
	@Override
	public int insert(CommentImg commentImg) {
		int generatedKey = -1;
		try(
				Connection con = HikariDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setInt(1, commentImg.getCommentId());
			pstmt.setBytes(2, commentImg.getImg());
			
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


}
