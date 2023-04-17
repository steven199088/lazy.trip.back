package group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import common.HikariDataSource;
import group.model.GroupVO;
import group.model.Group_memberVO;
import tour.model.TourVO;

public class GroupDAOImpl implements GroupDAO_interface {

	private static DataSource ds = null;

	private static final String INSERT_STMT = "INSERT INTO lazy.group (  group_member_count , group_name , member_id ,if_join_group_directly) values(?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT group_id , tour_id , group_member_count , group_name , member_id FROM lazy.group"
			+ " where member_id = ?" + " order by group_id";
	private static final String GET_ONE_STMT = "SELECT group_id , tour_id , group_member_count , group_name , member_id , if_join_group_directly FROM lazy.group where group_id = ?";
	private static final String DELETE = "DELETE FROM lazy.group WHERE group_id = ?";
	private static final String UPDATE_GROUP_SETTING_STMT = "UPDATE lazy.group set group_member_count = ?  ,group_name = ? ,if_join_group_directly = ? where group_id= ?";
	private static final String UPDATE_GROUP_TOUR_STMT = "UPDATE lazy.group set tour_id = ? where group_id= ?";

	private static final String GET_TOURNAME_STMT = "SELECT  tour_title , start_date ,end_date FROM lazy.tour t WHERE t.tour_id= ?";
	private static final String INSERT_WHEN_CREATE_STMT = "INSERT INTO lazy.group_member (member_id , group_id , g_m_status ) values(?,?,?)";

	@Override
	public int insert(GroupVO groupVo) {
		// TODO Auto-generated method stub
		int generatedKey = -1;

		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, groupVo.getGroupmembercount());
			pstmt.setString(2, groupVo.getGroupname());
			pstmt.setInt(3, groupVo.getMemberid());
			pstmt.setInt(4, groupVo.getIfjoingroupdirectly());

			pstmt.executeUpdate();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					generatedKey = rs.getInt(1);
				} else {
					generatedKey = -2;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedKey;

	}

	@Override
	public void update(GroupVO groupVo) {
		// TODO Auto-generated method stub
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_GROUP_SETTING_STMT)) {

			pstmt.setInt(1, groupVo.getGroupmembercount());
			pstmt.setString(2, groupVo.getGroupname());
			pstmt.setInt(3, groupVo.getIfjoingroupdirectly());
			pstmt.setInt(4, groupVo.getGroupid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer groupid) {
		// TODO Auto-generated method stub
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE)) {

			pstmt.setInt(1, groupid);
			pstmt.executeUpdate();

			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

	@Override
	public GroupVO findByPrimaryKey(Integer groupid) {
		// TODO Auto-generated method stub
		GroupVO groupVO = null;
		ResultSet rs = null;

		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ONE_STMT)) {
			pstmt.setInt(1, groupid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGroupid(rs.getInt("group_id"));
				groupVO.setTourid(rs.getInt("tour_id"));
				groupVO.setGroupmembercount(rs.getInt("group_member_count"));
				groupVO.setGroupname(rs.getString("group_name"));
				groupVO.setMemberid(rs.getInt("member_id"));
				groupVO.setIfjoingroupdirectly(rs.getInt("if_join_group_directly"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupVO;
	}

	@Override
	public TourVO findTourNameByID(Integer tourid) {
		TourVO tourVO = null;
		ResultSet rs = null;
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_TOURNAME_STMT)) {
			pstmt.setInt(1, tourid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tourVO = new TourVO();
				tourVO.setTourTitle(rs.getString("tour_title"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = sdf.format(rs.getDate("start_date"));
				String endDate = sdf.format(rs.getDate("end_date"));
				tourVO.setStartDate(startDate);
				tourVO.setEndDate(endDate);
				return tourVO;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return tourVO;
	}

	@Override
	public void insertWhenCreate(Group_memberVO groupmemberVO) {
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_WHEN_CREATE_STMT)) {
			pstmt.setInt(1, groupmemberVO.getMemberid());
			pstmt.setInt(2, groupmemberVO.getGroupid());
			pstmt.setInt(3, 1);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateTour(GroupVO groupVo) {
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_GROUP_TOUR_STMT)) {
			pstmt.setInt(1, groupVo.getTourid());
			pstmt.setInt(2, groupVo.getGroupid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public List<GroupVO> getAllbyMemberId(Integer member_id) {
//		// TODO Auto-generated method stub
//		List list = new ArrayList<GroupVO>();
//		
//		return list;
//	}

}
