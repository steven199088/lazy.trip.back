package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import common.HikariDataSource;
import tour.model.TourVO;

public class TourDaoImpl implements TourDao {
//	private static DataSource dataSource;
//	public TourDaoImpl() throws NamingException {
//		dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
//	}

	private static final String INSERT_SQL = "insert into tour (tour_title, start_date, end_date, tour_img, member_id) values (?,?,?,?,?);";
	private static final String UPDATE_SQL = "update tour set tour_title=?, start_date=?, end_date=?, tour_img=? where tour_id=? and member_id=?;";
	private static final String DELETE_SQL = "update tour set status=? where tour_id = ?";
	private static final String GET_ALL_SQL = "select tour_id, tour_title, start_date, end_date, tour_img, member_id, status from tour where member_id=? order by start_date;";
	private static final String GET_ONE_SQL = "select tour_id, tour_title, start_date, end_date, tour_img from tour where tour_id=? order by start_date;";
	private static final String GET_TOUR_SQL = "select tour_id, tour_title, start_date, end_date, tour_img, status from tour where tour_title like ? and member_id = ? order by start_date;";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public int insert(TourVO tourVO) {
		byte[] decodedBytes = null;
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, tourVO.getTourTitle());
			Date StartDate = dateFormat.parse(tourVO.getStartDate());
			Date endDate = dateFormat.parse(tourVO.getEndDate());
			ps.setObject(2, StartDate);
			ps.setObject(3, endDate);
			decodedBytes = Base64.getMimeDecoder().decode(tourVO.getTourImg());
			ps.setBytes(4, decodedBytes);
			ps.setInt(5, tourVO.getMemberId());
			int insertRow = ps.executeUpdate();
			if (insertRow > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int generatedKey = rs.getInt(1);
					return generatedKey;
				}
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(TourVO tourVO) {
		byte[] decodedBytes = null;
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, tourVO.getTourTitle());
			Date StartDate = dateFormat.parse(tourVO.getStartDate());
			Date endDate = dateFormat.parse(tourVO.getEndDate());
			ps.setObject(2, StartDate);
			ps.setObject(3, endDate);

			decodedBytes = Base64.getMimeDecoder().decode(tourVO.getTourImg());
			ps.setBytes(4, decodedBytes);
			ps.setInt(5, tourVO.getTourId());
			ps.setInt(6, tourVO.getMemberId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int delete(Integer tourId) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			String str = "D";
			ps.setString(1, str);
			ps.setInt(2, tourId);
			

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<TourVO> getAll(Integer memberId) {
		List<TourVO> list = new ArrayList<TourVO>();
		TourVO tourVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {
			
			ps.setInt(1, memberId);
			
			rs = ps.executeQuery();
			

			while (rs.next()) {
				tourVO = new TourVO();
				tourVO.setTourId(rs.getInt("tour_id"));
				tourVO.setTourTitle(rs.getString("tour_title"));
				tourVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourVO.setTourImg(encodedImage);
				}
				tourVO.setMemberId(rs.getInt("member_id"));
				tourVO.setStatus(rs.getString("status"));
				if (tourVO.getStatus() != null && tourVO.getStatus().equals("D")) {
					continue;
				}
				list.add(tourVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TourVO> findByTourTitle(String queryStr, Integer memberId) {
		List<TourVO> list = new ArrayList<TourVO>();
		TourVO tourVO = null;
		ResultSet rs = null;
		String Str = "%"+ queryStr +"%";
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_TOUR_SQL)) {
			
			ps.setString(1, Str);
			ps.setInt(2, memberId);

			rs = ps.executeQuery();

			while (rs.next()) {
				tourVO = new TourVO();
				tourVO.setTourId(rs.getInt("tour_id"));
				tourVO.setTourTitle(rs.getString("tour_title"));
				tourVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourVO.setTourImg(encodedImage);
				}
				tourVO.setStatus(rs.getString("status"));
				if (tourVO.getStatus() != null && tourVO.getStatus().equals("D")) {
					continue;
				}
				list.add(tourVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	@Override
	public TourVO getTourByTourId(Integer tourId) {
		TourVO tourVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

			ps.setInt(1, tourId);

			rs = ps.executeQuery();

			while (rs.next()) {
				tourVO = new TourVO();
				tourVO.setTourId(rs.getInt("tour_id"));
				tourVO.setTourTitle(rs.getString("tour_title"));
				tourVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourVO.setTourImg(encodedImage);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tourVO;

	}
}