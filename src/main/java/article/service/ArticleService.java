package article.service;

import java.sql.Timestamp;
import java.util.List;

import article.dao.ArticleDAO;
import article.dao.ArticleDAO_interface;
import article.model.ArticleVO;

public class ArticleService {

	private ArticleDAO_interface dao;

	public ArticleService() {
		dao = new ArticleDAO();
	}

	public ArticleVO addArticle(String articleTitle,String articleContent, Timestamp articleDate,Timestamp articleDateChange, byte[] articleImage, Integer adminId,
			Integer memberId, Integer tourId) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleTitle(articleTitle);
		articleVO.setArticleContent(articleContent);
		articleVO.setArticleDate(articleDate);
		articleVO.setArticleDateChange(null);
		articleVO.setArticleImage(articleImage);
		articleVO.setAdminId(adminId);
		articleVO.setMemberId(memberId);
		articleVO.setTourId(tourId);
		dao.insert(articleVO);
		
		return articleVO;
	}
	
	public ArticleVO updateArticle(Integer articleId,String articleTitle, String articleContent, Timestamp articleDateChange, byte[] articleImage, Integer adminId,
			Integer memberId, Integer tourId) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleId(articleId);
		articleVO.setArticleTitle(articleTitle);
		articleVO.setArticleContent(articleContent);
		articleVO.setArticleDateChange(articleDateChange);
		articleVO.setArticleImage(articleImage);
		articleVO.setAdminId(adminId);
		articleVO.setMemberId(memberId);
		articleVO.setTourId(tourId);
		dao.update(articleVO);
		return articleVO;
	}
	
	public void deleteArticle(Integer articleId) {
		dao.delete(articleId);
	}
	
	public ArticleVO getOneArticle(Integer articleId) {
		return dao.findByPrimaryKey(articleId);
	}
	
	public List<ArticleVO> getOneByMember(Integer memberId) {
		return dao.findByMemberKey(memberId);
	}

	public List<ArticleVO> getAll() {
		return dao.getAll();
	}
	
}
