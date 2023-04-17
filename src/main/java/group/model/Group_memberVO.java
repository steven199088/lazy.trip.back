package group.model;

import member.model.Member;

public class Group_memberVO implements java.io.Serializable {
	private Integer groupmember;
	private Integer memberid;
	private Integer groupid;
	private String selfintro;
	private String specialneed;
	private Integer gmstatus;
	private Member member;
	private GroupVO groupVO;

	public GroupVO getGroupvo() {
		return groupVO;
	}
	public void setGroupvo(GroupVO groupvo) {
		this.groupVO = groupvo;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Integer getGroupmember() {
		return groupmember;
	}
	public void setGroupmember(Integer groupmember) {
		this.groupmember = groupmember;
	}
	
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	
	public String getSelfintro() {
		return selfintro;
	}
	public void setSelfintro(String selfintro) {
		this.selfintro = selfintro;
	}
	
	public String getSpecialneed() {
		return specialneed;
	}
	public void setSpecialneed(String specialneed) {
		this.specialneed = specialneed;
	}
	
	public Integer getGmstatus() {
		return gmstatus;
	}
	public void setGmstatus(Integer gmstatus) {
		this.gmstatus = gmstatus;
	}
}
