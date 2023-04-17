package tour.dao;

import java.util.List;

import tour.model.TourScheduleVO;

public interface TourScheduleDao {
    public List<Integer> insert(List<TourScheduleVO> lists);

    public int update(TourScheduleVO tourScheduleVO);

    public int delete(Integer tourScheduleId);

    public List<TourScheduleVO> getAll();

    public List<TourScheduleVO> findByPrimaryKey(Integer tourId);
}
