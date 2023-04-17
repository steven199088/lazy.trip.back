package member.dao;

import java.util.List;

import member.model.Comment;

public interface CommentDAO {
	public int insert(Comment comment);
	public int updatById(Comment comment);
	public Comment selectById(Integer id);
	public List<Comment> getAllImg(Integer memId);
	public List<Comment> getAllComment(Integer memId);
	public List<Comment> getAll(Integer memId);
}
