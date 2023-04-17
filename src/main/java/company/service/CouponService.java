package company.service;

import java.util.List;

import company.dao.CouponDAO_interface;
import company.model.CouponVO;

//public class CouponService{
//	
//		private CouponDAO_interface dao;
//
//		public CouponService() {
//			dao = new CouponDAO();
//		}
//
//		public CouponVO addCoupon(Integer CouponID, String CouponUserName, String CouponPassword, String taxID,
//				String CouponName, String introduction, String addressCounty, String addressArea, String addressStreet,
//				Double latitude, Double longitude, String CouponImg) {
//
//			CouponVO CouponVO = new CouponVO();
//
//			CouponVO.setCouponID(CouponID);
//			CouponVO.setCouponUserName(CouponUserName);
//			CouponVO.setCouponPassword(CouponPassword);
//			CouponVO.setTaxID(taxID);
//			CouponVO.setCouponName(CouponName);
//			CouponVO.setIntroduction(introduction);
//			CouponVO.setAddressCounty(addressCounty);
//			CouponVO.setAddressArea(addressArea);
//			CouponVO.setAddressStreet(addressStreet);
//			CouponVO.setLatitude(latitude);
//			CouponVO.setLongitude(longitude);
//			CouponVO.setCouponImg(CouponImg);
//
//			dao.insert(CouponVO);
//
//			return CouponVO;
//		}
//
//		public CouponVO updateCoupon(Integer CouponID, String CouponUserName, String CouponPassword, String taxID,
//				String CouponName, String introduction, String addressCounty, String addressArea, String addressStreet,
//				Double latitude, Double longitude, String CouponImg) {
//
//			CouponVO CouponVO = new CouponVO();
//
//			CouponVO.setCouponID(CouponID);
//			CouponVO.setCouponUserName(CouponUserName);
//			CouponVO.setCouponPassword(CouponPassword);
//			CouponVO.setTaxID(taxID);
//			CouponVO.setCouponName(CouponName);
//			CouponVO.setIntroduction(introduction);
//			CouponVO.setAddressCounty(addressCounty);
//			CouponVO.setAddressArea(addressArea);
//			CouponVO.setAddressStreet(addressStreet);
//			CouponVO.setLatitude(latitude);
//			CouponVO.setLongitude(longitude);
//			CouponVO.setCouponImg(CouponImg);
//			dao.update(CouponVO);
//
//			return CouponVO;
//		}
//
//		public void deleteCoupon(Integer CouponID) {
//			dao.delete(CouponID);
//		}
//
//		public CouponVO getOneCoupon(Integer CouponID) {
//			return dao.findByPrimaryKey(CouponID);
//		}
//
//		public List<CouponVO> getAllByCouponID(Integer CouponID) {
//			return dao.getAllByCouponID(CouponID);
//		}
//		
//	}
//}
