package order.dao;

import order.model.*;

import java.util.List;
import java.util.Map;

import member.model.Member;

public interface OrderDAOInterface {

    public List<CompanyVO> selectFindCompanyAndRoomTypePriceByPosition(String addressCounty);
    public List<CompanyVO> SelectFindCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(String keyword);
    public CompanyVO selectFindCompanyByCompanyID(Integer companyID);
    public CompanyVO selectFindCompanyNameByCompanyID(CompanyVO companyVO);
    public List<RoomTypeVO> selectFindAllRoomTypeByCompanyID(Integer companyID);
    public RoomTypeVO selectFindRoomTypeByRoomTypeID(RoomTypeVO roomTypeVO);
    public RoomTypeVO selectFindRoomTypeNameByRoomTypeID(RoomTypeVO roomTypeVO);
    public List<RoomTypeImgVO> selectFindAllRoomTypeImgByRoomTypeID(Integer roomTypeID);
    public List<CompanyVO> SelectFindCompanyAllByCompanyID(Integer companyID);
    public List<CompanyVO> selectShowSearchKeyWordByCompanyNameOrAddress(CompanyVO companyVO);
    public int createOrderAndOrderDetail(List<OrderVO> orderVOs);
    public int orderPay(Integer orderID);
    public int updateOrderOverTimeForPay();
    public List<OrderVO> selectFindOrderByCompanyID(Integer companyID);
    public List<OrderDetailVO> selectFindOrderDetailByOrderID(Integer orderID);
    public List<OrderVO> selectFindOrderAllAndStatusWaitPayByOrderID(Integer orderID);
    public List<OrderVO> selectFindOrderAllAndStatusAlreadyPayByOrderID(Integer orderID);
    public Map<Integer, OrderVO> selectFindOrderAllByMemberID(Integer memberID);
    public Map<Integer, OrderVO> selectFindOrderAllAndAlreadyPayByCompanyID(Integer companyID);
    public Member selectFindMemberByMemberID(Integer id);


}
