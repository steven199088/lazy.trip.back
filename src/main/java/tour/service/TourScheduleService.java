package tour.service;

import java.util.List;

import tour.model.TourScheduleVO;

public interface TourScheduleService {
	List<Integer> tourScheCreate(List<TourScheduleVO> lists);

    TourScheduleVO tourScheUpdate(TourScheduleVO tourScheduleVO);

    String tourScheDelete(Integer tourScheduleId);

    List<TourScheduleVO> tourScheQueryAll();

    List<TourScheduleVO> tourScheQueryOne(Integer tourId);
}
