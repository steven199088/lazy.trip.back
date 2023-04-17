package friend.json;

public class ChatMessageWrapper {

    private String messageType; // new-message, reload-history, retrieve-history, new-chatroom, error
    private Object messageContent;

    private Integer memberId;

    private Integer chatroomId;

    public ChatMessageWrapper() {

    }

    public ChatMessageWrapper(String messageType, Object messageContent) {
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public ChatMessageWrapper(String messageType, Integer memberId) {
        this.messageType = messageType;
        this.memberId = memberId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Object getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Object messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }
}
