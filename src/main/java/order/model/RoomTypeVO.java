package order.model;

public class RoomTypeVO {
	private Integer roomTypeID;
	private Integer companyID;
	private String roomTypeName;
	private Integer roomTypePerson;
	private Integer roomTypeQuantity;
	private Integer roomTypePrice;
	private RoomTypeImgVO roomTypeImgVO;
	
	public RoomTypeImgVO getRoomTypeImgVO() {
		return roomTypeImgVO;
	}
	public void setRoomTypeImgVO(RoomTypeImgVO roomTypeImgVO) {
		this.roomTypeImgVO = roomTypeImgVO;
	}
	public Integer getRoomTypePrice() {
		return roomTypePrice;
	}
	public void setRoomTypePrice(Integer roomTypePrice) {
		this.roomTypePrice = roomTypePrice;
	}
	public Integer getRoomTypeID() {
		return roomTypeID;
	}
	public void setRoomTypeID(Integer roomTypeID) {
		this.roomTypeID = roomTypeID;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public Integer getRoomTypePerson() {
		return roomTypePerson;
	}
	public void setRoomTypePerson(Integer roomTypePerson) {
		this.roomTypePerson = roomTypePerson;
	}
	public Integer getRoomTypeQuantity() {
		return roomTypeQuantity;
	}
	public void setRoomTypeQuantity(Integer roomTypeQuantity) {
		this.roomTypeQuantity = roomTypeQuantity;
	}

	@Override
	public String toString() {
		return "RoomTypeVO{" +
				"roomTypeID=" + roomTypeID +
				", companyID=" + companyID +
				", roomTypeName='" + roomTypeName + '\'' +
				", roomTypePerson=" + roomTypePerson +
				", roomTypeQuantity=" + roomTypeQuantity +
				", roomTypePrice=" + roomTypePrice +
				'}'+roomTypeImgVO.toString();
	}
}
