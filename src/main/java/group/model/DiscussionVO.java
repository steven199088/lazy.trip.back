package group.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import member.model.Member;


public class DiscussionVO implements java.io.Serializable{
	private Integer discussion;
	private Integer memberid;
	private String discussioncontent;
	private Long dicussiondate;
	private Integer groupid;
	private Member member;
	private String dicussionday;
	private String dicussiontime;

	
	public String getDicussionday() {
		return dicussionday;
	}
	public void setDicussionday(Long unixTimestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String result = sdf.format(new Date(unixTimestamp * 1000L)); 
		this.dicussionday = sdf.format(new Date(unixTimestamp * 1000L));

	}
	
	public String getDicussiontime() {
		return dicussiontime;
	}
	public void setDicussiontime(Long unixTimestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss ");
		this.dicussiontime = sdf.format(new Date(unixTimestamp * 1000L));
	}


	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public Integer getDiscussion() {
		return discussion;
	}
	public void setDiscussion(Integer discussion) {
		this.discussion = discussion;
	}
	
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	public String getDiscussionContent() {
		return discussioncontent;
	}
	public void setDiscussionContent(String discussioncontent) {
		this.discussioncontent = discussioncontent;
	}
	
	public Long getDicussionDate() {
		return dicussiondate;
	}
	
	public String getDicussionTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
		return sdf.format(dicussiondate);
	}
	
	public void setDicussionDate(Long dicussiondate) {
		this.dicussiondate = dicussiondate;
	}
}
