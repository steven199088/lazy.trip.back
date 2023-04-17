package member.service;

import java.util.List;

import member.dao.CommentDAO;
import member.dao.CommentDAOImpl;
import member.model.Comment;

public class CommentService {
	private CommentDAO dao = new CommentDAOImpl();
	
	public String post(Comment comment) {
		final int resultCount = dao.insert(comment);
		return resultCount > 0 ? "發佈成功" : "發佈失敗";
	}
	public Comment find(Comment comment) {
		
		comment = dao.selectById(comment.getMemberId());
		return comment;
	}
	
	public List<Comment> listAll(Integer id){
		return dao.getAll(id);
	}
	public List<Comment> listComments(Integer id){
		return dao.getAllComment(id);
	}
	public List<Comment> listImgs(Integer id){
		return dao.getAllImg(id);
	}
}
