package friend.controller;

import com.google.gson.internal.LinkedTreeMap;
import friend.json.ChatMessageWrapper;
import friend.model.ChatMessage;
import friend.model.Chatroom;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;
import friend.service.ChatMessageService;
import friend.service.ChatMessageServiceImpl;
import member.model.Member;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@ServerEndpoint(value = "/chat-ws/{memberId}",
        decoders = friend.json.ChatMessageDecoder.class,
        encoders = friend.json.ChatMessageEncoder.class)
public class ChatServer {

    // 所有上線會員和他們的連線對話映射
    private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();

    ChatMessageService chatMessageService = new ChatMessageServiceImpl();
    ChatMemberService chatMemberService = new ChatMemberServiceImpl();

    Integer memberId;

    @OnOpen
    public void onOpen(@PathParam("memberId") Integer memberId, Session memberSession) throws IOException {
        // 會員上線
        this.memberId = memberId;
        sessionsMap.put(memberId, memberSession);

        System.out.println("All members online: " + sessionsMap.keySet());
    }

    @OnMessage
    public void onMessage(Session memberSession, ChatMessageWrapper wrapper) {
        String action = wrapper.getMessageType();
        Integer chatroomId = wrapper.getChatroomId();

        if (action.equals("retrieve-history")) {
            List<ChatMessage> chatHistory = chatMessageService.retrieveHistoryMessages(chatroomId);
            memberSession.getAsyncRemote().sendObject(new ChatMessageWrapper("reload-history", chatHistory));
        } else if (action.equals("new-message")) {
            Set<Integer> chatroomMembersId =
                    chatMemberService
                            .getMembersByChatroom(chatroomId)
                            .stream()
                            .map(Member::getId)
                            .collect(Collectors.toSet());

            // 儲存訊息
            if (wrapper.getMessageContent() instanceof LinkedTreeMap<?, ?> messageMap) {
                ChatMessage newMessage = new ChatMessage();
                newMessage.setMessage((String) messageMap.get("message"));
                newMessage.setSentAt(((Double) messageMap.get("sentAt")).intValue());
                newMessage.setChatroomId(wrapper.getChatroomId());
                newMessage.setSenderId(wrapper.getMemberId());
                newMessage.setSenderNickname((String) messageMap.get("senderNickname"));
                chatMessageService.saveMessage(newMessage);
            }
            // 推播訊息
            broadcast(wrapper, chatroomMembersId);
        }

    }

    @OnError
    public void onError(Session memberSession, Throwable e) {
        if (memberSession.isOpen()) {
            memberSession.getAsyncRemote().sendObject(new ChatMessageWrapper("error", e));
        }
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session memberSession, CloseReason reason) {
        // 會員下線
        sessionsMap.remove(memberId, memberSession);
        System.out.println("Connection with member ID " + memberId + " closed");
    }

    public static void notifyNewChatroom(Chatroom chatroom) {
        ChatMessageWrapper wrapper = new ChatMessageWrapper("new-chatroom", chatroom);
        ChatMemberService service = new ChatMemberServiceImpl();
        service.getMembersByChatroom(chatroom.getId()).stream()
                .map(Member::getId)
                .map(id -> sessionsMap.get(id))
                .forEach(session -> {
                    if (session.isOpen()) session.getAsyncRemote().sendObject(wrapper);
                });
    }

    private static void broadcast(ChatMessageWrapper wrapper, Set<Integer> receiversId) {
        receiversId
                .stream()
                .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                .map(chatMemberId -> sessionsMap.get(chatMemberId))
                .forEach(chatMemberSession -> {
                    if (chatMemberSession.isOpen()) chatMemberSession.getAsyncRemote().sendObject(wrapper);
                });
    }

}