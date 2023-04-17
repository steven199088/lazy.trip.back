package tour.service;

import java.util.List;

import tour.model.TourComDTO;
import tour.model.TourComVO;

public interface TourComService {
	int tourComCreate(TourComVO tourComVO);

	TourComVO tourComUpdate(TourComVO tourComVO);

    String tourComDelete(Integer tourComId);

    List<TourComVO> tourComQueryAll(Integer companyId);
    
    List<TourComVO> queryAll(Integer memberId);
    
    TourComVO getTourInfoByTourComId(Integer tourComId);
    
    List<TourComVO> queryAllBySelection(TourComDTO tourComVO);
}
