package group.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import common.HikariDataSource;
import group.model.DiscussionVO;
import member.model.Member;



public class DiscussionDAOImpl implements DiscussionDAO_interface {

	private static DataSource ds = null;
	
	private static final String INSERT_STMT ="INSERT INTO lazy.discussion(member_id , discussion_content ,discussion_date , group_id ) VALUES(?,?,current_timestamp,?)";
	private static final String GET_ALL_STMT ="SELECT d.discussion, d.member_id , m.member_username , m.member_name , m.member_img , d.discussion_content , unix_timestamp(d.discussion_date) "
			+ "FROM lazy.discussion d JOIN lazy.member m ON d.member_id = m.member_id "
			+ "WHERE group_id = ?  order by d.discussion_date desc;";
	private static final String DELETE_STMT = "DELETE from lazy.discussion where discussion = ?";
	private static final String DELETE_ALL_STMT = "DELETE FROM lazy.discussion WHERE group_id = ? ";
	@Override
	public void insert(DiscussionVO discussionVO) {
		// TODO Auto-generated method stub
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_STMT)){

			pstmt.setInt(1, discussionVO.getMemberid());
			pstmt.setString(2, discussionVO.getDiscussionContent());
			pstmt.setInt(3, discussionVO.getGroupid());

			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DiscussionVO> getAllContentbyGroupId(Integer groupid) {
		// TODO Auto-generated method stub
		List<DiscussionVO> list = new ArrayList<DiscussionVO>();
		DiscussionVO discussionVo = new DiscussionVO();
		Member member = new Member();
		ResultSet rs = null;
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_STMT)){
			pstmt.setInt(1, groupid);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				discussionVo = new DiscussionVO();
				member = new Member();
				member.setUsername(rs.getString("member_username"));
				member.setName(rs.getString("member_name"));
				
				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if(img !=null && img.length !=0 ) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
//				member.setImg(rs.getBytes("member_img"));
		
				
				discussionVo.setDiscussion(rs.getInt("discussion"));
				discussionVo.setMemberid(rs.getInt("member_id"));
				discussionVo.setDiscussionContent(rs.getString("discussion_content"));
				discussionVo.setDicussionDate(rs.getLong("unix_timestamp(d.discussion_date)"));
				discussionVo.setDicussionday(rs.getLong("unix_timestamp(d.discussion_date)"));
				discussionVo.setDicussiontime(rs.getLong("unix_timestamp(d.discussion_date)"));
				discussionVo.setMember(member);
				list.add(discussionVo);

			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void delete(Integer discussion) {
		// TODO Auto-generated method stub
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_STMT)){
			pstmt.setInt(1, discussion);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void deleteAll(Integer id) {
		// TODO Auto-generated method stub
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_ALL_STMT)){
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
