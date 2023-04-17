package friend.model;

public class ChatMessage {

    private Integer senderId;
    private String senderNickname;
    private Integer chatroomId;
    private String message;
    private Integer sentAt;

    public ChatMessage() {

    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSentAt() {
        return sentAt;
    }

    public void setSentAt(Integer sentAt) {
        this.sentAt = sentAt;
    }
}
