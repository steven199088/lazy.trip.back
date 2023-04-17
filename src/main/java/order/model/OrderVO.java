package order.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderVO implements Serializable {

    private Integer orderID;
    private Integer memberID;
    private Integer companyID;
    private String couponID;
    private LocalDate orderCheckInDate;
    private LocalDate orderCheckOutDate;
    private Integer orderNumberOfNights;
    private Integer orderTotalPrice;
    private String orderStatus;
    private LocalDateTime orderCreateDatetime;
    private LocalDateTime orderPayDeadline;
    private LocalDateTime orderPayDatetime;
    private String orderPayCardName;
    private String orderPayCardNumber;
    private String orderPayCardYear;
    private String orderPayCardMonth;
    private String travelerName;
    private String travelerIDNumber;
    private String travelerEmail;
    private String travelerPhone;
    private OrderDetailVO orderDetailVO;
    private List<OrderDetailVO> orderDetailVOList;
    private CompanyVO companyVO;


    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public LocalDate getOrderCheckInDate() {
        return orderCheckInDate;
    }

    public void setOrderCheckInDate(LocalDate orderCheckInDate) {
        this.orderCheckInDate = orderCheckInDate;
    }

    public LocalDate getOrderCheckOutDate() {
        return orderCheckOutDate;
    }

    public void setOrderCheckOutDate(LocalDate orderCheckOutDate) {
        this.orderCheckOutDate = orderCheckOutDate;
    }

    public Integer getOrderNumberOfNights() {
        return orderNumberOfNights;
    }

    public void setOrderNumberOfNights(Integer orderNumberOfNights) {
        this.orderNumberOfNights = orderNumberOfNights;
    }

    public Integer getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Integer orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderCreateDatetime() {
        return orderCreateDatetime;
    }

    public void setOrderCreateDatetime(LocalDateTime orderCreateDatetime) {
        this.orderCreateDatetime = orderCreateDatetime;
    }

    public LocalDateTime getOrderPayDeadline() {
        return orderPayDeadline;
    }

    public void setOrderPayDeadline(LocalDateTime orderPayDeadline) {
        this.orderPayDeadline = orderPayDeadline;
    }

    public LocalDateTime getOrderPayDatetime() {
        return orderPayDatetime;
    }

    public void setOrderPayDatetime(LocalDateTime orderPayDatetime) {
        this.orderPayDatetime = orderPayDatetime;
    }

    public String getOrderPayCardName() {
        return orderPayCardName;
    }

    public void setOrderPayCardName(String orderPayCardName) {
        this.orderPayCardName = orderPayCardName;
    }

    public String getOrderPayCardNumber() {
        return orderPayCardNumber;
    }

    public void setOrderPayCardNumber(String orderPayCardNumber) {
        this.orderPayCardNumber = orderPayCardNumber;
    }

    public String getOrderPayCardYear() {
        return orderPayCardYear;
    }

    public void setOrderPayCardYear(String orderPayCardYear) {
        this.orderPayCardYear = orderPayCardYear;
    }

    public String getOrderPayCardMonth() {
        return orderPayCardMonth;
    }

    public void setOrderPayCardMonth(String orderPayCardMonth) {
        this.orderPayCardMonth = orderPayCardMonth;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getTravelerIDNumber() {
        return travelerIDNumber;
    }

    public void setTravelerIDNumber(String travelerIDNumber) {
        this.travelerIDNumber = travelerIDNumber;
    }

    public String getTravelerEmail() {
        return travelerEmail;
    }

    public void setTravelerEmail(String travelerEmail) {
        this.travelerEmail = travelerEmail;
    }

    public String getTravelerPhone() {
        return travelerPhone;
    }

    public void setTravelerPhone(String travelerPhone) {
        this.travelerPhone = travelerPhone;
    }

    public OrderDetailVO getOrderDetailVO() {
        return orderDetailVO;
    }

    public void setOrderDetailVO(OrderDetailVO orderDetailVO) {
        this.orderDetailVO = orderDetailVO;
    }

    public CompanyVO getCompanyVO() {
        return companyVO;
    }

    public void setCompanyVO(CompanyVO companyVO) {
        this.companyVO = companyVO;
    }

    public List<OrderDetailVO> getOrderDetailVOList() {
        return orderDetailVOList;
    }

    public void setOrderDetailVOList(List<OrderDetailVO> orderDetailVOList) {
        this.orderDetailVOList = orderDetailVOList;
    }
}