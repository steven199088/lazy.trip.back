package article.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class ArticleVO implements Serializable{

	private Integer articleId;
	private String articleTitle;
	private String articleContent;
	private Timestamp articleDate;
	private Timestamp articleDateChange;
	private Integer adminId;
	private Integer memberId;
	private Integer tourId;
	private byte[] articleImage;
	


	
	public ArticleVO() {
	}




	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public Timestamp getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(Timestamp articleDate) {
		this.articleDate = articleDate;
	}

	public Timestamp getArticleDateChange() {
		return articleDateChange;
	}

	public void setArticleDateChange(Timestamp articleDateChange) {
		this.articleDateChange = articleDateChange;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}

	public byte[] getArticleImage() {
		return articleImage;
	}

	public void setArticleImage(byte[] articleImage) {
		this.articleImage = articleImage;
	}
	
}
