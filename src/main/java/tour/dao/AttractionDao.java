package tour.dao;

import java.util.List;

import tour.model.AttractionVO;

public interface AttractionDao {
    public int insert(AttractionVO attractionVO);

    public int update(AttractionVO attractionVO);

    public int delete(Integer attractionId);

    List<AttractionVO> getAll();

    AttractionVO findByPrimaryKey(Integer attractionId);
}
