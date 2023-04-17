package article.dao;

import java.util.*;

import article.model.ArticleVO;

public interface ArticleDAO_interface {

	public void insert(ArticleVO articleVO);
    public void update(ArticleVO articleVO);
    public void delete(Integer articleId);
    public ArticleVO findByPrimaryKey(Integer articleId);
    public List<ArticleVO> findByMemberKey(Integer memberId);
    public List<ArticleVO> getAll();
	
}
