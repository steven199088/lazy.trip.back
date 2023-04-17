package tour.dao;

import java.util.List;

import tour.model.TourVO;

public interface TourDao {
    int insert(TourVO tourVO);

    int update(TourVO tourVO);

    int delete(Integer tourId);

    List<TourVO> getAll(Integer memberId);
    
    TourVO getTourByTourId(Integer tourId);

    List<TourVO> findByTourTitle(String queryStr, Integer memberId);
}
