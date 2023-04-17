package group.dao;

import java.util.List;

import group.model.GroupVO;
import group.model.Group_memberVO;
import member.model.Member;

public interface Group_memberDAO_interface {
	public void insertNeedApprove(Integer id);

	public void insertDirectly(Integer id);
	
	public void opMembers(List<Integer> list);

	public void inviteFriendtoGroup(List<Integer> list);
	
	public int inviteByLink(Integer memId , Integer groupId);
	
	public Group_memberVO getOne(Integer id);

	public void updateInfo(Group_memberVO groupmemberVO);

	public void delete(List<Integer> list);
	
	public void deleteOne(Integer id);

	public void deleteAll(Integer id);

	public void exitGroup(Integer id , Integer group);
	
	public List<Group_memberVO> getAllInvite(Integer memberid);

	public List<GroupVO> getAllGroupByMemberid(Integer memberid);

	public List<Member> getAllMember(Integer groupid);

	public List<Group_memberVO> getMemList(Integer groupid);
	
	
	
    // public List<Group_memberVO> getAllDeleted(Integer groupid);
	// opMembers審核時操作成員加入:1或拒絕:4
	// insertNeedApprove為審核加入申請時 狀態碼:2
	// insertDirectly直接加入時新加入一筆 為update 狀態碼 3->1
	// 成員狀態: 1:是該群組成員; 2:審核中成員 ; 3:被邀請成員 ;4:被拒絕成員
	// getAll:每位使用者加入的所有群組
	// delete:成員被移出群組時刪除一至多筆
	// deleteOne:使用者移除邀請時刪除一筆
	// getAllMember:成員清單 用 group_id=?與g_m_status=1 join member列出圖與暱稱
	// inviteFriendtoGroup:邀請成員加入 狀態碼:3
	// getMemList只獲得一個group的所有member_id
}
