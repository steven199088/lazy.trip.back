package tour.dao;

import java.util.List;

import tour.model.TourComDTO;
import tour.model.TourComVO;

public interface TourComDao {
    int insert(TourComVO tourComVO);

    int update(TourComVO tourComVO);

    int delete(Integer tourComId);

    List<TourComVO> getAllByCompany(Integer companyId);
    
    List<TourComVO> getAll();

    TourComVO getTourByTourComId(Integer tourComId);
    
    List<TourComVO> getInfoBySelection(TourComDTO text);
}
