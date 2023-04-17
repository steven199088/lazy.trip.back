package group.service;

import java.util.Map;

import group.dao.GroupDAOImpl;
import group.dao.GroupDAO_interface;
import group.model.GroupVO;
import group.model.Group_memberVO;
import tour.model.TourVO;

public class GroupService {
	private GroupDAO_interface dao;

	public GroupService() {
		dao = new GroupDAOImpl();

	}

	public void InsertWhenCreate(Group_memberVO groupmemberVO) {
		dao.insertWhenCreate(groupmemberVO);
	}

	public int addGroup(GroupVO groupVO) {

		int result = dao.insert(groupVO);
		return result;
	}

	public GroupVO getOneGroupInfo(Integer groupid) {
		GroupVO groupvo = dao.findByPrimaryKey(groupid);
		return groupvo;
	}

	public void updateGroupInfo(GroupVO groupVO) {
		dao.update(groupVO);
	}

	public TourVO getOneTourInfo(Integer tourid) {
		return dao.findTourNameByID(tourid);
	}
	public void delOneGroup(Integer id) {
		dao.delete(id);
	}
	public void updateGroupTour(GroupVO vo) {
		dao.updateTour(vo);
	}
}
