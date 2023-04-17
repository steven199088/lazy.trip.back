package tour.dao;

import java.util.HashSet;
import java.util.Set;

import common.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tour.model.TourTagVO;

public class TourTagDaoImpl implements TourTagDao {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public int saveTourTag(TourTagVO tourTagVO) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.select(9);
			if(tourTagVO.getTourId() != null) {
				// 每個會員在每個主行程，標記哪些標籤
				String memberTourKey = new StringBuilder("member")
						.append(":")
						.append(tourTagVO.getMemberId().toString())
						.append(":")
						.append("tour")
						.append(":")
						.append(tourTagVO.getTourId().toString())
						.append(":")
						.append("tag").toString();
				jedis.sadd(memberTourKey, tourTagVO.getTourTagTitle());
				
				// 每個tag在哪些主行程內
				String tagTitleKey = new StringBuilder("tag")
						.append(":")
						.append(tourTagVO.getTourTagTitle())
						.append(":")
						.append("member")
						.append(":")
						.append(tourTagVO.getMemberId().toString())
						.append(":")
						.append("tour").toString();
				jedis.sadd(tagTitleKey, tourTagVO.getTourId().toString());
			} else {
				// 每個會員新增哪些標籤
				String memberKey = new StringBuilder("member")
						.append(":")
						.append(tourTagVO.getMemberId().toString())
						.append(":")
						.append("tag").toString();
				jedis.sadd(memberKey, tourTagVO.getTourTagTitle());
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			jedis.close();
		}
	}
	// 功能：用在渲染會員標記在每個主行程中有哪些tag
	public Set<String> getTourTagByTourId(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> oneTourTag = new HashSet<>();
		try {
			jedis = pool.getResource();
			jedis.select(9);
			String key = null;
			key = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour")
					.append(":")
					.append(tourTagVO.getTourId().toString())
					.append(":")
					.append("tag").toString();
			oneTourTag = jedis.smembers(key);
			return oneTourTag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> getTourByTourTagTitle(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> selectedTour = new HashSet<>();
		try {
			String key = new StringBuilder("tag")
					.append(":")
					.append(tourTagVO.getTourTagTitle())
					.append(":")
					.append("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour").toString();
			jedis = pool.getResource();
			jedis.select(9);
			selectedTour = jedis.sinter(key);
			return selectedTour;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> getTourTagByMember(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> allTourTag = new HashSet<>();
		try {
			String key = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(9);
			allTourTag = jedis.smembers(key);
			return allTourTag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}
	@Override
	public Set<String> getTourTagByQueryStr(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> queryTourTag = new HashSet<>();
		try {
			String key = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(9);
			Set<String> members = jedis.smembers(key);
			for(String member : members) {
				if(member.contains(tourTagVO.getTourTagTitle().toString())) {
					queryTourTag.add(member);
				}
			}
			return queryTourTag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public int deleteTourTagOnTour(TourTagVO tourTagVO) {
		Jedis jedis = null;
		try {
			String memberTourKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour")
					.append(":")
					.append(tourTagVO.getTourId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(9);
			jedis.srem(memberTourKey, tourTagVO.getTourTagTitle());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
	        return 0;
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public int deleteTourTag(TourTagVO tourTagVO) {
		Jedis jedis = null;
		try {
			String memberKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(9);
			jedis.srem(memberKey, tourTagVO.getTourTagTitle());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
	        return 0;
		} finally {
			jedis.close();
		}
	}
	
	

}
