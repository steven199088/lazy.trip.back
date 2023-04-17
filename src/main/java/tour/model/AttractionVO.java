package tour.model;

import java.io.Serializable;

public class AttractionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer attractionId;
    private String attractionTitle;
    private String location;
    private String introduction;
    private String attractionImg;
    private Double latitude;
    private Double longitude;

    public AttractionVO() {

    }

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractionTitle() {
        return attractionTitle;
    }

    public void setAttractionTitle(String attractionTitle) {
        this.attractionTitle = attractionTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAttractionImg() {
        return attractionImg;
    }

    public void setAttractionImg(String attractionImg) {
        this.attractionImg = attractionImg;
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

}

