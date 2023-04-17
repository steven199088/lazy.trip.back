package friend.json;

import java.util.List;

public class MemberStatus {

    private int memberId;
    private String status; // online, offline
    private List<Integer> commonChatroomsId;

    private String updateType; // server-push, request, response, ring

    public MemberStatus(int memberId, String status, String updateType) {
        this.memberId = memberId;
        this.status = status;
        this.updateType = updateType;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getCommonChatroomsId() {
        return commonChatroomsId;
    }

    public void setCommonChatroomsId(List<Integer> commonChatroomsId) {
        this.commonChatroomsId = commonChatroomsId;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
}
