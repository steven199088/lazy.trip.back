package tour.model;

import java.io.Serializable;

public class TourComDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tourComId;
	@Override
	public String toString() {
		return "TourComDTO [tourComId=" + tourComId + ", tourTitle=" + tourTitle + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", tourImg=" + tourImg + ", cost=" + cost + ", tourPerson=" + tourPerson
				+ ", companyId=" + companyId + ", status=" + status + ", feature=" + feature + "]";
	}


	public TourComDTO(String tourComId, String tourTitle, String startDate, String endDate, String tourImg, String cost,
			String tourPerson, String companyId, String status, String feature) {
		super();
		this.tourComId = tourComId;
		this.tourTitle = tourTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tourImg = tourImg;
		this.cost = cost;
		this.tourPerson = tourPerson;
		this.companyId = companyId;
		this.status = status;
		this.feature = feature;
	}


	private String tourTitle;
	private String startDate;
	private String endDate;
	private String tourImg;
	private String cost;
	private String tourPerson;
	private String companyId;
	private String status;
	private String feature;

	
	public TourComDTO() {
	}


	public String getTourComId() {
		return tourComId;
	}


	public void setTourComId(String tourComId) {
		this.tourComId = tourComId;
	}


	public String getTourTitle() {
		return tourTitle;
	}


	public void setTourTitle(String tourTitle) {
		this.tourTitle = tourTitle;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getTourImg() {
		return tourImg;
	}


	public void setTourImg(String tourImg) {
		this.tourImg = tourImg;
	}


	public String getCost() {
		return cost;
	}


	public void setCost(String cost) {
		this.cost = cost;
	}


	public String getTourPerson() {
		return tourPerson;
	}


	public void setTourPerson(String tourPerson) {
		this.tourPerson = tourPerson;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getFeature() {
		return feature;
	}


	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	


}

