package friend.repository;

import friend.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository {

    boolean addMessage(ChatMessage chatMessage);

    List<ChatMessage> getAllMessages(Integer chatroomId);
}
