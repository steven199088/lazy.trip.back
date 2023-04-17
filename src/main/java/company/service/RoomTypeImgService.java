package company.service;

import java.util.List;

import company.dao.RoomTypeImgDAO_interface;
import company.dao.RoomTypeImgDAO;
import company.model.RoomTypeImgVO;

public class RoomTypeImgService {

	private RoomTypeImgDAO_interface dao;

	public RoomTypeImgService() {
		dao = new RoomTypeImgDAO();
	}

	public RoomTypeImgVO addRoomTypeImg(Integer roomTypeImgID,Integer roomTypeID, byte[] roomTypeImg
			) {
		//查詢是否有資料  因為考慮到db會新增到兩筆資料所以判斷式
		RoomTypeImgVO checkVO = dao.findByRoomTypeID(roomTypeID);
		
		RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();

		roomTypeImgVO.setRoomTypeImgID(roomTypeImgID);
		roomTypeImgVO.setRoomTypeID(roomTypeID);
		roomTypeImgVO.setRoomTypeImg(roomTypeImg);
		if(checkVO != null) {
			//有資料用更新
			dao.update(roomTypeImgVO);
		}else {
			//無資料用新增
			dao.insert(roomTypeImgVO);
		}
		
		

		return roomTypeImgVO;
	}

	public RoomTypeImgVO updateRoomTypeImg(Integer roomTypeImgID,Integer roomTypeID, byte[] roomTypeImg
) {

		RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();

		roomTypeImgVO.setRoomTypeImgID(roomTypeImgID);
		roomTypeImgVO.setRoomTypeID(roomTypeID);
		roomTypeImgVO.setRoomTypeImg(roomTypeImg);
		dao.update(roomTypeImgVO);

		return roomTypeImgVO;
	}

	public void deleteRoomTypeImg(Integer roomTypeImgID) {
		dao.delete(roomTypeImgID);
	}

	public RoomTypeImgVO getOneRoomTypeImg(Integer roomTypeID) {
		return dao.findByRoomTypeID(roomTypeID);
	}

	public List<RoomTypeImgVO> getAllByRoomTypeImgID(Integer roomTypeImgID) {
		return dao.getAllByRoomTypeImgID(roomTypeImgID);
	}
	
	
	
}
