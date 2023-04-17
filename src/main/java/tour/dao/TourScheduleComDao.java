package tour.dao;

import java.util.List;

import tour.model.TourScheduleComVO;

public interface TourScheduleComDao {
	public List<Integer> insert(List<TourScheduleComVO> lists);

	public int update(TourScheduleComVO tourScheduleComVO);

	public int delete(Integer tourScheduleComId);

	public List<TourScheduleComVO> getAll(Integer tourComId);

	public List<TourScheduleComVO> findByPrimaryKey(Integer tourComId);
}
