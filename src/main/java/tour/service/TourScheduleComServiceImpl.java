package tour.service;

import java.util.List;

import javax.naming.NamingException;

import tour.dao.TourScheduleComDao;
import tour.dao.TourScheduleComDaoImpl;
import tour.model.TourScheduleComVO;


public class TourScheduleComServiceImpl implements TourScheduleComService{
	private TourScheduleComDao dao;
	public TourScheduleComServiceImpl() throws NamingException {
		dao = new TourScheduleComDaoImpl();
	}
	@Override
	public List<Integer> tourScheduleComCreate(List<TourScheduleComVO> lists) {
		final List<Integer> result = dao.insert(lists);
		return result != null ? result : null;
	}

	@Override
	public TourScheduleComVO tourScheduleComUpdate(TourScheduleComVO tourScheduleComVO) {
			final int result = dao.update(tourScheduleComVO);
			return result > 0 ? tourScheduleComVO : null;
	}

	@Override
	public String tourScheduleComDelete(Integer tourScheduleComId) {
		final int result = dao.delete(tourScheduleComId);
		return result > 0 ? "刪除成功" : null;
	}

	@Override
	public List<TourScheduleComVO> tourScheduleComQueryAll(Integer tourComId) {
		final List<TourScheduleComVO> resultLists = dao.getAll(tourComId);
		return resultLists;
	}
	@Override
	public List<TourScheduleComVO> tourScheComQueryOne(Integer tourComId) {
		final List<TourScheduleComVO> resultLists = dao.findByPrimaryKey(tourComId);
		return resultLists;
	}

}
