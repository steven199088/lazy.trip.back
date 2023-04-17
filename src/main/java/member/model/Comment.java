package member.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer memberId;
	private Timestamp time;
	private String text;
	private String staus;
	private String imgbase64Str;
	
	
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", memberId=" + memberId + ", time=" + time + ", text=" + text + ", staus=" + staus
				+ "]";
	}
	public Comment() {
		
	}
	public Comment(Integer id, Integer memberId, Timestamp time, String text, String staus) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.time = time;
		this.text = text;
		this.staus = staus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getStaus() {
		return staus;
	}
	public void setStaus(String staus) {
		this.staus = staus;
	}
	public String getImgbase64Str() {
		return imgbase64Str;
	}
	public void setImgbase64Str(String imgbase64Str) {
		this.imgbase64Str = imgbase64Str;
	}
	
	
	

}
