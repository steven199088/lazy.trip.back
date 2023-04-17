package tour.service;

import java.util.List;

import tour.model.TourScheduleComVO;

public interface TourScheduleComService {
	List<Integer> tourScheduleComCreate(List<TourScheduleComVO> lists);

	TourScheduleComVO tourScheduleComUpdate(TourScheduleComVO tourScheduleComVO);

    String tourScheduleComDelete(Integer tourScheduleComId);

    List<TourScheduleComVO> tourScheduleComQueryAll(Integer tourComId);

    List<TourScheduleComVO> tourScheComQueryOne(Integer tourComId);
}
