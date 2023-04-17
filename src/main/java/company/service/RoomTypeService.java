package company.service;

import java.util.ArrayList;
import java.util.List;

import company.dao.RoomTypeDAO;
import company.model.RoomDateVO;
import company.model.RoomTypeVO;
import company.dao.RoomTypeDAO_interface;
import company.dao.RoomTypeImgDAO;
import company.dao.RoomTypeImgDAO_interface;

public class RoomTypeService {

	private RoomTypeDAO_interface dao;
	private RoomTypeImgDAO_interface imgDao;

	public RoomTypeService() {
		dao = new RoomTypeDAO();
		imgDao = new RoomTypeImgDAO();
	}

	public RoomTypeVO addRoomType(Integer roomTypeID, Integer companyID, String roomTypeName, Integer roomTypePerson,
			Integer roomTypeQuantity, Integer roomTypePrice) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();

		roomTypeVO.setCompanyID(companyID);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypePerson(roomTypePerson);
		roomTypeVO.setRoomTypeQuantity(roomTypeQuantity);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		dao.insert(roomTypeVO);

		return roomTypeVO;
	}

	public RoomTypeVO updateRoomType(Integer roomTypeID, Integer companyID, String roomTypeName, Integer roomTypePerson,
			Integer roomTypeQuantity, Integer roomTypePrice) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();

		roomTypeVO.setCompanyID(companyID);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypePerson(roomTypePerson);
		roomTypeVO.setRoomTypeQuantity(roomTypeQuantity);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		roomTypeVO.setRoomTypeID(roomTypeID);
		dao.update(roomTypeVO);

		return roomTypeVO;
	}

	public void deleteRoomType(Integer roomTypeID) {
		dao.delete(roomTypeID);
		imgDao.delete(roomTypeID);
	}

	public RoomTypeVO getOneRoomType(Integer roomTypeID) {
		return dao.findByPrimaryKey(roomTypeID);
	}

	public List<RoomTypeVO> getAllByCompanyID(Integer companyID) {
		List<RoomTypeVO> roomTypeVOList = new ArrayList<RoomTypeVO>();
		roomTypeVOList = dao.getAllByCompanyID(companyID);

		List<RoomDateVO> roomDateVoList = new ArrayList<RoomDateVO>();
		roomDateVoList = dao.getDateCompanyID(companyID);
		
		//
		
		for (RoomTypeVO roomTypeVO : roomTypeVOList) {
			List<RoomDateVO> sameRoomTypeIdList = new ArrayList<RoomDateVO>();
			for (RoomDateVO roomDateVO : roomDateVoList) {						
				if (roomDateVO.getRoomTypeID().equals(roomTypeVO.getRoomTypeID())) {
					sameRoomTypeIdList.add(roomDateVO);					
				}				
			}
			roomTypeVO.setRoomDateVo(sameRoomTypeIdList);
		}

		return roomTypeVOList;
	}

}
