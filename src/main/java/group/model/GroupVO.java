package group.model;

import tour.model.TourVO;

public class GroupVO implements java.io.Serializable {
	private Integer groupid;
	private Integer tourid;
	private Integer groupmembercount;
	private String groupname;
	private Integer memberid;
	private Integer ifjoingroupdirectly;
	private TourVO tourVO;
	
	public TourVO getTourVO() {
		return tourVO;
	}
	public void setTourVO(TourVO tourVO) {
		this.tourVO = tourVO;
	}
	public Integer getIfjoingroupdirectly() {
		return ifjoingroupdirectly;
	}
	public void setIfjoingroupdirectly(Integer ifjoingroupdirectly) {
		this.ifjoingroupdirectly = ifjoingroupdirectly;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	
	public Integer getTourid() {
		return tourid;
	}
	public void setTourid(Integer tourid) {
		this.tourid = tourid;
	}
	
	public Integer getGroupmembercount() {
		return groupmembercount;
	}
	public void setGroupmembercount(Integer groupmembercount) {
		this.groupmembercount = groupmembercount;
	}
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
}
