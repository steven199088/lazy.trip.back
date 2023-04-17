package order.service;

import com.google.gson.JsonObject;
import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;
import order.model.CompanyVO;
import order.model.RoomTypeImgVO;
import order.model.RoomTypeVO;

import java.util.ArrayList;
import java.util.List;

public class BookingCompanyService {
    private final OrderDAOInterface dao;

    public BookingCompanyService() {
        dao = new OrderJDBCDAOImpl();
    }

    public List<CompanyVO> showCompanyAllByCompanyID(Integer companyID) {
        return dao.SelectFindCompanyAllByCompanyID(companyID);
    }

    //舊方法
    public List<JsonObject> showCompanyInformationByCompanyID(Integer companyID) {

        List<JsonObject> result = new ArrayList<>();
        CompanyVO companyVO = new CompanyVO();
        List<RoomTypeVO> roomTypeVOs = new ArrayList<>();
        List<RoomTypeImgVO> roomTypeImgVOs = new ArrayList<>();

        companyVO = dao.selectFindCompanyByCompanyID(companyID);
        roomTypeVOs = dao.selectFindAllRoomTypeByCompanyID(companyID);

        for (RoomTypeVO roomTypeVO : roomTypeVOs) {

            roomTypeImgVOs = dao.selectFindAllRoomTypeImgByRoomTypeID(roomTypeVO.getRoomTypeID());

            for (RoomTypeImgVO roomTypeImgVO : roomTypeImgVOs) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("company_ID", companyVO.getCompanyID());
                jsonObject.addProperty("company_name", companyVO.getCompanyName());
                jsonObject.addProperty("introduction", companyVO.getIntroduction());
                jsonObject.addProperty("address_county", companyVO.getAddressCounty());
                jsonObject.addProperty("address_area", companyVO.getAddressArea());
                jsonObject.addProperty("address_street", companyVO.getAddressStreet());
                jsonObject.addProperty("longitude", companyVO.getLongitude());
                jsonObject.addProperty("latitude", companyVO.getLatitude());
                jsonObject.addProperty("company_img", companyVO.getCompanyImg());
                jsonObject.addProperty("roomType_ID", roomTypeVO.getRoomTypeID());
                jsonObject.addProperty("roomType_name", roomTypeVO.getRoomTypeName());
                jsonObject.addProperty("roomType_person", roomTypeVO.getRoomTypePerson());
                jsonObject.addProperty("roomType_quantity", roomTypeVO.getRoomTypeQuantity());
                jsonObject.addProperty("roomType_price", roomTypeVO.getRoomTypePrice());
                jsonObject.addProperty("roomType_img_ID", roomTypeImgVO.getRoomTypeImgID());
                jsonObject.addProperty("roomType_img", roomTypeImgVO.getRoomTypeImg());
                result.add(jsonObject);
            }
        }
        return result;
    }
}
