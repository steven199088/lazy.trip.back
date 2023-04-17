package friend.service;

import friend.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    boolean saveMessage(ChatMessage chatMessage);

    List<ChatMessage> retrieveHistoryMessages(Integer chatroomId);
}
