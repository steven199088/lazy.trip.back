package tour.service;

import java.util.Set;

import javax.naming.NamingException;

import tour.dao.TourTagDao;
import tour.dao.TourTagDaoImpl;
import tour.model.TourTagVO;

public class TourTagServiceImpl implements TourTagService {
	private TourTagDao dao;

	public TourTagServiceImpl() throws NamingException {
		dao = new TourTagDaoImpl();
	}

	@Override
	public int tourTagCreate(TourTagVO tourTagVO) {
		final int result = dao.saveTourTag(tourTagVO);
		return result > 0 ? result : -99;
	}

	@Override
	public String tourTagOnTourDelete(TourTagVO tourTagVO) {
		final int result = dao.deleteTourTagOnTour(tourTagVO);
		return result > 0 ? "刪除成功" : null;
	}

	@Override
	public String tourTagDelete(TourTagVO tourTagVO) {
		final int result = dao.deleteTourTag(tourTagVO);
		return result > 0 ? "刪除成功" : null;
	}

	@Override
	public Set<String> tourQueryByMember(TourTagVO tourTagVO) {
		final Set<String> resultSet = dao.getTourTagByMember(tourTagVO);
		return resultSet;
	}

	@Override
	public Set<String> tourQueryByTourTagTitle(TourTagVO tourTagVO) {
		final Set<String> resultSet = dao.getTourByTourTagTitle(tourTagVO);
		return resultSet;

	}

	@Override
	public Set<String> tourQueryByTourId(TourTagVO tourTagVO) {
		final Set<String> resultSet = dao.getTourTagByTourId(tourTagVO);
		return resultSet;
	}

	@Override
	public Set<String> tourTagQuery(TourTagVO tourTagVO) {
		final Set<String> resultSet = dao.getTourTagByQueryStr(tourTagVO);
		return resultSet;
	}
}
