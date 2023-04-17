package group.service;

import java.util.List;

import group.dao.Group_memberDAOImpl;
import group.dao.Group_memberDAO_interface;
import group.model.GroupVO;
import group.model.Group_memberVO;
import member.model.Member;

public class GroupMemberService {
	private Group_memberDAO_interface dao;

	public GroupMemberService() {
		dao = new Group_memberDAOImpl();

	}

	public List<GroupVO> GetAllGroup(Integer memberid) {
		return dao.getAllGroupByMemberid(memberid);
	}

	public List<Member> getAllMember(Integer groupid) {
		return dao.getAllMember(groupid);
	}

	public List<Group_memberVO> getAllMemberList(Integer groupid) {
		return dao.getMemList(groupid);
	}

	public void delGroupMem(List<Integer> list) {
		dao.delete(list);
	}

	public void inviteFriend(List<Integer> list) {
		dao.inviteFriendtoGroup(list);
	}

	public int inviteFriendBylink(Integer memId, Integer groupId) {
		return dao.inviteByLink(memId, groupId);
	}

	public List<Group_memberVO> getAllInvite(Integer memberid) {
		return dao.getAllInvite(memberid);
	}

	public void acceptInvite(Integer id, Integer needApproval) {
		if (needApproval.equals(1)) {
			dao.insertNeedApprove(id);
		} else if (needApproval.equals(2)) {
			dao.insertDirectly(id);
		}
	}

	public Group_memberVO getOne(Integer id) {
		return dao.getOne(id);
	}

	public void updateInfo(Group_memberVO vo) {
		dao.updateInfo(vo);
	}

	public void deleteOne(Integer id) {
		dao.deleteOne(id);
	}

	public void deleteAll(Integer id) {
		dao.deleteAll(id);
	}

	public void exitGroup(Integer id,Integer group) {
		dao.exitGroup(id ,group);
	}
	
	public void opMembers(List<Integer> list) {
		dao.opMembers(list);
	}

}
