package order.model;

public class LinePayVO {
	
	private String productName;
	private Integer amount;
	private String currency;
	private String confirmUrl;
	private String linePayOrderID;
	private Integer orderID;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getConfirmUrl() {
		return confirmUrl;
	}
	public void setConfirmUrl(String confirmUrl) {
		this.confirmUrl = confirmUrl;
	}
	public String getLinePayOrderID() {
		return linePayOrderID;
	}
	public void setLinePayOrderID(String linePayOrderID) {
		this.linePayOrderID = linePayOrderID;
	}
	public Integer getOrderID() {
		return orderID;
	}
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	
	



}
