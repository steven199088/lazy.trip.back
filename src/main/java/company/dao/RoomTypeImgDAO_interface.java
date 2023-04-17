package company.dao;

import java.util.List;

import company.model.CompanyVO;
import company.model.RoomTypeImgVO;

public interface RoomTypeImgDAO_interface {
	public void insert(RoomTypeImgVO roomTypeImgVO);

	public void update(RoomTypeImgVO roomTypeImgVO);

	public void delete(Integer roomTypeImgID);

	public RoomTypeImgVO findByRoomTypeID(Integer roomTypeID) ;

	public List<RoomTypeImgVO> getAllByRoomTypeImgID(Integer roomTypeImgID);
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)
}
