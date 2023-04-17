package friend.service;

import friend.model.ChatMessage;
import friend.repository.ChatMessageRepository;
import friend.repository.ChatMessageRepositoryImpl;

import java.util.List;

public class ChatMessageServiceImpl implements ChatMessageService {

    private ChatMessageRepository repository;

    public ChatMessageServiceImpl() {
        this.repository = new ChatMessageRepositoryImpl();
    }

    @Override
    public boolean saveMessage(ChatMessage chatMessage) throws RuntimeException {
        return repository.addMessage(chatMessage);
    }

    @Override
    public List<ChatMessage> retrieveHistoryMessages(Integer chatroomId) throws RuntimeException {
        return repository.getAllMessages(chatroomId);
    }
}
