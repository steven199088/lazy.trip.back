package company.model;

import java.util.Date;

public class RoomDateVO {
	private Integer roomTypeID;
	private String orderCheckInDate;
	private String orderCheckOutDate;
	
	
	public Integer getRoomTypeID() {
		return roomTypeID;
	}
	public void setRoomTypeID(Integer roomTypeID) {
		this.roomTypeID = roomTypeID;
	}
	public String getOrderCheckInDate() {
		return orderCheckInDate;
	}
	public void setOrderCheckInDate(String orderCheckInDate) {
		this.orderCheckInDate = orderCheckInDate;
	}
	public String getOrderCheckOutDate() {
		return orderCheckOutDate;
	}
	public void setOrderCheckOutDate(String orderCheckOutDate) {
		this.orderCheckOutDate = orderCheckOutDate;
	}
}
