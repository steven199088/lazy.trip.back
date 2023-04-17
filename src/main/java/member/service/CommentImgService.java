package member.service;

import member.dao.CommentImgDAO;
import member.dao.CommentImgDAOImpl;
import member.model.Comment;
import member.model.CommentImg;

public class CommentImgService {
	private CommentImgDAO dao = new CommentImgDAOImpl();
	
	public void insert(CommentImg commentImg) {
		dao.insert(commentImg);
	}
}
