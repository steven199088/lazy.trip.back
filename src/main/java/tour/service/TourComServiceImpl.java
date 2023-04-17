package tour.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import tour.dao.TourComDao;
import tour.dao.TourComDaoImpl;
import tour.dao.TourDao;
import tour.dao.TourDaoImpl;
import tour.model.TourComDTO;
import tour.model.TourComVO;
import tour.model.TourVO;



public class TourComServiceImpl implements TourComService {
	private TourComDao tourComDao;
	private TourDao tourDao;

	public TourComServiceImpl() throws NamingException {
		tourComDao = new TourComDaoImpl();
		tourDao = new TourDaoImpl();
	}

	@Override
	public int tourComCreate(TourComVO tourComVO) {
		final int result = tourComDao.insert(tourComVO);
		return result > 0 ? result : -1;
	}

	@Override
	public TourComVO tourComUpdate(TourComVO tourComVO) {
		final int result = tourComDao.update(tourComVO);
		return result > 0 ? tourComVO : null;
	}

	@Override
	public String tourComDelete(Integer tourComId) {
		final int result = tourComDao.delete(tourComId);
		return result > 0 ? "刪除成功" : null;
	}

	@Override
	public List<TourComVO> tourComQueryAll(Integer companyId) {
		final List<TourComVO> result = tourComDao.getAllByCompany(companyId);
		return result;
	}

	@Override
	public TourComVO getTourInfoByTourComId(Integer companyId) {
		final TourComVO result = tourComDao.getTourByTourComId(companyId);
		return result;
	}

	@Override
	public List<TourComVO> queryAll(Integer memberId) {
		final List<TourComVO> resultTourCom = tourComDao.getAll();
		final List<TourVO> resultTour = tourDao.getAll(memberId);
		final List<String> strList;
		// 取得所有旅客行程的標題array
		strList = resultTour.stream()
							.map(TourVO::getTourTitle)
							.collect(Collectors.toList());
		// 取得所有廠商上架行程的特色標籤名array
		List<String> featureList = resultTourCom.stream()
									.map(TourComVO::getFeature)
									.filter(strList::contains)
									.collect(Collectors.toList());
		System.out.println(featureList.toString());
		
		List<TourComVO> recommendResult = new ArrayList<TourComVO>();
		List<TourComVO> result = new ArrayList<TourComVO>();
		for (TourComVO tour : resultTourCom) {
			if(featureList.contains(tour.getFeature())) {
				result.add(tour);
			}
		}
		recommendResult = result.stream().limit(3).collect(Collectors.toList());
		
		 if(featureList.size() <= 0) {
			 List<TourComVO> top3TourRecommend = resultTourCom.stream()
			 			.limit(3)
			 			.collect(Collectors.toList());
			 return top3TourRecommend;
		 }
		return recommendResult;
	}

	@Override
	public List<TourComVO> queryAllBySelection(TourComDTO tourComVO) {
		final List<TourComVO> result = tourComDao.getInfoBySelection(tourComVO);
		return result;
	}
}
