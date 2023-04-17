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
import tour.model.TourScheduleComVO;


public class TourScheduleComDaoImpl implements TourScheduleComDao {
	
	private static final String INSERT_SQL = "insert into tour_schedule_company (date, start_time, stay_time, end_time, attraction_id, c_tour_id, car_route_time) values (?,?,?,?,?,?,?);";
	private static final String UPDATE_SQL = "update tour_schedule_company set date=?, start_time=?, stay_time=?, end_time=?, attraction_id=? where c_tour_schedule_id=?;";
	private static final String DELETE_SQL = "update tour_schedule_company set status=? where c_tour_schedule_id = ?";
	private static final String GET_ALL_SQL = "select c_tour_schedule_id, c_tour_id, attraction_id, date, start_time, stay_time, end_time from tour_schedule_company where company_id;";
	private static final String GET_ONE_SQL = "select c_tour_schedule_id, ts.attraction_id, date, start_time, stay_time, end_time, car_route_time, status, attraction_title, location, attraction_img, longitude, latitude from tour_schedule_company ts join attraction a1 on ts.attraction_id = a1.attraction_id where c_tour_id = ?;";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@Override
	public List<Integer> insert(List<TourScheduleComVO> lists) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			for (TourScheduleComVO tourScheduleComVO : lists) {
				Date Date = dateFormat.parse(tourScheduleComVO.getDate());
				Time startTime = new Time(timeFormat.parse(tourScheduleComVO.getStartTime()).getTime());
				Time endTime = new Time(timeFormat.parse(tourScheduleComVO.getEndTime()).getTime());

				ps.setObject(1, Date);
				ps.setObject(2, startTime);
				ps.setInt(3, tourScheduleComVO.getStayTime());
				ps.setObject(4, endTime);
				ps.setInt(5, tourScheduleComVO.getAttractionId());
				ps.setInt(6, tourScheduleComVO.getTourComId());
				ps.setString(7, tourScheduleComVO.getCarRouteTime());
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
	public int update(TourScheduleComVO tourScheduleComVO) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			Date Date = dateFormat.parse(tourScheduleComVO.getDate());
			Time startTime = new Time(timeFormat.parse(tourScheduleComVO.getStartTime()).getTime());
			Time endTime = new Time(timeFormat.parse(tourScheduleComVO.getEndTime()).getTime());

			ps.setObject(1, Date);
			ps.setObject(2, startTime);
			ps.setInt(3, tourScheduleComVO.getStayTime());
			ps.setObject(4, endTime);
			ps.setInt(5, tourScheduleComVO.getAttractionId());
			ps.setInt(6, tourScheduleComVO.getTourScheduleComId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;

	}
    
	@Override
	public int delete(Integer tourScheduleComId) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			String str = "D";
			ps.setString(1, str);
			ps.setInt(2, tourScheduleComId);

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<TourScheduleComVO> getAll(Integer tourComId) {
		List<TourScheduleComVO> list = new ArrayList<TourScheduleComVO>();
		TourScheduleComVO tourScheduleComVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

			rs = ps.executeQuery();
			while (rs.next()) {
				tourScheduleComVO = new TourScheduleComVO();
				tourScheduleComVO.setTourScheduleComId(rs.getInt("c_tour_schedule_id"));
				tourScheduleComVO.setTourComId(rs.getInt("c_tour_id"));
				tourScheduleComVO.setAttractionId(rs.getInt("attraction_id"));
				tourScheduleComVO.setDate(String.valueOf(rs.getDate("date")));
				String strTime = timeFormat.format(rs.getTime("start_time"));
				tourScheduleComVO.setStartTime(strTime);
				tourScheduleComVO.setStayTime(rs.getInt("stay_time"));
				
				list.add(tourScheduleComVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<TourScheduleComVO> findByPrimaryKey(Integer tourComId) {
		List<TourScheduleComVO> list = new ArrayList<TourScheduleComVO>();
		TourScheduleComVO tourScheduleComVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

			ps.setInt(1, tourComId);

			rs = ps.executeQuery();
			while (rs.next()) {
				tourScheduleComVO = new TourScheduleComVO();
				tourScheduleComVO.setTourScheduleComId(rs.getInt("c_tour_Schedule_id"));
				tourScheduleComVO.setAttractionId(rs.getInt("attraction_id"));
				tourScheduleComVO.setDate(String.valueOf(rs.getDate("date")));
				String strStartTime = timeFormat.format(rs.getTime("start_time"));
				tourScheduleComVO.setStartTime(strStartTime);
				tourScheduleComVO.setStayTime(rs.getInt("stay_time"));
				String strEndTime = timeFormat.format(rs.getTime("end_time"));
				tourScheduleComVO.setEndTime(strEndTime);
				tourScheduleComVO.setCarRouteTime(rs.getString("car_route_time"));
				tourScheduleComVO.setStatus(rs.getString("status"));
				if (tourScheduleComVO.getStatus() != null && tourScheduleComVO.getStatus().equals("D")) {
					continue;
				}
				AttractionVO attractionVO = new AttractionVO();
				attractionVO.setAttractionTitle(rs.getString("attraction_title"));
				attractionVO.setLocation(rs.getString("location"));
				attractionVO.setAttractionImg(rs.getString("attraction_img"));
				attractionVO.setLatitude(rs.getDouble("latitude"));
				attractionVO.setLongitude(rs.getDouble("longitude"));
				tourScheduleComVO.setAttractionVO(attractionVO);
				list.add(tourScheduleComVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
