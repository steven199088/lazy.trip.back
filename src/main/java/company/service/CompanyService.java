package company.service;

import java.util.List;

import company.dao.CompanyDAO;
import company.dao.CompanyDAO_interface;
import company.model.CompanyVO;
import company.model.RoomTypeVO;

public class CompanyService {

	private CompanyDAO_interface dao;

	public CompanyService() {
		dao = new CompanyDAO();
	}

	public CompanyVO addCompany(Integer companyID, String companyUserName, String companyPassword, String taxID,
			String companyName, String introduction, String addressCounty, String addressArea, String addressStreet,
			Double latitude, Double longitude, String companyImg) {

		CompanyVO companyVO = new CompanyVO();

		companyVO.setCompanyID(companyID);
		companyVO.setCompanyUserName(companyUserName);
		companyVO.setCompanyPassword(companyPassword);
		companyVO.setTaxID(taxID);
		companyVO.setCompanyName(companyName);
		companyVO.setIntroduction(introduction);
		companyVO.setAddressCounty(addressCounty);
		companyVO.setAddressArea(addressArea);
		companyVO.setAddressStreet(addressStreet);
		companyVO.setLatitude(latitude);
		companyVO.setLongitude(longitude);
		companyVO.setCompanyImg(companyImg);

		dao.insert(companyVO);

		return companyVO;
	}

	public CompanyVO updateCompany(Integer companyID, String companyUserName, String companyPassword, String taxID,
			String companyName, String introduction, String addressCounty, String addressArea, String addressStreet,
			Double latitude, Double longitude, String companyImg) {

		CompanyVO companyVO = new CompanyVO();

		companyVO.setCompanyID(companyID);
		companyVO.setCompanyUserName(companyUserName);
		companyVO.setCompanyPassword(companyPassword);
		companyVO.setTaxID(taxID);
		companyVO.setCompanyName(companyName);
		companyVO.setIntroduction(introduction);
		companyVO.setAddressCounty(addressCounty);
		companyVO.setAddressArea(addressArea);
		companyVO.setAddressStreet(addressStreet);
		companyVO.setLatitude(latitude);
		companyVO.setLongitude(longitude);
		companyVO.setCompanyImg(companyImg);
		dao.update(companyVO);

		return companyVO;
	}

	public void deleteCompany(Integer companyID) {
		dao.delete(companyID);
	}

	public CompanyVO getOneCompany(Integer companyID) {
		return dao.findByPrimaryKey(companyID);
	}

	public List<CompanyVO> getAllByCompanyID(Integer companyID) {
		return dao.getAllByCompanyID(companyID);
	}
	public CompanyVO login(CompanyVO companyVO) {
		return companyVO = dao.login(companyVO);
	}
	
}