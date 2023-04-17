package group.dao;

import java.util.List;

import group.model.GroupVO;
import group.model.Group_memberVO;
import tour.model.TourVO;

public interface GroupDAO_interface {
	public int insert(GroupVO groupVo);

	public void update(GroupVO groupVo);
	
	public void updateTour(GroupVO groupVo);

	public void delete(Integer groupid);

	public GroupVO findByPrimaryKey(Integer groupid);

	public TourVO findTourNameByID(Integer tourid);

	public void insertWhenCreate(Group_memberVO groupmemberVO);

//        public List<GroupVO> getAllbyMemberId(Integer member_id);

	// findByPk:使用者點入一個群組是使用該group_id查詢
	// getAll:列出memberId所有正在邀請進入的群組
	// insertWhenCreate 創建群組時將群組長加入群組

}
