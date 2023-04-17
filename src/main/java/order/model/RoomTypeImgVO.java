package order.model;

public class RoomTypeImgVO implements java.io.Serializable {
	private Integer roomTypeImgID;
	private Integer roomTypeID;
	private String roomTypeImg;

	

	public String getRoomTypeImg() {
		return roomTypeImg;
	}

	public void setRoomTypeImg(String roomTypeImg) {
		this.roomTypeImg = roomTypeImg;
	}

	public Integer getRoomTypeImgID() {
		return roomTypeImgID;
	}

	public void setRoomTypeImgID(Integer roomTypeImgID) {
		this.roomTypeImgID = roomTypeImgID;
	}

	public Integer getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(Integer roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	@Override
	public String toString() {
		return "RoomTypeImgVO{" +
				"roomTypeImgID=" + roomTypeImgID +
				", roomTypeID=" + roomTypeID +
				", roomTypeImg='" + roomTypeImg + '\'' +
				'}';
	}
}
