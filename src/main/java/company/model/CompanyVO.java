package company.model;

public class CompanyVO implements java.io.Serializable {
	private Integer companyID;
	private String companyUserName;
	private String companyPassword;
	private String taxID;
	private String companyName;
	private String introduction;
	private String addressCounty;
	private String addressArea;
	private String addressStreet;
	private Double latitude;
	private Double longitude;
	private String companyImg;
	private RoomTypeVO roomTypeVO;

	public RoomTypeVO getRoomTypeVO() {
		return roomTypeVO;
	}

	public void setRoomTypeVO(RoomTypeVO roomTypeVO) {
		this.roomTypeVO = roomTypeVO;
	}

	public Integer getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}

	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}

	public String getAddressArea() {
		return addressArea;
	}

	public void setAddressArea(String addressArea) {
		this.addressArea = addressArea;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCompanyImg() {
		return companyImg;
	}

	public void setCompanyImg(String companyImg) {
		this.companyImg = companyImg;
	}

	@Override
	public String toString() {
		return "CompanyVO [companyID=" + companyID + ", companyUserName=" + companyUserName + ", companyPassword="
				+ companyPassword + ", taxID=" + taxID + ", companyName=" + companyName + ", introduction="
				+ introduction + ", addressCounty=" + addressCounty + ", addressArea=" + addressArea
				+ ", addressStreet=" + addressStreet + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", companyImg=" + companyImg + ", roomTypeVO=" + roomTypeVO + "]";
	}

	
}
