package tour.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TourTagVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tourTagTitle;
	private Integer tourId;
	private Integer memberId;
	private List<String> tourIdArr;

	public List<String> getTourIdArr() {
		return tourIdArr;
	}

	public void setTourIdArr(List<String> tourIdArr) {
		this.tourIdArr = tourIdArr;
	}

	public TourTagVO() {

	}

	public String getTourTagTitle() {
		return tourTagTitle;
	}

	public void setTourTagTitle(String tourTagTitle) {
		this.tourTagTitle = tourTagTitle;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@Override
	public String toString() {
		return "TourTagVO [tourTagTitle=" + tourTagTitle + ", tourId=" + tourId + ", memberId=" + memberId
				+"]";
	}
	
}
