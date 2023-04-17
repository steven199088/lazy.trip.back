package member.model;

import java.io.Serializable;

public class CommentImg implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer commentId;
	private byte[] img;
	private String imgBase64Str;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getImgBase64Str() {
		return imgBase64Str;
	}
	public void setImgBase64Str(String imgBase64Str) {
		this.imgBase64Str = imgBase64Str;
	}
	
	

}
