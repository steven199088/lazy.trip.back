package friend.json;

public enum FriendQueryType {
	
	ACCEPT("A", "accepted"), BLOCKED("B", "blocked"), REQUESTED("R", "requested");

	private String code;
	private String desc;
	
	FriendQueryType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}