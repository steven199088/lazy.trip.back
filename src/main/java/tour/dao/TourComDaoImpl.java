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
import tour.model.TourComDTO;
import tour.model.TourComVO;

public class TourComDaoImpl implements TourComDao {

	private static final String INSERT_SQL = "insert into tour_company (tour_title, start_date, end_date, tour_img, cost, tour_person, company_id, feature) values (?,?,?,?,?,?,?,?);";
	private static final String UPDATE_SQL = "update tour_company set tour_title=?, start_date=?, end_date=?, tour_img=?, cost=?, tour_person=? where c_tour_id=? and company_id=?;";
	private static final String DELETE_SQL = "update tour_company set status=? where c_tour_id = ?";
	private static final String GET_ALL_SQL_COM = "select c_tour_id, tour_title, start_date, end_date, tour_img, cost, tour_person, company_id, status, feature from tour_company where company_id=? order by start_date;";
	private static final String GET_ONE_SQL = "select c_tour_id, tour_title, start_date, end_date, tour_img, cost, tour_person, company_id, feature form tour_company where c_tour_id= ? order by start_date;";
	private static final String GET_ALL_SQL = "select c_tour_id, tour_title, start_date, end_date, tour_img, cost, tour_person, company_id, feature from tour_company where status is null order by start_date;";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public int insert(TourComVO tourComVO) {
		byte[] decodedBytes = null;
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, tourComVO.getTourTitle());
			Date StartDate = dateFormat.parse(tourComVO.getStartDate());
			Date endDate = dateFormat.parse(tourComVO.getEndDate());
			ps.setObject(2, StartDate);
			ps.setObject(3, endDate);

