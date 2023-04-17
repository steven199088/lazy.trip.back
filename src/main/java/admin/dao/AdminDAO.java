package admin.dao;

import java.util.List;

import admin.model.Admin;
import member.model.Member;


public interface AdminDAO {
	public int insert(Admin admin);
	public int updateById(Admin admin);
	public int updateMemberById(Member member);
	public int stopAccess(Admin admin);
	public Admin login(Admin admin);
	public Admin selectById(Integer id);
	public Admin selectByAccount(String account);
	public Admin selectByName(String name);
	public List<Admin> getAll();
	
}
