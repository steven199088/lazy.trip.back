package friend.model;

import java.sql.Timestamp;

public class Friendship {

    private Integer requesterId;
    private Integer addresseeId;
    private Integer statusCode;
    private Timestamp createdAt;

    public Friendship() {

    }

    public Friendship(Integer requesterId, Integer addresseeId, Integer statusCode) {
        super();
        this.requesterId = requesterId;
        this.addresseeId = addresseeId;
        this.statusCode = statusCode;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public Integer getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(Integer addresseeId) {
        this.addresseeId = addresseeId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}