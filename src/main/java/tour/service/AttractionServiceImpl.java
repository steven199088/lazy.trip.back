package tour.service;

import java.util.List;

import javax.naming.NamingException;

import tour.dao.AttractionDao;
import tour.dao.AttractionDaoImpl;
import tour.model.AttractionVO;

public class AttractionServiceImpl implements AttractionService {
    private AttractionDao dao;

    public AttractionServiceImpl() throws NamingException {
        dao = new AttractionDaoImpl();
    }

    @Override
    public int attrCreate(AttractionVO attractionVO) {
        final int result = dao.insert(attractionVO);
        return result > 0 ? result : -1;
    }

    @Override
    public AttractionVO attrUpdate(AttractionVO attractionVO) {
        final int result = dao.update(attractionVO);
        return result > 0 ? attractionVO : null;
    }

    @Override
    public String attrDelete(Integer attractionId) {
        final int result = dao.delete(attractionId);
        return result > 0 ? "刪除成功" : null;
    }

    @Override
    public List<AttractionVO> attrQueryAll() {
        final List<AttractionVO> resultLists = dao.getAll();
        return resultLists;
    }

    @Override
    public AttractionVO attrQueryOne(Integer attractionId) {
        final AttractionVO result = dao.findByPrimaryKey(attractionId);
        return result;
    }


}
