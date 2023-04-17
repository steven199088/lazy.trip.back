package member.service;

import java.util.List;

import member.model.Member;

public interface MemberService {
	public String register(Member member);
	public Member login(Member member);
	public String save(Member member);
	public String saveintro(Member member);
	public String savePassword(Member member);
	public byte[] findAvatrById(Integer id);
	public Member findById(Integer id);
	public Member findByAccount(String account);
	public Member changeImgById(Member member);
	public List<Member> findAll();
	public List<Member> findAll(String type, String text);
}
