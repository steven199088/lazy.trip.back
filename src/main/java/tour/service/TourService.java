package tour.service;

import java.util.List;

import tour.model.TourVO;

public interface TourService {
    int tourCreate(TourVO tourVO);

    TourVO tourUpdate(TourVO tourVO);

    String tourDelete(Integer tourId);

    List<TourVO> tourQueryAll(Integer memberId);

    List<TourVO> tourTitleQuery(String queryStr, Integer memberId);
    
    TourVO getTourInfoByTourId(Integer tourId);
}

