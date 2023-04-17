package tour.service;

import java.util.List;

import javax.naming.NamingException;

import tour.dao.TourDao;
import tour.dao.TourDaoImpl;
import tour.model.TourVO;

public class TourServiceImpl implements TourService {
    private TourDao dao;

    public TourServiceImpl() throws NamingException {
        dao = new TourDaoImpl();
    }

    @Override
    public int tourCreate(TourVO tourVO) {
        final int result = dao.insert(tourVO);
        return result > 0 ? result : -1;
    }

    @Override
    public TourVO tourUpdate(TourVO tourVO) {
        final int result = dao.update(tourVO);
        return result > 0 ? tourVO : null;
    }

    @Override
    public String tourDelete(Integer tourId) {
        final int result = dao.delete(tourId);
        return result > 0 ? "刪除成功" : null;
    }

    @Override
    public List<TourVO> tourQueryAll(Integer memberId) {
        final List<TourVO> resultLists = dao.getAll(memberId);
        return resultLists;
    }

    @Override
    public List<TourVO> tourTitleQuery(String queryStr, Integer memberId) {
        final List<TourVO> result = dao.findByTourTitle(queryStr, memberId);
        return result;
    }
    
    @Override
    public TourVO getTourInfoByTourId(Integer tourId) {
        final TourVO result = dao.getTourByTourId(tourId);
        return result;
    }
    
    
}
