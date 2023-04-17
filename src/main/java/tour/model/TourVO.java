package tour.model;

import java.io.Serializable;

public class TourVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer tourId;
    private String tourTitle;
    private String startDate;
    private String endDate;
    private String tourImg;
    private Integer memberId;
    private String status;

    @Override
	public String toString() {
		return "TourVO [tourId=" + tourId + ", tourTitle=" + tourTitle + ", startDate=" + startDate + ", endDate="
				+ endDate + ", tourImg=" + tourImg + ", memberId=" + memberId + ", status=" + status + "]";
	}

	public TourVO(Integer tourId, String tourTitle, String startDate, String endDate, String tourImg,
                  Integer memberId, String status) {
        super();
        this.tourId = tourId;
        this.tourTitle = tourTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourImg = tourImg;
        this.memberId = memberId;
        this.status = status;
    }

    

    public TourVO() {

    }

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
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

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public String getTourImg() {
        return tourImg;
    }

    public void setTourImg(String tourImg) {
        this.tourImg = tourImg;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
