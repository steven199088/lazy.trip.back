package admin.model;

public class Admin {
	private Integer id;
	private String account; 
	private String password;
	private String name;
	private String status;
	private String status_desciption;
	
	
	
	
	@Override
	public String toString() {
		return "Admin [id=" + id + ", account=" + account + ", password=" + password + ", name=" + name + ", status="
				+ status + ", status_desciption=" + status_desciption + "]";
	}
	public String getStatus_desciption() {
		return status_desciption;
	}
	public void setStatus_desciption(String status_desciption) {
		this.status_desciption = status_desciption;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