			decodedBytes = Base64.getMimeDecoder().decode(tourComVO.getTourImg());
			ps.setBytes(4, decodedBytes);
			ps.setInt(5, tourComVO.getCost());
			ps.setInt(6, tourComVO.getTourPerson());
			ps.setInt(7, tourComVO.getCompanyId());
			ps.setString(8, tourComVO.getFeature());
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
	public int update(TourComVO tourComVO) {
		byte[] decodedBytes = null;
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, tourComVO.getTourTitle());
			Date StartDate = dateFormat.parse(tourComVO.getStartDate());
			Date endDate = dateFormat.parse(tourComVO.getEndDate());
			ps.setObject(2, StartDate);
			ps.setObject(3, endDate);
			decodedBytes = Base64.getMimeDecoder().decode(tourComVO.getTourImg());
			ps.setBytes(4, decodedBytes);
			ps.setInt(5, tourComVO.getCost());
			ps.setInt(6, tourComVO.getTourPerson());
			ps.setInt(7, tourComVO.getTourComId());
			ps.setInt(8, tourComVO.getCompanyId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public int delete(Integer tourComId) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			String str = "D";
			ps.setString(1, str);
			ps.setInt(2, tourComId);

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<TourComVO> getAllByCompany(Integer companyId) {
		List<TourComVO> list = new ArrayList<TourComVO>();
		TourComVO tourComVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL_COM)) {
			ps.setInt(1, companyId);
			rs = ps.executeQuery();

			while (rs.next()) {
				tourComVO = new TourComVO();
				tourComVO.setTourComId(rs.getInt("c_tour_id"));
				tourComVO.setTourTitle(rs.getString("tour_title"));
				tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourComVO.setTourImg(encodedImage);
				}
				tourComVO.setCost(rs.getInt("cost"));
				tourComVO.setTourPerson(rs.getInt("tour_person"));
				tourComVO.setCompanyId(rs.getInt("company_id"));
				tourComVO.setFeature(rs.getString("feature"));
				tourComVO.setStatus(rs.getString("status"));
				if (tourComVO.getStatus() != null && tourComVO.getStatus().equals("D")) {
					continue;
				}
				list.add(tourComVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public TourComVO getTourByTourComId(Integer tourComId) {
		TourComVO tourComVO = null;
		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

			ps.setInt(1, tourComId);

			rs = ps.executeQuery();

			while (rs.next()) {
				tourComVO = new TourComVO();
				tourComVO.setTourComId(rs.getInt("c_tour_id"));
				tourComVO.setTourTitle(rs.getString("tour_title"));
				tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourComVO.setTourImg(encodedImage);
				}
				tourComVO.setCompanyId(rs.getInt("company_id"));
				tourComVO.setCost(rs.getInt("cost"));
				tourComVO.setTourPerson(rs.getInt("tour_person"));
				tourComVO.setFeature(rs.getString("feature"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tourComVO;

	}

	@Override
	public List<TourComVO> getAll() {
		List<TourComVO> list = new ArrayList<TourComVO>();
		TourComVO tourComVO = null;

		ResultSet rs = null;

		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {
			rs = ps.executeQuery();

			while (rs.next()) {
				tourComVO = new TourComVO();
				tourComVO.setTourComId(rs.getInt("c_tour_id"));
				tourComVO.setTourTitle(rs.getString("tour_title"));
				tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourComVO.setTourImg(encodedImage);
				}
				tourComVO.setCompanyId(rs.getInt("company_id"));
				tourComVO.setCost(rs.getInt("cost"));
				tourComVO.setTourPerson(rs.getInt("tour_person"));
				tourComVO.setFeature(rs.getString("feature"));

				list.add(tourComVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TourComVO> getInfoBySelection(TourComDTO text) {
		List<TourComVO> list = new ArrayList<TourComVO>();
		TourComVO tourComVO = null;
		StringBuilder SQL = new StringBuilder("select * from tour_company where company_id = ?");
		String getInfoText;
		ResultSet rs = null;

		if (text.getTourTitle() != null && !text.getTourTitle().isEmpty()) {
			getInfoText = "tour_title like ?";
			SQL.append(" and ").append(getInfoText);
		}
		if (text.getStartDate() != null && !text.getStartDate().isEmpty()) {
			getInfoText = "start_date >= ?";
			SQL.append(" and ").append(getInfoText);
		}
		if (text.getEndDate() != null && !text.getEndDate().isEmpty()) {
			getInfoText = "end_date <= ?";
			SQL.append(" and ").append(getInfoText);
		}
		if (text.getCost() != null && !text.getCost().isEmpty()) {
			getInfoText = "cost like ?";
			SQL.append(" and ").append(getInfoText);
		}
		if (text.getFeature() != null && !text.getFeature().isEmpty()) {
			getInfoText = "feature like ?";
			SQL.append(" and ").append(getInfoText);
		}
		SQL.append(" order by start_date;");
		
		System.out.println(SQL.toString());
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL.toString())) {
			ps.setString(1, text.getCompanyId());
			
			int index = 2;
			if (text.getTourTitle() != null && !text.getTourTitle().isEmpty()) {
				ps.setString(index++, "%" + text.getTourTitle() + "%");
			}
			if (text.getStartDate() != null && !text.getStartDate().isEmpty()) {
				ps.setString(index++, text.getStartDate());
			}
			if (text.getEndDate() != null && !text.getEndDate().isEmpty()) {
				ps.setString(index++, text.getEndDate());
			}
			if (text.getCost() != null && !text.getCost().isEmpty()) {
				ps.setString(index++, "%" + text.getCost() + "%");
			}
			if (text.getFeature() != null && !text.getFeature().isEmpty()) {
				ps.setString(index++, "%" + text.getFeature() + "%");
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				tourComVO = new TourComVO();
				tourComVO.setTourComId(rs.getInt("c_tour_id"));
				tourComVO.setTourTitle(rs.getString("tour_title"));
				tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
				tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
				byte[] imageBytes = rs.getBytes("tour_img");
				if (imageBytes != null) {
					String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
					tourComVO.setTourImg(encodedImage);
				}
				tourComVO.setCompanyId(rs.getInt("company_id"));
				tourComVO.setCost(rs.getInt("cost"));
				tourComVO.setTourPerson(rs.getInt("tour_person"));
				tourComVO.setFeature(rs.getString("feature"));
				tourComVO.setStatus(rs.getString("status"));
				if (tourComVO.getStatus() != null && tourComVO.getStatus().equals("D")) {
					continue;
				}

				list.add(tourComVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
