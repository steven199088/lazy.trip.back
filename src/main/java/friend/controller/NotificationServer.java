package friend.controller;

import com.google.gson.Gson;
import friend.json.ChatMessageWrapper;
import friend.json.MemberStatus;
import friend.json.MemberStatusBatch;
import friend.model.Chatroom;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/notification-ws/{memberId}",
        decoders = friend.json.MemberStatusDecoder.class,
        encoders = {friend.json.MemberStatusEncoder.class, friend.json.MemberStatusBatchEncoder.class})
public class NotificationServer {

    // 所有上線會員和他們的連線對話映射
    private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();

    ChatMemberService chatMemberService = new ChatMemberServiceImpl();

    Integer memberId;

    @OnOpen
    public void onOpen(@PathParam("memberId") Integer memberId, Session memberSession) throws IOException {
        // 會員上線
        this.memberId = memberId;
        sessionsMap.put(memberId, memberSession);
        System.out.println("All members online: " + sessionsMap.keySet());
        MemberStatus onlineNotification = new MemberStatus(memberId, "online", "server-push");

        Map<String, Set<Integer>> chatroomsIdAndChatroomMembersId =
                chatMemberService.getChatroomsIdAndChatroomMembersId(memberId);

        // 會員的所有聊天室
        Set<Integer> chatroomsId = chatroomsIdAndChatroomMembersId.get("chatroomsId");

        // Key：會員所有聊天室的全部成員，上線者 Value：其和會員的共同聊天室
        Map<Integer, List<Integer>> membersIdAndChatroomsId =
                chatroomsIdAndChatroomMembersId.get("chatroomMembersId").stream()
                        .filter(chatMemberId -> !Objects.equals(chatMemberId, memberId) && sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> id,
                                id -> chatMemberService.getChatroomsByMember(id).stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));

        // 將上線訊息通知所有上線中聊天室成員
        membersIdAndChatroomsId.forEach((id, commonChatroomsId) -> {
            onlineNotification.setCommonChatroomsId(commonChatroomsId);
            Session session = sessionsMap.get(id);
            if (session.isOpen()) session.getAsyncRemote().sendObject(onlineNotification);
        });

        memberSession.getAsyncRemote().sendObject(new MemberStatusBatch("online-batch", membersIdAndChatroomsId));
    }

    @OnMessage
    public void onMessage(Session memberSession, String text) {
        Gson gson = new Gson();
        MemberStatus update = gson.fromJson(text, MemberStatus.class);

        if (Objects.equals(update.getUpdateType(), "request")) {
            String status = sessionsMap.containsKey(update.getMemberId()) ? "online" : "offline";
            MemberStatus response = new MemberStatus(update.getMemberId(), status, "response");
            if (memberSession.isOpen()) memberSession.getAsyncRemote().sendObject(response);
        }

        if (Objects.equals(update.getUpdateType(), "ring")) {
            if (sessionsMap.containsKey(update.getMemberId())) {
                String greeting = String.format("好友 %s 跟你說早安", update.getStatus());
                MemberStatus ring = new MemberStatus(update.getMemberId(), greeting, "ring");
                sessionsMap.get(update.getMemberId()).getAsyncRemote().sendObject(ring);
            }
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

        MemberStatus offlineNotification = new MemberStatus(memberId, "offline", "server-push");

        Map<String, Set<Integer>> chatroomsIdAndChatroomMembersId =
                chatMemberService.getChatroomsIdAndChatroomMembersId(memberId);

        // 會員的所有聊天室
        Set<Integer> chatroomsId = chatroomsIdAndChatroomMembersId.get("chatroomsId");

        // 會員所有聊天室的全部成員，上線者，其和會員的共同聊天室
        Map<Session, List<Integer>> membersSessionAndChatroomsId =
                chatroomsIdAndChatroomMembersId.get("chatroomMembersId").stream()
                        .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> sessionsMap.get(id),
                                id -> chatMemberService.getChatroomsByMember(id).stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));
        // 將下線訊息通知所有上線中聊天室成員
        membersSessionAndChatroomsId.forEach((session, commonChatroomsId) -> {
            offlineNotification.setCommonChatroomsId(commonChatroomsId);
            if (session.isOpen()) session.getAsyncRemote().sendObject(offlineNotification);
        });
    }

}
