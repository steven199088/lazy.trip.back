package member.dao;

import java.util.List;

import member.model.Member;

public interface MemberDAO {

	public int insert(Member member);
	public int updateById(Member member);
	public int updatePasswordById(Member member);
	public int updateintroById(Member member);
	public int updateImgById(Member member);
	public Member find(Member member);
	public Member selectById(Integer id);
	public Member selectByAccount(String account);
	public byte[] selectAvatarById(Integer id);
	public List<Member> getAll();
	public List<Member> getAll(String type, String text);
}