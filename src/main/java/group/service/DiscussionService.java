package group.service;

import java.util.List;

import group.dao.DiscussionDAOImpl;
import group.dao.DiscussionDAO_interface;
import group.model.DiscussionVO;

public class DiscussionService {
	private DiscussionDAO_interface dao;
	
	public DiscussionService() {
		dao = new DiscussionDAOImpl();
	}
	
	public List<DiscussionVO> getAllContent(Integer groupid){
		return dao.getAllContentbyGroupId(groupid);
	}
	
	public void insert(DiscussionVO discussionVO) {
		dao.insert(discussionVO);
	}
	
	public void delete(Integer discussion) {
		dao.delete(discussion);
	}
	
	public void deleteAll(Integer id) {
		dao.deleteAll(id);
	}
}
