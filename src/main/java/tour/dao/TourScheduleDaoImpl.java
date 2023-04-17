package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.HikariDataSource;
import tour.model.AttractionVO;
import tour.model.TourScheduleVO;

public class TourScheduleDaoImpl implements TourScheduleDao {
//    private static DataSource dataSource;
//
//    public TourScheduleDaoImpl() throws NamingException {
//        dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
//    }

	private static final String INSERT_SQL = "insert into tour_schedule (date, start_time, stay_time, end_time, car_route_time, attraction_id, tour_id) values (?,?,?,?,?,?,?);";
	private static final String UPDATE_SQL = "update tour_schedule set date=?, start_time=?, stay_time=?, end_time=?, car_route_time=?, attraction_id=? where tour_schedule_id=?;";
	private static final String DELETE_SQL = "delete from tour_schedule where tour_schedule_id = ?";
	private static final String GET_ALL_SQL = "select tour_schedule_id, tour_id, attraction_id, date, start_time, stay_time from tour_schedule order by tour_schedule_id;";
	private static final String GET_ONE_SQL = "select tour_schedule_id, ts.attraction_id, date, start_time, stay_time, end_time, car_route_time, attraction_title, location, attraction_img, longitude, latitude from tour_schedule ts join attraction a1 on ts.attraction_id = a1.attraction_id where tour_id = ?;";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@Override
	public List<Integer> insert(List<TourScheduleVO> lists) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			for (TourScheduleVO tourScheduleVO : lists) {
				Date Date = dateFormat.parse(tourScheduleVO.getDate());
				Time startTime = new Time(timeFormat.parse(tourScheduleVO.getStartTime()).getTime());
				Time endTime = new Time(timeFormat.parse(tourScheduleVO.getEndTime()).getTime());

				ps.setObject(1, Date);
				ps.setObject(2, startTime);
				ps.setInt(3, tourScheduleVO.getStayTime());
				ps.setObject(4, endTime);
				ps.setString(5, tourScheduleVO.getCarRouteTime());
				ps.setInt(6, tourScheduleVO.getAttractionId());
				ps.setInt(7, tourScheduleVO.getTourId());
				ps.addBatch();
			}

			ps.executeBatch();
			ResultSet rs = ps.getGeneratedKeys();
			List<Integer> generatedKeys = new ArrayList<>();
			while (rs.next()) {
				generatedKeys.add(rs.getInt(1));
			}
			return generatedKeys;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(TourScheduleVO tourScheduleVO) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			Date Date = dateFormat.parse(tourScheduleVO.getDate());
			Time startTime = new Time(timeFormat.parse(tourScheduleVO.getStartTime()).getTime());
			Time endTime = new Time(timeFormat.parse(tourScheduleVO.getEndTime()).getTime());

			ps.setObject(1, Date);
			ps.setObject(2, startTime);
			ps.setInt(3, tourScheduleVO.getStayTime());
			ps.setObject(4, endTime);
			ps.setString(5, tourScheduleVO.getCarRouteTime());
			ps.setInt(6, tourScheduleVO.getAttractionId());
			ps.setInt(7, tourScheduleVO.getTourScheduleId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int delete(Integer tourScheduleId) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

			ps.setInt(1, tourScheduleId);

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<TourScheduleVO> getAll() {
		List<TourScheduleVO> list = new ArrayList<TourScheduleVO>();
		TourScheduleVO tourScheduleVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

			rs = ps.executeQuery();
			while (rs.next()) {
				tourScheduleVO = new TourScheduleVO();
				tourScheduleVO.setTourScheduleId(rs.getInt("tour_schedule_id"));
				tourScheduleVO.setTourId(rs.getInt("tour_id"));
				tourScheduleVO.setAttractionId(rs.getInt("attraction_id"));
				tourScheduleVO.setDate(String.valueOf(rs.getDate("date")));
				String strTime = timeFormat.format(rs.getTime("start_time"));
				tourScheduleVO.setStartTime(strTime);
				tourScheduleVO.setStayTime(rs.getInt("stay_time"));
				list.add(tourScheduleVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TourScheduleVO> findByPrimaryKey(Integer tourId) {
		List<TourScheduleVO> list = new ArrayList<TourScheduleVO>();
		TourScheduleVO tourScheduleVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

			ps.setInt(1, tourId);

			rs = ps.executeQuery();
			while (rs.next()) {
				tourScheduleVO = new TourScheduleVO();
				tourScheduleVO.setTourScheduleId(rs.getInt("tour_Schedule_id"));
				tourScheduleVO.setAttractionId(rs.getInt("attraction_id"));
				tourScheduleVO.setDate(String.valueOf(rs.getDate("date")));
				String strStartTime = timeFormat.format(rs.getTime("start_time"));
				tourScheduleVO.setStartTime(strStartTime);
				tourScheduleVO.setStayTime(rs.getInt("stay_time"));
				String strEndTime = timeFormat.format(rs.getTime("end_time"));
				tourScheduleVO.setEndTime(strEndTime);
				tourScheduleVO.setCarRouteTime(rs.getString("car_route_time"));
				AttractionVO attractionVO = new AttractionVO();
				attractionVO.setAttractionTitle(rs.getString("attraction_title"));
				attractionVO.setLocation(rs.getString("location"));
				attractionVO.setAttractionImg(rs.getString("attraction_img"));
				attractionVO.setLatitude(rs.getDouble("latitude"));
				attractionVO.setLongitude(rs.getDouble("longitude"));
				tourScheduleVO.setAttractionVO(attractionVO);
				list.add(tourScheduleVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
