package group.dao;

import java.util.List;

import group.model.DiscussionVO;

public interface DiscussionDAO_interface {
	public void insert(DiscussionVO discussionVO);
	public List<DiscussionVO> getAllContentbyGroupId(Integer groupid);
	public void delete(Integer discussion);
	public void deleteAll(Integer id);
	//insert:新增一筆留言
	//getAllbyGroupId:以groupId顯示所有留言
	//delete:刪除留言，僅限留言人(僅限部分預計由前端做)
}
