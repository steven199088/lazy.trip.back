package tour.dao;

import java.util.Set;

import tour.model.TourTagVO;

public interface TourTagDao {
	int saveTourTag(TourTagVO tourTagVO);
	int deleteTourTagOnTour(TourTagVO tourTagVO);
	int deleteTourTag(TourTagVO tourTagVO);
    Set<String> getTourTagByTourId(TourTagVO tourTagVO);
    Set<String> getTourByTourTagTitle(TourTagVO tourTagVO);
    Set<String> getTourTagByMember(TourTagVO tourTagVO);
    Set<String> getTourTagByQueryStr(TourTagVO tourTagVO);
    
//    TourTagVO updateTourTag(TourTagVO tourTagVO);
}
