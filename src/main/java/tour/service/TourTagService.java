package tour.service;

import java.util.List;
import java.util.Set;

import tour.model.TourTagVO;

public interface TourTagService {
    int tourTagCreate(TourTagVO tourTagVO);

//    TourVO tourTagUpdate(TourTagVO tourTagVO);
//
    String tourTagOnTourDelete(TourTagVO tourTagVO);

    String tourTagDelete(TourTagVO tourTagVO);
        
    Set<String> tourQueryByMember(TourTagVO tourTagVO);
    
    Set<String> tourQueryByTourId(TourTagVO tourTagVO);
    
    Set<String> tourQueryByTourTagTitle(TourTagVO tourTagVO);
    
    Set<String> tourTagQuery(TourTagVO tourTagVO);

//    TourTagVO tourTagQueryOne(Integer tourTagId);
}

