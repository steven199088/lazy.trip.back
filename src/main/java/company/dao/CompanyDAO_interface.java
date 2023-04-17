package company.dao;

import java.util.List;

import company.model.CompanyVO;
import company.model.RoomTypeVO;

public interface CompanyDAO_interface {
	public void insert(CompanyVO companyVO);

	public void update(CompanyVO companyVO);

	public void delete(Integer companyID);

	public CompanyVO findByPrimaryKey(Integer company);

	public List<CompanyVO> getAll();
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)

	public List<CompanyVO> getAllByCompanyID(Integer companyID);
	
	public CompanyVO login(CompanyVO company);
}
