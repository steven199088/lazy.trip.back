package company.dao;

import java.util.List;

import company.model.RoomDateVO;
import company.model.RoomTypeVO;

public interface RoomTypeDAO_interface {

	public void insert(RoomTypeVO roomTypeVO);

	public void update(RoomTypeVO roomTypeVO);

	public void delete(Integer roomTypeID);

	public RoomTypeVO findByPrimaryKey(Integer roomTypeID);

	public List<RoomTypeVO> getAll();
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)


	List<RoomTypeVO> getAllByCompanyID(Integer companyID);

	List<RoomDateVO> getDateCompanyID(Integer companyID);

}
