package order.service;

import com.google.gson.Gson;
import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;
import order.model.CompanyVO;

import java.util.List;

public class BookingSearchService {
    private final OrderDAOInterface dao;
    Gson gson;

    public BookingSearchService() {
        dao = new OrderJDBCDAOImpl();
        gson = new Gson();
    }


    public List<CompanyVO> showCompanyAndRoomTypePriceByPosition(String addressCounty){
        return dao.selectFindCompanyAndRoomTypePriceByPosition(addressCounty);
    }


    public List<CompanyVO> showCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(String keyword){
        return dao.SelectFindCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(keyword);
    }
}
