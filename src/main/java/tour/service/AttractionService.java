package tour.service;

import java.util.List;

import tour.model.AttractionVO;

public interface AttractionService {
    int attrCreate(AttractionVO attractionVO);

    AttractionVO attrUpdate(AttractionVO attractionVO);

    String attrDelete(Integer attractionId);

    List<AttractionVO> attrQueryAll();

    AttractionVO attrQueryOne(Integer attractionId);
}
