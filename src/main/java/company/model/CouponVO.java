package company.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class CouponVO implements java.io.Serializable{

	private Integer couponID;
	private Integer companyID;
	private String couponText;
	private LocalDateTime couponStartTime;
	private LocalDateTime couponEndTime;
	private Boolean couponStatus;
	private Double couponDiscount;
	private Integer couponQuantity;
	private Integer couponUsedQuantity;
	
	public Integer getCouponID() {
		return couponID;
	}
	public void setCouponID(Integer couponID) {
		this.couponID = couponID;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public String getCouponText() {
		return couponText;
	}
	public void setCouponText(String couponText) {
		this.couponText = couponText;
	}
	
	
	public LocalDateTime getCouponStartTime() {
		return couponStartTime;
	}
	public void setCouponStartTime(LocalDateTime couponStartTime) {
		this.couponStartTime = couponStartTime;
	}
	public LocalDateTime getCouponEndTime() {
		return couponEndTime;
	}
	public void setCouponEndTime(LocalDateTime couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	public Boolean getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(Boolean couponStatus) {
		this.couponStatus = couponStatus;
	}
	
	public Double getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(Double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public Integer getCouponQuantity() {
		return couponQuantity;
	}
	public void setCouponQuantity(Integer couponQuantity) {
		this.couponQuantity = couponQuantity;
	}
	public Integer getCouponUsedQuantity() {
		return couponUsedQuantity;
	}
	public void setCouponUsedQuantity(Integer couponUsedQuantity) {
		this.couponUsedQuantity = couponUsedQuantity;
	}
	
	
}
