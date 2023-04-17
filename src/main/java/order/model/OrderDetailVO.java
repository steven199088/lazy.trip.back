package order.model;


import java.io.Serializable;

public class OrderDetailVO implements Serializable {
    private Integer orderDetailID;
    private Integer orderID;
    private Integer roomTypeID;
    private String  roomTypeName;
    private Integer roomTypePerson;
    private Integer orderDetailRoomPrice;
    private Byte orderDetailRoomQuantity;
    private Integer orderDetailCouponDiscountPrice;

    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(Integer roomTypeID) {
        this.roomTypeID = roomTypeID;
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

    public Integer getOrderDetailRoomPrice() {
        return orderDetailRoomPrice;
    }

    public void setOrderDetailRoomPrice(Integer orderDetailRoomPrice) {
        this.orderDetailRoomPrice = orderDetailRoomPrice;
    }

    public Byte getOrderDetailRoomQuantity() {
        return orderDetailRoomQuantity;
    }

    public void setOrderDetailRoomQuantity(Byte orderDetailRoomQuantity) {
        this.orderDetailRoomQuantity = orderDetailRoomQuantity;
    }

    public Integer getOrderDetailCouponDiscountPrice() {
        return orderDetailCouponDiscountPrice;
    }

    public void setOrderDetailCouponDiscountPrice(Integer orderDetailCouponDiscountPrice) {
        this.orderDetailCouponDiscountPrice = orderDetailCouponDiscountPrice;
    }
}